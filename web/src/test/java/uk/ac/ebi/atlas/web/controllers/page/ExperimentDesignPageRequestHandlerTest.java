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

package uk.ac.ebi.atlas.web.controllers.page;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.springframework.test.util.MatcherAssertionErrors.assertThat;
import static uk.ac.ebi.atlas.web.controllers.page.ExperimentDesignPageRequestHandler.SAMPLE_COLUMN_HEADER_PATTERN;
import static uk.ac.ebi.atlas.web.controllers.page.ExperimentDesignPageRequestHandler.extractMatchingContent;

public class ExperimentDesignPageRequestHandlerTest {

    private ExperimentDesignPageRequestHandler subject;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testExtractGroup1Match() throws Exception {
        String matchingString = extractMatchingContent("Assay Bello", SAMPLE_COLUMN_HEADER_PATTERN);
        assertThat(matchingString, is(nullValue()));

        matchingString = extractMatchingContent("Assay Bello[assai]", SAMPLE_COLUMN_HEADER_PATTERN);
        assertThat(matchingString, is(nullValue()));

        matchingString = extractMatchingContent(" Sample Characteristics[bello assai] ", SAMPLE_COLUMN_HEADER_PATTERN);
        assertThat(matchingString, is("bello assai"));

        matchingString = extractMatchingContent("Sample Characteristics[bello assai]", SAMPLE_COLUMN_HEADER_PATTERN);
        assertThat(matchingString, is("bello assai"));

        matchingString = extractMatchingContent("Sample  Characteristics[bello assai]", SAMPLE_COLUMN_HEADER_PATTERN);
        assertThat(matchingString, is(nullValue()));
    }
}
