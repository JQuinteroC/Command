package GUI;

import Logica.Atacar;
import Logica.Defender;
import Logica.Herir;
import Logica.Invocador;
import Logica.Personaje;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JOptionPane;

public class FRM_Visor extends javax.swing.JFrame implements KeyListener, Observable {

    ArrayList<Personaje> personajes = new ArrayList<>();
    ArrayList<Personaje> huevos = new ArrayList<>();
    ArrayList<Observador> observadores = new ArrayList<>();
    Grupos grupos = new Grupos();
    Reproductor[] repro = new Reproductor[2];
    Thread musica;
    int intAleatorio;
    Random aleatorio;
    Invocador invoker = Invocador.obtenerInvocador();
    Boolean varTemporalHuevo;
    boolean aparecioHuevo = false;
    int contadorIzq;
    int contadorDer;

    public FRM_Visor(Personaje p1, Personaje huevo, Personaje p2, int cancion) {
        super("Magos y Duendes");
        // Instancia de la ventana
        initComponents();
        super.setLocationRelativeTo(null);
        varTemporalHuevo = true;
        contadorIzq = 0;
        contadorDer = 0;
        // Configuración del personaje y grupo
        p1.setPanel(panel);
        personajes.add(p1);
        personajes.add(p1.clone());
        personajes.get(0).setDesplazamientoVertical(300);
        personajes.get(0).setDesplazamientoHorizontal(60);
        personajes.get(0).setHitbox(60, 300, personajes.get(0).getAncho(), personajes.get(0).getAlto());
        personajes.get(0).setName(personajes.get(0).getName() + " 1");
        personajes.get(1).setDesplazamientoVertical(280);
        personajes.get(1).setDesplazamientoHorizontal(250);
        personajes.get(1).setHitbox(250, 280, personajes.get(1).getAncho(), personajes.get(1).getAlto());
        personajes.get(1).setName(personajes.get(1).getName() + " 2");
        panel.add(personajes.get(0));
        panel.add(personajes.get(1));

        // Configuración del personaje y grupo
        p2.setPanel(panel);
        personajes.add(p2);
        personajes.add(p2.clone());
        personajes.get(2).setDesplazamientoVertical(280);
        personajes.get(2).setDesplazamientoHorizontal(890);
        personajes.get(2).setAncho(-personajes.get(2).getAncho());
        personajes.get(2).setHitbox(890, 280, -personajes.get(2).getAncho(), personajes.get(2).getAlto());
        personajes.get(2).setName(personajes.get(2).getName() + " 1");

        personajes.get(3).setDesplazamientoVertical(300);
        personajes.get(3).setDesplazamientoHorizontal(1080);
        personajes.get(3).setAncho(-personajes.get(3).getAncho());
        personajes.get(3).setHitbox(1080, 300, -personajes.get(3).getAncho(), personajes.get(3).getAlto());
        personajes.get(3).setName(personajes.get(3).getName() + " 2");
        panel.add(personajes.get(3));
        panel.add(personajes.get(2));

        //se crean las poblaciones del patron Composite
        grupos.grupo1.addPersonaje(personajes.get(0));
        grupos.grupo1.addPersonaje(personajes.get(1));
        grupos.grupo2.addPersonaje(personajes.get(2));
        grupos.grupo2.addPersonaje(personajes.get(3));

        //Metiendo al huevito
        huevo.setPanel(panel);
        huevos.add(huevo);
        huevos.get(0).setDesplazamientoVertical(120);
        huevos.get(0).setDesplazamientoHorizontal(480);
        huevos.get(0).setHitbox(480 - 91, 120 - 40, huevos.get(0).getAncho() + 192, (huevos.get(0).getAlto() / 2) + 125);

        // Integración del listener 
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);

        //añadir observadores 
        observadores.add(grupos);

        //reproductores del chain 
        repro[0] = new Reproductor1();
        repro[1] = new Reproductor2();
        repro[0].setSuccessor(repro[1]);
        repro[0].cancion = cancion;
        repro[0].start();

        personajes.get(0).idle();
        personajes.get(1).idle();
        personajes.get(2).idle();
        personajes.get(3).idle();

        //Definicion de primer turno, quienes se pueden seleccionar, empezamos por izquierda, solo hay que seleccionar el enemigo
        grupos.grupo1.activo = true;
        grupos.grupo2.activo = false;

