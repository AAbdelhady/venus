CREATE TABLE IF NOT EXISTS bookings
(
  id          BIGINT PRIMARY KEY       NOT NULL,
  customer_id BIGINT                   NOT NULL,
  artist_id   BIGINT                   NOT NULL,
  message     VARCHAR(255)             NOT NULL,
  created     TIMESTAMP WITH TIME ZONE NOT NULL,
  modified    TIMESTAMP WITH TIME ZONE NOT NULL,
  CONSTRAINT bookings_customer_id_fk FOREIGN KEY (customer_id) REFERENCES customers (user_id) ON UPDATE CASCADE ON DELETE CASCADE,
  CONSTRAINT bookings_artist_id_fk FOREIGN KEY (artist_id) REFERENCES artists (user_id) ON UPDATE CASCADE ON DELETE CASCADE
);
CREATE SEQUENCE IF NOT EXISTS bookings_seq;
CREATE INDEX IF NOT EXISTS bookings_customer_id_index ON bookings (customer_id);
CREATE INDEX IF NOT EXISTS bookings_artist_id_index ON bookings (artist_id);