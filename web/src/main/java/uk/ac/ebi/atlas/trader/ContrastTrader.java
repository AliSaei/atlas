package uk.ac.ebi.atlas.trader;

import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.model.Experiment;
import uk.ac.ebi.atlas.model.differential.Contrast;
import uk.ac.ebi.atlas.model.differential.DifferentialExperiment;

import javax.inject.Inject;
import javax.inject.Named;

@Named()
@Scope("singleton")
public class ContrastTrader {

    private ExperimentTrader experimentTrader;

    @Inject
    public ContrastTrader(ExperimentTrader experimentTrader) {
        this.experimentTrader = experimentTrader;
    }

    public Contrast getContrast(String experimentAccession, String contrastId) {
        Experiment experiment = experimentTrader.getPublicExperiment(experimentAccession);
        //ToDo: we have to class cast here, to fix we need to have a common type for all diff experiments
        return ((DifferentialExperiment)experiment).getContrast(contrastId);
    }
}
