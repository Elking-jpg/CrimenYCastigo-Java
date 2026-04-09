package Escenarios;
import StP.*;

public class CasaRasumijin extends Escenario {
    private boolean habloConRasumijin = false;

    public CasaRasumijin() {
        super("Casa de Rasumijin", 
              "Una habitación pequeña pero desordenadamente vital. \n" +
              "Hay libros por todas partes y un mapa de la ciudad clavado en la pared.");
    }

    @Override
    public String obtenerAccionesPosibles(Personaje p) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n===================================================================================\n");
        if (!p.ahMatado && !habloConRasumijin) {
            sb.append("0. Hablar con Rasumijin sobre el trabajo de traducción");
        } else {
            sb.append("Rasumijin está ocupado con sus papeles. El ambiente se siente pesado.");
        }
        sb.append("\n===================================================================================\n");
        return sb.toString();
    }

    @Override
    public String ejecutarAccion(int opcion, Personaje p) {
        if (opcion == 0 && !p.ahMatado && !habloConRasumijin) {
            return hablarConRasumijin(p);
        }
        return "\nNo hay nada más que hacer aquí, solo quieres marcharte.\n";
    }

    private String hablarConRasumijin(Personaje p) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n===================================================================================\n");
        if (!habloConRasumijin) {
            sb.append("Rasumijin te recibe con una palmada brusca en el hombro y una sonrisa genuina:\n")
              .append("'¡Rodión! Llegas justo a tiempo. Tengo unos textos alemanes para traducir, \n")
              .append("nos pagarán bien... podemos salir de esta miseria juntos, hermano'.\n\n")
              .append("Miras los papeles, escuchas su entusiasmo y sientes una náusea intelectual.\n")
              .append("—No quiero tus limosnas, ni tus traducciones —respondes con frialdad—.\n")
              .append("La vitalidad de tu amigo te resulta insultante ante la magnitud de lo que planeas.");

            int agotamientoActual = p.alma.estadoFisico.get("Agotamiento Sensorial");
            p.alma.estadoFisico.put("Agotamiento Sensorial", Math.min(100, agotamientoActual + 20));
            
            habloConRasumijin = true;
            sb.append("\n\nAgotamiento Sensorial: ").append(p.alma.estadoFisico.get("Agotamiento Sensorial"));
        } else {
            sb.append("Rasumijin te observa con preocupación contenida. \n")
              .append("'¿Qué te pasa, Rodión? Estás pálido como un muerto'.");
        }
        sb.append("\n===================================================================================\n");
        return sb.toString();
    }
}