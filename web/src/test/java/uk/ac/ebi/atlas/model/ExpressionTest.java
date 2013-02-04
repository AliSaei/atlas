package uk.ac.ebi.atlas.model;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.impl.FactorSet;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(MockitoJUnitRunner.class)
public class ExpressionTest {

    private Expression subject;

    @Before
    public void initSubject() {

        Factor factor = new Factor("aType", "organ", "heart");

        subject = new Expression(2.3, new FactorSet().add(factor));
    }

    @Test
    public void testIsGreaterThan() throws Exception {

        assertThat(subject.isGreaterThan(0.0), is(true));

        assertThat(subject.isGreaterThan(3.0), is(false));

        assertThat(subject.isGreaterThan(2.3), is(false));

    }
}
