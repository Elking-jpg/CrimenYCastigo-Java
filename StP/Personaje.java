package StP;
import java.util.ArrayList;


import StP.HeapAcontecimientos.Handle;

public class Personaje {

    public AlmaPersonaje alma = new AlmaPersonaje() ;
    public ArrayList<String> inventario = new ArrayList<>();

    public String ubicacionActual; 
    public int aparecioEnComisaria = 0;
    public boolean ahMatado = false;
    public boolean llevaChaleco = false;
    public boolean listoParaRedencion = false;
    public boolean atrapado = false;
    public boolean mundoYaActualizado = false;
    public boolean cree;
    public int pasos = 0;

    public Handle<String> hSuicidio;
    public Handle<String> hComisaria;
    public Handle<String> hDelirio;
    public Handle<String> hTeletransporte;

    public boolean enDelirio = false;
    public int pasosEnDelirio = 0;

    public void iniciarPersonaje(boolean cree){

        int esperanza = cree ? 50 : 20;
        int decision = !cree ? 50 : 20;
        
        this.cree = cree;

        alma.estadoPsicologico.put("Paranoia",20);
        alma.estadoPsicologico.put("Esperanza",esperanza);
        alma.estadoPsicologico.put("Decision",decision);
        alma.estadoPsicologico.put("Lucidez",80);

        alma.estadoFisico.put("Fiebre",10);
        alma.estadoFisico.put("Agotamiento Sensorial",10);
        alma.estadoFisico.put("Billetera",50); // 100 kopecks = 1 rublo, se miede en kopeks
        
    }

    public void agregarObjetoAlInventario(String nombre){
        inventario.add(nombre);
    }

    public String mostrarInventario() {
        if (inventario.isEmpty()) {
            return "Tu inventario está vacío. No tenés ni un kopek.";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("En tu inventario tenés:\n");
        
        for (int i = 0; i < inventario.size(); i++) {
            // Le ponemos un numerito al lado para que quede más pro
            sb.append("- ").append(inventario.get(i)).append("\n");
        }
        
        return sb.toString();
    }

    public void mostrarAccionesDeInventario() {
        if (this.inventario.contains("Caja de cigarrillos") && this.pasos % 5 == 0) {
            System.out.println("5. Fumar un cigarrillo");
        }
        if (this.inventario.contains("Cerveza turbia")) {
            System.out.println("6. Tomar cerveza");
        }
    }

    public void usarItem(int opcion) {
        if (opcion == 5) {
            fumarCigarrillo(this);
        } else if (opcion == 6) {
            tomarCerveza(this);
        }
    }

    public String fumarCigarrillo(Personaje personaje) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n===================================================================================\n");
        
        int paranoiaActual = personaje.alma.estadoPsicologico.get("Paranoia");
        // Lógica de Stat: No baja de 0
        int nuevaParanoia = Math.max(0, paranoiaActual - 10); 
        personaje.alma.estadoPsicologico.put("Paranoia", nuevaParanoia);

        sb.append("Ves como el humo se extingue en el aire... te agrada esa sensación.");
        sb.append("\nParanoia: ").append(nuevaParanoia);
        sb.append("\n===================================================================================\n");
        
        personaje.pasos++;
        return sb.toString();
    }

    public String tomarCerveza(Personaje personaje) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n===================================================================================\n");

        int paranoiaActual = personaje.alma.estadoPsicologico.get("Paranoia");
        int lucidezActual = personaje.alma.estadoPsicologico.get("Lucidez");

        int nuevaParanoia = Math.min(100, paranoiaActual + 5);
        int nuevaLucidez = Math.max(0, lucidezActual - 5);

        personaje.alma.estadoPsicologico.put("Paranoia", nuevaParanoia);
        personaje.alma.estadoPsicologico.put("Lucidez", nuevaLucidez);

        sb.append("Sientes una amargura en la garganta... estás un poco más intranquilo.");
        sb.append("\nParanoia: ").append(nuevaParanoia);
        sb.append("\nLucidez: ").append(nuevaLucidez);
        sb.append("\n===================================================================================\n");
        
