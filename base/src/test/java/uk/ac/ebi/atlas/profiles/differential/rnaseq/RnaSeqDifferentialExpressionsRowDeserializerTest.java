
package uk.ac.ebi.atlas.profiles.differential.rnaseq;

import com.google.common.collect.ImmutableList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.AssayGroup;
import uk.ac.ebi.atlas.model.experiment.differential.Contrast;
import uk.ac.ebi.atlas.model.experiment.differential.DifferentialExpression;
import uk.ac.ebi.atlas.profiles.tsv.DifferentialExpressionsRowDeserializer;
import uk.ac.ebi.atlas.profiles.tsv.RnaSeqDifferentialExpressionsRowDeserializer;

import java.util.Iterator;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class RnaSeqDifferentialExpressionsRowDeserializerTest {


    AssayGroup g1 = new AssayGroup("g1", "run_11", "run_12", "run_13");
    AssayGroup g2 = new AssayGroup("g2", "run_21", "run_22", "run_23", "run_24");
    AssayGroup g3 = new AssayGroup("g3", "run_31", "run_32");

    Contrast g1_g2 = new Contrast("g1_g2","", g1, g2, "first contrast");
    Contrast g1_g3 = new Contrast("g1_g2","", g1, g3, "second contrast");

    DifferentialExpressionsRowDeserializer subject = new RnaSeqDifferentialExpressionsRowDeserializer(
            ImmutableList.of(g1_g2, g1_g3));

    @Test
    public void returnTheRightExpressions() {
        String P_VAL_1 = "1";
        String FOLD_CHANGE_1 = "0.474360080385946";

        String P_VAL_2 = "1";
        String FOLD_CHANGE_2 = "-Inf";

        String[] TWO_CONTRASTS = new String[]{P_VAL_1, FOLD_CHANGE_1, P_VAL_2, FOLD_CHANGE_2};

        Iterator<DifferentialExpression> it = subject.deserializeRow(TWO_CONTRASTS).iterator();

        DifferentialExpression expression = it.next();
        assertThat(expression.getPValue(), is(Double.valueOf(P_VAL_1)));
        assertThat(expression.getFoldChange(), is(Double.valueOf(FOLD_CHANGE_1)));

        expression = it.next();
        assertThat(expression.getPValue(), is(Double.valueOf(P_VAL_2)));
        assertThat(expression.getFoldChange(), is(Double.NEGATIVE_INFINITY));

        assertThat(it.hasNext(), is(false));
    }


    @Test
    public void skipNAValues() {
        String P_VAL_1 = "1";
        String FOLD_CHANGE_1 = "0.474360080385946";

        assertThat(subject.deserializeRow(new String[]{P_VAL_1, FOLD_CHANGE_1}).size(), is(1));
        assertThat(subject.deserializeRow(new String[]{P_VAL_1, FOLD_CHANGE_1, "NA", "NA"}).size(), is(1));
        assertThat(subject.deserializeRow(new String[]{"NA","NA", P_VAL_1, FOLD_CHANGE_1}).size(), is(1));
        assertThat(subject.deserializeRow(new String[]{"NA","NA", P_VAL_1, FOLD_CHANGE_1, "NA", "NA", "NA", "NA"})//Batman!
                .size(), is(1));
    }
}