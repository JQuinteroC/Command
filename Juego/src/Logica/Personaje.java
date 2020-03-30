package Logica;

import GUI.Grupos;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JPanel;

/**
 *
 * @author Mateo
 */
public class Personaje extends JComponent implements Cloneable, Composite {

    protected ImageIcon[] idle;
    protected ImageIcon[] herir;
    protected ImageIcon[] morir;
    protected ImageIcon[] atacar;
    public int desplazamiento;
    public int tempDesplazamiento;
    int x = 0; // Variable de identificación de animación
    int numero = 0;
    int ancho = 0;
    int alto = 0;
    int desplazamientoVertical = 0;
    int desplazamientoHorizontal = 0;
    Rectangle hitbox;
    boolean isMago = false; // Variable solo para mantener el aspecto en las animaciónes del Mago
    boolean animar = true; // Controla la ejecución de la animación
    static JPanel panel = null;
    public Thread hilo;
    public int vidaRest = 60;
    public int seleccionable; //0 no seleccionable, 1 seleccionado, 2 no seleccion
    public boolean defendiendo;
    public boolean muerto; //Si está muerto no se puede seleccionar
    public boolean mascota; //si es mascota

    // CONSTRUCTOR
    public Personaje() {
        // Se limpian todas las varibales para el cambio de personaje
        hilo = null;
        idle = null;
        herir = null;
        morir = null;
        atacar = null;
        x = 0;
        numero = 0;
        ancho = 0;
        alto = 0;
        isMago = false;
        panel = null;
        seleccionable = 2;
        defendiendo = false;
        muerto = false;
    }

    // SET AND GET
    public boolean isIsMago() {
        return isMago;
    }

    public void setDesplazamientoVertical(int desplazamiento) {
        this.desplazamientoVertical = desplazamiento;
    }

    public void setDesplazamientoHorizontal(int desplazamiento) {
        this.desplazamientoHorizontal = desplazamiento;
    }

    public void setAncho(int ancho) {
        this.ancho = ancho;
    }

    public void setAlto(int alto) {
        this.alto = alto;
    }

