package uk.ac.ebi.atlas.experimentpage.baseline;

import au.com.bytecode.opencsv.CSVWriter;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableMap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.experimentpage.context.BaselineRequestContext;
import uk.ac.ebi.atlas.experimentpage.context.BaselineRequestContextBuilder;
import uk.ac.ebi.atlas.experimentpage.context.GenesNotFoundException;
import uk.ac.ebi.atlas.experimentpage.context.LoadGeneIdsIntoRequestContext;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.baseline.Factor;
import uk.ac.ebi.atlas.profiles.baseline.BaselineProfileInputStreamFactory;
import uk.ac.ebi.atlas.profiles.baseline.BaselineProfileStreamPipelineBuilder;
import uk.ac.ebi.atlas.profiles.writer.BaselineProfilesTSVWriter;
import uk.ac.ebi.atlas.profiles.writer.CsvWriterFactory;
import uk.ac.ebi.atlas.trader.cache.BaselineExperimentsCache;
import uk.ac.ebi.atlas.web.BaselineRequestPreferences;

import javax.inject.Inject;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
public class BaselineProfilesWriterIT {

    public static final String E_MTAB_513 = "E-MTAB-513";
    private static final Factor FACTOR_LEUKOCYTE = new Factor("ORGANISM_PART", "leukocyte");
    public static final int GENE_NAME_INDEX = 1;
    private static final int GENE_SET_NAME_INDEX = 0;

    private BaselineProfilesWriter subject;

    @Inject
    private BaselineExperimentsCache baselineExperimentsCache;

    @Inject
    BaselineRequestContextBuilder baselineRequestContextBuilder;

    @Mock
    PrintWriter printWriterMock;

    @Mock
    CSVWriter csvWriterMock;

    @Mock
    private CsvWriterFactory csvWriterFactoryMock;

    private BaselineRequestPreferences requestPreferences = new BaselineRequestPreferences();

    @Inject
    private BaselineProfileInputStreamFactory baselineProfileInputStreamFactory;
    @Inject
    private BaselineProfileStreamPipelineBuilder baselineProfileStreamPipelineBuilder;

    @Inject
    private LoadGeneIdsIntoRequestContext loadGeneIdsIntoRequestContext;

    @Value("classpath:/file-templates/download-headers-baseline.txt")
    public Resource tsvFileMastheadTemplateResource;

    private BaselineRequestContext populateRequestContext(String experimentAccession) {
        MockitoAnnotations.initMocks(this);
        BaselineExperiment baselineExperiment = baselineExperimentsCache.getExperiment(experimentAccession);

        requestPreferences.setQueryFactorType("ORGANISM_PART");
        BaselineRequestContext baselineRequestContext = baselineRequestContextBuilder.forExperiment(baselineExperiment)
                .withPreferences(requestPreferences)
                .build();

        BaselineProfilesTSVWriter baselineProfilesTSVWriter = new BaselineProfilesTSVWriter(csvWriterFactoryMock);
        baselineProfilesTSVWriter.setRequestContext(baselineRequestContext);
        baselineProfilesTSVWriter.setTsvFileMastheadTemplateResource(tsvFileMastheadTemplateResource);

        when(csvWriterFactoryMock.createTsvWriter((Writer) anyObject())).thenReturn(csvWriterMock);

        subject = new BaselineProfilesWriter(baselineProfileStreamPipelineBuilder, baselineProfilesTSVWriter, baselineProfileInputStreamFactory, loadGeneIdsIntoRequestContext);

        return baselineRequestContext;
    }

