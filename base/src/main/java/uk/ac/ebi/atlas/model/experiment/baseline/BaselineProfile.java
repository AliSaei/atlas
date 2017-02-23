package uk.ac.ebi.atlas.model.experiment.baseline;

import com.google.common.base.Objects;
import org.apache.commons.collections.CollectionUtils;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.Profile;
import uk.ac.ebi.atlas.profiles.baseline.BaselineExpressionLevelRounder;

import java.util.HashSet;
import java.util.Set;

import static com.google.common.base.Preconditions.checkArgument;
import static java.lang.Math.max;
import static java.lang.Math.min;

public class BaselineProfile extends Profile<AssayGroup, BaselineExpression> {
    private static final double MIN_LEVEL = 0D;
    private double maxExpressionLevel = 0;
    private double minExpressionLevel = Double.MAX_VALUE;

    public BaselineProfile(String geneId, String geneName) {
        super(geneId, geneName);
    }

    public double getMaxExpressionLevel() {
        return maxExpressionLevel;
    }

    public double getMinExpressionLevel() {
        return minExpressionLevel;
    }

    public double getAverageExpressionLevelOn(Set<AssayGroup> assayGroups) {
        checkArgument(!CollectionUtils.isEmpty(assayGroups), "This method must be invoked with a non empty set of conditions");

        double expressionLevel = 0D;

        for (AssayGroup condition : assayGroups) {
            Double level = getExpressionLevel(condition);
            if (level != null) {
                expressionLevel += level;
            }
        }
        return expressionLevel / assayGroups.size();
    }

    public double getMaxExpressionLevelOn(Set<AssayGroup> assayGroups) {
        checkArgument(!CollectionUtils.isEmpty(assayGroups), "assayGroups set is supposed to be not empty");

        double expressionLevel = MIN_LEVEL;

        for (AssayGroup condition : assayGroups) {
            Double level = getExpressionLevel(condition);
            if (level != null) {
                expressionLevel = max(expressionLevel, level);
            }
        }
        return expressionLevel;
    }

    public Set<AssayGroup> getAssayGroupsWithExpressionLevelAtLeast(double threshold){
        Set<AssayGroup> result = new HashSet<>();
        for(AssayGroup condition : expressionsByCondition.keySet()){
            Double level = getExpressionLevel(condition);
            if (level != null && level >= threshold) {
                result.add(condition);
            }
        }
        return result;
    }


    // add the expression levels of another profile to this one
    public BaselineProfile sumProfile(BaselineProfile otherProfile) {
        for (AssayGroup assayGroup : otherProfile.getConditions()) {
            BaselineExpression otherExpression = otherProfile.getExpression(assayGroup);
            BaselineExpression thisExpression = getExpression(assayGroup);

            if (thisExpression == null) {
                add(assayGroup, otherExpression);
            } else {
                add(assayGroup, new BaselineExpression(thisExpression.getLevel() + otherExpression.getLevel()));
            }
        }
        return this;
    }

    // divide all expression levels by foldFactor
    public BaselineProfile foldProfile(int foldFactor) {
        resetMaxMin();
        for (AssayGroup assayGroup : getConditions()) {
            BaselineExpression expression = getExpression(assayGroup);
            double foldLevel = fold(expression.getLevel(), foldFactor);
            BaselineExpression foldedExpression =
                    new BaselineExpression(foldLevel, expression.getFactorGroup());
            add(assayGroup, foldedExpression);
        }
        return this;
    }

    private static double fold(double value, int foldFactor) {
        return BaselineExpressionLevelRounder.round(value / foldFactor);
    }

    private void resetMaxMin() {
        maxExpressionLevel = 0;
        minExpressionLevel = Double.MAX_VALUE;
    }
    @Override
    protected void updateStateAfterAddingExpression(BaselineExpression geneExpression) {
        maxExpressionLevel = max(maxExpressionLevel, geneExpression.getLevel());
        minExpressionLevel = min(minExpressionLevel, geneExpression.getLevel());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OldBaselineProfile)) return false;
        OldBaselineProfile that = (OldBaselineProfile) o;
        return super.equals(that) &&
                Double.compare(that.getMaxExpressionLevel(), getMaxExpressionLevel()) == 0 &&
                Double.compare(that.getMinExpressionLevel(), getMinExpressionLevel()) == 0 ;

    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getMaxExpressionLevel(), getMinExpressionLevel(), super.hashCode());
    }
}
