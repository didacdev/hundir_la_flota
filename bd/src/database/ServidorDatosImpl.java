package database;

import common.database.Data;
import common.database.Jugador;
import common.database.Partida;
import common.database.ServicioDatosInterfaz;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;

public class ServidorDatosImpl implements ServicioDatosInterfaz {

    private HashMap<String, Jugador> registredUsers;
    private List<String> onlineUsers;
    private List<Integer> waitingGames;
    private List<Integer> startedGames;
    private HashMap<Integer, Partida> createdGames;

    //--------------------------------- CONSTRUCTORES ---------------------------------
    public ServidorDatosImpl() {
    }

    public ServidorDatosImpl(HashMap<String, Jugador> registredUsers, List<String> onlineUsers, List<Integer> waitingGames, List<Integer> startedGames, HashMap<Integer, Partida> createdGames) {
        this.registredUsers = registredUsers;
        this.onlineUsers = onlineUsers;
        this.waitingGames = waitingGames;
        this.startedGames = startedGames;
        this.createdGames = createdGames;
    }

    //--------------------------------- GETTERS & SETTERS ---------------------------------
    public HashMap<String, Jugador> getRegistredUsers() {
        return registredUsers;
    }

    public void setRegistredUsers(HashMap<String, Jugador> registredUsers) {
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

    public List<Integer> getStartedGames() {
        return startedGames;
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
    public HashMap<String, HashMap<Integer, Integer>> getUsersList() throws RemoteException {

        HashMap<String, HashMap<Integer, Integer>> usersList = new HashMap<>();

        for (String user : this.registredUsers.keySet()) {
            usersList.put(user, this.registredUsers.get(user).getGamePoints());
        }

        return usersList;

    }
}
