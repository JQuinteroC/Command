package Logica;

import static Logica.Personaje.panel;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
 * @author <a href="https://github.com/JQuinteroC">JQuinteroC</a>
 */
public class Mascota extends Decorador {

    public Mascota(Personaje per, JPanel panel) throws IOException {
        super(per, panel);
        per.interrumpir();
        if (per.isIsMago()) { //MAGO
            setHilo(5, 5, 5, 5, 130);
        } else {  // OGRO
            setHilo(17, 12, 15, 12, 50);
        }
        setPanel(panel);
    }

    @Override
    public void paint(Graphics g) {
        try {
            if (personaje.muerto) {
                if (personaje.isMago) {
                    if (personaje.numero + 1 == 4) {
                        personaje.numero = 4;
                    }
                } else {
                    if (personaje.numero + 1 == 14) {
                        personaje.numero = 14;
                    }
                }
            }
            ImageIcon mascota = new ImageIcon(ImageIO.read(new File("Recursos\\PowerUp\\1.png")));
            dibujarMascota(personaje, mascota, g);
            personaje.paint(g);
        } catch (IOException ex) {

        }
    }

    @Override
    public void idle() {
        personaje.x = 0;
        if (!((personaje.tempDesplazamiento > 36) & (personaje.tempDesplazamiento < 41))) {
            personaje.numero = 0;
        }
        if (!personaje.hilo.isAlive()) {
            personaje.hilo.start();
        }
    }

    public void herir(int d) {
        personaje.vidaRest = personaje.vidaRest - d;
        this.setVidaRestante(personaje.getVidaRestante());
        System.out.println("A " + getName() + " le queda " + getVidaRestante() + " de vida restante.");
        personaje.x = 1;
        personaje.numero = 0;
        if (!personaje.hilo.isAlive()) {
            personaje.hilo.start();
        }
    }

    @Override
    public void morir() {
        personaje.x = 2;
        personaje.numero = 0;
        this.numero = 0;
    }

    @Override
    public void atacar() {
        personaje.x = 3;
        personaje.numero = 0;
        if (!personaje.hilo.isAlive()) {
            personaje.hilo.start();
        }
    }

    @Override
    public void setHilo(int idle, int herir, int morir, int atacar, int sleep) {
        personaje.hilo = new Thread() {
            @Override
            public void run() {
                try {
                    while (personaje.animar) {
                        switch (personaje.x) {
                            case 0:
                                personaje.numero++;
                                personaje.numero = personaje.numero % idle;
                                panel.repaint();
                                personaje.hilo.sleep(sleep + 30);
                                break;
                            case 1:
                                personaje.numero++;
                                personaje.numero = personaje.numero % herir;
                                panel.repaint();
                                personaje.hilo.sleep(sleep);
                                if (personaje.numero + 1 == herir) {
                                    idle();
                                }
                                break;
                            case 2:
                                personaje.numero++;
                                personaje.numero = personaje.numero % morir;
                                panel.repaint();
                                personaje.hilo.sleep(sleep);
                                if (personaje.numero + 1 == morir) {
                                    personaje.animar = false;
                                    personaje.muerto = true;
                                    personaje.hilo.stop();
                                }
                                break;
                            case 3:
                                personaje.numero++;
                                personaje.numero = personaje.numero % atacar;
                                panel.repaint();
                                personaje.hilo.sleep(sleep);
                                if (personaje.numero + 1 == atacar) {
                                    idle();
                                }
                                break;
                            default:
                                break;
                        }
                    }
                } catch (java.lang.InterruptedException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        };
    }

    @Override
    public void interrumpir() {
        try {
            personaje.hilo.interrupt();
        } catch (Exception ex) {
            System.out.println("hilo " + personaje.hilo.getName() + " no interrumpido");
        }
    }

    @Override
    public int getAncho() {
        return personaje.ancho;
    }

    @Override
    public int getAlto() {
        return personaje.alto;
    }

    @Override
    public int getDesplazamientoHorizontal() {
        return personaje.desplazamientoHorizontal;
    }

    @Override
    public int getDesplazamientoVertical() {
        return personaje.desplazamientoVertical;
    }

    @Override
    public Rectangle getHitbox() {
        return personaje.hitbox;
    }

    public void dibujarMascota(Personaje per, ImageIcon img, Graphics g) {
        if (per.isMago && per.ancho < 0) {
            g.drawImage(img.getImage(), -5 + per.desplazamientoHorizontal, 50 + per.desplazamientoVertical, img.getIconWidth() / 3, img.getIconHeight() / 3, null);
        } else if (per.isMago) {
            g.drawImage(img.getImage(), -50 + per.desplazamientoHorizontal, 50 + per.desplazamientoVertical, img.getIconWidth() / 3, img.getIconHeight() / 3, null);
        } else if (per.ancho < 0) {
            g.drawImage(img.getImage(), -85 + per.desplazamientoHorizontal, 115 + per.desplazamientoVertical, img.getIconWidth() / 3, img.getIconHeight() / 3, null);
        } else {
            g.drawImage(img.getImage(), 35 + per.desplazamientoHorizontal, 115 + per.desplazamientoVertical, img.getIconWidth() / 3, img.getIconHeight() / 3, null);
        }

    }
}