    // http://localhost:8080/gxa/experiments/E-MTAB-513?displayLevels=true&specific=true
    @Test
    public void eMTab513_Specific() throws GenesNotFoundException {
        BaselineRequestContext requestContext = populateRequestContext(E_MTAB_513);
        long genesCount = subject.write(printWriterMock, requestContext);

        int expectedCount = 148;

        ArgumentCaptor<String[]> lineCaptor = ArgumentCaptor.forClass(String[].class);
        verify(csvWriterMock, times(expectedCount + 1)).writeNext(lineCaptor.capture());

        List<String[]> lines = lineCaptor.getAllValues();

        ImmutableMap<String, String[]> geneNameToLine = indexByGeneName(lines);
        Set<String> geneNames = geneNameToLine.keySet();

        assertThat(genesCount, is((long) expectedCount));
        assertThat(geneNames, hasSize(expectedCount));


        String[] mettl25 = geneNameToLine.get("METTL25");
        String[] srsf2 = geneNameToLine.get("SRSF2");
        String[] nebl = geneNameToLine.get("NEBL");

        //System.out.println(Joiner.on("\", \"").join(geneNames));

        assertThat(geneNames, containsInAnyOrder("METTL25", "THOC3", "AHI1", "SNX27", "MRPL13", "RTDR1", "TERF2", "PRMT8", "TRPM2", "IL13RA1", "GFI1", "PRSS16", "CALU", "SCN2A", "ARHGAP1", "RGS7BP", "BMI1", "TMSB10", "SLC10A1", "RP11-192H23.4", "B3GNT1", "THOC6", "RGP1", "PTBP3", "ZKSCAN5", "VTI1B", "CBX1", "DCAF4L2", "FAM47B", "C6orf203", "COL4A3BP", "LIPF", "FZD7", "EFNB2", "DVL1", "BET1", "CPAMD8", "C1QL3", "MFSD7", "SRGAP3", "PPT1", "TGDS", "OCEL1", "DEPTOR", "RAE1", "AC061975.10", "VRK3", "CRY2", "GSTZ1", "NEBL", "RNF41", "TUBA1C", "GAS2L2", "CCNT2", "MSH6", "C6orf1", "PLCG2", "CC2D1B", "POLR2B", "GPAT2", "PVRL4", "GPD2", "FAM102B", "CTNNA1", "CRLS1", "ABCG8", "GOLPH3L", "VPS4A", "NOTUM", "RNF25", "FBXO38", "ASNS", "MT-ATP6", "CTNNBIP1", "APOBR", "SBK2", "SNRPA", "RRP8", "ZDHHC18", "RTN4", "IRF2BPL", "AIDA", "CCDC66", "SEMA3G", "ARHGAP8", "C17orf85", "C1QTNF2", "RHBDF1", "NEDD8", "UBQLNL", "ZNF350", "RAB27B", "IL12RB2", "POLE3", "MYOD1", "TEX33", "ZSCAN5B", "RAB13", "WDR76", "ABCD4", "GLB1L2", "COL15A1", "S1PR1", "ASPA", "FAM89A", "SLAMF6", "ZCRB1", "RNF208", "ZFP2", "VAPB", "ACTL7A", "MAPRE2", "PMM2", "ZNF236", "ARPC5", "CCNE2", "CRISP3", "RANBP17", "USP26", "DSCC1", "ERLIN1", "BOD1", "OSBP2", "BICD1", "RPRD2", "SLK", "ZNF713", "AXIN2", "C17orf64", "FAM172A", "KDM4A", "TMEM56", "WWC2", "DAPL1", "ATP1B3", "TRIM65", "ESPL1", "GFPT1", "MKS1", "GGPS1", "PLEKHB2", "EMCN", "EPB41L4A", "INPP5D", "NEK3", "WFDC10A", "INPP4A", "SRSF2"));
        assertThat(mettl25, is(new String[]{"ENSG00000127720", "METTL25", null, "2", "0.7", "2", "0.9", "2", "5", "4", "4", "0.9", "2", "3", "3", "1", "3", "3"}));
        assertThat(srsf2, is(new String[] {"ENSG00000161547", "SRSF2", null, null, null, null, null, null, null, null, null, null, null, null, "0.6", null, null, null}));
        assertThat(nebl, is(new String[] {"ENSG00000078114", "NEBL", "1", "0.7", "59", "4", "3", "210", "11", null, null, "17", "0.6", "0.9", "3", null, "5", "38"}));
    }

    //http://localhost:8080/gxa/experiments/E-MTAB-513?displayLevels=true&_specific=on
    @Test
    public void eMTab513_NotSpecific() throws GenesNotFoundException {
        setNotSpecific();
        eMTab513_Specific();
    }

