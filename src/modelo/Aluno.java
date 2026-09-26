package modelo;

public abstract class Aluno extends Usuario {
    private final String curso;
    private final int periodo;

    protected Aluno(String id, String nome, String email, String curso, int periodo) {
        super(id, nome, email);
        this.curso = curso;
        this.periodo = periodo;
    }

    public String getCurso() {
        return this.curso;
    }
}