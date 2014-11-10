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

import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import org.apache.solr.client.solrj.SolrServerException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import uk.ac.ebi.atlas.experimentpage.context.GenesNotFoundException;
import uk.ac.ebi.atlas.solr.BioentityProperty;

import javax.inject.Inject;
import java.util.List;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
public class SolrQueryServiceIT {

    @Inject
    private SolrQueryService subject;

    @Test
    public void testQuerySolrForProperties() throws SolrServerException {

        //when
        Multimap<String, String> multimap = subject.fetchProperties("ENSG00000109819", new String[]{"goterm"});

        // then
        assertThat(multimap.get("goterm"), hasItems("RNA splicing", "cellular response to oxidative stress", "cellular glucose homeostasis"));

    }

    @Test
    public void testGetPropertyValuesForIdentifier() throws SolrServerException {

        assertThat(subject.findPropertyValuesForGeneId("ENSG00000179218", "symbol"), hasItem("CALR"));
        assertThat(subject.findPropertyValuesForGeneId("ENSMUSG00000029816", "symbol"), hasItem("Gpnmb"));

    }

    @Test
    public void shouldFindCaseInsentiveIdButReturnABioentityPropertyWithRightCase() throws SolrServerException {

        BioentityProperty bioentityProperty = subject.findBioentityIdentifierProperty("enSG00000179218");
        assertThat(bioentityProperty.getBioentityType(), is("ensgene"));
        assertThat(bioentityProperty.getBioentityIdentifier(), is("ENSG00000179218"));

        bioentityProperty = subject.findBioentityIdentifierProperty("enSP00000355434");
        assertThat(bioentityProperty.getBioentityType(), is("ensprotein"));
        assertThat(bioentityProperty.getBioentityIdentifier(), is("ENSP00000355434"));

        bioentityProperty = subject.findBioentityIdentifierProperty("enST00000559981");
        assertThat(bioentityProperty.getBioentityType(), is("enstranscript"));
        assertThat(bioentityProperty.getBioentityIdentifier(), is("ENST00000559981"));

    }


    @Test
    public void shouldReturnNullForNonExistingId() throws SolrServerException {

        assertThat(subject.findBioentityIdentifierProperty("XYZEMC2"), is(nullValue()));
        assertThat(subject.findBioentityIdentifierProperty("Map2k7"), is(nullValue()));

    }


    @Test
    public void shouldReturnTheRightBioentityType() throws SolrServerException {

        assertThat(subject.findBioentityIdentifierProperty("ENSG00000179218").getBioentityType(), is("ensgene"));
        assertThat(subject.findBioentityIdentifierProperty("ENSP00000355434").getBioentityType(), is("ensprotein"));
        assertThat(subject.findBioentityIdentifierProperty("ENST00000559981").getBioentityType(), is("enstranscript"));

    }

    @Test
    public void mirbaseHairpinIdForSingleGene() {
        assertThat(subject.findBioentityIdentifierProperty("hsa-mir-15a"), is(nullValue()));
        assertThat(subject.findBioentityIdentifierProperty("hsa-mir-767"), is(nullValue()));
        assertThat(subject.findBioentityIdentifierProperty("hsa-mir-98"), is(nullValue()));
    }

    @Test
    public void mirbaseHairpinIdForSingleGene_ThatAlsoHasMature() {
        assertThat(subject.findBioentityIdentifierProperty("hsa-mir-636"), is(nullValue()));
    }

    @Test
    public void mirbaseHairpinIdForMultipleGenes() {
        assertThat(subject.findBioentityIdentifierProperty("hsa-mir-8072"), is(nullValue()));
        assertThat(subject.findBioentityIdentifierProperty("hsa-mir-1302-10"), is(nullValue()));
        assertThat(subject.findBioentityIdentifierProperty("hsa-mir-4646"), is(nullValue()));
    }

    @Test
    public void mirbaseMatureIdForSingleGene() {
        assertThat(subject.findBioentityIdentifierProperty("hsa-miR-486-5p"), is(nullValue()));
    }

    @Test
    public void mirbaseMatureIdForSingleGene_ThatAlsoHasHairpain() {
        assertThat(subject.findBioentityIdentifierProperty("hsa-miR-636"), is(nullValue()));
    }

    @Test
    public void testFetchGeneIdentifiersFromSolr() throws SolrServerException, GenesNotFoundException {

        // given
        Set<String> geneQueryResponse = subject.findGeneIdsOrSets("aspm", false, "homo sapiens");

        // then
        assertThat(geneQueryResponse, contains("ENSG00000066279"));

    }

    @Test
    public void testFetchGeneIdentifiersFromSolrMany() throws SolrServerException, GenesNotFoundException {

        // given
        Set<String> geneQueryResponse = subject.findGeneIdsOrSets("protein", false, "homo sapiens");

        // then
        assertThat(geneQueryResponse.size(), lessThan(200000));
        assertThat(geneQueryResponse, hasItems("ENSG00000126773", "ENSG00000183878"));

    }


    @Test
    public void findGenesFromMirBaseIDs()  {

        List<String> identifiers = Lists.newArrayList("hsa-mir-636");

        Set<String> ensemblIDs = subject.findGenesFromMirBaseIDs(identifiers);

        assertThat(ensemblIDs, contains("ENSG00000207556"));
    }

    @Test
    public void fetchCaseSensitiveGeneId() {

        Set<String> geneIds = subject.fetchGeneIds("CG11255", true, "");

        assertThat(geneIds, hasSize(1));
        assertThat(geneIds.iterator().next(), is("FBgn0036337")); //should be case sensitive, NOT all uppercase
    }

}