DROP TABLE IF EXISTS emprestimo;
DROP TABLE IF EXISTS usuario;
DROP TABLE IF EXISTS item;

CREATE TABLE IF NOT EXISTS item (
	id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
	codigo VARCHAR NOT NULL, titulo VARCHAR NOT NULL,
	tipo VARCHAR NOT NULL,
	autor VARCHAR NOT NULL,
	edicao INTEGER NOT NULL,
	disponivel BOOLEAN,
	
	CONSTRAINT ck_tipo_item CHECK (tipo IN ('LIVRO', 'REVISTA', 'DVD'))
);

CREATE TABLE IF NOT EXISTS usuario (
	id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
	nome VARCHAR(50) NOT NULL,
	tipo VARCHAR(20) NOT NULL,
	limite_itens INTEGER,
	
	CONSTRAINT ck_tipo_usuario CHECK (tipo IN ('PROFESSOR', 'ALUNO'))
);
	
CREATE TABLE IF NOT EXISTS emprestimo (
	id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
	item_id BIGINT NOT NULL,
	usuario_id BIGINT NOT NULL,
	data_retirada TIMESTAMPTZ NOT NULL DEFAULT now(),
	data_devolucao_prevista TIMESTAMPTZ NOT NULL,
	data_devolucao TIMESTAMPTZ,
	valor_multa INTEGER,
	
	CONSTRAINT fk_emprestimo_item FOREIGN KEY (item_id) REFERENCES item(id) ON DELETE CASCADE,
	CONSTRAINT fk_emprestimo_usuario FOREIGN KEY (usuario_id) REFERENCES usuario(id) ON DELETE CASCADE,
	CONSTRAINT ck_data_devolucao CHECK (data_devolucao IS NULL OR data_devolucao > data_retirada),
	CONSTRAINT ck_prev_devolucao CHECK (data_devolucao_prevista > data_retirada)
); 

INSERT INTO item (codigo, titulo, tipo, autor, edicao, disponivel) VALUES
('8583629501', 'Classroom of the Elite', 'LIVRO', 'Syougo Kinugasa', 1, TRUE),
('855651121X', 'Devoradores de Estrelas', 'LIVRO', 'Andy Weir', 1, TRUE),
('8556521142', 'A Assombração da Casa da Colina', 'LIVRO', 'Shirley Jackson', 1, TRUE),
('8551013130', 'Casas Estranhas', 'LIVRO', 'Uketsu', 1, TRUE),
('9788532529622', 'Harry Potter e a Pedra Filosofal', 'LIVRO', 'J. K. Rowling', 1, TRUE),
('9788535914849', '1984', 'LIVRO', 'George Orwell', 1, TRUE),
('9788575228773', 'Código Limpo', 'LIVRO', 'Robert C. Martin', 1, FALSE),
('9786555320347', 'O Hobbit', 'LIVRO', 'J. R. R. Tolkien', 1, TRUE),
('9788535914840', 'A Revolução dos Bichos', 'LIVRO', 'George Orwell', 1, FALSE),
('9788532529623', 'O Senhor dos Anéis', 'LIVRO', 'J. R. R. Tolkien', 2, TRUE),
('9788575221125', 'Estruturas de Dados e Algoritmos', 'LIVRO', 'Nivio Ziviani', 3, TRUE),

('REV001', 'Superinteressante', 'REVISTA', 'Editora Abril', 425, FALSE),
('REV002', 'National Geographic', 'REVISTA', 'National Geographic Society', 310, TRUE),
('REV003', 'Scientific American Brasil', 'REVISTA', 'Scientific American', 205, TRUE),

('DVD001', 'Interestelar', 'DVD', 'Christopher Nolan', 1, FALSE),
('DVD002', 'O Jogo da Imitação', 'DVD', 'Morten Tyldum', 1, TRUE);

INSERT INTO usuario (nome, tipo, limite_itens) VALUES
('Nicolau Antônio', 'ALUNO', 3),
('Roberval', 'PROFESSOR', 5),
('Ana Ribeiro', 'ALUNO', 3),
('Bruno Alves', 'ALUNO', 3),
('Carla Nunes', 'ALUNO', 3),
('Diego Martins', 'PROFESSOR', 5),
('Fernanda Costa', 'PROFESSOR', 5),
('Gabriel Oliveira', 'ALUNO', 3),
('Helena Souza', 'ALUNO', 3),
('Ricardo Almeida', 'PROFESSOR', 5);

INSERT INTO emprestimo (item_id, usuario_id, data_retirada, data_devolucao_prevista, data_devolucao, valor_multa) VALUES
(7, 3, '2026-10-20 10:00:00-03', '2026-11-03 10:00:00-03', NULL, 0),
(12, 4, '2026-10-25 14:30:00-03', '2026-11-01 14:30:00-03', NULL, 0),
(9, 5, '2026-10-28 09:00:00-03', '2026-11-11 09:00:00-03', NULL, 0),
(15, 6, '2026-11-01 15:00:00-03', '2026-11-04 15:00:00-03', NULL, 0),
(5, 8, '2026-11-02 11:00:00-03', '2026-11-16 11:00:00-03', NULL, 0);

INSERT INTO emprestimo (item_id, usuario_id, data_retirada, data_devolucao_prevista, data_devolucao, valor_multa) VALUES
(6, 3, '2026-09-01 09:00:00-03', '2026-09-15 09:00:00-03', '2026-09-18 09:00:00-03', 150),
(8, 3, '2026-09-20 10:00:00-03', '2026-10-04 10:00:00-03', '2026-10-04 09:00:00-03', 0),
(13, 4, '2026-10-01 08:00:00-03', '2026-10-08 08:00:00-03', '2026-10-12 08:00:00-03', 400),
(6, 5, '2026-10-05 13:00:00-03', '2026-10-19 13:00:00-03', '2026-10-21 13:00:00-03', 100),
(16, 6, '2026-10-10 16:00:00-03', '2026-10-13 16:00:00-03', '2026-10-16 16:00:00-03', 600),
(14, 7, '2026-10-15 11:00:00-03', '2026-10-22 11:00:00-03', '2026-10-22 10:00:00-03', 0);

UPDATE item i SET disponivel = NOT EXISTS (SELECT 1 FROM emprestimo e 
WHERE e.item_id = i.id AND e.data_devolucao IS NULL);