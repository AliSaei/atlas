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

package uk.ac.ebi.atlas.model.baseline;

import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.commands.context.BaselineRequestContext;

import java.util.HashSet;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.anyDouble;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BaselineProfileBuilderTest {

    public static final String GENE_ID = "geneId";
    @Mock
    BaselineRequestContext requestContextMock;

    @Mock
    BaselineExpression expressionMock;

    Factor factor;

    BaselineProfileBuilder subject;

    @Before
    public void setUp() throws Exception {
        factor = new Factor("type", "value");
        when(requestContextMock.getCutoff()).thenReturn(0.05);
        when(requestContextMock.getSelectedFilterFactors()).thenReturn(new HashSet<Factor>());
        when(requestContextMock.getAllQueryFactors()).thenReturn(Sets.newTreeSet(Sets.newHashSet(factor)));
        when(requestContextMock.getSelectedQueryFactors()).thenReturn(Sets.newHashSet(factor));
        when(requestContextMock.isSpecific()).thenReturn(false);

        when(expressionMock.isGreaterThan(anyDouble())).thenReturn(true);


        subject = new BaselineProfileBuilder(requestContextMock, new BaselineExpressionPrecondition(),
                new BaselineProfilePrecondition());
    }

    @Test
    public void testCreate() throws Exception {
        BaselineProfile profile = subject.forGeneId(GENE_ID).addExpression(expressionMock).create();
        assertThat(profile.getGeneId(), is(GENE_ID));
    }
}