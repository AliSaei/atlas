/*
 * Copyright 2008-2013 Microarray Informatics Team, EMBL-European Bioinformatics Institute
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
 * For further details of the Gene Expression Atlas project, including source code,
 * downloads and documentation, please see:
 *
 * http://gxa.github.com/gxa
 */

package uk.ac.ebi.atlas.trader.loader;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.apache.velocity.util.StringUtils;
import uk.ac.ebi.atlas.experimentimport.ExperimentDTO;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.AssayGroups;
import uk.ac.ebi.atlas.model.ExperimentDesign;
import uk.ac.ebi.atlas.model.baseline.*;
import uk.ac.ebi.atlas.trader.ConfigurationTrader;
import uk.ac.ebi.atlas.trader.SpeciesKingdomTrader;

import javax.inject.Inject;
import java.io.IOException;
import java.util.*;

import static com.google.common.base.Preconditions.checkNotNull;

//This class is wired into CacheConfiguration. Guava CacheBuilder, that is the one only client of this class, is not spring managed and only accepts an initial instance
//of BaselineExperimentsCacheLoader, hence BaselineExperimentsCacheLoader is a singleton. However BaselineExperimentsCacheLoader uses ExperimentBuilder and ExperimentalFactorsBuilder
//which have a prototype scope. To get around this BaselineExperimentsCacheLoader uses lookup-method injection to get a new prototypical instance of
//ExperimentBuilder/ExperimentalFactorsBuilder every time the load method is invoked
//TODO - why don't we just use new here instead of getting a prototypical instance?
public abstract class ProteomicsBaselineExperimentsCacheLoader extends ExperimentsCacheLoader<ProteomicsBaselineExperiment> {

    private final ProteomicsBaselineExperimentExpressionLevelFile expressionLevelFile;
    private final ConfigurationTrader configurationTrader;
    private final SpeciesKingdomTrader speciesKingdomTrader;

    @Inject
    protected ProteomicsBaselineExperimentsCacheLoader(ProteomicsBaselineExperimentExpressionLevelFile expressionLevelFile,
                                                       ConfigurationTrader configurationTrader, SpeciesKingdomTrader speciesKingdomTrader) {

        this.configurationTrader = configurationTrader;
        this.expressionLevelFile = expressionLevelFile;
        this.speciesKingdomTrader = speciesKingdomTrader;
    }

    @Override
    protected ProteomicsBaselineExperiment load(ExperimentDTO experimentDTO, String experimentDescription,
                                      boolean hasExtraInfoFile, ExperimentDesign experimentDesign) throws IOException {

        String experimentAccession = experimentDTO.getExperimentAccession();

        BaselineExperimentConfiguration factorsConfig = configurationTrader.getFactorsConfiguration(experimentAccession);

        AssayGroups assayGroups = configurationTrader.getExperimentConfiguration(experimentAccession).getAssayGroups();

        boolean hasRData = configurationTrader.getExperimentConfiguration(experimentAccession).hasRData();

        String kingdom = speciesKingdomTrader.getKingdom(experimentDTO.getSpecies());
        if (kingdom.isEmpty()) {
            Iterator<String> speciesIterator = experimentDTO.getSpecies().iterator();
            while (speciesIterator.hasNext() && kingdom.isEmpty()) {
                kingdom = speciesKingdomTrader.getKingdom(factorsConfig.getSpeciesMapping().get(speciesIterator.next()));
            }
        }

        String[] orderedAssayGroupIds = expressionLevelFile.readOrderedAssayGroupIds(experimentAccession);

        ExperimentalFactors experimentalFactors = createExperimentalFactors(experimentAccession, experimentDesign, factorsConfig, assayGroups, orderedAssayGroupIds);

        return createExperimentBuilder().forOrganisms(experimentDTO.getSpecies())
                .ofKingdom(kingdom)
                .withAccession(experimentAccession)
                .withLastUpdate(experimentDTO.getLastUpdate())
                .withDescription(experimentDescription)
                .withExtraInfo(hasExtraInfoFile)
                .withRData(hasRData)
                .withDisplayName(factorsConfig.getExperimentDisplayName())
                .withSpeciesMapping(factorsConfig.getSpeciesMapping())
                .withPubMedIds(experimentDTO.getPubmedIds())
                .withAssayGroups(assayGroups)
                .withExperimentDesign(experimentDesign)
                .withExperimentalFactors(experimentalFactors)
                .withDataProviderURL(factorsConfig.getDataProviderURL())
                .withDataProviderDescription(factorsConfig.getDataProviderDescription())
                .createProteomics();

    }

