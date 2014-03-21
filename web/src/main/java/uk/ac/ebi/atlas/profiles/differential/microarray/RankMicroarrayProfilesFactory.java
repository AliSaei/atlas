package uk.ac.ebi.atlas.profiles.differential.microarray;

import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.model.differential.DifferentialProfileComparatorFactory;
import uk.ac.ebi.atlas.model.differential.DifferentialProfilesList;
import uk.ac.ebi.atlas.model.differential.microarray.MicroarrayProfile;
import uk.ac.ebi.atlas.profiles.GeneProfilesListBuilder;
import uk.ac.ebi.atlas.profiles.RankProfiles;
import uk.ac.ebi.atlas.profiles.differential.DifferentialProfileStreamOptions;
import uk.ac.ebi.atlas.profiles.differential.DifferentialProfilesListBuilder;
import uk.ac.ebi.atlas.profiles.differential.RankProfilesFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Comparator;

@Named
@Scope("singleton")
public class RankMicroarrayProfilesFactory implements RankProfilesFactory<MicroarrayProfile, DifferentialProfilesList<MicroarrayProfile>, DifferentialProfileStreamOptions> {

    private DifferentialProfileComparatorFactory<MicroarrayProfile> differentialProfileComparatorFactory;
    private DifferentialProfilesListBuilder<MicroarrayProfile> geneProfilesListBuilder;

    @Inject
    public RankMicroarrayProfilesFactory(DifferentialProfileComparatorFactory<MicroarrayProfile> differentialProfileComparatorFactory,DifferentialProfilesListBuilder<MicroarrayProfile> geneProfilesListBuilder) {
        this.differentialProfileComparatorFactory = differentialProfileComparatorFactory;
        this.geneProfilesListBuilder = geneProfilesListBuilder;
    }

    public RankProfiles<MicroarrayProfile, DifferentialProfilesList<MicroarrayProfile>> create(DifferentialProfileStreamOptions options) {
        Comparator<MicroarrayProfile> comparator = differentialProfileComparatorFactory.create(options);
        return new RankProfiles<>(comparator, geneProfilesListBuilder);
    }

}
