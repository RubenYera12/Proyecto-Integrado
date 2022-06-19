INSERT INTO `category` (`id`,`name`) VALUES
('CAT0001','Nuevo Ingreso'),
('CAT0002','Oficial'),
('CAT0003','Provincial'),
('CAT0004','División de honor'),
('CAT0005','3ª RFAF'),
('CAT0006','2ª RFAF'),
('CAT0007','1ªRFAF'),
('CAT0008','2ª División'),
('CAT0009','1ª División');

UPDATE category_seq SET next_val = 10;

INSERT INTO `referee` (`id`,`admin`,`birth_date`,`city`,`email`,`firstname`,`image_url`,`license_num`,`name`,`nevera`,`password`,`telf_number`,`category_id`) VALUES
('REF0001',true,'2001-10-12','Jaén','rubenacyeramartin@gmail.com','Yera Martín','img/defaultReferee.png','123123','Rubén',false,'123','123124234','CAT0002'),
('REF0002',false,'2001-08-02','Jaén','juan@gmail.com','Titos Blanca','img/defaultReferee.png','423234','Juan',false,'123','343242','CAT0003'),
('REF0003',false,'2004-05-31','Jaén','diego@gmail.com','Yera Martín','img/defaultReferee.png','1231234','Diego',false,'123','343423','CAT0001');

UPDATE ref_seq SET next_val = 4;

INSERT INTO `competition` (`id`,`image`,`name`,`zone`) VALUES
('COM0001','img/champions.png','Champions League','Europa'),
('COM0002','img/UEL.png','Europa League','Europa'),
('COM0003','img/defaultCompetition.png','Conference League','Europa');

UPDATE comp_seq SET next_val = 4;

INSERT INTO `team` (`id`,`coach`,`image`,`name`,`stadium`,`competition_id`) VALUES
('TEAM0001','Carlo Ancelotti','img/realmadrid.png','Real Madrid','Santiago Bernabeu','COM0001'),
('TEAM0002','Xavi','img/defaultTeam.png','FC Barcelona','Spotify Camp Now','COM0002'),
('TEAM0003','Pelegrini','img/defaultTeam.png','Real Betis Balompie','Benito Villamarín','COM0002'),
('TEAM0004','Chumilla','img/defaultTeam.png','Real Jaén','La Victoria','COM0001');

UPDATE team_seq SET next_val = 5;

INSERT INTO `competition_team_list` (`competition_id`,`team_list_id`) VALUES
('COM0001','TEAM0001'),
('COM0001','TEAM0004'),
('COM0002','TEAM0002'),
('COM0002','TEAM0003');

INSERT INTO `player` (`id`,`birth_date`,`firstname`,`image_url`,`license_num`,`name`,`number`,`sancion`,`team_id`) VALUES
('PLA0001','2022-06-15','Benzema','img/defaultReferee.png','64534','Karim',9,0,'TEAM0001'),
('PLA0002','2022-06-15','Canales','img/defaultReferee.png','786567','Sergio',10,0,'TEAM0003'),
('PLA0003','2022-06-15','Perez','img/defaultReferee.png','42345','Pepe',7,0,'TEAM0004'),
('PLA0004','2022-06-15','Jr','img/defaultReferee.png','645376574','Vinicius',20,0,'TEAM0001'),
('PLA0005','2022-06-15','Álaba','img/defaultReferee.png','12314','David',4,0,'TEAM0001'),
('PLA0006','2022-06-15','Courtois','img/defaultReferee.png','645376574','Thibaut',1,0,'TEAM0001'),
('PLA0007','2022-06-15','Jiménez','img/defaultReferee.png','64536574','Javier',2,0,'TEAM0004'),
('PLA0008','2022-06-15','Ibáñez','img/defaultReferee.png','6787','Alejandro',9,0,'TEAM0004'),
('PLA0009','2022-06-15','Laguna','img/defaultReferee.png','4545','Juan',11,0,'TEAM0004');

UPDATE player_seq SET next_val = 10;

INSERT INTO `team_players` (`team_id`,`players_id`) VALUES
('TEAM0001','PLA0001'),
('TEAM0003','PLA0002'),
('TEAM0004','PLA0003'),
('TEAM0001','PLA0004'),
('TEAM0001','PLA0005'),
('TEAM0001','PLA0006'),
('TEAM0004','PLA0007'),
('TEAM0004','PLA0008'),
('TEAM0004','PLA0009');


INSERT INTO `partidos` (`id`,`fecha`,`hora`,`season`,`competition_id`,`local_id`,`visitor_id`) VALUES
('MATCH0001','2022-06-19','20.30','21-22','COM0001','TEAM0001','TEAM0004'),
('MATCH0002','2022-06-20','20.30','21-22','COM0002','TEAM0002','TEAM0003');

UPDATE match_seq SET next_val = 3;


INSERT INTO `designation` (`id`,`price_assistant`,`price_referee`,`status`,`assistant_referee1_id`,`assistant_referee2_id`,`main_referee_id`,`match_id`) VALUES
('D0001',35.9,90,'ACEPTADA','REF0002','REF0003','REF0001','MATCH0001');

UPDATE designation_seq SET next_val = 2;

INSERT INTO `acta` (`id`,`designation_id`) VALUES
('ACT0001','D0001');

UPDATE acta_seq SET next_val = 2;


