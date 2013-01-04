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

package uk.ac.ebi.atlas.acceptance.selenium.pages;

import org.openqa.selenium.WebDriver;

public class ExperimentDesignFeedbackPage extends FeedbackPage {

    public static String EXPERIMENT_ACCESSION = "E-MTAB-513";

    private static final String DEFAULT_PAGE_URI = "/gxa/experiments/" + EXPERIMENT_ACCESSION + "/experiment-design";

    public ExperimentDesignFeedbackPage(WebDriver driver) {
        super(driver);
    }

    public ExperimentDesignFeedbackPage(WebDriver driver, String httpParameters) {
        super(driver, httpParameters);
    }

    @Override
    protected String getPageURI() {
        return DEFAULT_PAGE_URI;
    }

}