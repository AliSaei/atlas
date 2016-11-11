
package uk.ac.ebi.atlas.controllers.rest.experimentdesign;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import uk.ac.ebi.atlas.commons.readers.FileTsvReaderBuilder;
import uk.ac.ebi.atlas.commons.readers.TsvReader;
import uk.ac.ebi.atlas.model.differential.DifferentialExperiment;
import uk.ac.ebi.atlas.trader.ExperimentTrader;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.List;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DifferentialDesignDownloadControllerTest {

    public static final String EXPERIMENT_ACCESSION = "accession";
    public static final String ASSAY_ACCESSION = "assayAccession";
    private static final String ACCESS_KEY = "hunter2";

    @Mock
    private HttpServletResponse responseMock;

    @Mock
    private DifferentialExperiment experimentMock;

    @Mock
    private FileTsvReaderBuilder fileTsvReaderBuilderMock;

    @Mock
    private TsvReader tsvReaderMock;

    @Mock
    private PrintWriter printWriterMock;

    @Mock
    ExperimentTrader experimentTrader;

    private DifferentialDesignDownloadController subject;

    private Set<String> assayAccessions = Sets.newHashSet(ASSAY_ACCESSION);

    @Before
    public void setUp() throws Exception {
        when(fileTsvReaderBuilderMock.forTsvFilePathTemplate(anyString())).thenReturn(fileTsvReaderBuilderMock);
        when(fileTsvReaderBuilderMock.withExperimentAccession(EXPERIMENT_ACCESSION)).thenReturn(fileTsvReaderBuilderMock);
        when(fileTsvReaderBuilderMock.build()).thenReturn(tsvReaderMock);

        subject = new DifferentialDesignDownloadController(fileTsvReaderBuilderMock,experimentTrader);
        subject.initializeTsvReader();

        List<String[]> designs = Lists.newArrayList();
        designs.add(new String[]{"header1", "header2", "header3"});
        designs.add(new String[]{ASSAY_ACCESSION, "value2", "value3"});
        designs.add(new String[]{"otherAccession", "value5", "value6"});

        when(experimentTrader.getExperiment(EXPERIMENT_ACCESSION,ACCESS_KEY)).thenReturn(experimentMock);
        when(experimentMock.getAccession()).thenReturn(EXPERIMENT_ACCESSION);
        when(tsvReaderMock.readAll()).thenReturn(designs);

        when(experimentMock.getAssayAccessions()).thenReturn(assayAccessions);
        when(responseMock.getWriter()).thenReturn(printWriterMock);
    }

    @Test
    public void testDownloadRnaSeqExperimentDesign() throws Exception {

        subject.downloadRnaSeqExperimentDesign(EXPERIMENT_ACCESSION,ACCESS_KEY, responseMock);
        verify(printWriterMock).write("header1\theader2\theader3\tAnalysed\n", 0, 33);
        verify(printWriterMock).write("assayAccession\tvalue2\tvalue3\tYes\n", 0, 33);
        verify(printWriterMock).write("otherAccession\tvalue5\tvalue6\tNo\n", 0, 32);

        verify(responseMock).setHeader("Content-Disposition", "attachment; filename=\"" + EXPERIMENT_ACCESSION + "-experiment-design.tsv\"");
        verify(responseMock).setContentType("text/plain; charset=utf-8");

    }

    @Test
    public void testDownloadMicroarrayExperimentDesign() throws Exception {

        subject.downloadMicroarrayExperimentDesign(EXPERIMENT_ACCESSION,ACCESS_KEY, responseMock);
        verify(printWriterMock).write("header1\theader2\theader3\tAnalysed\n", 0, 33);
        verify(printWriterMock).write("assayAccession\tvalue2\tvalue3\tYes\n", 0, 33);
        verify(printWriterMock).write("otherAccession\tvalue5\tvalue6\tNo\n", 0, 32);

        verify(responseMock).setHeader("Content-Disposition", "attachment; filename=\"" + EXPERIMENT_ACCESSION + "-experiment-design.tsv\"");
        verify(responseMock).setContentType("text/plain; charset=utf-8");

    }

    @Test
    public void testGetAnalysedRowsAccessions() {

        Set<String> analysedRowsAccessions = subject.getAnalysedRowsAccessions(experimentMock);
        assertThat(analysedRowsAccessions, is(assayAccessions));
    }
}