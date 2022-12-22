CREATE TABLE accident (
  id SERIAL PRIMARY KEY,
  name TEXT,
  text TEXT,
  address TEXT,
  type_id INTEGER NOT NULL REFERENCES accident_type(id)
);