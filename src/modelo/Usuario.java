package modelo;

import contrato.Notificavel;
import java.util.Arrays;

public abstract class Usuario implements Notificavel {

    private final String matricula;
    private final String nome;
    private final String email;
    private final ItemAcervo[] itensEmprestados;

    private int quantidadeEmprestada;
    private double multaAcumulada;

    private static int totalUsuariosCriados = 0;

    protected Usuario(String matricula, String nome, String email) {

        if (matricula == null || matricula.isBlank()) {
            throw new IllegalArgumentException("Matricula e obrigatoria.");
        }
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome e obrigatorio.");
        }

        this.matricula = matricula;
        this.nome = nome;
        this.email = email;
        this.quantidadeEmprestada = 0;
        this.multaAcumulada = 0.0;

        this.itensEmprestados = new ItemAcervo[getLimiteItens()];

        totalUsuariosCriados++;
    }

    public abstract int getLimiteItens();

    public abstract double getPercentualDesconto();

    public abstract String getCategoria();

    public abstract boolean podeRetirarMaterialRestrito();

    public double aplicarDesconto(double multaBruta) {
        if (multaBruta < 0) {
            throw new IllegalArgumentException("Multa bruta nao pode ser negativa.");
        }
        return multaBruta * (1 - getPercentualDesconto());
    }

    public boolean podeEmprestarMais() {
        return quantidadeEmprestada < getLimiteItens();
    }

    void registrarEmprestimo(ItemAcervo item) {
        if (!podeEmprestarMais()) {
            throw new IllegalStateException(
                    "Limite de emprestimos ja atingido para " + nome);
        }
        itensEmprestados[quantidadeEmprestada] = item;
        quantidadeEmprestada++;
    }

    boolean registrarDevolucao(ItemAcervo item) {
        for (int i = 0; i < quantidadeEmprestada; i++) {
            if (itensEmprestados[i].equals(item)) {
                for (int j = i; j < quantidadeEmprestada - 1; j++) {
                    itensEmprestados[j] = itensEmprestados[j + 1];
                }
                itensEmprestados[quantidadeEmprestada - 1] = null;
                quantidadeEmprestada--;
                return true;
            }
        }
        return false;
    }

    public void acumularMulta(double valor) {
        if (valor < 0) {
            throw new IllegalArgumentException("Valor de multa nao pode ser negativo.");
        }
        this.multaAcumulada += valor;
    }

    public void quitarMulta(double valor) {
        if (valor <= 0) {
            throw new IllegalArgumentException("Valor do pagamento deve ser positivo.");
        }
        if (valor > multaAcumulada) {
            throw new IllegalStateException(String.format(
                    "Pagamento de R$ %.2f excede o debito de R$ %.2f.", valor, multaAcumulada));
        }
        this.multaAcumulada -= valor;
    }
    public String getMatricula() {
        return matricula;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public int getQuantidadeEmprestada() {
        return quantidadeEmprestada;
    }

    public double getMultaAcumulada() {
        return multaAcumulada;
    }

    public ItemAcervo[] getItensEmprestados() {
        return Arrays.copyOf(itensEmprestados, quantidadeEmprestada);
    }

    public static int getTotalUsuariosCriados() {
        return totalUsuariosCriados;
    }

    @Override
    public String getIdentificacaoContato() {
        return email != null ? email : nome;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Usuario outro = (Usuario) obj;
        return matricula.equals(outro.matricula);
    }

    @Override
    public int hashCode() {
        return matricula.hashCode();
    }

    @Override
    public String toString() {
        return String.format("%s (%s) - %d/%d itens | multa R$ %.2f",
                nome, getCategoria(), quantidadeEmprestada, getLimiteItens(), multaAcumulada);
    }
}