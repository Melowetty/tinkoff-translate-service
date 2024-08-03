CREATE TABLE IF NOT EXISTS translate_record(
    id SERIAL PRIMARY KEY,
    ip_address VARCHAR(45) NOT NULL,
    source_language REFERENCES language (id) ON DELETE SET NULL,
    target_language REFERENCES language (id) ON DELETE SET NULL,
    input TEXT NOT NULL,
    translated TEXT NOT NULL,
    created_on TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS language(
    id SERIAL PRIMARY KEY,
    code VARCHAR(3) UNIQUE NOT NULL,
    name VARCHAR(64) NOT NULL,
);