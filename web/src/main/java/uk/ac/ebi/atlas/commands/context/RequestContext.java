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

package uk.ac.ebi.atlas.commands.context;

import com.google.common.base.Objects;
import uk.ac.ebi.atlas.geneindex.GeneQueryResponse;
import uk.ac.ebi.atlas.web.ExperimentPageRequestPreferences;

import java.util.Set;
import java.util.SortedSet;


public abstract class RequestContext<T, K extends ExperimentPageRequestPreferences> {
    private K requestPreferences;
    private Set<T> selectedQueryFactors;
    private String filteredBySpecies;
    private SortedSet<T> allQueryFactors;
    private GeneQueryResponse geneQueryResponse;

    public GeneQueryResponse getGeneQueryResponse() {
        return geneQueryResponse;
    }

    public void setGeneQueryResponse(GeneQueryResponse geneQueryResponse) {
        this.geneQueryResponse = geneQueryResponse;
    }

    public String getGeneQuery() {
        return getRequestPreferences().getGeneQuery();
    }

    public Integer getHeatmapMatrixSize() {
        return getRequestPreferences().getHeatmapMatrixSize();
    }

    public Set<T> getSelectedQueryFactors() {
        return selectedQueryFactors;
    }

    public String getFilteredBySpecies() {
        return filteredBySpecies;
    }

    public double getCutoff() {
        return getRequestPreferences().getCutoff();
    }

    public boolean isSpecific() {
        return getRequestPreferences().isSpecific();
    }

    public boolean isExactMatch() {
        return getRequestPreferences().isExactMatch();
    }

    public boolean isGeneSetMatch() {
        return getRequestPreferences().isGeneSetMatch();
    }

    public SortedSet<T> getAllQueryFactors() {
        return allQueryFactors;
    }

    void setAllQueryFactors(SortedSet<T> allQueryFactors) {
        this.allQueryFactors = allQueryFactors;
    }

    void setSelectedQueryFactors(Set<T> selectedQueryFactors) {
        this.selectedQueryFactors = selectedQueryFactors;
    }

    void setFilteredBySpecies(String filteredBySpecies) {
        this.filteredBySpecies = filteredBySpecies;
    }

    void setRequestPreferences(K requestPreferences) {
        this.requestPreferences = requestPreferences;
    }

    K getRequestPreferences() {
        return requestPreferences;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(getClass())
                .add("requestPreferences", getRequestPreferences())
                .add("filteredBySpecies", filteredBySpecies)
                .add("geneQueryResponse", geneQueryResponse)
                .toString();
    }

}
