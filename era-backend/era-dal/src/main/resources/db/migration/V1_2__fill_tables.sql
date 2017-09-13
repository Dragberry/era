INSERT INTO GENERATOR (GEN_NAME) VALUES ('CUSTOMER_GEN');
INSERT INTO GENERATOR (GEN_NAME) VALUES ('USER_ACCOUNT_GEN');
INSERT INTO GENERATOR (GEN_NAME) VALUES ('ROLE_GEN');
INSERT INTO GENERATOR (GEN_NAME) VALUES ('EINSTITUTION_GEN');
INSERT INTO GENERATOR (GEN_NAME) VALUES ('SPECIALTY_GEN');
INSERT INTO GENERATOR (GEN_NAME) VALUES ('ENROLLEE_GEN');
INSERT INTO GENERATOR (GEN_NAME) VALUES ('CERTIFICATE_GEN');
INSERT INTO GENERATOR (GEN_NAME) VALUES ('SUBJECT_GEN');
INSERT INTO GENERATOR (GEN_NAME) VALUES ('REGISTRATION_GEN');
INSERT INTO GENERATOR (GEN_NAME) VALUES ('REG_PERIOD_GEN');
INSERT INTO GENERATOR (GEN_NAME) VALUES ('REG_SPECIALTY_GEN');
INSERT INTO GENERATOR (GEN_NAME) VALUES ('REPORT_TEMPLATE_GEN');

INSERT INTO EDUCATION_INSTITUTION (EDUCATION_INSTITUTION_KEY, NAME, SHORT_NAME, COUNTRY, CITY)
	VALUES ((SELECT MAX(GEN_VALUE) FROM GENERATOR WHERE GEN_NAME = 'EINSTITUTION_GEN'), 'Филиал БГУИР "Минский радиотехнический колледж"', 'УО "МРК"', 'BY', 'Минск');
UPDATE GENERATOR SET GEN_VALUE = GEN_VALUE + 1 WHERE GEN_NAME = 'EINSTITUTION_GEN';
INSERT INTO EDUCATION_INSTITUTION (EDUCATION_INSTITUTION_KEY, NAME, SHORT_NAME, COUNTRY, CITY)
	VALUES ((SELECT MAX(GEN_VALUE) FROM GENERATOR WHERE GEN_NAME = 'EINSTITUTION_GEN'), 'УО "Тестовое"', 'УО "Тест"', 'BY', 'Минск');
UPDATE GENERATOR SET GEN_VALUE = GEN_VALUE + 1 WHERE GEN_NAME = 'EINSTITUTION_GEN';

INSERT INTO CUSTOMER (CUSTOMER_KEY, CUSTOMER_NAME, INSTITUTION_KEY) VALUES 
	((SELECT MAX(GEN_VALUE) FROM GENERATOR WHERE GEN_NAME = 'CUSTOMER_GEN'), 'УО "МРК"', (SELECT EDUCATION_INSTITUTION_KEY FROM EDUCATION_INSTITUTION WHERE NAME = 'Филиал БГУИР "Минский радиотехнический колледж"'));
UPDATE GENERATOR SET GEN_VALUE = GEN_VALUE + 1 WHERE GEN_NAME = 'CUSTOMER_GEN';
INSERT INTO CUSTOMER (CUSTOMER_KEY, CUSTOMER_NAME, INSTITUTION_KEY) VALUES 
	((SELECT MAX(GEN_VALUE) FROM GENERATOR WHERE GEN_NAME = 'CUSTOMER_GEN'), 'УО "Тест"', (SELECT EDUCATION_INSTITUTION_KEY FROM EDUCATION_INSTITUTION WHERE NAME = 'УО "Тестовое"'));
UPDATE GENERATOR SET GEN_VALUE = GEN_VALUE + 1 WHERE GEN_NAME = 'CUSTOMER_GEN';

