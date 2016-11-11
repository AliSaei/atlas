package uk.ac.ebi.atlas.experimentimport;

import com.google.common.collect.Sets;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.model.ExperimentType;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayExperimentConfiguration;
import uk.ac.ebi.atlas.trader.ConfigurationTrader;

import javax.inject.Inject;
import javax.inject.Named;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.Properties;
import java.util.Set;

import static com.google.common.base.Preconditions.checkState;

@Named
@Scope("prototype")
public class ExperimentChecker {

    private Properties configurationProperties;

    private ConfigurationTrader configurationTrader;

    @Inject
    public ExperimentChecker(@Named("configuration") Properties configurationProperties,
                             ConfigurationTrader configurationTrader) {
        this.configurationProperties = configurationProperties;
        this.configurationTrader = configurationTrader;
    }

    public void checkAllFiles(String experimentAccession, ExperimentType experimentType) {

        // every experiment should have analysis methods file
        checkFileExistsAndIsReadable("experiment.analysis-method.path.template", experimentAccession);

        switch (experimentType) {
            case RNASEQ_MRNA_BASELINE:
            case PROTEOMICS_BASELINE:
                checkBaselineFiles(experimentAccession);
                break;
            case RNASEQ_MRNA_DIFFERENTIAL:
                checkDifferentialFiles(experimentAccession);
                break;
            case MICROARRAY_1COLOUR_MRNA_DIFFERENTIAL:
            case MICROARRAY_1COLOUR_MICRORNA_DIFFERENTIAL:
                checkMicroarrayFiles(experimentAccession);
                break;
            case MICROARRAY_2COLOUR_MRNA_DIFFERENTIAL:
                checkTwoColourFiles(experimentAccession);
                break;
            default:
                throw new IllegalStateException("The specified experiment type is not supported.");
        }
    }


    void checkBaselineFiles(String experimentAccession) {
        Set<String> baselineExperimentPathTemplates =
                Sets.newHashSet("experiment.magetab.path.template", "experiment.factors.path.template");

        checkFilesAreAllPresentAndWeCanReadThem(baselineExperimentPathTemplates, experimentAccession);
    }

    void checkDifferentialFiles(String experimentAccession) {
        Set<String> differentialExperimentPathTemplates =
                Sets.newHashSet("diff.experiment.data.path.template", "diff.experiment.raw-counts.path.template");

        checkFilesAreAllPresentAndWeCanReadThem(differentialExperimentPathTemplates, experimentAccession);
    }

    void checkMicroarrayFiles(String experimentAccession) {
        MicroarrayExperimentConfiguration microarrayConfiguration =
                        configurationTrader.getMicroarrayExperimentConfiguration(experimentAccession);
        for (String arrayDesign : microarrayConfiguration.getArrayDesignAccessions()) {
            Set<String> arrayDesignDependentPathTemplates = Sets.newHashSet("microarray.experiment.data.path.template", "microarray.normalized.data.path.template");
            checkFilesAreAllPresentAndWeCanReadThem(arrayDesignDependentPathTemplates, experimentAccession, arrayDesign);
        }
    }

    void checkTwoColourFiles(String experimentAccession) {
        MicroarrayExperimentConfiguration microarrayExperimentConfiguration =
                configurationTrader.getMicroarrayExperimentConfiguration(experimentAccession);

        Set<String> arrayDesignDependentPathTemplates = Sets.newHashSet("microarray.experiment.data.path.template", "microarray.log-fold-changes.data.path.template");
        for (String arrayDesign : microarrayExperimentConfiguration.getArrayDesignAccessions()) {
            checkFilesAreAllPresentAndWeCanReadThem(arrayDesignDependentPathTemplates, experimentAccession, arrayDesign);
        }
    }

    private void checkFilesAreAllPresentAndWeCanReadThem(Collection<String> pathTemplatePropertyKeys, String... pathArguments) {
        for (String pathTemplatePropertyKey : pathTemplatePropertyKeys) {
            checkFileExistsAndIsReadable(pathTemplatePropertyKey, pathArguments);
        }
    }

    void checkFileExistsAndIsReadable(String pathTemplatePropertyKey, String... pathArguments) {
        String pathTemplate = configurationProperties.getProperty(pathTemplatePropertyKey);
        Path path = Paths.get(MessageFormat.format(pathTemplate, (Object[])pathArguments));
        checkState(Files.isReadable(path), "Required file can not be read: " + path.toAbsolutePath().toString());
    }

    public void checkConfigurationFile(String accession) {
        checkFileExistsAndIsReadable("experiment.configuration.path.template", accession);
    }

}