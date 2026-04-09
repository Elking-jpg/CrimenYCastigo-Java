package Escenarios;
import StP.*;

public class Malecon extends Escenario {
    public Malecon() {
        super("Malecon", "El viento del Neva golpea con una frialdad indiferente. El agua oscura fluye pesadamente, como si arrastrara los pecados de toda San Petersburgo hacia el mar.");
    }

    @Override
    public String obtenerAccionesPosibles(Personaje p) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n===================================================================================\n");
        if (p.inventario.contains("Hacha")) sb.append("0. Arrojar el hacha al río Neva\n");
        sb.append("1. Mirar la inmensidad del agua");
        sb.append("\n===================================================================================\n");
        return sb.toString();
    }

    @Override
    public String ejecutarAccion(int opcion, Personaje p) {
        if (opcion == 0 && p.inventario.contains("Hacha")) return gestionarDescarteHacha(p);
        else if (opcion == 1) return mirarRio(p);
        return "\nEl frío es insoportable, no hay nada más que hacer aquí.\n";
    }

    private String gestionarDescarteHacha(Personaje p) {
        p.inventario.remove("Hacha");
        StringBuilder sb = new StringBuilder();
        sb.append("\n===================================================================================\n");
        if (p.ahMatado) {
            int paranoia = p.alma.estadoPsicologico.get("Paranoia");
            p.alma.estadoPsicologico.put("Paranoia", Math.max(0, paranoia - 25));

            int esperanza = p.alma.estadoPsicologico.get("Esperanza");
            p.alma.estadoPsicologico.put("Esperanza", Math.min(100, esperanza + 20));

            sb.append("Sacas el hierro manchado y lo dejas caer. El chapoteo es breve.\n");
            sb.append("Has borrado la prueba física, pero haz purificado tu alma ?.\n");
            sb.append("Tu Paranoia disminuye al ver desaparecer el arma.");
            
        } else {
            p.alma.estadoPsicologico.put("Decision", 0);
            int esp = p.alma.estadoPsicologico.get("Esperanza");
            p.alma.estadoPsicologico.put("Esperanza", Math.min(100, esp + 5)); 
            sb.append("Lanzas el hacha con un gesto de asco hacia ti mismo.\n");
            sb.append("Al verla hundirse, comprendes la verdad: no eres un Napoleón.\n");
            sb.append("Eres un hombre ordinario que ha temblado ante su propia sombra.\n");
            sb.append("Tu Decisión colapsa. El sueño de la grandeza ha muerto en el fango del río.");
        }
        
        sb.append("\n\nParanoia: ").append(p.alma.estadoPsicologico.get("Paranoia"))
          .append("\nDecision: ").append(p.alma.estadoPsicologico.get("Decision"))
          .append("\nEsperanza: ").append(p.alma.estadoPsicologico.get("Esperanza"));
        sb.append("\n===================================================================================\n");
        return sb.toString();
    }

    private String mirarRio(Personaje p) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n===================================================================================\n");
        if (p.ahMatado) {
            sb.append("El Neva parece un espejo negro. Te preguntas si bajo esa corriente \n");
            sb.append("hay lugar para un hombre que ha roto la ley de Dios y de los hombres.");
            int pnx = p.alma.estadoPsicologico.get("Paranoia");
            p.alma.estadoPsicologico.put("Paranoia", Math.min(100, pnx + 5));
        } else {
            sb.append("Observas el fluir del agua. Es eterno, ajeno a tus deudas y a tus delirios.\n");
            sb.append("Sientes una pequeña chispa de calma ante la magnitud de la naturaleza.");
            int esp = p.alma.estadoPsicologico.get("Esperanza");
            p.alma.estadoPsicologico.put("Esperanza", Math.min(100, esp + 5));
        }
        sb.append("\n\nParanoia: ").append(p.alma.estadoPsicologico.get("Paranoia"))
          .append("\nEsperanza: ").append(p.alma.estadoPsicologico.get("Esperanza"));
        sb.append("\n===================================================================================\n");
        return sb.toString();
    }
}