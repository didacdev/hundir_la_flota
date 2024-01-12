package server;

import common.client.CallbackJugadorIterfaz;
import common.database.Coordinate;
import common.database.Jugador;
import common.database.Partida;
import common.server.ServicioGestorInterfaz;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ServicioGestorImpl implements ServicioGestorInterfaz {

    private HashMap<Integer, Partida> partidasEnCurso;
    private HashMap<Integer, BlockingQueue<ArrayList<Coordinate>>> coordenadasEnviadas;

    public ServicioGestorImpl() {
        this.partidasEnCurso = new HashMap<>();
        this.coordenadasEnviadas = new HashMap<>();
    }

    @Override
    public void addPartidaEnCurso(Integer gameId, Partida partida) throws RemoteException {
        partidasEnCurso.put(gameId, partida);
    }

    @Override
    public Partida getPartidaEnCurso(Integer gameId) throws RemoteException {
        return partidasEnCurso.get(gameId);
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

            addPartidaEnCurso(gameId, partida);

            // Ejecutar el método game en un nuevo hilo
            new Thread(() -> {
                try {
                    game(gameId, partida.getPlayerOne().getClienteID(), partida.getPlayerTwo().getClienteID());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }).start();

            return true;

        }catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }

    @Override
    public void setCoordinates(Integer gameID, Integer clienteID, Coordinate coordenadaUno, Coordinate coordenadaDos) throws RemoteException {
        BlockingQueue<ArrayList<Coordinate>> queue = coordenadasEnviadas.get(gameID);
        if (queue == null) {
            queue = new LinkedBlockingQueue<>();
            coordenadasEnviadas.put(gameID, queue);
        }
        ArrayList<Coordinate> coordinates = new ArrayList<>();
        coordinates.add(coordenadaUno);
        coordinates.add(coordenadaDos);
        queue.add(coordinates);

        // Añadir los barcos al tablero del jugador
        Partida partida = partidasEnCurso.get(gameID);
        Jugador jugador;
        if (partida.getPlayerOne().getClienteID().equals(clienteID)) {
            jugador = partida.getPlayerOne();
        } else {
            jugador = partida.getPlayerTwo();
        }
        jugador.addShipsToBoard(coordenadaUno, coordenadaDos);
    }

    @Override
    public void setCoordinates(Integer gameID, Integer clienteID, Coordinate shot) throws RemoteException {
        BlockingQueue<ArrayList<Coordinate>> queue = coordenadasEnviadas.get(gameID);
        if (queue == null) {
            queue = new LinkedBlockingQueue<>();
            coordenadasEnviadas.put(gameID, queue);
        }
        ArrayList<Coordinate> coordinates = new ArrayList<>();
        coordinates.add(shot);
        queue.add(coordinates);

        // Añadir disparo al jugador
        Partida partida = partidasEnCurso.get(gameID);
        Jugador jugador;
        if (partida.getPlayerOne().getClienteID().equals(clienteID)) {
            jugador = partida.getPlayerTwo();
        } else {
            jugador = partida.getPlayerOne();
        }
        jugador.addReceivedShot(shot);
    }

    @Override
    public void game(Integer gameId, Integer clienteOneID, Integer clientDosID) throws RemoteException {

        CallbackJugadorIterfaz callbackJugador1 = Servidor.getCallbackJugador(clienteOneID.toString());
        CallbackJugadorIterfaz callbackJugador2 = Servidor.getCallbackJugador(clientDosID.toString());

        Partida partida = partidasEnCurso.get(gameId);

        // Obtenemos las coordenadas de los barcos del jugador 1
        coordenadasEnviadas.put(gameId, new LinkedBlockingQueue<>());
        callbackJugador1.notificar(gameId + "-coordenadas");

        ArrayList<Coordinate> coordinates1 = new ArrayList<>();
        try {
            coordinates1 = coordenadasEnviadas.get(gameId).take(); // Espera hasta que el cliente 1 envíe las coordenadas
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        partida.getPlayerOne().setShipOne(coordinates1.get(0));
        partida.getPlayerOne().setShipTwo(coordinates1.get(1));
        coordenadasEnviadas.get(gameId).clear();

        // Obtenemos las coordenadas de los barcos del jugador 2
        callbackJugador2.notificar(gameId + "-coordenadas");

        ArrayList<Coordinate> coordinates2 = new ArrayList<>();
        try {
            coordinates2 = coordenadasEnviadas.get(gameId).take(); // Espera hasta que el cliente 2 envíe las coordenadas
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        partida.getPlayerTwo().setShipOne(coordinates2.get(0));
        partida.getPlayerTwo().setShipTwo(coordinates2.get(1));
        coordenadasEnviadas.get(gameId).clear();

        // Iniciar el juego
        while (!partida.isGameOver()) {
            ArrayList<Coordinate> shots1 = new ArrayList<>();
            ArrayList<Coordinate> shots2 = new ArrayList<>();

            // Pedir al jugador uno las coordenadas de un disparo
            callbackJugador1.notificar(gameId + "-disparo");
            try {

                shots1 = coordenadasEnviadas.get(gameId).take();
            } catch (InterruptedException e) {
                throw new RemoteException("Error al obtener las coordenadas del disparo", e);
            }
            Coordinate shot1 = new Coordinate(shots1.get(0).getRowBow().toUpperCase(), shots1.get(0).getColumnBow(), "-");

            // Comprobar el resultado del disparo
            String result1 = checkShot(shot1, partida.getPlayerTwo(), partida.getPlayerOne());

            // Informar del resultado del disparo
            callbackJugador1.notificar(gameId + "-" + result1);
            callbackJugador2.notificar(gameId + "-" + result1);

            // Comprobar si el juego ha terminado
            if (partida.isGameOver()) {
                break;
            }

            // Repetir los pasos para el jugador dos
            callbackJugador2.notificar(gameId + "-disparo");
            try {
                shots2 = coordenadasEnviadas.get(gameId).take();
            } catch (InterruptedException e) {
                throw new RemoteException("Error al obtener las coordenadas del disparo", e);
            }
            Coordinate shot2 = new Coordinate(shots2.get(0).getRowBow().toUpperCase(), shots2.get(0).getColumnBow(), "-");

            String result2 = checkShot(shot2, partida.getPlayerOne(), partida.getPlayerTwo());

            callbackJugador2.notificar(gameId + "-" + result2);
            callbackJugador1.notificar(gameId + "-" + result2);

            // Comprobar si el juego ha terminado
            if (partida.isGameOver()) {
                break;
            }
        }

        System.out.println();
        System.out.println("Partida " + gameId + " terminada");
        System.out.println("Puntos de " + partida.getPlayerOne().getUsername() + ": " + partida.getPlayerOne().getTotalPoints());
        System.out.println("Puntos de " + partida.getPlayerTwo().getUsername() + ": " + partida.getPlayerTwo().getTotalPoints());

        // Informar de que el juego ha terminado
        if (partida.getPlayerOne().getTotalPoints() > partida.getPlayerTwo().getTotalPoints()) {
            callbackJugador1.notificar("ganador");
            callbackJugador2.notificar("perdedor");
        } else if (partida.getPlayerOne().getTotalPoints() < partida.getPlayerTwo().getTotalPoints()) {
            callbackJugador1.notificar("perdedor");
            callbackJugador2.notificar("ganador");
        }

        System.out.println("Partida terminada");

        // Guardar la partida en la base de datos
        Servidor.getServicioDatos().saveGame(gameId, partida);
        // Borrar la partida de las partidas en curso
        partidasEnCurso.remove(gameId);
        Servidor.getServicioDatos().removeStartedGame(gameId);
    }

    private String checkShot(Coordinate shot, Jugador opponent, Jugador player) {
        List<Coordinate> occupiedCoordinatesShipOne = opponent.getShipOne().getOccupiedCoordinates();
        List<Coordinate> occupiedCoordinatesShipTwo = opponent.getShipTwo().getOccupiedCoordinates();

        List<Coordinate> receivedShots = opponent.getReceivedShots();

        for (Coordinate coordinate : occupiedCoordinatesShipOne) {
            if (coordinate.equals(shot)) {
                player.addGamePoint();
                if (receivedShots.containsAll(occupiedCoordinatesShipOne)) {
                    return "hundido";
                }
                return "tocado";
            }
        }

        for (Coordinate coordinate : occupiedCoordinatesShipTwo) {
            if (coordinate.equals(shot)) {
                player.addGamePoint();
                if (receivedShots.containsAll(occupiedCoordinatesShipTwo)) {
                    return "hundido";
                }
                return "tocado";
            }
        }

        return "agua";
    }
}
