package Escenarios;
import StP.*;

public class Escalera extends Escenario {
    private boolean porteriaVista = false;

    public Escalera() {
        super("Escalera", "Está oscuro. Se oyen gritos en el piso de abajo.");
    }

    @Override
    public String obtenerAccionesPosibles(Personaje p) {
        StringBuilder sb = new StringBuilder();
        if (!p.ahMatado) {
            if (p.alma.estadoPsicologico.get("Decision") > 70 && p.llevaChaleco && !p.inventario.contains("Hacha")) {
                sb.append("0. Tomar el hacha");
            } else {
                sb.append("0. Mirar habitación del portero");
            }
        } else { sb.append("..."); }
        return sb.toString();
    }

    @Override
    public String ejecutarAccion(int opcion, Personaje p) {
        if (!p.ahMatado) {
            if (opcion == 0 && p.alma.estadoPsicologico.get("Decision") > 70 && p.llevaChaleco && !p.inventario.contains("Hacha")) return agarrarElHacha(p);
            else if (opcion == 0) return verPorteria(p);
        }
        return " No hay nada más que ver aquí...";
    }

    public String agarrarElHacha(Personaje p) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n===================================================================================\n");

        p.inventario.add("Hacha");
        int dec = p.alma.estadoPsicologico.get("Decision");
        p.alma.estadoPsicologico.put("Decision", Math.min(100, dec + 5));
        sb.append("Escondes el hacha entre tu chaleco. Ya no puedes pensar con claridad..\nDecision: " + p.alma.estadoPsicologico.get("Decision"));
        sb.append("\n===================================================================================\n");

        return sb.toString();

    }

    public String verPorteria(Personaje p) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n===================================================================================\n");
        if (!p.ahMatado && !p.inventario.contains("Hacha")) {
            sb.append("Ves un hacha roja con sólido mango de madera. Tu cuerpo se estremece, pero la ignoras..");
            if (!porteriaVista) {
                int dec = p.alma.estadoPsicologico.get("Decision");
                p.alma.estadoPsicologico.put("Decision", Math.min(100, dec + 10));
                porteriaVista = true;
                sb.append("\nDecision: ").append(p.alma.estadoPsicologico.get("Decision"));
            }
        }
        sb.append("\n===================================================================================\n");
        return sb.toString();
    }
}