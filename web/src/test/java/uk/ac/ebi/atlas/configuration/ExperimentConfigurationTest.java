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

package uk.ac.ebi.atlas.configuration;

import org.junit.Before;
import org.junit.Test;
import uk.ac.ebi.atlas.model.ExperimentType;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ExperimentConfigurationTest {

    private static final String EXPERIMENT_ACCESSION = "EXPERIMENT_ACCESSION";
    private static final ExperimentType EXPERIMENT_TYPE = ExperimentType.BASELINE;

    private ExperimentConfiguration subject;

    @Before
    public void setUp() throws Exception {
        subject = new ExperimentConfiguration(EXPERIMENT_ACCESSION, EXPERIMENT_TYPE);
    }

    @Test
    public void testGetExperimentAccession() throws Exception {
        assertThat(subject.getExperimentAccession(), is(EXPERIMENT_ACCESSION));
    }

    @Test
    public void testGetExperimentType() throws Exception {
        assertThat(subject.getExperimentType(), is(EXPERIMENT_TYPE));
    }
}