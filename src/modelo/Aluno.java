package modelo;

public class Aluno extends Usuario {
    private final String curso;
    private final int periodo;

    public Aluno(String id, String nome, String email, String curso, int periodo) {
        super(id, nome, email);

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

    public String getCurso() {
        return curso;
    }

    public int getPeriodo() {
        return periodo;
    }
}