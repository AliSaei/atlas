
package uk.ac.ebi.atlas.profiles.baseline;

import com.google.common.collect.ImmutableList;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.baseline.ExperimentalFactors;
import uk.ac.ebi.atlas.model.baseline.Factor;
import uk.ac.ebi.atlas.model.baseline.FactorGroup;
import uk.ac.ebi.atlas.trader.cache.RnaSeqBaselineExperimentsCache;

import java.util.concurrent.ExecutionException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ExpressionsRowTsvDeserializerBaselineBuilderTest {

    private static final String MOCK_EXPERIMENT_ACCESSION = "MOCK_EXPERIMENT_ACCESSION";

    @Mock
    private RnaSeqBaselineExperimentsCache experimentsCacheMock;

    @Mock
    private Factor factorMock1;

    @Mock
    private Factor factorMock2;

    @Mock
    private Factor factorMock3;

    @Mock
    private BaselineExperiment experimentMock;

    @Mock
    ExperimentalFactors experimentalFactorsMock;

    @Mock
    FactorGroup factorGroup;

    private ExpressionsRowDeserializerBaselineBuilder subject;



    @Before
    public void initializeSubject() throws ExecutionException {

        when(factorMock1.getType()).thenReturn("ORGANISM_PART");
        when(factorMock1.getValue()).thenReturn("heart");

        when(factorMock2.getType()).thenReturn("ORGANISM_PART");
        when(factorMock2.getValue()).thenReturn("liver");

        when(factorMock3.getType()).thenReturn("ORGANISM_PART");
        when(factorMock3.getValue()).thenReturn("lung");

        when(experimentsCacheMock.getExperiment(MOCK_EXPERIMENT_ACCESSION)).thenReturn(experimentMock);
        when(experimentMock.getExperimentalFactors()).thenReturn(experimentalFactorsMock);
        when(experimentalFactorsMock.getFactorGroupsInOrder()).thenReturn(ImmutableList.of(factorGroup));

        subject = new ExpressionsRowDeserializerBaselineBuilder(experimentsCacheMock);
    }

    @Test(expected = IllegalStateException.class)
    public void createThrowsExceptionGivenThatExperimentAccessionHasNotBeenProvided() {
        //when
        subject.build();
    }

    @Test(expected = IllegalStateException.class)
    public void createThrowsExceptionGivenThatOrderedHeadersHaveNotBeenProvided() {
        //when
        subject.build();
    }

    @Test
    public void createShouldSucceedWhenSpecificationHasBeenSet() {
        //when
        subject.forExperiment(MOCK_EXPERIMENT_ACCESSION);
        //then
        assertThat(subject.build(), is(notNullValue()));
    }

}