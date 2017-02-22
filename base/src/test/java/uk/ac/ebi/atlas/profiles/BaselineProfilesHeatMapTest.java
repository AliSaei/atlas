package uk.ac.ebi.atlas.profiles;

import com.google.common.collect.ImmutableSetMultimap;
import org.apache.commons.math.util.MathUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.experiment.baseline.*;
import uk.ac.ebi.atlas.profiles.baseline.BaselineProfilesEMTab513React71;
import uk.ac.ebi.atlas.experimentpage.baseline.BaselineProfilesHeatMap;
import uk.ac.ebi.atlas.profiles.baseline.BaselineProfileInputStreamFactory;
import uk.ac.ebi.atlas.profiles.baseline.BaselineProfileStreamOptions;
import uk.ac.ebi.atlas.profiles.baseline.BaselineProfilesListBuilder;
import uk.ac.ebi.atlas.profiles.baseline.RankBaselineProfilesFactory;
import uk.ac.ebi.atlas.solr.query.GeneQueryResponse;

import java.util.Collections;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BaselineProfilesHeatMapTest {

    private static final double POLR2B_LEUKOCYTE = 47D;
    private static final double SNRPA_LEUKOCYTE = 19D;
    private static final double CCNT2_LEUKOCYTE = 9D;
    private static final double ZKSCAN5_LEUKOCYTE = 5D;
    private static final double NUMBER_OF_GENES_IN_GENE_SET = 7;
    private static final int NUMBER_OF_FRACTIONAL_DIGITS = 0;
    private static final Factor FACTOR_LEUKOCYTE = new Factor("ORGANISM_PART", "leukocyte");

    private BaselineProfilesListBuilder geneProfilesListBuilder = new BaselineProfilesListBuilder();
    private RankBaselineProfilesFactory rankProfilesFactory = new RankBaselineProfilesFactory( geneProfilesListBuilder);
    private BaselineProfilesHeatMap subject;
    private BaselineProfilesEMTab513React71 eMTab513react71InputStream = new BaselineProfilesEMTab513React71(0.5);

    private ImmutableSetMultimap<String, String> react71GeneIds = ImmutableSetMultimap.<String, String>builder().putAll("react_71", "ENSG00000196652", "ENSG00000082258", "ENSG00000047315", "ENSG00000077312", "ENSG00000198939", "ENSG00000178665", "ENSG00000161547").build();

    @Mock
    GeneQueryResponse geneQueryResponse;

    @Mock
    BaselineProfileStreamOptions options;

    @Mock
    BaselineProfileInputStreamFactory inputStreamFactory;

    @Before
    public void before() throws Exception {
        when(inputStreamFactory.create(Matchers.any(BaselineExperiment.class), Matchers.any(BaselineProfileStreamOptions
                .class)))
                .thenReturn( eMTab513react71InputStream);


        when(options.getHeatmapMatrixSize()).thenReturn(50);

        when(options.getAllQueryFactors()).thenReturn(eMTab513react71InputStream.getOrderedFactorGroups().extractFactors());
        when(geneQueryResponse.getQueryTermsToIds()).thenReturn(react71GeneIds);

        subject = new BaselineProfilesHeatMap(rankProfilesFactory, inputStreamFactory);
    }

    // http://localhost:8080/gxa/experiments/E-MTAB-513?displayLevels=true&geneQuery=react_71&specific=true
    @Test
    public void eMTab513react71_Specific() {
        isSpecific();
        BaselineProfilesList profiles = subject.fetch(BaselineExperimentTest.mockExperiment(),options,
                geneQueryResponse, false);

        assertThat(profiles.extractGeneNames(), contains("SRSF2", "ZNF713", "ZFP2", "POLR2B", "SNRPA", "CCNT2", "ZKSCAN5"));

        OldBaselineProfile srsf2 = profiles.get(0);
        OldBaselineProfile znf713 = profiles.get(1);
        OldBaselineProfile zfp2 = profiles.get(2);
        OldBaselineProfile polr2b = profiles.get(3);
        OldBaselineProfile snrpa = profiles.get(4);
        OldBaselineProfile ccnt2 = profiles.get(5);
        OldBaselineProfile zkscan5 = profiles.get(6);
        assertThat(srsf2.getName(), is("SRSF2"));
        assertThat(znf713.getName(), is("ZNF713"));
        assertThat(zfp2.getName(), is("ZFP2"));
        assertThat(polr2b.getName(), is("POLR2B"));
        assertThat(snrpa.getName(), is("SNRPA"));
        assertThat(ccnt2.getName(), is("CCNT2"));
        assertThat(zkscan5.getName(), is("ZKSCAN5"));

        assertThat(srsf2.getExpressionLevel(FACTOR_LEUKOCYTE), is(nullValue()));
        assertThat(znf713.getExpressionLevel(FACTOR_LEUKOCYTE), is(nullValue()));
        assertThat(zfp2.getExpressionLevel(FACTOR_LEUKOCYTE), is(nullValue()));
        assertThat(polr2b.getExpressionLevel(FACTOR_LEUKOCYTE), is(POLR2B_LEUKOCYTE));
        assertThat(snrpa.getExpressionLevel(FACTOR_LEUKOCYTE), is(SNRPA_LEUKOCYTE));
        assertThat(ccnt2.getExpressionLevel(FACTOR_LEUKOCYTE), is(CCNT2_LEUKOCYTE));
        assertThat(zkscan5.getExpressionLevel(FACTOR_LEUKOCYTE), is(ZKSCAN5_LEUKOCYTE));

        checkAllPolr2bExpressionLevels(polr2b);
    }

    private void isSpecific() {
        when(options.isSpecific()).thenReturn(true);
    }

    //http://localhost:8080/gxa/experiments/E-MTAB-513?displayLevels=true&geneQuery=react_71&_specific=on
    @Test
    public void eMTab513react71_NotSpecific() {
        BaselineProfilesList profiles = subject.fetch(BaselineExperimentTest.mockExperiment(),options, geneQueryResponse, false);

        assertThat(profiles.extractGeneNames(), contains("POLR2B", "SNRPA", "CCNT2", "ZKSCAN5", "ZFP2", "ZNF713", "SRSF2"));

        OldBaselineProfile srsf2 = profiles.get(6);
        OldBaselineProfile znf713 = profiles.get(5);
        OldBaselineProfile zfp2 = profiles.get(4);
        OldBaselineProfile polr2b = profiles.get(0);
        OldBaselineProfile snrpa = profiles.get(1);
        OldBaselineProfile ccnt2 = profiles.get(2);
        OldBaselineProfile zkscan5 = profiles.get(3);
        assertThat(srsf2.getName(), is("SRSF2"));
        assertThat(znf713.getName(), is("ZNF713"));
        assertThat(zfp2.getName(), is("ZFP2"));
        assertThat(polr2b.getName(), is("POLR2B"));
        assertThat(snrpa.getName(), is("SNRPA"));
        assertThat(ccnt2.getName(), is("CCNT2"));
        assertThat(zkscan5.getName(), is("ZKSCAN5"));

        assertThat(srsf2.getExpressionLevel(FACTOR_LEUKOCYTE), is(nullValue()));
        assertThat(znf713.getExpressionLevel(FACTOR_LEUKOCYTE), is(nullValue()));
        assertThat(zfp2.getExpressionLevel(FACTOR_LEUKOCYTE), is(nullValue()));
        assertThat(polr2b.getExpressionLevel(FACTOR_LEUKOCYTE), is(POLR2B_LEUKOCYTE));
        assertThat(snrpa.getExpressionLevel(FACTOR_LEUKOCYTE), is(SNRPA_LEUKOCYTE));
        assertThat(ccnt2.getExpressionLevel(FACTOR_LEUKOCYTE), is(CCNT2_LEUKOCYTE));
        assertThat(zkscan5.getExpressionLevel(FACTOR_LEUKOCYTE), is(ZKSCAN5_LEUKOCYTE));

        checkAllPolr2bExpressionLevels(polr2b);
    }

    // http://localhost:8080/gxa/experiments/E-MTAB-513?displayLevels=true&geneQuery=react_71&specific=true&geneSetMatch=true
    @Test
    public void eMTab513react71_Specific_GeneSet() {
        isSpecific();

        BaselineProfilesList profiles = subject.fetch(BaselineExperimentTest.mockExperiment(),options, geneQueryResponse, true);

        assertThat(profiles.extractGeneNames(), contains("react_71"));

        OldBaselineProfile react71 = profiles.get(0);

        checkAllReact71ExpressionLevels(react71);

    }

    //http://localhost:8080/gxa/experiments/E-MTAB-513?displayLevels=true&geneQuery=react_71&_specific=on&geneSetMatch=true
    @Test
    public void eMTab513react71_NotSpecific_GeneSet() {

        BaselineProfilesList profiles = subject.fetch(BaselineExperimentTest.mockExperiment(),options, geneQueryResponse, true);

        assertThat(profiles.extractGeneNames(), contains("react_71"));

        OldBaselineProfile react71 = profiles.get(0);

        checkAllReact71ExpressionLevels(react71);
    }

    // http://localhost:8080/gxa/experiments/E-MTAB-513?displayLevels=true&geneQuery=react_71&specific=true&queryFactorValues=leukocyte
    @Test
    public void eMTab513react71_Specific_QueryFactorLeukocyte()  {
        isSpecific();
        queryFactors(Collections.singleton(FACTOR_LEUKOCYTE));

        BaselineProfilesList profiles = subject.fetch(BaselineExperimentTest.mockExperiment(),options, geneQueryResponse, false);

        assertThat(profiles.extractGeneNames(), contains("POLR2B"));

        OldBaselineProfile polr2b = profiles.get(0);

        checkAllPolr2bExpressionLevels(polr2b);
    }

    private void queryFactors(Set<Factor> factors) {
        when(options.getSelectedQueryFactors()).thenReturn(factors);
    }

    // http://localhost:8080/gxa/experiments/E-MTAB-513?displayLevels=true&geneQuery=react_71&_specific=on&queryFactorValues=leukocyte
    @Test
    public void eMTab513react71_NotSpecific_QueryFactorLeukocyte()  {
        queryFactors(Collections.singleton(FACTOR_LEUKOCYTE));

        BaselineProfilesList profiles = subject.fetch(BaselineExperimentTest.mockExperiment(),options, geneQueryResponse, false);

        assertThat(profiles.extractGeneNames(), contains("POLR2B", "SNRPA", "CCNT2", "ZKSCAN5"));

        OldBaselineProfile polr2b = profiles.get(0);
        OldBaselineProfile snrpa = profiles.get(1);
        OldBaselineProfile ccnt2 = profiles.get(2);
        OldBaselineProfile zkscan5 = profiles.get(3);
        assertThat(polr2b.getName(), is("POLR2B"));
        assertThat(snrpa.getName(), is("SNRPA"));
        assertThat(ccnt2.getName(), is("CCNT2"));
        assertThat(zkscan5.getName(), is("ZKSCAN5"));

        assertThat(polr2b.getExpressionLevel(FACTOR_LEUKOCYTE), is(POLR2B_LEUKOCYTE));
        assertThat(snrpa.getExpressionLevel(FACTOR_LEUKOCYTE), is(SNRPA_LEUKOCYTE));
        assertThat(ccnt2.getExpressionLevel(FACTOR_LEUKOCYTE), is(CCNT2_LEUKOCYTE));
        assertThat(zkscan5.getExpressionLevel(FACTOR_LEUKOCYTE), is(ZKSCAN5_LEUKOCYTE));

        checkAllPolr2bExpressionLevels(polr2b);
    }

    // http://localhost:8080/gxa/experiments/E-MTAB-513?displayLevels=true&geneQuery=react_71&specific=true&queryFactorValues=leukocyte&geneSetMatch=true
    @Test
    public void eMTab513react71_Specific_GeneSet_QueryFactorLeukocyteGeneSet_NoResults()  {
        isSpecific();
        queryFactors(Collections.singleton(FACTOR_LEUKOCYTE));

        BaselineProfilesList profiles = subject.fetch(BaselineExperimentTest.mockExperiment(),options, geneQueryResponse, true);

        // no results because Leukoctye is not specific to react_71
        assertThat(profiles, is(empty()));
    }

    // http://localhost:8080/gxa/experiments/E-MTAB-513?displayLevels=true&geneQuery=react_71&specific=true&queryFactorValues=prostate&geneSetMatch=true
    @Test
    public void eMTab513react71_Specific_GeneSet_QueryFactorProstateGeneSet_CheckExpressionLevelsForReact71()  {
        isSpecific();
        queryFactors(Collections.singleton(factor("prostate")));

        BaselineProfilesList profiles = subject.fetch(BaselineExperimentTest.mockExperiment(),options, geneQueryResponse, true);

        assertThat(profiles.extractGeneNames(), contains("react_71"));

        OldBaselineProfile react71 = profiles.get(0);

        checkAllReact71ExpressionLevels(react71);
    }

    // http://localhost:8080/gxa/experiments/E-MTAB-513?displayLevels=true&geneQuery=react_71&_specific=on&queryFactorValues=leukocyte&geneSetMatch=true
    @Test
    public void eMTab513react71_NotSpecific_GeneSet_QueryFactorLeukocyteGeneSet_NoResults()  {
        queryFactors(Collections.singleton(FACTOR_LEUKOCYTE));

        BaselineProfilesList profiles = subject.fetch(BaselineExperimentTest.mockExperiment(),options, geneQueryResponse, true);

        assertThat(profiles.extractGeneNames(), contains("react_71"));

        OldBaselineProfile react71 = profiles.get(0);

        checkAllReact71ExpressionLevels(react71);
    }

    private void checkAllPolr2bExpressionLevels(OldBaselineProfile polr2b) {
        assertThat(polr2b.getExpressionLevel(factor("adipose")), is(16D));
        assertThat(polr2b.getExpressionLevel(factor("adrenal gland")), is(30D));
        assertThat(polr2b.getExpressionLevel(factor("brain")), is(24D));
        assertThat(polr2b.getExpressionLevel(factor("breast")), is(18D));
        assertThat(polr2b.getExpressionLevel(factor("colon")), is(20D));
        assertThat(polr2b.getExpressionLevel(factor("heart")), is(26D));
        assertThat(polr2b.getExpressionLevel(factor("kidney")), is(nullValue()));
        assertThat(polr2b.getExpressionLevel(factor("leukocyte")), is(47D));
        assertThat(polr2b.getExpressionLevel(factor("liver")), is(12D));
        assertThat(polr2b.getExpressionLevel(factor("lung")), is(nullValue()));
        assertThat(polr2b.getExpressionLevel(factor("lymph node")), is(21D));
        assertThat(polr2b.getExpressionLevel(factor("ovary")), is(25D));
        assertThat(polr2b.getExpressionLevel(factor("prostate")), is(24D));
        assertThat(polr2b.getExpressionLevel(factor("skeletal muscle")), is(28D));
        assertThat(polr2b.getExpressionLevel(factor("testis")), is(33D));
        assertThat(polr2b.getExpressionLevel(factor("thyroid")), is(38D));
    }


    private void checkAllReact71ExpressionLevels(OldBaselineProfile react71) {
        assertThat(react71.getExpressionLevel(factor("adipose")), is(7D));
        assertThat(react71.getExpressionLevel(factor("adrenal gland")), is(10D));
        assertThat(react71.getExpressionLevel(factor("brain")), is(6D));
        assertThat(react71.getExpressionLevel(factor("breast")), is(6D));
        assertThat(react71.getExpressionLevel(factor("colon")), is(8D));
        assertThat(react71.getExpressionLevel(factor("heart")), is(6D));
        assertThat(react71.getExpressionLevel(factor("kidney")), is(4D));
        assertThat(react71.getExpressionLevel(FACTOR_LEUKOCYTE), is(MathUtils.round((POLR2B_LEUKOCYTE + SNRPA_LEUKOCYTE + CCNT2_LEUKOCYTE + ZKSCAN5_LEUKOCYTE) / NUMBER_OF_GENES_IN_GENE_SET, NUMBER_OF_FRACTIONAL_DIGITS)));
        assertThat(react71.getExpressionLevel(factor("liver")), is(4D));
        assertThat(react71.getExpressionLevel(factor("lung")), is(6D));
        assertThat(react71.getExpressionLevel(factor("lymph node")), is(9D));
        assertThat(react71.getExpressionLevel(factor("ovary")), is(9D));
        assertThat(react71.getExpressionLevel(factor("prostate")), is(17D));
        assertThat(react71.getExpressionLevel(factor("skeletal muscle")), is(11D));
        assertThat(react71.getExpressionLevel(factor("testis")), is(11D));
        assertThat(react71.getExpressionLevel(factor("thyroid")), is(8D));
    }

    private Factor factor(String factorValue) {
        return new Factor("ORGANISM_PART", factorValue);
    }

}
