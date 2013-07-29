package uk.ac.ebi.atlas.commands.context;

import com.google.common.base.Objects;
import uk.ac.ebi.atlas.model.differential.Contrast;
import uk.ac.ebi.atlas.model.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.model.differential.Regulation;
import uk.ac.ebi.atlas.web.DifferentialRequestPreferences;

public class DifferentialRequestContext<T extends DifferentialExperiment> extends RequestContext<Contrast, DifferentialRequestPreferences> {

    private T experiment;

    public T getExperiment() {
        return experiment;
    }

    void setExperiment(T experiment) {
        this.experiment = experiment;
    }

    public Regulation getRegulation() {
        return getRequestPreferences().getRegulation();
    }

    @Override
    protected void setRequestPreferences(DifferentialRequestPreferences requestPreferences) {
        super.setRequestPreferences(requestPreferences);
    }

    @Override
    protected DifferentialRequestPreferences getRequestPreferences() {
        return super.getRequestPreferences();
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this.getClass())
                .addValue(super.toString())
                .add("regulation", getRegulation())
                .add("experiment", getExperiment())
                .toString();
    }

}
