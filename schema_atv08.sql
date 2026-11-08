/*1. listar todo o acervo, com código, título, tipo e disponibilidade.*/

SELECT codigo, titulo, tipo, disponivel FROM item;

/*2. listar os empréstimos em aberto, com o nome do usuário e o título do item.*/

SELECT nome, titulo FROM usuario u JOIN emprestimo e
ON u.id = e.usuario_id AND e.data_devolucao IS NULL
JOIN item i ON i.id = e.item_id;

/*3. calcular o total de multas acumuladas por usuário.*/

SELECT u.nome, SUM(e.valor_multa) FROM usuario u LEFT JOIN emprestimo e
ON u.id = e.usuario_id
GROUP BY u.nome;

/*4. listar os itens que nunca foram emprestados.*/

SELECT titulo, autor, tipo FROM item i LEFT JOIN emprestimo e
ON i.id = e.item_id
WHERE e.item_id IS NULL;