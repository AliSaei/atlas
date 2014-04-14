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

import au.com.bytecode.opencsv.CSVWriter;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ArrayUtils;
import uk.ac.ebi.atlas.commons.streams.ObjectInputStream;
import uk.ac.ebi.atlas.model.GeneProfilesList;
import uk.ac.ebi.atlas.model.Profile;
import uk.ac.ebi.atlas.profiles.differential.ProfileStreamOptions;

import javax.inject.Inject;
import java.io.IOException;
import java.io.Writer;
import java.util.Set;

public abstract class GeneProfilesTSVWriter<T extends Profile, K, O extends ProfileStreamOptions> implements AutoCloseable {

    private static final String GENE_NAME_COLUMN_NAME = "Gene Name";
    private static final String GENE_ID_COLUMN_NAME = "Gene ID";
    private final CsvWriterFactory csvWriterFactory;

    private CSVWriter csvWriter;
    private Writer responseWriter;

    @Inject
    public GeneProfilesTSVWriter(CsvWriterFactory csvWriterFactory) {
        this.csvWriterFactory = csvWriterFactory;
    }

    public long write(GeneProfilesList<T> geneProfilesList, Set<K> conditions, O options) throws IOException {

        responseWriter.write(getTsvFileMasthead(options) + "\n");
        csvWriter.writeNext(buildCsvColumnHeaders(conditions, options));

        for (T profile : geneProfilesList) {
            String[] csvRow = buildCsvRow(profile, conditions);
            csvWriter.writeNext(csvRow);
        }

        csvWriter.flush();

        return (long) geneProfilesList.size();
    }

    public long write(ObjectInputStream<T> inputStream, Set<K> conditions, O options) throws IOException {

        responseWriter.write(getTsvFileMasthead(options) + "\n");
        csvWriter.writeNext(buildCsvColumnHeaders(conditions, options));

        long count = 0;
        T geneProfile;
        while ((geneProfile = inputStream.readNext()) != null) {
            ++count;
            String[] csvRow = buildCsvRow(geneProfile, conditions);
            csvWriter.writeNext(csvRow);
        }

        csvWriter.flush();

        return count;
    }

    public long write(Iterable<T> inputStream, Set<K> conditions, O options) throws IOException {

        responseWriter.write(getTsvFileMasthead(options) + "\n");
        csvWriter.writeNext(buildCsvColumnHeaders(conditions, options));

        long count = 0;
        for (T geneProfile : inputStream) {
            ++count;
            String[] csvRow = buildCsvRow(geneProfile, conditions);
            csvWriter.writeNext(csvRow);
        }

        csvWriter.flush();

        return count;
    }

    @Override
    public void close() throws IOException {
        csvWriter.close();
    }

    protected abstract String[] getConditionColumnHeaders(Set<K> conditions);

    protected abstract String getTsvFileMasthead(O options);

    protected String[] getProfileIdColumnHeaders(O options){
        return new String[]{GENE_ID_COLUMN_NAME, GENE_NAME_COLUMN_NAME};
    }

    protected String[] buildCsvColumnHeaders(Set<K> conditionValues, O options) {
        String[] profileIdColumnHeaders = getProfileIdColumnHeaders(options);
        String[] conditionColumnHeaders = getConditionColumnHeaders(conditionValues);
        return buildCsvRow(profileIdColumnHeaders, conditionColumnHeaders);
    }

    String[] buildCsvRow(final T geneProfile, Set<K> conditions) {
        String[] expressionLevels = extractConditionLevels(geneProfile, conditions);

        String[] rowHeaders = getRowHeaders(geneProfile);
        return buildCsvRow(rowHeaders, expressionLevels);
    }

    String[] getRowHeaders(T geneProfile) {
        String geneProfileId = geneProfile.getId();
        String geneName = geneProfile.getName();
        String secondaryRowHeader = getSecondaryRowHeader(geneProfile);

        //for Microarray experiment
        if (!StringUtils.isBlank(secondaryRowHeader)) {
            return new String[]{geneProfileId, geneName, secondaryRowHeader};
        }
        //For geneSet gene Id is null
        if(geneProfileId == null) {
            return new String[]{geneName};
        }
        return new String[]{geneProfileId, geneName};
    }

    protected abstract String getSecondaryRowHeader(T profile);

    protected abstract String[] extractConditionLevels(T geneProfile, Set<K> conditions);

    protected String[] buildCsvRow(String[] rowHeaders, String[] values) {
        return ArrayUtils.addAll(rowHeaders, values);
    }

    public void setResponseWriter(Writer responseWriter) {
        this.responseWriter = responseWriter;
        csvWriter = csvWriterFactory.createTsvWriter(responseWriter);
    }

}