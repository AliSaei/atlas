
package uk.ac.ebi.atlas.web;


import uk.ac.ebi.atlas.model.differential.Regulation;

import javax.validation.constraints.Min;

public class DifferentialRequestPreferences extends ExperimentPageRequestPreferences {

    public static final double DEFAULT_CUTOFF = 0.05d;

    public static final double DEFAULT_FOLD_CHANGE_CUTOFF = 1d;

    private Regulation regulation = Regulation.UP_DOWN;

    @Min(value = 0, message = "Log2-fold change cut off is an absolute amount, and so must be greater than zero")
    private double foldChangeCutOff = DEFAULT_FOLD_CHANGE_CUTOFF;

    @Override
    public double getDefaultCutoff() {
        return DEFAULT_CUTOFF;
    }

    // exposed as JavaBean getter so JSP can read it
    public double getDefaultFoldChangeCutOff() {
        return DEFAULT_FOLD_CHANGE_CUTOFF;
    }

    public Regulation getRegulation() {
        return regulation;
    }

    public void setRegulation(Regulation regulation) {
        this.regulation = regulation;
    }

    public Double getFoldChangeCutOff() {
        return foldChangeCutOff;
    }

    public void setFoldChangeCutOff(Double foldChangeCutOff) {
        // handle no value case, eg: when textbox is left empty
        if (foldChangeCutOff != null) {
            this.foldChangeCutOff = foldChangeCutOff;
        }
    }
}