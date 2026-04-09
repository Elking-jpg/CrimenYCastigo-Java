package Escenarios;
import StP.*;

public class JardinYusupov extends Escenario {
    private boolean yaDescanso = false;

    public JardinYusupov() {
        super("Jardin Yusupov", "Un oasis verde en medio del polvo de San Petersburgo. El agua de los estanques refleja un cielo que parece menos hostil aquí.");
    }

    @Override
    public String obtenerAccionesPosibles(Personaje p) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n===================================================================================\n");        
        sb.append("0. Sentarse en un banco a descansar\n");
        sb.append("1. Observar el estanque");
        sb.append("\n===================================================================================\n");
        return sb.toString();
    }

    @Override
    public String ejecutarAccion(int opcion, Personaje p) {
        if (opcion == 0) return descansarEnBanco(p);
        else if (opcion == 1) return observarEstanque(p);
        return "\nNo hay nada más que hacer, Rodion. El tiempo corre.\n";
    }

    private String descansarEnBanco(Personaje p) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n===================================================================================\n");
        if (p.inventario.contains("Hacha")) {
            sb.append("Te sientas, pero el frío del hacha contra tu costado te impide relajarte.\n");
            sb.append("Ves a la gente y te preguntas si podrían adivinar lo que escondes bajo el chaleco.\n");
            if (!yaDescanso) {
                int par = p.alma.estadoPsicologico.get("Paranoia");
                int fie = p.alma.estadoFisico.get("Fiebre");
                p.alma.estadoPsicologico.put("Paranoia", Math.max(0, par - 5));
                p.alma.estadoFisico.put("Fiebre", Math.max(0, fie - 5));
                yaDescanso = true;
            }
            sb.append("Logras calmarte un poco, pero la sospecha te rodea.");
        } else {
            sb.append("Cierras los ojos. El ruido de la ciudad se vuelve un murmullo lejano.\n");
            sb.append("Por un momento, imaginas que podrías marcharte lejos, a un lugar donde nadie te conozca.\n");
            if (!yaDescanso) {
                int par = p.alma.estadoPsicologico.get("Paranoia");
                int fie = p.alma.estadoFisico.get("Fiebre");
                p.alma.estadoPsicologico.put("Paranoia", Math.max(0, par - 15));
                p.alma.estadoFisico.put("Fiebre", Math.max(0, fie - 10));
                yaDescanso = true;
                sb.append("El descanso ha renovado un poco tu cuerpo y tu mente.");
            } else {
                sb.append("Ya has descansado lo suficiente. El aire del jardín empieza a recordarte a la cal de las paredes.");
            }
        }
        sb.append("\n\nParanoia: ").append(p.alma.estadoPsicologico.get("Paranoia"))
          .append("\nFiebre: ").append(p.alma.estadoFisico.get("Fiebre"));
        sb.append("\n===================================================================================\n");
        return sb.toString();
    }

    private String observarEstanque(Personaje p) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n===================================================================================\n");
        if (p.ahMatado) {
            sb.append("Miras el agua turbia. Te preguntas si el Neva lavaría tu alma con la misma facilidad que limpia el polvo.\n");
            sb.append("El reflejo de tu rostro te resulta extraño, como el de un desconocido.");
        } else {
            sb.append("Ves a los cisnes nadar con una elegancia indiferente a la miseria humana.\n");
            sb.append("Te sientes como un insecto mirando un world al que no perteneces.");
        }
        int lucidez = p.alma.estadoPsicologico.get("Lucidez");
        p.alma.estadoPsicologico.put("Lucidez", Math.min(100, lucidez + 5));
        sb.append("\n\nLucidez: ").append(p.alma.estadoPsicologico.get("Lucidez"));
        sb.append("\n===================================================================================\n");
        return sb.toString();
    }
}