    //TODO: move this elsewhere, it is duplication of the same method in BaselineExperimentsCacheLoader
    private ExperimentalFactors createExperimentalFactors(String experimentAccession, ExperimentDesign experimentDesign, BaselineExperimentConfiguration factorsConfig, AssayGroups assayGroups, String[] orderedAssayGroupIds) {
        String defaultQueryFactorType = factorsConfig.getDefaultQueryFactorType();
        Set<Factor> defaultFilterFactors = factorsConfig.getDefaultFilterFactors();
        Set<String> requiredFactorTypes = getRequiredFactorTypes(defaultQueryFactorType, defaultFilterFactors);
        Map<String, String> factorNamesByType = getFactorDisplayNameByType(experimentDesign.getFactorHeaders(), requiredFactorTypes);

        List<FactorGroup> orderedFactorGroups = extractOrderedFactorGroups(experimentAccession, orderedAssayGroupIds, assayGroups, experimentDesign);
        Map<String, FactorGroup> orderedFactorGroupsByAssayGroup = extractOrderedFactorGroupsByAssayGroup(orderedAssayGroupIds, assayGroups, experimentDesign);

        ExperimentalFactorsBuilder experimentalFactorsBuilder = createExperimentalFactorsBuilder();

        return experimentalFactorsBuilder
                .withOrderedFactorGroups(orderedFactorGroups)
                .withOrderedFactorGroupsByAssayGroupId(orderedFactorGroupsByAssayGroup)
                .withMenuFilterFactorTypes(factorsConfig.getMenuFilterFactorTypes())
                .withFactorTypes(factorsConfig.getFactorTypes())
                .withFactorNamesByType(factorNamesByType)
                .withDefaultQueryType(factorsConfig.getDefaultQueryFactorType())
                .withDefaultFilterFactors(defaultFilterFactors)
                .create();
    }

    Set<String> getRequiredFactorTypes(String defaultQueryFactorType, Set<Factor> defaultFilterFactors) {
        Set<String> requiredFactorTypes = Sets.newHashSet(defaultQueryFactorType);

        for (Factor defaultFilterFactor : defaultFilterFactors) {
            requiredFactorTypes.add(defaultFilterFactor.getType());
        }
        return requiredFactorTypes;
    }

    List<FactorGroup> extractOrderedFactorGroups(String experimentAccession, String[] orderedAssayGroupIds, final AssayGroups assayGroups, ExperimentDesign experimentDesign) {

        List<FactorGroup> factorGroups = Lists.newArrayList();

        for (String groupId : orderedAssayGroupIds) {
            AssayGroup assayGroup = assayGroups.getAssayGroup(groupId);

            checkNotNull(assayGroup, String.format("%s: No assay group \"%s\"", experimentAccession, groupId));

            FactorGroup factorGroup = experimentDesign.getFactors(assayGroup.getFirstAssayAccession());
            factorGroups.add(factorGroup);

        }
        return factorGroups;

    }

    Map<String, FactorGroup> extractOrderedFactorGroupsByAssayGroup(String[] orderedAssayGroupIds, final AssayGroups assayGroups, ExperimentDesign experimentDesign) {

        Map<String, FactorGroup> factorGroups = Maps.newLinkedHashMap();

        for (String groupId : orderedAssayGroupIds) {
            AssayGroup assayGroup = assayGroups.getAssayGroup(groupId);

            FactorGroup factorGroup = experimentDesign.getFactors(assayGroup.getFirstAssayAccession());
            factorGroups.put(groupId, factorGroup);

        }
        return factorGroups;

    }

    protected Map<String, String> getFactorDisplayNameByType(SortedSet<String> factorHeaders, Set<String> requiredFactorTypes) {
        Map<String, String> factorDisplayNameByType = Maps.newHashMap();

        for (String factorType : factorHeaders) {
            String normalizedFactorType = Factor.normalize(factorType);
            if (requiredFactorTypes.contains(normalizedFactorType)) {
                factorDisplayNameByType.put(normalizedFactorType, prettifyFactorType(factorType));
            }
        }
        return factorDisplayNameByType;
    }

    //TODO: move this into ExperimentFactorsLoader
    protected String prettifyFactorType(String factorType) {
        StringBuilder result = new StringBuilder();
        String[] split = factorType.replaceAll("_", " ").split(" ");
        boolean firstTokenCapitalized = false;
        for (String token : split) {
            int nbUpperCase = countUpperCaseLetters(token);
            if (nbUpperCase > 1) {
                result.append(token);
            } else {
                token = token.toLowerCase();

                if (!firstTokenCapitalized) {
                    token = StringUtils.capitalizeFirstLetter(token);
                    firstTokenCapitalized = true;
                }
                result.append(token);
            }
            result.append(" ");
        }

        return result.toString().trim();
    }

    protected int countUpperCaseLetters(String token) {
        int nbUpperCase = 0;
        for (int i = 0; i < token.length(); i++) {
            if (Character.isUpperCase(token.charAt(i))) {
                nbUpperCase++;
            }
        }
        return nbUpperCase;
    }

    protected abstract BaselineExperimentBuilder createExperimentBuilder();

    protected abstract ExperimentalFactorsBuilder createExperimentalFactorsBuilder();

}
