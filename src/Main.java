import contrato.Config;
import contrato.Emprestavel;
import modelo.*;
import servicos.Biblioteca;

/*
 * ============================================================
 * MAIN - demonstracao dos quatro pilares em funcionamento
 * ============================================================
 * Execute esta classe. Cada secao da saida corresponde a uma
 * fase da atividade. Acompanhe com os comentarios ao lado.
 * ============================================================
 */

/**
 * Programa principal do sistema de biblioteca.
 *
 * <p>Roteiro da demonstracao:</p>
 * <ol>
 *   <li>acervo cadastrado (polimorfismo na listagem);</li>
 *   <li>emprestimos, com todas as regras sendo aplicadas;</li>
 *   <li>obras de referencia e o bloqueio em tempo de compilacao;</li>
 *   <li>devolucoes com multa e desconto por tipo de usuario;</li>
 *   <li>situacao dos usuarios;</li>
 *   <li>reservas (multiplas interfaces);</li>
 *   <li>notificacoes (metodos default);</li>
 *   <li>testes de encapsulamento;</li>
 *   <li>equals e hashCode;</li>
 *   <li>prova de extensibilidade.</li>
 * </ol>
 */
public class Main {

    public static void main(String[] args) {

        titulo("BIBLIOTECA UNIVERSITARIA - DEMONSTRACAO");

        Biblioteca biblioteca = new Biblioteca("Biblioteca Central");

        // ============================================================
        // CADASTRO DO ACERVO
        // ============================================================
        // Todos os objetos abaixo sao de classes DIFERENTES, mas o metodo
        // cadastrarItem() recebe ItemBiblioteca. Isso e UPCASTING: a subclasse
        // vira o tipo do pai automaticamente, sem cast e sem risco.
        // ------------------------------------------------------------

        Livro cleanCode = new Livro("L001", "Clean Code", 2008,
                "Robert C. Martin", "978-0132350884");
        Livro estruturas = new Livro("L002", "Estruturas de Dados", 2012,
                "Nivio Ziviani", "978-8522110506");
        Revista sciAm = new Revista("R001", "Scientific American", 2026,
                412, "04/2026");
        DVD documentario = new DVD("D001", "Documentario: Codigos", 2019,
                92, "Livre");

        biblioteca.cadastrarItem(cleanCode);
        biblioteca.cadastrarItem(estruturas);
        biblioteca.cadastrarItem(sciAm);
        biblioteca.cadastrarItem(documentario);

        // ============================================================
        // CADASTRO DE USUARIOS
        // ============================================================

        Aluno ana = new Aluno("G2024001", "Ana Ribeiro",
                "ana@uni.br", "Ciencia da Computacao", 4);
        Aluno bruno = new Aluno("G2024002", "Bruno Alves",
                "bruno@uni.br", "Engenharia", 2);
        Aluno carla = new Aluno("P2023010", "Carla Nunes",
                "carla@uni.br", "Mestrado em CC", 4);
        Professor diego = new Professor("D0099", "Diego Martins",
                "diego@uni.br", "Computacao", "Dr.");

        biblioteca.cadastrarUsuario(ana);
        biblioteca.cadastrarUsuario(bruno);
        biblioteca.cadastrarUsuario(carla);
        biblioteca.cadastrarUsuario(diego);

        // ============================================================
        // [1] LISTAGEM POLIMORFICA DO ACERVO
        // ============================================================

        biblioteca.listarAcervo();

        // ============================================================
        // [2] EMPRESTIMOS
        // ============================================================

        System.out.println("[2] EMPRESTIMOS");
        separador();

        biblioteca.emprestar("L001", ana);       // ok
        biblioteca.emprestar("R001", ana);       // ok
        biblioteca.emprestar("D001", ana);       // ok — atinge o limite de 3
        biblioteca.emprestar("L002", ana);       // bloqueado: limite
        biblioteca.emprestar("L001", bruno);     // bloqueado: indisponivel
        biblioteca.emprestar("L002", diego);     // ok
        System.out.println();

        // ============================================================
        // [4] DEVOLUCOES COM ATRASO
        // ============================================================

        System.out.println("[4] DEVOLUCOES COM 5 DIAS DE ATRASO");
        separador();

        demonstrarDevolucao(biblioteca, "L001", ana, 5);       // livro: 0,50/dia
        demonstrarDevolucao(biblioteca, "D001", ana, 5);       // DVD:   2,00/dia
        demonstrarDevolucao(biblioteca, "L002", diego, 5);     // livro: 0,50/dia, -50%
        System.out.println();

        // ============================================================
        // [5] SITUACAO DOS USUARIOS
        // ============================================================

        biblioteca.listarSituacaoUsuarios();

        // ============================================================
        // [6] RESERVAS — multiplas interfaces
        // ============================================================

        System.out.println("[6] RESERVAS");
        separador();

        // Clean Code voltou ao acervo na secao [4]; Bruno pega agora.
        biblioteca.emprestar("L001", bruno);
        cleanCode.reservar(carla);                    // Carla entra na fila
        System.out.println();

        biblioteca.listarReservas();

        // ⚠️ A LINHA ABAIXO NAO COMPILA:
        //
        //     sciAm.reservar(carla);
        //
        // Revista nao implementa Reservavel. Mesma logica do caso anterior:
        // a capacidade que a classe nao tem, ela nao finge ter.

        // ============================================================
        // [7] NOTIFICACOES — metodos default de interface
        // ============================================================

        biblioteca.notificarDevedores();

        // ============================================================
        // [8] TESTES DE ENCAPSULAMENTO
        // ============================================================

        System.out.println("[8] TESTES DE ENCAPSULAMENTO");
        separador();

        // --- Teste 1: bloqueado em COMPILACAO ---
        //
        //     cleanCode.disponivel = true;
        //
        // Erro: disponivel has private access in ItemBiblioteca
        System.out.println("   [OK] Compilacao: cleanCode.disponivel = true - bloqueado");

        // --- Teste 2: bloqueado em COMPILACAO ---
        //
        //     ana.multaAcumulada = 0;
        //
        // Erro: multaAcumulada has private access in Usuario
        System.out.println("   [OK] Compilacao: ana.multaAcumulada = 0 - bloqueado");

        // --- Teste 3: invariante do construtor ---
        try {
            new Livro("L999", "Livro Impossivel", 1200, "Autor", "000");
        } catch (IllegalArgumentException e) {
            System.out.println("   [OK] Excecao: " + e.getMessage());
        }

        // --- Teste 4: invariante de metodo ---
        try {
            ana.acumularMulta(-50);
        } catch (IllegalArgumentException e) {
            System.out.println("   [OK] Excecao: " + e.getMessage());
        }

        // --- Teste 5: operacao invalida para o estado atual ---
        try {
            bruno.quitarMulta(100.0);         // Bruno nao deve nada
        } catch (IllegalStateException e) {
            System.out.println("   [OK] Excecao: " + e.getMessage());
        }

        // --- Teste 6: COPIA DEFENSIVA ---
        // Este e o teste mais importante da secao. Sem Arrays.copyOf no
        // getter, o "private" do atributo seria puramente decorativo.
        ItemBiblioteca[] copiaExterna = ana.getItensEmprestados();
        int antes = ana.getQuantidadeEmprestada();

        if (copiaExterna.length > 0) {
            copiaExterna[0] = null;           // sabotagem deliberada
        }

        int depois = ana.getQuantidadeEmprestada();
        ItemBiblioteca[] novaConsulta = ana.getItensEmprestados();

        boolean intacto = (antes == depois)
                && (novaConsulta.length == 0 || novaConsulta[0] != null);

        System.out.println("   [" + (intacto ? "OK" : "FALHOU")
                + "] Copia defensiva: alterar o array devolvido nao afetou o estado interno");
        System.out.println();

        // ============================================================
        // [9] equals() E hashCode()
        // ============================================================

        System.out.println("[9] IGUALDADE DE OBJETOS");
        separador();

        Livro copiaDoMesmoLivro = new Livro("L001", "Clean Code", 2008,
                "Robert C. Martin", "978-0132350884");

        System.out.println("   cleanCode == copia .............. "
                + (cleanCode == copiaDoMesmoLivro)
                + "   (compara ENDERECOS - sao dois objetos distintos)");
        System.out.println("   cleanCode.equals(copia) ......... "
                + cleanCode.equals(copiaDoMesmoLivro)
                + "    (compara CONTEUDO - mesmo codigo L001)");
        System.out.println("   hashCodes iguais ................ "
                + (cleanCode.hashCode() == copiaDoMesmoLivro.hashCode())
                + "    (obrigatorio quando equals e true)");
        System.out.println();

        // ============================================================
        // RESUMO FINAL — atributos e metodos static
        // ============================================================

        titulo("RESUMO");
        System.out.println("   Itens criados nesta execucao ..... "
                + ItemBiblioteca.getTotalItensCriados());
        System.out.println("   Usuarios criados ................. "
                + Usuario.getTotalUsuariosCriados());
        System.out.println("   Itens no acervo .................. "
                + biblioteca.getTotalItens());
        System.out.printf("   Total em multas .................. R$ %.2f%n",
                biblioteca.calcularMultasTotais());
        System.out.println();
        System.out.println("   Note que getTotalItensCriados() e chamado pela CLASSE,");
        System.out.println("   sem objeto algum. E o mesmo motivo pelo qual main e");
        System.out.println("   static e Math.sqrt() funciona sem new Math().");
        System.out.println();
    }

