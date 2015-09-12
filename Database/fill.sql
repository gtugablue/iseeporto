# Achievements
INSERT INTO Achievement(id, name, description) VALUES (1, "Perda da virgindade", "Fez a primeira visita.");
INSERT INTO Achievement(id, name, description) VALUES (2, "Comentador", "Fez a primeira review.");
INSERT INTO Achievement(id, name, description) VALUES (3, "Turista", "Fez 10 visitas.");
INSERT INTO Achievement(id, name, description) VALUES (4, "Contribuidor", "Criou o primeiro ponto de interesse.");
INSERT INTO Achievement(id, name, description) VALUES (5, "Cartógrafo", "Criou 5 pontos de interesse.");

# Tipo de Pontos de Interesse
INSERT INTO TypeOfPoI(type) VALUES ("Monumento");
INSERT INTO TypeOfPoI(type) VALUES ("Paisagem");
INSERT INTO TypeOfPoI(type) VALUES ("Museu");
INSERT INTO TypeOfPoI(type) VALUES ("Diversão");

# Regiões dos Pontos de Interesse
INSERT INTO Region(name) VALUES ("Porto");

# Utilizadores
INSERT INTO User(idFacebook, name, points, numVisits, numReviews, numPoIs, numAchievements) VALUES (1109369355758471, "Joao Silva",0, 0, 0, 0, 0);
INSERT INTO User(idFacebook, name, points, numVisits, numReviews, numPoIs, numAchievements) VALUES (1037259616297913, "Gustavo Silva", 0, 0, 0, 0, 0);

# Pontos de Interesse
INSERT INTO PointsOfInterest(userId, typeId, regionId, name, description, address, latitude, longitude, creationDate, numLikes, numDislikes, numVisits, rating, active) VALUES (1037259616297913, 1, 1, "Clérigos",
                                                                                                                                                                                "A Torre dos Clérigos é uma torre sineira que faz parte da Igreja dos Clérigos e está situada na cidade do Porto. É um monumento considerado por muitos o ex libris da cidade do Porto.",
                                                                                                                                                                                "Rua de São Filipe de Nery, 4050-546 Porto",
                                                                                                                                                                                41.1456753, -8.6145985,
                                                                                                                                                                                "2015-09-08", 0, 0, 0, 0, true);
INSERT INTO PointsOfInterest(userId, typeId, regionId, name, description, address, latitude, longitude, creationDate, numLikes, numDislikes, numVisits, rating, active) VALUES (1109369355758471, 1, 1, "Sé do Porto",
                                                                                                                                                                                "A Sé / Catedral da cidade do Porto, situado no coração do centro histórico da cidade do Porto, é um dos principais e mais antigos monumentos de Portugal.",
                                                                                                                                                                                "Terreiro da Sé, 4050-573 Porto",
                                                                                                                                                                                41.142826, -8.6111836,
                                                                                                                                                                                "2015-09-08", 0, 0, 0, 0, true);
INSERT INTO PointsOfInterest(userId, typeId, regionId, name, description, address, latitude, longitude, creationDate, numLikes, numDislikes, numVisits, rating, active) VALUES (1109369355758471, 1, 1, "Palácio da Bolsa",
                                                                                                                                                                                "O Palácio da Bolsa, ou Palácio da Associação Comercial do Porto, na cidade do Porto, em Portugal, começou a ser construído em Outubro de 1842, em virtude do encerramento da Casa da Bolsa do Comércio, o que obrigou temporariamente os comerciantes portuenses a discutirem os seus negócios na Rua dos Ingleses, em pleno ar livre.",
                                                                                                                                                                                "R. de Ferreira Borges, 4050-253 Porto",
                                                                                                                                                                                41.1413772, -8.6156725,
                                                                                                                                                                                "2015-09-08", 0, 0, 0, 0, true);
INSERT INTO PointsOfInterest(userId, typeId, regionId, name, description, address, latitude, longitude, creationDate, numLikes, numDislikes, numVisits, rating, active) VALUES (1109369355758471, 1, 1, "Palácio da Bolsa",
                                                                                                                                                                                "O Palácio da Bolsa, ou Palácio da Associação Comercial do Porto, na cidade do Porto, em Portugal, começou a ser construído em Outubro de 1842, em virtude do encerramento da Casa da Bolsa do Comércio, o que obrigou temporariamente os comerciantes portuenses a discutirem os seus negócios na Rua dos Ingleses, em pleno ar livre.",
                                                                                                                                                                                "R. de Ferreira Borges, 4050-253 Porto",
                                                                                                                                                                                41.1413772, -8.6156725,
                                                                                                                                                                                "2015-09-08", 0, 0, 0, 0, true);
INSERT INTO PointsOfInterest(userId, typeId, regionId, name, description, address, latitude, longitude, creationDate, numLikes, numDislikes, numVisits, rating, active) VALUES (1109369355758471, 1, 1, "Igreja de São Francisco",
                                                                                                                                                                                "A Igreja de São Francisco é uma igreja gótica da cidade do Porto, situada na freguesia de São Nicolau em pleno Centro histórico do Porto.",
                                                                                                                                                                                "Rua do Infante D. Henrique, 4050-297 Porto",
                                                                                                                                                                                41.1410111, -8.6157173,
                                                                                                                                                                                "2015-09-08", 0, 0, 0, 0, true);
INSERT INTO PointsOfInterest(userId, typeId, regionId, name, description, address, latitude, longitude, creationDate, numLikes, numDislikes, numVisits, rating, active) VALUES (1109369355758471, 3, 1, "Museu Militar do Porto",
                                                                                                                                                                                "O Museu Militar do Porto é uma instituição pertencente ao Exército Português, vocacionada para a preservação da história militar.",
                                                                                                                                                                                "Rua do Heroísmo 329, 4300-259 Porto",
                                                                                                                                                                                41.1459596, -8.6152627,
                                                                                                                                                                                "2015-09-08", 0, 0, 0, 0, true);
INSERT INTO PointsOfInterest(userId, typeId, regionId, name, description, address, latitude, longitude, creationDate, numLikes, numDislikes, numVisits, rating, active) VALUES (1109369355758471, 4, 1, "Estádio do Dragão",
                                                                                                                                                                                "O Estádio do Dragão é um estádio de futebol localizado na freguesia de Campanhã, cidade do Porto, atualmente propriedade do Futebol Clube do Porto, sendo neste recinto que a equipa de futebol joga as suas partidas em casa.",
                                                                                                                                                                                "Via Futebol Clube do Porto, 4350-415 Porto",
                                                                                                                                                                                41.16177, -8.583591,
                                                                                                                                                                                "2015-09-08", 0, 0, 0, 0, true);

INSERT INTO PointsOfInterest(userId, typeId, regionId, name, description, address, latitude, longitude, creationDate, numLikes, numDislikes, numVisits, rating, active) VALUES (1109369355758471, 2, 1, "Parque da Cidade",
                                                                                                                                                                                "O Parque da Cidade do Porto, da autoria do arquitecto paisagista Sidónio Pardal, é o maior parque urbano do país, ocupando um total de 83 hectares e cerca de 10 km de caminhos.",
                                                                                                                                                                                "Estrada Interior da Circunvalação , 15443, 4100-183 Porto",
                                                                                                                                                                                41.17137, -8.678023,
                                                                                                                                                                                "2015-09-08", 0, 0, 0, 0, true);
# Reviews
INSERT INTO Reviews(userId, poiId, comment, `like`) VALUES (1109369355758471, 1, "Adorei!!!", true);