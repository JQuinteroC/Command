package Logica;

import java.awt.Rectangle;
import javax.swing.ImageIcon;

/**
 *
 * @author <a href="https://github.com/JQuinteroC">JQuinteroC</a>
 */
public class Mago extends Personaje {

    public Mago() {
        super.setHilo(5, 5, 5, 5, 130);
        super.ancho = 419/2;
        super.alto = 381/2;
        super.isMago = true;
        super.caminar = new ImageIcon[5];
        super.saltar = new ImageIcon[5];
        super.morir = new ImageIcon[5];
        super.atacar = new ImageIcon[5];
        super.hitbox = new Rectangle(desplazamientoHorizontal+(ancho/2)-10, desplazamientoVertical+(alto/4), (ancho/2)-40, (alto/2)+25);  
    }
}
