package Escenarios;
import StP.Escenario;
import StP.Personaje;

public class CallejonOscura extends Escenario{
    
    public CallejonOscura() {
    super("Calle Oscura", "Un atajo estrecho entre edificios de color amarillento. El aire huele a cal y a polvo de construcción.");
    }

    @Override
    public String obtenerAccionesPosibles(Personaje p) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n===================================================================================\n");
        sb.append("Solo sombras y paredes descascaradas. No hay nada que ver, solo avanzar.");
        sb.append("\n===================================================================================\n");  
        return sb.toString();
    }

    @Override
    public String ejecutarAccion(int opcion, Personaje p) {
        return ""; 
    }


}

