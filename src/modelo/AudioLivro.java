package modelo;

public class AudioLivro extends ItemEmprestavel {
    private final String narrador;
    private final int duracaoMinutos;

    public AudioLivro(String id, String titulo, int ano, String narrador, int duracaoMinutos) {
        super(id, titulo, ano);

        if (narrador == null || narrador.isBlank()) {
            throw new IllegalArgumentException("Narrador é obrigatório");
        }
        if (duracaoMinutos <= 0) {
            throw new IllegalArgumentException("Duração deve ser maior que 0 minutos");
        }
        this.narrador = narrador;
        this.duracaoMinutos = duracaoMinutos;
    }

    @Override
    public int getPrazoEmprestimoDias() {
        return 10;
    }

    @Override
    public double getMultaPorDia() {
        return 0.75;
    }

    @Override
    public String getCategoria() {
        return "AUDIO";
    }

    @Override
    public String getDescricaoDetalhada() {
        return "Narrador por" + narrador + "| " + duracaoMinutos + "min";
    }
}
