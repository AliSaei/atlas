package uk.ac.ebi.atlas.model.experiment.differential.microarray;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.arraydesign.ArrayDesign;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.experiment.ExperimentDesign;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.model.experiment.differential.Contrast;
import uk.ac.ebi.atlas.species.Species;
import uk.ac.ebi.atlas.species.SpeciesProperties;
import uk.ac.ebi.atlas.testutils.AssayGroupFactory;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class MicroarrayExperimentTest {
    private static final ImmutableSet<String> ARRAY_DESIGN_ACCESSIONS = ImmutableSet.of("A-AFFY-44", "A-GEOD-20277");
    private static final ImmutableSet<String> ARRAY_DESIGN_NAMES =
            ImmutableSet.of(
                    "Affymetrix GeneChip Human Genome U133 Plus 2.0 [HG-U133_Plus_2]",
                    "TaqMan® Array Human MicroRNA A+B Cards Set v3.0");

    private static final List<ArrayDesign> ARRAY_DESIGNS = ImmutableList.of(
            ArrayDesign.create("A-AFFY-44", "Affymetrix GeneChip Human Genome U133 Plus 2.0 [HG-U133_Plus_2]"),
            ArrayDesign.create("A-GEOD-20277", "TaqMan® Array Human MicroRNA A+B Cards Set v3.0")
            );

    private static final String PUBMEDID = "PUBMEDID";
    private static final String DOI = "100.100/doi";

    private static final AssayGroup G1 = AssayGroupFactory.create("id", "assay 1", "assay 2");
    private static final AssayGroup G2 = AssayGroupFactory.create("test", "assay 1");
    private static final Contrast CONTRAST =
            new Contrast("g1_g2", ARRAY_DESIGN_ACCESSIONS.iterator().next(), G1, G2, "contrast");

    private MicroarrayExperiment subject;

    public static MicroarrayExperiment get(String accession,
                                           ExperimentType type,
                                           List<Contrast> contrasts, List<ArrayDesign> arrayDesigns) {

        ImmutableMap<String, String> genomeBrowser =
                ImmutableMap.of("type", "genome_browser", "name", "Ensembl",
                                "url", "http://www.ensembl.org/Homo_sapiens");

        SpeciesProperties speciesProperties =
                SpeciesProperties.create("Homo_sapiens", "ORGANISM_TYPE", "animals", ImmutableList.of(genomeBrowser));

        return new MicroarrayExperiment(
                type, accession, new Date(),
                contrasts.stream().map(contrast1 -> Pair.of(contrast1, true)).collect(Collectors.toList()),
                "description", new Species("Homo sapiens", speciesProperties),
                mock(ExperimentDesign.class),
                Sets.newHashSet(PUBMEDID),
                Sets.newHashSet(DOI),
                arrayDesigns);
    }

    @Before
    public void setUp() throws Exception {
        subject =
                get(
                        "accession", ExperimentType.MICROARRAY_1COLOUR_MRNA_DIFFERENTIAL,
                        ImmutableList.of(CONTRAST), ARRAY_DESIGNS);
    }

    @Test
    public void testGetArrayDesignAccessions() {
        assertThat(subject.getArrayDesignAccessions(), containsInAnyOrder(ARRAY_DESIGN_ACCESSIONS.toArray()));
    }

    @Test
    public void testGetPubMedIds() {
        assertThat(subject.getPubMedIds(), contains(PUBMEDID));
    }

    @Test
    public void testGetDois() {
        assertThat(subject.getDois(), contains(DOI));
    }

    @Test
    public void microRnaExperimentsHaveNoGenomeBrowsers() {
        MicroarrayExperiment miRnaSubject =
                get(
                        "accession", ExperimentType.MICROARRAY_1COLOUR_MICRORNA_DIFFERENTIAL,
                        ImmutableList.of(CONTRAST), ARRAY_DESIGNS);

        assertThat(subject.getGenomeBrowserNames(), hasSize(1));
        assertThat(miRnaSubject.getGenomeBrowserNames(), hasSize(0));
    }

    @Test
    public void buildExperimentInfo() {
        assertThat(
                subject.buildExperimentInfo().getArrayDesignNames(),
                containsInAnyOrder(ARRAY_DESIGN_NAMES.toArray()));
        assertThat(
                subject.buildExperimentInfo().getArrayDesigns(),
                containsInAnyOrder(ARRAY_DESIGN_ACCESSIONS.toArray()));
    }
}
