package uk.ac.ebi.atlas.experimentpage.context;

import com.atlassian.util.concurrent.LazyReference;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.springframework.context.annotation.Scope;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.ExpressionUnit;
import uk.ac.ebi.atlas.model.experiment.baseline.BaselineExperiment;
import uk.ac.ebi.atlas.model.experiment.baseline.Factor;
import uk.ac.ebi.atlas.model.experiment.baseline.FactorGroup;
import uk.ac.ebi.atlas.model.experiment.baseline.RichFactorGroup;
import uk.ac.ebi.atlas.profiles.baseline.BaselineProfileStreamOptions;
import uk.ac.ebi.atlas.web.BaselineRequestPreferences;

import javax.inject.Named;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Named
@Scope("request")
public class BaselineRequestContext<Unit extends ExpressionUnit.Absolute>
        extends RequestContext<AssayGroup,BaselineExperiment, BaselineRequestPreferences<Unit>>
        implements BaselineProfileStreamOptions<Unit> {

    private LazyReference<ImmutableMap<AssayGroup, String>> displayNamePerSelectedAssayGroup = new LazyReference<ImmutableMap<AssayGroup, String>>() {
        @Override
        protected ImmutableMap<AssayGroup, String> create() throws Exception {
            return displayNamePerSelectedAssayGroup();
        }
    };

    public BaselineRequestContext(BaselineRequestPreferences<Unit> requestPreferences, BaselineExperiment experiment) {
        super(requestPreferences, experiment);
    }

    @Override
    public String displayNameForColumn(AssayGroup assayGroup) {
            return Optional.ofNullable(displayNamePerSelectedAssayGroup.get().get(assayGroup)).orElse(assayGroup.getId()) ;
    }

    private List<String> typesWhoseValuesToDisplay() {

        List<FactorGroup> factorGroups = dataColumnsToBeReturned().transform(experiment::getFactors).toList();

        List<String> typesInOrderWeWant = Stream.concat(
                experiment.getDisplayDefaults().prescribedOrderOfFilters().stream(),
                factorGroups.stream().flatMap(factors -> ImmutableList.copyOf(factors).stream().map(Factor::getType)).sorted()
            ).map(Factor::normalize).distinct().collect(Collectors.toList());

        List<String> typesWhoseValuesVaryAcrossSelectedDescriptors =
                RichFactorGroup.filterOutTypesWithCommonValues(
                        typesInOrderWeWant,
                        factorGroups
                );

        return typesWhoseValuesVaryAcrossSelectedDescriptors.isEmpty()
                ? experiment.getDisplayDefaults().prescribedOrderOfFilters().subList(0, Math.min(1, experiment.getDisplayDefaults().prescribedOrderOfFilters().size()))
                : typesWhoseValuesVaryAcrossSelectedDescriptors;
    }

    private ImmutableMap<AssayGroup, String> displayNamePerSelectedAssayGroup() {
        ImmutableMap.Builder<AssayGroup, String> b = ImmutableMap.builder();

        for (AssayGroup assayGroup : dataColumnsToBeReturned()) {
            final FactorGroup factorGroup = experiment.getFactors(assayGroup);

            b.put(
                    assayGroup,
                    typesWhoseValuesToDisplay().stream()
                            .map(type -> factorGroup.factorOfType(Factor.normalize(type)).getValue())
                            .collect(Collectors.joining(", "))
            );
        }

        return b.build();
    }

    @Override
    public Unit getExpressionUnit() {
        return requestPreferences.getUnit();
    }

}