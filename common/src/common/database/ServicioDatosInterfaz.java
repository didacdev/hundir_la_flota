package common.database;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;

public interface ServicioDatosInterfaz extends Remote {

    public static final String NOMBRE_SERVICIO = "ServicioDatos";
    public static final String host = "localhost";

    // Añadir métodos remotos

    /**
     * Devuelve una lista con la información actual del servidor
     *
     * @return Lista con lela información actual del servidor
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

    /**
     * Devuelve una lista con los juegos en ejecución
     * @return Lista con los juegos en ejecución
     * @throws RemoteException
     */
    List<Integer> getStartedGames() throws RemoteException;


}
