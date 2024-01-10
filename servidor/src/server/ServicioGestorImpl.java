package server;

import common.client.CallbackJugadorIterfaz;
import common.database.Jugador;
import common.database.Partida;
import common.server.ServicioGestorInterfaz;

import java.rmi.Naming;
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

    @Override
    public Boolean startGame(String username) throws RemoteException {

        try {
            Jugador jugador = Servidor.getServicioDatos().getUsersList().stream().filter(j -> j.getUsername().equals(username)).findFirst().get();
            Partida partida = new Partida(jugador);
            int gameId = Servidor.getServicioDatos().addCreatedGame(partida);
            Servidor.getServicioDatos().addWaitingGame(gameId);
            return true;

        }catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }

    @Override
    public void registerForCallback(CallbackJugadorIterfaz callbackClientObject, Integer clientID) throws RemoteException {
        Servidor.addCallbackJugador(clientID.toString(), callbackClientObject);
    }

    @Override
    public Boolean joinGame(String username, int gameId) throws RemoteException {

        try {
            Jugador jugador = Servidor.getServicioDatos().getUsersList().stream().filter(j -> j.getUsername().equals(username)).findFirst().get();
            Partida partida = Servidor.getServicioDatos().getCreatedGames().get(gameId);

            CallbackJugadorIterfaz callbackJugador1 = Servidor.getCallbackJugador(partida.getPlayerOne().getClienteID().toString());
            callbackJugador1.notificar("partida_iniciada");

            CallbackJugadorIterfaz callbackJugador2 = Servidor.getCallbackJugador(jugador.getClienteID().toString());
            callbackJugador2.notificar("partida_iniciada");

            partida.setPlayerTwo(jugador);
            Servidor.getServicioDatos().updateStartedGame(gameId, partida);
            Servidor.getServicioDatos().removeWaitingGame(gameId);
            Servidor.getServicioDatos().addStartedGame(gameId);
            return true;

        }catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }
}
