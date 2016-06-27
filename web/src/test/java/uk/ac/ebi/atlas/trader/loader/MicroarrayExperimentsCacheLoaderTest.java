package uk.ac.ebi.atlas.trader.loader;

import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.experimentimport.ExperimentDAO;
import uk.ac.ebi.atlas.experimentimport.ExperimentDTO;
import uk.ac.ebi.atlas.model.ExperimentDesign;
import uk.ac.ebi.atlas.model.ExperimentType;
import uk.ac.ebi.atlas.model.differential.Contrast;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayExperiment;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayExperimentConfiguration;
import uk.ac.ebi.atlas.trader.ConfigurationTrader;
import uk.ac.ebi.atlas.trader.SpeciesKingdomTrader;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MicroarrayExperimentsCacheLoaderTest {

    private static final String ACCESSION = "accession";
    private static final String ARRAYDESIGNS = "arraydesigns";
    private static final String SPECIES = "species";
    private static final String ACCESS_KEY = "AN_UUID";

    @Mock
    private ConfigurationTrader configurationTraderMock;

    @Mock
    private SpeciesKingdomTrader speciesKingdomTraderMock;

    @Mock
    private MicroarrayExperimentConfiguration experimentConfigurationMock;

    @Mock
    ExperimentDTO experimentDTOMock;

    @Mock
    private Contrast contrastMock;

    @Mock
    private ExperimentDAO experimentDAOMock;

    @Mock
    private ExperimentDesign experimentDesignMock;

    private MicroarrayExperimentsCacheLoader subject;

    @Before
    public void setUp() throws Exception {
        subject = new MicroarrayExperimentsCacheLoader(configurationTraderMock, speciesKingdomTraderMock);

        when(experimentDTOMock.getExperimentAccession()).thenReturn(ACCESSION);
        when(experimentDTOMock.getExperimentType()).thenReturn(ExperimentType.MICROARRAY_1COLOUR_MRNA_DIFFERENTIAL);
        when(experimentDTOMock.getAccessKey()).thenReturn(ACCESS_KEY);
        when(experimentDTOMock.getPubmedIds()).thenReturn(Sets.newHashSet("pubmed1"));
        when(experimentDTOMock.getSpecies()).thenReturn(SPECIES);

        when(configurationTraderMock.getMicroarrayExperimentConfiguration(ACCESSION)).thenReturn(experimentConfigurationMock);
        when(speciesKingdomTraderMock.getKingdom(experimentDTOMock.getSpecies())).thenReturn("kingdom");
        when(experimentConfigurationMock.getContrasts()).thenReturn(Sets.newHashSet(contrastMock));
        when(experimentConfigurationMock.getArrayDesignAccessions()).thenReturn(Sets.newTreeSet(Sets.newHashSet(ARRAYDESIGNS)));

        when(experimentDAOMock.findPublicExperiment(ACCESSION)).thenReturn(experimentDTOMock);
    }

    @Test
    public void testLoad() throws Exception {
        MicroarrayExperiment microarrayExperiment = subject.load(experimentDTOMock, "description",
                false, experimentDesignMock);
        assertThat(microarrayExperiment.getAccession(), is(ACCESSION));
        assertThat(microarrayExperiment.getArrayDesignAccessions(), hasItem(ARRAYDESIGNS));
        assertThat(microarrayExperiment.getSpecies(), is(SPECIES));
        assertThat(microarrayExperiment.getExperimentDesign(), is(experimentDesignMock));
    }
}