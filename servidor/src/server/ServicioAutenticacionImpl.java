package server;

import common.database.ServicioDatosInterfaz;
import common.server.ServicioAutenticacionInterfaz;

import java.rmi.Naming;
import java.rmi.RemoteException;

public class ServicioAutenticacionImpl implements ServicioAutenticacionInterfaz {

    public ServicioAutenticacionImpl() {

    }

    @Override
    public boolean registrarUsuario(String nombre, String password) throws RemoteException {

        return Servidor.getServicioDatos().addUser(nombre, password);
    }

    @Override
    public boolean iniciarSesion(String nombre, String password) throws RemoteException {

            return Servidor.getServicioDatos().getUsersList().stream().anyMatch(jugador -> jugador.getUsername().equals(nombre) && jugador.getPassword().equals(password));
    }
}
