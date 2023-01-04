-- Insertion de quelques données de références

-- Pays
INSERT INTO pays(`id_pays`,`libelle`,`TVA`,`actif`)
VALUES ('FR','France','20.00',1);

INSERT INTO pays(`id_pays`,`libelle`,`TVA`,`actif`)
VALUES ('IT','Italie','20.00',1);


-- type d'adresse

INSERT INTO type_adresse(`id_type_adresse`,`libelle`)
VALUES(1,'Adresse de livraison');

INSERT INTO type_adresse(`id_type_adresse`,`libelle`)
VALUES(2,'Adresse de facturation');

INSERT INTO type_adresse(`id_type_adresse`,`libelle`)
VALUES(3,'Autres');

-- clients

INSERT INTO clients ( `nom`, `prenom`, `fk_id_pays`) VALUES ('Martin', 'Pierre', 'FR');