    // http://localhost:8080/gxa/experiments/E-MTAB-513?displayLevels=true&specific=true&queryFactorValues=leukocyte
    @Test
    public void eMTab513_Specific_QueryFactorLeukocyte() throws GenesNotFoundException {
        setQueryFactor(FACTOR_LEUKOCYTE);

        BaselineRequestContext requestContext = populateRequestContext(E_MTAB_513);
        long genesCount = subject.write(printWriterMock, requestContext);
        int expectedCount = 19;

        ArgumentCaptor<String[]> lineCaptor = ArgumentCaptor.forClass(String[].class);
        verify(csvWriterMock, times(expectedCount + 1)).writeNext(lineCaptor.capture());

        List<String[]> lines = lineCaptor.getAllValues();

        ImmutableMap<String, String[]> geneNameToLine = indexByGeneName(lines);
        Set<String> geneNames = geneNameToLine.keySet();

        assertThat(genesCount, is((long)expectedCount));
        assertThat(geneNames, hasSize(expectedCount));

        String[] apobr = geneNameToLine.get("APOBR");
        String[] slamf6 = geneNameToLine.get("SLAMF6");

        //System.out.println(Joiner.on("\", \"").join(geneNames));

        assertThat(geneNames, containsInAnyOrder("APOBR", "INPP5D", "PTBP3", "ZDHHC18", "PPT1", "ARPC5", "GFI1", "INPP4A", "SNX27", "KDM4A", "POLR2B", "PLCG2", "SLAMF6", "COL4A3BP", "RAB27B", "FBXO38", "C6orf1", "TMSB10", "POLE3"));
        assertThat(apobr, is(new String[]{"ENSG00000184730", "APOBR", "2", "4", "1", "1", "2", null, "0.7", "42", "0.6", "2", "1", "0.9", "1", null, "1", null}));
        assertThat(slamf6, is(new String[] {"ENSG00000162739", "SLAMF6", null, "11", null, null, "0.6", null, null, "44", "0.7", "2", "36", null, null, null, null, null}));

    }

    // http://localhost:8080/gxa/experiments/E-MTAB-513?displayLevels=true&_specific=on&queryFactorValues=leukocyte
    @Test
    public void eMTab513_NotSpecific_QueryFactorLeukocyte() throws GenesNotFoundException {
        setNotSpecific();
        setQueryFactor(FACTOR_LEUKOCYTE);

        BaselineRequestContext requestContext = populateRequestContext(E_MTAB_513);
        long genesCount = subject.write(printWriterMock, requestContext);
        int expectedCount = 103;

        ArgumentCaptor<String[]> lineCaptor = ArgumentCaptor.forClass(String[].class);
        verify(csvWriterMock, times(expectedCount + 1)).writeNext(lineCaptor.capture());

        List<String[]> lines = lineCaptor.getAllValues();

        ImmutableMap<String, String[]> geneNameToLine = indexByGeneName(lines);
        Set<String> geneNames = geneNameToLine.keySet();

        assertThat(genesCount, is((long)expectedCount));
        assertThat(geneNames, hasSize(expectedCount));

        String[] apobr = geneNameToLine.get("APOBR");
        String[] mt_atp6 = geneNameToLine.get("MT-ATP6");

        assertThat(geneNames, containsInAnyOrder("METTL25", "THOC3", "AHI1", "SNX27", "MRPL13", "TERF2", "TRPM2", "IL13RA1", "GFI1", "CALU", "ARHGAP1", "BMI1", "TMSB10", "B3GNT1", "THOC6", "PTBP3", "ZKSCAN5", "VTI1B", "CBX1", "C6orf203", "COL4A3BP", "DVL1", "BET1", "CPAMD8", "C1QL3", "MFSD7", "PPT1", "TGDS", "OCEL1", "DEPTOR", "RAE1", "AC061975.10", "VRK3", "CRY2", "GSTZ1", "RNF41", "TUBA1C", "CCNT2", "MSH6", "C6orf1", "PLCG2", "CC2D1B", "POLR2B", "GPAT2", "GPD2", "FAM102B", "CTNNA1", "CRLS1", "GOLPH3L", "VPS4A", "RNF25", "FBXO38", "ASNS", "MT-ATP6", "CTNNBIP1", "APOBR", "SNRPA", "RRP8", "ZDHHC18", "RTN4", "IRF2BPL", "AIDA", "C17orf85", "NEDD8", "ZNF350", "RAB27B", "IL12RB2", "POLE3", "RAB13", "WDR76", "ABCD4", "GLB1L2", "S1PR1", "FAM89A", "SLAMF6", "ZCRB1", "RNF208", "VAPB", "MAPRE2", "ZNF236", "ARPC5", "CRISP3", "ERLIN1", "BOD1", "OSBP2", "BICD1", "RPRD2", "SLK", "AXIN2", "FAM172A", "KDM4A", "WWC2", "ATP1B3", "TRIM65", "GFPT1", "MKS1", "GGPS1", "PLEKHB2", "EMCN", "EPB41L4A", "INPP5D", "NEK3", "INPP4A"));
        assertThat(apobr, is(new String[]{"ENSG00000184730", "APOBR", "2", "4", "1", "1", "2", null, "0.7", "42", "0.6", "2", "1", "0.9", "1", null, "1", null}));
        assertThat(mt_atp6, is(new String[] {"ENSG00000198899", "MT-ATP6", null, "10690", null, null, null, null, null, "4149", null, "6899", "7810", "6724", null, null, null, "8664"}));

    }

