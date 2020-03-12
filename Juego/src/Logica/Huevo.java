package Logica;

import java.awt.Rectangle;
import javax.swing.ImageIcon;

/**
 *
 * @author Cheetos
 */
public class Huevo extends Personaje{
        public Huevo() {
        super.setHilo(1, 1, 1, 1, 130);
        super.ancho = 437/4;
        super.alto = 504/4;
        super.isMago = false;
        super.caminar = new ImageIcon[1];
        super.saltar = new ImageIcon[1];
        super.morir = new ImageIcon[1];
        super.atacar = new ImageIcon[1];
        super.hitbox = new Rectangle(desplazamientoHorizontal+(ancho/2)-3, desplazamientoVertical+(alto/4)-30, ancho, (alto/2)+60);  
    }
}
