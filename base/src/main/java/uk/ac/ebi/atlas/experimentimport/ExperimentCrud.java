package uk.ac.ebi.atlas.experimentimport;

import com.google.common.base.Preconditions;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.atlas.controllers.ResourceNotFoundException;
import uk.ac.ebi.atlas.experimentimport.condensedSdrf.CondensedSdrfParser;
import uk.ac.ebi.atlas.experimentimport.condensedSdrf.CondensedSdrfParserOutput;
import uk.ac.ebi.atlas.experimentimport.experimentdesign.ExperimentDesignFileWriterService;
import uk.ac.ebi.atlas.experimentimport.idf.IdfParser;
import uk.ac.ebi.atlas.experimentimport.idf.IdfParserOutput;
import uk.ac.ebi.atlas.model.experiment.ExperimentConfiguration;
import uk.ac.ebi.atlas.model.experiment.ExperimentDesign;
import uk.ac.ebi.atlas.model.experiment.ExperimentType;
import uk.ac.ebi.atlas.trader.ConfigurationTrader;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkNotNull;

// Inserts experiment in (scxa_)experiment table and writes the experiment design file to expdesign/
public class ExperimentCrud {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExperimentCrud.class);

    private final ExperimentDao experimentDao;
    private final ExperimentChecker experimentChecker;
    private final ExperimentDesignFileWriterService experimentDesignFileWriterService;
    private final CondensedSdrfParser condensedSdrfParser;
    private final IdfParser idfParser;
    private final ConfigurationTrader configurationTrader;

    public ExperimentCrud(ExperimentDao experimentDao,
                          ExperimentChecker experimentChecker,
                          CondensedSdrfParser condensedSdrfParser,
                          IdfParser idfParser,
                          ExperimentDesignFileWriterService experimentDesignFileWriterService,
                          ConfigurationTrader configurationTrader) {

        this.experimentDao = experimentDao;
        this.experimentChecker = experimentChecker;
        this.condensedSdrfParser = condensedSdrfParser;
        this.idfParser = idfParser;
        this.experimentDesignFileWriterService = experimentDesignFileWriterService;
        this.configurationTrader = configurationTrader;
    }

    public UUID importExperiment(String experimentAccession, boolean isPrivate) {
        checkNotNull(experimentAccession);

        Pair<ExperimentConfiguration, CondensedSdrfParserOutput> files = loadAndValidateFiles(experimentAccession);
        ExperimentConfiguration experimentConfiguration = files.getLeft();
        CondensedSdrfParserOutput condensedSdrfParserOutput = files.getRight();

        IdfParserOutput idfParserOutput = idfParser.parse(experimentAccession);

        Optional<String> accessKey = fetchExperimentAccessKey(experimentAccession);

        ExperimentDTO experimentDTO = ExperimentDTO.create(
                condensedSdrfParserOutput,
                idfParserOutput,
                condensedSdrfParserOutput
                        .getExperimentDesign()
                        .getSpeciesForAssays(
                                experimentConfiguration.getAssayGroups().stream()
                                        .flatMap(assayGroup -> assayGroup.assaysAnalyzedForThisDataColumn().stream())
                                        .collect(Collectors.toSet())),
                isPrivate);

        if (accessKey.isPresent()) {
            deleteExperiment(experimentAccession);
        }

        UUID accessKeyUuid = accessKey.map(UUID::fromString).orElseGet(UUID::randomUUID);
        experimentDao.addExperiment(experimentDTO, accessKeyUuid);

        updateWithNewExperimentDesign(condensedSdrfParserOutput.getExperimentDesign(), experimentDTO);

        return accessKeyUuid;
    }

    public UUID importSingleCellExperiment(String experimentAccession, boolean isPrivate) {
        checkNotNull(experimentAccession);

        Optional<String> accessKey = fetchExperimentAccessKey(experimentAccession);

        CondensedSdrfParserOutput condensedSdrfParserOutput =
                condensedSdrfParser.parse(experimentAccession, ExperimentType.SINGLE_CELL_RNASEQ_MRNA_BASELINE);

        IdfParserOutput idfParserOutput = idfParser.parse(experimentAccession);

        ExperimentDTO experimentDTO =
                ExperimentDTO.create(
                        condensedSdrfParserOutput,
                        idfParserOutput,
                        condensedSdrfParserOutput.getSpecies(),
                        isPrivate);

        if (accessKey.isPresent()) {
            deleteExperiment(experimentAccession);
        }

        UUID accessKeyUuid = accessKey.map(UUID::fromString).orElseGet(UUID::randomUUID);
        experimentDao.addExperiment(experimentDTO, accessKeyUuid);

        updateWithNewExperimentDesign(condensedSdrfParserOutput.getExperimentDesign(), experimentDTO);

        return accessKeyUuid;
    }

    private Optional<String> fetchExperimentAccessKey(String experimentAccession) {
        try {
            ExperimentDTO experiment = findExperiment(experimentAccession);
            return Optional.of(experiment.getAccessKey());
        } catch (ResourceNotFoundException e) {
            return Optional.empty();
        }
    }

    public void deleteExperiment(String experimentAccession) {
        ExperimentDTO experimentDTO = findExperiment(experimentAccession);
        checkNotNull(experimentDTO, MessageFormat.format("Experiment not found: {0}", experimentAccession));

        experimentDao.deleteExperiment(experimentDTO.getExperimentAccession());
    }

    public ExperimentDTO findExperiment(String experimentAccession) {
        return experimentDao.getExperimentAsAdmin(experimentAccession);
    }

    public List<ExperimentDTO> findAllExperiments() {
        return experimentDao.getAllExperimentsAsAdmin();
    }

    public void makeExperimentPrivate(String experimentAccession) {
        setExperimentPrivacyStatus(experimentAccession, true);
    }

    public void makeExperimentPublic(String experimentAccession) {
        setExperimentPrivacyStatus(experimentAccession, false);
    }

    private void setExperimentPrivacyStatus(String experimentAccession, boolean newPrivacyStatus) {
        ExperimentDesign newDesign = loadAndValidateFiles(experimentAccession).getRight().getExperimentDesign();
        experimentDao.setExperimentPrivacyStatus(experimentAccession, newPrivacyStatus);
        ExperimentDTO experimentDTO = experimentDao.getExperimentAsAdmin(experimentAccession);
        Preconditions.checkState(
                newPrivacyStatus == experimentDTO.isPrivate(),
                "Failed to change experiment status in the db! (?)");

        updateWithNewExperimentDesign(newDesign, experimentDTO);
    }

    public void updateExperimentDesign(String experimentAccession) {
        updateWithNewExperimentDesign(
                loadAndValidateFiles(experimentAccession).getRight().getExperimentDesign(),
                experimentDao.getExperimentAsAdmin(experimentAccession)
        );
    }

    // Same as updateExperimentDesign but bypasses checks to validate files (e.g. configuration XML)
    public void updateSingleCellExperimentDesign(String experimentAccession) {
        CondensedSdrfParserOutput condensedSdrfParserOutput =
                condensedSdrfParser.parse(experimentAccession, ExperimentType.SINGLE_CELL_RNASEQ_MRNA_BASELINE);

        updateWithNewExperimentDesign(
                condensedSdrfParserOutput.getExperimentDesign(),
                experimentDao.getExperimentAsAdmin(experimentAccession));
    }

    private void updateWithNewExperimentDesign(ExperimentDesign newDesign, ExperimentDTO experimentDTO) {
        try {
            experimentDesignFileWriterService.writeExperimentDesignFile(experimentDTO.getExperimentAccession(),
                    experimentDTO.getExperimentType(), newDesign);
            LOGGER.info("updated design for experiment {}", experimentDTO.getExperimentAccession());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private Pair<ExperimentConfiguration, CondensedSdrfParserOutput> loadAndValidateFiles(String experimentAccession) {
        ExperimentConfiguration experimentConfiguration =
                configurationTrader.getExperimentConfiguration(experimentAccession);
        experimentChecker.checkAllFiles(experimentAccession, experimentConfiguration.getExperimentType());

        CondensedSdrfParserOutput condensedSdrfParserOutput =
                condensedSdrfParser.parse(experimentAccession, experimentConfiguration.getExperimentType());

        new ExperimentFilesCrossValidator(experimentConfiguration, condensedSdrfParserOutput.getExperimentDesign())
                .validate();

        return Pair.of(experimentConfiguration, condensedSdrfParserOutput);
    }

    public void checkFiles(String experimentAccession) throws RuntimeException {
        loadAndValidateFiles(experimentAccession);
    }
}
