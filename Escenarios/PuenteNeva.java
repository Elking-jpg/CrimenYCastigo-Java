package Escenarios;
import StP.*;

public class PuenteNeva extends Escenario {
    public PuenteNeva() {
        super("Puente sobre el Neva", "El panorama es espléndido. El agua centellea bajo el sol y, a lo lejos, la cúpula de la catedral brilla como oro puro. El aire aquí es más frío, más limpio, pero también más cruel.");
    }

    @Override
    public String obtenerAccionesPosibles(Personaje p) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n===================================================================================\n");
        sb.append("0. Contemplar el panorama del Palacio\n");
        if (p.alma.estadoFisico.get("Billetera") > 0 || p.inventario.contains("Kopek")) {
            sb.append("1. Arrojar un kopek al agua");
        }
        sb.append("\n===================================================================================\n");
        return sb.toString();
    }

    @Override
    public String ejecutarAccion(int opcion, Personaje p) {
        if (opcion == 0) return contemplarPanorama(p);
        else if (opcion == 1) return arrojarMoneda(p);
        return "\nEl viento te empuja a seguir caminando, Rodión.\n";
    }

    private String contemplarPanorama(Personaje p) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n===================================================================================\n");
        sb.append("Miras la majestuosidad de los edificios y el azul profundo del río. \n");
        sb.append("Sientes que un abismo infranqueable te separa de toda esa armonía.\n");
        int luc = p.alma.estadoPsicologico.get("Lucidez");
        int agot = p.alma.estadoFisico.get("Agotamiento Sensorial");
        p.alma.estadoPsicologico.put("Lucidez", Math.min(100, luc + 10));
        p.alma.estadoFisico.put("Agotamiento Sensorial", Math.min(100, agot + 15));
        sb.append("\nTu Lucidez aumenta, pero el esfuerzo de procesar la realidad te agota.");
        sb.append("\n\nLucidez: ").append(p.alma.estadoPsicologico.get("Lucidez"))
          .append("\nAgotamiento Sensorial: ").append(p.alma.estadoFisico.get("Agotamiento Sensorial"));
        sb.append("\n===================================================================================\n");
        return sb.toString();
    }

    private String arrojarMoneda(Personaje p) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n===================================================================================\n");
        int dinero = p.alma.estadoFisico.get("Billetera");
        boolean teniaObjeto = p.inventario.remove("Kopek");
        if (teniaObjeto || dinero > 0) {
            if (!teniaObjeto) { p.alma.estadoFisico.put("Billetera", dinero - 1); }
            int esp = p.alma.estadoPsicologico.get("Esperanza");
            p.alma.estadoPsicologico.put("Esperanza", Math.max(0, esp - 5));
            sb.append("Sacas el kopek y lo dejas caer al agua. \nVes el pequeño destello metálico antes de que el Neva lo devore.\nNo es una ofrenda, es un desprecio a la supervivencia.\nHas perdido un kopek y un poco más de esperanza.");
        } else {
            sb.append("Buscas en tus bolsillos, pero no hay nada que entregarle al río.");
        }
        sb.append("\n\nEsperanza: ").append(p.alma.estadoPsicologico.get("Esperanza"))
          .append("\nBilletera: ").append(p.alma.estadoFisico.get("Billetera"));
        sb.append("\n===================================================================================\n");
        return sb.toString();
    }
}