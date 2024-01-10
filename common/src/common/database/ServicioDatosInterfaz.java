package common.database;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;

public interface ServicioDatosInterfaz extends Remote {

    public static final String NOMBRE_SERVICIO = "ServicioDatos";

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
     * Añade un usuario a la lista de usuarios registrados
     * @param username nombre del usuario
     * @throws RemoteException
     */
    public void addOnlineUser(String username) throws RemoteException;

    /**
     * Devuelve la lista de jugadores logeados
     * @return Lista de jugadores logeados
     * @throws RemoteException
     */
    public List<String> getOnlineUsers() throws RemoteException;

    /**
     * Devuelve una lista con los juegos en ejecución
     * @return Lista con los juegos en ejecución
     * @throws RemoteException
     */
    HashMap<Integer, Partida> getStartedGames() throws RemoteException;

    /**
     * Añade un usuario a la lista de usuarios registrados
     * @param nombre nombre del usuario
     * @param password contraseña del usuario
     * @param clientID id del cliente
     * @return true si se ha registrado correctamente, false si no
     * @throws RemoteException
     */
    public boolean addUser(String nombre, String password, Integer clientID) throws RemoteException;

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

    /**
     * Añade una partida a la lista de partidas creadas
     * @param partida partida a añadir
     * @return id de la partida
     * @throws RemoteException
     */
    public Integer addCreatedGame(Partida partida) throws RemoteException;

    /**
     * Añade una partida a la lista de partidas en ejecución
     * @param gameId id de la partida
     * @throws RemoteException
     */
    public void addWaitingGame(Integer gameId) throws RemoteException;

    /**
     * Elimina una partida de la lista de partidas en espera
     * @param gameId id de la partida
     * @throws RemoteException
     */
    public void removeWaitingGame(Integer gameId) throws RemoteException;

    /**
     * Añade una partida a la lista de partidas en ejecución
     * @param gameId id de la partida
     * @throws RemoteException
     */
    public void addStartedGame(Integer gameId) throws RemoteException;

    /**
     * Actualiza una partida creada
     * @param gameId id de la partida
     * @param partida partida actualizada
     * @throws RemoteException
     */
    public void updateStartedGame(Integer gameId, Partida partida) throws RemoteException;

    /**
     * Elimina un jugador de la lista de jugadores logeados
     * @param username nombre del jugador
     * @throws RemoteException
     */
    public void removeOnlineUser(String username) throws RemoteException;

}
