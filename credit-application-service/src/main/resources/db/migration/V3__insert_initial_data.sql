-- =================================================================================
-- 1. AFFILIATES (With explicit IDs to ensure relationships)
-- =================================================================================

-- ID: 1 - Pepito (Meets all criteria)
INSERT INTO affiliates (id, document, name, salary, affiliation_date, enabled)
VALUES (1, '1020304050', 'Pepito Pérez', 3500000.00, DATEADD('MONTH', -8, CURRENT_DATE), TRUE);

-- ID: 2 - Juan (Varied history)
INSERT INTO affiliates (id, document, name, salary, affiliation_date, enabled)
VALUES (2, '9080706050', 'Juan Historial', 5000000.00, DATEADD('MONTH', -24, CURRENT_DATE), TRUE);

-- ID: 3 - María (Low salary - New PENDING case)
INSERT INTO affiliates (id, document, name, salary, affiliation_date, enabled)
VALUES (3, '1122334455', 'Maria Pobre', 1000000.00, DATEADD('MONTH', -12, CURRENT_DATE), TRUE);


-- =================================================================================
-- 2. JUAN'S APPLICATIONS (ID 2)
-- =================================================================================

-- ID: 1 -> REJECTED (Low score)
INSERT INTO credit_applications (id, affiliate_id, amount, term, proposal_rate, application_date, status)
VALUES (1, 2, 5000000.00, 12, 1.5, DATEADD('MONTH', -3, CURRENT_DATE), 'RECHAZADO');

INSERT INTO evaluations (id, application_id, score, risk_level, reason, evaluation_date)
VALUES (1, 1, 400, 'ALTO', 'RECHAZADO: Score insuficiente y Riesgo Alto.', DATEADD('MONTH', -3, CURRENT_TIMESTAMP));


-- ID: 2 -> REJECTED (High amount / Payment capacity)
INSERT INTO credit_applications (id, affiliate_id, amount, term, proposal_rate, application_date, status)
VALUES (2, 2, 80000000.00, 24, 1.8, DATEADD('MONTH', -2, CURRENT_DATE), 'RECHAZADO');

INSERT INTO evaluations (id, application_id, score, risk_level, reason, evaluation_date)
VALUES (2, 2, 750, 'MEDIO', 'RECHAZADO: Capacidad de pago insuficiente.', DATEADD('MONTH', -2, CURRENT_TIMESTAMP));


-- ID: 3 -> APPROVED
INSERT INTO credit_applications (id, affiliate_id, amount, term, proposal_rate, application_date, status)
VALUES (3, 2, 2000000.00, 6, 1.2, DATEADD('MONTH', -1, CURRENT_DATE), 'APROBADO');

INSERT INTO evaluations (id, application_id, score, risk_level, reason, evaluation_date)
VALUES (3, 3, 850, 'BAJO', 'Solicitud APROBADA satisfactoriamente.', DATEADD('MONTH', -1, CURRENT_TIMESTAMP));


-- ID: 4 -> PENDING (No evaluation)
INSERT INTO credit_applications (id, affiliate_id, amount, term, proposal_rate, application_date, status)
VALUES (4, 2, 1000000.00, 12, 1.5, CURRENT_DATE, 'PENDIENTE');


-- =================================================================================
-- 3. MARÍA'S APPLICATIONS (ID 3)
-- =================================================================================

-- ID: 5 -> PENDING (To test capacity rules when processing)
-- Note: María earns 1,000,000. Asks for 500,000 over 12 months. Should be able to afford it.
INSERT INTO credit_applications (id, affiliate_id, amount, term, proposal_rate, application_date, status)
VALUES (5, 3, 500000.00, 12, 1.5, CURRENT_DATE, 'PENDIENTE');


-- =================================================================================
-- 4. RESTART COUNTERS (RESTART IDs)
-- =================================================================================
-- Affiliates: We have 1, 2, 3 -> Next: 4
ALTER TABLE affiliates ALTER COLUMN id RESTART WITH 4;

-- Applications: We have 1, 2, 3, 4, 5 -> Next: 6
ALTER TABLE credit_applications ALTER COLUMN id RESTART WITH 6;

-- Evaluations: We have 1, 2, 3 -> Next: 4
ALTER TABLE evaluations ALTER COLUMN id RESTART WITH 4;