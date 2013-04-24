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

package uk.ac.ebi.atlas.web.controllers;

import org.springframework.context.annotation.Scope;

import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

@Named
@Scope("request")
public class DownloadURLBuilder {

    private static final String TSV_RAW_FILE_EXTENSION = "/raw-counts.tsv";

    private static final String TSV_NORMALIZED_FILE_EXTENSION = "/normalized.tsv";

    private static final String TSV_ANALYTICS_FILE_EXTENSION = "/all-analytics.tsv";

    public String buildDownloadRawUrl(HttpServletRequest request) {
        return extractBaseURL(request) + TSV_RAW_FILE_EXTENSION;
    }

    public String buildDownloadAllAnalyticsUrl(HttpServletRequest request) {
        return extractBaseURL(request) + TSV_ANALYTICS_FILE_EXTENSION;
    }

    public String buildDownloadNormalizedDataUrl(HttpServletRequest request) {
        return extractBaseURL(request) + TSV_NORMALIZED_FILE_EXTENSION;
    }

    private String extractBaseURL(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        if (requestURI.endsWith("/experiment-design")) {
            return requestURI.replace("/experiment-design", "");
        } else if (requestURI.endsWith("/analysis-methods")) {
            return requestURI.replace("/analysis-methods", "");
        } else {
            return requestURI;
        }
    }
}