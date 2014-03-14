package uk.ac.ebi.atlas.search.diffanalytics;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.search.diffanalytics.DiffAnalyticsTSVWriter;
import uk.ac.ebi.atlas.web.GeneQuerySearchRequestParameters;

import javax.inject.Inject;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
public class DiffAnalyticsTSVWriterHeaderIT {

    @Inject
    private DiffAnalyticsTSVWriter subject;

    @Test
    public void headerTextShouldContainThreeRows(){
        GeneQuerySearchRequestParameters requestParameters = new GeneQuerySearchRequestParameters();

        String[] headerRows = subject.getTsvFileMasthead(requestParameters).split("\n");

        assertThat(headerRows.length, is(3));
    }

    @Test
    public void thirdHeaderLineShouldDescribeTimestamp(){
        GeneQuerySearchRequestParameters requestParameters = new GeneQuerySearchRequestParameters();

        String[] headerRows = subject.getTsvFileMasthead(requestParameters).split("\n");

        assertThat(headerRows[2], startsWith("# Timestamp: "));
        assertThat(headerRows[2].length(), greaterThan("# Timestamp: ".length()));
    }

    @Test
    public void queryDescriptionWithGeneQueryExactMatch(){
        GeneQuerySearchRequestParameters requestParameters = new GeneQuerySearchRequestParameters();
        requestParameters.setGeneQuery("TEST");

        String[] headerRows = subject.getTsvFileMasthead(requestParameters).split("\n");

        assertThat(headerRows[1], is("# Query: Genes matching: 'TEST' exactly, specifically up/down differentially expressed, given the False Discovery Rate cutoff: 0.05"));
    }

    @Test
    public void queryDescriptionWithGeneQueryInexactMatch(){
        GeneQuerySearchRequestParameters requestParameters = new GeneQuerySearchRequestParameters();
        requestParameters.setGeneQuery("TEST");
        requestParameters.setExactMatch(false);

        String[] headerRows = subject.getTsvFileMasthead(requestParameters).split("\n");

        assertThat(headerRows[1], is("# Query: Genes matching: 'TEST', specifically up/down differentially expressed, given the False Discovery Rate cutoff: 0.05"));
    }

    @Test
    public void queryDescriptionWithCondition(){
        GeneQuerySearchRequestParameters requestParameters = new GeneQuerySearchRequestParameters();
        requestParameters.setCondition("LIVER");

        String[] headerRows = subject.getTsvFileMasthead(requestParameters).split("\n");

        assertThat(headerRows[1], is("# Query: specifically up/down differentially expressed in condition matching 'LIVER', given the False Discovery Rate cutoff: 0.05"));
    }


    @Test
    public void queryDescriptionWithGeneQueryAndCondition(){
        GeneQuerySearchRequestParameters requestParameters = new GeneQuerySearchRequestParameters();
        requestParameters.setGeneQuery("TEST");
        requestParameters.setCondition("LIVER");

        String[] headerRows = subject.getTsvFileMasthead(requestParameters).split("\n");

        assertThat(headerRows[1], is("# Query: Genes matching: 'TEST' exactly, specifically up/down differentially expressed in condition matching 'LIVER', given the False Discovery Rate cutoff: 0.05"));
    }

    @Test
    public void firstHeaderLineShouldDescribeAtlasVersion(){
        GeneQuerySearchRequestParameters requestParameters = new GeneQuerySearchRequestParameters();

        String[] headerRows = subject.getTsvFileMasthead(requestParameters).split("\n");

        assertThat(headerRows[0], startsWith("# Expression Atlas version: "));
        assertThat(headerRows[0].length(), greaterThan("# Expression Atlas version: ".length()));
    }
}
