package database;

import common.database.Data;
import common.database.Jugador;
import common.database.Partida;
import common.database.ServicioDatosInterfaz;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

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
    public boolean addUser(String nombre, String password, Integer clientID) throws RemoteException {
        Jugador jugador = new Jugador(nombre, password);
        jugador.setClienteID(clientID);
        if (registredUsers.contains(jugador)) {
            return false;
        } else {
            registredUsers.add(jugador);
            return true;
        }
    }

    @Override
    public List<String> getOnlineUsers() throws RemoteException {
        return this.onlineUsers;
    }

    @Override
    public void addOnlineUser(String username) throws RemoteException {
        this.onlineUsers.add(username);
    }

    @Override
    public void removeOnlineUser(String username) throws RemoteException {
        this.onlineUsers.remove(username);
    }

    @Override
    public HashMap<Integer, Partida> getCreatedGames() throws RemoteException {
        return this.createdGames;
    }

    @Override
    public List<Integer> getWaitingGames() throws RemoteException {
        return this.waitingGames;
    }

    @Override
    public Integer addCreatedGame(Partida partida) throws RemoteException {
        Random random = new Random();
        Integer gameId;

        do {
            gameId = random.nextInt(1000);
        } while (createdGames.containsKey(gameId));

        createdGames.put(gameId, partida);

        return gameId;
    }

    @Override
    public void addWaitingGame(Integer gameId) throws RemoteException {
        waitingGames.add(gameId);
    }

    @Override
    public void removeWaitingGame(Integer gameId) throws RemoteException {
        waitingGames.remove(gameId);
    }

    @Override
    public void addStartedGame(Integer gameId) throws RemoteException {
        startedGames.add(gameId);
    }

    @Override
    public void updateStartedGame(Integer gameId, Partida partida) throws RemoteException {
        createdGames.put(gameId, partida);
    }

    @Override
    public void saveGame(Integer gameID, Partida partida) throws RemoteException {

        for (int i = 0; i < registredUsers.size(); i++) {
            Jugador jugador = registredUsers.get(i);
            if (jugador.getClienteID().equals(partida.getPlayerOne().getClienteID())) {
                registredUsers.set(i, partida.getPlayerOne());
            } else if (jugador.getClienteID().equals(partida.getPlayerTwo().getClienteID())) {
                registredUsers.set(i, partida.getPlayerTwo());
            }
        }

        createdGames.put(gameID, partida);
    }

    @Override
    public void removeStartedGame(Integer gameId) throws RemoteException {
        startedGames.remove(gameId);
    }
}
