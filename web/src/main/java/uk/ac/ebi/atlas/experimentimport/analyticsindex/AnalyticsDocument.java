package uk.ac.ebi.atlas.experimentimport.analyticsindex;

import org.apache.solr.client.solrj.beans.Field;
import uk.ac.ebi.atlas.model.ExperimentType;
import uk.ac.ebi.atlas.model.differential.Regulation;

import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;


public class AnalyticsDocument {

    @Field("bioentityIdentifier")
    String bioentityIdentifier;

    @Field
    String species;

    @Field
    String kingdom;

    @Field
    String experimentAccession;

    //TODO: add baseline boolean

    ExperimentType experimentType;

    @Field("experimentType")
    String experimentTypeAsString;

    @Field
    String defaultQueryFactorType;

    @Field
    String identifierSearch;

    @Field
    String conditionsSearch;

    @Field
    String assayGroupId;

    @Field
    Double expressionLevel;

    @Field
    String contrastId;

    @Field
    Set<String> factors;

    @Field
    Integer numReplicates;

    @Field
    Double foldChange;

    @Field
    Double pValue;

    @Field
    Double tStatistics;

    @Field
    Regulation regulation;

    public String getBioentityIdentifier() {
        return bioentityIdentifier;
    }

    public String getSpecies() {
        return species;
    }

    public String getKingdom() {
        return kingdom;
    }

    public String getExperimentAccession() {
        return experimentAccession;
    }

    public ExperimentType getExperimentType() {
        return experimentType;
    }

    public String getDefaultQueryFactorType() {
        return defaultQueryFactorType;
    }

    public String getIdentifierSearch() {
        return identifierSearch;
    }

    public String getConditionsSearch() {
        return conditionsSearch;
    }

    public String getAssayGroupId() {
        return assayGroupId;
    }

    public Double getExpressionLevel() {
        return expressionLevel;
    }

    public String getContrastId() {
        return contrastId;
    }

    public Set<String> getFactors() {
        return factors;
    }

    public Integer getNumReplicates() {
        return numReplicates;
    }

    public Double getFoldChange() {
        return foldChange;
    }

    public Double getPValue() {
        return pValue;
    }

    public Double getTStatistics() {
        return tStatistics;
    }

    public Regulation getRegulation() {
        return regulation;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private final AnalyticsDocument build;

        public Builder() {
            build = new AnalyticsDocument();
        }

        public AnalyticsDocument build() {
            checkNotNull(build.bioentityIdentifier, "missing bioentityIdentifier");
            checkNotNull(build.species, "missing species");
            checkNotNull(build.kingdom, "missing kingdom");
            checkNotNull(build.experimentAccession, "missing experimentAccession");
            checkNotNull(build.experimentType, "missing experimentType");
            checkNotNull(build.conditionsSearch, "missing conditionsSearch");

            if (build.experimentType.isBaseline()) {
                checkNotNull(build.assayGroupId, "missing assayGroupId for baseline experiment");
                checkNotNull(build.expressionLevel, "missing expression level for baseline experiment");
            } else if (build.experimentType.isDifferential()) {
                checkNotNull(build.contrastId, "missing contrastId for differential experiment");
                checkNotNull(build.factors, "missing factors for differential experiment");
                checkNotNull(build.numReplicates, "missing numReplicates for differential experiment");
                checkNotNull(build.foldChange, "missing foldChange for differential experiment");
                checkNotNull(build.pValue, "missing pValue for differential experiment");
//                checkNotNull(build.tStatistics, "missing tStatistics for differential experiment");
                checkNotNull(build.regulation, "missing regulation for differential experiment");
            }

            build.experimentTypeAsString = build.experimentType.getDescription();
            return build;
        }

        public Builder bioentityIdentifier(String bioentityIdentifier) {
            build.bioentityIdentifier = bioentityIdentifier;
            return this;
        }

        public Builder species(String species) {
            build.species = species;
            return this;
        }

        public Builder kingdom(String kingdom) {
            build.kingdom = kingdom;
            return this;
        }

        public Builder experimentAccession(String experimentAccession) {
            build.experimentAccession = experimentAccession;
            return this;
        }

        public Builder experimentType(ExperimentType experimentType) {
            build.experimentType = experimentType;
            return this;
        }

        public Builder defaultQueryFactorType(String defaultQueryFactorType) {
            build.defaultQueryFactorType = defaultQueryFactorType;
            return this;
        }

        public Builder identifierSearch(String identifierSearch) {
            build.identifierSearch = identifierSearch;
            return this;
        }

        public Builder conditionsSearch(String conditionsSearch) {
            build.conditionsSearch = conditionsSearch;
            return this;
        }

        public Builder assayGroupId(String assayGroupId) {
            build.assayGroupId = assayGroupId;
            return this;
        }

        public Builder expressionLevel(double expressionLevel) {
            build.expressionLevel = expressionLevel;
            return this;
        }

        public Builder contrastId(String contrastId) {
            build.contrastId = contrastId;
            return this;
        }

        public Builder factors(Set<String> factor) {
            build.factors = factor;
            return this;
        }

        public Builder numReplicates(int numReplicates) {
            build.numReplicates = numReplicates;
            return this;
        }

        public Builder foldChange(double foldChange) {
            build.foldChange = foldChange;
            build.regulation = Regulation.valueOf(foldChange);
            return this;
        }

        public Builder pValue(double pValue) {
            build.pValue = pValue;
            return this;
        }

        public Builder tStatistics(double tStatistics) {
            build.tStatistics = tStatistics;
            return this;
        }

    }

}
