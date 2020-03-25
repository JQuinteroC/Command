package Logica;

/**
 *
 * @author Mateo
 */
public class Herir implements Command{

    Personaje personaje;
    int d;
    
    public Herir(Personaje personaje, int d) {
        this.personaje = personaje;
        this.d = d;
    }
    
    private void hacerDaño(){
        personaje.herir(d);
    }

    @Override
    public void accion() {
        hacerDaño();
    }
    
}
