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

package uk.ac.ebi.atlas.search.baseline;

import com.google.common.base.Joiner;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multimap;
import com.google.common.collect.SetMultimap;
import com.google.common.collect.Sets;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.util.StopWatch;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.baseline.FactorGroup;
import uk.ac.ebi.atlas.solr.query.SolrQueryService;
import uk.ac.ebi.atlas.solr.query.conditions.BaselineConditionsSearchService;
import uk.ac.ebi.atlas.solr.query.conditions.IndexedAssayGroup;
import uk.ac.ebi.atlas.trader.cache.BaselineExperimentsCache;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;

@Named
@Scope("request")
//TODO: merge this class with BaselineExperimentProfileSearchService
public class BaselineExperimentAssayGroupSearchService {

    private static final Logger LOGGER = Logger.getLogger(BaselineExperimentAssayGroupSearchService.class);

    private final BaselineExperimentsCache baselineExperimentsCache;

    private BaselineExperimentAssayGroupsDao baselineExperimentAssayGroupsDao;

    private BaselineConditionsSearchService baselineConditionsSearchService;

    private SolrQueryService solrQueryService;

    @Inject
    public BaselineExperimentAssayGroupSearchService(BaselineExperimentAssayGroupsDao baselineExperimentAssayGroupsDao, BaselineConditionsSearchService baselineConditionsSearchService, SolrQueryService solrQueryService, BaselineExperimentsCache baselineExperimentsCache) {
        this.baselineExperimentsCache = baselineExperimentsCache;
        this.baselineExperimentAssayGroupsDao = baselineExperimentAssayGroupsDao;
        this.baselineConditionsSearchService = baselineConditionsSearchService;
        this.solrQueryService = solrQueryService;
    }

    boolean isEmpty(Optional<? extends Collection<?>> coll) {
        return (!coll.isPresent() || coll.get().isEmpty());
    }

    public SortedSet<BaselineExperimentAssayGroup> queryAnySpecies(Set<String> geneIds, Optional<String> condition) {
        return query(geneIds, condition, Optional.<String>absent());
    }

    public SortedSet<BaselineExperimentAssayGroup> query(Set<String> geneIds, Optional<String> condition, Optional<String> species) {
        LOGGER.info(String.format("<query> geneIds=%s, condition=%s", Joiner.on(", ").join(geneIds), condition));
        StopWatch stopWatch = new StopWatch(getClass().getSimpleName());
        stopWatch.start();

        String conditionString = condition.isPresent() ? condition.get() : "";
        String speciesString = species.isPresent() ? species.get() : "";

        Optional<ImmutableSet<IndexedAssayGroup>> indexedAssayGroups = fetchAssayGroupsForCondition(conditionString);

        SetMultimap<String, String> assayGroupsWithExpressionByExperiment = baselineExperimentAssayGroupsDao.fetchExperimentAssayGroupsWithNonSpecificExpression(indexedAssayGroups, Optional.of(geneIds));

        SortedSet<BaselineExperimentAssayGroup> baselineExperimentAssayGroups =
                searchedForConditionButGotNoResults(conditionString, indexedAssayGroups) ? emptySortedSet()
                : buildResults(assayGroupsWithExpressionByExperiment, !StringUtils.isBlank(conditionString), speciesString);

        stopWatch.stop();
        LOGGER.info(String.format("<query> %s results, took %s seconds", baselineExperimentAssayGroups.size(), stopWatch.getTotalTimeSeconds()));

        return baselineExperimentAssayGroups;
    }

    private boolean searchedForConditionButGotNoResults(String condition, Optional<ImmutableSet<IndexedAssayGroup>> indexedAssayGroups) {
        return (!StringUtils.isBlank(condition) && isEmpty(indexedAssayGroups));
    }

    public static SortedSet<BaselineExperimentAssayGroup> emptySortedSet() {
        return Sets.newTreeSet();
    }

