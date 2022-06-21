-- device_type
INSERT INTO device_type (description) VALUES('Windows Workstation');
INSERT INTO device_type (description) VALUES('Windows Server');
INSERT INTO device_type (description) VALUES('Mac');
INSERT INTO device_type (description) VALUES('Linux Workstation');
INSERT INTO device_type (description) VALUES('Linux Server');

-- device
INSERT INTO device (uuid, system_name, type_id) VALUES('630d2ebe-b948-456d-a7f0-0ec8491cab85', 'Windows 10', 1);
INSERT INTO device (uuid, system_name, type_id) VALUES('b446666b-a114-4090-8a8f-6c00959f805e', 'Windows 11', 2);
INSERT INTO device (uuid, system_name, type_id) VALUES('cba6eb0a-4b13-47cc-bb80-6a50f8395174', 'macOS Monterey', 3);
INSERT INTO device (uuid, system_name, type_id) VALUES('3e7d04fa-6b0c-4b60-a850-6341f6ebf66d', 'macOS Big Sur', 3);
INSERT INTO device (uuid, system_name, type_id) VALUES('a9fd54e0-7811-415a-b93e-92d970d48717', 'macOS Catalina', 3);

-- service_type
INSERT INTO service_type (description) VALUES('Device');
INSERT INTO service_type (description) VALUES('Antivirus');
INSERT INTO service_type (description) VALUES('Backup');
INSERT INTO service_type (description) VALUES('PSA');
INSERT INTO service_type (description) VALUES('Screen Share');

-- service
INSERT INTO service (description, type_id) VALUES('Desktops and notebooks', 1);
INSERT INTO service (description, type_id) VALUES('Program designed to detect and remove viruses and other kinds of malicious software', 2);
INSERT INTO service (description, type_id) VALUES('Copy of data saved in the cloud', 3);
INSERT INTO service (description, type_id) VALUES('Professional Services Automation (PSA)', 4);
INSERT INTO service (description, type_id) VALUES('Sharing access to a given computer screen', 5);

-- service_cost
INSERT INTO service_cost (amount, device_type_id, service_id) VALUES(4, 1, 1);
INSERT INTO service_cost (amount, device_type_id, service_id) VALUES(4, 2, 1);
INSERT INTO service_cost (amount, device_type_id, service_id) VALUES(4, 3, 1);
INSERT INTO service_cost (amount, device_type_id, service_id) VALUES(4, 4, 1);
INSERT INTO service_cost (amount, device_type_id, service_id) VALUES(4, 5, 1);

INSERT INTO service_cost (amount, device_type_id, service_id) VALUES(5, 1, 2);
INSERT INTO service_cost (amount, device_type_id, service_id) VALUES(5, 2, 2);
INSERT INTO service_cost (amount, device_type_id, service_id) VALUES(7, 3, 2);
INSERT INTO service_cost (amount, device_type_id, service_id) VALUES(5, 4, 2);
INSERT INTO service_cost (amount, device_type_id, service_id) VALUES(5, 5, 2);

INSERT INTO service_cost (amount, device_type_id, service_id) VALUES(3, 1, 3);
INSERT INTO service_cost (amount, device_type_id, service_id) VALUES(3, 2, 3);
INSERT INTO service_cost (amount, device_type_id, service_id) VALUES(3, 3, 3);
INSERT INTO service_cost (amount, device_type_id, service_id) VALUES(3, 4, 3);
INSERT INTO service_cost (amount, device_type_id, service_id) VALUES(3, 5, 3);

INSERT INTO service_cost (amount, device_type_id, service_id) VALUES(2, 1, 4);
INSERT INTO service_cost (amount, device_type_id, service_id) VALUES(2, 2, 4);
INSERT INTO service_cost (amount, device_type_id, service_id) VALUES(2, 3, 4);
INSERT INTO service_cost (amount, device_type_id, service_id) VALUES(2, 4, 4);
INSERT INTO service_cost (amount, device_type_id, service_id) VALUES(2, 5, 4);

INSERT INTO service_cost (amount, device_type_id, service_id) VALUES(1, 1, 5);
INSERT INTO service_cost (amount, device_type_id, service_id) VALUES(1, 2, 5);
INSERT INTO service_cost (amount, device_type_id, service_id) VALUES(1, 3, 5);
INSERT INTO service_cost (amount, device_type_id, service_id) VALUES(1, 4, 5);
INSERT INTO service_cost (amount, device_type_id, service_id) VALUES(1, 5, 5);

-- device_service
INSERT INTO device_service (device_id, service_id) VALUES(1, 1);
INSERT INTO device_service (device_id, service_id) VALUES(2, 1);
INSERT INTO device_service (device_id, service_id) VALUES(3, 1);
INSERT INTO device_service (device_id, service_id) VALUES(4, 1);
INSERT INTO device_service (device_id, service_id) VALUES(5, 1);

INSERT INTO device_service (device_id, service_id) VALUES(1, 2);
INSERT INTO device_service (device_id, service_id) VALUES(2, 2);
INSERT INTO device_service (device_id, service_id) VALUES(3, 2);
INSERT INTO device_service (device_id, service_id) VALUES(4, 2);
INSERT INTO device_service (device_id, service_id) VALUES(5, 2);

INSERT INTO device_service (device_id, service_id) VALUES(1, 3);
INSERT INTO device_service (device_id, service_id) VALUES(2, 3);
INSERT INTO device_service (device_id, service_id) VALUES(3, 3);
INSERT INTO device_service (device_id, service_id) VALUES(4, 3);
INSERT INTO device_service (device_id, service_id) VALUES(5, 3);

INSERT INTO device_service (device_id, service_id) VALUES(1, 5);
INSERT INTO device_service (device_id, service_id) VALUES(2, 5);
INSERT INTO device_service (device_id, service_id) VALUES(3, 5);
INSERT INTO device_service (device_id, service_id) VALUES(4, 5);
INSERT INTO device_service (device_id, service_id) VALUES(5, 5);