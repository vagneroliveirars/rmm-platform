CREATE TABLE device_service (
	device_id int8 NOT NULL,
	service_id int8 NOT NULL,
	CONSTRAINT device_service_pkey PRIMARY KEY (device_id, service_id),
	CONSTRAINT device_service_device_id_fk FOREIGN KEY (device_id) REFERENCES device(id),
	CONSTRAINT device_service_service_id_fk FOREIGN KEY (service_id) REFERENCES service(id)
);