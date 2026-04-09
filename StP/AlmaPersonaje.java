package StP;
import java.util.HashMap;
import java.util.Map;

public class AlmaPersonaje {
    
    public Map<String, Integer> estadoPsicologico = new HashMap<>();

    public Map<String, Integer> estadoFisico = new HashMap<>();
    

    public String mostrarAlma() {
        StringBuilder sb = new StringBuilder();
        sb.append("--- ESTADO PSICOLÓGICO (ALMA) ---\n");
        
        // entrySet() 
        for (Map.Entry<String, Integer> entrada : estadoPsicologico.entrySet()) {
            sb.append(" - ").append(entrada.getKey())
            .append(": ").append(entrada.getValue()).append("\n");
        }
        
        return sb.toString();
    }

    public String mostrarEstadoFisico() {
        StringBuilder sb = new StringBuilder();
        sb.append("--- ESTADO FÍSICO ---\n");
        
        // entrySet() 
        for (Map.Entry<String, Integer> entrada : estadoFisico.entrySet()) {
            sb.append(" - ").append(entrada.getKey())
            .append(": ").append(entrada.getValue()).append("\n");
        }
        
        return sb.toString();
    }
}
