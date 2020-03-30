package Logica;

/**
 *
 * @author Mateo
 */
public class Herir implements Command {

    Personaje personaje;
    Mascota macota;
    int d;

    public Herir(Personaje personaje, int d) {
        this.personaje = personaje;
        this.d = d;
    }

    private void hacerDaño() {
        personaje.herir(d);
        if (personaje.vidaRest <= 0) {
            personaje.muerto = true;
            personaje.seleccionable = 0;
            personaje.morir();
        }
    }

    @Override
    public void accion() {
        hacerDaño();
    }

}
