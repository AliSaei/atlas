package uk.ac.ebi.atlas.download;

import com.google.common.net.HttpHeaders;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import uk.ac.ebi.atlas.configuration.TestConfig;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.text.MessageFormat;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = TestConfig.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FileDownloadControllerWIT {
    private static final String EXPERIMENT_ACCESSION = "E-MTAB-5061";
    private static final String EXPERIMENT_DESIGN_FILE_NAME = "ExpDesign-{0}.tsv";
    private static final String ARCHIVE_NAME = "{0}-{1}-files.zip";
    private static final String FILE_DOWNLOAD_URL = "/experiment/{experimentAccession}/download";
    private static final String ARCHIVE_DOWNLOAD_URL = "/experiment/{experimentAccession}/download/zip";

    @Inject
    private DataSource dataSource;

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @BeforeAll
    void populateDatabaseTables() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScripts(new ClassPathResource("fixtures/scxa_experiment-fixture.sql"));
        populator.execute(dataSource);
    }

    @AfterAll
    void cleanDatabaseTables() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScripts(new ClassPathResource("fixtures/scxa_experiment-delete.sql"));
        populator.execute(dataSource);
    }

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    void downloadFileForValidExperimentAccession() throws Exception {
        String expectedFileName = MessageFormat.format(EXPERIMENT_DESIGN_FILE_NAME, EXPERIMENT_ACCESSION);
        this.mockMvc.perform(get(FILE_DOWNLOAD_URL, EXPERIMENT_ACCESSION)
                .param("fileType", ExperimentFileType.EXPERIMENT_DESIGN.getId()))
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + expectedFileName))
                .andExpect(content().contentType(MediaType.TEXT_PLAIN));
    }

    @Test
    void downloadFileForInvalidExperimentAccession() throws Exception {
        this.mockMvc.perform(get(FILE_DOWNLOAD_URL, "FOO")
                .param("fileType", ExperimentFileType.EXPERIMENT_DESIGN.getId()))
                .andExpect(status().is(400))
                .andExpect(view().name("error-page"));
    }

    @Test
    void downloadFileForInvalidExperimentFileType() throws Exception {
        this.mockMvc.perform(get(FILE_DOWNLOAD_URL, EXPERIMENT_ACCESSION)
                .param("fileType", "foo"))
                .andExpect(status().is(404))
                .andExpect(view().name("error-page"));
    }

    @Test
    void downloadArchiveForValidExperimentAccession() throws Exception {
        String expectedArchiveName =
                MessageFormat.format(
                        ARCHIVE_NAME, EXPERIMENT_ACCESSION, ExperimentFileType.QUANTIFICATION_FILTERED.getId());

        this.mockMvc.perform(get(ARCHIVE_DOWNLOAD_URL, EXPERIMENT_ACCESSION)
                .param("fileType", ExperimentFileType.QUANTIFICATION_FILTERED.getId()))
                .andExpect(status().isOk())
                .andExpect(
                        header().string(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + expectedArchiveName))
                .andExpect(content().contentType("application/zip"));
    }

    @Test
    void downloadArchiveForInvalidExperimentAccession() throws Exception {
        this.mockMvc.perform(get(ARCHIVE_DOWNLOAD_URL, "FOO")
                .param("fileType", ExperimentFileType.EXPERIMENT_DESIGN.getId()))
                .andExpect(status().is(400))
                .andExpect(view().name("error-page"));
    }

    @Test
    void downloadArchiveForInvalidExperimentFileType() throws Exception {
        this.mockMvc.perform(get(ARCHIVE_DOWNLOAD_URL, EXPERIMENT_ACCESSION)
                .param("fileType", "foo"))
                .andExpect(status().is(404))
                .andExpect(view().name("error-page"));
    }
}
