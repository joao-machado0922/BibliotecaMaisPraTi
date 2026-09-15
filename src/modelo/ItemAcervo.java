package modelo;

import contrato.Config;

public abstract class ItemAcervo {
    private final String id;
    private final String titulo;
    private final int ano;
    private boolean disponivel;
    private static int totalItensCriados = 0;

    protected ItemAcervo(String id, String titulo, int ano) {
        if (totalItensCriados == Config.CAPACIDADE_ACERVO) {
            throw new IllegalArgumentException("CAPACIDADE DA BIBLIOTECA NO MÁXIMO!");
        }

        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Código inválido");
        }

        if (titulo == null || titulo.isBlank()) {
            throw new IllegalArgumentException("Título inválido");
        }

        if (ano < Config.ANO_MINIMO_PUBLICACAO || ano > Config.ANO_MAXIMO_PUBLICACAO) {
            throw new IllegalArgumentException("Ano inválido");
        }

        this.id = id;
        this.titulo = titulo;
        this.ano = ano;
        this.disponivel = true;
        totalItensCriados ++;
    }

    public abstract String getCategoria();
    public abstract String getDescricao();

    public String getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public int getAno() {
        return ano;
    }

    public boolean isDisponivel() {
        return this.disponivel;
    }

    protected void marcarComoEmprestado() {
        this.disponivel = false;
    }

    protected void marcarComoDisponivel() {
        this.disponivel = true;
    }

    public static int getTotalItensCriados() {
        return totalItensCriados;
    }
}
