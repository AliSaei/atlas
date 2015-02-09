package uk.ac.ebi.atlas.experimentimport.analytics.index.support;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.AssayGroups;
import uk.ac.ebi.atlas.model.ExperimentDesign;
import uk.ac.ebi.atlas.model.Species;
import uk.ac.ebi.atlas.model.baseline.*;

import java.util.Collections;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SpeciesGrouperTest {

    private static final String FACTOR_TYPE = "type";
    private static final String HOMO_SAPIENS = "Homo sapiens";
    private static final String HOMO_SAPIENS_ENSEMBL = "homo sapiens Ensembl";
    private static final String FACTOR_NAME = "name";
    private static final String EXPERIMENT_ACCESSION = "accession";
    private static final String RUN_ACCESSION1 = "run1";
    private static final String RUN_ACCESSION2 = "run2";
    private static final String MUS_MUSCULUS = "Mus musculus";
    public static final String ORGANISM_PART = "ORGANISM_PART";
    public static final String G1 = "g1";
    public static final String G2 = "g2";
    private static final Factor FACTOR_HOMO_SAPIENS = new Factor(ORGANISM_PART, HOMO_SAPIENS);
    private static final Factor FACTOR_MUS_MUSCULUS = new Factor(ORGANISM_PART, MUS_MUSCULUS);
    private static final String ORGANISM = "ORGANISM";

    private BaselineExperimentBuilder subject;

    @Mock
    private FactorGroup factorGroupMock;

    @Mock
    private ExperimentDesign experimentDesignMock;

    @Mock
    private ExperimentalFactors experimentalFactors;

    private Map<String, String> nameMap = Maps.newHashMap();

    private Map<String, String> speciesMap = Maps.newHashMap();

    @Mock
    private AssayGroups assayGroupsMock;

    @Mock
    private AssayGroups assayGroupsOrganismPartMock;

    @Mock
    private ExperimentalFactors experimentalFactorsMock;

    @Before
    public void setUp() throws Exception {
        subject = new BaselineExperimentBuilder();

        nameMap.put(FACTOR_TYPE, FACTOR_NAME);

        speciesMap.put(HOMO_SAPIENS, HOMO_SAPIENS_ENSEMBL);

        when(assayGroupsMock.iterator()).thenReturn(Sets.newHashSet(new AssayGroup(G1, RUN_ACCESSION1), new AssayGroup(G2, RUN_ACCESSION2)).iterator());
        when(assayGroupsMock.getAssayAccessions()).thenReturn(Sets.newHashSet(RUN_ACCESSION1, RUN_ACCESSION2));
        when(assayGroupsMock.getAssayGroupIds()).thenReturn(Sets.newHashSet(G1, G2));

        when(assayGroupsOrganismPartMock.iterator()).thenReturn(Sets.newHashSet(new AssayGroup(G1, RUN_ACCESSION1), new AssayGroup(G2, RUN_ACCESSION2)).iterator());
        when(assayGroupsOrganismPartMock.getAssayAccessions()).thenReturn(Sets.newHashSet(RUN_ACCESSION1, RUN_ACCESSION2));
        when(assayGroupsOrganismPartMock.getAssayGroupIds()).thenReturn(Sets.newHashSet(G1, G2));

        ImmutableMap<String, Factor> organismPartByAssayGroupId = ImmutableMap.of(G1, FACTOR_HOMO_SAPIENS, G2, FACTOR_MUS_MUSCULUS);
        when(experimentalFactorsMock.getFactorGroupedByAssayGroupId(ORGANISM)).thenReturn(organismPartByAssayGroupId);
    }

    @Test
    public void buildSingleEnsemblSpeciesGroupedByAssayGroupId() throws Exception {

        BaselineExperiment experiment = subject.forOrganisms(Sets.newHashSet(HOMO_SAPIENS))
                .withAccession(EXPERIMENT_ACCESSION)
                .withSpeciesMapping(speciesMap)
                .withExperimentDesign(experimentDesignMock)
                .withExperimentalFactors(experimentalFactors)
                .withAssayGroups(assayGroupsMock)
                .withPubMedIds(Collections.<String>emptySet())
                .create();

        ImmutableMap<String, String> speciesGroupedByAssayGroupId = SpeciesGrouper.buildEnsemblSpeciesGroupedByAssayGroupId(experiment);

        assertThat(speciesGroupedByAssayGroupId.size(), is(2));
        assertThat(speciesGroupedByAssayGroupId, hasEntry(G1, HOMO_SAPIENS_ENSEMBL));
        assertThat(speciesGroupedByAssayGroupId, hasEntry(G2, HOMO_SAPIENS_ENSEMBL));
    }

    @Test
    public void buildMultipleEnsemblSpeciesGroupedByAssayGroupId() throws Exception {

        BaselineExperiment experiment = subject.forOrganisms(Sets.newHashSet(HOMO_SAPIENS, MUS_MUSCULUS))
                .withAccession(EXPERIMENT_ACCESSION)
                .withSpeciesMapping(speciesMap)
                .withExperimentDesign(experimentDesignMock)
                .withExperimentalFactors(experimentalFactors)
                .withAssayGroups(assayGroupsMock)
                .withPubMedIds(Collections.<String>emptySet())
                .withExperimentalFactors(experimentalFactorsMock)
                .create();

        ImmutableMap<String, String> speciesGroupedByAssayGroupId = SpeciesGrouper.buildEnsemblSpeciesGroupedByAssayGroupId(experiment);

        assertThat(speciesGroupedByAssayGroupId.size(), is(2));
        assertThat(speciesGroupedByAssayGroupId, hasEntry(G1, HOMO_SAPIENS_ENSEMBL));
        assertThat(speciesGroupedByAssayGroupId, hasEntry(G2, Species.convertToEnsemblSpecies(MUS_MUSCULUS)));
    }


}