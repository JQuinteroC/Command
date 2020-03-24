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
public class ConstructorDuende extends ConstruirPersonaje {

    @Override
    public void ConstruirPersonaje() {
        personaje = new Duende();
    }

    @Override
    public void CrearAnimaciones() {
        for (int i = 0; i < 17; i++) {
            try {
                personaje.idle[i] = new ImageIcon(ImageIO.read(new File("Recursos\\Goblin\\Idle\\0_Goblin_Idle_" + i + ".png")));
            } catch (IOException ex) {
                Logger.getLogger(Duende.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        for (int i = 0; i < 12; i++) {
            try {
                personaje.herir[i] = new ImageIcon(ImageIO.read(new File("Recursos\\Goblin\\Hurt\\0_Goblin_Hurt_" + i + ".png")));
            } catch (IOException ex) {
                Logger.getLogger(Duende.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        for (int i = 0; i < 15; i++) {
            try {
                personaje.morir[i] = new ImageIcon(ImageIO.read(new File("Recursos\\Goblin\\Die\\0_Goblin_Dying_" + i + ".png")));
            } catch (IOException ex) {
                Logger.getLogger(Duende.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        for (int i = 0; i < 12; i++) {
            try {
                personaje.atacar[i] = new ImageIcon(ImageIO.read(new File("Recursos\\Goblin\\Attack\\0_Goblin_Attack_" + i + ".png")));
            } catch (IOException ex) {
                Logger.getLogger(Duende.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
