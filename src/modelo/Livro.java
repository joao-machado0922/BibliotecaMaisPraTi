package modelo;

import contrato.Reservavel;

public class Livro extends ItemEmprestavel implements Reservavel {
    private final String autor;
    private final String isbn;
    private final int quantidadePaginas;
    private int renovacoesUtilizadas;
    private String nomeReservante;

    private static final int MAXIMO_RENOVACOES = 2;

    public Livro(String id, String titulo, int ano, String autor, String isbn, int quantidadePaginas) {
        super(id, titulo, ano);
        if (autor == null || autor.isBlank()) {
            throw new IllegalArgumentException("Autor inválido");
        }

        if (isbn == null || isbn.isBlank()) {
            throw new IllegalArgumentException("ISBN inválido");
        }

        if (quantidadePaginas <= 0) {
            throw new IllegalArgumentException("Quantidade de páginas inválida");
        }

        this.autor = autor;
        this.isbn = isbn;
        this.quantidadePaginas = quantidadePaginas;
        this.renovacoesUtilizadas = 0;
        this.nomeReservante = null;
    }

    @Override
    public int getPrazoEmprestimoDias() {
        return 14;
    }

    @Override
    public double getMultaPorDia() {
        return 0.50;
    }

    @Override
    public boolean permiteRenovacao() {
        return renovacoesUtilizadas < MAXIMO_RENOVACOES;
    }

    @Override
    public void reservar(Usuario usuario) {
        if (usuario == null) {
            throw new IllegalArgumentException("Usuário inválido");
        }

        if (isDisponivel()) {
           this.nomeReservante = usuario.getNome();
        }
    }

    @Override
    public boolean temReserva() {
        return nomeReservante != null;
    }

    @Override
    public String getReservante() {
        return nomeReservante == null ? "-" : this.nomeReservante;
    }

    @Override
    public String getCategoria() {
        return "Livro";
    }

    @Override
    public String getDescricao() {
        return "Autor: " + this.autor + "| Páginas: " + this.quantidadePaginas + "| ISBN: " + this.isbn;
    }

    public boolean renovar() {
        if (!permiteRenovacao()) {
            return false;
        }
        renovacoesUtilizadas++;
        return true;
    }
}
