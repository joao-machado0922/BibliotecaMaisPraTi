package modelo;

public class PosGraduado extends Aluno {

    public PosGraduado(String id, String nome, String email, ItemAcervo[] itensEmprestados, int quantidadeEmprestada, double multaAcumulada, String curso) {
        super(id, nome, email, itensEmprestados, quantidadeEmprestada, multaAcumulada, curso);
    }

    @Override
    public int getLimiteEmprestimos() {
        return 4;
    }

    @Override
    public double getPercentualDesconto() {
        return 0.2;
    }
}
