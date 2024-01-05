package common.client;

import java.rmi.Remote;

public interface CallbackJugadorIterfaz extends Remote {
    public static final String NOMBRE_SERVICIO = "CallbackJugador";
    public static final String host = "localhost";
    public static final int port = 0;

    // Añadir métodos remotos
}