INSERT INTO USER_ACCOUNT (USER_ACCOUNT_KEY, USERNAME, CUSTOMER_KEY, FIRST_NAME, LAST_NAME, BIRTHDATE, EMAIL, ENABLED, PASSWORD, PASSWORD_RESET_DATE)
	VALUES ((SELECT MAX(GEN_VALUE) FROM GENERATOR WHERE GEN_NAME = 'USER_ACCOUNT_GEN'), 'makseemka', (SELECT CUSTOMER_KEY FROM CUSTOMER WHERE CUSTOMER_NAME = 'УО "МРК"'), 'Максим', 'Драгун', '1991-02-04', 'max@dragberry.net', true, '$2a$04$QjQ1pKIFt.KZMUD/ZjhWFOBBLYOshUh9OMIZ5y76t61A2mNV8qGSG', '2017-08-25');
UPDATE GENERATOR SET GEN_VALUE = GEN_VALUE + 1 WHERE GEN_NAME = 'USER_ACCOUNT_GEN';
INSERT INTO USER_ACCOUNT (USER_ACCOUNT_KEY, USERNAME, CUSTOMER_KEY, FIRST_NAME, LAST_NAME, BIRTHDATE, EMAIL, ENABLED, PASSWORD, PASSWORD_RESET_DATE)
	VALUES ((SELECT MAX(GEN_VALUE) FROM GENERATOR WHERE GEN_NAME = 'USER_ACCOUNT_GEN'), 'chueshkovu', (SELECT CUSTOMER_KEY FROM CUSTOMER WHERE CUSTOMER_NAME = 'УО "МРК"'), 'Юрий', 'Чуешков', '1994-08-25', 'chueshkov@dragberry.net', true, '$2a$04$QjQ1pKIFt.KZMUD/ZjhWFOBBLYOshUh9OMIZ5y76t61A2mNV8qGSG', '2017-08-25');
UPDATE GENERATOR SET GEN_VALUE = GEN_VALUE + 1 WHERE GEN_NAME = 'USER_ACCOUNT_GEN';
INSERT INTO USER_ACCOUNT (USER_ACCOUNT_KEY, USERNAME, CUSTOMER_KEY, FIRST_NAME, LAST_NAME, BIRTHDATE, EMAIL, ENABLED, PASSWORD, PASSWORD_RESET_DATE)
	VALUES ((SELECT MAX(GEN_VALUE) FROM GENERATOR WHERE GEN_NAME = 'USER_ACCOUNT_GEN'), 'test', (SELECT CUSTOMER_KEY FROM CUSTOMER WHERE CUSTOMER_NAME = 'УО "Тест"'), 'Тест', 'Тестов', '1994-08-25', 'test@dragberry.net', true, '$2a$04$QjQ1pKIFt.KZMUD/ZjhWFOBBLYOshUh9OMIZ5y76t61A2mNV8qGSG', '2017-08-25');
UPDATE GENERATOR SET GEN_VALUE = GEN_VALUE + 1 WHERE GEN_NAME = 'USER_ACCOUNT_GEN';

-- Admin roles
INSERT INTO ROLE (ROLE_KEY, MODULE, ACTION)
	VALUES ((SELECT MAX(GEN_VALUE) FROM GENERATOR WHERE GEN_NAME = 'ROLE_GEN'), 'ADMIN', 'VIEW');
UPDATE GENERATOR SET GEN_VALUE = GEN_VALUE + 1 WHERE GEN_NAME = 'ROLE_GEN';
-- Registrations roles
INSERT INTO ROLE (ROLE_KEY, MODULE, ACTION)
	VALUES ((SELECT MAX(GEN_VALUE) FROM GENERATOR WHERE GEN_NAME = 'ROLE_GEN'), 'REGISTRATIONS', 'VIEW');
UPDATE GENERATOR SET GEN_VALUE = GEN_VALUE + 1 WHERE GEN_NAME = 'ROLE_GEN';
INSERT INTO ROLE (ROLE_KEY, MODULE, ACTION)
	VALUES ((SELECT MAX(GEN_VALUE) FROM GENERATOR WHERE GEN_NAME = 'ROLE_GEN'), 'REGISTRATIONS', 'CREATE');
