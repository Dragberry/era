INSERT INTO OUT_OF_COMPETITION 
	(OUT_OF_COMPETITION_KEY, PRIORITY, DELETED, NAME, DESCRIPTION)
VALUES ((SELECT MAX(GEN_VALUE) FROM GENERATOR WHERE GEN_NAME = 'OUT_OF_COMPETITION_GEN'),
		0, 0, 'Сирота', '');
UPDATE GENERATOR SET GEN_VALUE = GEN_VALUE + 1 WHERE GEN_NAME = 'OUT_OF_COMPETITION_GEN';
