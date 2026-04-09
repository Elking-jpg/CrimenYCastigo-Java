package Escenarios;
import StP.Escenario;
import StP.Personaje;

public class RioNeva extends Escenario {
    public RioNeva() {
        super("Orillas del Neva", "El agua golpea rítmicamente contra el granito de los muelles. El horizonte es amplio, pero el frío que emana del río se siente como una advertencia.");
    }

    @Override
    public String obtenerAccionesPosibles(Personaje p) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n===================================================================================\n");
        sb.append("0. Mojarse la cara y las manos con el agua del río\n");
        sb.append("1. Contemplar la corriente en silencio");
        sb.append("\n===================================================================================\n");
        return sb.toString();
    }

    @Override
    public String ejecutarAccion(int opcion, Personaje p) {
        if (opcion == 0) return ritualLavado(p);
        else if (opcion == 1) return contemplarCorriente(p);
        return "\nEl río sigue su curso, ignorándote.\n";
    }

    private String ritualLavado(Personaje p) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n===================================================================================\n");
        if (p.ahMatado) {
            sb.append("Sumerges las manos en el agua helada. Frotas con desesperación, \n");
            sb.append("pero aunque el agua sale limpia, sientes que el frío se queda bajo tu piel.\n");
            sb.append("No hay jabón en el mundo que limpie lo que el hierro ha hecho.\n");
            int par = p.alma.estadoPsicologico.get("Paranoia");
            int agot = p.alma.estadoFisico.get("Agotamiento Sensorial");
            p.alma.estadoPsicologico.put("Paranoia", Math.max(0, par - 10));
            p.alma.estadoFisico.put("Agotamiento Sensorial", Math.min(100, agot + 10));
            sb.append("\nEl choque térmico calma tus nervios, pero agota tu cuerpo.");
        } else {
            sb.append("Te mojas la cara. El agua está sucia y sabe a hierro y a ciudad. \n");
            sb.append("Te despierta de tu letargo, recordándote que aún tienes un cuerpo que alimentar.\n");
            int luc = p.alma.estadoPsicologico.get("Lucidez");
            p.alma.estadoPsicologico.put("Lucidez", Math.min(100, luc + 10));
        }
        sb.append("\n\nParanoia: ").append(p.alma.estadoPsicologico.get("Paranoia"))
          .append("\nLucidez: ").append(p.alma.estadoPsicologico.get("Lucidez"))
          .append("\nAgotamiento Sensorial: ").append(p.alma.estadoFisico.get("Agotamiento Sensorial"));
        sb.append("\n===================================================================================\n");
        return sb.toString();
    }

    private String contemplarCorriente(Personaje p) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n===================================================================================\n");
        sb.append("Ves pasar restos de madera y suciedad arrastrados hacia el mar. \n");
        sb.append("Deseas fervientemente ser uno de esos objetos: algo sin voluntad, \n");
        sb.append("algo que simplemente se deja llevar por una fuerza superior.\n");
        int dec = p.alma.estadoPsicologico.get("Decision");
        p.alma.estadoPsicologico.put("Decision", Math.max(0, dec - 10));
        sb.append("\nTu voluntad de actuar se disuelve en la corriente.");
        sb.append("\n\nDecision: ").append(p.alma.estadoPsicologico.get("Decision"));
        sb.append("\n===================================================================================\n");
        return sb.toString();
    }
}