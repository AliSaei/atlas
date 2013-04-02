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
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BaselineProfileComparatorTest {

    private BaselineProfileComparator subject;

    private static final String QUERY_FACTOR_TYPE = "ORGANISM_PART";

    @Mock
    private BaselineProfile geneWithSpecificity1;

    @Mock
    private BaselineProfile geneWithSpecificity16;

    @Mock
    private BaselineProfile geneWithSpecificity16AndSmallerExpressionLevel;

    private Factor factor1 = new Factor(QUERY_FACTOR_TYPE, "nose");
    private Factor factor2 = new Factor(QUERY_FACTOR_TYPE, "trunk");
    private Factor factor3 = new Factor(QUERY_FACTOR_TYPE, "head");


    private Set<Factor> selectedOrganismParts = Sets.newHashSet(factor1);

    private Set<Factor> allOrganismParts = Sets.newHashSet(factor1, factor2);

    private double cutoff = 0.5;

    @Before
    public void initGeneExpressions() {
        when(geneWithSpecificity1.getSpecificity()).thenReturn(1);
        when(geneWithSpecificity1.getAverageExpressionLevelOn(allOrganismParts)).thenReturn(5D);
        when(geneWithSpecificity1.getAverageExpressionLevelOn(selectedOrganismParts)).thenReturn(5D);

        when(geneWithSpecificity16.getSpecificity()).thenReturn(16);
        when(geneWithSpecificity16.getAverageExpressionLevelOn(allOrganismParts)).thenReturn(10D);
        when(geneWithSpecificity16.getAverageExpressionLevelOn(selectedOrganismParts)).thenReturn(2D);
        when(geneWithSpecificity16.getAverageExpressionLevelOn(Sets.difference(allOrganismParts, selectedOrganismParts))).thenReturn(1D);

        when(geneWithSpecificity16AndSmallerExpressionLevel.getSpecificity()).thenReturn(16);
        when(geneWithSpecificity16AndSmallerExpressionLevel.getAverageExpressionLevelOn(allOrganismParts)).thenReturn(0D);
    }


    @Test
    public void lowSpecificityShouldFollowHigherSpecificity() {

        subject = new BaselineProfileComparator(true, null, allOrganismParts, cutoff);

        //when
        int comparison = subject.compare(geneWithSpecificity16, geneWithSpecificity1);

        //then
        assertThat(comparison, is(greaterThan(0)));

    }

    @Test
    public void highSpecificityShouldPreceedLowSpecificity() {

        subject = new BaselineProfileComparator(true, null, allOrganismParts, cutoff);

        //when
        int comparison = subject.compare(geneWithSpecificity1, geneWithSpecificity16);

        //then
        assertThat(comparison, is(lessThan(0)));

    }

    @Test
    public void sameSpecificityShouldBeSortedByExpressionLevel() {

        subject = new BaselineProfileComparator(true, null, allOrganismParts, cutoff);

        //when
        int comparison = subject.compare(geneWithSpecificity16, geneWithSpecificity16AndSmallerExpressionLevel);

        // then
        assertThat(comparison, is(lessThan(0)));

    }


    @Test
    public void higherAverageAcrossAllTissues() {

        subject = new BaselineProfileComparator(false, null, allOrganismParts, cutoff);

        // when
        int comparison = subject.compare(geneWithSpecificity1, geneWithSpecificity16);

        // then
        assertThat(comparison, is(greaterThan(0)));

    }

    @Test
    public void higherAverageAcrossSelectedTissues() {

        subject = new BaselineProfileComparator(false, selectedOrganismParts, allOrganismParts, cutoff);

        // when
        int comparison = subject.compare(geneWithSpecificity1, geneWithSpecificity16);

        // then
        assertThat(comparison, is(lessThan(0)));

    }

    @Test
    public void testGetExpressionLevelFoldChangeOnForSpecificity1ShouldIncreaseExpLevel() {
        subject = new BaselineProfileComparator(false, selectedOrganismParts, allOrganismParts, 0.5);

        double averageExpressionLevel = subject.getExpressionLevelFoldChangeOn(geneWithSpecificity1);
        assertThat(averageExpressionLevel, Matchers.is(10d));

    }


    @Test
    public void testGetExpressionLevelFoldChangeOnForCutoff0() {
        subject = new BaselineProfileComparator(false, selectedOrganismParts, allOrganismParts, 0d);

        double averageExpressionLevel = subject.getExpressionLevelFoldChangeOn(geneWithSpecificity1);
        assertThat(averageExpressionLevel, is(55.55555555555556));

    }

    @Test
    public void testGetExpressionLevelFoldChangeOnForSpecificityGraterThen1() {
        subject = new BaselineProfileComparator(false, selectedOrganismParts, allOrganismParts, 0.5);

        double averageExpressionLevel = subject.getExpressionLevelFoldChangeOn(geneWithSpecificity16);
        assertThat(averageExpressionLevel, is(2.0));


    }
}
