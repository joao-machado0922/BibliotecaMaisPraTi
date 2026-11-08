package modelo;

import contrato.Config;

public abstract class ItemBiblioteca {

    private final String codigo;

    private final String titulo;
    private final int ano;

    private boolean disponivel;

    private static int totalItensCriados = 0;

    protected ItemBiblioteca(String codigo, String titulo, int ano) {

        if (codigo == null || codigo.isBlank()) {
            throw new IllegalArgumentException("Codigo do item e obrigatorio.");
        }

        if (titulo == null || titulo.isBlank()) {
            throw new IllegalArgumentException("Titulo do item e obrigatorio.");
        }

        if (ano < Config.ANO_MINIMO_PUBLICACAO || ano > Config.ANO_MAXIMO_PUBLICACAO) {
            throw new IllegalArgumentException(
                    "Ano invalido: " + ano + ". Esperado entre "
                            + Config.ANO_MINIMO_PUBLICACAO + " e " + Config.ANO_MAXIMO_PUBLICACAO + ".");
        }

        this.codigo = codigo;
        this.titulo = titulo;
        this.ano = ano;
        this.disponivel = true;

        totalItensCriados++;
    }

    public abstract int getPrazoEmprestimoDias();

    public abstract double getMultaPorDia();

    public abstract String getCategoria();

    public abstract String getDescricaoDetalhada();

    public String getCodigo() {
        return codigo;
    }

    public String getTitulo() {
        return titulo;
    }

    public int getAno() {
        return ano;
    }

    public boolean isDisponivel() {
        return disponivel;
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ItemBiblioteca outro = (ItemBiblioteca) obj;
        return codigo.equals(outro.codigo);
    }

    @Override
    public int hashCode() {
        return codigo.hashCode();
    }

    @Override
    public String toString() {
        return String.format("%-8s | %-32s | %d", getCategoria(), titulo, ano);
    }
}