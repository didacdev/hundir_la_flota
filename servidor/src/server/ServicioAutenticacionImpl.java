package server;

import common.database.Jugador;
import common.database.ServicioDatosInterfaz;
import common.server.ServicioAutenticacionInterfaz;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.List;

public class ServicioAutenticacionImpl implements ServicioAutenticacionInterfaz {

    public ServicioAutenticacionImpl() {

    }

    @Override
    public boolean registrarUsuario(String nombre, String password, Integer clientID) throws RemoteException {

        List<Jugador> registeredUsers = Servidor.getServicioDatos().getUsersList();

        if (registeredUsers.stream().anyMatch(jugador -> jugador.getUsername().equals(nombre))) {
            return false;
        }

        return Servidor.getServicioDatos().addUser(nombre, password, clientID);
    }

    @Override
    public boolean iniciarSesion(String nombre, String password) throws RemoteException {

        List<String> onlineUsers = Servidor.getServicioDatos().getOnlineUsers();

        if (onlineUsers.stream().anyMatch(jugador -> jugador.equals(nombre))) {
            return false;
        }

        Servidor.getServicioDatos().addOnlineUser(nombre);
        return Servidor.getServicioDatos().getUsersList().stream().anyMatch(jugador -> jugador.getUsername().equals(nombre) && jugador.getPassword().equals(password));
    }

    @Override
    public boolean cerrarSesion(String nombre) throws RemoteException {

        List<String> onlineUsers = Servidor.getServicioDatos().getOnlineUsers();

        if (onlineUsers.stream().noneMatch(jugador -> jugador.equals(nombre))) {
            return false;
        }

        Servidor.getServicioDatos().removeOnlineUser(nombre);
        return true;
    }
}
