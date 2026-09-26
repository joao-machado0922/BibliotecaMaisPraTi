package modelo;

public class Revista extends ItemEmprestavel{
    private final int edicao;
    private final String mesAnoPublicacao;

    public Revista(String id, String titulo, int ano, int edicao, String mesAnoPublicacao) {
        super(id, titulo, ano);
        if (edicao <= 0) {
            throw new IllegalArgumentException("Numero da edicao deve ser maior que zero");
        }

        this.edicao = edicao;
        this.mesAnoPublicacao = mesAnoPublicacao;
    }

    @Override
    public int getPrazoEmprestimoDias() {
        return 7;
    }

    @Override
    public double getMultaPorDia() {
        return 1.00;
    }

    @Override
    public String getCategoria() {
        return "REVISTA";
    }

    @Override
    public String getDescricaoDetalhada() {
        return "Edicao " + edicao + " | " + mesAnoPublicacao;
    }

    public int getEdicao() {
        return edicao;
    }

    public String getMesAnoPublicacao() {
        return mesAnoPublicacao;
    }
}
