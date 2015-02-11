///*
// * Copyright 2008-2013 Microarray Informatics Team, EMBL-European Bioinformatics Institute
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// * http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// *
// *
// * For further details of the Gene Expression Atlas project, including source code,
// * downloads and documentation, please see:
// *
// * http://gxa.github.com/gxa
// */
//
//package uk.ac.ebi.atlas.solr.query;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.web.WebAppConfiguration;
//
//import javax.inject.Inject;
//import java.util.List;
//
//import static org.hamcrest.CoreMatchers.hasItem;
//import static org.hamcrest.CoreMatchers.is;
//import static org.junit.Assert.assertThat;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@WebAppConfiguration
//@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
//public class MultiTermSuggestionServiceIT {
//
//    @Inject
//    private MultiTermSuggestionService subject;
//
//    @Test
//    public void apop_suggests_multi_term_suggestion() {
//        List<String> suggestions = subject.fetchMultiTermSuggestions("apop");
//        assertThat(suggestions, hasItem("apoptotic process"));
//    }
//
//    @Test
//    public void apoptotic_p_suggests_apototic_process() {
//        List<String> suggestions = subject.fetchMultiTermSuggestions("apoptotic p");
//        assertThat(suggestions.get(0), is("apoptotic process"));
//    }
//
//    @Test
//    public void mitochondrial_enc() {
//        List<String> suggestions = subject.fetchMultiTermSuggestions("mitochondrial enc");
//        assertThat(suggestions.get(0), is("Mitochondrial-encoded proline-accepting tRNA. [Source:TAIR_LOCUS;Acc:ATMG00350]"));
//    }
//
//    @Test
//    public void ifContainsNonWordCharactersReturnNoSuggestions() {
//        List<String> properties = subject.fetchMultiTermSuggestions("prot%");
//        assertThat(properties.size(), is(0));
//    }
//
//    @Test
//    public void searchTermContainingHyphen() {
//        List<String> properties = subject.fetchMultiTermSuggestions("G-protein");
//        assertThat(properties.size(), is(30));
//        assertThat(properties, hasItem("G-protein coupled receptor signaling pathway"));
//    }
//
//}
