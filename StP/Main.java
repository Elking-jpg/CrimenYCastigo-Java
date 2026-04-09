package StP;


public class Main {
    public static void main(String[] args) {
        // 1. Inicializamos la ventana y reemplazamos la consola
        VentanaJuego ventana = new VentanaJuego();
        
        Ciudad sanPetesburgo = new Ciudad();
        sanPetesburgo.generarMapa();

        // Estado inicial
        Personaje personaje = new Personaje();
        
        // --- Lógica de Dios ---
        boolean flagReligiosa = true;
        boolean creeEnDios = true;
        while (flagReligiosa) {
            ventana.imprimir("¿Crees en Dios?\n0. Si\n1. No");
            
            String resp = ventana.leer(); // Leemos de la ventana
            
            if (resp.equals("0") || resp.equals("1")) {
                creeEnDios = resp.equals("0");
                flagReligiosa = false;
                ventana.limpiar(); // Limpiamos para iniciar el juego
            } else {
                ventana.imprimir("Opción no válida, Rodion.");
            }
        }

        personaje.iniciarPersonaje(creeEnDios);
        personaje.ubicacionActual = "Habitacion"; 

        boolean jugando = true;
        boolean mundoYaActualizado = false;
        boolean flagRedencion = false;


        HeapAcontecimientos<String> Acontecimientos = new HeapAcontecimientos<String>();

        while (jugando) {
            Escenario lugarActual = sanPetesburgo.obtenerEscenario(personaje.ubicacionActual);
            
            if(!mundoYaActualizado && personaje.ahMatado){
                sanPetesburgo.actualizarEstadoMundo(personaje);
                personaje.inicializarCrisis(Acontecimientos);
                mundoYaActualizado = true;
            }

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

            if(!flagRedencion && personaje.listoParaRedencion){
                sanPetesburgo.mundoParaRedencion(personaje);
                flagRedencion = true;
            }


            if(personaje.ahMatado && personaje.pasos % 5 == 0 && !personaje.listoParaRedencion){

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
                case 0: // MOVERSE
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
                
                case 4: // ACCIONES DE ESCENARIO
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