CREATE TABLE device_type (
	id bigserial NOT NULL,
	description varchar(255) NOT NULL,
	CONSTRAINT device_type_pkey PRIMARY KEY (id)
);