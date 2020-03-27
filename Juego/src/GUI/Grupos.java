package GUI;

import Logica.Personaje;
import Logica.Poblacion;

/**
 *
 * @author Mateo
 */
public class Grupos implements Observador {

    Poblacion grupo1 = new Poblacion("Grupo1");
    Poblacion grupo2 = new Poblacion("Grupo2");

    @Override
    public void update() {
        if(grupo1.power){
            grupo1.operar();
        }else{
            grupo2.operar();
        }
    }

}
