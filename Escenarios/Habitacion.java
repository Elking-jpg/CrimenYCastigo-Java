package Escenarios;
import StP.*;

public class Habitacion extends Escenario {
    private boolean ventanaMirada = false;
    private boolean cajaTomada = false;

    public Habitacion() {
        super("Habitacion", "Un cuarto sofocante. Las paredes parecen cerrarse sobre ti.");
    }

    @Override
    public String obtenerAccionesPosibles(Personaje p) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n===================================================================================\n");
        sb.append("0. Ver por la ventana\n");
        if (!p.ahMatado && !p.llevaChaleco) sb.append("1. Ponerse el chaleco de lana\n");
        if (!p.inventario.contains("Caja de cigarrillos") && !cajaTomada) sb.append("2. Guardar la caja de cigarrillos");
        sb.append("\n===================================================================================\n");
        return sb.toString();
    }

    @Override
    public String ejecutarAccion(int opcion, Personaje p) {
        switch (opcion) {
            case 0: return verVentana(p);
            case 1: if (!p.ahMatado && !p.llevaChaleco) return ponerseChaleco(p); break;
            case 2: if (!cajaTomada) return agarrarCaja(p); break;
        }
        return "\nNo hay nada más que hacer aquí.\n";
    }

    private String verVentana(Personaje p) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n===================================================================================\n");
        if (!p.ahMatado) {
            sb.append("Ves el sol pegando en la vereda y un viejo jugando con unas ratas.\n");
            sb.append("La luz te da un respiro momentáneo; afuera, la vida sigue su curso indiferente.");
            if (!ventanaMirada) {
                int esp = p.alma.estadoPsicologico.get("Esperanza");
                p.alma.estadoPsicologico.put("Esperanza", Math.min(100, esp + 5));
                ventanaMirada = true;
                sb.append("\nEsperanza: ").append(p.alma.estadoPsicologico.get("Esperanza"));
            }
        } else {
            sb.append("La luz te deslumbra y te hiere los ojos. Ves multitudes que parecen vigilarte.\n");
            sb.append("Crees reconocer la figura de Porfirio Petrovich en cada esquina, acechando tu portal.");
            if (!ventanaMirada) {
                int par = p.alma.estadoPsicologico.get("Paranoia");
                p.alma.estadoPsicologico.put("Paranoia", Math.min(100, par + 20));
                ventanaMirada = true;
                sb.append("\nParanoia: ").append(p.alma.estadoPsicologico.get("Paranoia"));
            }
        }
        sb.append("\n===================================================================================\n");
        return sb.toString();
    }

    private String ponerseChaleco(Personaje p) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n===================================================================================\n");

        p.llevaChaleco = true;
        int dec = p.alma.estadoPsicologico.get("Decision");
        p.alma.estadoPsicologico.put("Decision", Math.min(100, dec + 10));
        sb.append("Te pones el chaleco. Sientes un espasmo de determinación.\nDecision: " + p.alma.estadoPsicologico.get("Decision"));
        sb.append("\n===================================================================================\n");

        return sb.toString();
    }

    private String agarrarCaja(Personaje p) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n===================================================================================\n");
        p.inventario.add("Caja de cigarrillos");
        cajaTomada = true;

        sb.append("Guardas la caja de cigarrillos. Tus dedos rozan el cartón con ansiedad.");
        sb.append("\n===================================================================================\n");

        return sb.toString();

    }
}