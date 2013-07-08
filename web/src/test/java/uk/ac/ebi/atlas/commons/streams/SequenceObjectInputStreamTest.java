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

package uk.ac.ebi.atlas.commons.streams;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Vector;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SequenceObjectInputStreamTest {

    private final static Object RETURN_VALUE1 = new Object();
    private final static Object RETURN_VALUE2 = new Object();

    @Mock
    private ObjectInputStream objectInputStreamMock1;

    @Mock
    private ObjectInputStream objectInputStreamMock2;

    private SequenceObjectInputStream subject;

    @Before
    public void setUp() throws Exception {
        Vector<ObjectInputStream> inputStreamVector = new Vector();
        inputStreamVector.add(objectInputStreamMock1);
        inputStreamVector.add(objectInputStreamMock2);
        subject = new SequenceObjectInputStream(inputStreamVector.elements());
    }

    @Test
    public void testReadNext() throws Exception {
        when(objectInputStreamMock1.readNext()).thenReturn(RETURN_VALUE1);
        assertThat(subject.readNext(), is(RETURN_VALUE1));
        verify(objectInputStreamMock1).readNext();
        when(objectInputStreamMock1.readNext()).thenReturn(null);

        when(objectInputStreamMock2.readNext()).thenReturn(RETURN_VALUE2);
        assertThat(subject.readNext(), is(RETURN_VALUE2));
        verify(objectInputStreamMock2).readNext();
        verify(objectInputStreamMock1).close();
        when(objectInputStreamMock2.readNext()).thenReturn(null);

        assertThat(subject.readNext(), is(nullValue()));
        verify(objectInputStreamMock2).close();
    }

    @Test
    public void testClose() throws Exception {
        subject.close();
        verify(objectInputStreamMock1).close();
        verify(objectInputStreamMock2).close();
    }
}