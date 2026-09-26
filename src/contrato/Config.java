package contrato;

import java.time.LocalDate;

public interface Config {
    int CAPACIDADE_ACERVO = 50;
    int CAPACIDADE_USUARIOS = 30;
    int ANO_MINIMO_PUBLICACAO = 1400;
    int ANO_MAXIMO_PUBLICACAO = LocalDate.now().getYear();
    int LARGURA_RELATORIO = 62;
}
