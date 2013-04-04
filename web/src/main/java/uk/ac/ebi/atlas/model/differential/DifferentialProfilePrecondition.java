/*
 * Copyright 2008-2013 Microarray Informatics Team, EMBL-European Bioinformatics Institute
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
 * For further details of the Gene Expression Atlas project, including source code,
 * downloads and documentation, please see:
 *
 * http://gxa.github.com/gxa
 */

package uk.ac.ebi.atlas.model.differential;

import com.google.common.base.Predicate;
import com.google.common.collect.Sets;

import java.util.Set;

public abstract class DifferentialProfilePrecondition<T extends DifferentialProfile> implements Predicate<T> {

    private Set<Contrast> selectedQueryFactors;
    private Regulation regulation;
    private Set<Contrast> allQueryFactors;

    DifferentialProfilePrecondition setSelectedQueryFactors(Set<Contrast> selectedQueryFactors) {
        this.selectedQueryFactors = selectedQueryFactors;
        return this;
    }

    DifferentialProfilePrecondition setAllQueryFactors(Set<Contrast> allQueryFactors) {
        this.allQueryFactors = allQueryFactors;
        return this;
    }

    DifferentialProfilePrecondition setRegulation(Regulation regulation) {
        this.regulation = regulation;
        return this;
    }

    public boolean apply(T profile) {
        if (profile.isEmpty()){
            return false;
        }

        double averageExpressionLevelOnSelectedFactors = averageExpressionLevelOnSelectedFactors(profile);
        double averageExpressionLevelOnNonSelectedFactors = averageExpressionLevelOnNonSelectedFactors(profile);

        boolean moreSignificantInSelectedConditions = averageExpressionLevelOnNonSelectedFactors == 0 ||
                averageExpressionLevelOnSelectedFactors < averageExpressionLevelOnNonSelectedFactors;

        return moreSignificantInSelectedConditions && applyExtraConditions(profile);
    }

    private double averageExpressionLevelOnSelectedFactors(T profile) {
        return profile.getAverageExpressionLevelOn(selectedQueryFactors, regulation);
    }

    private double averageExpressionLevelOnNonSelectedFactors(T profile) {
        Set<Contrast> remainingFactors = Sets.difference(allQueryFactors, selectedQueryFactors);
        return profile.getAverageExpressionLevelOn(remainingFactors, regulation);
    }

    protected abstract boolean applyExtraConditions(T profile);
}
