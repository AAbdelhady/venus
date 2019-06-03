CREATE TABLE IF NOT EXISTS appointments
(
  id               BIGINT PRIMARY KEY       NOT NULL,
  customer_id      BIGINT                   NOT NULL,
  artist_id        BIGINT                   NOT NULL,
  appointment_time TIMESTAMP WITH TIME ZONE NOT NULL,
  created          TIMESTAMP WITH TIME ZONE NOT NULL,
  modified         TIMESTAMP WITH TIME ZONE NOT NULL,
  CONSTRAINT appointments_customer_id_fk FOREIGN KEY (customer_id) REFERENCES customers (user_id) ON UPDATE CASCADE ON DELETE CASCADE,
  CONSTRAINT appointments_artist_id_fk FOREIGN KEY (artist_id) REFERENCES artists (user_id) ON UPDATE CASCADE ON DELETE CASCADE
);
CREATE SEQUENCE IF NOT EXISTS appointments_seq;
CREATE INDEX IF NOT EXISTS appointments_customer_id_index ON appointments (customer_id);
CREATE INDEX IF NOT EXISTS appointments_artist_id_index ON appointments (artist_id);