    // http://localhost:8080/gxa/experiments/E-MTAB-513?displayLevels=true&_specific=on&geneQuery=react_14797+react_19184+react_604+react_111102+react_111217+react_6900+react_71+react_116125+react_75774+react_6802+react_17015+react_22258+react_15518+react_115566+react_12627&geneSetMatch=true
    @Test
    public void eMTab513_NotSpecific_MultipleGeneSets() throws GenesNotFoundException {
        setNotSpecific();
        eMTab513_Specific_MultipleGeneSets();
    }

    // http://localhost:8080/gxa/experiments/E-MTAB-513?displayLevels=true&specific=true&geneQuery=react_14797+react_19184+react_604+react_111102+react_111217+react_6900+react_71+react_116125+react_75774+react_6802+react_17015+react_22258+react_15518+react_115566+react_12627&geneSetMatch=true
    @Test
    public void eMTab513_Specific_MultipleGeneSets() throws GenesNotFoundException {
        String geneSets = "react_14797\treact_19184\treact_604\treact_111102\treact_111217\treact_6900\treact_71\treact_116125\treact_75774\treact_6802\treact_17015\treact_22258\treact_15518\treact_115566\treact_12627";
        setGeneQuery(geneSets);

        BaselineRequestContext requestContext = populateRequestContext(E_MTAB_513);
        long genesCount = subject.writeAsGeneSets(printWriterMock, requestContext);
        int expectedCount = 15;

        ArgumentCaptor<String[]> lineCaptor = ArgumentCaptor.forClass(String[].class);
        verify(csvWriterMock, times(expectedCount + 1)).writeNext(lineCaptor.capture());

        List<String[]> lines = lineCaptor.getAllValues();

        ImmutableMap<String, String[]> geneNameToLine = indexByGeneSetName(lines);
        Set<String> geneNames = geneNameToLine.keySet();

        assertThat(genesCount, is((long)expectedCount));
        assertThat(geneNames, hasSize(expectedCount));

        String[] react_111217 = geneNameToLine.get("react_111217");
        String[] react_12627 = geneNameToLine.get("react_12627");

        assertThat(geneNames, containsInAnyOrder(geneSets.split("\t")));
        assertThat(react_111217, is(new String[]{"react_111217", "8", "677", "17", "9", "8", "9", "10", "270", "11", "439", "497", "430", "10", "7", "15", "551"}));
        assertThat(react_12627, is(new String[] {"react_12627", "2", "3", "4", "3", "2", "2", "4", "4", "2", "2", "3", "5", "4", "1", "6", "4"}));
    }


