package modelo;

public class AlunoPosGraduado extends Usuario {

    private final String programa;
    private final String orientador;

    public AlunoPosGraduado(String matricula, String nome, String email,
                             String programa, String orientador) {
        super(matricula, nome, email);
        this.programa = programa;
        this.orientador = orientador;
    }

    @Override
    public int getLimiteItens() {
        return 5;
    }

    @Override
    public double getPercentualDesconto() {
        return 0.20;     // 20%
    }

    @Override
    public String getCategoria() {
        return "Pos-graduacao";
    }

    @Override
    public boolean podeRetirarMaterialRestrito() {
        return true;
    }
    @Override
    public String toString() {
        return super.toString() + " | " + programa;
    }
}
