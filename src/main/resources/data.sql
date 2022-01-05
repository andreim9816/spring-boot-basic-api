INSERT INTO department VALUES(null, 'Cardiologie');
INSERT INTO department VALUES(null, 'Chirurgie');

INSERT INTO doctor VALUES(null, 'Alex', 'Mocanu', 1);
INSERT INTO doctor VALUES(null, 'Claudiu', 'Teohari', 1);
INSERT INTO doctor VALUES(null, 'Costel', 'Bojog', 2);

INSERT INTO address VALUES(null, 'Bucuresti', 23, 'Biruinta');
INSERT INTO address VALUES(null, 'Bucuresti', 112, 'Independentei');
INSERT INTO address VALUES(null, 'Bacau', 1, 'Copou');
INSERT INTO address VALUES(null, 'Timisoara', 33, 'Argesului');

INSERT INTO patient VALUES(null, 'Vio','Dragu', '1981128270821', 1, 1);
INSERT INTO patient VALUES(null, 'Raul', 'Gheba', '1941128270866', 2, 2);

INSERT INTO consult VALUES (null, '', SYSDATE() - 4, 'durere in suflet', 'durere in gat', 1, 1);
INSERT INTO consult VALUES (null, '', SYSDATE() - 2, '-', 'durere in gat, somnolenta', 2, 1);
INSERT INTO consult VALUES (null, '', SYSDATE(), '-', 's-a extins durerea in cot', 1, 1);

INSERT INTO medication VALUES (null, 'Ibuprofen', 300);
INSERT INTO medication VALUES (null, 'Ibuprofen', 800);
INSERT INTO medication VALUES (null, 'Paracetamol', 400);
INSERT INTO medication VALUES (null, 'Decasept', 50);
INSERT INTO medication VALUES (null, 'Colebil', 2);


INSERT INTO prescription VALUES (1, 1);
INSERT INTO prescription VALUES (2, 1);
INSERT INTO prescription VALUES (3, 1);
INSERT INTO prescription VALUES (1, 2);
INSERT INTO prescription VALUES (4, 2);
