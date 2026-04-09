package Escenarios;
import StP.*;

public class NodoEspecial1 extends Escenario {
    public NodoEspecial1(){
        super("...", "...");
    }
    @Override
    public String obtenerAccionesPosibles(Personaje p) {
        return "...";
    }

    @Override
    public String ejecutarAccion(int opcion, Personaje p) {
        return "...";
    }

}
