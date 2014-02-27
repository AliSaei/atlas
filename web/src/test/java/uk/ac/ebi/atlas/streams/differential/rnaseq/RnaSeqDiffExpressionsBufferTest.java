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

package uk.ac.ebi.atlas.streams.differential.rnaseq;

import com.google.common.collect.Lists;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.differential.Contrast;
import uk.ac.ebi.atlas.model.differential.DifferentialExpression;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class RnaSeqDiffExpressionsBufferTest {

    public static final String P_VAL_1 = "1";
    public static final String FOLD_CHANGE_1 = "0.474360080385946";

    public static final String P_VAL_2 = "1";
    public static final String FOLD_CHANGE_2 = "-Inf";

    private static final String[] TWO_CONTRASTS = new String[]{P_VAL_1, FOLD_CHANGE_1, P_VAL_2, FOLD_CHANGE_2};

    private RnaSeqDiffExpressionsQueue subject;

    @Mock
    private Contrast contrast1Mock;
    @Mock
    private Contrast contrast2Mock;

    @Before
    public void initializeSubject() {
        subject = new RnaSeqDiffExpressionsQueue(Lists.newArrayList(contrast1Mock, contrast2Mock));

    }

    @Test
    public void pollShouldReturnExpressionsInTheRightOrder() throws Exception {
        //given we load the buffer with three expressions
        subject.reload(TWO_CONTRASTS);

        //when
        DifferentialExpression expression = subject.poll();
        //then we expect first expression
        assertThat(expression.getPValue(), is(Double.valueOf(P_VAL_1)));
        assertThat(expression.getFoldChange(), is(Double.valueOf(FOLD_CHANGE_1)));
        assertThat(expression.getContrast(), is(contrast1Mock));

        //given we poll again
        expression = subject.poll();
        assertThat(expression.getPValue(), is(Double.valueOf(P_VAL_2)));
        assertThat(expression.getFoldChange(), is(Double.NEGATIVE_INFINITY));
        assertThat(expression.getContrast(), is(contrast2Mock));


        assertThat(subject.poll(), is(nullValue()));
    }

    @Test
    public void bufferShouldReturnNullWhenExhausted() throws Exception {
        //given we load the buffer with three expressions
        subject.reload(TWO_CONTRASTS);

        //when
        subject.poll();
        subject.poll();
        subject.poll();
        //then we expect next poll to return null
        assertThat(subject.poll(), Matchers.is(nullValue()));
    }

    @Test
    public void reloadShouldRefillAnExhaustedBuffer() throws Exception {
        //given we load the buffer with three expressions
        subject.reload(TWO_CONTRASTS);

        //when we poll until exhaustion
        DifferentialExpression run;
        do {
            run = subject.poll();
        } while (run != null);
        //and we reload again with new values
        subject.reload("1", "2");
        //and we poll
        DifferentialExpression expression = subject.poll();
        //then we expect to find the new values
        assertThat(expression.getPValue(), is(1d));
    }

    @Test(expected = IllegalStateException.class)
    public void reloadShouldThrowExceptionIfBufferIsNotEmpty() throws Exception {
        //given we load the buffer with three expressions
        subject.reload(TWO_CONTRASTS);
        //and we poll only a single expression
        subject.poll();

        //when we reload again
        subject.reload(TWO_CONTRASTS);

        //then we expect an IllegalArgumentException
    }

    @Test
    public void skipNALines() {

        subject.reload("NA", "NA");

        DifferentialExpression expression = subject.poll();

        assertThat(expression, is(CoreMatchers.nullValue()));

        subject.reload("1", "NA");

        expression = subject.poll();

        assertThat(expression, is(CoreMatchers.nullValue()));

        subject.reload("NA", "1");

        expression = subject.poll();

        assertThat(expression, is(CoreMatchers.nullValue()));
    }

    @Test
    public void skipNALinesKeepsCorrespondingContrasts() {

        subject.reload("NA", "1", P_VAL_2, "-Inf");

        DifferentialExpression expression = subject.poll();

        assertThat(expression.getPValue(), is(Double.valueOf(P_VAL_2)));
        assertThat(expression.getFoldChange(), is(Double.NEGATIVE_INFINITY));
        assertThat(expression.getContrast(), is(contrast2Mock));
    }

}
