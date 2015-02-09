package uk.ac.ebi.atlas.experimentimport.analytics.index;

import org.junit.Test;
import uk.ac.ebi.atlas.model.ExperimentType;

public class AnalyticsDocumentTest {

    @Test(expected = NullPointerException.class)
    public void cannotBuildBaselineWithoutExpressionLevel() {
        AnalyticsDocument.builder()
                .bioentityIdentifier("foo")
                .species("bar")
                .experimentAccession("E-FOO-1")
                .experimentType(ExperimentType.RNASEQ_MRNA_BASELINE)
                .defaultQueryFactorType("ORGANISM_PART")
                .identifierSearch("foo")
                .conditionsSearch("bar")
                .assayGroupId("g1")
                .build();
    }

    @Test
    public void buildBaseline() {
        AnalyticsDocument.builder()
                .bioentityIdentifier("foo")
                .species("bar")
                .experimentAccession("E-FOO-1")
                .experimentType(ExperimentType.RNASEQ_MRNA_BASELINE)
                .defaultQueryFactorType("ORGANISM_PART")
                .identifierSearch("foo")
                .conditionsSearch("bar")
                .assayGroupId("g1")
                .expressionLevel(1)
                .build();
    }

    @Test(expected = NullPointerException.class)
    public void cannotBuildDifferentialWithoutNumReplicates() {
        AnalyticsDocument.builder()
                .bioentityIdentifier("foo")
                .species("bar")
                .experimentAccession("E-FOO-1")
                .experimentType(ExperimentType.MICROARRAY_1COLOUR_MICRORNA_DIFFERENTIAL)
                .defaultQueryFactorType("ORGANISM_PART")
                .identifierSearch("foo")
                .conditionsSearch("bar")
                .contrastId("g1_g2")
                .contrastType("sex")
                .build();
    }

    @Test(expected = NullPointerException.class)
    public void cannotBuildDifferentialWithoutFoldChange() {
        AnalyticsDocument.builder()
                .bioentityIdentifier("foo")
                .species("bar")
                .experimentAccession("E-FOO-1")
                .experimentType(ExperimentType.MICROARRAY_1COLOUR_MICRORNA_DIFFERENTIAL)
                .defaultQueryFactorType("ORGANISM_PART")
                .identifierSearch("foo")
                .conditionsSearch("bar")
                .contrastId("g1_g2")
                .contrastType("sex")
                .numReplicates(1)
                .build();
    }

    @Test
    public void buildDifferential() {
        AnalyticsDocument.builder()
                .bioentityIdentifier("foo")
                .species("bar")
                .experimentAccession("E-FOO-1")
                .experimentType(ExperimentType.MICROARRAY_1COLOUR_MICRORNA_DIFFERENTIAL)
                .defaultQueryFactorType("ORGANISM_PART")
                .identifierSearch("foo")
                .conditionsSearch("bar")
                .contrastId("g1_g2")
                .contrastType("sex")
                .numReplicates(1)
                .foldChange(0.02)
                .build();
    }

}
