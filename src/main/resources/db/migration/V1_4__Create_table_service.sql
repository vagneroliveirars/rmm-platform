CREATE TABLE service (
	id bigserial NOT NULL,
	description varchar(255) NOT NULL,
	type_id int8 NOT NULL,
	CONSTRAINT service_pkey PRIMARY KEY (id),
	CONSTRAINT service_type_id_uk UNIQUE (type_id),
	CONSTRAINT service_type_id_fk FOREIGN KEY (type_id) REFERENCES service_type(id)
);