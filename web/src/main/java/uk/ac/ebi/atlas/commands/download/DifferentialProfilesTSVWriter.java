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

package uk.ac.ebi.atlas.commands.download;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import uk.ac.ebi.atlas.commands.context.DifferentialRequestContext;
import uk.ac.ebi.atlas.model.differential.Contrast;
import uk.ac.ebi.atlas.model.differential.DifferentialExpression;
import uk.ac.ebi.atlas.model.differential.DifferentialProfile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public abstract class DifferentialProfilesTSVWriter<T extends DifferentialProfile<K>, K extends DifferentialExpression> extends GeneProfilesTSVWriter<T, Contrast> {
    private static final Logger LOGGER = Logger.getLogger(RnaSeqProfilesTSVWriter.class);
    private Resource tsvFileMastheadResource;
    private String tsvFileMastheadTemplate;

    @Value("classpath:/file-templates/download-headers-differential.txt")
    public void setTsvFileMastheadTemplateResource(Resource tsvFileMastheadResource) {
        this.tsvFileMastheadResource = tsvFileMastheadResource;
    }

    @PostConstruct
    void initTsvFileMastheadTemplate() {
        try (InputStream inputStream = tsvFileMastheadResource.getInputStream()) {
            tsvFileMastheadTemplate = IOUtils.toString(inputStream);
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw new IllegalStateException(e);
        }
    }

    @Override
    protected String[] getConditionColumnHeaders(Set<Contrast> conditions) {
        List<String> conditionColumnHeaders = new ArrayList<>();

        List<String> attributeLabels = getExpressionColumnsHeaders();
        for (Contrast condition : conditions) {
            for (String attributeLabel : attributeLabels) {
                conditionColumnHeaders.add(condition.getDisplayName() + "." + attributeLabel);
            }
        }
        return conditionColumnHeaders.toArray(new String[conditionColumnHeaders.size()]);
    }

    protected abstract DifferentialRequestContext getRequestContext();

    protected abstract List<String> getExpressionColumnsHeaders();

    @Override
    protected String getTsvFileMasthead() {
        DifferentialRequestContext requestContext = getRequestContext();
        String geneQuery = requestContext.getGeneQuery();
        String specific = requestContext.isSpecific() ? " specifically" : "";
        String exactMatch = requestContext.isExactMatch() ? " exactly" : "";
        String regulation = " " + requestContext.getRegulation().getLabel();
        String selectedContrasts = formatSelectedContrasts(requestContext);
        double cutoff = requestContext.getCutoff();
        String experimentAccession = requestContext.getExperiment().getAccession();
        String timeStamp = new SimpleDateFormat("E, dd-MMM-yyyy HH:mm:ss").format(new Date());
        return MessageFormat.format(tsvFileMastheadTemplate, geneQuery, exactMatch, specific, regulation, selectedContrasts, cutoff,
                experimentAccession, timeStamp);

    }

    private String formatSelectedContrasts(DifferentialRequestContext requestContext) {
        if (requestContext.getSelectedQueryFactors().isEmpty()) {
            return "any contrast";
        }
        Collection<String> selectedContrasts = Collections2.transform(requestContext.getSelectedQueryFactors(), new Function<Contrast, String>() {
            @Override
            public String apply(Contrast constrast) {
                return constrast.getDisplayName();
            }
        });
        return "contrast" + (selectedContrasts.size() == 1 ? ": " : "s: ") + Joiner.on(", ").join(selectedContrasts);
    }

    @Override
    protected String[] extractConditionLevels(T geneProfile, Set<Contrast> contrasts) {

        String[] conditionLevelStrings = null;

        for (Contrast contrast : contrasts) {
            K expression = geneProfile.getExpression(contrast);
            String[] expressionLevelStrings = getExpressionLevelStrings(expression);
            conditionLevelStrings = ArrayUtils.addAll(conditionLevelStrings, expressionLevelStrings);
        }
        return conditionLevelStrings;
    }

    final String[] getExpressionLevelStrings(K expression) {
        String[] expressionLevelData = new String[getExpressionColumnsHeaders().size()];
        if (expression == null) {
            return expressionLevelData;
        }
        List<String> expressionStrings = Lists.transform(getExpressionLevelData(expression), expressionValueToString());

        return expressionStrings.toArray(new String[expressionStrings.size()]);
    }

    protected abstract List<Double> getExpressionLevelData(K expression);

    protected Function<Double, String> expressionValueToString() {
        return new Function<Double, String>() {
            @Override
            public String apply(Double expressionValue) {
                if (Double.isInfinite(expressionValue)) {
                    return "NA";
                }
                return Double.toString(expressionValue);
            }
        };

    }

    protected String getValueToString(double value) {
        if (Double.isInfinite(value)) {
            return "NA";
        }
        return Double.toString(value);
    }
}
