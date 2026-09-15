package modelo;

public class AlunoGraduacao extends Aluno {
    public AlunoGraduacao(String id, String nome, String email, ItemAcervo[] itensEmprestados, int quantidadeEmprestada, double multaAcumulada, String curso) {
        super(id, nome, email, itensEmprestados, quantidadeEmprestada, multaAcumulada, curso);
    }

    @Override
    public int getLimiteEmprestimos() {
        return 3;
    }

    @Override
    public double getPercentualDesconto() {
        return 0;
    }
}
