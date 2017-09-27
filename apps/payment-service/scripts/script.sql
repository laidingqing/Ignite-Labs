CREATE DATABASE IF NOT EXISTS apache_ignite;

CREATE TABLE PAYMENT (

  id           BIGINT,
  amount       DECIMAL,
  purpose      VARCHAR(200),
  creationDate DATE,
  status       VARCHAR(50)

);