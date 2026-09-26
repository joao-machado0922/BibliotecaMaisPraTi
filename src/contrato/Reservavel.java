package contrato;

import modelo.Usuario;

public interface Reservavel {
    void reservar(Usuario usuario);
    boolean temReserva();
    String getNomeReservante();
}
