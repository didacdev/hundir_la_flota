package common.database;

import java.io.Serializable;

public class Data implements Serializable {

    private int registredUsers;
    private int onlineUsers;
    private int startedGames;
    private int waitingGames;
    private int createdGames;

    public Data(int registredUsers, int onlineUsers, int startedGames, int waitingGames, int createdGames) {
        this.registredUsers = registredUsers;
        this.onlineUsers = onlineUsers;
        this.startedGames = startedGames;
        this.waitingGames = waitingGames;
        this.createdGames = createdGames;
    }

    public int getRegistredUsers() {
        return registredUsers;
    }

    public void setRegistredUsers(int registredUsers) {
        this.registredUsers = registredUsers;
    }

    public int getOnlineUsers() {
        return onlineUsers;
    }

    public void setOnlineUsers(int onlineUsers) {
        this.onlineUsers = onlineUsers;
    }

    public int getStartedGames() {
        return startedGames;
    }

    public void setStartedGames(int startedGames) {
        this.startedGames = startedGames;
    }

    public int getWaitingGames() {
        return waitingGames;
    }

    public void setWaitingGames(int waitingGames) {
        this.waitingGames = waitingGames;
    }

    public int getCreatedGames() {
        return createdGames;
    }

    public void setCreatedGames(int createdGames) {
        this.createdGames = createdGames;
    }
}
