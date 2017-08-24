CREATE TABLE GENERATOR (
	GEN_NAME VARCHAR(20) NOT NULL,
	GEN_VALUE BIGINT NOT NULL DEFAULT 1000,
	PRIMARY KEY (GEN_NAME)
) ENGINE=INNODB;

CREATE TABLE CUSTOMER (
	CUSTOMER_KEY BIGINT NOT NULL,
	CUSTOMER_NAME VARCHAR(64),
	PRIMARY KEY (CUSTOMER_KEY)
) ENGINE=INNODB;

CREATE TABLE USER_ACCOUNT (
	USER_ACCOUNT_KEY BIGINT NOT NULL,
	USERNAME VARCHAR(64) NOT NULL,
	CUSTOMER_KEY BIGINT NOT NULL,
	FIRST_NAME VARCHAR(64),
	LAST_NAME VARCHAR(64),
	BIRTHDATE DATE,
	EMAIL VARCHAR(255) NOT NULL,
    ENABLED BIT NOT NULL,
	PASSWORD VARCHAR(80) NOT NULL,
	PRIMARY KEY (USER_ACCOUNT_KEY),
	CONSTRAINT FK_USER_ACCOUNT_CUSTOMER FOREIGN KEY (CUSTOMER_KEY) REFERENCES CUSTOMER (CUSTOMER_KEY),
    UNIQUE INDEX (USERNAME),
    UNIQUE INDEX (EMAIL)
) ENGINE=INNODB;

CREATE TABLE ROLE (
	ROLE_KEY BIGINT NOT NULL,
	ROLE_NAME VARCHAR(64) NOT NULL,
	PRIMARY KEY (ROLE_KEY)
) ENGINE=INNODB;

CREATE TABLE USER_ACCOUNT_ROLE (
	USER_ACCOUNT_KEY BIGINT NOT NULL,
	ROLE_KEY BIGINT NOT NULL,
	PRIMARY KEY(USER_ACCOUNT_KEY, ROLE_KEY),
	CONSTRAINT FK_ROLE_ACCOUNT FOREIGN KEY (ROLE_KEY) REFERENCES ROLE (ROLE_KEY),
    CONSTRAINT FK_ACCOUNT_ROLE FOREIGN KEY (USER_ACCOUNT_KEY) REFERENCES USER_ACCOUNT (USER_ACCOUNT_KEY)
) ENGINE=INNODB;

CREATE TABLE EDUCATION_INSTITUTION (
	EDUCATION_INSTITUTION_KEY BIGINT NOT NULL,
	NAME VARCHAR(128) NOT NULL,
	SHORT_NAME VARCHAR(16),
	COUNTRY CHAR(2),
	CITY VARCHAR(32),
	PRIMARY KEY (EDUCATION_INSTITUTION_KEY)
) ENGINE=INNODB;

CREATE TABLE SPECIALITY (
	SPECIALITY_KEY BIGINT NOT NULL,
	CODE VARCHAR(16) NOT NULL,
	TITLE VARCHAR(128) NOT NULL,
	STATUS CHAR(1) NOT NULL,
	PRIMARY KEY (SPECIALITY_KEY),
	CONSTRAINT CHK_SPECIALITY_STATUS CHECK (STATUS IN ('A', 'I'))
) ENGINE=INNODB;

CREATE TABLE EINSTITUTION_SPECIALITY (
	EDUCATION_INSTITUTION_KEY BIGINT NOT NULL,
	SPECIALITY_KEY BIGINT NOT NULL,
	PRIMARY KEY(EDUCATION_INSTITUTION_KEY, SPECIALITY_KEY),
	CONSTRAINT FK_EINSTITUTION_SPECIALITY FOREIGN KEY (EDUCATION_INSTITUTION_KEY) REFERENCES EDUCATION_INSTITUTION (EDUCATION_INSTITUTION_KEY),
	CONSTRAINT FK_SPECIALITY_EINSTITUTION FOREIGN KEY (SPECIALITY_KEY) REFERENCES SPECIALITY (SPECIALITY_KEY)
) ENGINE=INNODB;

