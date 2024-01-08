package server;

import common.database.Jugador;
import common.database.Partida;
import common.server.ServicioGestorInterfaz;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ServicioGestorImpl implements ServicioGestorInterfaz {

    public ServicioGestorImpl() {
    }

    @Override
    public Jugador getJugador(String nombre) throws RemoteException {

        return Servidor.getServicioDatos().getUsersList().stream().filter(jugador -> jugador.getUsername().equals(nombre)).findFirst().get();
    }

    @Override
    public List<String> getWaitingGamesWithCreators() throws RemoteException {
        List<Integer> waitingGames = Servidor.getServicioDatos().getWaitingGames();
        HashMap<Integer, Partida> createdGames = Servidor.getServicioDatos().getCreatedGames();
        List<String> waitingGamesList = new ArrayList<>();

        for (Integer gameId : waitingGames) {
            Partida partida = createdGames.get(gameId);
            if (partida != null) {
                waitingGamesList.add(gameId + " - " + partida.getPlayerOne().getUsername());
            }
        }

        return waitingGamesList;
    }
}
