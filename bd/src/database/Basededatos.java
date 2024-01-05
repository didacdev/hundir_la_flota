package database;

import common.database.Jugador;
import common.database.Partida;
import common.database.ServicioDatosInterfaz;
import common.rmi.IniciarRMI;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


public class Basededatos extends IniciarRMI {

    public Basededatos() {

        super(Basededatos.class, 2002);
    }

    @Override
    public void operacionRMI() {
        try {
            Registry registry = LocateRegistry.createRegistry(registryPort);

            // Crear jugadores y partidas de prueba
            Jugador jugador1 = new Jugador("password1");
            Jugador jugador2 = new Jugador("password2");

            Partida partida1 = new Partida(jugador1);
            partida1.setPlayerTwo(jugador2);

            // Crear listas y mapas para almacenar los jugadores y partidas
//            HashMap<String, Jugador> registredUsers = new HashMap<>();
//            registredUsers.put("jugador1", jugador1);
//            registredUsers.put("jugador2", jugador2);
//
//            List<String> onlineUsers = new ArrayList<>();
//            onlineUsers.add("jugador1");
//            onlineUsers.add("jugador2");
//
//            List<Integer> waitingGames = new ArrayList<>();
//            waitingGames.add(1);
//
//            List<Integer> startedGames = new ArrayList<>();
//            startedGames.add(2);
//
//            HashMap<Integer, Partida> createdGames = new HashMap<>();
//            createdGames.put(1, partida1);

            // Crear una instancia de ServidorDatosImpl con los datos de prueba
//            ServidorDatosImpl serDat = new ServidorDatosImpl(registredUsers, onlineUsers, waitingGames, startedGames, createdGames);

            ServidorDatosImpl serDat = new ServidorDatosImpl();
            ServicioDatosInterfaz serDatStub = (ServicioDatosInterfaz) UnicastRemoteObject.exportObject(serDat, ServicioDatosInterfaz.port);

            registry.rebind(ServicioDatosInterfaz.NOMBRE_SERVICIO, serDatStub);

            System.out.println(Arrays.toString(registry.list()));

            System.out.println("Base de datos ok...");

            // Ejecución de la interfaz
            Interfaz interfaz = new Interfaz(serDat);
            interfaz.iniciar();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Basededatos();
    }
}
