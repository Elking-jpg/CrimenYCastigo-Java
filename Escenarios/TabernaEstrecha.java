package Escenarios;
import StP.*;

public class TabernaEstrecha extends Escenario {
    private boolean habloConMarmeladov = false;

    public TabernaEstrecha() {
        super("Taberna Estrecha", "Sótano oscuro impregnado de aguardiente y tabaco barato.");
    }

    @Override
    public String obtenerAccionesPosibles(Personaje p) {
        StringBuilder sb = new StringBuilder();
        if (!habloConMarmeladov) {
            sb.append("0. Comprar una cerveza (10 kopeks)\n").append("1. Hablar con el hombre del rincón");
        } else {
            sb.append("0. Comprar una cerveza (10 kopeks)");
        }
        return sb.toString();
    }

    @Override
    public String ejecutarAccion(int opcion, Personaje p) {
        if (opcion == 0) return comprarCerveza(p);
        else if (opcion == 1 && !habloConMarmeladov) return hablarConMarmeladov(p);
        return "\nNo hay nadie más que quiera hablar contigo aquí.\n";
    }

    private String comprarCerveza(Personaje p) {
        int dinero = p.alma.estadoFisico.get("Billetera");
        if (dinero >= 10 && !p.inventario.contains("Cerveza turbia")) {
            p.alma.estadoFisico.put("Billetera", dinero - 10);
            p.inventario.add("Cerveza turbia");
            return "\nPagas 10 kopeks por una cerveza turbia.\nBilletera: " + p.alma.estadoFisico.get("Billetera");
        } else {
            return "\nNo puedes comprar eso ahora, Rodion.";
        }
    }

    private String hablarConMarmeladov(Personaje p) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n===================================================================================\n");
        sb.append("\nEl hombre se tambalea y te toma del brazo: \n'¡Permítame, caballero... pero si la miseria, mi querido señor, la miseria no es un vicio...!'\n... ...\n");
        habloConMarmeladov = true;
        if (p.cree) {
            int esp = p.alma.estadoPsicologico.get("Esperanza");
            p.alma.estadoPsicologico.put("Esperanza", Math.min(100, esp + 10));
            sb.append("Sus palabras resuenan en tu fe. Tu esperanza aumenta.");
        } else {
            int agot = p.alma.estadoFisico.get("Agotamiento Sensorial");
            p.alma.estadoFisico.put("Agotamiento Sensorial", Math.min(100, agot + 10));
            sb.append("La decadencia del hombre te agota profundamente.");
        }
        sb.append("\n\nEsperanza: ").append(p.alma.estadoPsicologico.get("Esperanza"))
          .append("\nAgotamiento Sensorial: ").append(p.alma.estadoFisico.get("Agotamiento Sensorial"));
        sb.append("\n===================================================================================\n");
        return sb.toString();
    }
}