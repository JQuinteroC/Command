package Logica;

import java.util.ArrayList;

/**
 *
 * @author Mateo
 */
public class Invocador {

    public ArrayList<Command> comando;
    static Invocador invocador;

    private Invocador() {
        comando = new ArrayList<>();
    }

    public void ejecutarComandos() {
        for (int i = 0; i < comando.size(); i++) {
            comando.get(i).accion();
        }
    }

    static public Invocador obtenerInvocador() {
        if (invocador == null) {
            invocador = new Invocador();
            return invocador;
        } else {
            return invocador;
        }
    }

}
