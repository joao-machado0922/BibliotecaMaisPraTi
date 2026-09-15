package modelo;

public abstract class Aluno extends Usuario {
    private final String curso;

    protected Aluno(String id, String nome, String email, ItemAcervo[] itensEmprestados, int quantidadeEmprestada, double multaAcumulada, String curso) {
        super(id, nome, email, itensEmprestados, quantidadeEmprestada, multaAcumulada);
        this.curso = curso;
    }
}