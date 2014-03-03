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

package uk.ac.ebi.atlas.acceptance.rest.tests.mtab1066;


import com.jayway.restassured.response.Response;
import com.jayway.restassured.response.ResponseBody;
import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.rest.EndPoint;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class MTAB1066MicroarrayExperimentDownloadControllerIT {

    private EndPoint subject = new EndPoint("/gxa/experiments/E-MTAB-1066.tsv?foldChangeCutOff=0");

    @Test
    public void verifyHeader() {
        Response response = subject.getResponse();

        // http status code OK
        assertThat(response.getStatusCode(), is(200));

        // unicode encoded plain text
        assertThat(response.getContentType(), is("text/plain;charset=utf-8"));

        // filename of attachment should be ending in -query-results.tsv
        assertThat(response.getHeader("Content-Disposition"), containsString("-query-results.tsv"));
    }

    @Test
    public void verifyFirstLine() {

        List<String> firstLine = subject.getRowValues(3);

        assertThat(firstLine,
                contains("Gene ID", "Gene Name", "Design Element", "genotype:'cycC mutant' vs 'wild type'.p-value", "genotype:'cycC mutant' vs 'wild type'.log2foldchange", "genotype:'cycC mutant' vs 'wild type'.t-statistic", "genotype:'cdk8 mutant' vs 'wild type'.p-value", "genotype:'cdk8 mutant' vs 'wild type'.log2foldchange", "genotype:'cdk8 mutant' vs 'wild type'.t-statistic")
        );

    }

    @Test
    public void verifySecondLine() {

        List<String> secondLine = subject.getRowValues(4);

        assertThat(secondLine.get(0), is("FBgn0038465"));
        assertThat(secondLine.get(1), is("Irc"));
        assertThat(secondLine.get(2), is("1633391_at"));
        assertThat(secondLine.get(3), is("4.32183975431433E-4"));
        assertThat(secondLine.get(4), is("0.676040000000002"));
        assertThat(secondLine.get(5), is("8.29055586787586"));
        assertThat(secondLine.get(6), isEmptyString());
        assertThat(secondLine.get(7), isEmptyString());
        assertThat(secondLine.get(8), isEmptyString());
    }

    @Test
    public void verifyLenghtOfDocument() {
        ResponseBody body = subject.getResponseBody();

        String[] lines = body.asString().split("\n");
        assertThat(lines.length, is(178));
    }

}