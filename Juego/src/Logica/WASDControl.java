package Logica;

import java.awt.event.KeyEvent;

/**
 *
 * @author <a href="https://github.com/JQuinteroC">JQuinteroC</a>
 */
public class WASDControl extends EstrategiaControl {
    
    public WASDControl(KeyEvent evento, Personaje personaje) {
        super(evento, personaje);
    }

    @Override
    public void identificarEvento() {
        if (evento != null) {
            switch (evento.getKeyChar()) {
                case 'w':
                    accion = 2;
                    break;
                case 'a':
                    accion = 3;
                    break;
                case 's':
                    accion = 4;
                    break;
                case 'd':
                    accion = 1;
                    break;
                case 'e':
                    accion = 5;
                    break;
                case 'r':
                    accion = 6;
                    break;
                case 'f':
                    accion = 7;
                    break;
                default:
                    break;
            }
        }
    }
}
