package servicos;

import contrato.Config;
import contrato.Emprestavel;
import contrato.Reservavel;
import modelo.ItemBiblioteca;
import modelo.Usuario;

public class Biblioteca {

    private final String nome;

    private final ItemBiblioteca[] acervo;
    private int totalItens;

    private final Usuario[] usuarios;
    private int totalUsuarios;

    public Biblioteca(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome da biblioteca e obrigatorio.");
        }
        this.nome = nome;
        this.acervo = new ItemBiblioteca[Config.CAPACIDADE_ACERVO];
        this.usuarios = new Usuario[Config.CAPACIDADE_USUARIOS];
        this.totalItens = 0;
        this.totalUsuarios = 0;
    }

    public void cadastrarItem(ItemBiblioteca item) {
        if (item == null) {
            throw new IllegalArgumentException("Item nao pode ser nulo.");
        }
        if (totalItens >= acervo.length) {
            throw new IllegalStateException("Acervo lotado (limite do array).");
        }
        if (buscarItemPorCodigo(item.getCodigo()) != null) {
            throw new IllegalStateException("Ja existe item com o codigo " + item.getCodigo());
        }
        acervo[totalItens] = item;
        totalItens++;
    }

    public void cadastrarUsuario(Usuario usuario) {
        if (usuario == null) {
            throw new IllegalArgumentException("Usuario nao pode ser nulo.");
        }
        if (totalUsuarios >= usuarios.length) {
            throw new IllegalStateException("Limite de usuarios atingido.");
        }
        usuarios[totalUsuarios] = usuario;
        totalUsuarios++;
    }

    public ItemBiblioteca buscarItemPorCodigo(String codigo) {
        for (int i = 0; i < totalItens; i++) {
            if (acervo[i].getCodigo().equals(codigo)) {   // equals, nunca ==
                return acervo[i];
            }
        }
        return null;
    }

    public boolean emprestar(String codigoItem, Usuario usuario) {

        ItemBiblioteca item = buscarItemPorCodigo(codigoItem);

        if (item == null) {
            System.out.println("   [X] Item nao encontrado: " + codigoItem);
            return false;
        }

        if (item instanceof Emprestavel emprestavel) {
            return emprestavel.emprestar(usuario);
        }

        System.out.printf("   [X] %-22s tentou \"%s\" - obra de consulta local%n",
                usuario.getNome(), item.getTitulo());
        return false;
    }

    public double processarDevolucao(String codigoItem, Usuario usuario, int diasAtraso) {

        ItemBiblioteca item = buscarItemPorCodigo(codigoItem);

        if (item == null || !(item instanceof Emprestavel emprestavel)) {
            return 0.0;
        }

        if (!emprestavel.devolver()) {
            return 0.0;
        }

        double multaBruta = emprestavel.calcularMulta(diasAtraso);
        double multaLiquida = usuario.aplicarDesconto(multaBruta);

        if (multaLiquida > 0) {
            usuario.acumularMulta(multaLiquida);
        }

        return multaLiquida;
    }

    private void imprimirSeparador() {
        System.out.println("-".repeat(Config.LARGURA_RELATORIO));
    }

    public void listarAcervo() {
        System.out.println("[1] ACERVO CADASTRADO (" + totalItens + " itens)");
        imprimirSeparador();

        for (int i = 0; i < totalItens; i++) {
            ItemBiblioteca item = acervo[i];

            System.out.printf("%-8s | %-31s | %2d dias | R$ %.2f/dia%n",
                    item.getCategoria(),
                    item.getTitulo(),
                    item.getPrazoEmprestimoDias(),
                    item.getMultaPorDia());
        }

        System.out.println();
    }

    public double calcularMultasTotais() {
        double total = 0;
        for (int i = 0; i < totalUsuarios; i++) {
            total += usuarios[i].getMultaAcumulada();
        }
        return total;
    }

    public void listarSituacaoUsuarios() {
        System.out.println("[5] SITUACAO DOS USUARIOS");
        imprimirSeparador();

        for (int i = 0; i < totalUsuarios; i++) {
            System.out.println("   " + usuarios[i]);
        }

        imprimirSeparador();
        System.out.printf("   TOTAL EM MULTAS: R$ %.2f%n%n", calcularMultasTotais());
    }

    public void listarReservas() {
        boolean encontrou = false;
        for (int i = 0; i < totalItens; i++) {
            if (acervo[i] instanceof Reservavel r && r.temReserva()) {
                System.out.printf("   %-32s reservado por %s%n",
                        acervo[i].getTitulo(), r.getNomeReservante());
                encontrou = true;
            }
        }
        if (!encontrou) {
            System.out.println("   (nenhuma reserva ativa)");
        }
        System.out.println();
    }

    public void notificarDevedores() {
        System.out.println("[7] NOTIFICACOES AUTOMATICAS");
        imprimirSeparador();

        for (int i = 0; i < totalUsuarios; i++) {
            Usuario u = usuarios[i];
            if (u.getMultaAcumulada() > 0) {
                if (u.getMultaAcumulada() >= 10.0) {
                    u.notificarUrgente(String.format(
                            "Debito de R$ %.2f pendente.", u.getMultaAcumulada()));
                } else {
                    u.notificar(String.format(
                            "Voce possui R$ %.2f em multas.", u.getMultaAcumulada()));
                }
            }
        }
        System.out.println();
    }

    public String getNome() {
        return nome;
    }

    public int getTotalItens() {
        return totalItens;
    }

    public ItemBiblioteca[] getAcervo() {
        return java.util.Arrays.copyOf(acervo, totalItens);
    }
}