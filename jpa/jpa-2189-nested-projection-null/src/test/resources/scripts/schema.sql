CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE product (
	id uuid NOT NULL DEFAULT uuid_generate_v4(),
	"name" varchar(100) NOT NULL,
	description text NULL,
	featured_media varchar(200) NOT NULL,
	status varchar(10) NOT NULL DEFAULT 'DRAFT'::character varying,
	tags _varchar NULL,
	created_date timestamptz NOT NULL,
	price numeric NULL DEFAULT 0,
	last_modified_date timestamptz NOT NULL,
	created_by varchar(100) NOT NULL,
	last_modified_by varchar(100) NOT NULL,
	CONSTRAINT product_id_pk PRIMARY KEY (id),
	CONSTRAINT product_name_unq UNIQUE (name)
);

CREATE TABLE product_details (
	id uuid NOT NULL,
	cost_price numeric NULL,
        CONSTRAINT product_detials_id_pk PRIMARY KEY (id)
);

ALTER TABLE product_details ADD CONSTRAINT product_details_id_fk FOREIGN KEY (id) REFERENCES product(id) ON UPDATE CASCADE ON DELETE CASCADE