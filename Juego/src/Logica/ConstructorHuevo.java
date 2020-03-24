package Logica;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 *
 * @author Cheetos
 */
public class ConstructorHuevo extends ConstruirPersonaje {

    @Override
    public void ConstruirPersonaje() {
        personaje = new Huevo();
    }

    @Override
    public void CrearAnimaciones() {
        try {
            personaje.idle[0] = new ImageIcon(ImageIO.read(new File("Recursos\\PowerUp\\egg.png")));
        } catch (IOException ex) {
            Logger.getLogger(Huevo.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            personaje.herir[0] = new ImageIcon(ImageIO.read(new File("Recursos\\PowerUp\\egg.png")));
        } catch (IOException ex) {
            Logger.getLogger(Huevo.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            personaje.morir[0] = new ImageIcon(ImageIO.read(new File("Recursos\\PowerUp\\egg.png")));
        } catch (IOException ex) {
            Logger.getLogger(Huevo.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            personaje.atacar[0] = new ImageIcon(ImageIO.read(new File("Recursos\\PowerUp\\egg.png")));
        } catch (IOException ex) {
            Logger.getLogger(Huevo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
