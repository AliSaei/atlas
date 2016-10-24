package uk.ac.ebi.atlas.bioentity.interpro;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.inject.Inject;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContext.xml", "classpath:oracleContext.xml"})
public class InterProTermTraderIT {

    private static final String IPR000001 = "IPR000001";
    private static final String KRINGLE_DOMAIN = "Kringle (domain)";
    private static final String IPR029787 = "IPR029787";
    private static final String NUCLEOTIDE_CYCLASE_DOMAIN = "Nucleotide cyclase (domain)";

    @Inject
    InterProTrader subject;

    @Test
    public void hasIPR000001() {
        assertThat(subject.getTermName(IPR000001), is(KRINGLE_DOMAIN));
    }

    @Test
    public void hasIPR029787() {
        assertThat(subject.getTermName(IPR029787), is(NUCLEOTIDE_CYCLASE_DOMAIN));
    }

}