package Logica;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
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
    boolean animar = false; // Controla la ejecución de la animación
    static JPanel panel = null;
    public Thread hilo;
    protected EstrategiaControl control;   /// Borrar
    protected boolean tipoControl;         /// Borrar
    public int vidaRest = 60;
    public boolean seleccionable;

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
        tipoControl = true;
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
                    while (true) {
                    switch (x) {
                        case 0:
                            numero++;
                            switch (desplazamiento) { //Personaje Individual
                                case 39:
                                    desplazamientoHorizontal += 24;
                                    hitbox.x += 24;
                                    tempDesplazamiento = desplazamiento;
                                    desplazamiento = 0;
                                    break;
                                case 38:
                                    desplazamientoVertical -= 24;
                                    hitbox.y -= 24;
                                    tempDesplazamiento = desplazamiento;
                                    desplazamiento = 0;
                                    break;
                                case 37:
                                    desplazamientoHorizontal -= 24;
                                    hitbox.x -= 24;
                                    tempDesplazamiento = desplazamiento;
                                    desplazamiento = 0;
                                    break;
                                case 40:
                                    desplazamientoVertical += 24;
                                    hitbox.y += 24;
                                    tempDesplazamiento = desplazamiento;
                                    desplazamiento = 0;
                                    break;
                                default:
                                    break;
                            }
                            numero = numero % idle;
                            panel.repaint();
                            hilo.sleep(sleep+30);
                            break;
                        case 1:
                            numero++;
                            numero = numero % herir;
                            panel.repaint();
                            hilo.sleep(sleep);
                            break;
                        case 2:
                            numero++;
                            numero = numero % morir;
                            panel.repaint();
                            hilo.sleep(sleep);
                            if (numero + 1== morir) {
                                idle();
                            }
                            break;
                        case 3:
                            numero++;
                            numero = numero % atacar;
                            panel.repaint();
                            hilo.sleep(sleep);
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
            g.drawRect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);
            switch (x) {
                case 0:
                    g.drawImage(idle[numero].getImage(), 50 + desplazamientoHorizontal, 0 + desplazamientoVertical, ancho, alto, null);
                    break;
                case 1:
                    if (isMago && this.ancho < 0) {
                        g.drawImage(herir[numero].getImage(), 55 + desplazamientoHorizontal, -38 + desplazamientoVertical, ancho + 5, alto + 30, null);
                    } else if (isMago) {
                        g.drawImage(herir[numero].getImage(), 50 + desplazamientoHorizontal, -38 + desplazamientoVertical, ancho - 8, alto + 30, null);
                    } else {
                        g.drawImage(herir[numero].getImage(), 50 + desplazamientoHorizontal, 0 + desplazamientoVertical, ancho, alto, null);
                    }
                    break;
                case 2:
                    if (isMago && this.ancho < 0) {
                        g.drawImage(morir[numero].getImage(), 55 + desplazamientoHorizontal, 2 + desplazamientoVertical, ancho + 10, alto - 2, null);
                    } else if (isMago) {
                        g.drawImage(morir[numero].getImage(), 55 + desplazamientoHorizontal, 2 + desplazamientoVertical, ancho - 8, alto - 2, null);
                    } else {
                        g.drawImage(morir[numero].getImage(), 50 + desplazamientoHorizontal, 0 + desplazamientoVertical, ancho, alto, null);
                    }
                    break;
                case 3:
                    if (isMago && this.ancho < 0) {
                        g.drawImage(atacar[numero].getImage(), 55 + desplazamientoHorizontal, -25 + desplazamientoVertical, ancho - 140, alto + 45, null);
                    } else if (isMago) {
                        g.drawImage(atacar[numero].getImage(), 50 + desplazamientoHorizontal, -25 + desplazamientoVertical, ancho + 150, alto + 45, null);
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

    public void herir() {
        x = 1;
        numero = 0;
        if (!hilo.isAlive()) {
            hilo.start();
        }
    }

    public void morir() {
        x = 2;
        numero = 0;
        if (!hilo.isAlive()) {
            hilo.start();
        }
    }

    public void atacar() {
        x = 3;
        numero = 0;
        if (!hilo.isAlive()) {
            hilo.start();
        }
    }

    //operacion necesaria para el manejo de poblaciones
    @Override
    public void operar(KeyEvent evento) {
        if (tipoControl) {
            control = new FlechasControl(evento, this);
            control.operar();
        } else {
            control = new WASDControl(evento, this);
            control.operar();
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

    @Override
    public void cambiarControl() {
        tipoControl = !tipoControl;
    }
}
