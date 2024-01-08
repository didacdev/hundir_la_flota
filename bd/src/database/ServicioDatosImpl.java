package database;

import common.database.Data;
import common.database.Jugador;
import common.database.Partida;
import common.database.ServicioDatosInterfaz;

import java.rmi.RemoteException;
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
    }

    public ServicioDatosImpl(List<Jugador> registredUsers, List<String> onlineUsers, List<Integer> waitingGames, List<Integer> startedGames, HashMap<Integer, Partida> createdGames) {
        this.registredUsers = registredUsers;
        this.onlineUsers = onlineUsers;
        this.waitingGames = waitingGames;
        this.startedGames = startedGames;
        this.createdGames = createdGames;
    }

    //--------------------------------- GETTERS & SETTERS ---------------------------------
    public List<Jugador> getRegistredUsers() {
        return registredUsers;
    }

    public void setRegistredUsers(List<Jugador> registredUsers) {
        this.registredUsers = registredUsers;
    }

    public List<String> getOnlineUsers() {
        return onlineUsers;
    }

    public void setOnlineUsers(List<String> onlineUsers) {
        this.onlineUsers = onlineUsers;
    }

    public List<Integer> getWaitingGames() {
        return waitingGames;
    }

    public void setWaitingGames(List<Integer> waitingGames) {
        this.waitingGames = waitingGames;
    }

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

    public void setStartedGames(List<Integer> startedGames) {
        this.startedGames = startedGames;
    }

    public HashMap<Integer, Partida> getCreatedGames() {
        return createdGames;
    }

    public void setCreatedGames(HashMap<Integer, Partida> createdGames) {
        this.createdGames = createdGames;
    }


    //--------------------------------- METODOS ---------------------------------
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
}
