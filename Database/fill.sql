# Achievements
INSERT INTO Achievement(id, name, description) VALUES (1, "Perda da virgindade", "Fez a primeira visita.");
INSERT INTO Achievement(id, name, description) VALUES (2, "Comentador", "Fez a primeira review.");
INSERT INTO Achievement(id, name, description) VALUES (3, "Turista", "Fez 10 visitas.");
INSERT INTO Achievement(id, name, description) VALUES (4, "Contribuidor", "Criou o primeiro ponto de interesse.");
INSERT INTO Achievement(id, name, description) VALUES (5, "Cart�grafo", "Criou 5 pontos de interesse.");

# Tipo de Pontos de Interesse
INSERT INTO TypeOfPoI(type) VALUES ("Monumento");
INSERT INTO TypeOfPoI(type) VALUES ("Paisagem");
INSERT INTO TypeOfPoI(type) VALUES ("Museu");
INSERT INTO TypeOfPoI(type) VALUES ("Divers�o");

# Regi�es dos Pontos de Interesse
INSERT INTO Region(name) VALUES ("Porto");

# Utilizadores
INSERT INTO User(idFacebook, name, points, numVisits, numReviews, numPoIs, numAchievements) VALUES (1109369355758471, "Joao Silva",0, 0, 0, 0, 0);
INSERT INTO User(idFacebook, name, points, numVisits, numReviews, numPoIs, numAchievements) VALUES (1037259616297913, "Gustavo Silva", 0, 0, 0, 0, 0);

# Pontos de Interesse
INSERT INTO PointsOfInterest(userId, typeId, regionId, name, description, address, latitude, longitude, creationDate, numLikes, numDislikes, numVisits, rating, active) VALUES (1037259616297913, 1, 1, "Cl�rigos",
                                                                                                                                                                                "A Torre dos Cl�rigos � uma torre sineira que faz parte da Igreja dos Cl�rigos e est� situada na cidade do Porto. � um monumento considerado por muitos o ex libris da cidade do Porto.",
                                                                                                                                                                                "Rua de S�o Filipe de Nery, 4050-546 Porto",
                                                                                                                                                                                41.1456753, -8.6145985,
                                                                                                                                                                                "2015-09-08", 0, 0, 0, 0, true);
INSERT INTO PointsOfInterest(userId, typeId, regionId, name, description, address, latitude, longitude, creationDate, numLikes, numDislikes, numVisits, rating, active) VALUES (1109369355758471, 1, 1, "S� do Porto",
                                                                                                                                                                                "A S� / Catedral da cidade do Porto, situado no cora��o do centro hist�rico da cidade do Porto, � um dos principais e mais antigos monumentos de Portugal.",
                                                                                                                                                                                "Terreiro da S�, 4050-573 Porto",
                                                                                                                                                                                41.142826, -8.6111836,
                                                                                                                                                                                "2015-09-08", 0, 0, 0, 0, true);
INSERT INTO PointsOfInterest(userId, typeId, regionId, name, description, address, latitude, longitude, creationDate, numLikes, numDislikes, numVisits, rating, active) VALUES (1109369355758471, 1, 1, "Pal�cio da Bolsa",
                                                                                                                                                                                "O Pal�cio da Bolsa, ou Pal�cio da Associa��o Comercial do Porto, na cidade do Porto, em Portugal, come�ou a ser constru�do em Outubro de 1842, em virtude do encerramento da Casa da Bolsa do Com�rcio, o que obrigou temporariamente os comerciantes portuenses a discutirem os seus neg�cios na Rua dos Ingleses, em pleno ar livre.",
                                                                                                                                                                                "R. de Ferreira Borges, 4050-253 Porto",
                                                                                                                                                                                41.1413772, -8.6156725,
                                                                                                                                                                                "2015-09-08", 0, 0, 0, 0, true);