UPDATE GENERATOR SET GEN_VALUE = GEN_VALUE + 1 WHERE GEN_NAME = 'ROLE_GEN';
INSERT INTO ROLE (ROLE_KEY, MODULE, ACTION)
	VALUES ((SELECT MAX(GEN_VALUE) FROM GENERATOR WHERE GEN_NAME = 'ROLE_GEN'), 'REGISTRATIONS', 'UPDATE');
UPDATE GENERATOR SET GEN_VALUE = GEN_VALUE + 1 WHERE GEN_NAME = 'ROLE_GEN';
INSERT INTO ROLE (ROLE_KEY, MODULE, ACTION)
	VALUES ((SELECT MAX(GEN_VALUE) FROM GENERATOR WHERE GEN_NAME = 'ROLE_GEN'), 'REGISTRATIONS', 'DELETE');
UPDATE GENERATOR SET GEN_VALUE = GEN_VALUE + 1 WHERE GEN_NAME = 'ROLE_GEN';
-- User accounts roles
INSERT INTO ROLE (ROLE_KEY, MODULE, ACTION)
	VALUES ((SELECT MAX(GEN_VALUE) FROM GENERATOR WHERE GEN_NAME = 'ROLE_GEN'), 'USERACCOUNTS', 'VIEW');
UPDATE GENERATOR SET GEN_VALUE = GEN_VALUE + 1 WHERE GEN_NAME = 'ROLE_GEN';
INSERT INTO ROLE (ROLE_KEY, MODULE, ACTION)
	VALUES ((SELECT MAX(GEN_VALUE) FROM GENERATOR WHERE GEN_NAME = 'ROLE_GEN'), 'USERACCOUNTS', 'CREATE');
UPDATE GENERATOR SET GEN_VALUE = GEN_VALUE + 1 WHERE GEN_NAME = 'ROLE_GEN';
INSERT INTO ROLE (ROLE_KEY, MODULE, ACTION)
	VALUES ((SELECT MAX(GEN_VALUE) FROM GENERATOR WHERE GEN_NAME = 'ROLE_GEN'), 'USERACCOUNTS', 'UPDATE');
UPDATE GENERATOR SET GEN_VALUE = GEN_VALUE + 1 WHERE GEN_NAME = 'ROLE_GEN';
INSERT INTO ROLE (ROLE_KEY, MODULE, ACTION)
	VALUES ((SELECT MAX(GEN_VALUE) FROM GENERATOR WHERE GEN_NAME = 'ROLE_GEN'), 'USERACCOUNTS', 'DELETE');
UPDATE GENERATOR SET GEN_VALUE = GEN_VALUE + 1 WHERE GEN_NAME = 'ROLE_GEN';

-- makseemka
-- Registrations roles
INSERT INTO USER_ACCOUNT_ROLE (USER_ACCOUNT_KEY, ROLE_KEY) 
	VALUES (
		(SELECT USER_ACCOUNT_KEY FROM USER_ACCOUNT WHERE USERNAME = 'makseemka'), 
		(SELECT ROLE_KEY FROM ROLE WHERE MODULE = 'REGISTRATIONS' AND ACTION = 'VIEW'));
INSERT INTO USER_ACCOUNT_ROLE (USER_ACCOUNT_KEY, ROLE_KEY) 
	VALUES (
		(SELECT USER_ACCOUNT_KEY FROM USER_ACCOUNT WHERE USERNAME = 'makseemka'), 
		(SELECT ROLE_KEY FROM ROLE WHERE MODULE = 'REGISTRATIONS' AND ACTION = 'CREATE'));
