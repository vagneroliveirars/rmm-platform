CREATE TABLE service_cost (
	id bigserial NOT NULL,
	amount numeric(19, 2) NOT NULL,
	device_type_id int8 NOT NULL,
	service_id int8 NOT NULL,
	CONSTRAINT service_cost_pkey PRIMARY KEY (id),
	CONSTRAINT service_cost_service_id_device_type_id_uk UNIQUE (service_id, device_type_id),
	CONSTRAINT service_cost_device_type_id_fk FOREIGN KEY (device_type_id) REFERENCES device_type(id),
	CONSTRAINT service_cost_service_id_fk FOREIGN KEY (service_id) REFERENCES service(id)
);