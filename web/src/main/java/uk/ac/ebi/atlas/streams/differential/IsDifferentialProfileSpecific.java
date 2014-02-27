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

package uk.ac.ebi.atlas.streams.differential;

import com.google.common.base.Predicate;
import com.google.common.collect.Sets;
import uk.ac.ebi.atlas.model.differential.Contrast;
import uk.ac.ebi.atlas.model.differential.DifferentialProfile;

import java.util.Set;

import static com.google.common.base.Preconditions.checkArgument;

public class IsDifferentialProfileSpecific implements Predicate<DifferentialProfile> {

    private final Set<Contrast> selectedQueryContrasts;
    private final Sets.SetView<Contrast> nonSelectedQueryContrasts;

    public IsDifferentialProfileSpecific(Set<Contrast> selectedQueryContrasts, Set<Contrast> allQueryFactors) {
        checkArgument(!selectedQueryContrasts.isEmpty(),"selectedQueryContrasts is empty");
        checkArgument(!allQueryFactors.isEmpty(), "allQueryFactors is empty");

        this.selectedQueryContrasts = selectedQueryContrasts;
        this.nonSelectedQueryContrasts = Sets.difference(allQueryFactors, selectedQueryContrasts);
    }

    @Override
    public boolean apply(DifferentialProfile differentialProfile) {
        double averageExpressionLevelOnSelectedQueryContrasts = differentialProfile.getAverageExpressionLevelOn(selectedQueryContrasts);
        double minExpressionLevelOnNonSelectedQueryContrasts = differentialProfile.getStrongestExpressionLevelOn(nonSelectedQueryContrasts);

        return averageExpressionLevelOnSelectedQueryContrasts < minExpressionLevelOnNonSelectedQueryContrasts;
    }

}