    public void setHilo(int idle, int herir, int morir, int atacar, int sleep) {
        this.hilo = new Thread() {
            @Override
            public void run() {
                try {
                    while (animar) {
                        switch (x) {
                            case 0:
                                numero++;
                                numero = numero % idle;
                                panel.repaint();
                                hilo.sleep(sleep + 30);
                                break;
                            case 1:
                                numero++;
                                numero = numero % herir;
                                panel.repaint();
                                hilo.sleep(sleep);
                                if (numero + 1 == herir) {
                                    idle();
                                }
                                break;
                            case 2:
                                numero++;
                                numero = numero % morir;
                                panel.repaint();
                                hilo.sleep(sleep);
                                if (numero + 1 == morir) {
                                    animar = false;
                                    muerto = true;
                                    hilo.stop();
                                }
                                break;
                            case 3:
                                numero++;
                                numero = numero % atacar;
                                panel.repaint();
                                hilo.sleep(sleep);
                                if (numero + 1 == atacar) {
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

    public void setPanel(JPanel panel) {
        Personaje.panel = panel;
        setBounds(0, 0, panel.getWidth(), panel.getHeight());
    }

    public ImageIcon[] getIdle() {
        return idle;
    }

    public void setIdle(ImageIcon[] idle) {
        this.idle = idle;
    }

    public ImageIcon[] getHerir() {
        return herir;
    }

    public void setHerir(ImageIcon[] herir) {
        this.herir = herir;
    }

    public ImageIcon[] getMorir() {
        return morir;
    }

    public void setMorir(ImageIcon[] morir) {
        this.morir = morir;
    }

    public ImageIcon[] getAtacar() {
        return atacar;
    }

    public void setAtacar(ImageIcon[] atacar) {
        this.atacar = atacar;
    }

    // CLONE
    @Override
    public Personaje clone() {
        Personaje PersonajeClonado = null;
        try {
            PersonajeClonado = (Personaje) super.clone();
            if (PersonajeClonado.isIsMago()) {
                PersonajeClonado.setHilo(5, 5, 5, 5, 130);
            } else {
                PersonajeClonado.setHilo(17, 12, 15, 12, 50);
            }
        } catch (CloneNotSupportedException e) {
        }
        return PersonajeClonado;
    }

    // PINTAR EN PANEL
    @Override
    public void paint(Graphics g) {
        try {
            // Hace que se congele en la última imagen de la animación de muerte
            if (muerto) {
                if (isMago) {
                    numero = 4;
                } else {
                    numero = 14;
                }
            }
            switch (x) {
                case 0:
                    g.drawImage(idle[numero].getImage(), 50 + desplazamientoHorizontal, 0 + desplazamientoVertical, ancho, alto, null);
                    break;
                case 1:
                    if (isMago && this.ancho < 0) {
                        g.drawImage(herir[numero].getImage(), 65 + desplazamientoHorizontal, -3 + desplazamientoVertical, ancho - 50, alto + 18, null);
                    } else if (isMago) {
                        g.drawImage(herir[numero].getImage(), 35 + desplazamientoHorizontal, -3 + desplazamientoVertical, ancho + 50, alto + 18, null);
                    } else {
                        g.drawImage(herir[numero].getImage(), 50 + desplazamientoHorizontal, 0 + desplazamientoVertical, ancho, alto, null);
                    }
                    break;
                case 2:
                    if (isMago && this.ancho < 0) {
                        g.drawImage(morir[numero].getImage(), 45 + desplazamientoHorizontal, -4 + desplazamientoVertical, ancho + 3, alto + 14, null);
                    } else if (isMago) {
                        g.drawImage(morir[numero].getImage(), 53 + desplazamientoHorizontal, -4 + desplazamientoVertical, ancho + 3, alto + 14, null);
                    } else {
                        g.drawImage(morir[numero].getImage(), 50 + desplazamientoHorizontal, 0 + desplazamientoVertical, ancho, alto, null);
                    }
                    break;
                case 3:
                    if (isMago && this.ancho < 0) {
                        g.drawImage(atacar[numero].getImage(), 55 + desplazamientoHorizontal, -25 + desplazamientoVertical, ancho - 155, alto + 60, null);
                    } else if (isMago) {
                        g.drawImage(atacar[numero].getImage(), 45 + desplazamientoHorizontal, -30 + desplazamientoVertical, ancho + 165, alto + 60, null);
                    } else {
                        g.drawImage(atacar[numero].getImage(), 50 + desplazamientoHorizontal, 0 + desplazamientoVertical, ancho, alto, null);
                    }
                    break;
                default:
                    break;
            }
        } catch (Exception ex) {

        }
    }

    // ANIMACIONES
    public void idle() {
        x = 0;
        if (!((tempDesplazamiento > 36) & (tempDesplazamiento < 41))) {
            numero = 0;
        }
        if (!hilo.isAlive()) {
            hilo.start();
        }
    }

    public void herir(int d) {
        vidaRest = vidaRest - d;
        System.out.println("A " + getName() + " le queda " + getVidaRestante() + " de vida restante.");
        x = 1;
        numero = 0;
        if (!hilo.isAlive()) {
            hilo.start();
        }
    }

    public void morir() {
        x = 2;
        numero = 0;
    }

    public void atacar() {
        x = 3;
        numero = 0;
        if (!hilo.isAlive()) {
            hilo.start();
        }
    }

    public void interrumpir() {
        try {
            hilo.interrupt();
        } catch (Exception ex) {
            System.out.println("hilo " + hilo.getName() + " no interrumpido");
        }
    }

    public int getAncho() {
        return ancho;
    }

    public int getAlto() {
        return alto;
    }

    public int getDesplazamientoHorizontal() {
        return desplazamientoHorizontal;
    }

    public int getDesplazamientoVertical() {
        return desplazamientoVertical;
    }

    public Rectangle getHitbox() {
        return hitbox;
    }

    public void setHitbox(int x, int y, int ancho, int alto) {
        if (isMago && this.ancho < 0) {
            hitbox = new Rectangle(x + (ancho / 2) - 235, y + (alto / 4) - 35, (ancho / 2) + 65, (alto / 2) + 75);
        } else if (isMago) {
            hitbox = new Rectangle(x + (ancho / 2) - 45, y + (alto / 4) - 35, (ancho / 2) + 65, (alto / 2) + 75);
        } else if (this.ancho < 0) {
            hitbox = new Rectangle(x + (ancho / 2) - 300, y + (alto / 4), (ancho / 2) - 40, (alto / 2) + 25);
        } else {
            hitbox = new Rectangle(x + (ancho / 2) - 10, y + (alto / 4), (ancho / 2) - 40, (alto / 2) + 25);
        }
    }

    public int getSeleccionable() {
        return seleccionable;
    }

    public void setSeleccionable(int b) {
        this.seleccionable = b;
    }

    public int getVidaRestante() {
        return vidaRest;
    }

    public void setVidaRestante(int i) {
        this.vidaRest = i;
    }

    public boolean getMuerto() {
        return muerto;
    }

    public void setMuerto(boolean i) {
        this.muerto = i;
    }

    public boolean getDefendiendo() {
        return defendiendo;
    }

    public void setDefendiendo(boolean b) {
        this.defendiendo = b;
    }

    void defender() {
        vidaRest = vidaRest + 7;
    }

    @Override
    public void operar(int i, Grupos g) {
        this.vidaRest += 20;
        try {
            Personaje mas = new Mascota(this, this.panel);
            if (g.grupo1.power) {
                mas.setName(g.grupo1.get(i).getName());
                mas.setVidaRestante(this.getVidaRestante());
                g.grupo1.set(i, mas);                
            } else {
                mas.setName(g.grupo2.get(i).getName());
                mas.setVidaRestante(this.getVidaRestante());
                g.grupo2.set(i, mas);
            }
        } catch (IOException ex) {
            Logger.getLogger(Personaje.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