INSERT INTO USER_ACCOUNT_ROLE (USER_ACCOUNT_KEY, ROLE_KEY) 
	VALUES (
		(SELECT USER_ACCOUNT_KEY FROM USER_ACCOUNT WHERE USERNAME = 'makseemka'), 
		(SELECT ROLE_KEY FROM ROLE WHERE MODULE = 'REGISTRATIONS' AND ACTION = 'UPDATE'));
INSERT INTO USER_ACCOUNT_ROLE (USER_ACCOUNT_KEY, ROLE_KEY) 
	VALUES (
		(SELECT USER_ACCOUNT_KEY FROM USER_ACCOUNT WHERE USERNAME = 'makseemka'), 
		(SELECT ROLE_KEY FROM ROLE WHERE MODULE = 'REGISTRATIONS' AND ACTION = 'DELETE'));
-- User accounts roles
INSERT INTO USER_ACCOUNT_ROLE (USER_ACCOUNT_KEY, ROLE_KEY) 
	VALUES (
		(SELECT USER_ACCOUNT_KEY FROM USER_ACCOUNT WHERE USERNAME = 'makseemka'), 
		(SELECT ROLE_KEY FROM ROLE WHERE MODULE = 'USERACCOUNTS' AND ACTION = 'VIEW'));
INSERT INTO USER_ACCOUNT_ROLE (USER_ACCOUNT_KEY, ROLE_KEY) 
	VALUES (
		(SELECT USER_ACCOUNT_KEY FROM USER_ACCOUNT WHERE USERNAME = 'makseemka'), 
		(SELECT ROLE_KEY FROM ROLE WHERE MODULE = 'USERACCOUNTS' AND ACTION = 'CREATE'));
INSERT INTO USER_ACCOUNT_ROLE (USER_ACCOUNT_KEY, ROLE_KEY) 
	VALUES (
		(SELECT USER_ACCOUNT_KEY FROM USER_ACCOUNT WHERE USERNAME = 'makseemka'), 
		(SELECT ROLE_KEY FROM ROLE WHERE MODULE = 'USERACCOUNTS' AND ACTION = 'UPDATE'));
INSERT INTO USER_ACCOUNT_ROLE (USER_ACCOUNT_KEY, ROLE_KEY) 
	VALUES (
		(SELECT USER_ACCOUNT_KEY FROM USER_ACCOUNT WHERE USERNAME = 'makseemka'), 
		(SELECT ROLE_KEY FROM ROLE WHERE MODULE = 'USERACCOUNTS' AND ACTION = 'DELETE'));
-- Admin roles
INSERT INTO USER_ACCOUNT_ROLE (USER_ACCOUNT_KEY, ROLE_KEY) 
	VALUES (
		(SELECT USER_ACCOUNT_KEY FROM USER_ACCOUNT WHERE USERNAME = 'makseemka'), 
		(SELECT ROLE_KEY FROM ROLE WHERE MODULE = 'ADMIN' AND ACTION = 'VIEW'));
-- chueshkov
-- Registration roles
INSERT INTO USER_ACCOUNT_ROLE (USER_ACCOUNT_KEY, ROLE_KEY) 
	VALUES (
		(SELECT USER_ACCOUNT_KEY FROM USER_ACCOUNT WHERE USERNAME = 'chueshkovu'), 
		(SELECT ROLE_KEY FROM ROLE WHERE MODULE = 'REGISTRATIONS' AND ACTION = 'VIEW'));
-- test
-- Registration roles
INSERT INTO USER_ACCOUNT_ROLE (USER_ACCOUNT_KEY, ROLE_KEY) 
	VALUES (
		(SELECT USER_ACCOUNT_KEY FROM USER_ACCOUNT WHERE USERNAME = 'test'), 
		(SELECT ROLE_KEY FROM ROLE WHERE MODULE = 'REGISTRATIONS' AND ACTION = 'VIEW'));
INSERT INTO USER_ACCOUNT_ROLE (USER_ACCOUNT_KEY, ROLE_KEY) 
	VALUES (
		(SELECT USER_ACCOUNT_KEY FROM USER_ACCOUNT WHERE USERNAME = 'test'), 
		(SELECT ROLE_KEY FROM ROLE WHERE MODULE = 'REGISTRATIONS' AND ACTION = 'CREATE'));
