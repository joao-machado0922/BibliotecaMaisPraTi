package modelo;

public class Professor extends Usuario {
    public Professor(String id, String nome, String email, ItemAcervo[] itensEmprestados, int quantidadeEmprestada, double multaAcumulada) {
        super(id, nome, email, itensEmprestados, quantidadeEmprestada, multaAcumulada);
    }

    @Override
    public int getLimiteEmprestimos() {
        return 5;
    }

    @Override
    public double getPercentualDesconto() {
        return 0.5;
    }
}
