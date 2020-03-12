package Logica;

import java.awt.Rectangle;
import javax.swing.ImageIcon;

/**
 *
 * @author Mateo
 */
public class Duende extends Personaje {

    public Duende() {
        super.setHilo(24, 18, 15, 12, 50);
        super.ancho = 587/2;
        super.alto = 556/2;
        super.isMago = false;
        super.caminar = new ImageIcon[24];
        super.saltar = new ImageIcon[18];
        super.morir = new ImageIcon[15];
        super.atacar = new ImageIcon[12];
        super.hitbox = new Rectangle(desplazamientoHorizontal+(ancho/2)-10, desplazamientoVertical+(alto/4), (ancho/2)-40, (alto/2)+25);   
    }
}
