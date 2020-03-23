package Logica;

/**
 *
 * @author Mateo
 */
public class Atacar implements  Command{

    Personaje personaje;

    public Atacar(Personaje personaje) {
        this.personaje = personaje;
    }
    
    private void hacerAtaque(){
        personaje.atacar();
    }
    
    @Override
    public void accion() {
        hacerAtaque();
    }
    
}
