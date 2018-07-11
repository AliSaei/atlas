package uk.ac.ebi.atlas.bioentity.interpro;

import au.com.bytecode.opencsv.CSVReader;
import com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import uk.ac.ebi.atlas.model.OntologyTerm;
import uk.ac.ebi.atlas.utils.CsvReaderFactory;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Path;
import java.util.Optional;

@Component
public class InterProTrader {
    private static final Logger LOGGER = LoggerFactory.getLogger(InterProTrader.class);
    private final ImmutableMap<String, OntologyTerm> accessionToTerm;

    public InterProTrader(Path interProFilePath) {
        try (CSVReader tsvReader = CsvReaderFactory.createForTsv(interProFilePath.toString())) {
            accessionToTerm = new InterProTSVParser(tsvReader).parse();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public Optional<OntologyTerm> get(String accession) {
        if (accessionToTerm.get(accession) == null) {
            LOGGER.warn("Unable to find InterPro accession {}", accession);
        }

        return Optional.ofNullable(accessionToTerm.get(accession));
    }
}