    // use above query instead, see TODO below
    @Deprecated
    public SortedSet<BaselineExperimentAssayGroup> query(String geneQuery, String condition, String specie, boolean isExactMatch) {
        LOGGER.info(String.format("<query> geneQuery=%s, condition=%s", geneQuery, condition));
        StopWatch stopWatch = new StopWatch(getClass().getSimpleName());
        stopWatch.start();

        Optional<ImmutableSet<IndexedAssayGroup>> indexedAssayGroups = fetchAssayGroupsForCondition(condition);

        String species = StringUtils.isNotBlank(specie) ? specie : "";

        //TODO: move outside into caller, because this is called twice, here and in DiffAnalyticsSearchService
        Optional<Set<String>> geneIds = solrQueryService.expandGeneQueryIntoGeneIds(geneQuery, species, isExactMatch);

        SetMultimap<String, String> assayGroupsWithExpressionByExperiment = baselineExperimentAssayGroupsDao.fetchExperimentAssayGroupsWithNonSpecificExpression(indexedAssayGroups, geneIds);

        boolean conditionSearch = !isEmpty(indexedAssayGroups);

        SortedSet<BaselineExperimentAssayGroup> baselineExperimentAssayGroups = Sets.newTreeSet();
        if (conditionSearch || StringUtils.isNotEmpty(geneQuery) && StringUtils.isEmpty(condition)) {
            baselineExperimentAssayGroups = buildResults(assayGroupsWithExpressionByExperiment, conditionSearch, species);
        }

        stopWatch.stop();
        LOGGER.info(String.format("<query> %s results, took %s seconds", baselineExperimentAssayGroups.size(), stopWatch.getTotalTimeSeconds()));

        return baselineExperimentAssayGroups;
    }

    SortedSet<BaselineExperimentAssayGroup> buildResults(SetMultimap<String, String> assayGroupsWithExpressionByExperiment, boolean conditionSearch, String searchSpecies) {
        SortedSet<BaselineExperimentAssayGroup> results = Sets.newTreeSet();

        for (Map.Entry<String, Collection<String>> exprAssayGroups : assayGroupsWithExpressionByExperiment.asMap().entrySet()) {

            String experimentAccession = exprAssayGroups.getKey();
            Collection<String> assayGroupIds = exprAssayGroups.getValue();

            BaselineExperiment experiment = baselineExperimentsCache.getExperiment(experimentAccession);

            Multimap<FactorGroup, String> assayGroupIdsByFilterFactors = experiment.getExperimentalFactors().groupAssayGroupIdsByNonDefaultFactor(assayGroupIds);

            for (Map.Entry<FactorGroup, Collection<String>> filterFactorAssayGroupIds : assayGroupIdsByFilterFactors.asMap().entrySet()) {
                FactorGroup filterFactor = filterFactorAssayGroupIds.getKey();
                Collection<String> assayGroupIdsForFilterFactor = filterFactorAssayGroupIds.getValue();

                String experimentSpecies = experiment.isMultiOrganismExperiment() ? filterFactor.getOrganismFactorValue() : experiment.getFirstOrganism();

                //filter by searchSpecies
                if (StringUtils.isBlank(searchSpecies) || experimentSpecies.equalsIgnoreCase(searchSpecies)) {
                    BaselineExperimentAssayGroup result = new BaselineExperimentAssayGroup(experiment.getAccession(), experiment.getDisplayName(),
                            experimentSpecies, experiment.getExperimentalFactors().getDefaultQueryFactorType());
                    result.setFilterFactors(filterFactor);
                    if (conditionSearch) {
                        result.setAssayGroupsWithCondition(ImmutableSet.copyOf(assayGroupIdsForFilterFactor), experiment);
                    }
                    results.add(result);
                }
            }

        }
        return results;
    }

    private Optional<ImmutableSet<IndexedAssayGroup>> fetchAssayGroupsForCondition(String condition) {
        if (StringUtils.isBlank(condition)) {
            return Optional.absent();
        }

        SetMultimap<String, String> assayGroupsPerExperiment = baselineConditionsSearchService.findAssayGroupsPerExperiment(condition);

        ImmutableSet<IndexedAssayGroup> indexedAssayGroups = createSetOfIndexedAssayGroups(assayGroupsPerExperiment);

        return Optional.of(indexedAssayGroups);
    }

    static ImmutableSet<IndexedAssayGroup> createSetOfIndexedAssayGroups(SetMultimap<String, String> assayGroupsPerExperiment) {
        ImmutableSet.Builder<IndexedAssayGroup> builder = ImmutableSet.builder();

        for (Map.Entry<String, String> entry : assayGroupsPerExperiment.entries()) {
            builder.add(new IndexedAssayGroup(entry.getKey(), entry.getValue()));
        }

        return builder.build();
    }

}
