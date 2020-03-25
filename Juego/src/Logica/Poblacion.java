package Logica;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 *
 * @author Mateo
 */
public class Poblacion implements Composite {

    private String nombrePoblacion = "";
    ArrayList<Composite> poblacion = new ArrayList<>();

    public Poblacion(String nombre) {
        this.nombrePoblacion = nombre;
    }

    @Override
    public void operar(KeyEvent evento) {
        for (int i = 0; i < poblacion.size(); i++) {
            poblacion.get(i).operar(evento);   
        }
    }

    @Override
    public void cambiarControl(){
        for (int i = 0; i < poblacion.size(); i++) {
            poblacion.get(i).cambiarControl();
        }
    }
    
    public boolean isHere(Personaje p) {
        if (poblacion.contains(p)) {
            return true;
        } else {
            return false;
        }
    }

    public void deletePerson(Personaje p) {
        poblacion.remove(p);
    }

    public void addPersonaje(Composite p) {
        this.poblacion.add(p);
    }

}
