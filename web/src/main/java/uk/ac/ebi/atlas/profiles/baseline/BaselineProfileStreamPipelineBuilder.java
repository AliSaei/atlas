package uk.ac.ebi.atlas.profiles.baseline;

import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.Iterables;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.model.baseline.BaselineProfile;
import uk.ac.ebi.atlas.model.baseline.Factor;
import uk.ac.ebi.atlas.profiles.ProfileStreamFilters;
import uk.ac.ebi.atlas.profiles.differential.ProfileStreamPipelineBuilder;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Set;

@Named
@Scope("prototype")
public class BaselineProfileStreamPipelineBuilder implements ProfileStreamPipelineBuilder<BaselineProfile, BaselineProfileStreamOptions> {

    private final GeneSetBaselineProfilesBuilder geneSetBaselineProfilesBuilder;

    @Inject
    public BaselineProfileStreamPipelineBuilder(GeneSetBaselineProfilesBuilder geneSetBaselineProfilesBuilder) {
        this.geneSetBaselineProfilesBuilder = geneSetBaselineProfilesBuilder;
    }

    @Override
    public Iterable<BaselineProfile> build(Iterable<BaselineProfile> profiles, BaselineProfileStreamOptions options) {
        boolean isSpecific = options.isSpecific();
        Set<Factor> queryFactors = options.getSelectedQueryFactors();
        Set<String> uppercaseGeneIDs = options.getSelectedGeneIDs();
        Set<Factor> allQueryFactors = options.getAllQueryFactors();
        ImmutableSetMultimap<String,String> geneSetIdsToGeneIds = options.getGeneSetIdsToGeneIds();
        boolean isGeneSetMatch = options.isGeneSetMatch();

        Iterable<BaselineProfile> profilesPipeline = profiles;

        if (!uppercaseGeneIDs.isEmpty()) {
            profilesPipeline = ProfileStreamFilters.filterByGeneIds(profilesPipeline, uppercaseGeneIDs);
        }

        if (isGeneSetMatch) {
            profilesPipeline = geneSetBaselineProfilesBuilder.averageIntoGeneSets(profilesPipeline, geneSetIdsToGeneIds);
        }

        if (!queryFactors.isEmpty()) {
            profilesPipeline = isSpecific ?
                    filterByQueryFactorSpecificity(profilesPipeline, queryFactors, allQueryFactors) :
                    ProfileStreamFilters.filterByQueryFactors(profilesPipeline, queryFactors);
        }

        return profilesPipeline;
    }

    public static Iterable<BaselineProfile> filterByQueryFactorSpecificity(Iterable<BaselineProfile> profiles, Set<Factor> queryFactors, Set<Factor> allQueryFactors) {
        IsBaselineProfileSpecific isBaselineProfileSpecific = new IsBaselineProfileSpecific(queryFactors, allQueryFactors);
        return Iterables.filter(profiles, isBaselineProfileSpecific);
    }

}
