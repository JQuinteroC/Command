package Logica;

import java.awt.event.KeyEvent;

/**
 *
 * @author <a href="https://github.com/JQuinteroC">JQuinteroC</a>
 */
public abstract class EstrategiaControl {

    KeyEvent evento;
    Personaje personaje;
    int accion;
    Invocador invoker;

    public EstrategiaControl(KeyEvent evento, Personaje personaje) {
        this.evento = evento;
        this.personaje = personaje;
    }

    public void operar() {
        identificarEvento();
        if (accion == 1) {
            invoker = Invocador.obtenerInvocador();
            invoker.comando.add(new Atacar(personaje));
        }
        if (accion == 2) {
            invoker = Invocador.obtenerInvocador();
            invoker.comando.add(new Saltar(personaje));
        }
        if (accion == 3) {
            personaje.morir();
        }
        if (accion == 8) {
            personaje.interrumpir();
        }
        if (accion == 9) {
            invoker = Invocador.obtenerInvocador();
            invoker.ejecutarComandos();
        }
    }

    public void identificarEvento() {
    }
}
