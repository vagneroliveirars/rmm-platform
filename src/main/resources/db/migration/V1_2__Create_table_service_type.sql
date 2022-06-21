CREATE TABLE service_type (
	id bigserial NOT NULL,
	description varchar(255) NOT NULL,
	CONSTRAINT service_type_pkey PRIMARY KEY (id)
);