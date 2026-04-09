package StP;


public class Main {
    public static void main(String[] args) {
        // Para la visualización
        VentanaJuego ventana = new VentanaJuego();
        
        // Se crea la ciudad (grafo)
        Ciudad sanPetesburgo = new Ciudad();
        sanPetesburgo.generarMapa();

        // Se crea el personaje pero lo lleno luego 
        Personaje personaje = new Personaje();
        
        // Banderitas
        boolean flagReligiosa = true;
        boolean creeEnDios = true;

        while (flagReligiosa) {
            ventana.imprimir("¿Crees en Dios?\n0. Si\n1. No");
            
            String resp = ventana.leer(); 
            
            if (resp.equals("0") || resp.equals("1")) {
                creeEnDios = resp.equals("0");
                flagReligiosa = false;
                ventana.limpiar(); 
            } else {
                ventana.imprimir("Opción no válida, Rodion.");
            }
        }

        // Relleno con los stats dependiendo de sus creencias (basada en una lógica Dostoievskiana, no creer en Dios => más propenso a creer que todo está permitodo => Mayor decisión base para cometer "pecados" y menos esperanza general)
        personaje.iniciarPersonaje(creeEnDios);
        personaje.ubicacionActual = "Habitacion"; 

        // Más banderas 
        boolean jugando = true;
        boolean mundoYaActualizado = false;
        boolean flagRedencion = false;


        // Heap donde se guardaran los aconetcimientos
        HeapAcontecimientos<String> Acontecimientos = new HeapAcontecimientos<String>();


        while (jugando) {
            // Nodo inicial 
            Escenario lugarActual = sanPetesburgo.obtenerEscenario(personaje.ubicacionActual);
            
            // Si no se ah actualizado el mundo y personaje ah matado => Se actualiza el Grafo, se eliminan nodos, mueren y nacen nuevas conecciones
            if(!mundoYaActualizado && personaje.ahMatado){
                sanPetesburgo.actualizarEstadoMundo(personaje);
                personaje.inicializarCrisis(Acontecimientos);
                mundoYaActualizado = true;
            }

            // Si ah matado y no está listo para redención, cada paso que de será una gota de sangre que caerá para su desangramiento moral inevitable 
            if (personaje.ahMatado && !personaje.listoParaRedencion) {
                personaje.recalibrarDelirios(Acontecimientos);
                
                if (personaje.pasos % 3 == 0) {
                    int fiebreActual = personaje.alma.estadoFisico.get("Fiebre");
                    personaje.alma.estadoFisico.put("Fiebre", Math.min(100, fiebreActual + 10));

                    ventana.imprimir("\nLa fiebre no para de subir...\n"); 
                    ventana.imprimir("Fiebre: " + personaje.alma.estadoFisico.get("Fiebre"));          

                } else if (personaje.pasos % 5 == 0) {
                    int parActual = personaje.alma.estadoPsicologico.get("Paranoia");
                    int lucActual = personaje.alma.estadoPsicologico.get("Lucidez");

                    personaje.alma.estadoPsicologico.put("Paranoia", Math.min(100, parActual + 5));
                    personaje.alma.estadoPsicologico.put("Lucidez", Math.min(100, lucActual + 5));

                    ventana.imprimir("\nEstás cada vez peor Rodion... Date prisa\n");
                    ventana.imprimir("Paranoia: " + personaje.alma.estadoPsicologico.get("Paranoia"));
                    ventana.imprimir("Lucidez: " + personaje.alma.estadoPsicologico.get("Lucidez"));
                } 
            }

            // Si está listo para la redención se modifica el grafo lo más adecuado para que solo pueda "ganar"
            if(!flagRedencion && personaje.listoParaRedencion){
                sanPetesburgo.mundoParaRedencion(personaje);
                flagRedencion = true;
            }

            /*
                Lógica de los acontecimientos, se cocinan a fuego lento, y se van actualizando "paso" a "paso", dependen de los stats 
                en cada "paso" del personaje, un acontecimiento sucede si o si después de que el resto de la cantidad de pasos dados 
                dividido 6 sea 0, hay 4 posibles acontecimientos, comisaría (Teletransporte a la comisaría, si esto pasa 3 veces te atrapan)
                teletransporte aleatoreo, Delirio (son unos nodos especiales) y suicidio, este ultimo es el más complejo de que pase por 
                lo que creeo creer del personaje que simula el juego.
                
                Esta es el bazuca para un mosquito del que me refería, realmente acá se pueden usar una cantidad n de posibilidades, en este caso
                la complejidad real ni si quiera es aprovechada por las características del heap, porque al final relleno los nodos de vuelta lo que
                me caga la característica O(1) de extracción y actualización de prioridad, porque insert es si o si O(log(n)) por el bubbleup.
                
                No obstante, aunque no estoy aprovechando la capacidad real de uso en este proyecto en específico, está el potencial, para aprovecharlo de verdad
                podría hacer algo parecido pero con una cantidad n de acontecimientos bastante más grande, lo cual quizas pase en otro proyecto, por ahora, no.
            */
            if(personaje.ahMatado && personaje.pasos % 6 == 0 && !personaje.listoParaRedencion){

                String ActualAcontecimiento = Acontecimientos.extractMax();

                switch (ActualAcontecimiento) {

                    case "Comisaria":
                        if (personaje.aparecioEnComisaria < 3) {
                            personaje.pasos++;
                            personaje.aparecioEnComisaria++;
                            personaje.ubicacionActual = "Comisaria"; 
                            lugarActual = sanPetesburgo.obtenerEscenario(personaje.ubicacionActual);
                            
                            ventana.limpiar();
                            
                            ventana.imprimir(personaje.AparicionEnComisaria(personaje.aparecioEnComisaria));
                            
                            int lucidez = personaje.alma.estadoPsicologico.get("Lucidez");
                            if(lucidez + 10 <= 100){
                                personaje.alma.estadoPsicologico.put("Lucidez", lucidez + 40);
                            }

                            ventana.leer(); 

                        } else {

                            ventana.limpiar();
                            ventana.imprimir(personaje.finalesAlternativos()); 
                            jugando = false;
                        }
                        break;
                    
                    case "Delirio":

                        personaje.ubicacionActual = "..."; 
                        lugarActual = sanPetesburgo.obtenerEscenario(personaje.ubicacionActual);

                        personaje.enDelirio = true;
                        ventana.limpiar();
                        ventana.imprimir(personaje.entradaAlDelirio());
                        

                        break;
                    
                    case "Teletransporte":
                        personaje.pasos++;
                        ventana.limpiar();
                        ventana.imprimir(personaje.eventoTeletransporte());

                        String[] nodos = {"Comisaria", "Malecon", "Calle Oscura", "Habitacion", "Rio Neva", "Casa de Sonnya", "Casa De Alyona Ivanovna"};
                        int index = (int) (Math.random() * nodos.length);
                        
                        personaje.ubicacionActual = nodos[index];
                        lugarActual = sanPetesburgo.obtenerEscenario(personaje.ubicacionActual);

                        int paranoiaActual = personaje.alma.estadoPsicologico.get("Paranoia");
                        int nuevaParanoia = Math.max(0, paranoiaActual - 40); 
                        personaje.alma.estadoPsicologico.put("Paranoia", nuevaParanoia);

                        ventana.imprimir("\n[La adrenalina baja... tu Paranoia ahora es: " + nuevaParanoia + "]");
                        ventana.leer(); 

                        break;

                    case "Suicidio":

                        ventana.limpiar();
                        ventana.imprimir(personaje.eventoSuicidio());                        
                        ventana.leer();
                        jugando = false;

                        break;

                    default:


                        
                        break;
                }

                if(jugando){
                    if(ActualAcontecimiento.equals("Comisaria")){ 
                        personaje.hComisaria = Acontecimientos.insert("Comisaria", 0);
                    }
                    if(ActualAcontecimiento.equals("Teletransporte")){ 
                        personaje.hTeletransporte = Acontecimientos.insert("Teletransporte", 0);
                    }
                    if(ActualAcontecimiento.equals("Delirio")){ 
                        personaje.hDelirio = Acontecimientos.insert("Delirio", 0);
                    }
                }

            }


            // Final "bueno"
            if (personaje.atrapado && !personaje.listoParaRedencion) {
                ventana.imprimir("\nHas sido atrapado.");
                jugando = false;
                break;
            } else if (personaje.atrapado && !personaje.listoParaRedencion){
                ventana.imprimir("Aquí empieza otra historia, la de la lenta renovación de un hombre, la\r\n" + //
                                        "de su regeneración progresiva, su paso gradual de un mundo a otro y su\r\n" + //
                                        "conocimiento escalonado de una realidad totalmente ignorada. En todo esto\r\n" + //
                                        "habría materia para una nueva narración, pero la nuestra ha terminado.");
            }

            ventana.imprimir("\n===================================================================================\n");
            ventana.imprimir("ESTÁS EN: " + lugarActual.nombre);
            ventana.imprimir(lugarActual.descripcion);
            ventana.imprimir("\n===================================================================================\n");

            ventana.imprimir("¿Que harás?\n");
            ventana.imprimir("0. Moverse / Salir");
            ventana.imprimir("1. Inventario");
            ventana.imprimir("2. Estado Psicológico");
            ventana.imprimir("3. Estado Físico");
            ventana.imprimir("4. Inspeccionar lugar");
            
            if (personaje.inventario.contains("Caja de cigarrillos") && personaje.pasos % 5 == 0) {
                ventana.imprimir("5. Fumar");
            }
            if (personaje.inventario.contains("Cerveza turbia")) {
                ventana.imprimir("6. Tomar cerveza");
            }
            ventana.imprimir("9. ABANDONAR PARTIDA");

            String entrada = ventana.leer();
            int accionGlobal;
            
            try {
                accionGlobal = Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                ventana.imprimir("\n[ERROR]: Rodion, usa números.");
                continue;
            }

            switch (accionGlobal) {
                case 0: // Lógica de moverse, imprimiendo las i salidas posibles en cada nodo
                    if(!personaje.enDelirio){
                        ventana.limpiar(); 
                        ventana.imprimir("\n¿A dónde te dirigen tus pasos?");
                        for (int i = 0; i < lugarActual.salidas.size(); i++) {
                            ventana.imprimir(i + ". " + lugarActual.salidas.get(i).nombreTransicion);
                        }
                        
                        try {
                            int destinoIdx = Integer.parseInt(ventana.leer());
                            if (destinoIdx >= 0 && destinoIdx < lugarActual.salidas.size()) {
                                lugarActual = lugarActual.salidas.get(destinoIdx).destino;
                                personaje.ubicacionActual = lugarActual.nombre;
                                personaje.pasos++;
                            } else {
                                ventana.imprimir("Opción no válida.");
                            }
                        } catch (NumberFormatException e) { ventana.imprimir("Error de entrada."); }
                        
                    } else  {
                        ventana.limpiar();
                        ventana.imprimir("\n¿Que es esto?");
                        for (int i = 0; i < lugarActual.salidas.size(); i++) {
                            ventana.imprimir(i + ". " + lugarActual.salidas.get(i).nombreTransicion);
                        }
                        try {
                            int destinoIdx = Integer.parseInt(ventana.leer());
                            if (destinoIdx >= 0 && destinoIdx < lugarActual.salidas.size()) {
                                
                                personaje.pasos++;
                                lugarActual = lugarActual.salidas.get(destinoIdx).destino;
                                personaje.ubicacionActual = lugarActual.nombre;

                                personaje.alma.estadoFisico.put("Fiebre", 40);

                                personaje.pasosEnDelirio++;

                                if (personaje.pasosEnDelirio >= 3) {
                                    personaje.ubicacionActual = "Habitacion";
                                    lugarActual = sanPetesburgo.obtenerEscenario(personaje.ubicacionActual);

                                    ventana.limpiar();
                                    ventana.imprimir("!!! DESPIERTAR !!!\n");
                                    ventana.imprimir("La niebla se disipa de golpe. El esfuerzo te devuelve a tu habitación.");
                                    
                                    personaje.enDelirio = false;
                                    personaje.pasosEnDelirio = 0;


                                    ventana.leer();
                                } else {
                                    ventana.imprimir("\n..."); 
                                }
                            } else {
                                ventana.imprimir("...");
                            }
                            } catch (NumberFormatException e) { 
                                ventana.imprimir("...?"); 
                            }
                        }
                        break;

                case 1: 
                    ventana.limpiar();
                    ventana.imprimir(personaje.mostrarInventario()); break;
                case 2:
                    ventana.limpiar(); 
                    ventana.imprimir(personaje.alma.mostrarAlma()); break;
                case 3: 
                    ventana.limpiar();
                    ventana.imprimir(personaje.alma.mostrarEstadoFisico()); break;
                
                case 4: // La interacción directa con cada nodo, toda la lógica individual está dentro de los nodos
                    ventana.limpiar();
                    String opcionesSub = lugarActual.obtenerAccionesPosibles(personaje);
                    ventana.imprimir(opcionesSub);
                    
                    try {
                        int subOpcion = Integer.parseInt(ventana.leer());
                        String resultado = lugarActual.ejecutarAccion(subOpcion, personaje);
                        ventana.imprimir(resultado);
                    } catch (NumberFormatException e) { ventana.imprimir("Error de entrada."); }
                    break;

                case 5:
                    ventana.limpiar();
                    if (personaje.inventario.contains("Caja de cigarrillos") && personaje.pasos % 4 == 0) {
                        ventana.imprimir(personaje.fumarCigarrillo(personaje));
                    } else { ventana.imprimir("\nNo es el momento, Rodion."); }
                    break;

                case 6:
                    ventana.limpiar();
                    if (personaje.inventario.contains("Cerveza turbia")) {
                       ventana.imprimir(personaje.tomarCerveza(personaje));
                    } else { ventana.imprimir("\nNo tienes nada que beber."); }
                    break;

                case 9:
                    ventana.limpiar();
                    jugando = false;
                    ventana.imprimir("Has huido de tus pecados... por ahora.");
                    break;
                
                default:
                    ventana.imprimir("\nOpción no válida.");
                    break;
            }
        }
    }
}