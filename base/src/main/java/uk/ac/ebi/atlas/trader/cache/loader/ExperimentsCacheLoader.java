package uk.ac.ebi.atlas.trader.cache.loader;

import com.google.common.cache.CacheLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.atlas.experimentimport.ExperimentDTO;
import uk.ac.ebi.atlas.experimentimport.ExperimentDao;
import uk.ac.ebi.atlas.experimentimport.idf.IdfParser;
import uk.ac.ebi.atlas.experimentimport.idf.IdfParserOutput;
import uk.ac.ebi.atlas.model.experiment.Experiment;
import uk.ac.ebi.atlas.model.experiment.ExperimentDesign;
import uk.ac.ebi.atlas.trader.ExperimentDesignParser;

import javax.annotation.Nonnull;

public class ExperimentsCacheLoader<T extends Experiment> extends CacheLoader<String, T> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExperimentsCacheLoader.class);

    private final ExperimentDesignParser experimentDesignParser;
    private final ExperimentDao experimentDao;
    private final ExperimentFactory<T> experimentFactory;
    private final IdfParser idfParser;

    public ExperimentsCacheLoader(ExperimentDesignParser experimentDesignParser,
                                  ExperimentDao experimentDao,
                                  ExperimentFactory<T> experimentFactory,
                                  IdfParser idfParser) {
        this.experimentDesignParser = experimentDesignParser;
        this.experimentDao = experimentDao;
        this.experimentFactory = experimentFactory;
        this.idfParser = idfParser;
    }

    @Override
    public T load(@Nonnull String experimentAccession) {
        LOGGER.info("loading experiment with accession: {}", experimentAccession);

        ExperimentDTO experimentDTO = experimentDao.getExperimentAsAdmin(experimentAccession);
        ExperimentDesign experimentDesign = experimentDesignParser.parse(experimentAccession);
        IdfParserOutput idfParserOutput = idfParser.parse(experimentAccession);

        return experimentFactory.create(experimentDTO, experimentDesign, idfParserOutput);
    }
}
