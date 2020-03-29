package GUI;

import Logica.Personaje;
import Logica.Poblacion;

/**
 *
 * @author Mateo
 */
public class Grupos implements Observador {

    public Poblacion grupo1 = new Poblacion("Grupo 1");
    public Poblacion grupo2 = new Poblacion("Grupo 2");

    @Override
    public void update() {
        if (grupo1.power) {
            grupo1.operar(0, this);
        } else {
            grupo2.operar(0, this);
        }
    }

}
