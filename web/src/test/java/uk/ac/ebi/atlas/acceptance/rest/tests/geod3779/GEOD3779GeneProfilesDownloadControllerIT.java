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

package uk.ac.ebi.atlas.acceptance.rest.tests.geod3779;


import com.jayway.restassured.response.Response;
import com.jayway.restassured.response.ResponseBody;
import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.rest.EndPoint;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;

public class GEOD3779GeneProfilesDownloadControllerIT {

    private static final String EXPRESSION_ATLAS_VERSION = "# Expression Atlas version:";
    private static final String QUERY_DESCRIPTION = "# Query: Genes matching: '', specifically up/down differentially expressed in any contrast given the False Discovery Rate cutoff: 0.05 in experiment E-GEOD-3779";
    private static final String TIMESTAMP = "# Timestamp:";
    private EndPoint subject = new EndPoint("/gxa/experiments/E-GEOD-3779.tsv?geneQuery=&exactMatch=false");

    @Test
    public void verifyHeader() {
        Response response = subject.getResponse();

        // http status code OK
        assertThat(response.getStatusCode(), is(200));

        // unicode encoded plain text
        assertThat(response.getContentType(), is("application/octet-stream;charset=ISO-8859-1"));

        // filename of attachment should be a zip
        assertThat(response.getHeader("Content-Disposition"), is("attachment; filename=\"E-GEOD-3779-query-results.tsv.zip\""));
    }

    @Test
    public void verifyLenghtOfDocument() throws Exception {
        ResponseBody body = subject.getResponseBody();

        ZipInputStream zipInputStream = new ZipInputStream(body.asInputStream());
        int entries = 0;
        while (zipInputStream.getNextEntry() != null) {
            entries++;
        }
        assertThat(entries, is(2));
    }

    @Test
    public void verifyFirstEntry() throws Exception {
        ResponseBody body = subject.getResponseBody();

        ZipInputStream zipInputStream = new ZipInputStream(body.asInputStream());
        ZipEntry zipEntry = zipInputStream.getNextEntry();
        assertThat(zipEntry.getName(), is("E-GEOD-3779_A-AFFY-23-query-results.tsv"));

        BufferedReader reader = new BufferedReader(new InputStreamReader(zipInputStream));
        assertThat(reader.readLine(), startsWith(EXPRESSION_ATLAS_VERSION));
        assertThat(reader.readLine(), is(QUERY_DESCRIPTION));
        assertThat(reader.readLine(), startsWith(TIMESTAMP));
        assertThat(reader.readLine(), is("Gene name\tDesign Element\tgenotype:'p107 -/-' vs 'wild type' on A-AFFY-23.p-value\tgenotype:'p107 -/-' vs 'wild type' on A-AFFY-23.log2foldchange\tgenotype:'p107 -/-' vs 'wild type' on A-AFFY-23.t-statistic"));
        assertThat(reader.readLine(), is("Mycl1\t1422088_at\t0.04\t0.00409480833333209\t0.0502355223125666"));
    }

    @Test
    public void verifySecondEntry() throws Exception {
        ResponseBody body = subject.getResponseBody();

        ZipInputStream zipInputStream = new ZipInputStream(body.asInputStream());
        zipInputStream.getNextEntry();
        ZipEntry zipEntry = zipInputStream.getNextEntry();
        assertThat(zipEntry.getName(), is("E-GEOD-3779_A-AFFY-24-query-results.tsv"));

        BufferedReader reader = new BufferedReader(new InputStreamReader(zipInputStream));
        assertThat(reader.readLine(), startsWith(EXPRESSION_ATLAS_VERSION));
        assertThat(reader.readLine(), is(QUERY_DESCRIPTION));
        assertThat(reader.readLine(), startsWith(TIMESTAMP));
        assertThat(reader.readLine(), is("Gene name\tDesign Element\tgenotype:'p107 -/-' vs 'wild type' on A-AFFY-24.p-value\tgenotype:'p107 -/-' vs 'wild type' on A-AFFY-24.log2foldchange\tgenotype:'p107 -/-' vs 'wild type' on A-AFFY-24.t-statistic"));
        assertThat(reader.readLine(), is("Snx30\t1456479_at\t0.02\t0.00641014999999978\t0.120394369986336"));
    }

}