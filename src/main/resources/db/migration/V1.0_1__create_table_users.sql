-- users table --
CREATE TABLE IF NOT EXISTS users
(
  id       BIGINT PRIMARY KEY       NOT NULL,
  created TIMESTAMP WITH TIME ZONE NOT NULL,
  modified TIMESTAMP WITH TIME ZONE NOT NULL
);
CREATE SEQUENCE IF NOT EXISTS users_seq;
CREATE UNIQUE INDEX IF NOT EXISTS users_id_uindex ON users (id);
CREATE INDEX IF NOT EXISTS users_modified_index ON users (modified);


-- customers table --
CREATE TABLE IF NOT EXISTS customers
(
  id       BIGINT PRIMARY KEY       NOT NULL,
  CONSTRAINT customers_user_id_fk FOREIGN KEY (id) REFERENCES users (id) ON UPDATE CASCADE ON DELETE CASCADE
);
CREATE UNIQUE INDEX IF NOT EXISTS customers_id_uindex ON customers (id);


-- artists table --
CREATE TABLE IF NOT EXISTS artists
(
  id       BIGINT PRIMARY KEY       NOT NULL,
  CONSTRAINT artists_user_id_fk FOREIGN KEY (id) REFERENCES users (id) ON UPDATE CASCADE ON DELETE CASCADE
);
CREATE UNIQUE INDEX IF NOT EXISTS artists_id_uindex ON customers (id);