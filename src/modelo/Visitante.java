package modelo;

public class Visitante extends Usuario {

    private final String documentoIdentidade;

    public Visitante(String matricula, String nome, String email,
                     String documentoIdentidade) {
        super(matricula, nome, email);

        if (documentoIdentidade == null || documentoIdentidade.isBlank()) {
            throw new IllegalArgumentException("Documento e obrigatorio para visitantes.");
        }

        this.documentoIdentidade = documentoIdentidade;
    }

    @Override
    public int getLimiteItens() {
        return 1;
    }

    @Override
    public double getPercentualDesconto() {
        return 0.0;
    }

    @Override
    public String getCategoria() {
        return "Visitante";
    }

    @Override
    public boolean podeRetirarMaterialRestrito() {
        return false;
    }
}