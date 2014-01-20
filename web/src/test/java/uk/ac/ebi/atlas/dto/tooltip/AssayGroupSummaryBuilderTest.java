package uk.ac.ebi.atlas.dto.tooltip;

import org.junit.Test;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.ExperimentDesign;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;

public class AssayGroupSummaryBuilderTest {

    private static final String ASSAY1 = "ASSAY1";
    private static final String FACTOR_HEADER = "factor header";
    private static final String FACTOR_VALUE = "factor value";

    private static final String ASSAY2 = "ASSAY2";
    private static final String FACTOR_HEADER2 = "factor header 2";
    private static final String FACTOR_VALUE2 = "factor value 2";

    private static final AssayGroup ASSAY_GROUP = new AssayGroup(ASSAY1, ASSAY2);
    private static final String SAMPLE_HEADER = "sample header";
    private static final String SAMPLE_VALUE1 = "sample value 2";
    private static final String SAMPLE_VALUE2 = "sample value 2";

    @Test
    public void build(){
        ExperimentDesign experimentDesign = new ExperimentDesign();
        experimentDesign.putFactor(ASSAY1, FACTOR_HEADER, FACTOR_VALUE);
        experimentDesign.putFactor(ASSAY1, FACTOR_HEADER2, FACTOR_VALUE2);

        experimentDesign.putFactor(ASSAY2, FACTOR_HEADER, FACTOR_VALUE);
        experimentDesign.putFactor(ASSAY2, FACTOR_HEADER2, FACTOR_VALUE2);

        experimentDesign.putSample(ASSAY1, SAMPLE_HEADER, SAMPLE_VALUE1);
        experimentDesign.putSample(ASSAY2, SAMPLE_HEADER, SAMPLE_VALUE2);

        AssayGroupSummaryBuilder subject = new AssayGroupSummaryBuilder().
                forAssayGroup(ASSAY_GROUP).
                withExperimentDesign(experimentDesign);

        AssayGroupSummary assayGroupSummary = subject.build();

        assertThat(assayGroupSummary, contains(
                new AssayProperty(FACTOR_HEADER, FACTOR_VALUE, ContrastPropertyType.FACTOR),
                new AssayProperty(FACTOR_HEADER2, FACTOR_VALUE2, ContrastPropertyType.FACTOR),
                new AssayProperty(SAMPLE_HEADER, SAMPLE_VALUE2, ContrastPropertyType.SAMPLE)));
    }

}