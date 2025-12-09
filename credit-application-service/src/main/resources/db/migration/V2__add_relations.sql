-- Affiliate (1) -> (N) Applications
ALTER TABLE credit_applications
    ADD CONSTRAINT fk_application_affiliate
        FOREIGN KEY (affiliate_id) REFERENCES affiliates(id);

-- Application (1) -> (1) Evaluation (La FK está en evaluations apuntando a credit_applications)
ALTER TABLE evaluations
    ADD CONSTRAINT fk_evaluation_application
        FOREIGN KEY (application_id) REFERENCES credit_applications(id);