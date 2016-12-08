package uk.ac.ebi.atlas.species;

import com.google.auto.value.AutoValue;

import java.util.List;
import java.util.Map;

@AutoValue
public abstract class SpeciesConfigurationRecord {
    public static SpeciesConfigurationRecord create(String name, String defaultQueryFactorType, String kingdom,
                                                  Map<String, List<String>> resources) {
        return new AutoValue_SpeciesConfigurationRecord(name, defaultQueryFactorType, kingdom, resources);
    }

    public abstract String name();
    public abstract String defaultQueryFactorType();
    public abstract String kingdom();
    public abstract Map<String, List<String>> resources();

    public boolean isPlant() {
        return "plants".equals(kingdom());
    }


}