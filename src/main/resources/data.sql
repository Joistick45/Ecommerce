INSERT INTO categoria (id, nome)
VALUES (10,'Eletrodomesticos'), (11,'Utensilios'); 

INSERT INTO usuario (nome, email, senha)
VALUES 
('Usuario', 'usuario@email.com', '$2a$10$4LVjo6Vke3C/oUKZItovOexLqqRBJv/psmnk5dWFFRZaWmVnQ36dG'),
('Moderador', 'moderador@email.com', '$2a$10$4LVjo6Vke3C/oUKZItovOexLqqRBJv/psmnk5dWFFRZaWmVnQ36dG');;


INSERT INTO perfil(id,nome)
VALUES 
(1,'ROLE_USER'),
(2,'ROLE_ADMINISTRATOR');

INSERT INTO usuario_perfis(usuario_id,perfis_id)
VALUES
(1, 1),
(2, 2);