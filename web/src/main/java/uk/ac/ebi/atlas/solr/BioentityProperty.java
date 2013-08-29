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

package uk.ac.ebi.atlas.solr;

import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.beans.Field;

import java.util.Objects;

import static com.google.common.base.Preconditions.checkArgument;

public class BioentityProperty {

    @Field("bioentity_identifier")
    private String bioentityIdentifier;

    @Field("bioentity_type")
    private String bioentityType;

    @Field("species")
    private String species;

    @Field("property_value")
    private String value;

    @Field("property_name")
    private String name;

    //Default constructor required by Solr API
    public BioentityProperty(){
    }

    public BioentityProperty(String bioentityIdentifier, String bioentityType, String species, String name, String value) {
        checkArgument(StringUtils.isNotBlank(value),"Invalid blank property value");

        this.bioentityType = bioentityType;
        this.species = species;
        this.name = name;
        this.bioentityIdentifier = bioentityIdentifier;
        this.value = value;

    }

    public String getBioentityType() {
        return bioentityType;
    }

    public void setBioentityType(String bioentityType) {
        this.bioentityType = bioentityType;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBioentityIdentifier() {
        return bioentityIdentifier;
    }

    public void setBioentityIdentifier(String bioentityIdentifier) {
        this.bioentityIdentifier = bioentityIdentifier;
    }

    @Override
    public int hashCode() {return Objects.hash(bioentityIdentifier,
                                                bioentityType,
                                                species,
                                                value,
                                                name);}

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {return true;}
        if (obj == null || getClass() != obj.getClass()) {return false;}
        final BioentityProperty other = (BioentityProperty) obj;
        return Objects.equals(bioentityIdentifier, other.bioentityIdentifier)
                && Objects.equals(bioentityType, other.bioentityType)
                && Objects.equals(species, other.species)
                && Objects.equals(value, other.value)
                && Objects.equals(name, other.name);
    }
}
