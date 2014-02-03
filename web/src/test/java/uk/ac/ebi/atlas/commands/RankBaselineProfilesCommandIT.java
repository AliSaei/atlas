/*
 * Copyright 2008-2013 Microarray Informatics Team, EMBL-European Bioinformatics Institute
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
 * For further details of the Gene Expression Atlas project, including source code,
 * downloads and documentation, please see:
 *
 * http://gxa.github.com/gxa
 */

package uk.ac.ebi.atlas.commands;

import com.google.common.base.Joiner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.commands.context.BaselineRequestContextBuilder;
import uk.ac.ebi.atlas.model.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.baseline.BaselineProfilesList;
import uk.ac.ebi.atlas.model.baseline.Factor;
import uk.ac.ebi.atlas.trader.cache.BaselineExperimentsCache;
import uk.ac.ebi.atlas.web.BaselineRequestPreferences;

import javax.inject.Inject;
import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
public class RankBaselineProfilesCommandIT {

    public static final String E_MTAB_513 = "E-MTAB-513";
    private static final Factor FACTOR_LEUKOCYTE = new Factor("ORGANISM_PART", "leukocyte");

    @Inject
    private BaselineExperimentsCache baselineExperimentsCache;

    @Inject
    private RankBaselineProfilesCommand subject;

    @Inject
    BaselineRequestContextBuilder baselineRequestContextBuilder;

    private BaselineRequestPreferences requestPreferences = new BaselineRequestPreferences();

    @Before
    public void initRequestContext() {
        populateRequestContext();
    }

    private void populateRequestContext() {
        BaselineExperiment baselineExperiment = baselineExperimentsCache.getExperiment(E_MTAB_513);

        requestPreferences.setQueryFactorType("ORGANISM_PART");
        baselineRequestContextBuilder.forExperiment(baselineExperiment)
                .withPreferences(requestPreferences)
                .build();
    }

    // http://localhost:8080/gxa/experiments/E-MTAB-513?displayLevels=true&specific=true
    @Test
    public void eMTab513react71_Specific() throws GenesNotFoundException {
        BaselineProfilesList profiles = subject.execute(E_MTAB_513);

        //System.out.println(Joiner.on("\", \"").join(profiles.extractGeneNames()));

        assertThat(profiles.getTotalResultCount(), is(148));
        assertThat(profiles.extractGeneNames(), contains("ACTL7A", "TEX33", "DCAF4L2", "SLC10A1", "ABCG8", "PRMT8", "UBQLNL", "FAM47B", "ZSCAN5B", "WFDC10A", "CCNE2", "USP26", "SBK2", "LIPF", "SRSF2", "C17orf64", "SCN2A", "RP11-192H23.4", "MYOD1", "ESPL1", "ZNF713", "GAS2L2", "NOTUM", "CRISP3", "C1QL3", "GFI1", "MT-ATP6", "SLAMF6", "MFSD7", "PVRL4", "RTDR1", "ARHGAP8", "RAB27B", "TRPM2", "IL12RB2", "DAPL1", "RGS7BP", "AC061975.10", "PRSS16", "SRGAP3", "C1QTNF2", "NEDD8", "PMM2", "RGP1", "GLB1L2", "CCDC66", "RANBP17", "BICD1", "ZFP2", "NEBL"));

    }

//    @Test
//    public void eMTab513_Specific_2Genes() throws GenesNotFoundException {
//        requestPreferences.setGeneQuery("ENSG00000176566 ENSG00000100652");
//        BaselineProfilesList profiles = subject.execute(E_MTAB_513);
//
//        System.out.println(Joiner.on("\", \"").join(profiles.extractGeneNames()));
//
//        assertThat(profiles.getTotalResultCount(), is(2));
//        assertThat(profiles.extractGeneNames(), contains("SLC10A1", "DCAF4L2"));
//
//    }

