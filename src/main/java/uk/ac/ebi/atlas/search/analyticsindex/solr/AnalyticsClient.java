package uk.ac.ebi.atlas.search.analyticsindex.solr;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;

@Named
public class AnalyticsClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(AnalyticsClient.class);

    private SolrClient analyticsSolrClient;

    @Inject
    public AnalyticsClient(@Qualifier("analyticsSolrClient") SolrClient analyticsSolrClient) {
        this.analyticsSolrClient = analyticsSolrClient;
    }

    public QueryResponse query(SolrQuery solrQuery) {
        try {
            return analyticsSolrClient.query(solrQuery);
        } catch (SolrServerException | IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw new SolrException(SolrException.ErrorCode.UNKNOWN, e);
        }
    }
}