    // http://localhost:8080/gxa/experiments/E-MTAB-513?displayLevels=true&geneQuery=react_71&specific=true&queryFactorValues=leukocyte&geneSetMatch=true
    @Test
//    @Ignore
    public void eMTab513react71_Specific_GeneSet_QueryFactorLeukocyteGeneSet_NoResults() throws GenesNotFoundException {
        setQueryFactor(FACTOR_LEUKOCYTE);
        setGeneQuery("react_71");

        BaselineRequestContext requestContext = populateRequestContext(E_MTAB_513);
        long genesCount = subject.writeAsGeneSets(printWriterMock, requestContext);
        int expectedCount = 0;

        ArgumentCaptor<String[]> lineCaptor = ArgumentCaptor.forClass(String[].class);
        verify(csvWriterMock, times(expectedCount + 1)).writeNext(lineCaptor.capture());

        List<String[]> lines = lineCaptor.getAllValues();

        ImmutableMap<String, String[]> geneNameToLine = indexByGeneSetName(lines);
        Set<String> geneNames = geneNameToLine.keySet();

        // no results because Leukoctye is not specific to react_71
        assertThat(genesCount, is((long)expectedCount));
        assertThat(geneNames, is(empty()));
    }

    // http://localhost:8080/gxa/experiments/E-MTAB-513?displayLevels=true&geneQuery=react_71&specific=true&queryFactorValues=prostate&geneSetMatch=true
    @Test
    public void eMTab513react71_Specific_GeneSet_QueryFactorProstateGeneSet_CheckExpressionLevelsForReact71() throws GenesNotFoundException {
        setQueryFactor(factor("prostate"));
        setGeneQuery("react_71");

        BaselineRequestContext requestContext = populateRequestContext(E_MTAB_513);
        long genesCount = subject.writeAsGeneSets(printWriterMock, requestContext);
        int expectedCount = 1;

        ArgumentCaptor<String[]> lineCaptor = ArgumentCaptor.forClass(String[].class);
        verify(csvWriterMock, times(expectedCount + 1)).writeNext(lineCaptor.capture());

        List<String[]> lines = lineCaptor.getAllValues();

        ImmutableMap<String, String[]> geneNameToLine = indexByGeneSetName(lines);
        Set<String> geneNames = geneNameToLine.keySet();

        System.out.println(Joiner.on("\", \"").join(geneNames));

        assertThat(genesCount, is((long)expectedCount));
        assertThat(geneNames, hasSize(expectedCount));

        String[] react_71 = geneNameToLine.get("react_71");

        assertThat(geneNames, containsInAnyOrder("react_71"));
        assertThat(react_71, is(new String[] {"react_71", "7", "10", "6", "6", "8", "6", "4", "11", "4", "6", "9", "9", "17", "11", "11", "8"}));
    }

    // http://localhost:8080/gxa/experiments/E-MTAB-513?displayLevels=true&geneQuery=react_71&_specific=on&queryFactorValues=leukocyte&geneSetMatch=true
    @Test
    public void eMTab513react71_NotSpecific_GeneSet_QueryFactorLeukocyteGeneSet_NoResults() throws GenesNotFoundException {
        setNotSpecific();
        eMTab513react71_Specific_GeneSet_QueryFactorProstateGeneSet_CheckExpressionLevelsForReact71();
    }

    private Factor factor(String factorValue) {
        return new Factor("ORGANISM_PART", factorValue);
    }


    private ImmutableMap<String, String[]> indexByGeneName(List<String[]> lines) {
        ImmutableMap.Builder<String, String[]> builder = ImmutableMap.builder();
        for (String line[] : lines) {
            String geneName = line[GENE_NAME_INDEX];
            if (!"Gene Name".equals(geneName)) {
                builder.put(line[GENE_NAME_INDEX], line);
            }
        }

        return builder.build();
    }

    private ImmutableMap<String, String[]> indexByGeneSetName(List<String[]> lines) {
        ImmutableMap.Builder<String, String[]> builder = ImmutableMap.builder();
        for (String line[] : lines) {
            String geneName = line[GENE_SET_NAME_INDEX];
            if (!"Gene set".equals(geneName)) {
                builder.put(line[GENE_SET_NAME_INDEX], line);
            }
        }

        return builder.build();
    }

    private void setGeneQuery(String geneQuery) {
        requestPreferences.setGeneQuery(geneQuery);
    }

    private void setNotSpecific() {
        requestPreferences.setSpecific(false);
    }

    private void setQueryFactor(Factor factor) {
        requestPreferences.setQueryFactorType(factor.getType());
        requestPreferences.setQueryFactorValues(Collections.singleton(factor.getValue()));
    }

}
