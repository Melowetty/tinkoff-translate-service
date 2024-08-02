CREATE TABLE IF NOT EXISTS translate_record(
    id SERIAL PRIMARY KEY,
    ip_address INET NOT NULL,
    source_language VARCHAR(64) NOT NULL,
    target_language VARCHAR(64) NOT NULL,
    input TEXT NOT NULL,
    translated TEXT NOT NULL,
    created_on TIMESTAMP NOT NULL
);