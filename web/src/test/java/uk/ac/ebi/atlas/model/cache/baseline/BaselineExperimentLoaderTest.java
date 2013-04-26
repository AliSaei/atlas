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

package uk.ac.ebi.atlas.model.cache.baseline;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.commons.readers.TsvReader;
import uk.ac.ebi.atlas.model.ConfigurationTrader;
import uk.ac.ebi.atlas.model.baseline.ExperimentRun;
import uk.ac.ebi.atlas.model.baseline.Factor;
import uk.ac.ebi.atlas.model.baseline.FactorGroup;
import uk.ac.ebi.atlas.model.cache.baseline.magetab.MageTabParser;
import uk.ac.ebi.atlas.model.cache.baseline.magetab.MageTabParserBuilder;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BaselineExperimentLoaderTest {

    public static final String SPECIES = "species";
    public static final String COLUMN_HEADER12 = "SRR12345,SRR23456";
    public static final String COLUMN_HEADER3 = "SRR34567";
    public static final String COLUMN_HEADER1 = "SRR12345";
    public static final String COLUMN_HEADER2 = "SRR23456";
    public static final String DEFAULT_QUERY_FACTOR_TYPE = "defaultQueryFactorType";
    public static final String FACTOR_TYPE = "factorType";

    @Mock
    private BaselineExperimentLoader subject;

    @Mock
    private MageTabParser mageTabParserMock;

    @Mock
    private ExperimentRun experimentRunMock1;

    @Mock
    private ExperimentRun experimentRunMock2;

    @Mock
    private FactorGroup factorGroupMock1;

    @Mock
    private FactorGroup factorGroupMock2;

    @Mock
    private Factor factorMock;

    @Mock
    private MageTabParserBuilder mageTabParserBuilderMock;

    @Mock
    private ConfigurationTrader configurationTraderMock;

    @Mock
    private TsvReader experimentDataTsvReaderMock;

    @Test
    public void testGetSpecies() throws Exception {

        doCallRealMethod().when(subject).getSpecies(mageTabParserMock);
        when(mageTabParserMock.extractSpecies()).thenReturn(Sets.newHashSet(SPECIES));

        assertThat(subject.getSpecies(mageTabParserMock), contains(SPECIES));
    }

    @Test
    public void testExtractOrderedFactorGroups() throws Exception {

        String[] columnHeaders = {COLUMN_HEADER12, COLUMN_HEADER3};
        Map<String, ExperimentRun> experimentRuns = Maps.newHashMap();
        experimentRuns.put(COLUMN_HEADER1, experimentRunMock1);
        experimentRuns.put(COLUMN_HEADER3, experimentRunMock2);

        when(experimentRunMock1.getFactorGroup()).thenReturn(factorGroupMock1);
        when(experimentRunMock2.getFactorGroup()).thenReturn(factorGroupMock2);

        doCallRealMethod().when(subject).extractOrderedFactorGroups(columnHeaders, experimentRuns);

        List<FactorGroup> factorGroups = subject.extractOrderedFactorGroups(columnHeaders, experimentRuns);
        assertThat(factorGroups, contains(factorGroupMock1, factorGroupMock2));
    }

    @Test
    public void testExtractProcessedRunAccessions() throws Exception {

        String[] columnHeaders = {COLUMN_HEADER12, COLUMN_HEADER3, COLUMN_HEADER1};

        doCallRealMethod().when(subject).extractProcessedRunAccessions(columnHeaders);
        Set<String> runAccessions = subject.extractProcessedRunAccessions(columnHeaders);
        assertThat(runAccessions, containsInAnyOrder(COLUMN_HEADER1, COLUMN_HEADER2, COLUMN_HEADER3));
    }

    @Test
    public void testGetRequiredFactorTypes() throws Exception {

        Set<Factor> defaultFilterFactors = new HashSet<>();

        doCallRealMethod().when(subject).getRequiredFactorTypes(DEFAULT_QUERY_FACTOR_TYPE, defaultFilterFactors);
        Set<String> requiredFactorTypes = subject.getRequiredFactorTypes(DEFAULT_QUERY_FACTOR_TYPE, defaultFilterFactors);
        assertThat(requiredFactorTypes, contains(DEFAULT_QUERY_FACTOR_TYPE));

        when(factorMock.getType()).thenReturn(FACTOR_TYPE);
        defaultFilterFactors.add(factorMock);
        requiredFactorTypes = subject.getRequiredFactorTypes(DEFAULT_QUERY_FACTOR_TYPE, defaultFilterFactors);
        assertThat(requiredFactorTypes, containsInAnyOrder(DEFAULT_QUERY_FACTOR_TYPE, FACTOR_TYPE));
    }


}