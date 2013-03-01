/*
 * Copyright 2008-2012 Microarray Informatics Team, EMBL-European Bioinformatics Institute
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

package uk.ac.ebi.atlas.model;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.*;

import static com.google.common.base.Preconditions.checkNotNull;

public class Experiment implements Serializable {

    private static final String EXPERIMENT_RUN_NOT_FOUND = "ExperimentRun {0} not found";

    private String description;
    private Set<String> species;

    private String defaultQueryFactorType;
    private Set<Factor> defaultFilterFactors;

    private String displayName;

    private Map<String, ExperimentRun> experimentRuns = new HashMap<>();

    private ExperimentalFactors experimentalFactors;

    private boolean hasExtraInfoFile;


    Experiment(ExperimentalFactors experimentalFactors, Collection<ExperimentRun> experimentRuns, String description, String displayName, Set<String> species, String defaultQueryFactorType, Set<Factor> defaultFilterFactors, boolean hasExtraInfoFile) {
        this.description = description;
        this.displayName = displayName;
        this.species = species;
        this.experimentalFactors = experimentalFactors;
        this.defaultQueryFactorType = defaultQueryFactorType;
        this.defaultFilterFactors = defaultFilterFactors;
        this.hasExtraInfoFile = hasExtraInfoFile;
        for (ExperimentRun experimentRun : experimentRuns) {
            this.experimentRuns.put(experimentRun.getAccession(), experimentRun);
        }
    }

    public String getDisplayName() {
        return displayName;
    }

    public FactorGroup getFactorGroup(String experimentRunAccession) {
        ExperimentRun experimentRun = getExperimentRun(experimentRunAccession);
        checkNotNull(experimentRun, MessageFormat.format(EXPERIMENT_RUN_NOT_FOUND, experimentRunAccession));

        return experimentRun.getFactorGroup();
    }

    public Set<String> getExperimentRunAccessions() {
        return experimentRuns.keySet();
    }

    private ExperimentRun getExperimentRun(String experimentRunAccession) {
        return experimentRuns.get(experimentRunAccession);
    }

    public Set<String> getSpecies() {
        return Collections.unmodifiableSet(species);
    }

    public String getFirstSpecies() {
        return species.iterator().next();
    }

    public String getDescription() {
        return description;
    }

    public String getDefaultQueryFactorType() {
        return defaultQueryFactorType;
    }

    public Set<Factor> getDefaultFilterFactors() {
        return Collections.unmodifiableSet(defaultFilterFactors);
    }

    public ExperimentalFactors getExperimentalFactors() {
        return experimentalFactors;
    }

    public boolean isForSingleSpecie() {
        return 1 == species.size();
    }

    public boolean hasExtraInfoFile() {
        return hasExtraInfoFile;
    }

}
