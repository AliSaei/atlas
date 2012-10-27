package uk.ac.ebi.atlas.model;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class GeneSpecificityComparatorTest {

    private GeneSpecificityComparator subject;

    @Mock
    private GeneProfile geneWithSpecificity1;

    @Mock
    private GeneProfile geneWithSpecificity16;

    @Mock
    private GeneProfile geneWithSpecificity16AndSmallerExpressionLevel;


    @Before
    public void initGeneExpressions() {
        when(geneWithSpecificity1.getSpecificity()).thenReturn(1);
        when(geneWithSpecificity16.getSpecificity()).thenReturn(16);
        when(geneWithSpecificity16.getMaxExpressionLevel()).thenReturn(10D);
        when(geneWithSpecificity16AndSmallerExpressionLevel.getSpecificity()).thenReturn(16);
        when(geneWithSpecificity16AndSmallerExpressionLevel.getMaxExpressionLevel()).thenReturn(0D);
    }

    @Before
    public void initSubject() {
        subject = new GeneSpecificityComparator(true);
    }

    @Test
    public void lowSpecificityShouldFollowHigherSpecificity() {
        //when
        int comparison = subject.compare(geneWithSpecificity16, geneWithSpecificity1);

        //then
        assertThat(comparison, is(lessThan(0)));

    }

    @Test
    public void highSpecificityShouldPreceedLowSpecificity() {
        //when
        int comparison = subject.compare(geneWithSpecificity1, geneWithSpecificity16);

        //then
        assertThat(comparison, is(greaterThan(0)));

    }


}
