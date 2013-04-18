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

package uk.ac.ebi.atlas.utils;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Factory;
import org.apache.commons.collections.map.LazyMap;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.model.baseline.ExperimentalFactors;
import uk.ac.ebi.atlas.model.baseline.Factor;
import uk.ac.ebi.atlas.web.FilterFactorsConverter;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;

import static com.google.common.base.Preconditions.checkState;

@Named("filterFactorMenuBuilder")
@Scope("prototype")
public class FilterFactorMenuBuilder {

    private ExperimentalFactors experimentalFactors;
    private SortedSet<Factor> factors;
    private FilterFactorsConverter factorConverter;

    @Inject
    FilterFactorMenuBuilder(FilterFactorsConverter factorConverter) {
        this.factorConverter = factorConverter;
    }

    public FilterFactorMenuBuilder withExperimentalFactors(ExperimentalFactors experimentalFactors) {
        this.experimentalFactors = experimentalFactors;
        return this;
    }

    public FilterFactorMenuBuilder forFilterFactors(Set<Factor> setOfFactor) {
        this.factors = new TreeSet<>(setOfFactor);
        return this;
    }

    public SortedSet<FilterFactorMenuVoice> build() {
        checkState(experimentalFactors != null, "Please provide experimental factors");
        checkState(CollectionUtils.isNotEmpty(factors), "Please provide a set of factors");

        return extractVoicesForFactors(factors);
    }

    protected SortedSet<FilterFactorMenuVoice> extractVoicesForFactors(Set<Factor> setOfFactor) {

        Map<String, Set<Factor>> factorNames = extractFactorNames(setOfFactor);

        SortedSet<FilterFactorMenuVoice> result = new TreeSet<>();
        for (String factorName : factorNames.keySet()) {
            FilterFactorMenuVoice voice = new FilterFactorMenuVoice(factorName);
            voice.setType(experimentalFactors.getFactorType(factorName));

            SortedSet<FilterFactorMenuVoice> children = buildVoices(factorNames.get(factorName), setOfFactor);
            voice.setChildren(children);
            result.add(voice);
        }

        return result;
    }

    protected SortedSet<FilterFactorMenuVoice> buildVoices(Set<Factor> factorsForFactorName, Set<Factor> allFactors) {

        SortedSet<FilterFactorMenuVoice> result = new TreeSet<>();
        for (Factor factor : factorsForFactorName) {
            String value = factor.getValue();
            FilterFactorMenuVoice voice = new FilterFactorMenuVoice(value);
            voice.setFactor(factor);
            result.add(voice);

            // limit set of factors for following levels
            Set<Factor> withoutCurrent = Sets.newHashSet(allFactors);
            withoutCurrent.removeAll(factorsForFactorName);

            // prepare recursion
            Set<Factor> remaining = filterRemainingFactors(factor, withoutCurrent);
            if (CollectionUtils.isNotEmpty(remaining)) {
                SortedSet<FilterFactorMenuVoice> children = extractVoicesForFactors(remaining);
                voice.setChildren(children);
            }
        }

        return result;
    }

    protected Set<Factor> filterRemainingFactors(Factor factor, Set<Factor> allFactors) {

        SortedSet<Factor> coOccurringFactors = experimentalFactors.getCoOccurringFactors(factor);
        Set<Factor> remaining = Sets.newHashSet(coOccurringFactors);
        remaining.retainAll(allFactors);

        return remaining;
    }

    protected Map<String, Set<Factor>> extractFactorNames(Set<Factor> setOfFactor) {

        Map<String, Set<Factor>> result = LazyMap.decorate(new HashMap<>(), new Factory() {
            @Override
            public Object create() {
                return new HashSet<>();
            }
        });

        for (Factor factor : setOfFactor) {
            String type = factor.getType();
            String factorName = experimentalFactors.getFactorName(type);
            result.get(factorName).add(factor);
        }

        return result;
    }

    public String getLink(String queryFactorType, Factor... factors) {
        return getJsonUrl(queryFactorType, factors);
    }

    protected String getJsonUrl(String queryFactorType, Factor... factors) {
        return new Gson().toJson(buildFactorsCombination(queryFactorType, factors));
    }

    private FactorsCombination buildFactorsCombination(String queryFactorType, Factor... factors) {
        return new FactorsCombination(queryFactorType, factors);
    }

    private final class FactorsCombination {

        private String queryFactorType;

        private String serializedFactors;

        FactorsCombination(String queryFactorType, Factor... factors) {
            this.queryFactorType = queryFactorType;
            serializedFactors = factorConverter.serialize(Lists.newArrayList(factors));
        }

        public String getQueryFactorType() {
            return queryFactorType;
        }

        public String getSerializedFactors() {
            return serializedFactors;
        }

    }

}