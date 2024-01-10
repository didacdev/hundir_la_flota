package common.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServicioAutenticacionInterfaz extends Remote {
    public static final String NOMBRE_SERVICIO = "ServicioAutenticacion";

    // Añadir métodos remotos

    /**
     * Registra un usuario en el servidor
     * @param nombre nombre del usuario
     * @param password contraseña del usuario
     * @param clientID id del cliente
     * @return
     * @throws RemoteException
     */
    public boolean registrarUsuario(String nombre, String password, Integer clientID) throws RemoteException;

    /**
     * Inicia sesión en el servidor
     * @param nombre nombre del usuario
     * @param password contraseña del usuario
     * @return true si se ha iniciado sesión correctamente, false si no
     * @throws RemoteException
     */
    public boolean iniciarSesion(String nombre, String password) throws RemoteException;

    /**
     * Cierra sesión en el servidor
     * @param nombre nombre del usuario
     * @return true si se ha cerrado sesión correctamente, false si no
     * @throws RemoteException
     */
    public boolean cerrarSesion(String nombre) throws RemoteException;
}


