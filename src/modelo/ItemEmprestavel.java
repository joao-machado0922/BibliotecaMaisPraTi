package modelo;

import contrato.Emprestavel;

public abstract class ItemEmprestavel extends ItemBiblioteca implements Emprestavel {

    private Usuario usuarioAtual;

    protected ItemEmprestavel(String codigo, String titulo, int ano) {
        super(codigo, titulo, ano);
        this.usuarioAtual = null;
    }

    @Override
    public boolean emprestar(Usuario usuario) {

        if (usuario == null) {
            throw new IllegalArgumentException("Usuario nao pode ser nulo.");
        }

        if (!isDisponivel()) {
            System.out.printf("   [X] %-22s tentou \"%s\" - item indisponivel%n",
                    usuario.getNome(), getTitulo());
            return false;
        }

        if (!usuario.podeEmprestarMais()) {
            System.out.printf("   [X] %-22s tentou \"%s\" - limite de %d item(s) atingido%n",
                    usuario.getNome(), getTitulo(), usuario.getLimiteItens());
            return false;
        }

        usuario.registrarEmprestimo(this);
        marcarComoEmprestado();
        this.usuarioAtual = usuario;

        System.out.printf("   [OK] %-21s pegou \"%s\"  [%d/%d]%n",
                usuario.getNome(), getTitulo(),
                usuario.getQuantidadeEmprestada(), usuario.getLimiteItens());
        return true;
    }

    @Override
    public boolean devolver() {
        if (isDisponivel()) {
            return false;
        }
        if (usuarioAtual != null) {
            usuarioAtual.registrarDevolucao(this);
        }
        marcarComoDisponivel();
        this.usuarioAtual = null;
        return true;
    }

    public Usuario getUsuarioAtual() {
        return usuarioAtual;
    }
    public String getNomeUsuarioAtual() {
        return usuarioAtual == null ? "-" : usuarioAtual.getNome();
    }
}