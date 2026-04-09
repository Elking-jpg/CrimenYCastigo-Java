package Escenarios;
import StP.*;

public class Avenida extends Escenario {
    private boolean avenidaYaDioBono = false;

    public Avenida() {
        super("Avenida", "El corazón de la ciudad. El ruido de los carruajes te marea.");
    }

    @Override
    public String obtenerAccionesPosibles(Personaje p) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n===================================================================================\n");
        if (!p.ahMatado) {
            sb.append("0. Observar a la multitud");
        } else {
            sb.append("0. Mirar transeúntes");
        }
        sb.append("\n===================================================================================\n");
        return sb.toString();
    }

    @Override
    public String ejecutarAccion(int opcion, Personaje p) {
        if (opcion == 0) {
            return observarMultitud(p);
        } else {
            return "\nNo hay nada más que ver aquí, Rodion.\n";
        }
    }

    private String observarMultitud(Personaje p) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n===================================================================================\n");
        if (!p.ahMatado) {
            sb.append("Ves rostros miserables, gente arrastrándose por la vida sin un propósito real...\n");
            if (!p.cree && !avenidaYaDioBono) {
                sb.append("La falta de originalidad incluso para la vanalidad ordinaria no te sorprende..\n");
                int decisionActual = p.alma.estadoPsicologico.get("Decision");
                p.alma.estadoPsicologico.put("Decision", Math.min(100, decisionActual + 5));
                avenidaYaDioBono = true; 
                sb.append("\nDecision: ").append(p.alma.estadoPsicologico.get("Decision"));
            } else if (p.cree) {
                sb.append("Sientes una punzada de compasión y pena, pero te obligas a apartar la mirada.");
            } else {
                sb.append("Ya has procesado esta miseria antes. No hay nada nuevo que concluir.");
            }
        } else {
            sb.append("No ves personas, solo centenares de pares de ojos que parecen taladrar tu espalda.");
            int paranoiaActual = p.alma.estadoPsicologico.get("Paranoia");
            p.alma.estadoPsicologico.put("Paranoia", Math.min(100, paranoiaActual + 5));
            sb.append("\nParanoia: ").append(p.alma.estadoPsicologico.get("Paranoia"));
        }
        sb.append("\n===================================================================================\n");
        return sb.toString();
    }
}