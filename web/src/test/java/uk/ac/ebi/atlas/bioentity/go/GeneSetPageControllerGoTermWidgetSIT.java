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

package uk.ac.ebi.atlas.bioentity.go;

import org.junit.Test;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SinglePageSeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.BioEntitiesPage;
import uk.ac.ebi.atlas.acceptance.utils.SeleniumUtil;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;

public class GeneSetPageControllerGoTermWidgetSIT extends SinglePageSeleniumFixture {

    private static final String IDENTIFIER = "GO:0005527";

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
        assertThat(subject.getBioEntityCardTitle(), is("GO:0005527 macrolide binding"));
        assertThat(subject.getPropertiesTableSize(), is(1));
        assertThat(subject.getPropertiesTableRow(0), hasItems("Gene Ontology", "macrolide binding"));
        assertThat(subject.getLinksInTableRow(0).get(0), is("http://amigo.geneontology.org/amigo/term/GO%3A0005527"));
    }

    @Test
    public void baselinePaneShowsWidget() {
        subject.clickBaselinePane();
        assertThat(subject.getBaselinePaneHeaderResultsMessage(), is("Results in tissues"));

        SeleniumUtil.waitForElementByIdUntilVisible(driver, "heatmap-div");

        List<String> geneNames = subject.getGeneNames();

        assertThat(geneNames, contains("FKBP1A"));
    }

    @Test
    public void noDifferentialResults() {
        assertThat(subject.getDiffPaneHeaderResultsMessage(), is("No results"));
    }


}