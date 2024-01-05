package common.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServicioAutenticacionInterfaz extends Remote {
    public static final String NOMBRE_SERVICIO = "ServicioAutenticacion";
    public static final String host = "localhost";
    public static final int port = 7778;

    // Añadir métodos remotos
}


