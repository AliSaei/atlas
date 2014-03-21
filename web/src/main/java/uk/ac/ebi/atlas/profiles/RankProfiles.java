package uk.ac.ebi.atlas.profiles;

import com.google.common.collect.MinMaxPriorityQueue;
import uk.ac.ebi.atlas.model.GeneProfilesList;
import uk.ac.ebi.atlas.model.Profile;

import java.util.Comparator;

public class RankProfiles<T extends Profile, L extends GeneProfilesList<T>> {

    private Comparator<T> comparator;
    private GeneProfilesListBuilder<L> geneProfilesListBuilder;

    public RankProfiles(Comparator<T> comparator, GeneProfilesListBuilder<L> geneProfilesListBuilder) {
        this.comparator = comparator;
        this.geneProfilesListBuilder = geneProfilesListBuilder;
    }

    public L rank(Iterable<T> profiles, int maxSize) {

        MinMaxPriorityQueue<T> rankingQueue =  MinMaxPriorityQueue.orderedBy(comparator).maximumSize(maxSize).create();

        int count = 0;

        for (T profile : profiles) {
            rankingQueue.add(profile);
            count++;
        }

        L list = geneProfilesListBuilder.create();
        list.addAll(rankingQueue);
        list.setTotalResultCount(count);
        return list;
    }

}
