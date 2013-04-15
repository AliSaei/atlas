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

package uk.ac.ebi.atlas.web.controllers.rest;

import com.google.gson.Gson;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import uk.ac.ebi.atlas.model.baseline.Factor;
import uk.ac.ebi.atlas.transcript.TranscriptContributionCalculator;
import uk.ac.ebi.atlas.web.BaselineRequestPreferences;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;

@Controller
@Scope("request")
public class RankedGeneTranscriptsController {

    private TranscriptContributionCalculator transcriptContributionCalculator;

    @Inject
    public RankedGeneTranscriptsController(TranscriptContributionCalculator transcriptContributionCalculator) {
        this.transcriptContributionCalculator = transcriptContributionCalculator;
    }

    @RequestMapping(value = "/json/transcripts/{experimentAccession}/{geneId}/{factorValue}", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String getRankedTranscripts(HttpServletRequest request, @PathVariable String experimentAccession,
                                       @PathVariable String geneId,
                                       @PathVariable String factorValue,
                                       @ModelAttribute("preferences") @Valid BaselineRequestPreferences preferences,
                                        @RequestParam(value = "rankingSize", defaultValue = "3") Integer rankingSize) {


//        String queryFactorType = preferences.getQueryFactorType();
        String queryFactorType = "organism part";
        Factor factor = new Factor(queryFactorType, factorValue);

        Map<String, Double> transcriptRates = transcriptContributionCalculator.getTranscriptContributions(geneId, experimentAccession, factor);

//        SortedMap<String, Double> transcriptRates = new TreeMap();
//
//        //Following code is just to generate random ranking, don't keep it seriously!
//
//        double topTranscriptsTotalPercentage = 0;
//        for(int i = 0; i < rankingSize; i++){
//            double randomPortion = (100d - RandomUtils.nextInt(50))/(rankingSize + 1);
//            randomPortion = MathUtils.round(randomPortion, 2);
//
//            transcriptRates.put("ENST00000" + (i + 1), randomPortion);
//            topTranscriptsTotalPercentage += randomPortion;
//        }
//
//        double othersPortion = 100 - topTranscriptsTotalPercentage;
//        transcriptRates.put("Others", othersPortion);


        return new Gson().toJson(transcriptRates, Map.class);

    }

}
