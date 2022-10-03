CREATE TABLE IF NOT EXISTS users (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
  name VARCHAR(255) NOT NULL,
  email VARCHAR(512) NOT NULL,
  CONSTRAINT pk_user PRIMARY KEY (id),
  CONSTRAINT UQ_USER_EMAIL UNIQUE (email)
);

CREATE TABLE IF NOT EXISTS items (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
  owner_id BIGINT NOT NULL REFERENCES users (id),
  name VARCHAR(255) NOT NULL,
  description TEXT NOT NULL,
  available BOOLEAN NOT NULL,
  CONSTRAINT pk_item PRIMARY KEY (id)
);

CREATE TYPE IF NOT EXISTS booking_status AS ENUM ('CURRENT', 'PAST', 'FUTURE', 'WAITING', 'REJECTED', 'APPROVED');

CREATE TABLE IF NOT EXISTS booking (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
  item_id BIGINT NOT NULL REFERENCES items (id),
  booked_by_id BIGINT NOT NULL REFERENCES users (id),
  from_timestamp timestamp NOT NULL,
  to_timestamp timestamp NOT NULL,
  status booking_status not null default 'WAITING',
  review TEXT NOT NULL,
  CONSTRAINT pk_booking PRIMARY KEY (id)
);

CREATE TYPE IF NOT EXISTS item_status AS ENUM ('OPEN', 'CLOSED');

CREATE TABLE IF NOT EXISTS item_requests (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
  item_id BIGINT NOT NULL REFERENCES items (id),
  author_id BIGINT NOT NULL REFERENCES users (id),
  status item_status not null default 'OPEN',
  CONSTRAINT pk_item_requests PRIMARY KEY (id)
);