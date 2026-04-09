package Escenarios;
import StP.*;

public class Comisaria extends Escenario {
    private boolean deudaPagada = false;

    public Comisaria() {
        super("Comisaria", "Un edificio gris de techos altos. El olor a papel viejo es asfixiante.");
    }

    @Override
    public String obtenerAccionesPosibles(Personaje p) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n===================================================================================\n");
        if (!deudaPagada) sb.append("0. Pagar deuda de alquiler (40 kopeks)\n");
        if (p.ahMatado) sb.append("1. Confesar el crimen ante Porfirio Petrovich");
        sb.append("\n===================================================================================\n");
        return sb.toString();
    }

    @Override
    public String ejecutarAccion(int opcion, Personaje p) {
        if (opcion == 0 && !deudaPagada) return gestionarDeuda(p);
        if (opcion == 1 && p.ahMatado) { p.atrapado = true; return procesarConfesion(p); }
        return "\nPorfirio Petrovich sigue escribiendo, ignorándote.\n";
    }

    private String gestionarDeuda(Personaje p) {
        int dinero = p.alma.estadoFisico.get("Billetera");
        StringBuilder sb = new StringBuilder();
        sb.append("\n===================================================================================\n");
        if (!p.ahMatado) {
            if (dinero >= 40) {
                p.alma.estadoFisico.put("Billetera", dinero - 40);
                deudaPagada = true;
                sb.append("Entregas los 40 kopeks. El oficial anota el pago con indiferencia.");
                sb.append("\nBilletera: ").append(p.alma.estadoFisico.get("Billetera") + " kopeks");
            } else {
                sb.append("No tienes los 40 kopeks. 'Vuelva cuando sea productivo, Raskólnikov'.");
            }
        } else {
            int ParanoiaActual = p.alma.estadoPsicologico.get("Paranoia");
            p.alma.estadoPsicologico.put("Paranoia", Math.min(100, ParanoiaActual + 15));

            deudaPagada = true;
            sb.append("Intentas pagar la deuda con manos temblorosas. \n");
            sb.append("Petrovich deja de escribir, se inclina hacia adelante y te clava la mirada: \n");
            sb.append("—¿Por qué está tan pálido, Rodión Románovich? ¿Acaso le asustan los papeles... o es el aire de esta oficina?\n");
            sb.append("La paranoia te golpea como un mazo. Sientes que él lo sabe todo.");

            sb.append("\n\nParanoia: " + p.alma.estadoPsicologico.get("Paranoia"));
        }
        sb.append("\n===================================================================================\n");
        return sb.toString();
    }

    private String procesarConfesion(Personaje p) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n===================================================================================\n");
        if (p.listoParaRedencion) {
            sb.append("Te adelantas hacia la mesa, sintiendo que el suelo se inclina bajo tus pies. \n");
            sb.append("—¡Fui yo! —gritas con una voz que no parece tuya, rompiendo el zumbido de la oficina—.\n");
            sb.append("—¡Yo maté a la vieja funcionaria y a su hermana Lizaveta con un hacha, y las robé!\n\n");
            sb.append("El silencio que sigue es absoluto. Porfirio Petrovich te mira, no con triunfo, sino con una serena compasión.\n\n");
            sb.append("Años después, el paisaje ha cambiado. Ya no hay edificios amarillentos ni aire viciado.\n");
            sb.append("Estás en las orillas de un río ancho y desierto en Siberia. El sol brilla sobre la estepa infinita.\n");
            sb.append("Sonia está sentada a tu lado; ella no te dice nada, solo te toma la mano, y en ese contacto \n");
            sb.append("comprendes que tu condena ha terminado y tu nueva vida comienza. \n\n");
            sb.append("Bajo tu almohada descansa el Nuevo Testamento que ella te dio. Aún no lo has abierto. \n");
            sb.append("Siete años... solo parecen siete días. El hombre ha nacido en el sufrimiento, \n");
            sb.append("y ante ti se abre la historia de una renovación gradual, el paso de un mundo a otro.");
        } else {
            sb.append("Confiesas con una frialdad cadavérica. No hay lágrimas, solo un cansancio infinito. \n");
            sb.append("Petrovich asiente, casi con lástima. No hay una Sonia esperándote afuera. \n\n");
            sb.append("La celda se convierte en tu mundo. Pasan los años y tu teoría se vuelve veneno. \n");
            sb.append("Una noche, el silencio de la prisión es demasiado. \n");
            sb.append("No hay resurrección para quien no cree en su propia humanidad. El vacío termina por reclamarte.");
        }
        sb.append("\n===================================================================================\n");
        return sb.toString();
    }
}