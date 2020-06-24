create table if not exists persistent_logins (
  username varchar_ignorecase(100) not null,
  series varchar(64) primary key,
  token varchar(64) not null,
  last_used timestamp not null
  );


INSERT INTO Role VALUES (1, 'ADMIN');
INSERT INTO Role VALUES (2, 'SELLER');
INSERT INTO Role VALUES (3, 'BUYER');


INSERT INTO Users VALUES (1,1,0,'admin@gmail.com','admin','admin','$2a$10$nA8k2TPoXgACwWhCZXhomOlvwtNReWprcVgjRpDiZNAGXN3UMLgSO','admin');
INSERT INTO Users VALUES (2,2,1,'seller@gmail.com','seller','seller','$2a$10$nA8k2TPoXgACwWhCZXhomOlvwtNReWprcVgjRpDiZNAGXN3UMLgSO','seller');
INSERT INTO Users VALUES (3,3,1,'buyer@gmail.com','buyer','buyer','$2a$10$nA8k2TPoXgACwWhCZXhomOlvwtNReWprcVgjRpDiZNAGXN3UMLgSO','buyer');
INSERT INTO Users VALUES (4,3,0,'sell@gmail.com','sell','sell','$2a$10$nA8k2TPoXgACwWhCZXhomOlvwtNReWprcVgjRpDiZNAGXN3UMLgSO','sell');

INSERT INTO User_role VALUES (1,1);
INSERT INTO User_role VALUES (2,2);
INSERT INTO User_role VALUES (3,3);
INSERT INTO User_role VALUES (4,3);

INSERT INTO Category VALUES (1,'2019-01-01','Vetement','Vetement',null,1);
INSERT INTO Category VALUES (2,'2019-01-01','Chemise','Chemise',null,1);

INSERT INTO PRODUCT VALUES (1,'2019-08-14','A winter jacket is a garment that can help you withstand the cold, wind, and snow or rain. It should contain thick insulation so your body stays warm even when not in motion. It should also protect from wind and precipitation.','kdfdkgdsk_2019-08-14_23_57_33.png','Vetement',24.0,23,null,1,2);

INSERT INTO PRODUCT VALUES (2,'2019-08-14','A winter jacket is a garment that can help you withstand the cold, wind, and snow or rain. It should contain thick insulation so your body stays warm even when not in motion. It should also protect from wind and precipitation.','Chemise_2019-08-15_0_6_46.png','Vetement',51.0,23,null,1,2);

