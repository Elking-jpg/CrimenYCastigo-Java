package Escenarios;
import StP.Escenario;
import StP.Personaje;

public class PuenteKokushkin extends Escenario {
    public PuenteKokushkin() {
        super("Puente Kokushkin", "El aire sobre el canal está viciado. Abajo, en los sótanos que dan al agua, se oyen gritos y risas histéricas que rompen el silencio de la noche.");
    }

    @Override
    public String obtenerAccionesPosibles(Personaje p) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n--------------------------------\n");
        sb.append("0. Detenerse a escuchar los gritos del subsuelo");
        sb.append("\n--------------------------------\n");
        return sb.toString();
    }

    @Override
    public String ejecutarAccion(int opcion, Personaje p) {
        if (opcion == 0) return escucharGritos(p);
        return "\nEl puente parece vibrar bajo tus pies, no hay nada más que hacer.\n";
    }

    private String escucharGritos(Personaje p) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n===================================================================================\n");
        sb.append("Te detienes y te apoyas en la barandilla oxidada. \n");
        sb.append("Desde las profundidades de los edificios amarillentos, el sonido de una disputa \n");
        sb.append("se mezcla con el chapoteo del agua estancada. Es el grito de la miseria pura.\n");
        int agot = p.alma.estadoFisico.get("Agotamiento Sensorial");
        p.alma.estadoFisico.put("Agotamiento Sensorial", Math.min(100, agot + 10));
        if (p.inventario.contains("Hacha")) {
            sb.append("\nSientes el peso del hacha contra tu cuerpo. Cada grito parece una acusación directa.");
            int paranoia = p.alma.estadoPsicologico.get("Paranoia");
            p.alma.estadoPsicologico.put("Paranoia", Math.min(100, paranoia + 5));
        }
        sb.append("\nTu Agotamiento Sensorial aumenta.");
        sb.append("\n\nAgotamiento Sensorial: ").append(p.alma.estadoFisico.get("Agotamiento Sensorial"))
          .append("\nParanoia: ").append(p.alma.estadoPsicologico.get("Paranoia"));
        sb.append("\n===================================================================================\n");
        return sb.toString();
    }
}