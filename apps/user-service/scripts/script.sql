USE apache_ignite;

CREATE TABLE USERS (

  id           bigint PRIMARY KEY,
  name         text,
  email        text,
  creationDate timestamp,
  status       text

);