CREATE TABLE ENROLLEE (
	ENROLLEE_KEY BIGINT NOT NULL,
	FIRST_NAME VARCHAR(64) NOT NULL,
	MIDDLE_NAME VARCHAR(64) NOT NULL,
	LAST_NAME VARCHAR(64) NOT NULL,
	BIRTHDATE DATE NOT NULL,
	PHONE VARCHAR(16) NOT NULL,
	EMAIL VARCHAR(256),
	
	DOCUMENT_TYPE CHAR(1) NOT NULL,
	ID VARCHAR(16) NOT NULL,
	DOCUMENT_ID VARCHAR(16) NOT NULL,
	DOCUMENT_ISSUE_DATE DATE NOT NULL,
	DOCUMENT_ISSUED_BY VARCHAR(64) NOT NULL,
	
	COUNTRY CHAR(2) NOT NULL,
	CITY VARCHAR(32) NOT NULL,
	STREET VARCHAR(32) NOT NULL,
	HOUSE VARCHAR(8) NOT NULL,
	HOUSING VARCHAR(8),
	FLAT VARCHAR(8),
	ZIP_CODE VARCHAR(10),
	PRIMARY KEY (ENROLLEE_KEY),
	CONSTRAINT CHK_DOCUMENT_TYPE CHECK (STATUS IN ('P'))
) ENGINE=INNODB;

CREATE TABLE CERTIFICATE (
	CERTIFICATE_KEY BIGINT NOT NULL,
	INSTITUTION VARCHAR(128) NOT NULL,
	YEAR INT NOT NULL,
	ENROLLEE_KEY BIGINT NOT NULL,
	PRIMARY KEY (CERTIFICATE_KEY),
	CONSTRAINT FK_ENROLLEE_CERTIFICATE FOREIGN KEY (ENROLLEE_KEY) REFERENCES ENROLLEE (ENROLLEE_KEY)
) ENGINE=INNODB;

CREATE TABLE SUBJECT (
	SUBJECT_KEY BIGINT NOT NULL,
	TITLE VARCHAR(32) NOT NULL,
	PRIMARY KEY (SUBJECT_KEY),
	UNIQUE INDEX (TITLE)
) ENGINE=INNODB;

CREATE TABLE CERTIFICATE_SUBJECT (
	SUBJECT_KEY BIGINT NOT NULL,
	CERTIFICATE_KEY BIGINT NOT NULL,
	MARK TINYINT NOT NULL,
	CONSTRAINT FK_CERTIFICATE_SUBJECT FOREIGN KEY (SUBJECT_KEY) REFERENCES SUBJECT (SUBJECT_KEY),
	CONSTRAINT FK_SUBJECT_CERTIFICATE FOREIGN KEY (CERTIFICATE_KEY) REFERENCES CERTIFICATE (CERTIFICATE_KEY)
) ENGINE=INNODB;

CREATE TABLE REGISTRATION (
	REGISTRATION_KEY BIGINT NOT NULL,
	REGISTRATION_DATE DATE NOT NULL,
	ENROLLEE_KEY BIGINT NOT NULL,
	REGISTERED_BY BIGINT NOT NULL,
	EDUCATION_INSTITUTION_KEY BIGINT NOT NULL,
	SPECIALITY_KEY BIGINT NOT NULL,
	CERTIFICATE_KEY BIGINT NOT NULL,
	PRIMARY KEY (REGISTRATION_KEY),
	CONSTRAINT FK_REGISTRATION_EN FOREIGN KEY (ENROLLEE_KEY) REFERENCES ENROLLEE (ENROLLEE_KEY),
	CONSTRAINT FK_REGISTRATION_UA FOREIGN KEY (REGISTERED_BY) REFERENCES USER_ACCOUNT (USER_ACCOUNT_KEY),
	CONSTRAINT FK_REGISTRATION_EI FOREIGN KEY (EDUCATION_INSTITUTION_KEY) REFERENCES EDUCATION_INSTITUTION (EDUCATION_INSTITUTION_KEY),
	CONSTRAINT FK_REGISTRATION_SP FOREIGN KEY (SPECIALITY_KEY) REFERENCES SPECIALITY (SPECIALITY_KEY),
	CONSTRAINT FK_REGISTRATION_CE FOREIGN KEY (CERTIFICATE_KEY) REFERENCES CERTIFICATE (CERTIFICATE_KEY)
) ENGINE=INNODB;

CREATE TABLE REPORT_TEMPLATE (
	REPORT_TEMPLATE_KEY BIGINT NOT NULL,
	EDUCATION_INSTITUTION_KEY BIGINT NOT NULL,
	FILE_EXTENSION VARCHAR(4) NOT NULL,
	TITLE VARCHAR(80) NOT NULL,
	TEMPLATE LONGBLOB NOT NULL,
	PRIMARY KEY (REPORT_TEMPLATE_KEY),
	CONSTRAINT FK_TEMPLATE_EI FOREIGN KEY (EDUCATION_INSTITUTION_KEY) REFERENCES EDUCATION_INSTITUTION (EDUCATION_INSTITUTION_KEY)
) ENGINE=INNODB;