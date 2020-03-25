package Logica;

import java.util.ArrayList;

/**
 *
 * @author Mateo
 */
public class Poblacion implements Composite {

    public boolean activo = false;
    private String nombrePoblacion = "";
    public ArrayList<Composite> poblacion = new ArrayList<>();

    public Poblacion(String nombre) {
        this.nombrePoblacion = nombre;
    }

    @Override
    public void operar() {
        for (int i = 0; i < poblacion.size(); i++) {
            poblacion.get(i).operar();
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
