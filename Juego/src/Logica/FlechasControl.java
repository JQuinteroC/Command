package Logica;

import java.awt.event.KeyEvent;

/**
 *
 * @author <a href="https://github.com/JQuinteroC">JQuinteroC</a>
 */
public class FlechasControl extends EstrategiaControl {

    public FlechasControl(KeyEvent evento, Personaje personaje) {
        super(evento, personaje);
    }

    @Override
    public void identificarEvento() {
        if (evento != null) {
            switch (evento.getKeyCode()) {
                case 39:
                    accion = 1;
                    break;
                case 38:
                    accion = 2;
                    break;
                case 37:
                    accion = 3;
                    break;
                case 40:
                    accion = 4;
                    break;
                case 97:
                    accion = 5;
                    break;
                case 98:
                    accion = 7;
                    break;
                case 99:
                    accion = 6;
                    break;
                case 32:
                    accion = 9;
                    break;
                default:
                    break;
            }
        }
    }

}
