package Logica;

import GUI.Grupos;
import java.util.ArrayList;

/**
 *
 * @author Mateo
 */
public class Poblacion implements Composite {

    public boolean power = false;
    public boolean activo = false;
    private String nombrePoblacion = "";
    public ArrayList<Composite> poblacion = new ArrayList<>();

    public Poblacion(String nombre) {
        this.nombrePoblacion = nombre;
    }

    @Override
    public void operar(int j, Grupos g) {
        for (int i = 0; i < poblacion.size(); i++) {
            poblacion.get(i).operar(i, g);
        }
    }

    public boolean isHere(Composite p) {
        if (poblacion.contains(p)) {
            return true;
        } else {
            return false;
        }
    }

    public void set(int index, Composite p) {
        poblacion.set(index, p);
    }
    
    public Composite get(int index){
        return poblacion.get(index);
    }
    
    public void deletePerson(Composite p) {
        poblacion.remove(p);
    }

    public void addPersonaje(Composite p) {
        this.poblacion.add(p);
    }

    @Override
    public String getName() {
        return getName();
    }

}
