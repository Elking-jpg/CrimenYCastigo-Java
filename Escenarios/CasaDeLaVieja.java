package Escenarios;
import StP.Escenario;
import StP.Personaje;

public class CasaDeLaVieja extends Escenario {
    
    public CasaDeLaVieja() {
        super("Casa De Alyona Ivanovna", 
              "La fachada es alta y gris, con ventanas que parecen ojos vigilantes. \n" +
              "El portal es un túnel oscuro que huele a rancio; los peldaños están \n" +
              "desgastados por miles de pasos invisibles.");
    }

    @Override
    public String obtenerAccionesPosibles(Personaje p) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n===================================================================================\n");

        if (!p.ahMatado) {
            sb.append("0. Saludar a Alyona");
            if (p.alma.estadoPsicologico.get("Decision") >= 100 && p.inventario.contains("Hacha") && yaHablaron) {
                sb.append("\n7. Matar a Alyona");
            }
        } else {
            sb.append("1. Ver el lugar");   
        }

        sb.append("\n===================================================================================\n");
        return sb.toString();
    }

    @Override
    public String ejecutarAccion(int opcion, Personaje p) {
        if (opcion == 0 && !p.ahMatado) {
            return saludarAlyona(p);
        } else if (opcion == 1 && p.ahMatado) {
            return verElLugar(p);
        } else if (opcion == 7 && !p.ahMatado && p.alma.estadoPsicologico.get("Decision") >= 100 && yaHablaron) {
            return matarAlyona(p);
        } else {
            return "\nNo hay nada más que ver aquí, Rodion.\n";
        }
    }

    public String matarAlyona(Personaje p) {
        p.ahMatado = true;
        p.pasos = 1;

        int fiebreActual = p.alma.estadoFisico.get("Fiebre");
        p.alma.estadoFisico.put("Fiebre", Math.min(100, fiebreActual + 10));

        p.alma.estadoPsicologico.put("Paranoia", 100);
        p.alma.estadoPsicologico.put("Esperanza", 10);
        p.alma.estadoPsicologico.put("Lucidez", 10);
        p.alma.estadoPsicologico.put("Decision", 10);
        
        StringBuilder sb = new StringBuilder();
        sb.append("\n===================================================================================\n");
        sb.append("La puerta se abre apenas un resquicio. Esos ojos de rata te observan tras la cadena.\n")
          .append("Al entrar, el aire te golpea con un olor a cera y a limpieza rancia.\n\n")
          .append("Ella se da la vuelta... El hacha sale del chaleco casi sin peso.\n")
          .append("El golpe cae de arriba abajo, seco, sordo, contra la frente de la vieja.\n")
          .append("Se desploma como un fardo de ropa vieja. Un gemido que se ahoga en la alfombra.\n\n")
          .append("No eres un Napoleón, Rodion; eres un carnicero temblando en una habitación llena de sol.\n")
          .append("-----------------------------------------------------------------------------------\n")
          .append("¡Lizaveta está en la entrada! Se queda de pie, mirando el cuerpo de su hermana.\n")
          .append("No grita. Solo retrocede un paso, con una expresión de espanto infinito.\n")
          .append("El pánico te nubla la vista. El segundo hachazo es torpe, desesperado.\n\n")
          .append("Has matado al piojo, pero también a la santa. \n")
          .append("La sangre de ambas se mezcla en el mismo suelo amarillento.");
        
        sb.append("\n\nFiebre: ").append(p.alma.estadoFisico.get("Fiebre"))
          .append("\nParanoia: 100\nEsperanza: 10\nLucidez: 10");
        sb.append("\n===================================================================================\n"); 
        
        return sb.toString();
    }

    boolean yaHablaron = false;

    public String saludarAlyona(Personaje p) {
        yaHablaron = true;
        p.alma.estadoPsicologico.put("Decision", 100);
        StringBuilder sb = new StringBuilder();
        sb.append("\n===================================================================================\n");

        if(p.inventario.contains("Hacha")){
            sb.append("Cada escalón parece una sentencia. El amarillo de las paredes te agrede la vista.\n")
              .append("Sientes el hierro del hacha contra tu costilla, un peso con voluntad propia.\n")
              .append("¿Vas a temblar como un insecto o vas a demostrar tu derecho de gran hombre?\n\n")
              .append("—¿Otra vez usted, Raskólnikov? —sisea la vieja—. \n")
              .append("Traiga algo que valga la pena o lárguese. Su vida no vale ni un kopek para mí.");
        } else {
            sb.append("Subes la escalera con paso pesado. Vacío. Sin el peso oculto bajo el gabán.\n")
              .append("Al llegar a la puerta no sientes el vértigo del poder, sino la amargura del ruego.\n")
              .append("Tus manos vacías buscan el frío metal de un reloj de cobre en el bolsillo.\n\n")
              .append("—¿Viene vacío? —pregunta ella con burla—. \n")
              .append("Vuelva cuando tenga algo que brille, o deje de ensuciar mi alfombra.");
        }

        sb.append("\n\nDecision: 100");
        sb.append("\n===================================================================================\n");
        return sb.toString();
    }

    public String verElLugar(Personaje p) {
        int paranoiaActual = p.alma.estadoPsicologico.get("Paranoia");
        p.alma.estadoPsicologico.put("Paranoia", Math.max(0, paranoiaActual - 20));

        StringBuilder sb = new StringBuilder();
        sb.append("\n===================================================================================\n");
        sb.append("Te detienes ante la puerta que era de Alyona. \n")
          .append("Tiras de la campana de hojalata. El tintineo rasga el silencio como un grito.\n")
          .append("Esperas que la realidad se restaure, pero solo recibes el eco vacío de tu golpe.\n")
          .append("Ese timbre ya no llama a nadie; solo anuncia tu propia condena.");
        
        sb.append("\n\nParanoia: ").append(p.alma.estadoPsicologico.get("Paranoia"));
        sb.append("\n===================================================================================\n");
        
        return sb.toString();
    }
}