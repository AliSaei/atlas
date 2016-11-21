package uk.ac.ebi.atlas.utils;

import au.com.bytecode.opencsv.CSVReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Reader;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.zip.GZIPInputStream;

import static com.google.common.base.Preconditions.checkNotNull;

public class CsvReaderFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(CsvReaderFactory.class);

    public static CSVReader createForTsv(String tsvFilePath) {
        try {
            Path filePath = FileSystems.getDefault().getPath(checkNotNull(tsvFilePath));
            Reader reader = Files.newBufferedReader(filePath, StandardCharsets.UTF_8);
            return new CSVReader(reader, '\t');
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    public static CSVReader createForTsvGz(String tsvGzFilePath) throws NoSuchFileException {
        try {
            Path filePath = FileSystems.getDefault().getPath(checkNotNull(tsvGzFilePath));
            Reader reader = new BufferedReader(new InputStreamReader(new GZIPInputStream(Files.newInputStream(filePath))));
            return new CSVReader(reader, '\t');
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw new NoSuchFileException(e.getMessage());
        }
    }

}
