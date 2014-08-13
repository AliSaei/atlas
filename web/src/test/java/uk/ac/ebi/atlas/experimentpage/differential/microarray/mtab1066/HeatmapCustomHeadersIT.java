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

package uk.ac.ebi.atlas.experimentpage.differential.microarray.mtab1066;

import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SinglePageSeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.HeatmapTablePage;

import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class HeatmapCustomHeadersIT extends SinglePageSeleniumFixture {

    private static final String EXPERIMENT_ACCESSION = "E-MTAB-1066";

    protected HeatmapTablePage subject;

    public void getStartingPage() {
        subject = new HeatmapTablePage(driver, EXPERIMENT_ACCESSION);
        subject.get();
    }

    @Test
    public void verifyDownloadLinks() {
        subject = new HeatmapTablePage(driver, EXPERIMENT_ACCESSION);
        subject.get();

        assertThat(subject.getDownloadExpressionProfilesLink(), endsWith("/gxa/experiments/E-MTAB-1066.tsv"));
        assertThat(subject.getDownloadAnalyticsLink(), endsWith("/gxa/experiments/E-MTAB-1066/all-analytics.tsv"));
        assertThat(subject.getDownloadNormalizedLink(), endsWith("/gxa/experiments/E-MTAB-1066/normalized.tsv"));
    }

    @Test
    public void shouldHaveAGeneHeader() {

        assertThat(subject.getGeneColumnHeader(), is("Gene"));

    }

    @Test
    public void shouldHaveADesignElement() {

        assertThat(subject.getDesignElementHeader(), is("Design Element"));

    }
}
