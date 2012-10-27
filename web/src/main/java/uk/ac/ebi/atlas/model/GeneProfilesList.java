package uk.ac.ebi.atlas.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.SortedSet;
import java.util.TreeSet;

import static com.google.common.base.Preconditions.checkArgument;

public class GeneProfilesList extends ArrayList<GeneProfile> {
    
    private static final long serialVersionUID = -1678371004778942235L;

    public GeneProfilesList(Collection<GeneProfile> collection) {
        super(collection);
    }

    public GeneProfilesList getTop(int size) {
        return subList(0, size);
    }

    public Double getExpressionLevel(String geneId, String organismPart) {
        for (GeneProfile geneProfile : this) {
            if (geneId.equalsIgnoreCase(geneProfile.getGeneId())){
                return geneProfile.getExpressionLevel(organismPart);
            }
        }
        return null;
    }

    @Override
    public GeneProfilesList subList(int fromIndex, int toIndex) {
        checkArgument(toIndex >= 0, "Upper index value must be larger than 0");
        if (toIndex > this.size()) {
            return this;
        }
        return new GeneProfilesList(super.subList(fromIndex, toIndex));
    }

    public double getMaxExpressionLevel() {
        double maxExpressionLevel = 0;
        for (GeneProfile geneProfile : this) {
            if (maxExpressionLevel < geneProfile.getMaxExpressionLevel()) {
                maxExpressionLevel = geneProfile.getMaxExpressionLevel();
            }
        }
        return maxExpressionLevel;
    }

    public double getMinExpressionLevel() {
        double minExpressionLevel = Double.MAX_VALUE;
        for (GeneProfile geneProfile : this) {
            if (geneProfile.getMinExpressionLevel() < minExpressionLevel) {
                minExpressionLevel = geneProfile.getMinExpressionLevel();
            }
        }
        return minExpressionLevel;
    }

    public Integer getTotalResultCount() {
        return size();
    }

    public SortedSet<String> getAllOrganismParts() {
        SortedSet<String> allOrganismParts = new TreeSet<>();
        for (GeneProfile geneProfile : this) {
            allOrganismParts.addAll(geneProfile.getOrganismParts());
        }
        return allOrganismParts;
    }
}