    //http://localhost:8080/gxa/experiments/E-MTAB-513?displayLevels=true&_specific=on
    @Test
    public void eMTab513react71_NotSpecific() throws GenesNotFoundException {
        setNotSpecific();
        BaselineProfilesList profiles = subject.execute(E_MTAB_513);

        assertThat(profiles.getTotalResultCount(), is(148));
        assertThat(profiles.extractGeneNames(), contains("MT-ATP6", "TMSB10", "RTN4", "NEDD8", "ARPC5", "RAB13", "CTNNA1", "ATP1B3", "CALU", "VPS4A", "PPT1", "VTI1B", "S1PR1", "AIDA", "ZCRB1", "IL13RA1", "ARHGAP1", "CBX1", "AC061975.10", "CRLS1", "BMI1", "PLEKHB2", "BOD1", "MAPRE2", "POLR2B", "NEBL", "SNRPA", "POLE3", "GFPT1", "B3GNT1", "GGPS1", "MRPL13", "DEPTOR", "VAPB", "COL15A1", "C6orf203", "GOLPH3L", "PTBP3", "TUBA1C", "SLK", "ASNS", "EMCN", "OCEL1", "RNF25", "RNF41", "INPP5D", "ERLIN1", "CRY2", "THOC3", "COL4A3BP"));
    }

    // http://localhost:8080/gxa/experiments/E-MTAB-513?displayLevels=true&specific=true&queryFactorValues=leukocyte
    @Test
    public void eMTab513react71_Specific_QueryFactorLeukocyte() throws GenesNotFoundException {
        setQueryFactor(FACTOR_LEUKOCYTE);

        BaselineProfilesList profiles = subject.execute(E_MTAB_513);

        assertThat(profiles.getTotalResultCount(), is(19));
        assertThat(profiles.extractGeneNames(), contains("APOBR", "INPP5D", "PTBP3", "ZDHHC18", "PPT1", "ARPC5", "GFI1", "INPP4A", "SNX27", "KDM4A", "POLR2B", "PLCG2", "SLAMF6", "COL4A3BP", "RAB27B", "FBXO38", "C6orf1", "TMSB10", "POLE3"));

    }

    // http://localhost:8080/gxa/experiments/E-MTAB-513?displayLevels=true&_specific=on&queryFactorValues=leukocyte
    @Test
    public void eMTab513react71_NotSpecific_QueryFactorLeukocyte() throws GenesNotFoundException {
        setNotSpecific();
        setQueryFactor(FACTOR_LEUKOCYTE);

        BaselineProfilesList profiles = subject.execute(E_MTAB_513);

        assertThat(profiles.getTotalResultCount(), is(103));
        assertThat(profiles.extractGeneNames(), contains("MT-ATP6", "TMSB10", "ARPC5", "NEDD8", "RTN4", "PPT1", "INPP5D", "S1PR1", "PTBP3", "ATP1B3", "AC061975.10", "PLEKHB2", "POLR2B", "CTNNA1", "SLAMF6", "MAPRE2", "IL13RA1", "APOBR", "ZDHHC18", "ARHGAP1", "POLE3", "AIDA", "VPS4A", "CBX1", "COL4A3BP", "PLCG2", "GGPS1", "BMI1", "RNF41", "SNX27", "INPP4A", "SNRPA", "OCEL1", "TUBA1C", "ZCRB1", "VRK3", "VTI1B", "FBXO38", "RNF25", "RAE1", "CALU", "SLK", "C6orf1", "CRLS1", "MRPL13", "MSH6", "BET1", "VAPB", "KDM4A", "TERF2"));

    }


    @Test
    public void eMTab513react71_NotSpecific_QueryFactorLeukocyte_11Genes() throws GenesNotFoundException {
        setNotSpecific();
        setQueryFactor(FACTOR_LEUKOCYTE);
        setGeneQuery("MT-ATP6 TMSB10 RTN4 NEDD8 ARPC5 RAB13 CTNNA1 ATP1B3 CALU VPS4A PPT1");

        BaselineProfilesList profiles = subject.execute(E_MTAB_513);

        System.out.println(Joiner.on("\", \"").join(profiles.extractGeneNames()));
        assertThat(profiles.getTotalResultCount(), is(11));
        assertThat(profiles.extractGeneNames(), contains("MT-ATP6", "TMSB10", "ARPC5", "NEDD8", "RTN4", "PPT1", "ATP1B3", "CTNNA1", "VPS4A", "CALU", "RAB13"));

    }


