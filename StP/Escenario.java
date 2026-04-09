package StP;
import java.util.ArrayList;


public abstract class Escenario { 
    public String nombre;
    public String descripcion;
    public ArrayList<Salida> salidas = new ArrayList<>();

    public Escenario(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public void conectarCon(String nombreCamino, Escenario destino) {
    this.salidas.add(new Salida(nombreCamino, destino));
    }

    public abstract String obtenerAccionesPosibles(Personaje p);
    public abstract String ejecutarAccion(int opcion, Personaje p);
}
