
package uk.ac.ebi.atlas.experimentpage.baseline.geod26284;

import org.junit.BeforeClass;
import uk.ac.ebi.atlas.acceptance.selenium.fixture.SingleDriverSeleniumFixture;
import uk.ac.ebi.atlas.acceptance.selenium.pages.Geod26284HeatmapTablePage;

public class OneCellLineAndCutoff05NonSpecificSearchSIT extends Geod26284HeatmapTableTests {

    @BeforeClass
    public static void getStartingPage() {
        subject = new Geod26284HeatmapTablePage(SingleDriverSeleniumFixture.create(),
                "serializedFilterFactors=CELLULAR_COMPONENT%3Awhole+cell%2CRNA%3Atotal+RNA&queryFactorType=&heatmapMatrixSize=50&displayLevels=true&displayGeneDistribution=false&geneQuery=&queryFactorValues=CD34-positive+mobilized+cell+cell+line&_queryFactorValues=1&_specific=on&cutoff=0.5");
        subject.get();
    }

    @Override
    protected String getQueryFactorLabel() {
        return "Cell line";
    }

    @Override
    protected String[] getTop9Genes() {
        return new String[]{"CD74", "VIM", "MPO", "RPS20", "SERPINB1", "SLC25A5", "CSDE1", "UQCRC1", "PROM1"};
    }

    @Override
    protected String[] getHeatmapHeader() {
        return new String[]{"CD34-positive…", "HFDPC cell line", "HPC-PL cell…", "IMR-90", "hMSC-AT cell…"};
    }

    @Override
    protected String[] getFirstGeneProfile() {
        return new String[]{"386", "0.9", "", "", "11"};
    }

    @Override
    protected String[] getNinthGeneProfile() {
        return new String[]{"47", "", "", "", ""};
    }

    @Override
    protected String getGeneCount() {
        return "374";
    }

}