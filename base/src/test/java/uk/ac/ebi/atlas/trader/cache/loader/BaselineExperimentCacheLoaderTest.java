package uk.ac.ebi.atlas.trader.cache.loader;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.experimentimport.ExperimentDTO;
import uk.ac.ebi.atlas.model.*;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.baseline.BaselineExperimentConfiguration;
import uk.ac.ebi.atlas.model.baseline.ExperimentalFactors;
import uk.ac.ebi.atlas.model.baseline.ExperimentalFactorsFactory;
import uk.ac.ebi.atlas.resource.DataFileHub;
import uk.ac.ebi.atlas.resource.MockDataFileHub;
import uk.ac.ebi.atlas.trader.ConfigurationTrader;
import uk.ac.ebi.atlas.trader.SpeciesFactory;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BaselineExperimentCacheLoaderTest {

    class Loader extends BaselineExperimentsCacheLoader {

        protected Loader(ExperimentalFactorsFactory experimentalFactorsFactory, ExperimentType experimentType,
                         ConfigurationTrader configurationTrader, SpeciesFactory speciesFactory, DataFileHub dataFileHub) {
            super( experimentalFactorsFactory,experimentType, configurationTrader,speciesFactory,dataFileHub);
        }
    }

    String experimentAccession = "E-MOCK-1";

    ExperimentType experimentType = ExperimentType.RNASEQ_MRNA_BASELINE;

    ExperimentDTO dto = new ExperimentDTO(experimentAccession, experimentType, "homo_sapiens", Collections
            .<String>emptySet(), "mock experiment",new Date(), false, "accessKeyUUID");
    @Mock
    ExperimentalFactorsFactory experimentalFactorsFactory;
    @Mock
    ConfigurationTrader configurationTrader ;
    @Mock
    SpeciesFactory speciesFactory;
    @Mock
    ExperimentConfiguration configuration;
    @Mock
    BaselineExperimentConfiguration baselineConfiguration;
    @Mock
    ExperimentalFactors experimentalFactors;
    @Mock
    AssayGroups assayGroups;
    @Mock
    ExperimentDesign experimentDesign;
    @Spy
    MockDataFileHub dataFileHub = MockDataFileHub.get();


    BaselineExperimentsCacheLoader subject;

    @Before
    public void setUp(){
        Set<String> assayGroupIds =  ImmutableSet.of("assay group id 1");
        dataFileHub.addTemporaryFile(MessageFormat.format("/magetab/{0}/{0}.tsv", experimentAccession),assayGroupIds);

        subject = new Loader(experimentalFactorsFactory,experimentType, configurationTrader, speciesFactory, dataFileHub);
        when(configurationTrader.getExperimentConfiguration(experimentAccession)).thenReturn(configuration);
        when(configurationTrader.getBaselineFactorsConfiguration(experimentAccession)).thenReturn(baselineConfiguration);
        when(configuration.getAssayGroups()).thenReturn(assayGroups);
        when(assayGroups.getAssayGroupIds()).thenReturn(assayGroupIds);
        when(speciesFactory.create(dto,baselineConfiguration)).thenReturn(new Species("homo_sapiens","homo_sapiens","kingdom",
                "ensembl_db"));

        when(experimentalFactorsFactory.createExperimentalFactors(eq(experimentAccession),eq(experimentDesign),
                eq(baselineConfiguration), eq(assayGroups), any(String [] .class), anyBoolean())).thenReturn
                (experimentalFactors);
    }

    private void verifyCollaborators() {
        verify(configurationTrader).getExperimentConfiguration(experimentAccession);
        verify(configurationTrader).getBaselineFactorsConfiguration(experimentAccession);
        verify(configuration).getAssayGroups();
        verify(experimentalFactorsFactory).createExperimentalFactors(eq(experimentAccession),eq(experimentDesign),
                eq(baselineConfiguration), eq(assayGroups), any(String [] .class), anyBoolean());
        verify(speciesFactory).create(dto,baselineConfiguration);
        if(!baselineConfiguration.orderCurated()){
            verify(dataFileHub, atLeastOnce()).getExperimentFiles(experimentAccession);
        }
    }

    private void noMoreInteractionsWithCollaborators() {
        verifyNoMoreInteractions(experimentalFactorsFactory, configurationTrader,
                speciesFactory);
    }

    @Test(expected=IllegalStateException.class)
    public void assayGroupsShouldBeNonEmpty() throws Exception{
        when(configuration.getAssayGroups()).thenReturn(Mockito.mock(AssayGroups.class));
        BaselineExperiment e = subject.load(dto, "description from array express", experimentDesign);
    }

    @Test
    public void useAllCollaborators() throws Exception {
        BaselineExperiment e = subject.load(dto, "description from array express", experimentDesign);
        verifyCollaborators();
        noMoreInteractionsWithCollaborators();
    }

    @Test
    public void noAlternativeViewsForTypicalExperiment() throws Exception {
        BaselineExperiment e = subject.load(dto, "description from array express", experimentDesign);

        assertThat(e.alternativeViews(), hasSize(0));
        verifyCollaborators();
        noMoreInteractionsWithCollaborators();
    }

    @Test
    public void alternativeViews() throws Exception {
        String alternativeViewAccession = "E-MOCK-2";
        when(baselineConfiguration.getAlternativeViews()).thenReturn(ImmutableList.of(alternativeViewAccession));
        BaselineExperimentConfiguration alternativeViewBaselineConfiguration = mock(BaselineExperimentConfiguration
                .class);
        when(configurationTrader.getBaselineFactorsConfiguration(alternativeViewAccession)).thenReturn
                (alternativeViewBaselineConfiguration);
        String s = "default query factor of other experiment";
        when(alternativeViewBaselineConfiguration.getDefaultQueryFactorType()).thenReturn(s);

        BaselineExperiment e = subject.load(dto, "description from array express", experimentDesign);

        assertThat(e.alternativeViews(), hasSize(1));
        assertThat(e.alternativeViews().get(0).getLeft(), is(alternativeViewAccession));
        assertThat(e.alternativeViews().get(0).getRight(), containsString(s));
        verifyCollaborators();
        verify(baselineConfiguration, atLeastOnce()).getAlternativeViews();
        verify(alternativeViewBaselineConfiguration).getDefaultQueryFactorType();
        verify(configurationTrader).getBaselineFactorsConfiguration(alternativeViewAccession);
        noMoreInteractionsWithCollaborators();
    }

    @Test
    public void extractAssayGroupIdsProteomics() {
        String[] tsvFileHeader = "GeneID\tg1.SpectralCount\tg2.SpectralCount\tg3.SpectralCount\tg4.SpectralCount\tg5.SpectralCount\tg6.SpectralCount\tg7.SpectralCount\tg8.SpectralCount\tg9.SpectralCount\tg10.SpectralCount\tg11.SpectralCount\tg12.SpectralCount\tg13.SpectralCount\tg14.SpectralCount\tg15.SpectralCount\tg16.SpectralCount\tg17.SpectralCount\tg18.SpectralCount\tg19.SpectralCount\tg20.SpectralCount\tg21.SpectralCount\tg22.SpectralCount\tg23.SpectralCount\tg24.SpectralCount\tg25.SpectralCount\tg26.SpectralCount\tg27.SpectralCount\tg28.SpectralCount\tg29.SpectralCount\tg30.SpectralCount\tg1.WithInSampleAbundance\tg2.WithInSampleAbundance\tg3.WithInSampleAbundance\tg4.WithInSampleAbundance\tg5.WithInSampleAbundance\tg6.WithInSampleAbundance\tg7.WithInSampleAbundance\tg8.WithInSampleAbundance\tg9.WithInSampleAbundance\tg10.WithInSampleAbundance\tg11.WithInSampleAbundance\tg12.WithInSampleAbundance\tg13.WithInSampleAbundance\tg14.WithInSampleAbundance\tg15.WithInSampleAbundance\tg16.WithInSampleAbundance\tg17.WithInSampleAbundance\tg18.WithInSampleAbundance\tg19.WithInSampleAbundance\tg20.WithInSampleAbundance\tg21.WithInSampleAbundance\tg22.WithInSampleAbundance\tg23.WithInSampleAbundance\tg24.WithInSampleAbundance\tg25.WithInSampleAbundance\tg26.WithInSampleAbundance\tg27.WithInSampleAbundance\tg28.WithInSampleAbundance\tg29.WithInSampleAbundance\tg30.WithInSampleAbundance".split("\t");
        assertThat(subject.readOrderedAssayGroupIds(tsvFileHeader, ExperimentType.PROTEOMICS_BASELINE), is(new String[]{"g1", "g2",
                "g3", "g4", "g5",
                "g6", "g7", "g8", "g9",
                "g10", "g11", "g12", "g13", "g14", "g15", "g16", "g17", "g18", "g19", "g20", "g21", "g22", "g23", "g24", "g25", "g26", "g27", "g28", "g29", "g30"}));
    }
    
}
