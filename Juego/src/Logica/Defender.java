package Logica;

/**
 *
 * @author Mateo
 */
public class Defender implements Command {

    Personaje personaje;

    public Defender(Personaje personaje) {
        this.personaje = personaje;
    }

    private void hacerDefensa() {
        personaje.defender();
    }

    @Override
    public void accion() {
        hacerDefensa();
    }

}
