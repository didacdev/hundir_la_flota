package common.database;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashMap;

public interface ServicioDatosInterfaz extends Remote {

    public static final String NOMBRE_SERVICIO = "ServicioDatos";
    public static final String host = "localhost";
    public static final int port = 7777;

    // Añadir métodos remotos

    /**
     * Devuelve una lista con la información actual del servidor
     *
     * @return Lista con la información actual del servidor
     * @throws RemoteException
     */
    public Data getData() throws RemoteException;

    /**
     * Devuelve una lista con los usuarios registrados y sus puntuaciones
     *
     * @return Lista con los usuarios registrados y sus puntuaciones
     * @throws RemoteException
     */
    public HashMap<String, HashMap<Integer, Integer>> getUsersList() throws RemoteException;


}