INSERT INTO PointsOfInterest(userId, typeId, regionId, name, description, address, latitude, longitude, creationDate, numLikes, numDislikes, numVisits, rating, active) VALUES (1109369355758471, 1, 1, "Pal�cio da Bolsa",
                                                                                                                                                                                "O Pal�cio da Bolsa, ou Pal�cio da Associa��o Comercial do Porto, na cidade do Porto, em Portugal, come�ou a ser constru�do em Outubro de 1842, em virtude do encerramento da Casa da Bolsa do Com�rcio, o que obrigou temporariamente os comerciantes portuenses a discutirem os seus neg�cios na Rua dos Ingleses, em pleno ar livre.",
                                                                                                                                                                                "R. de Ferreira Borges, 4050-253 Porto",
                                                                                                                                                                                41.1413772, -8.6156725,
                                                                                                                                                                                "2015-09-08", 0, 0, 0, 0, true);
INSERT INTO PointsOfInterest(userId, typeId, regionId, name, description, address, latitude, longitude, creationDate, numLikes, numDislikes, numVisits, rating, active) VALUES (1109369355758471, 1, 1, "Igreja de S�o Francisco",
                                                                                                                                                                                "A Igreja de S�o Francisco � uma igreja g�tica da cidade do Porto, situada na freguesia de S�o Nicolau em pleno Centro hist�rico do Porto.",
                                                                                                                                                                                "Rua do Infante D. Henrique, 4050-297 Porto",
                                                                                                                                                                                41.1410111, -8.6157173,
                                                                                                                                                                                "2015-09-08", 0, 0, 0, 0, true);
INSERT INTO PointsOfInterest(userId, typeId, regionId, name, description, address, latitude, longitude, creationDate, numLikes, numDislikes, numVisits, rating, active) VALUES (1109369355758471, 3, 1, "Museu Militar do Porto",
                                                                                                                                                                                "O Museu Militar do Porto � uma institui��o pertencente ao Ex�rcito Portugu�s, vocacionada para a preserva��o da hist�ria militar.",
                                                                                                                                                                                "Rua do Hero�smo 329, 4300-259 Porto",
                                                                                                                                                                                41.1459596, -8.6152627,
                                                                                                                                                                                "2015-09-08", 0, 0, 0, 0, true);
INSERT INTO PointsOfInterest(userId, typeId, regionId, name, description, address, latitude, longitude, creationDate, numLikes, numDislikes, numVisits, rating, active) VALUES (1109369355758471, 4, 1, "Est�dio do Drag�o",
                                                                                                                                                                                "O Est�dio do Drag�o � um est�dio de futebol localizado na freguesia de Campanh�, cidade do Porto, atualmente propriedade do Futebol Clube do Porto, sendo neste recinto que a equipa de futebol joga as suas partidas em casa.",
                                                                                                                                                                                "Via Futebol Clube do Porto, 4350-415 Porto",
                                                                                                                                                                                41.16177, -8.583591,
                                                                                                                                                                                "2015-09-08", 0, 0, 0, 0, true);

INSERT INTO PointsOfInterest(userId, typeId, regionId, name, description, address, latitude, longitude, creationDate, numLikes, numDislikes, numVisits, rating, active) VALUES (1109369355758471, 2, 1, "Parque da Cidade",
                                                                                                                                                                                "O Parque da Cidade do Porto, da autoria do arquitecto paisagista Sid�nio Pardal, � o maior parque urbano do pa�s, ocupando um total de 83 hectares e cerca de 10 km de caminhos.",
                                                                                                                                                                                "Estrada Interior da Circunvala��o , 15443, 4100-183 Porto",
                                                                                                                                                                                41.17137, -8.678023,
                                                                                                                                                                                "2015-09-08", 0, 0, 0, 0, true);
# Reviews
INSERT INTO Reviews(userId, poiId, comment, `like`) VALUES (1109369355758471, 1, "Adorei!!!", true);