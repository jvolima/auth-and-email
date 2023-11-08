CREATE TABLE tb_user
(
    id                                    UUID         NOT NULL,
    firstname                             VARCHAR(255) NOT NULL,
    lastname                              VARCHAR(255) NOT NULL,
    email                                 VARCHAR(255) NOT NULL,
    password                              VARCHAR(255) NOT NULL,
    role                                  VARCHAR(255),
    enabled                               BOOLEAN,
    verification_token                    VARCHAR(255),
    change_password_token                 VARCHAR(255),
    change_password_token_expiration_date TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_tb_user PRIMARY KEY (id)
);

ALTER TABLE tb_user
    ADD CONSTRAINT uc_tb_user_email UNIQUE (email);