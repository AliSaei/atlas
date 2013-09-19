CREATE TABLE IF NOT EXISTS RNASEQ_BSLN_TRANSCRIPTS(
    EXPERIMENT VARCHAR(255) NOT NULL,
    GENE_IDENTIFIER VARCHAR(255) NOT NULL,
    TRANSCRIPT_IDENTIFIER VARCHAR(255) NOT NULL,
    TRANSCRIPT_EXPRESSIONS ARRAY NOT NULL,
    ISACTIVE BOOLEAN NOT NULL DEFAULT TRUE,
    PRIMARY KEY (EXPERIMENT, GENE_IDENTIFIER, TRANSCRIPT_IDENTIFIER)
);

-- CREATE TABLE IF NOT EXISTS bioentity_name(
--     identifier VARCHAR(255) NOT NULL,
--     name VARCHAR(255) NOT NULL,
--     organism  VARCHAR(255),
--     type VARCHAR(50),
--     PRIMARY KEY (identifier, organism, type)
-- );

CREATE TABLE IF NOT EXISTS designelement_mapping(
    designelement VARCHAR(255) NOT NULL,
    identifier VARCHAR(255) NOT NULL,
    type VARCHAR(50) NOT NULL,
    arraydesign  VARCHAR(255),
    PRIMARY KEY (designelement, arraydesign)
);

-- CREATE OR REPLACE VIEW public_experiment AS
-- SELECT ACCESSION, type, last_update
-- FROM experiment WHERE PRIVATE=false;

-- enable the next statement only after successful migration
DROP TABLE IF EXISTS experiment_configuration;

-- ALTER TABLE EXPERIMENT ADD COLUMN IF NOT EXISTS access_key VARCHAR(50) NOT NULL;