    @Test
    public void eMTab513react71_NotSpecific_QueryFactorLeukocyte_2Genes() throws GenesNotFoundException {
        setNotSpecific();
        setQueryFactor(FACTOR_LEUKOCYTE);
        setGeneQuery("MAPRE2 SLAMF6");

        BaselineProfilesList profiles = subject.execute(E_MTAB_513);


        System.out.println(Joiner.on("\", \"").join(profiles.extractGeneNames()));
        assertThat(profiles.getTotalResultCount(), is(2));
        assertThat(profiles.extractGeneNames(), contains("SLAMF6", "MAPRE2"));

    }

    // http://localhost:8080/gxa/experiments/E-MTAB-513?displayLevels=true&_specific=on&geneQuery=react_14797+react_19184+react_604+react_111102+react_111217+react_6900+react_71+react_116125+react_75774+react_6802+react_17015+react_22258+react_15518+react_115566+react_12627&geneSetMatch=true
    @Test
    public void eMTab513react71_NotSpecific_MultipleGeneSets() throws GenesNotFoundException {
        setNotSpecific();
        setGeneSet();
        setGeneQuery("react_14797 react_19184 react_604 react_111102 react_111217 react_6900 react_71 react_116125 react_75774 react_6802 react_17015 react_22258 react_15518 react_115566 react_12627");

        BaselineProfilesList profiles = subject.execute(E_MTAB_513);
        //System.out.println(Joiner.on("\", \"").join(profiles.extractGeneNames()));

        assertThat(profiles.getTotalResultCount(), is(41));
        assertThat(profiles.extractGeneNames(), contains("react_111217", "react_111102", "react_6802", "react_19184", "react_604", "react_15518", "react_6900", "react_14797", "react_116125", "react_75774", "react_17015", "react_22258", "react_71", "react_115566", "react_12627"));

    }

    // http://localhost:8080/gxa/experiments/E-MTAB-513?displayLevels=true&specific=true&geneQuery=react_14797+react_19184+react_604+react_111102+react_111217+react_6900+react_71+react_116125+react_75774+react_6802+react_17015+react_22258+react_15518+react_115566+react_12627&geneSetMatch=true
    @Test
    public void eMTab513react71_Specific_MultipleGeneSets() throws GenesNotFoundException {
        setGeneSet();
        setGeneQuery("react_14797 react_19184 react_604 react_111102 react_111217 react_6900 react_71 react_116125 react_75774 react_6802 react_17015 react_22258 react_15518 react_115566 react_12627");

        BaselineProfilesList profiles = subject.execute(E_MTAB_513);
        //System.out.println(Joiner.on("\", \"").join(profiles.extractGeneNames()));

        assertThat(profiles.getTotalResultCount(), is(41));
        assertThat(profiles.extractGeneNames(), contains("react_111217", "react_111102", "react_6802", "react_19184", "react_604", "react_15518", "react_6900", "react_14797", "react_116125", "react_75774", "react_17015", "react_22258", "react_71", "react_115566", "react_12627"));

    }

    private void setGeneQuery(String geneQuery) {
        requestPreferences.setGeneQuery(geneQuery);
    }

    private void setGeneSet() {
        requestPreferences.setGeneSetMatch(true);
    }

    private void setNotSpecific() {
        requestPreferences.setSpecific(false);
    }

    private void setQueryFactor(Factor factor) {
        requestPreferences.setQueryFactorType(factor.getType());
        requestPreferences.setQueryFactorValues(Collections.singleton(factor.getValue()));

        // set query factor on request context
        populateRequestContext();
    }
}
