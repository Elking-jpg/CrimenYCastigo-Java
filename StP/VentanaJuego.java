package StP;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.LinkedBlockingQueue;

public class VentanaJuego extends JFrame {
    private JTextArea areaTexto;
    private JTextField campoEntrada;
    private LinkedBlockingQueue<String> colaEntrada; // Para leer lo que escribes
    private LinkedBlockingQueue<String> colaTextosAnimar; // Para mostrar texto letra a letra

    public VentanaJuego() {
        // 1. Configuración básica de la ventana
        setTitle("San Petersburgo - Crimen y Castigo");
        setSize(850, 600); // Un poco más grande para que quepa bien el texto
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // 2. Área de Texto (El Terminal)
        areaTexto = new JTextArea();
        areaTexto.setEditable(false);
        areaTexto.setBackground(Color.BLACK); // Fondo negro profundo
        areaTexto.setForeground(new Color(57, 255, 20)); // Verde Neón Clásico
        
        // FUENTE
        areaTexto.setFont(new Font("Monospaced", Font.BOLD, 22));
        
        areaTexto.setLineWrap(true);
        areaTexto.setWrapStyleWord(true);
        areaTexto.setMargin(new Insets(10, 10, 10, 10)); // Margen interno para que no pegue al borde

        JScrollPane scroll = new JScrollPane(areaTexto);
        scroll.setBorder(null); // Quitar borde feo del scroll
        add(scroll, BorderLayout.CENTER);

        // 3. Campo de Entrada (La barra de comandos)
        campoEntrada = new JTextField();
        campoEntrada.setBackground(new Color(30, 30, 30));
        campoEntrada.setForeground(Color.WHITE);
        campoEntrada.setCaretColor(Color.WHITE);
        campoEntrada.setFont(new Font("Monospaced", Font.PLAIN, 18));
        campoEntrada.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.GRAY));
        add(campoEntrada, BorderLayout.SOUTH);

        // 4. Inicialización de colas y eventos
        colaEntrada = new LinkedBlockingQueue<>();
        colaTextosAnimar = new LinkedBlockingQueue<>();

        // Acción al presionar ENTER
        campoEntrada.addActionListener(e -> {
            String texto = campoEntrada.getText();
            if (!texto.isEmpty()) {
                colaEntrada.offer(texto);
                campoEntrada.setText("");
            }
        });

        // 5. Iniciar el motor de animación (Un solo hilo para evitar el caos de letras)
        iniciarHiloAnimador();

        setLocationRelativeTo(null); // Centrar ventana en pantalla
        setVisible(true);
    }

    // El Main llama a este método para "encolar" mensajes
    public void imprimir(String texto) {
        colaTextosAnimar.offer(texto);
    }

    // Este hilo es el único que escribe, uno por uno, los mensajes de la cola
    private void iniciarHiloAnimador() {
        Thread animador = new Thread(() -> {
            while (true) {
                try {
                    String mensaje = colaTextosAnimar.take(); // Espera a que llegue un mensaje
                    areaTexto.append("\n> "); // Indicador de sistema
                    
                    for (char letra : mensaje.toCharArray()) {
                        areaTexto.append(String.valueOf(letra));
                        // Auto-scroll al final
                        areaTexto.setCaretPosition(areaTexto.getDocument().getLength());
                        Thread.sleep(7); // Velocidad de aparición (ms) -----------------------------
                    }
                    areaTexto.append("\n");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });
        animador.setDaemon(true); // Se cierra si cierras el programa
        animador.start();
    }

    // Método para limpiar la terminal
    public void limpiar() {
        areaTexto.setText("");
    }

    // El Main llama a este para esperar tu respuesta
    public String leer() {
        try {
            return colaEntrada.take();
        } catch (InterruptedException e) {
            return "";
        }
    }
}