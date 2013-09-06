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

package uk.ac.ebi.atlas.solr.query.builders;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * This is a builder, keep always in mind that builders are potentially stateful.
 * If you need to build different query strings within the same client process
 * you must use a new instance of builder for each query.
 */
public abstract class SolrQueryBuilder<T extends SolrQueryBuilder<T>> {

    public static final String PROPERTY_NAME_FIELD = "property_name";

    public static final String BIOENTITY_TYPE_FIELD = "bioentity_type";

    protected StringBuilder queryStringBuilder = new StringBuilder();

    public T withSpecies(String species){
        if (StringUtils.isNotBlank(species)){
            queryStringBuilder.append(" AND species:\"").append(species).append("\"");
        }
        return getThis();
    }

    public T withBioentityTypes(String... bioentityTypes){
        checkArgument(ArrayUtils.isNotEmpty(bioentityTypes));

        List<String> bioentityTypeConditions = transformToConditions(BIOENTITY_TYPE_FIELD, bioentityTypes);

        queryStringBuilder.append(" AND (");
        Joiner.on(" OR ").appendTo(queryStringBuilder, bioentityTypeConditions).append(")");
        return getThis();
    }

    public T withPropertyNames(String[] propertyNames){
        checkArgument(ArrayUtils.isNotEmpty(propertyNames));

        List<String> propertyNameConditions = transformToConditions(PROPERTY_NAME_FIELD, propertyNames);

        queryStringBuilder.append(" AND (");
        Joiner.on(" OR ").appendTo(queryStringBuilder, propertyNameConditions).append(")");
        return getThis();
    }

    protected List<String> transformToConditions(final String filedName, String[] values){
        return Lists.transform(Lists.newArrayList(values), new Function<String, String>() {
            @Override
            public String apply(String bioEntityType) {
                return filedName.concat(":\"").concat(bioEntityType).concat("\"");
            }
        });

    }

    protected abstract T getThis();

}
