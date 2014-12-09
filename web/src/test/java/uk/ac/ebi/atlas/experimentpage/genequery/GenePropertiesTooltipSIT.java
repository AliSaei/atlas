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

package uk.ac.ebi.atlas.experimentpage.genequery;

import org.junit.Ignore;
import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SinglePageSeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.HeatmapTablePage;

import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.Matchers.hasItems;
import static org.junit.Assert.assertThat;

public class GenePropertiesTooltipSIT extends SinglePageSeleniumFixture {

    private static final String EXPERIMENT_ACCESSION = "E-MTAB-513";

    private static final String HTTP_PARAMETERS = "geneQuery=%22actin-related+protein%22+bile+acid+protein&_queryFactorValues=1&specific=true&_specific=on&cutoff=0.5";

    protected HeatmapTablePage subject;

    public void getStartingPage() {
        subject = new HeatmapTablePage(driver, EXPERIMENT_ACCESSION, HTTP_PARAMETERS);
        subject.get();
    }

    //This will fail with PhantomJS
    @Test
    @Ignore //TODO: we need to fix this test for matching the correct id:geneQuery in selenium
    public void matchingGeneQueryTermsShouldBeHighlighted() throws InterruptedException {
        //given
        String tooltipContent = subject.getGenePropertyTooltipContent(0);

        //then
        assertThat(tooltipContent, containsString("ACTL7A"));
        List<String> highlightedTerms = subject.getGenePropertyTooltipHighlightedTerms(0);
        assertThat(highlightedTerms, hasItems("protein", "Actin-related protein"));

    }

}
