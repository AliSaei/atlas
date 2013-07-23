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

package uk.ac.ebi.atlas.experimentloader;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.model.ExperimentType;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.util.List;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class ConfigurationDaoIT {

    private static final String E_MTAB_513 = "E-MTAB-513";
    private static final String E_MTAB_1066 = "E-MTAB-1066";
    private static final ExperimentType TYPE_BASELINE = ExperimentType.BASELINE;
    private static final ExperimentType TYPE_DIFFERENTIAL = ExperimentType.DIFFERENTIAL;
    private static final ExperimentType TYPE_MICROARRAY = ExperimentType.MICROARRAY;
    private static final ExperimentType TYPE_MICRORNA = ExperimentType.MICRORNA;
    private static final String DIFFERENTIAL_ACCESION = "ANOTHER";
    private static final String MICROARRAY_ACCESSION = "YET ANOTHER";
    private static final String MICRORNA_ACCESSION = "YET YET ANOTHER";

    @Inject
    @Qualifier("dataSource")
    private DataSource dataSource;

    @Inject
    private ExperimentConfigurationDao subject;

    @Before
    public void setUp() throws Exception {
        subject.addExperimentConfiguration(E_MTAB_513, TYPE_BASELINE);
    }

    @After
    public void tearDown() throws Exception {
        subject.deleteExperimentConfiguration(E_MTAB_513);
        subject.deleteExperimentConfiguration(E_MTAB_1066);
        subject.deleteExperimentConfiguration(DIFFERENTIAL_ACCESION);
        subject.deleteExperimentConfiguration(MICROARRAY_ACCESSION);
        subject.deleteExperimentConfiguration(MICRORNA_ACCESSION);
    }

    @Test
    public void testGetExperimentConfigurations() throws Exception {
        List<ExperimentConfiguration> experimentConfigurations = subject.findAllExperimentConfigurations();
        assertThat(experimentConfigurations, hasItem(new ExperimentConfiguration(E_MTAB_513, TYPE_BASELINE)));
    }

    @Test
    public void testGetExperimentConfigurationsByType() throws Exception {
        subject.addExperimentConfiguration(DIFFERENTIAL_ACCESION, TYPE_DIFFERENTIAL);
        subject.addExperimentConfiguration(MICROARRAY_ACCESSION, TYPE_MICROARRAY);
        subject.addExperimentConfiguration(MICRORNA_ACCESSION, TYPE_MICRORNA);
        Set<String> experimentAccessions = subject.getExperimentAccessions(TYPE_BASELINE);
        assertThat(experimentAccessions, contains(E_MTAB_513));
        experimentAccessions = subject.getExperimentAccessions(TYPE_DIFFERENTIAL);
        assertThat(experimentAccessions, hasItem(DIFFERENTIAL_ACCESION));
        experimentAccessions = subject.getExperimentAccessions(TYPE_MICROARRAY);
        assertThat(experimentAccessions, hasItem(MICROARRAY_ACCESSION));
        experimentAccessions = subject.getExperimentAccessions(TYPE_MICRORNA);
        assertThat(experimentAccessions, hasItem(MICRORNA_ACCESSION));
    }

    @Test
    public void testGetExperimentConfiguration() throws Exception {
        ExperimentConfiguration experimentConfiguration = subject.getExperimentConfiguration(E_MTAB_513);
        assertThat(experimentConfiguration.getExperimentAccession(), is(E_MTAB_513));
        assertThat(experimentConfiguration.getExperimentType(), is(TYPE_BASELINE));
    }

    @Test
    public void testGetExperimentConfigurationEmpty() throws Exception {
        assertThat(subject.getExperimentConfiguration("NON-EXISTING"), is(nullValue()));
    }

    @Test
    public void testAddExperimentConfiguration() throws Exception {
        List<ExperimentConfiguration> experimentConfigurations = subject.findAllExperimentConfigurations();
        int size = experimentConfigurations.size();
        assertThat(subject.addExperimentConfiguration(E_MTAB_1066, TYPE_MICROARRAY), is(true));
        experimentConfigurations = subject.findAllExperimentConfigurations();
        assertThat(experimentConfigurations.size(), is(size + 1));
        assertThat(experimentConfigurations, hasItem(new ExperimentConfiguration(E_MTAB_1066, TYPE_MICROARRAY)));
    }

    @Test
    public void testDeleteExperimentConfiguration() throws Exception {
        List<ExperimentConfiguration> experimentConfigurations = subject.findAllExperimentConfigurations();
        int size = experimentConfigurations.size();
        assertThat(subject.deleteExperimentConfiguration(E_MTAB_513), is(true));
        assertThat(subject.findAllExperimentConfigurations().size(), is(size - 1));
    }
}