        //Definicion de Random
        aleatorio = new Random(System.currentTimeMillis());
        // Producir nuevo int aleatorio entre 7 y 14
        intAleatorio = aleatorio.nextInt(8) + 8;
    }

    @Override
    public void notificar() {
        observadores.get(0).update();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        aleatorio.setSeed(System.currentTimeMillis()); //Se actualiza la seed del random
        intAleatorio = aleatorio.nextInt(8) + 8;
        switch (e.getKeyChar()) {
            case 'q':
                FRM_Selector selector = null;
                try {
                    selector = FRM_Selector.getInstance();
                } catch (IOException ex) {
                    Logger.getLogger(FRM_Visor.class.getName()).log(Level.SEVERE, null, ex);
                }
                selector.setVisible(true);
                // Se interrumpe el hilo de musica
                repro[0].stop();
                repro[1].stop();
                personajes.clear();
                huevos.clear();
                this.dispose();
                break;
            case 'z':
                if (aparecioHuevo && contadorDer < 50 && contadorIzq < 50) {
                    contadorIzq++;
                    System.out.println("Contador Z: " + contadorIzq);
                    if (contadorIzq == 50) {
                        panel.remove(huevos.get(0));
                        grupos.grupo1.power = true;
                        notificar();
                        //personajes.get(0).setVidaRestante(personajes.get(0).getVidaRestante() + 30);
                        contadorDer = 51;

                    }
                }
                break;
            case '3':
                if (aparecioHuevo && contadorDer < 50 && contadorIzq < 50) {
                    contadorDer++;
                    System.out.println("Contador 3: " + contadorDer);
                    if (contadorDer == 50) {
                        grupos.grupo2.power = true;
                        //personajes.get(2).setVidaRestante(personajes.get(2).getVidaRestante() + 30);
                        contadorIzq = 51;
                        //System.out.println("Ahora " + personajes.get(2).getName() + " tiene " + personajes.get(2).getVidaRestante() + " de vida.");
                        panel.remove(huevos.get(0));
                    }
                }
                break;

            case ' ':
                if (grupos.grupo1.activo) {
                    if (personajes.get(0).getSeleccionable() == 1) {
                        if (personajes.get(2).getSeleccionable() == 1 || personajes.get(3).getSeleccionable() == 1) {
                            turno(0, 1, 2, 3);
                        } else {
                            JOptionPane.showMessageDialog(null, "Escoge un personaje enemigo");
                        }
                    } else if (personajes.get(1).getSeleccionable() == 1) {
                        if (personajes.get(2).getSeleccionable() == 1 || personajes.get(3).getSeleccionable() == 1) {
                            turno(1, 0, 2, 3);
                        } else {
                            JOptionPane.showMessageDialog(null, "Escoge un personaje enemigo");
                        }
                    }
                } else if (grupos.grupo2.activo) {
                    if (personajes.get(2).getSeleccionable() == 1) {
                        if (personajes.get(0).getSeleccionable() == 1 || personajes.get(1).getSeleccionable() == 1) {
                            turno(2, 3, 0, 1);
                        } else {
                            JOptionPane.showMessageDialog(null, "Escoge un personaje enemigo");
                        }
                    } else if (personajes.get(3).getSeleccionable() == 1) {
                        if (personajes.get(0).getSeleccionable() == 1 || personajes.get(1).getSeleccionable() == 1) {
                            turno(3, 2, 0, 1);
                        } else {
                            JOptionPane.showMessageDialog(null, "Escoge un personaje enemigo");
                        }
                    }
                }
                if (!aparecioHuevo) {
                    for (int i = 0; i < personajes.size(); i++) {
                        if (personajes.get(i).vidaRest <= 20) {
                            panel.add(huevos.get(0));
                            aparecioHuevo = true;
                            break;
                        }
                    }
                }
                break;

            default:
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }// </editor-fold>  

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel = new javax.swing.JPanel();
        fondo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1200, 600));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panel.setOpaque(false);
        panel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelMouseClicked(evt);
            }
        });

        org.jdesktop.layout.GroupLayout panelLayout = new org.jdesktop.layout.GroupLayout(panel);
        panel.setLayout(panelLayout);
        panelLayout.setHorizontalGroup(
            panelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 1200, Short.MAX_VALUE)
        );
        panelLayout.setVerticalGroup(
            panelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 600, Short.MAX_VALUE)
        );

        getContentPane().add(panel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1200, 600));

        fondo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/catarata.png"))); // NOI18N
        getContentPane().add(fondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1200, 600));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void panelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelMouseClicked
        PointerInfo a = MouseInfo.getPointerInfo();
        Point b = a.getLocation();
        int x = (int) b.getX() - this.getX() - 8;
        int y = (int) b.getY() - this.getY() - 32;
        for (int i = 0; i < personajes.size(); i++) {
            if (colisionPointer(x, y, personajes.get(i))) {
                if (personajes.get(i).seleccionable != 0) {
                    System.out.println("Colision con " + personajes.get(i).getName());

                    if (grupos.grupo1.isHere(personajes.get(i))) {
                        personajes.get(i).seleccionable = 1;

                    } else if (grupos.grupo2.isHere(personajes.get(i))) {
                        personajes.get(i).seleccionable = 1;
                    } else {
                        System.out.println("Personaje esta muerto");
                    }
                    int j;
                    if (i % 2 == 0) {
                        j = i + 1;
                    } else {
                        j = i - 1;
                    }
                    if (personajes.get(j).seleccionable != 0) {
                        personajes.get(j).seleccionable = 2;
                    }
                } else {
                    System.out.println("Personaje ya seleccionado");
                }
            }
        }
    }//GEN-LAST:event_panelMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel fondo;
    private javax.swing.JPanel panel;
    // End of variables declaration//GEN-END:variables

    private boolean colisionPointer(int x, int y, Personaje personaje) {
        if ((x > personaje.getHitbox().getX()) & (x < personaje.getHitbox().getX() + (personaje.getHitbox().getWidth())) & (y > personaje.getHitbox().getY()) & (y < personaje.getHitbox().getY() + personaje.getHitbox().getHeight())) {
            return true;
        } else {
            return false;
        }
    }

    private void turno(int prin, int compa, int ene1, int ene2) {

        //control de acciones
        if (!personajes.get(prin).muerto) {
            String[] options = {"Atacar", "Defender"};
            int seleccions = JOptionPane.showOptionDialog(null, "¿Que quieres hacer?", "Acción", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
            if (seleccions == 0) {
                personajes.get(prin).setDefendiendo(false);
                Atacar a = new Atacar(personajes.get(prin));

                //Preguntar a quien Atacar            
                Herir h = null;
                if (personajes.get(ene1).getSeleccionable() == 1 && !personajes.get(ene1).muerto) {
                    h = new Herir(personajes.get(ene1), intAleatorio);
                    invoker.comando.add(h);
                    invoker.comando.add(a);
                } else if (personajes.get(ene2).getSeleccionable() == 1 && !personajes.get(ene2).muerto) {
                    h = new Herir(personajes.get(ene2), intAleatorio);
                    invoker.comando.add(h);
                    invoker.comando.add(a);
                }

            } else {
                Defender d = new Defender(personajes.get(prin));
                invoker.comando.add(d);
            }
            personajes.get(prin).seleccionable = 0;
            invoker.ejecutarComandos();
            invoker.comando.clear();

            //control de turno
            if (personajes.get(compa).seleccionable == 0 || personajes.get(compa).muerto) {
                if (grupos.grupo1.isHere(personajes.get(compa))) {
                    grupos.grupo1.activo = false;
                    grupos.grupo2.activo = true;
                } else {
                    grupos.grupo1.activo = true;
                    grupos.grupo2.activo = false;
                }
                personajes.get(prin).seleccionable = 2;
                personajes.get(compa).seleccionable = 2;
            }
        } else {
            JOptionPane.showMessageDialog(null, "Esta morido :C !!!!");
        }
        //Se obtiene un ganador
        if (personajes.get(0).muerto && personajes.get(1).muerto) {
            JOptionPane.showMessageDialog(null, "HA GANADO EL EQUIPO DERECHO!!");
        } else if (personajes.get(2).muerto && personajes.get(3).muerto) {
            JOptionPane.showMessageDialog(null, "HA GANADO EL EQUIPO IZQUIERDO!!");
        }
    }
}
