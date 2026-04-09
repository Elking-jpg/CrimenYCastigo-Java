package StP;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.LinkedBlockingQueue;

public class VentanaJuego extends JFrame {
    private JTextArea areaTexto;
    private JTextField campoEntrada;
    private LinkedBlockingQueue<String> colaEntrada;
    private LinkedBlockingQueue<String> colaTextosAnimar; 

    public VentanaJuego() {

        setTitle("San Petersburgo - Crimen y Castigo");
        setSize(850, 600); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());


        areaTexto = new JTextArea();
        areaTexto.setEditable(false);
        areaTexto.setBackground(Color.BLACK); 
        areaTexto.setForeground(new Color(57, 255, 20));
        
        // FUENTE
        areaTexto.setFont(new Font("Monospaced", Font.BOLD, 22));
        
        areaTexto.setLineWrap(true);
        areaTexto.setWrapStyleWord(true);
        areaTexto.setMargin(new Insets(10, 10, 10, 10)); 

        JScrollPane scroll = new JScrollPane(areaTexto);
        scroll.setBorder(null); 
        add(scroll, BorderLayout.CENTER);

        campoEntrada = new JTextField();
        campoEntrada.setBackground(new Color(30, 30, 30));
        campoEntrada.setForeground(Color.WHITE);
        campoEntrada.setCaretColor(Color.WHITE);
        campoEntrada.setFont(new Font("Monospaced", Font.PLAIN, 18));
        campoEntrada.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.GRAY));
        add(campoEntrada, BorderLayout.SOUTH);

        colaEntrada = new LinkedBlockingQueue<>();
        colaTextosAnimar = new LinkedBlockingQueue<>();

        campoEntrada.addActionListener(e -> {
            String texto = campoEntrada.getText();
            if (!texto.isEmpty()) {
                colaEntrada.offer(texto);
                campoEntrada.setText("");
            }
        });

        iniciarHiloAnimador();

        setLocationRelativeTo(null); 
        setVisible(true);
    }

    public void imprimir(String texto) {
        colaTextosAnimar.offer(texto);
    }

    private void iniciarHiloAnimador() {
        Thread animador = new Thread(() -> {
            while (true) {
                try {
                    String mensaje = colaTextosAnimar.take(); 
                    areaTexto.append("\n> "); 
                    
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
        animador.setDaemon(true);
        animador.start();
    }

    public void limpiar() {
        areaTexto.setText("");
    }

    public String leer() {
        try {
            return colaEntrada.take();
        } catch (InterruptedException e) {
            return "";
        }
    }
}