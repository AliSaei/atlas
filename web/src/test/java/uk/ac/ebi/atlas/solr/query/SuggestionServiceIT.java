package uk.ac.ebi.atlas.solr.query;

import com.google.common.base.Joiner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.inject.Inject;
import java.util.List;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:solrContextIT.xml", "classpath:oracleContext.xml"})
public class SuggestionServiceIT {

    @Inject
    private SuggestionService suggestionService;

    @Test
    public void apop() {
        List<String> suggestions = suggestionService.fetchTopSuggestions("apop", null);
        System.out.println(Joiner.on(",\n").join(suggestions));
        assertThat(suggestions, hasItem("\"apoptotic process\""));
    }

}
