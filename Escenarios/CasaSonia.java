package Escenarios;
import StP.Escenario;
import StP.Personaje;

public class CasaSonia extends Escenario {
    private boolean capituloLeido = false;
    private boolean yaHabloSonia = false;

    public CasaSonia() {
        super("Casa de Sonnya", 
              "Una habitación de aspecto irregular, casi una choza, con paredes amarillentas \n" +
              "y un silencio sepulcral que parece juzgar a quien entra.");
    }

    @Override
    public String obtenerAccionesPosibles(Personaje p) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n===================================================================================\n");
        
        if (!p.ahMatado) {
            sb.append("0. Hablar con Sonia sobre la miseria");
        } else {
            if (!capituloLeido) {
                sb.append("1. Pedirle que lea el capítulo de la resurrección de Lázaro\n");
            }
            
            if (!p.listoParaRedencion && !p.inventario.contains("Hacha")) {
                sb.append("2. Confesar el crimen y pedir redención");
            }
            
            if (capituloLeido && p.listoParaRedencion) {
                sb.append("El Nuevo Testamento descansa sobre la mesa. Sonia te mira con una compasión que duele.");
            }
        }
        
        sb.append("\n===================================================================================\n");
        return sb.toString();
    }

    @Override
    public String ejecutarAccion(int opcion, Personaje p) {
        if (opcion == 0 && !p.ahMatado) {
            return conversacionPreliminar(p);
        }
        
        if (opcion == 1 && p.ahMatado && !capituloLeido) {
            return leerCapituloLazaro(p);
        }
        
        if (opcion == 2 && p.ahMatado && !p.listoParaRedencion && !p.inventario.contains("Hacha")) {
            return confesionYRedencion(p);
        }

        return "\nEl silencio entre ambos es lo único que queda.\n";
    }

    private String conversacionPreliminar(Personaje p) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n===================================================================================\n");
        if (!yaHabloSonia) {
            sb.append("Sonia te mira con sus ojos azules, llenos de un miedo manso. \n")
              .append("Hablas con ella sobre el sacrificio, sobre cómo se vende para salvar a los suyos. \n")
              .append("Su fe inquebrantable te desarma, te hace sentir pequeño y humano.\n\n");

            int esp = p.alma.estadoPsicologico.get("Esperanza");
            int dec = p.alma.estadoPsicologico.get("Decision");
            
            if(esp + 15 <= 100) p.alma.estadoPsicologico.put("Esperanza", esp + 15);
            if(dec - 20 >= 0) p.alma.estadoPsicologico.put("Decision", dec - 20);

            sb.append("Sientes una extraña calidez. \n")
              .append("Esperanza: ").append(p.alma.estadoPsicologico.get("Esperanza"))
              .append("\nDecision: ").append(p.alma.estadoPsicologico.get("Decision"));
            yaHabloSonia = true;
        } else {
            sb.append("Te quedas mirándola en silencio. Sonia baja la vista, intimidada.");
        }
        sb.append("\n===================================================================================\n");
        return sb.toString();
    }

    private String leerCapituloLazaro(Personaje p) {
        capituloLeido = true;
        StringBuilder sb = new StringBuilder();
        sb.append("\n===================================================================================\n");
        sb.append("Sonia toma el libro viejo y manoseado de la mesa. Sus dedos tiemblan al pasar las páginas, \n")
          .append("pero su voz cobra una fuerza sobrenatural al comenzar a leer:\n\n")
          .append("'...Jesús le dijo: Yo soy la resurrección y la vida; el que cree en mí, \n")
          .append("aunque esté muerto, vivirá.'\n\n")
          .append("Las palabras golpean tu fiebre como un martillo. Sientes que algo muerto en ti se agita.\n");
        
        int paranoia = p.alma.estadoPsicologico.get("Paranoia");
        p.alma.estadoPsicologico.put("Paranoia", Math.max(0, paranoia - 20));
        
        sb.append("\nLa paranoia disminuye. Sientes que hay un camino, aunque sea doloroso.");
        sb.append("\n===================================================================================\n");
        return sb.toString();
    }

    private String confesionYRedencion(Personaje p) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n===================================================================================\n");
        
        if (!capituloLeido) {
            sb.append("Intentas hablar, pero las palabras se traban en tu garganta seca. \n")
              .append("Sonia te pone una mano en el hombro y señala el libro sobre la mesa. \n")
              .append("—Rodion Románovich... primero debe escuchar la Palabra. Solo así entenderá.");
            sb.append("\n===================================================================================\n");
            return sb.toString();
        }

        sb.append("Te desplomas ante ella, tus rodillas golpean el suelo con un sonido seco. \n")
          .append("—He matado... —susurras con la voz rota—. He matado a un piojo, pero me he matado a mí mismo.\n\n")
          .append("Sonia se horroriza, pero no se aparta. Te rodea con sus brazos, llorando contigo.\n")
          .append("—¡Qué has hecho contigo! —exclama—. Pero ahora bésala... bésala.\n\n")
          .append("—Ve a la plaza, bésala y di a todo el mundo: 'He matado'. Luego, entrégate.");

        p.listoParaRedencion = true;
        p.alma.estadoPsicologico.put("Esperanza", 100);
        p.alma.estadoPsicologico.put("Paranoia", 0);
        p.alma.estadoPsicologico.put("Lucidez", 100);
        p.alma.estadoFisico.put("Fiebre", 0);
        p.alma.estadoFisico.put("Agotamiento Sensorial", 0);

        sb.append("\n\nSientes el peso del mundo caer. Esperanza al máximo. Estás listo.");
        sb.append("\n===================================================================================\n");
        return sb.toString();
    }
}