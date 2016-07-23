package uk.ac.ebi.atlas.search;

import com.google.auto.value.AutoValue;
import javax.annotation.Nullable;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

@AutoValue
public abstract class SemanticQueryTerm {
    public abstract String value();
    @Nullable
    public abstract String category();

    public static SemanticQueryTerm create(String value) {
        return create(value, "");
    }

    public static SemanticQueryTerm create(String value, String category) {
        return new AutoValue_SemanticQueryTerm(value, category);
    }

    public boolean hasNoCategory() {
        return isBlank(category());
    }

    public boolean hasValue() {
        return isNotBlank(value());
    }

    public String description() {
        if (hasNoCategory()) {
            return (String.format("\"%s\"", value()));
        } else {
            return (String.format("\"%s\" (%s)", value(), category()));
        }
    }

    @Override
    public String toString() {
        if (hasNoCategory()) {
            return (String.format("\"%s\"", value()));
        } else {
            return (String.format("\"%s:{%s}\"", category(), value()));
        }
    }
}
