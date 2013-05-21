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

package uk.ac.ebi.atlas.model.cache.differential;

import uk.ac.ebi.arrayexpress2.magetab.exception.ParseException;
import uk.ac.ebi.atlas.model.ConfigurationTrader;
import uk.ac.ebi.atlas.model.cache.ExperimentLoader;
import uk.ac.ebi.atlas.model.differential.Contrast;
import uk.ac.ebi.atlas.model.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.model.differential.DifferentialExperimentConfiguration;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.Set;

@Named
public class DifferentialExperimentLoader extends ExperimentLoader<DifferentialExperiment> {

    private ConfigurationTrader configurationTrader;

    @Inject
    public DifferentialExperimentLoader(ConfigurationTrader configurationTrader) {
        this.configurationTrader = configurationTrader;
    }

    @Override
    protected DifferentialExperiment load(String accession, String experimentDescription, boolean hasExtraInfoFile) throws ParseException, IOException {

        DifferentialExperimentConfiguration differentialExperimentConfiguration = configurationTrader.getDifferentialExperimentConfiguration(accession);
        Set<Contrast> contrasts = differentialExperimentConfiguration.getContrasts();

        Set<String> species = extractSpecies(accession);

        return new DifferentialExperiment(accession, contrasts, experimentDescription, hasExtraInfoFile, species);

    }
}
