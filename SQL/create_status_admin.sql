CREATE TABLE status_company
(
  id integer NOT NULL,
  name character varying(255),
  status boolean,
  CONSTRAINT balance_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE status_company
  OWNER TO thsadmin;


INSERT INTO status_company(id, name, status)
    VALUES (1, 'mari.ruas', true);
INSERT INTO status_company(id, name, status)
    VALUES (2, 'odon.moojen', true);