package modelo;

public class AlunoGraduacao extends Usuario {

    private final String curso;
    private final int periodo;

    public AlunoGraduacao(String matricula, String nome, String email,
                          String curso, int periodo) {
        super(matricula, nome, email);

        if (periodo < 1 || periodo > 12) {
            throw new IllegalArgumentException("Periodo deve estar entre 1 e 12.");
        }

        this.curso = curso;
        this.periodo = periodo;
    }

    @Override
    public int getLimiteItens() {
        return 3;
    }

    @Override
    public double getPercentualDesconto() {
        return 0.0;
    }

    @Override
    public String getCategoria() {
        return "Graduacao";
    }

    @Override
    public boolean podeRetirarMaterialRestrito() {
        return false;
    }

    public String getCurso() {
        return curso;
    }

    public int getPeriodo() {
        return periodo;
    }
}