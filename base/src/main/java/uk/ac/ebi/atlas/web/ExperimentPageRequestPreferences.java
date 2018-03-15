package uk.ac.ebi.atlas.web;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableSet;
import org.hibernate.validator.constraints.Range;
import uk.ac.ebi.atlas.model.ExpressionUnit;
import uk.ac.ebi.atlas.search.SemanticQuery;

import java.util.Set;

public abstract class ExperimentPageRequestPreferences<U extends ExpressionUnit> {
    public static final double VERY_SMALL_NON_ZERO_VALUE = Double.MIN_VALUE;
    public static final int DEFAULT_NUMBER_OF_RANKED_GENES = 50;

    private static final int HEATMAP_SIZE_MIN = 1;
    private static final int HEATMAP_SIZE_MAX = 1000;

    @Range(min = HEATMAP_SIZE_MIN, max = HEATMAP_SIZE_MAX)
    private int heatmapMatrixSize = DEFAULT_NUMBER_OF_RANKED_GENES;

    private SemanticQuery geneQuery = getDefaultGeneQuery();
    private double cutoff = getDefaultCutoff();
    private Set<String> selectedColumnIds = ImmutableSet.of();
    private boolean specific = true;

    protected ExperimentPageRequestPreferences() {
    }

    public void setSelectedColumnIds(Set<String> selectedColumnIds) {
        if (selectedColumnIds != null) {
            this.selectedColumnIds = ImmutableSet.copyOf(selectedColumnIds);
        }
    }

    public Set<String> getSelectedColumnIds(){
        return selectedColumnIds;
    }

    public void setHeatmapMatrixSize(int heatmapMatrixSize) {
        this.heatmapMatrixSize = heatmapMatrixSize;
    }

    public int getHeatmapMatrixSize() {
        return this.heatmapMatrixSize;
    }

    public void setGeneQuery(SemanticQuery geneQuery) {
        this.geneQuery = geneQuery;
    }

    public SemanticQuery getGeneQuery() {
        return this.geneQuery;
    }

    protected SemanticQuery getDefaultGeneQuery() {
        return SemanticQuery.create();
    }

    public void setCutoff(double cutoff) {
        this.cutoff = cutoff;
    }

    public double getCutoff() {
        return this.cutoff;
    }

    public abstract double getDefaultCutoff();

    public void setSpecific(boolean specific) {
        this.specific = specific;
    }

    public boolean isSpecific() {
        return this.specific;
    }

    public String toString() {
        return MoreObjects.toStringHelper(this.getClass())
                .addValue(super.toString())
                .add("cutoff", cutoff)
                .add("specific", specific)
                .toString();
    }

    public abstract U getUnit();

    /*
    Used for equality of cache keys.
    Currently:
    - Kryo serialized files
    - Histograms
    This combined with experiment accession should 1-1 map to a data file. The design is unclear and a bit of a wart.
    Sorry. :)

    When we serialize files, calculate histograms, or request everything for download, we mean "everything but zero"
    On the other hand we also offer an option in the UI to explicitly ask for zeros.
    We can't afford to kryo serialize these - the format is only better because we take advantage of data sparsity.
    If someone chooses to ask for zeros we need to go through the original text file.
     */
    public String serializationShortString() {
        return getClass().getSimpleName() + (cutoff == 0.0d);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExperimentPageRequestPreferences that = (ExperimentPageRequestPreferences) o;
        return serializationShortString().equals(that.serializationShortString());
    }

    @Override
    public int hashCode() {
        return serializationShortString().hashCode();
    }
}
