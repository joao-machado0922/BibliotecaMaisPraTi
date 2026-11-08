package modelo;

import contrato.Reservavel;

public class Livro extends ItemEmprestavel implements Reservavel {
    private final String autor;
    private final String isbn;
    private int renovacoesUtilizadas;
    private String nomeReservante;

    private static final int MAXIMO_RENOVACOES = 2;

    public Livro(String id, String titulo, int ano, String autor, String isbn) {
        super(id, titulo, ano);
        if (autor == null || autor.isBlank()) {
            throw new IllegalArgumentException("Autor inválido");
        }

        if (isbn == null || isbn.isBlank()) {
            throw new IllegalArgumentException("ISBN inválido");
        }

        this.autor = autor;
        this.isbn = isbn;
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

        if (!isDisponivel() && !temReserva()) {
            this.nomeReservante = usuario.getNome();
        }
    }

    @Override
    public boolean temReserva() {
        return nomeReservante != null;
    }

    @Override
    public String getNomeReservante() {
        return nomeReservante == null ? "-" : this.nomeReservante;
    }

    @Override
    public String getCategoria() {
        return "LIVRO";
    }

    @Override
    public String getDescricaoDetalhada() {
        return "Autor: " + this.autor + "| ISBN: " + this.isbn;
    }

    public boolean renovar() {
        if (!permiteRenovacao()) {
            return false;
        }
        renovacoesUtilizadas++;
        return true;
    }
}
