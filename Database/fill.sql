# Tipo de Pontos de Interesse
INSERT INTO TypeOfPoI(type) VALUES ("Monumento");
INSERT INTO TypeOfPoI(type) VALUES ("Paisagem");

# Regi�es dos Pontos de Interesse
INSERT INTO Region(name) VALUES ("Porto");

# Utilizadores
INSERT INTO User(idFacebook, points) VALUES (1109369355758471, 0);

# Pontos de Interesse
INSERT INTO PointsOfInterest(userId, typeId, regionId, name, description, address, latitude, longitude, creationDate, numLikes, numDislikes, rating) VALUES (1109369355758471, 1, 1, "Cl�rigos",
                                                                                                                                                             "A Torre dos Cl�rigos � uma torre sineira que faz parte da Igreja dos Cl�rigos e est� situada na cidade do Porto. � um monumento considerado por muitos o ex libris da cidade do Porto.",
                                                                                                                                                             "Rua de S�o Filipe de Nery, 4050-546 Porto",
                                                                                                                                                             41.1456753, -8.6145985,
                                                                                                                                                             "2015-09-08", 0, 0, 0);
INSERT INTO PointsOfInterest(userId, typeId, regionId, name, description, address, latitude, longitude, creationDate, numLikes, numDislikes, rating) VALUES (1109369355758471, 1, 1, "S� do Porto",
                                                                                                                                                             "A S� / Catedral da cidade do Porto, situado no cora��o do centro hist�rico da cidade do Porto, � um dos principais e mais antigos monumentos de Portugal.",
                                                                                                                                                             "Terreiro da S�, 4050-573 Porto",
                                                                                                                                                             41.142826, -8.6111836,
                                                                                                                                                             "2015-09-08", 0, 0, 0);