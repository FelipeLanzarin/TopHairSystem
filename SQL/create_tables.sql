DROP TABLE user_branch;
DROP TABLE balance;
DROP TABLE commerce_item;
DROP TABLE image;
DROP TABLE product;
DROP TABLE transaction;
DROP TABLE installment;
DROP TABLE payment;
DROP TABLE ths_order;
DROP TABLE profile;
DROP TABLE employee;
DROP TABLE sub_category;
DROP TABLE category;
DROP TABLE cashier;
DROP TABLE account;
DROP TABLE branch_company;
DROP TABLE company;
DROP TABLE city;

CREATE TABLE city
(
  id integer NOT NULL,
  country character varying(255),
  name character varying(255),
  uf character varying(255),
  CONSTRAINT city_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE city
  OWNER TO thsadmin;
  
  
CREATE TABLE company
(
  id integer NOT NULL,
  address character varying(255),
  cep character varying(255),
  cnpj character varying(255),
  creation_date timestamp without time zone,
  email character varying(255),
  name character varying(255),
  neighborhood character varying(255),
  "number" character varying(255),
  telephone character varying(255),
  city_id integer,
  CONSTRAINT company_pkey PRIMARY KEY (id),
  CONSTRAINT fk_8bncevv4ukevxceln84sgtayl FOREIGN KEY (city_id)
      REFERENCES city (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE company
  OWNER TO thsadmin;

 CREATE TABLE branch_company
(
  id integer NOT NULL,
  address character varying(255),
  branchid character varying(255),
  cep character varying(255),
  creation_date timestamp without time zone,
  description character varying(9999),
  email character varying(255),
  neighborhood character varying(255),
  "number" character varying(255),
  telephone character varying(255),
  city_id integer,
  company_id integer,
  CONSTRAINT branch_company_pkey PRIMARY KEY (id),
  CONSTRAINT fk_621o12aeu3tjm4x3bbaa58f3f FOREIGN KEY (city_id)
      REFERENCES city (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_wt7xpxx7j1h847101ye6bfnj FOREIGN KEY (company_id)
      REFERENCES company (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE branch_company
  OWNER TO thsadmin;



  
CREATE TABLE account
(
  id integer NOT NULL,
  amount double precision,
  date timestamp without time zone,
  description character varying(9999),
  payment_method character varying(255),
  branch_company_id integer,
  CONSTRAINT account_pkey PRIMARY KEY (id),
  CONSTRAINT fk_37ohi6y5lej8ooeid1l2rg8bf FOREIGN KEY (branch_company_id)
      REFERENCES branch_company (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE account
  OWNER TO thsadmin;

  
CREATE TABLE cashier
(
  id integer NOT NULL,
  balance double precision,
  description character varying(9999),
  branch_company_id integer,
  CONSTRAINT cashier_pkey PRIMARY KEY (id),
  CONSTRAINT fk_ex8avno44idbg5gecp5866qfw FOREIGN KEY (branch_company_id)
      REFERENCES branch_company (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE cashier
  OWNER TO thsadmin;

  

CREATE TABLE category
(
  id integer NOT NULL,
  description character varying(9999),
  name character varying(255),
  company_id integer,
  CONSTRAINT category_pkey PRIMARY KEY (id),
  CONSTRAINT fk_mmthtxh45dp98qrbv0kma3vc9 FOREIGN KEY (company_id)
      REFERENCES company (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE category
  OWNER TO thsadmin;




CREATE TABLE sub_category
(
  id integer NOT NULL,
  description character varying(9999),
  name character varying(255),
  category_id integer,
  CONSTRAINT sub_category_pkey PRIMARY KEY (id),
  CONSTRAINT fk_gua6bdolxg704ggnoeq1qr7er FOREIGN KEY (category_id)
      REFERENCES category (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE sub_category
  OWNER TO thsadmin;




  
  
 CREATE TABLE employee
(
  id integer NOT NULL,
  active boolean,
  address character varying(255),
  cep character varying(255),
  color character varying(255),
  cpf character varying(255),
  creationdate timestamp without time zone,
  email character varying(255),
  name character varying(255),
  neighborhood character varying(255),
  "number" character varying(255),
  telephone character varying(255),
  branch_company_id integer,
  city_id integer,
  company_id integer,
  CONSTRAINT employee_pkey PRIMARY KEY (id),
  CONSTRAINT fk_d7qxnluxvwgqihmldw2fqoqg8 FOREIGN KEY (city_id)
      REFERENCES city (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_dsfuhat4uiyl00yg4iaqipin7 FOREIGN KEY (branch_company_id)
      REFERENCES branch_company (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_k20labyfxi4i3ftuv4yv5age9 FOREIGN KEY (company_id)
      REFERENCES company (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE employee
  OWNER TO thsadmin;

  

CREATE TABLE profile
(
  id integer NOT NULL,
  address character varying(255),
  cep character varying(255),
  cpf character varying(255),
  creation_date timestamp without time zone,
  email character varying(255),
  name character varying(255),
  neighborhood character varying(255),
  "number" character varying(255),
  password character varying(255),
  telephone character varying(255),
  city_id integer,
  CONSTRAINT profile_pkey PRIMARY KEY (id),
  CONSTRAINT fk_keiy467n5h1qlwnteejmx2b2p FOREIGN KEY (city_id)
      REFERENCES city (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE profile
  OWNER TO thsadmin;







CREATE TABLE ths_order
(
  id integer NOT NULL,
  amount double precision,
  creation_date timestamp without time zone,
  description character varying(9999),
  discount double precision,
  is_attendance boolean,
  payment_status character varying(255),
  scheduler timestamp without time zone,
  status character varying(255),
  sub_total_amount double precision,
  employee_id integer,
  profile_id integer,
  CONSTRAINT ths_order_pkey PRIMARY KEY (id),
  CONSTRAINT fk_j1hakp1nypb0082biwse4g34 FOREIGN KEY (employee_id)
      REFERENCES employee (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_m94dkwu9pbe5yf0xmym8i0ryo FOREIGN KEY (profile_id)
      REFERENCES profile (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE ths_order
  OWNER TO thsadmin;





 
CREATE TABLE payment
(
  id integer NOT NULL,
  amount double precision,
  amount_received double precision,
  creation_date timestamp without time zone,
  description character varying(9999),
  last_payment_date timestamp without time zone,
  order_id integer,
  CONSTRAINT payment_pkey PRIMARY KEY (id),
  CONSTRAINT fk_mf7n8wo2rwrxsd6f3t9ub2mep FOREIGN KEY (order_id)
      REFERENCES ths_order (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE payment
  OWNER TO thsadmin;



CREATE TABLE installment
(
  id integer NOT NULL,
  amount double precision,
  amount_payed double precision,
  pay_date timestamp without time zone,
  payment_date timestamp without time zone,
  payment_method timestamp without time zone,
  payment_id integer,
  CONSTRAINT installment_pkey PRIMARY KEY (id),
  CONSTRAINT fk_ki3ul9xujpbhn53wbkipf6ejc FOREIGN KEY (payment_id)
      REFERENCES payment (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE installment
  OWNER TO thsadmin;
  



CREATE TABLE transaction
(
  id integer NOT NULL,
  amount double precision,
  description character varying(9999),
  "time" timestamp without time zone,
  type character varying(255),
  account_id integer,
  cashier_id integer,
  installment_id integer,
  CONSTRAINT transaction_pkey PRIMARY KEY (id),
  CONSTRAINT fk_1mklxpd6k7rx3hwstrwm8uk3c FOREIGN KEY (cashier_id)
      REFERENCES cashier (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_s3ryo9kppar0lp949w27yqkr4 FOREIGN KEY (installment_id)
      REFERENCES installment (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_syue16450hrqk910w0if4e778 FOREIGN KEY (account_id)
      REFERENCES account (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE transaction
  OWNER TO thsadmin;



CREATE TABLE product
(
  id integer NOT NULL,
  cost_price double precision,
  creation_date timestamp without time zone,
  description character varying(9999),
  name character varying(255),
  price double precision,
  type character varying(255),
  un integer,
  untype character varying(255),
  sub_category_id integer,
  CONSTRAINT product_pkey PRIMARY KEY (id),
  CONSTRAINT fk_4x4tagavlxmxc2h9550w5vqvo FOREIGN KEY (sub_category_id)
      REFERENCES sub_category (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE product
  OWNER TO thsadmin;





 
CREATE TABLE image
(
  id integer NOT NULL,
  description character varying(9999),
  partofprocess character varying(255),
  path character varying(255),
  order_id integer,
  CONSTRAINT image_pkey PRIMARY KEY (id),
  CONSTRAINT fk_g65dvp0vp7tqepm6x2f6rq74m FOREIGN KEY (order_id)
      REFERENCES ths_order (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE image
  OWNER TO thsadmin;


CREATE TABLE commerce_item
(
  id integer NOT NULL,
  amount double precision,
  cost_price double precision,
  creation_date timestamp without time zone,
  discount double precision,
  note character varying(255),
  quantity integer,
  unit_price double precision,
  order_id integer,
  product_id integer,
  CONSTRAINT commerce_item_pkey PRIMARY KEY (id),
  CONSTRAINT fk_1j9cnci44kahb5b8ve9xpbdqd FOREIGN KEY (product_id)
      REFERENCES product (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_gdm2q4k66s66etma7yle4cbvx FOREIGN KEY (order_id)
      REFERENCES ths_order (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)

WITH (
  OIDS=FALSE
);
ALTER TABLE commerce_item
  OWNER TO thsadmin;




  

 CREATE TABLE balance
(
  id integer NOT NULL,
  value double precision,
  branch_company_id integer,
  profile_id integer,
  CONSTRAINT balance_pkey PRIMARY KEY (id),
  CONSTRAINT fk_1vkmmbyisi8jm1cgmpcd9mxhe FOREIGN KEY (branch_company_id)
      REFERENCES branch_company (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_bdtd4cvor4b50ml0ewdmcxnmp FOREIGN KEY (profile_id)
      REFERENCES profile (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE balance
  OWNER TO thsadmin;




  CREATE TABLE user_branch
(
  login character varying(255) NOT NULL,
  password character varying(255),
  type character varying(255),
  CONSTRAINT user_branch_pkey PRIMARY KEY (login)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE user_branch
  OWNER TO thsadmin;

