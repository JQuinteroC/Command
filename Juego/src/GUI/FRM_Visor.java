package GUI;

import Logica.Atacar;
import Logica.Defender;
import Logica.Herir;
import Logica.Invocador;
import Logica.Personaje;
import java.awt.Color;
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
    ArrayList<javax.swing.JLabel> labels1 = new ArrayList<>();
    ArrayList<Personaje> huevos = new ArrayList<>();
    ArrayList<Observador> observadores = new ArrayList<>();
    Grupos grupos = new Grupos();
    Reproductor[] repro = new Reproductor[2];
    Thread musica;
    int intAleatorio;
    Random aleatorio;
    Invocador invoker = Invocador.obtenerInvocador();
    Boolean varTemporalHuevo;
    java.awt.Color color;
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

        color = turno.getForeground();

        //llenar arrays de labels
        labels1.add(vidap1);
        labels1.add(vidap2);
        labels1.add(vidap3);
        labels1.add(vidap4);

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
        
        press1.setVisible(false);
        press2.setVisible(false);
        
    }


    @Override
    public void notificar() {
        press1.setVisible(false);
        press2.setVisible(false);
        if (grupos.grupo1.power) {
            JOptionPane.showMessageDialog(null, "GANO EL EQUIPO IZQUIERDO! +20 de vida a sus personajes");
            panel.remove(personajes.get(0));
            panel.remove(personajes.get(1));

            observadores.get(0).update();
            personajes.set(0, (Personaje) grupos.grupo1.poblacion.get(0));
            personajes.set(1, (Personaje) grupos.grupo1.poblacion.get(1));

            panel.add(personajes.get(0));
            panel.add(personajes.get(1));

            personajes.get(0).idle();
            personajes.get(1).idle();
        } else {
             JOptionPane.showMessageDialog(null, "GANO EL EQUIPO DERECHO! +20 de vida a sus personajes");
            panel.remove(personajes.get(2));
            panel.remove(personajes.get(3));

            observadores.get(0).update();
            personajes.set(2, (Personaje) grupos.grupo2.poblacion.get(0));
            personajes.set(3, (Personaje) grupos.grupo2.poblacion.get(1));

            panel.add(personajes.get(2));
            panel.add(personajes.get(3));

            personajes.get(2).idle();
            personajes.get(3).idle();
        }
        actualizarVida();
        panel.repaint();
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
                    press1.setText("PRESIONA LA Z: "+contadorIzq);
                    if (contadorIzq == 50) {
                        panel.remove(huevos.get(0));
                        grupos.grupo1.power = true;
                        contadorDer = 51;
                        notificar();
                    }
                }
                break;
            case '3':
                if (aparecioHuevo && contadorDer < 50 && contadorIzq < 50) {
                    contadorDer++;
                    press2.setText("PRESIONA EL 3: "+contadorDer);
                    if (contadorDer == 50) {
                        panel.remove(huevos.get(0));
                        grupos.grupo2.power = true;
                        contadorIzq = 51;
                        notificar();
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
                            JOptionPane.showMessageDialog(null, "HA APARECIDO UN HUEVO, ATRAPALO!\nGANA EL PRIMERO EN LLEGAR A 50");
                            press1.setVisible(true);
                            press2.setVisible(true);
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
        vidap2 = new javax.swing.JLabel();
        vidap1 = new javax.swing.JLabel();
        vidap3 = new javax.swing.JLabel();
        vidap4 = new javax.swing.JLabel();
        turno = new javax.swing.JLabel();
        golpe = new javax.swing.JLabel();
        selet1 = new javax.swing.JLabel();
        selet2 = new javax.swing.JLabel();
        press1 = new javax.swing.JLabel();
        press2 = new javax.swing.JLabel();
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

        vidap2.setFont(new java.awt.Font("Sylfaen", 1, 14)); // NOI18N
        vidap2.setForeground(new java.awt.Color(255, 0, 0));
        vidap2.setText("Vida: 60");

        vidap1.setFont(new java.awt.Font("Sylfaen", 1, 14)); // NOI18N
        vidap1.setForeground(new java.awt.Color(255, 0, 0));
        vidap1.setText("Vida: 60");

        vidap3.setFont(new java.awt.Font("Sylfaen", 1, 14)); // NOI18N
        vidap3.setForeground(new java.awt.Color(255, 0, 0));
        vidap3.setText("Vida: 60");

        vidap4.setFont(new java.awt.Font("Sylfaen", 1, 14)); // NOI18N
        vidap4.setForeground(new java.awt.Color(255, 0, 0));
        vidap4.setText("Vida: 60");

        turno.setFont(new java.awt.Font("Showcard Gothic", 1, 36)); // NOI18N
        turno.setForeground(new java.awt.Color(0, 204, 255));
        turno.setText("TURNO DEL EQUIPO IZQUIERDO!");

        golpe.setFont(new java.awt.Font("Showcard Gothic", 1, 18)); // NOI18N
        golpe.setForeground(new java.awt.Color(255, 102, 0));
        golpe.setText("Ultimo Golpe:  0");

        selet1.setFont(new java.awt.Font("Showcard Gothic", 1, 24)); // NOI18N
        selet1.setForeground(new java.awt.Color(255, 255, 0));
        selet1.setText("SELECCIONADO : ");

        selet2.setFont(new java.awt.Font("Showcard Gothic", 1, 24)); // NOI18N
        selet2.setForeground(new java.awt.Color(255, 255, 0));
        selet2.setText("SELECCIONADO : ");

        press1.setFont(new java.awt.Font("Showcard Gothic", 1, 18)); // NOI18N
        press1.setForeground(new java.awt.Color(204, 0, 204));
        press1.setText("PRESIONA LA Z:");

        press2.setFont(new java.awt.Font("Showcard Gothic", 1, 18)); // NOI18N
        press2.setForeground(new java.awt.Color(204, 0, 204));
        press2.setText("PRESIONA EL 3:");

        org.jdesktop.layout.GroupLayout panelLayout = new org.jdesktop.layout.GroupLayout(panel);
        panel.setLayout(panelLayout);
        panelLayout.setHorizontalGroup(
            panelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, panelLayout.createSequentialGroup()
                .addContainerGap(302, Short.MAX_VALUE)
                .add(panelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, panelLayout.createSequentialGroup()
                        .add(golpe)
                        .add(491, 491, 491))
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, panelLayout.createSequentialGroup()
                        .add(turno)
                        .add(294, 294, 294))))
            .add(org.jdesktop.layout.GroupLayout.TRAILING, panelLayout.createSequentialGroup()
                .add(245, 245, 245)
                .add(vidap1)
                .add(91, 91, 91)
                .add(vidap2)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(vidap3)
                .add(147, 147, 147)
                .add(vidap4)
                .add(164, 164, 164))
            .add(panelLayout.createSequentialGroup()
                .add(128, 128, 128)
                .add(selet1)
                .add(331, 331, 331)
                .add(selet2)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .add(panelLayout.createSequentialGroup()
                .addContainerGap()
                .add(press1)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(press2)
                .add(74, 74, 74))
        );
        panelLayout.setVerticalGroup(
            panelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(panelLayout.createSequentialGroup()
                .add(63, 63, 63)
                .add(turno)
                .add(82, 82, 82)
                .add(panelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(selet1)
                    .add(selet2))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 130, Short.MAX_VALUE)
                .add(golpe)
                .add(148, 148, 148)
                .add(panelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(vidap1)
                    .add(vidap2)
                    .add(vidap3)
                    .add(vidap4))
                .add(37, 37, 37)
                .add(panelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(press2)
                    .add(press1)))
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

                    if (grupos.grupo1.isHere(personajes.get(i))) {
                        personajes.get(i).seleccionable = 1;
                        selet1.setText("SELECCIONADO: "+personajes.get(i).getName());
                    } else if (grupos.grupo2.isHere(personajes.get(i))) {
                        personajes.get(i).seleccionable = 1;
                        selet2.setText("SELECCIONADO: "+personajes.get(i).getName());
                    } else {
                        JOptionPane.showMessageDialog(null, "El personaje esta muerto");
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
                    JOptionPane.showMessageDialog(null, "Personaje no seleccionable");
                }
            }
        }
    }//GEN-LAST:event_panelMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel fondo;
    private javax.swing.JLabel golpe;
    private javax.swing.JPanel panel;
    private javax.swing.JLabel press1;
    private javax.swing.JLabel press2;
    private javax.swing.JLabel selet1;
    private javax.swing.JLabel selet2;
    private javax.swing.JLabel turno;
    private javax.swing.JLabel vidap1;
    private javax.swing.JLabel vidap2;
    private javax.swing.JLabel vidap3;
    private javax.swing.JLabel vidap4;
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
                    golpe.setText("ULTIMO GOLPE: " + intAleatorio);
                    invoker.comando.add(h);
                    invoker.comando.add(a);
                } else if (personajes.get(ene2).getSeleccionable() == 1 && !personajes.get(ene2).muerto) {
                    h = new Herir(personajes.get(ene2), intAleatorio);
                    golpe.setText("ULTIMO GOLPE: " + intAleatorio);
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
            
            //limpiar selecciones
            selet1.setText("SELECCIONADO: ");
            selet2.setText("SELECCIONADO: ");
            
            //actualizar labels
            actualizarVida();

            //control de turno
            if (personajes.get(compa).seleccionable == 0 || personajes.get(compa).muerto) {
                if (grupos.grupo1.isHere(personajes.get(compa))) {
                    turno.setText("TURNO DEL EQUIPO DERECHO");
                    turno.setForeground(Color.RED);
                    grupos.grupo1.activo = false;
                    grupos.grupo2.activo = true;
                } else {
                    turno.setText("TURNO DEL EQUIPO IZQUIERDO");
                    turno.setForeground(color);
                    grupos.grupo1.activo = true;
                    grupos.grupo2.activo = false;
                }
                if (!personajes.get(prin).muerto) {
                    personajes.get(prin).seleccionable = 2;
                }
                if (!personajes.get(compa).muerto) {
                    personajes.get(compa).seleccionable = 2;
                }
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

    public void actualizarVida() {
        for (int i = 0; i < personajes.size(); i++) {
            if (!personajes.get(i).muerto) {
                labels1.get(i).setText("Vida: " + personajes.get(i).vidaRest);
            } else {
                labels1.get(i).setText("MUERTO");
                labels1.get(i).setForeground(Color.YELLOW);
            }
        }
    }

}
