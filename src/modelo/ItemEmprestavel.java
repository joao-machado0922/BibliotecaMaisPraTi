package modelo;

import contrato.Emprestavel;

public abstract class ItemEmprestavel extends ItemAcervo implements Emprestavel {

    private Usuario usuarioAtual;

    protected ItemEmprestavel(String id, String titulo, int ano) {
        super(id, titulo, ano);
        this.usuarioAtual = null;
    }

    @Override
    public boolean emprestar(Usuario usuario) {
        if(usuario == null) {
            throw new IllegalArgumentException("Usuário não encontrado");
        }

        if(!isDisponivel()) {
            throw new IllegalArgumentException("Item indisponível");
        }

        marcarComoEmprestado();

        return true;
    }

}
