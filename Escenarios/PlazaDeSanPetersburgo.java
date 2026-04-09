package Escenarios;
import StP.*;

public class PlazaDeSanPetersburgo extends Escenario {
    private boolean conversacionEscuchada = false;
    private boolean flag = true;

    public PlazaDeSanPetersburgo() {
        super("Plaza de San Petersburgo", "La plaza está abarrotada. Comerciantes gritan sus precios y el hedor a multitud es sofocante.\nVes a lo lejos a Lizaveta, la hermana de la usurera, hablando con unos mercaderes.");
    }

    @Override
    public String obtenerAccionesPosibles(Personaje p) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n===================================================================================\n");
        sb.append(this.descripcion);
        sb.append("\n===================================================================================\n");
        sb.append("0. Acercarse a escuchar a Lizaveta\n").append("1. Observar a los comerciantes");
        return sb.toString();
    }

    @Override
    public String ejecutarAccion(int opcion, Personaje p) {
        if (opcion == 0) return escucharConversacionPrivada(p);
        else if (opcion == 1) return verComerciantes(p);
        return "\nOpción no válida, Rodion.\n";
    }

    private String escucharConversacionPrivada(Personaje p) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n===================================================================================\n");
        if (!conversacionEscuchada) {
            sb.append("Te ocultas tras un carruaje. Escuchas a Lizaveta decir: 'Estaré allí a las siete... mi hermana se quedará sola en casa'.\n");
            sb.append("Una chispa eléctrica recorre tu columna. El destino te está sirviendo la oportunidad en bandeja de plata.\n");
            int dec = p.alma.estadoPsicologico.get("Decision");
            p.alma.estadoPsicologico.put("Decision", Math.min(100, dec + 30));
            sb.append("Tu Decisión aumenta considerablemente..");
            conversacionEscuchada = true;
        } else {
            sb.append("Lizaveta ya se ha marchado. Las palabras 'a las siete' siguen martillando tu cráneo.");
        }
        sb.append("\n\nDecision: ").append(p.alma.estadoPsicologico.get("Decision"));
        sb.append("\n===================================================================================\n");
        return sb.toString();
    }

    private String verComerciantes(Personaje p) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n===================================================================================\n");
        sb.append("Ves una marea de gente regateando por centavos, rostros sudorosos y gritos estridentes.\n");
        sb.append("El ruido constante y el movimiento frenético saturan tus sentidos.\n");
        if (flag){ 
            int agot = p.alma.estadoFisico.get("Agotamiento Sensorial");
            p.alma.estadoFisico.put("Agotamiento Sensorial", Math.min(100, agot + 15));
            flag = false;
        }
        sb.append("\n\nAgotamiento Sensorial: ").append(p.alma.estadoFisico.get("Agotamiento Sensorial"));
        sb.append("\n===================================================================================\n");
        return sb.toString();
    }
}