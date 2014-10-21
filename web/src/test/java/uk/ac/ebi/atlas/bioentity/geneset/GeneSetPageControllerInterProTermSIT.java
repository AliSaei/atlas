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

package uk.ac.ebi.atlas.bioentity.geneset;

import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SinglePageSeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.BaselineBioEntitiesSearchResult;
import uk.ac.ebi.atlas.acceptance.selenium.pages.BioEntitiesPage;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class GeneSetPageControllerInterProTermSIT extends SinglePageSeleniumFixture {

    private static final String IDENTIFIER = "IPR000003";

    private BioEntitiesPage subject;

    @Override
    protected void getStartingPage() {
        subject = new BioEntitiesPage(driver, "genesets/" + IDENTIFIER + "?openPanelIndex=0");
        subject.get();
    }

    @Test
    public void searchResultsHeader() {
        assertThat(subject.getSearchResultsHeader(), endsWith("results for " + IDENTIFIER));
    }

    @Test
    public void infoCard() {
        assertThat(subject.getBioEntityCardTitle(), is("IPR000003 Retinoid X receptor/HNF4 (family)"));
        assertThat(subject.getPropertiesTableSize(), is(1));
        assertThat(subject.getPropertiesTableRow(0), hasItems("InterPro", "Retinoid X receptor/HNF4 (family)"));
        assertThat(subject.getLinksInTableRow(0).get(0), is("http://www.ebi.ac.uk/interpro/entry/IPR000003"));
    }

    @Test
    public void baselineResults() {
        subject.clickBaselinePane();
        assertThat(subject.getBaselinePaneHeaderResultsMessage(), is("5 results"));

        List<BaselineBioEntitiesSearchResult> baselineCounts = subject.getBaselineCounts();

        assertThat(baselineCounts, hasSize(6)); //including geneset description

        assertThat(baselineCounts.get(1).getExperimentAccession(), is("E-MTAB-1733"));
        assertThat(baselineCounts.get(2).getExperimentAccession(), is("E-MTAB-599"));
    }

    @Test
    public void diffResults() {
        subject.clickDifferentialPane();
        assertThat(subject.getDiffPaneHeaderResultsMessage(), is("1 result"));
        assertThat(subject.getDiffHeatmapTableGeneColumn(), contains("RXRA"));
    }

}