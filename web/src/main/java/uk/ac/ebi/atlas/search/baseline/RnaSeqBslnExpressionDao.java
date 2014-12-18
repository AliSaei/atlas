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

package uk.ac.ebi.atlas.search.baseline;

import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.mutable.MutableInt;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import uk.ac.ebi.atlas.profiles.baseline.BaselineExpressionLevelRounder;
import uk.ac.ebi.atlas.search.DatabaseQuery;
import uk.ac.ebi.atlas.search.OracleObjectFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

@Named
@Scope("prototype")
public class RnaSeqBslnExpressionDao {

    private static final Logger LOGGER = Logger.getLogger(RnaSeqBslnExpressionDao.class);

    private final JdbcTemplate jdbcTemplate;

    private final OracleObjectFactory oracleObjectFactory;

    private final BaselineExpressionLevelRounder baselineExpressionLevelRounder;

    @Inject
    public RnaSeqBslnExpressionDao(JdbcTemplate jdbcTemplate, OracleObjectFactory oracleObjectFactory, BaselineExpressionLevelRounder baselineExpressionLevelRounder) {
        this.jdbcTemplate = jdbcTemplate;
        this.oracleObjectFactory = oracleObjectFactory;
        this.baselineExpressionLevelRounder = baselineExpressionLevelRounder;
    }

    //TODO: allow fetching by species
    public ImmutableList<RnaSeqBslnExpression> fetchAverageExpressionByExperimentAssayGroup(final Collection<String> geneIds) {
        if (geneIds.isEmpty()) {
            return ImmutableList.of();
        }

        LOGGER.debug(String.format("fetchAverageExpressionByExperimentAssayGroup for %s genes", geneIds.size()));

        Stopwatch stopwatch = Stopwatch.createStarted();

        try {

            DatabaseQuery<Object> baselineExpressionQuery = buildSelect(geneIds);

            final ImmutableList.Builder<RnaSeqBslnExpression> builder = ImmutableList.builder();

            final MutableInt numberOfGenesExpressedInCurrentExperiment = new MutableInt(0);

            jdbcTemplate.query(baselineExpressionQuery.getQuery(),
                    new RowCallbackHandler() {
                        @Override
                        public void processRow(ResultSet rs) throws SQLException {
                            String experimentAccession = rs.getString(RnaSeqBslnQueryBuilder.EXPERIMENT);
                            String assayGroupId = rs.getString(RnaSeqBslnQueryBuilder.ASSAY_GROUP_ID);

                            if (assayGroupId == null) {
                                // every-time we see a null assaygroupid, this is the beginning of rows for another experiment
                                // and this row will contain the experiment level totals
                                double numberOfGenesExpressed = rs.getInt(RnaSeqBslnQueryBuilder.NUMBER_GENES_EXPRESSED);
                                numberOfGenesExpressedInCurrentExperiment.setValue(numberOfGenesExpressed);
                                return;
                            }

                            double expression = baselineExpressionLevelRounder.round(rs.getDouble(RnaSeqBslnQueryBuilder.EXPRESSION) / numberOfGenesExpressedInCurrentExperiment.intValue());
                            RnaSeqBslnExpression bslnExpression = RnaSeqBslnExpression.create(experimentAccession, assayGroupId, expression);

                            builder.add(bslnExpression);
                        }
                    },
                    baselineExpressionQuery.getParameters().toArray());

            ImmutableList<RnaSeqBslnExpression> results = builder.build();

            stopwatch.stop();
            LOGGER.debug(String.format("fetchAverageExpressionByExperimentAssayGroup returned %s results in %.2f seconds", results.size(), stopwatch.elapsed(TimeUnit.MILLISECONDS) / 1000D));
            return results;

        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw e;
        }

    }

    DatabaseQuery<Object> buildSelect(Collection<String> geneIds) {
        RnaSeqBslnQueryBuilder builder = new RnaSeqBslnQueryBuilder();
        builder.withGeneIds(oracleObjectFactory.createOracleArrayForIdentifiers(geneIds));
        return builder.build();
    }

}
