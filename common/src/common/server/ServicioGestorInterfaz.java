package common.server;

import java.rmi.Remote;

public interface ServicioGestorInterfaz extends Remote {

    public static final String NOMBRE_SERVICIO = "ServicioGestor";
    public static final String host = "localhost";
    public static final int port = 7779;

    // Añadir métodos remotos
}