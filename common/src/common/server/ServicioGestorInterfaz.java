package common.server;

import common.client.CallbackJugadorIterfaz;
import common.database.Coordinate;
import common.database.Jugador;
import common.database.Partida;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;

public interface ServicioGestorInterfaz extends Remote {

    public static final String NOMBRE_SERVICIO = "ServicioGestor";

    // Añadir métodos remotos

    /**
     * Añade una partida en curso
     * @param gameID id de la partida
     * @param partida partida
     * @throws RemoteException
     */
    public void addPartidaEnCurso(Integer gameID, Partida partida) throws RemoteException;

    /**
     * Devuelve un jugador
     * @param nombre nombre del jugador
     * @return jugador
     * @throws Exception
     */
    public Jugador getJugador(String nombre) throws RemoteException;

    /**
     * Devuelve una lista con las partidas a la espera de jugadores y el jugador que las ha creado
     * @return Lista con las partidas a la espera de jugadores y el jugador que las ha creado
     * @throws RemoteException
     */
    public List<String> getWaitingGamesWithCreators() throws RemoteException;

    /**
     * Inicia una partida
     * @param username nombre del jugador
     * @post partida añadida a la lista de partidas en ejecución y a la lista de partidas creadas
     * @throws RemoteException
     * @return true si se ha iniciado correctamente, false si no
     */
    public Boolean startGame(String username) throws RemoteException;

    /**
     * Une un jugador a una partida
     * @param username nombre del jugador
     * @param gameId id de la partida
     * @return true si se ha unido correctamente, false si no
     * @throws RemoteException
     */
    public Boolean joinGame(String username, int gameId) throws RemoteException;

    /**
     * Añade un callback a la lista de callbacks
     * @param callbackClientObject callback
     * @param clientID id del cliente
     * @throws RemoteException
     */
    public void registerForCallback(CallbackJugadorIterfaz callbackClientObject, Integer clientID) throws RemoteException;

    /**
     * Algortimo de juego
     * @param gameId id de la partida
     * @param clienteOneID id del primer cliente
     * @param clientDosID id del segundo cliente
     * @throws RemoteException
     */
    public void game(Integer gameId, Integer clienteOneID, Integer clientDosID) throws RemoteException;

    /**
     * Añade las coordenadas de los barcos a la partida
     * @param gameID id de la partida
     * @param clienteID id del cliente
     * @param coordenadaUno coordenada del primer barco
     * @param coordenadaDos coordenada del segundo barco
     * @throws RemoteException
     */
    public void setCoordinates(Integer gameID, Integer clienteID, Coordinate coordenadaUno, Coordinate coordenadaDos) throws RemoteException;

    /**
     * Devuelve una partida en curso
     * @param gameId id de la partida
     * @return partida
     * @throws RemoteException
     */
    public Partida getPartidaEnCurso(Integer gameId) throws RemoteException;
}