    // ============================================================
    // METODOS AUXILIARES — static porque so dependem dos parametros
    // ============================================================

    /**
     * Processa uma devolucao e imprime o detalhamento da multa.
     *
     * <p>O parametro e {@code Usuario}, o tipo abstrato. Este metodo funciona
     * para graduacao, pos, professor, visitante — e para qualquer tipo criado
     * no futuro.</p>
     */
    private static void demonstrarDevolucao(Biblioteca biblioteca, String codigo,
                                            Usuario usuario, int diasAtraso) {

        ItemBiblioteca item = biblioteca.buscarItemPorCodigo(codigo);
        if (!(item instanceof Emprestavel e)) {
            return;
        }

        double bruta = e.calcularMulta(diasAtraso);
        double liquida = biblioteca.processarDevolucao(codigo, usuario, diasAtraso);

        System.out.printf("   %-16s %-24s bruta R$ %6.2f | desc. %3.0f%% | paga R$ %6.2f%n",
                usuario.getNome().split(" ")[0] + " (" + usuario.getCategoria() + ")",
                "\"" + item.getTitulo() + "\"",
                bruta,
                usuario.getPercentualDesconto() * 100,
                liquida);
    }

    private static void separador() {
        System.out.println("-".repeat(Config.LARGURA_RELATORIO));
    }

    private static void titulo(String texto) {
        System.out.println();
        System.out.println("=".repeat(Config.LARGURA_RELATORIO));
        System.out.println("   " + texto);
        System.out.println("=".repeat(Config.LARGURA_RELATORIO));
        System.out.println();
    }
}