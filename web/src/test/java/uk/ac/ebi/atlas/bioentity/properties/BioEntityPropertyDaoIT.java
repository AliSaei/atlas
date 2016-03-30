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

package uk.ac.ebi.atlas.bioentity.properties;

import com.google.common.collect.Multimap;
import org.apache.solr.client.solrj.SolrServerException;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.inject.Inject;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
public class BioEntityPropertyDaoIT {

    @Inject
    private BioEntityPropertyDao subject;

    @Value("#{configuration['index.property_names.genepage']}")
    private String[] genePagePropertyTypes;

    @Test
    public void fetchGenePageProperties() throws Exception {
        // given
        Multimap<String, String> properties = subject.fetchGenePageProperties("ENSMUSG00000029816", genePagePropertyTypes);

        // index.types.genepage=symbol,description,synonym,ortholog,goterm,interproterm,ensfamily_description,ensgene,entrezgene,uniprot,mgi_id,mgi_description,gene_biotype,design_element

        assertThat(properties.size(), greaterThan(50));
        assertThat(properties.get("synonym").size(), is(3));
        assertThat(properties.get("synonym"), containsInAnyOrder("DC-HIL", "Dchil", "Osteoactivin"));
        assertThat(properties.get("ortholog").size(), is(65));
        assertThat(properties.get("go").size(), is(12));
        assertThat(properties.get("interpro"), containsInAnyOrder("IPR000601", "IPR022409"));
        assertThat(properties.get("ensfamily_description"), containsInAnyOrder("PRECURSOR"));
        assertThat(properties.get("ensgene"), containsInAnyOrder("ENSMUSG00000029816"));
        assertThat(properties.get("entrezgene"), containsInAnyOrder("93695"));
        assertThat(properties.get("uniprot"), containsInAnyOrder("A0A0N4SVG5", "Q8BVA0", "Q99P91"));
        assertThat(properties.get("mgi_id"), containsInAnyOrder("MGI:1934765"));
        assertThat(properties.get("mgi_description"), containsInAnyOrder("glycoprotein (transmembrane) nmb"));
        assertThat(properties.get("gene_biotype"), containsInAnyOrder("protein_coding"));
        assertThat(properties.get("design_element").size(), is(33));
    }

    @Test
    public void fetchTooltipProperties() throws Exception {
        // given
        Multimap<String, String> properties = subject.fetchTooltipProperties("ENSMODG00000012671");

        assertThat(properties.size(), is(39));
        assertThat(properties.get("synonym").size(), Matchers.is(5));
        assertThat(properties.get("synonym"), hasItems("Calmbp1", "MCPH5", "ASP"));
        assertThat(properties.get("goterm"), hasItems("oogenesis", "developmental growth", "positive regulation of neuroblast proliferation"));
        assertThat(properties.get("interproterm"), hasItems("Calmodulin-regulated spectrin-associated protein, CH domain", "Armadillo-type fold", "IQ motif, EF-hand binding site"));
    }

    @Test
    public void fetchProperties_goterm() throws SolrServerException {
        //when
        Multimap<String, String> multimap = subject.fetchProperties("ENSG00000109819", new String[]{"goterm"});
        // then
        MatcherAssert.assertThat(multimap.get("goterm"), hasItems("RNA splicing", "cellular response to oxidative stress", "cellular glucose homeostasis"));
    }

    @Test
    public void findPropertyValuesForGeneId() throws SolrServerException {
        assertThat(subject.fetchPropertyValuesForGeneId("ENSG00000179218", "symbol"), hasItem("CALR"));
        assertThat(subject.fetchPropertyValuesForGeneId("ENSMUSG00000029816", "symbol"), hasItem("Gpnmb"));
    }

}
