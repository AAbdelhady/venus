-- users table --
CREATE TABLE IF NOT EXISTS users
(
  id                  BIGINT PRIMARY KEY       NOT NULL,
  login_id            VARCHAR(64)              NOT NULL,
  first_name          VARCHAR(64)              NOT NULL,
  last_name           VARCHAR(64)              NOT NULL,
  role                VARCHAR(64)              NOT NULL,
  auth_provider       VARCHAR(64)              NOT NULL,
  email               VARCHAR(64)              NOT NULL,
  phone               VARCHAR(64),
  profile_picture_url VARCHAR(256),
  created             TIMESTAMP WITH TIME ZONE NOT NULL,
  modified            TIMESTAMP WITH TIME ZONE NOT NULL,
  CONSTRAINT users_login_id_unique UNIQUE (login_id)
);
CREATE SEQUENCE IF NOT EXISTS users_seq;
CREATE UNIQUE INDEX IF NOT EXISTS users_id_uindex ON users (id);
CREATE UNIQUE INDEX IF NOT EXISTS users_login_id_uindex ON users (login_id);
CREATE INDEX IF NOT EXISTS users_modified_index ON users (modified);


-- customers table --
CREATE TABLE IF NOT EXISTS customers
(
  user_id BIGINT PRIMARY KEY NOT NULL,
  CONSTRAINT customers_user_id_fk FOREIGN KEY (user_id) REFERENCES users (id) ON UPDATE CASCADE ON DELETE CASCADE
);
CREATE UNIQUE INDEX IF NOT EXISTS customers_id_uindex ON customers (user_id);


-- artists table --
CREATE TABLE IF NOT EXISTS artists
(
  user_id     BIGINT PRIMARY KEY NOT NULL,
  active      BOOLEAN            NOT NULL DEFAULT FALSE,
  artist_nick VARCHAR(64)        NOT NULL,
  CONSTRAINT artists_user_id_fk FOREIGN KEY (user_id) REFERENCES users (id) ON UPDATE CASCADE ON DELETE CASCADE
);
CREATE UNIQUE INDEX IF NOT EXISTS artists_id_uindex ON customers (user_id);