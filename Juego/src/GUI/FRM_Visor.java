package GUI;

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

public class FRM_Visor extends javax.swing.JFrame implements KeyListener, Observable {

    ArrayList<Personaje> personajes = new ArrayList<>();
    ArrayList<Personaje> huevos = new ArrayList<>();
    ArrayList<Observador> observadores = new ArrayList<>();
    Grupos grupos = new Grupos();
    Reproductor[] repro = new Reproductor[2];
    Thread musica;
    Personaje personajeSeleccionado;
    Personaje enemigoSeleccionado;
    Boolean turno;

    public FRM_Visor(Personaje p1, Personaje huevo, Personaje p2, int cancion) {
        super("Magos y Duendes");
        // Instancia de la ventana
        initComponents();
        super.setLocationRelativeTo(null);
        turno = false;
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
        panel.add(personajes.get(2));
        panel.add(personajes.get(3));

        //se crean las poblaciones del patron Composite
        grupos.grupo1.addPersonaje(personajes.get(0));
        grupos.grupo1.addPersonaje(personajes.get(1));
        grupos.grupo2.addPersonaje(personajes.get(2));
        grupos.grupo2.addPersonaje(personajes.get(3));

        //Metiendo al huevito
        huevo.setPanel(panel);
        huevos.add(huevo);
        huevos.get(0).setDesplazamientoVertical(50);
        huevos.get(0).setDesplazamientoHorizontal(480);
        huevos.get(0).setHitbox(480 - 91, 50 - 40, huevos.get(0).getAncho() + 192, (huevos.get(0).getAlto() / 2) + 125);

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

        //Definicion de primer turno, quienes se pueden seleccionar, empezamos por izquierda, solo hay que seleccionar el enemigo
        personajes.get(0).setSeleccionable(0); //Pasar a ponerselo al grupo designado
        personajes.get(1).setSeleccionable(0);
        personajes.get(2).setSeleccionable(1); //Pasar a ponerselo al grupo designado
        personajes.get(3).setSeleccionable(1);
    }

    @Override
    public void notificar() {
    }

    public void notificar(Personaje p) {
        for (Observador o : observadores) {
            o.update(p);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
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
            case 10:
                grupos.grupo1.cambiarControl();
                break;
            case ' ':
                if ((personajeSeleccionado != null) && (enemigoSeleccionado != null)) {
                    if ((personajes.get(2).getSeleccionable() == 1) || (personajes.get(3).getSeleccionable() == 1)) { //Pasar a revisar el grupo en lugar de solo un personaje
                        grupos.grupo1.operar(e);
                        System.out.println("El " + personajeSeleccionado.getName() + " ataca a " + enemigoSeleccionado.getName());
                        personajeSeleccionado.setSeleccionable(2);
                        enemigoSeleccionado.setVidaRestante(enemigoSeleccionado.getVidaRestante() - 30);
                        System.out.println("A " + enemigoSeleccionado.getName() + " le queda " + enemigoSeleccionado.getVidaRestante() + " de vida restante.");
                        if (enemigoSeleccionado.getVidaRestante() <= 0) {
                            enemigoSeleccionado.setMuerto(true);
                            enemigoSeleccionado.setSeleccionable(2);
                        }
                        if ((personajes.get(0).getSeleccionable() == 2) && (personajes.get(1).getSeleccionable() == 2)) {
                            for (int j = 0; j < 4; j++) { //configurar para que sea por grupos
                                if (personajes.get(j).getMuerto() == false) {
                                    if (j < 2) {
                                        personajes.get(j).setSeleccionable(1);
                                    } else {
                                        personajes.get(j).setSeleccionable(0);
                                    }
                                }
                            }
                            System.out.println();
                            System.out.println("Cambio de Turno");
                            System.out.println();
                        }
                        personajeSeleccionado = null;
                        enemigoSeleccionado = null;
                    } else if ((personajes.get(0).getSeleccionable() == 1) || (personajes.get(1).getSeleccionable() == 1)) {
                        grupos.grupo2.operar(e);
                        System.out.println("El " + personajeSeleccionado.getName() + " ataca a " + enemigoSeleccionado.getName());
                        personajeSeleccionado.setSeleccionable(2);
                        enemigoSeleccionado.setVidaRestante(enemigoSeleccionado.getVidaRestante() - 30);
                        System.out.println("A " + enemigoSeleccionado.getName() + " le queda " + enemigoSeleccionado.getVidaRestante() + " de vida restante.");
                        if (enemigoSeleccionado.getVidaRestante() <= 0) {
                            enemigoSeleccionado.setMuerto(true);
                            enemigoSeleccionado.setSeleccionable(2);
                        }
                        if ((personajes.get(2).getSeleccionable() == 2) && (personajes.get(3).getSeleccionable() == 2)) {
                            for (int j = 0; j < 4; j++) { //configurar para que sea por grupos
                                if (personajes.get(j).getMuerto() == false) {
                                    if (j < 2) {
                                        personajes.get(j).setSeleccionable(0);
                                    } else {
                                        personajes.get(j).setSeleccionable(1);
                                    }
                                }
                            }
                            System.out.println();
                            System.out.println("Cambio de Turno");
                            System.out.println();
                        }
                        personajeSeleccionado = null;
                        enemigoSeleccionado = null;
                    }
                }
                break;
            default:
                grupos.grupo1.operar(e);
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
                System.out.println("Colision con " + personajes.get(i).getName());
                notificar(personajes.get(i));
                if (personajes.get(i).getSeleccionable() == 1) {
                    enemigoSeleccionado = personajes.get(i);
                } else if (personajes.get(i).getSeleccionable() == 0) {
                    personajeSeleccionado = personajes.get(i);
                } else {
                    System.out.println("Personaje ya usado o muerto");
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
}
