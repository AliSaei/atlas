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

public class GeneSetPageControllerGoTermSIT extends SinglePageSeleniumFixture {

    private static final String IDENTIFIER = "GO:0005515";

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
        assertThat(subject.getBioEntityCardTitle(), is("GO:0005515 protein binding"));
        assertThat(subject.getPropertiesTableSize(), is(1));
        assertThat(subject.getPropertiesTableRow(0), hasItems("Gene Ontology", "protein binding"));
        assertThat(subject.getLinksInTableRow(0).get(0), is("http://amigo.geneontology.org/amigo/term/GO%3A0005515"));
    }

    @Test
    public void baselineResults() {
        subject.clickBaselinePane();
        assertThat(subject.getBaselinePaneHeaderResultsMessage(), is("22 results"));

        List<BaselineBioEntitiesSearchResult> baselineCounts = subject.getBaselineCounts();

        assertThat(baselineCounts, hasSize(23));

        assertThat(baselineCounts.get(1).getExperimentAccession(), is("E-GEOD-26284"));
        assertThat(baselineCounts.get(2).getExperimentAccession(), is("E-GEOD-26284"));
    }

    @Test
    public void hasDifferentialResults() {
        subject.clickDifferentialPane();
        subject.clickDiffResultsDisplayLevelsButton();
        assertThat(subject.getDiffPaneHeaderResultsMessage(), is("5797 results"));

        assertThat(subject.getDiffHeatmapHeaders(), contains("Gene", "Organism", "Comparison", "Log2-fold change"));
        assertThat(subject.getDiffHeatmapRow(1), contains("Uty", "Mus musculus", "sex:'male' vs 'female'", "10.46"));

        //System.out.println("\"" + Joiner.on("\", \"").join(subject.getDiffHeatmapTableGeneColumn()) + "\"");
        assertThat(subject.getDiffHeatmapTableGeneColumn(), contains("Uty", "IL12B", "Hop3", "IL6", "ATHSP101", "PTGS2", "IL6", "BAG6", "FN1", "CCL20", "FN1", "ROF2", "ATHSFA2", "CD9", "IL1A", "IL23A", "FCGR2B", "IL1A", "A37", "CD36", "CD36", "SAMHD1", "IL23A", "PTGS2", "TGFBI", "ATHSFA2", "CCL20", "SLAMF1", "F3", "ROF2", "INHBA", "IL1R2", "TNF", "DREB2A", "KCTD12", "SPINK1", "IL1R2", "Cldn8", "TIMP2", "fs(1)Ya", "ATHSP101", "RNASE1", "CDC48D", "TNF", "EBI3", "RNASE1", "CNKSR3", "CD9", "TIMP2", "CCL4"));
    }


}