package modelo;

public class DVD extends ItemEmprestavel {
    private final int duracaoMinutos;
    private final String classificacaoIndicativa;

    public DVD(String id, String titulo, int ano, int duracaoMinutos, String classificacaoIndicativa) {
        super(id, titulo, ano);

        if (duracaoMinutos <= 0) {
            throw new IllegalArgumentException("Duração deve ser maior que 0 minutos");
        }

        this.duracaoMinutos = duracaoMinutos;
        this.classificacaoIndicativa = classificacaoIndicativa;
    }

    @Override
    public int getPrazoEmprestimoDias() {
        return 3;
    }

    @Override
    public double getMultaPorDia() {
        return 2.00;
    }

    @Override
    public String getCategoria() {
        return "DVD";
    }

    @Override
    public String getDescricaoDetalhada() {
        return duracaoMinutos + "min | Classificação: " + classificacaoIndicativa;
    }

    public int getDuracaoMinutos() {
        return duracaoMinutos;
    }

    public String getClassificacaoIndicativa() {
        return classificacaoIndicativa;
    }
}
