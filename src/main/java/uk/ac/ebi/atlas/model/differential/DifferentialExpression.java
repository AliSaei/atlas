package uk.ac.ebi.atlas.model.differential;

import com.google.common.base.Objects;
import uk.ac.ebi.atlas.model.Expression;

public class DifferentialExpression implements Expression {
    private static final double SMALLEST_P_VALUE_ALLOWED = 1E-125;
    public static final double WEAKEST_LEVEL = 0;

    private double pValue;

    private double foldChange;

    private Contrast contrast;

    // If pValue is smaller than minim allowed value, treat it as 0D. This checks this condition when reading
    //  from the tsv file

    public DifferentialExpression(double pValue, double foldChange) {
        this.pValue = (pValue < SMALLEST_P_VALUE_ALLOWED) ? 0D : pValue;
        this.foldChange = foldChange;
    }

    public DifferentialExpression(double pValue, double foldChange, Contrast contrast) {
        this(pValue, foldChange);
        this.contrast = contrast;
    }

    public double getPValue() {
        return pValue;
    }

    public double getFoldChange() {
        return foldChange;
    }

    public boolean isRegulatedLike(Regulation regulation) {
        return Regulation.UP_DOWN.equals(regulation)
                || isLikeUpRegulation(regulation)
                || isLikeDownRegulation(regulation);
    }

    private boolean isLikeUpRegulation(Regulation regulation) {
        return Regulation.UP.equals(regulation) && isOverExpressed();
    }

    private boolean isLikeDownRegulation(Regulation regulation) {
        return Regulation.DOWN.equals(regulation) && isUnderExpressed();
    }

    public Contrast getContrast() {
        return contrast;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if ((null == object) || (getClass() != object.getClass())) {
            return false;
        }

        DifferentialExpression other = (DifferentialExpression) object;

        return Objects.equal(foldChange, other.foldChange) &&
                Objects.equal(pValue, other.pValue) &&
                Objects.equal(contrast, other.contrast);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(pValue, foldChange, contrast);
    }

    @Override
    public double getLevel() {
        return getFoldChange();
    }

    @Override
    public boolean isKnown() {
        return true;
    }

    public boolean isOverExpressed() {
        return foldChange > 0;
    }

    public boolean isUnderExpressed() {
        return foldChange < 0;
    }

    public double getAbsoluteFoldChange() {
        return Math.abs(foldChange);
    }
}