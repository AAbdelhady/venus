CREATE TABLE IF NOT EXISTS users
(
  id       BIGINT PRIMARY KEY       NOT NULL,
  modified TIMESTAMP WITH TIME ZONE NOT NULL
);
CREATE SEQUENCE IF NOT EXISTS users_seq;
CREATE UNIQUE INDEX IF NOT EXISTS users_id_uindex ON users (id);
CREATE INDEX IF NOT EXISTS users_modified_index ON users (modified);