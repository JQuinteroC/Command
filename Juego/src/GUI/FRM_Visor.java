package GUI;

import Logica.Mascota;
import Logica.Personaje;
import Logica.Poblacion;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class FRM_Visor extends javax.swing.JFrame implements KeyListener, Observable {

    ArrayList<Personaje> personajes = new ArrayList<>();
    ArrayList<Personaje> huevos = new ArrayList<>();
    ArrayList<Observador> observadores = new ArrayList<>();
    Grupos grupos = new Grupos();
    Reproductor[] repro = new Reproductor[2];
    Thread musica;

    public FRM_Visor(Personaje p, Personaje huevo, int cancion) {

        // Instancia de la ventana
        initComponents();
        super.setLocationRelativeTo(null);

        // Configuración del personaje y grupo
        p.setPanel(panel);
        this.personajes.add(p.clone());
        this.personajes.add(p.clone());
        this.personajes.add(p);
        this.personajes.add(p.clone());
        this.personajes.get(1).setDesplazamientoVertical(45);
        this.personajes.get(1).setDesplazamientoHorizontal(460);
        this.personajes.get(1).setHitbox(460, 45, this.personajes.get(1).getAncho(), this.personajes.get(1).getAlto());
        this.personajes.get(2).setDesplazamientoVertical(-10);
        this.personajes.get(2).setDesplazamientoHorizontal(530);
        this.personajes.get(2).setHitbox(530, -10, this.personajes.get(2).getAncho(), this.personajes.get(2).getAlto());
        this.personajes.get(3).setDesplazamientoVertical(-10);
        this.personajes.get(3).setDesplazamientoHorizontal(390);
        this.personajes.get(3).setHitbox(390, -10, this.personajes.get(3).getAncho(), this.personajes.get(3).getAlto());
        panel.add(this.personajes.get(0));
        panel.add(this.personajes.get(1));
        panel.add(this.personajes.get(2));
        panel.add(this.personajes.get(3));

        //se crean las poblaciones del patron Composite
        grupos.grupo.addPersonaje(this.personajes.get(0));
        grupos.grupo2.addPersonaje(this.personajes.get(1));
        grupos.grupo2.addPersonaje(this.personajes.get(2));
        grupos.grupo2.addPersonaje(this.personajes.get(3));

        //Metiendo al huevito
        huevo.setPanel(panel);
        this.huevos.add(huevo);
        this.huevos.get(0).setDesplazamientoVertical(300);
        this.huevos.get(0).setDesplazamientoHorizontal(480);
        this.huevos.get(0).setHitbox(480 - 91, 300 - 40, this.huevos.get(0).getAncho() + 192, (this.huevos.get(0).getAlto() / 2) + 125);
        panel.add(this.huevos.get(0));

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

    }

    @Override
    public void notificar() {
    }

    public void notificar(Personaje p) {
        for (Observador o : observadores) {
            o.update(p);
        }
    }

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
                this.dispose();
                break;
            case '+':
                boolean agregado = false;
                for (int i = 0; i < 4; i++) {
                    if (grupos.grupo.isHere(personajes.get(i))) {
                    } else {
                        grupos.grupo2.deletePerson(personajes.get(i));
                        grupos.grupo.addPersonaje(this.personajes.get(i));
                        agregado = true;
                        i = 10;
                    }
                }
                if (!agregado) {
                    JOptionPane.showMessageDialog(null, "No hay mas personajes para agregar");
                }

                break;
            case '-':
                agregado = false;
                for (int i = 3; i >= 0; i--) {
                    if (grupos.grupo2.isHere(personajes.get(i))) {
                    } else {
                        grupos.grupo.deletePerson(personajes.get(i));
                        grupos.grupo2.addPersonaje(this.personajes.get(i));
                        agregado = true;
                        i = -1;
                    }
                }
                if (!agregado) {
                    JOptionPane.showMessageDialog(null, "No hay mas personajes para agregar");
                }
                break;
            case 10:
                grupos.grupo.cambiarControl();
                break;
            default:
                grupos.grupo.operar(e);
                break;
        }
        for (int i = 0; i < personajes.size(); i++) {
            for (int j = 0; j < huevos.size(); j++) {
                if (personajes.get(i).getHitbox().intersects(huevos.get(j).getHitbox())) {
                    int team = 0;
                    huevos.get(j).interrumpir();
                    panel.remove(huevos.get(j));
                    huevos.remove(j);
                    personajes.get(i).interrumpir();
                    panel.remove(personajes.get(i));
                    if (grupos.grupo.isHere(personajes.get(i))) {
                        grupos.grupo.deletePerson(personajes.get(i));
                        team = 1;
                    } else {
                        grupos.grupo2.deletePerson(personajes.get(i));
                        team = 2;
                    }
                    Personaje mas;
                    try {
                        mas = new Mascota(personajes.get(i), panel);
                        //mas.decorado=true;
                        personajes.set(i, mas);
                        if (team == 1) {
                            grupos.grupo.addPersonaje(mas);
                        } else {
                            grupos.grupo2.addPersonaje(mas);
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(FRM_Visor.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    panel.add(personajes.get(i));
                    panel.repaint();
                }
            }
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
                notificar(personajes.get(i));
            }
        }
    }//GEN-LAST:event_panelMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel fondo;
    private javax.swing.JPanel panel;
    // End of variables declaration//GEN-END:variables

    private boolean colision(Personaje p, Personaje huevo) {
        if ((p.getDesplazamientoHorizontal() + p.getAncho() > huevo.getDesplazamientoHorizontal()) & (p.getDesplazamientoHorizontal() < huevo.getDesplazamientoHorizontal() + (huevo.getAncho())) & (p.getDesplazamientoVertical() > huevo.getDesplazamientoVertical()) & (p.getDesplazamientoVertical() < huevo.getDesplazamientoVertical() + (huevo.getAlto()))) {
            return true;
        } else {
            return false;
        }
    }

    private boolean colisionPointer(int x, int y, Personaje personaje) {
        if ((x > personaje.getHitbox().getX()) & (x < personaje.getHitbox().getX() + (personaje.getHitbox().getWidth())) & (y > personaje.getHitbox().getY()) & (y < personaje.getHitbox().getY() + personaje.getHitbox().getHeight())) {
            return true;
        } else {
            return false;
        }
    }
}
