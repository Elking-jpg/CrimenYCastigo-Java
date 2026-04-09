package StP;
import java.util.HashMap;
import java.util.Map;
import Escenarios.*;

public class Ciudad {

    public Map<String, Escenario> Escenarios = new HashMap<>();

    public void generarMapa(){



        Escenario habitacion = new Habitacion();
        Escenario escalera = new Escalera();
        Escenario plaza = new PlazaDeSanPetersburgo();
        Escenario taberna = new TabernaEstrecha();
        Escenario avenidaPrincipal = new Avenida();
        Escenario comisaria = new Comisaria(); 
        Escenario rioNeva = new RioNeva();
        Escenario puenteNeva = new PuenteNeva();
        Escenario malecon = new Malecon();
        Escenario casaRasumijin = new CasaRasumijin();
        Escenario casaSonia = new CasaSonia();
        Escenario calleOscura = new CallejonOscura();
        Escenario casaDeLaVieja = new CasaDeLaVieja();
        Escenario puenteKokushkin = new PuenteKokushkin();
        Escenario jardinYusupov = new JardinYusupov();
        Escenario NodoEspecial1 = new NodoEspecial1();
        Escenario NodoEspecial2 = new NodoEspecial2();

        agregarEscenario(habitacion);
        agregarEscenario(escalera);
        agregarEscenario(plaza);
        agregarEscenario(comisaria);
        agregarEscenario(avenidaPrincipal);
        agregarEscenario(rioNeva);
        agregarEscenario(puenteNeva);
        agregarEscenario(taberna);
        agregarEscenario(malecon);
        agregarEscenario(casaRasumijin);
        agregarEscenario(casaSonia);
        agregarEscenario(calleOscura);
        agregarEscenario(casaDeLaVieja);
        agregarEscenario(puenteKokushkin);
        agregarEscenario(jardinYusupov);

        agregarEscenario(NodoEspecial1);
        agregarEscenario(NodoEspecial2);

        NodoEspecial1.conectarCon("...", NodoEspecial2);
        NodoEspecial2.conectarCon("...", NodoEspecial1);

        habitacion.conectarCon("Bajar a la Escalera", escalera);
        escalera.conectarCon("Subir a la Habitacion", habitacion);
        escalera.conectarCon("Salir a la Avenida Principal", avenidaPrincipal);
        avenidaPrincipal.conectarCon("Entrar al edificio", escalera);
        avenidaPrincipal.conectarCon("Ir hacia el Malecon", malecon);
        malecon.conectarCon("Volver a la Avenida", avenidaPrincipal);
        avenidaPrincipal.conectarCon("Caminar hacia la Plaza", plaza);
        plaza.conectarCon("Volver a la Avenida", avenidaPrincipal);
        malecon.conectarCon("Bajar al Rio Neva", rioNeva);
        rioNeva.conectarCon("Subir al Malecon", malecon);
        rioNeva.conectarCon("Cruzar el Puente Neva", puenteNeva);
        puenteNeva.conectarCon("Volver al Rio", rioNeva);
        puenteNeva.conectarCon("Ir a la Casa de Sonnya", casaSonia);
        casaSonia.conectarCon("Ir al Puente Neva", puenteNeva);
        casaSonia.conectarCon("Cruzar el Puente 2", puenteKokushkin);
        puenteKokushkin.conectarCon("Ir a casa de Sonnya", casaSonia);
        puenteKokushkin.conectarCon("Entrar a la Comisaria", comisaria);
        comisaria.conectarCon("Salir al Puente 2", puenteKokushkin);
        comisaria.conectarCon("Ir a la Plaza", plaza);
        plaza.conectarCon("Ir a la Comisaria", comisaria);
        plaza.conectarCon("Entrar a la Taberna", taberna);
        taberna.conectarCon("Salir a la Plaza", plaza);
        taberna.conectarCon("Ir a Casa de Rasumijin", casaRasumijin);
        casaRasumijin.conectarCon("Volver a la Taberna", taberna);
        casaRasumijin.conectarCon("Bajar a la Calle Oscura", calleOscura);
        calleOscura.conectarCon("Subir a lo de Rasumijin", casaRasumijin);
        plaza.conectarCon("Caminar por la Calle Oscura", calleOscura);
        calleOscura.conectarCon("Volver a la Plaza", plaza);
        calleOscura.conectarCon("Entrar a Casa de la Vieja", casaDeLaVieja);
        casaDeLaVieja.conectarCon("Salir a la calle", calleOscura);
        casaDeLaVieja.conectarCon("Ir hacia el Parque", jardinYusupov);
        jardinYusupov.conectarCon("Ir a lo de la Vieja", casaDeLaVieja);
        jardinYusupov.conectarCon("Ir a la Comisaria", comisaria);
        comisaria.conectarCon("Ir al Parque", jardinYusupov);
        
    }

    private void agregarEscenario(Escenario escenario) {
        Escenarios.put(escenario.nombre, escenario);
    }

    public Escenario obtenerEscenario(String nombre) {
        return Escenarios.get(nombre);
    }





    private void eliminarNodoDelGrafo(String nombreEscenario) {
        Escenarios.remove(nombreEscenario);

        for (Escenario e : Escenarios.values()) {
            e.salidas.removeIf(salida -> salida.destino.nombre.equals(nombreEscenario));
        }
    }

    private void conectarNodos(String origen, String destino) {
        Escenario escOrigen = Escenarios.get(origen);
        Escenario escDestino = Escenarios.get(destino);
        
        if (escOrigen != null && escDestino != null) {
            escOrigen.conectarCon("Ir hacia " + destino, escDestino);
        }
    }

    public void actualizarEstadoMundo(Personaje p) {
        if (p.ahMatado && !p.mundoYaActualizado) {
            eliminarNodoDelGrafo("Taberna Estrecha");
            eliminarNodoDelGrafo("Casa de Rasumijin");
            eliminarNodoDelGrafo("Plaza de San Petersburgo");
            eliminarNodoDelGrafo("Jardin Yusupov");
            eliminarNodoDelGrafo("Puente Kokushkin");

            Escenario calle = Escenarios.get("Calle Oscura");
            if(calle != null) calle.salidas.clear();

            conectarNodos("Calle Oscura", "Comisaria");
            conectarNodos("Calle Oscura", "Calle Oscura");
            conectarNodos("Comisaria", "Malecon");
            conectarNodos("Malecon", "Comisaria");



        }

    }




    public void mundoParaRedencion(Personaje p){
        eliminarNodoDelGrafo("Casa De Alyona Ivanovna");
        eliminarNodoDelGrafo("Calle Oscura");
        eliminarNodoDelGrafo("Malecon");
        eliminarNodoDelGrafo("Puente sobre el Neva");
        
        conectarNodos("Casa de Sonnya", "Comisaria");
    } 
}