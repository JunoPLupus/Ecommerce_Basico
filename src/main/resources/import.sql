-- 0. Roles
INSERT INTO role (nome)
    VALUES
        ('ROLE_ADMIN'),
        ('ROLE_USER');

-- 1. Clientes
-- Pessoas Físicas
INSERT INTO pessoa (tipo, nome, cpf, email, telefone)
    VALUES
        ('F', 'Ana Silva', '152.701.629-38', 'ana@email.com', '63 99111-2222'),
        ('F', 'Carlos Souza', '462.664.301-93', 'carlos@email.com', '63 99333-4444');

-- Pessoas Jurídicas
INSERT INTO pessoa (tipo, razao_social, cnpj, email, telefone)
    VALUES
        ('J', 'Tech Ltda', '67.421.057/0001-35', 'contato@tech.com', '63 3211-5555');

-- 2. Usuários
INSERT INTO usuario(pessoa_id, login, password)
    VALUES
        (1, 'ana.silva', '$2a$10$EMwxg8PHibgcWkMtvO3Ceeo8Trr9IoRcN0grFbFYvJ5s.JNMzO/Ey'),
        (2, 'carlos.souza', '$2a$10$EMwxg8PHibgcWkMtvO3Ceeo8Trr9IoRcN0grFbFYvJ5s.JNMzO/Ey'),
        (3, 'tech.ltda', '$2a$10$EMwxg8PHibgcWkMtvO3Ceeo8Trr9IoRcN0grFbFYvJ5s.JNMzO/Ey');

-- 3. Roles de Usuários
INSERT INTO usuario_roles (usuarios_id, roles_id) VALUES
        (1, 1),
        (2, 2),
        (3, 2);

-- 4. Produtos
INSERT INTO produto (url_imagem, descricao, valor)
    VALUES
        ('https://m.media-amazon.com/images/I/51SM5xU-M1L._AC_SX679_.jpg','PlayStation®5 Slim Edição Digital', 4099.90),
        ('https://m.media-amazon.com/images/I/61EtSxH1huL._AC_SX679_.jpg', 'Caneta Azul Bic Cristal Dura Mais – Ponta Esferográfica Média de 1.0mm. Escrita Macia e Cor Intensa – Leve 4 Pague 3 – Pacote 4 Canetas', 5.99),
        ('https://m.media-amazon.com/images/I/71zFMjCmtrL._AC_SX679_.jpg', 'Teclado Mecânico Gamer 60% RGB, Switch Blue YH, QWERTY, Retroiluminação RGB com 12 Efeitos, 63 Teclas, USB 2.0, Preto, Anti-Ghosting', 114.85),
        ('https://m.media-amazon.com/images/I/61w4qyZKF9L._SY522_.jpg','Tidy First?: Minirrefatorações para um melhor design de software', 46.50);

-- 5. Vendas
INSERT INTO venda (data, cliente_id)
    VALUES
        ('2026-01-15 10:30:00', 1),
        ('2026-01-16 14:00:00', 2),
        ('2026-02-03 09:15:00', 3),
        ('2026-02-14 16:45:00', 1),
        ('2026-03-01 11:00:00', 2);

-- 6. Itens
INSERT INTO item_venda (quantidade, produto_id, venda_id)
VALUES
    (2, 1, 1),
    (1, 2, 1),
    (3, 3, 2),
    (1, 1, 2),
    (1, 4, 3),
    (2, 2, 3),
    (1, 3, 4),
    (1, 4, 4),
    (3, 2, 5),
    (1, 3, 5),
    (1, 1, 5);