INSERT INTO USER_ACCOUNT_ROLE (USER_ACCOUNT_KEY, ROLE_KEY) 
	VALUES (
		(SELECT USER_ACCOUNT_KEY FROM USER_ACCOUNT WHERE USERNAME = 'test'), 
		(SELECT ROLE_KEY FROM ROLE WHERE MODULE = 'REGISTRATIONS' AND ACTION = 'UPDATE'));
INSERT INTO USER_ACCOUNT_ROLE (USER_ACCOUNT_KEY, ROLE_KEY) 
	VALUES (
		(SELECT USER_ACCOUNT_KEY FROM USER_ACCOUNT WHERE USERNAME = 'test'), 
		(SELECT ROLE_KEY FROM ROLE WHERE MODULE = 'REGISTRATIONS' AND ACTION = 'DELETE'));
-- Admin roles
INSERT INTO USER_ACCOUNT_ROLE (USER_ACCOUNT_KEY, ROLE_KEY) 
	VALUES (
		(SELECT USER_ACCOUNT_KEY FROM USER_ACCOUNT WHERE USERNAME = 'test'), 
		(SELECT ROLE_KEY FROM ROLE WHERE MODULE = 'ADMIN' AND ACTION = 'VIEW'));

INSERT INTO SPECIALTY (SPECIALTY_KEY, CODE, TITLE, QUALIFICATION, STATUS)
	VALUES ((SELECT MAX(GEN_VALUE) FROM GENERATOR WHERE GEN_NAME = 'SPECIALTY_GEN'), '2 39 02 32', 'Проектирование и производство радиоэлектронных средств', 'техник-элекроник', 'A');
UPDATE GENERATOR SET GEN_VALUE = GEN_VALUE + 1 WHERE GEN_NAME = 'SPECIALTY_GEN';
INSERT INTO SPECIALTY (SPECIALTY_KEY, CODE, TITLE, QUALIFICATION, STATUS)
	VALUES ((SELECT MAX(GEN_VALUE) FROM GENERATOR WHERE GEN_NAME = 'SPECIALTY_GEN'), '2 39 02 31', 'Техническая эксплуатация радиоэлектронных средств', 'техник-элекроник', 'A');
UPDATE GENERATOR SET GEN_VALUE = GEN_VALUE + 1 WHERE GEN_NAME = 'SPECIALTY_GEN';
INSERT INTO SPECIALTY (SPECIALTY_KEY, CODE, TITLE, QUALIFICATION, STATUS)
	VALUES ((SELECT MAX(GEN_VALUE) FROM GENERATOR WHERE GEN_NAME = 'SPECIALTY_GEN'), '2 40 02 02', 'Электронные вычислительные средства', 'техник-элекроник', 'A');
UPDATE GENERATOR SET GEN_VALUE = GEN_VALUE + 1 WHERE GEN_NAME = 'SPECIALTY_GEN';
INSERT INTO SPECIALTY (SPECIALTY_KEY, CODE, TITLE, QUALIFICATION, STATUS)
	VALUES ((SELECT MAX(GEN_VALUE) FROM GENERATOR WHERE GEN_NAME = 'SPECIALTY_GEN'), '2 41 01 02', 'Микро- и наноэлектронные технологии и системы', 'техник-элекроник', 'A');
UPDATE GENERATOR SET GEN_VALUE = GEN_VALUE + 1 WHERE GEN_NAME = 'SPECIALTY_GEN';
INSERT INTO SPECIALTY (SPECIALTY_KEY, CODE, TITLE, QUALIFICATION, STATUS)
	VALUES ((SELECT MAX(GEN_VALUE) FROM GENERATOR WHERE GEN_NAME = 'SPECIALTY_GEN'), '2 40 01 01', 'Программное обеспечение информационных технологий', 'техник-программист', 'A');
