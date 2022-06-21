CREATE TABLE device (
	id bigserial NOT NULL,
	system_name varchar(255) NOT NULL,
	uuid uuid NOT NULL,
	type_id int8 NOT NULL,
	CONSTRAINT device_pkey PRIMARY KEY (id),
	CONSTRAINT device_uuid_uk UNIQUE (uuid),
	CONSTRAINT device_type_id_fk FOREIGN KEY (type_id) REFERENCES device_type(id)
);