        personaje.inventario.remove("Cerveza turbia");
        return sb.toString();
    }

    public String finalesAlternativos(){

        StringBuilder sb = new StringBuilder();
        sb.append("\n===================================================================================\n");
        sb.append("La puerta de la celda se cierra con un estrépito metálico que resuena en la eternidad. \n");
        sb.append("No hay cánticos, ni Biblias, ni rostros amados esperando en la estepa siberiana. \n");
        sb.append("Te sientas en el jergón frío y comprendes, con una lucidez lacerante, tu gran error: \n");
        sb.append("te has sacrificado en el altar de una soberbia estéril, una aritmética del alma \n");
        sb.append("que solo ha producido ceros. Has jugado a ser un arquitecto del destino y has terminado \n");
        sb.append("siendo solo un inquilino de la nada, un piojo que se creyó Napoleón solo para descubrir \n");
        sb.append("que ni siquiera sabía sostener el peso de su propia existencia. \n\n");
        sb.append("Es notable, Rodion Románovich, cómo un intelecto tan capaz ha preferido el laberinto \n");
        sb.append("de la culpa solitaria al sencillo camino de la verdad compartida. \n");
        sb.append("Has gastado tu vida intentando demostrar un punto que a nadie le importa, \n");
        sb.append("olvidando que el genio sin espíritu no es más que una curiosidad patética del fango.");
        sb.append("\n===================================================================================\n");
        return sb.toString();
    }

    public String AparicionEnComisaria(int Numero){

        StringBuilder sb = new StringBuilder();

        if (Numero == 1){

            sb.append("\n===================================================================================\n");
            sb.append("Parpadeas y el aire viciado de la calle desaparece, reemplazado por el olor a \n");
            sb.append("tinta barata y sudor burocrático. El espacio se ha plegado bajo el peso de tu angustia. \n");
            sb.append("De pronto, no hay calles, solo este techo alto y gris que parece colapsar sobre ti. \n\n");
            sb.append("Levantas la vista y, tras una montaña de expedientes amarillentos, \n");
            sb.append("la sonrisa de Porfirio Petrovich te recibe como si nunca te hubieras ido. \n");
            sb.append("—¡Pero si es usted, Rodion Románovich! —exclama con una alegría que hiela la sangre—. \n\n");                            sb.append("—Es fascinante cómo funciona una mente tan brillante como la suya. \n");
            sb.append("Usted no ha caminado hasta aquí, ha sido su propia claridad la que lo ha traicionado. \n");
            sb.append("¿No lo ve? Su razón es tan perfecta, tan implacable, que no puede tolerar el caos de la libertad. \n");
            sb.append("Su inteligencia ha trazado el mapa y sus pies, siendo esclavos de esa lógica superior, \n");
            sb.append("no han tenido más remedio que traerlo ante mí. \n");
            sb.append("Dígame, ¿el aire de la libertad le resultaba ya demasiado pesado para su privilegiado cerebro?");
            sb.append("\n===================================================================================\n");

        } else {

            sb.append("\n===================================================================================\n");
            sb.append("El mundo se desgarra de nuevo. El aire frío del río se convierte, en un latido, \n");
            sb.append("en el calor estancado de la oficina de Porfirio. \n");
            sb.append("Esta vez, el mareo es más amargo; sientes que no eres tú quien se mueve, \n");
            sb.append("sino que la realidad misma te escupe de vuelta a este lugar. \n\n");
            sb.append("Porfirio Petrovich ni siquiera levanta la vista al principio. Sigue mojando su pluma \n");
            sb.append("en el tintero con una calma que te crispa los nervios. \n\n");
            sb.append("—Vaya, vaya... Rodion Románovich. De nuevo por aquí —dice, dejando la pluma con cuidado—. \n");
            sb.append("Esto ya no es una coincidencia, ¿verdad? Es casi una ley física. \n");
            sb.append("Su lucidez es tan implacable que lo atrae al lugar del examen como una polilla a la luz. \n");
            sb.append("Usted cree que huye, pero su cerebro, ese motor magnífico y honesto, \n");
            sb.append("sabe que aquí es donde debe estar. Sabe que su lógica no estará completa \n");
            sb.append("hasta que me cuente lo que esa misma lógica lo obliga a confesar. \n\n");
            sb.append("—Dígame, mi querido amigo... ¿No empieza a sentirse cansado de ser tan inteligente? \n");
            sb.append("Debe ser agotador tener una razón que lo traiciona a cada paso, \n");
            sb.append("recordándole que, por mucho que corra, su lugar está frente a mi escritorio.");
            sb.append("\n===================================================================================\n");

        }

        return sb.toString();
    }
    
    public String entradaAlDelirio(){

        StringBuilder sb = new StringBuilder();
        sb.append("\n===================================================================================\n");
        sb.append("El mundo se vuelve una mancha amarilla y espesa. \n");
        sb.append("El techo de San Petersburgo baja hasta rozar tu frente, sudada y ardiendo. \n\n");
        sb.append("No hay calles, solo un pasillo infinito que huele a sangre seca y cal. \n");
        sb.append("Escuchas el eco de unos pasos que no son los tuyos, \n");
        sb.append("y una risa de rata que te susurra desde el fondo de tu propio cráneo: \n\n");
        sb.append("—¿A dónde vas, Rodion? ¿Acaso una cucaracha tiene a dónde huir? \n\n");
        sb.append("Tus piernas pesan como el hierro. Solo puedes arrastrarte en la niebla.");
        sb.append("\n===================================================================================\n");
        return sb.toString();
    }

    // __________________________HE_p

    public void inicializarCrisis(HeapAcontecimientos<String> heap) {
        hSuicidio = heap.insert("Suicidio", 0);
        hComisaria = heap.insert("Comisaria", 0);
        hDelirio = heap.insert("Delirio", 0);
        hTeletransporte = heap.insert("Teletransporte", 0);
    }

    public void recalibrarDelirios(HeapAcontecimientos<String> heap) {
        if (!ahMatado) return;

        int lucidez = alma.estadoPsicologico.get("Lucidez");
        int paranoia = alma.estadoPsicologico.get("Paranoia");
        int fiebre = alma.estadoFisico.get("Fiebre");

        heap.changeKey(hComisaria, 100 - lucidez);
        heap.changeKey(hTeletransporte, paranoia);
        heap.changeKey(hDelirio, fiebre);

        if (lucidez + paranoia + fiebre >= 265) {
            heap.changeKey(hSuicidio, 101);
        } else {
            heap.changeKey(hSuicidio, 0);
        }
    }

    public String eventoTeletransporte() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n===================================================================================\n");
        sb.append("!!! DISOCIACIÓN PARANOIA !!!\n\n");
        sb.append("Sientes mil ojos clavados en tu nuca. Las paredes de San Petersburgo se contraen \n");
        sb.append("y las sombras de los transeúntes parecen señalarte. El pánico te nubla la vista. \n\n");
        sb.append("Corres sin rumbo, doblando esquinas al azar para escapar de una persecución que \n");
        sb.append("solo existe en tu cerebro. Cuando recuperas el aliento, el mundo es distinto. \n");
        sb.append("No sabes dónde estás, ni cuánto tiempo has estado huyendo de nada.");
        sb.append("\n===================================================================================\n");
        return sb.toString();
    }

    public String eventoSuicidio() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n===================================================================================\n");
        sb.append("!!! COLAPSO ABSOLUTO !!!\n\n");
        sb.append("La suma de tus pecados, tu fiebre y tu miedo ha quebrado el último soporte de tu razón. \n");
        sb.append("Ya no hay 'hombres superiores' ni 'cucarachas'. Solo queda un ruido sordo en tus oídos. \n\n");
        sb.append("Caminas hacia el pretil del puente como un autómata. El agua del Neva, negra y \n");
        sb.append("gélida, te invita a silenciar las voces para siempre. \n\n");
        sb.append("—Lo siento —susurras al vacío—. No eras Napoleón después de todo.");
        sb.append("\n===================================================================================\n");
        return sb.toString();
    }

}
