package Logica;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 *
 * @author <a href="https://github.com/JQuinteroC">JQuinteroC</a>
 */
public class ConstructorMago extends ConstruirPersonaje {
    
    @Override
    public void ConstruirPersonaje() {
        personaje = new Mago();
    }
    
    @Override
    public void CrearAnimaciones() {
        for (int i = 0; i < 5; i++) {
            try {
                personaje.idle[i] = new ImageIcon(ImageIO.read(new File("Recursos\\Wizard\\Idle\\1_IDLE_" + i + ".png")));
            } catch (IOException ex) {
                Logger.getLogger(Mago.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        for (int i = 0; i < 5; i++) {
            try {
                personaje.herir[i] = new ImageIcon(ImageIO.read(new File("Recursos\\Wizard\\Hurt\\6_HURT_" + i + ".png")));
            } catch (IOException ex) {
                Logger.getLogger(Mago.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        for (int i = 0; i < 5; i++) {
            try {
                personaje.morir[i] = new ImageIcon(ImageIO.read(new File("Recursos\\Wizard\\Die\\7_DIE_" + i + ".png")));
            } catch (IOException ex) {
                Logger.getLogger(Mago.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        for (int i = 0; i < 5; i++) {
            try {
                personaje.atacar[i] = new ImageIcon(ImageIO.read(new File("Recursos\\Wizard\\Attack\\5_ATTACK_" + i + ".png")));
            } catch (IOException ex) {
                Logger.getLogger(Mago.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
