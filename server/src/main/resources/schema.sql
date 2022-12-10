CREATE TABLE IF NOT EXISTS users (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
  name VARCHAR(255) NOT NULL,
  email VARCHAR(512) NOT NULL UNIQUE,
  CONSTRAINT pk_user PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS items (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
  owner_id BIGINT NOT NULL REFERENCES users (id),
  name VARCHAR(255) NOT NULL,
  description TEXT NOT NULL,
  available BOOLEAN NOT NULL,
  CONSTRAINT pk_item PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS booking (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
  item_id BIGINT NOT NULL REFERENCES items (id),
  booked_by_id BIGINT NOT NULL REFERENCES users (id),
  from_timestamp timestamp NOT NULL,
  to_timestamp timestamp NOT NULL,
  status VARCHAR not null default 'WAITING',
  review TEXT,
  CONSTRAINT pk_booking PRIMARY KEY (id)
);


CREATE TABLE IF NOT EXISTS requests (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
  author_id BIGINT NOT NULL REFERENCES users (id),
  description TEXT not null,
  status VARCHAR not null default 'OPEN',
  created_at timestamp not null default current_timestamp,
  CONSTRAINT pk_requests PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS item_suggestions (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
  request_id BIGINT NOT NULL REFERENCES requests (id),
  item_id BIGINT NOT NULL REFERENCES items (id),
  created_at timestamp not null default current_timestamp,
  CONSTRAINT pk_item_requests PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS comments (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
  item_id BIGINT NOT NULL REFERENCES items (id),
  author_id BIGINT NOT NULL REFERENCES users (id),
  review TEXT NOT NULL,
  created_at timestamp not null default current_timestamp,
  CONSTRAINT pk_comments PRIMARY KEY (id)
);