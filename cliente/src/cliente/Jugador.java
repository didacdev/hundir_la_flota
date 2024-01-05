package cliente;

import common.rmi.IniciarRMI;

public class Jugador extends IniciarRMI{

    public Jugador() {
        super(Jugador.class, 2001);
    }

    @Override
    public void operacionRMI() {

        try {
            System.out.println("Hola jugador");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new Jugador();
    }
}
