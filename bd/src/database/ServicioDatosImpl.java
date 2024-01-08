package database;

import common.database.Data;
import common.database.Jugador;
import common.database.Partida;
import common.database.ServicioDatosInterfaz;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ServicioDatosImpl implements ServicioDatosInterfaz {

    private List<Jugador> registredUsers;
    private List<String> onlineUsers;
    private List<Integer> waitingGames;
    private List<Integer> startedGames;
    private HashMap<Integer, Partida> createdGames;

    //--------------------------------- CONSTRUCTORES ---------------------------------
    public ServicioDatosImpl() {
        this.registredUsers = new ArrayList<>();
        this.onlineUsers = new ArrayList<>();
        this.waitingGames = new ArrayList<>();
        this.startedGames = new ArrayList<>();
        this.createdGames = new HashMap<>();
    }

    //--------------------------------- METODOS ---------------------------------

    public HashMap<Integer, Partida> getStartedGames() {

        HashMap<Integer, Partida> startedGamesMap = new HashMap<>();
        for (Integer gameId : startedGames) {
            Partida partida = createdGames.get(gameId);
            if (partida != null) {
                startedGamesMap.put(gameId, partida);
            }
        }
        return startedGamesMap;

    }

    @Override
    public Data getData() throws RemoteException {
        int registredUsers = this.registredUsers.size();
        int onlineUsers = this.onlineUsers.size();
        int waitingGames = this.waitingGames.size();
        int startedGames = this.startedGames.size();
        int createdGames = this.createdGames.size();

        return new Data(registredUsers, onlineUsers, startedGames, waitingGames, createdGames);
    }

    @Override
    public List<Jugador> getUsersList() throws RemoteException {

        return this.registredUsers;

    }

    @Override
    public boolean addUser(String nombre, String password) throws RemoteException {
        Jugador jugador = new Jugador(nombre, password);
        if (registredUsers.contains(jugador)) {
            return false;
        } else {
            registredUsers.add(jugador);
            return true;
        }
    }

    @Override
    public HashMap<Integer, Partida> getCreatedGames() throws RemoteException {
        return this.createdGames;
    }

    @Override
    public List<Integer> getWaitingGames() throws RemoteException {
        return this.waitingGames;
    }
}