UPDATE GENERATOR SET GEN_VALUE = GEN_VALUE + 1 WHERE GEN_NAME = 'SPECIALTY_GEN';
INSERT INTO SPECIALTY (SPECIALTY_KEY, CODE, TITLE, QUALIFICATION, STATUS)
	VALUES ((SELECT MAX(GEN_VALUE) FROM GENERATOR WHERE GEN_NAME = 'SPECIALTY_GEN'), '2 39 03 02', 'Программируемые мобильные системы', 'техник-элекроник', 'A');
UPDATE GENERATOR SET GEN_VALUE = GEN_VALUE + 1 WHERE GEN_NAME = 'SPECIALTY_GEN';

INSERT INTO SUBJECT (SUBJECT_KEY, TITLE)
	VALUES ((SELECT MAX(GEN_VALUE) FROM GENERATOR WHERE GEN_NAME = 'SUBJECT_GEN'), 'Русский язык');
UPDATE GENERATOR SET GEN_VALUE = GEN_VALUE + 1 WHERE GEN_NAME = 'SUBJECT_GEN';
INSERT INTO SUBJECT (SUBJECT_KEY, TITLE)
	VALUES ((SELECT MAX(GEN_VALUE) FROM GENERATOR WHERE GEN_NAME = 'SUBJECT_GEN'), 'Русская литература');
UPDATE GENERATOR SET GEN_VALUE = GEN_VALUE + 1 WHERE GEN_NAME = 'SUBJECT_GEN';
INSERT INTO SUBJECT (SUBJECT_KEY, TITLE)
	VALUES ((SELECT MAX(GEN_VALUE) FROM GENERATOR WHERE GEN_NAME = 'SUBJECT_GEN'), 'Белорусский язык');
UPDATE GENERATOR SET GEN_VALUE = GEN_VALUE + 1 WHERE GEN_NAME = 'SUBJECT_GEN';
INSERT INTO SUBJECT (SUBJECT_KEY, TITLE)
	VALUES ((SELECT MAX(GEN_VALUE) FROM GENERATOR WHERE GEN_NAME = 'SUBJECT_GEN'), 'Белорусская литература');
UPDATE GENERATOR SET GEN_VALUE = GEN_VALUE + 1 WHERE GEN_NAME = 'SUBJECT_GEN';
INSERT INTO SUBJECT (SUBJECT_KEY, TITLE)
	VALUES ((SELECT MAX(GEN_VALUE) FROM GENERATOR WHERE GEN_NAME = 'SUBJECT_GEN'), 'Математика');
UPDATE GENERATOR SET GEN_VALUE = GEN_VALUE + 1 WHERE GEN_NAME = 'SUBJECT_GEN';
INSERT INTO SUBJECT (SUBJECT_KEY, TITLE)
	VALUES ((SELECT MAX(GEN_VALUE) FROM GENERATOR WHERE GEN_NAME = 'SUBJECT_GEN'), 'Химия');
UPDATE GENERATOR SET GEN_VALUE = GEN_VALUE + 1 WHERE GEN_NAME = 'SUBJECT_GEN';
INSERT INTO SUBJECT (SUBJECT_KEY, TITLE)
	VALUES ((SELECT MAX(GEN_VALUE) FROM GENERATOR WHERE GEN_NAME = 'SUBJECT_GEN'), 'Физика');
UPDATE GENERATOR SET GEN_VALUE = GEN_VALUE + 1 WHERE GEN_NAME = 'SUBJECT_GEN';
INSERT INTO SUBJECT (SUBJECT_KEY, TITLE)
	VALUES ((SELECT MAX(GEN_VALUE) FROM GENERATOR WHERE GEN_NAME = 'SUBJECT_GEN'), 'Биология');
UPDATE GENERATOR SET GEN_VALUE = GEN_VALUE + 1 WHERE GEN_NAME = 'SUBJECT_GEN';