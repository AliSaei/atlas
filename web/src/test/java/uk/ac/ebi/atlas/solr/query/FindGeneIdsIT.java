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

package uk.ac.ebi.atlas.solr.query;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.commands.GenesNotFoundException;

import javax.inject.Inject;
import java.net.URISyntaxException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml"})
public class FindGeneIdsIT {

    @Inject
    private SolrQueryService subject;

    private static final String SPECIES = "homo sapiens";

    @Test
    public void findGeneIdsWithoutExactMatchTest() throws URISyntaxException, GenesNotFoundException {
        //given
        String geneQuery = "GO:0008134 \"p53 binding\"";
        //when
        GeneQueryResponse result = subject.findGeneIdsOrSets(geneQuery, false, SPECIES, false);

        //some genes are found
        assertThat(result.getAllGeneIds(), hasItems("ENSG00000131759", "ENSG00000112592"));
        assertThat(result.getAllGeneIds().size(), is(greaterThan(300)));
        assertThat(result.getAllGeneIds().size(), is(lessThan(600)));

    }

    @Test
    public void findGeneIdsWithExactMatchTest() throws URISyntaxException, GenesNotFoundException {
        //given
        String geneQuery = "ENSG00000131759 \"mRNA splicing, via spliceosome\"";
        //when
        GeneQueryResponse result = subject.findGeneIdsOrSets(geneQuery, true, SPECIES, false);

        //some genes are found
        assertThat(result.getAllGeneIds(), hasItems("ENSG00000131759", "ENSG00000084072"));
        assertThat(result.getAllGeneIds().size(), is(greaterThan(190)));
        assertThat(result.getAllGeneIds().size(), is(lessThan(210)));

    }

    @Test
    public void shouldReturnEmptyResultWhenThereIsNoMatchingId() throws URISyntaxException, GenesNotFoundException {
        //given
        String query = "\"NOTHING FOUND\"";

        GeneQueryResponse geneQueryResponse = subject.findGeneIdsOrSets(query, false, SPECIES, false);

        assertThat(geneQueryResponse.isEmpty(), is(true));

    }


}