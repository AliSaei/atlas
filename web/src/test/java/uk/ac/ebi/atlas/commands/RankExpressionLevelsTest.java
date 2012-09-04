package uk.ac.ebi.atlas.commands;

import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.model.ExpressionLevel;
import uk.ac.ebi.atlas.streams.ExpressionLevelInputStream;
import uk.ac.ebi.atlas.streams.ExpressionLevelInputStreamBuilder;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RankExpressionLevelsTest {

    public static final String EXPERIMENT_ACCESSION = "ACCESSION_VALUE" ;

    @Mock
    ExpressionLevelInputStreamBuilder inputStreamBuilder;

    @Mock
    ExpressionLevelInputStream inputStream;

    @Mock
    RankStreamingObjects<ExpressionLevel> rankObjectsCommand;

    private List<ExpressionLevel> top10LevelsMock = Lists.newArrayList(mock(ExpressionLevel.class));

    private RankExpressionLevels subject;

    @Before
    public void init() throws Exception {
        when(inputStreamBuilder.createFor(EXPERIMENT_ACCESSION)).thenReturn(inputStream);
        when(rankObjectsCommand.setRankSize(anyInt())).thenReturn(rankObjectsCommand);
        when(rankObjectsCommand.apply(inputStream)).thenReturn(top10LevelsMock);

        subject = new RankExpressionLevels(inputStreamBuilder, rankObjectsCommand);
    }

    @Test
    public void rankingShouldBuildAnObjectInputStreamAndUseItWithARankObjectsCommand() throws Exception {
        //when
        List<ExpressionLevel> expressionLevels = subject.apply(EXPERIMENT_ACCESSION);

        //then
        verify(rankObjectsCommand).setRankSize(10);
        //and
        verify(inputStreamBuilder).createFor(EXPERIMENT_ACCESSION);
        //and
        verify(rankObjectsCommand).apply(inputStream);
        //and
        assertThat(expressionLevels, is(top10LevelsMock));

    }

    @Test
    public void testSetRankingSize() throws Exception {

    }
}
