
package uk.ac.ebi.atlas.web;

import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.baseline.Factor;
import uk.ac.ebi.atlas.model.baseline.impl.FactorSet;

import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.mockito.Mockito.mock;

public class FilterFactorsConverterTest {

    private static final String SERIALIZED_FACTOR1 = "TYPE1:value1";
    private static final String SERIALIZED_FACTOR2 = "TYPE2:value2";
    private static final String SERIALIZED_FACTORS = SERIALIZED_FACTOR1 + "," + SERIALIZED_FACTOR2;

    private Factor factor1 = new Factor("type1", "value1");
    private Factor factor2 = new Factor("type2", "value2");

    @Before
    public void setUp() throws Exception {
        BaselineExperiment experimentMock = mock(BaselineExperiment.class);
    }

    @Test
    public void prettyPrint() {
        FactorSet factors = new FactorSet();

        factors.add(factor1);
        factors.add(factor2);

        assertThat(FilterFactorsConverter.prettyPrint(factors), is("value2, value1"));
    }

    @Test
    public void testPrettyPrintWithEmptyFactors() {
        FactorSet factors = new FactorSet();

        assertThat(FilterFactorsConverter.prettyPrint(factors), is(""));
    }

    @Test
    public void testSerializeFactors() throws Exception {
        //given
        String serializedFactors = FilterFactorsConverter.serialize(Lists.newArrayList(factor1, factor2));
        //then
        assertThat(serializedFactors, is(SERIALIZED_FACTORS));
    }

    @Test
    public void testDeserialize() throws Exception {
        //given
        Set<Factor> factors = FilterFactorsConverter.deserialize(SERIALIZED_FACTORS);
        //then
        assertThat(factors, containsInAnyOrder(factor1, factor2));
    }
}