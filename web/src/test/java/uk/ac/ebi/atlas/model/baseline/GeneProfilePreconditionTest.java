package uk.ac.ebi.atlas.model.baseline;

import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.baseline.Expression;
import uk.ac.ebi.atlas.model.baseline.Factor;
import uk.ac.ebi.atlas.model.baseline.GeneExpressionPrecondition;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class GeneProfilePreconditionTest {

    private GeneExpressionPrecondition subject;

    private Factor factor1 = new Factor("type1", "value1");
    private Factor factor2 = new Factor("type2", "value2");

    private Factor factor3 = new Factor("type3", "value3");

    @Mock
    private Expression expressionMock;

    @Before
    public void init(){
    }

    @Test
    public void checkLimitingFactorsShouldSucceedWhenExpressionContainsAllLimitingFactors() throws Exception {

        //given
        given(expressionMock.containsAll(Sets.newHashSet(factor1,factor2))).willReturn(true);

        //when
        subject = new GeneExpressionPrecondition();
        subject.setFilterFactors(Sets.newHashSet(factor1, factor2));

        //then
        assertThat(subject.checkFilterFactors(expressionMock), is(true));
    }

    @Test
    public void checkLimitingFactorsShouldSucceedWhenNoLimitingFactorSetIsProvided() throws Exception {

        //given
        subject = new GeneExpressionPrecondition();

        //then
        assertThat(subject.checkFilterFactors(expressionMock), is(true));
    }

    @Test
    public void applyShouldFailExpressionDoesntContainAllLimitingFactors() throws Exception {

        //given
        subject = new GeneExpressionPrecondition();
        subject.setFilterFactors(Sets.newHashSet(factor1, factor2));
        given(expressionMock.containsAll(Sets.newHashSet(factor1,factor2))).willReturn(false);

        //then
        assertThat(subject.apply(expressionMock), is(false));
    }

    @Test
    public void applyShouldSucceedIfLevelIsGreaterThanCutoff() throws Exception {

        //given
        subject = new GeneExpressionPrecondition();
        subject.setFilterFactors(Sets.newHashSet(factor1, factor2));
        subject.setCutoff(1d);

        given(expressionMock.containsAll(Sets.newHashSet(factor1,factor2))).willReturn(true);
        given(expressionMock.isGreaterThan(1d)).willReturn(true);

        //then
        assertThat(subject.apply(expressionMock), is(true));
    }
}
