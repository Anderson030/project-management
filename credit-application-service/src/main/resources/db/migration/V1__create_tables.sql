CREATE TABLE affiliates (
                            id BIGINT AUTO_INCREMENT PRIMARY KEY,
                            document VARCHAR(20) NOT NULL UNIQUE,
                            name VARCHAR(100) NOT NULL,
                            salary DECIMAL(19, 2) NOT NULL,
                            affiliation_date DATE NOT NULL,
                            enabled BOOLEAN DEFAULT TRUE
);

CREATE TABLE credit_applications (
                                     id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                     affiliate_id BIGINT, -- Se relacionará en la V2
                                     amount DECIMAL(19, 2) NOT NULL,
                                     term INT NOT NULL,
                                     proposal_rate DECIMAL(19, 2) NOT NULL,
                                     application_date DATE NOT NULL,
                                     status VARCHAR(20) NOT NULL
);

CREATE TABLE evaluations (
                             id BIGINT AUTO_INCREMENT PRIMARY KEY,
                             application_id BIGINT, -- Se relacionará en la V2
                             score INT NOT NULL,
                             risk_level VARCHAR(20) NOT NULL,
                             reason TEXT,
                             evaluation_date TIMESTAMP NOT NULL
);