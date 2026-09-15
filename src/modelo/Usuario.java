package modelo;

public abstract class Usuario {
    private final String id;
    private final String nome;
    private final String email;
    private final ItemAcervo[] itensEmprestados;
    private int quantidadeEmprestada;
    private double multaAcumulada;

    private static int totalUsuariosCriados = 0;

    protected Usuario(String id, String nome, String email, ItemAcervo[] itensEmprestados, int quantidadeEmprestada, double multaAcumulada) {
        this.totalUsuariosCriados++;

        this.id = id;
        this.nome = nome;
        this.email = email;
        this.itensEmprestados = itensEmprestados;
        this.quantidadeEmprestada = quantidadeEmprestada;
        this.multaAcumulada = multaAcumulada;
    }

    public String getNome() {
        return this.nome;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public ItemAcervo[] getItensEmprestados() {
        return itensEmprestados;
    }

    public int getQuantidadeEmprestada() {
        return quantidadeEmprestada;
    }

    public double getMultaAcumulada() {
        return multaAcumulada;
    }

    public static int getTotalUsuariosCriados() {
        return totalUsuariosCriados;
    }

    public abstract int getLimiteEmprestimos();

    public abstract double getPercentualDesconto();
}
