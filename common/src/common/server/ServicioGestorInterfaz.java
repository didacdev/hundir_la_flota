package common.server;

import common.database.Jugador;
import common.database.Partida;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;

public interface ServicioGestorInterfaz extends Remote {

    public static final String NOMBRE_SERVICIO = "ServicioGestor";
    public static final String host = "localhost";

    // Añadir métodos remotos

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
}
