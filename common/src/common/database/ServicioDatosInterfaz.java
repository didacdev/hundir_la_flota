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
    public List<Jugador> getUsersList() throws RemoteException;

    /**
     * Devuelve una lista con los juegos en ejecución
     * @return Lista con los juegos en ejecución
     * @throws RemoteException
     */
    HashMap<Integer, Partida> getStartedGames() throws RemoteException;

    /**
     * Registra un usuario en la base de datos
     * @param nombre nombre del usuario
     * @param password contraseña del usuario
     * @return true si se ha registrado correctamente, false si no
     * @throws RemoteException
     */
    public boolean addUser(String nombre, String password) throws RemoteException;

    /**
     * Devuelve las partidas creadas
     * @return Hashmap con las partidas creadas
     * @throws RemoteException
     */
    public HashMap<Integer, Partida> getCreatedGames() throws RemoteException;

    /**
     * Devuelve las partidas a la espera de jugadores
     * @return Lista con las partidas a la espera de jugadores
     * @throws RemoteException
     */
    public List<Integer> getWaitingGames() throws RemoteException;


}
