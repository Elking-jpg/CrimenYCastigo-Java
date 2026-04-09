package Escenarios;
import StP.*;

public class NodoEspecial2 extends Escenario {
    public NodoEspecial2(){
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