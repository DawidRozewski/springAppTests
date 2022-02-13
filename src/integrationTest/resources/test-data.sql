INSERT INTO user(id, user_name) VALUES (1, 'user');

INSERT INTO privileges(id, user_id, name, active)
VALUES (1, 1, 'createPolicy', 1);

INSERT INTO customer (id, name, active)
VALUES (1, 'Customer', 1);

INSERT INTO policy_configuration(id, amount_discount, percent_discount)
VALUES (1, 10.00, 15.00);
INSERT INTO policy_configuration(id, amount_discount, percent_discount)
VALUES (2, 10.10, 15.10);

INSERT INTO customer_policy_configuration(id, amount_discount, percent_discount, policy_configuration_id)
VALUES (1, 10.00, 15.00, 1);

INSERT INTO policy (id, customer_id, status, parent_id, created, configuration_id, creator_id)
VALUES (1, 1, 'ACTIVE', 0, '2020-05-01T12:00:00.000Z', 1, 1);
