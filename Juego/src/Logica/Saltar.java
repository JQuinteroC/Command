package Logica;

/**
 *
 * @author Mateo
 */
public class Saltar implements Command{

    Personaje personaje;
    
    public Saltar(Personaje personaje) {
        this.personaje = personaje;
    }
    
    
    
    private void hacerSalto(){
        personaje.saltar();
    }

    @Override
    public void accion() {
        hacerSalto();
    }
    
}
