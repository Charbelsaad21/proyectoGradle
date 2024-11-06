import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;

public class PantallaPuntaje extends JFrame {
    private BufferedImage backgroundImage;

    public PantallaPuntaje(String[] nombresJugadores, int[] puntajes) {
        setTitle("Puntajes Finales");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Cargar imagen de fondo con un ajuste de brillo
        try {
            backgroundImage = ImageIO.read(getClass().getResource("/Images/Fondo.jpg"));
            float scaleFactor = 0.5f;
            RescaleOp op = new RescaleOp(scaleFactor, 0, null);
            backgroundImage = op.filter(backgroundImage, null);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Panel principal con fondo
        JPanel mainPanel = new BackgroundPanel();
        mainPanel.setLayout(new BorderLayout());
        
        // Título en la parte superior
        JLabel titulo = new JLabel("Puntajes Finales", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setForeground(Color.WHITE); // Ajuste de color para visibilidad sobre el fondo
        mainPanel.add(titulo, BorderLayout.NORTH);

        // Panel para puntajes
        JPanel panelPuntajes = new JPanel();
        panelPuntajes.setLayout(new GridLayout(1, 4, 10, 0));
        panelPuntajes.setBorder(new EmptyBorder(80, 60, 80, 60));
        panelPuntajes.setOpaque(false); // Asegurarse de que el panel sea transparente

        // Agregar los paneles de puntaje de cada jugador
        for (int i = 0; i < nombresJugadores.length; i++) {
            agregarPuntaje(panelPuntajes, nombresJugadores[i], puntajes[i], getColorJugador(i));
        }

        mainPanel.add(panelPuntajes, BorderLayout.CENTER);
        mainPanel.setOpaque(false); // Hacer el mainPanel transparente

        add(mainPanel, BorderLayout.CENTER);
    }

    private void agregarPuntaje(JPanel panel, String nombre, int puntaje, Color color) {
        JPanel puntajePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(color);
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        puntajePanel.setOpaque(false);
        puntajePanel.setLayout(new BorderLayout());
        puntajePanel.setPreferredSize(new Dimension(80, 60));
        puntajePanel.setBorder(new EmptyBorder(0, 5, 0, 5));

        JLabel nombreJugador = new JLabel(nombre, SwingConstants.CENTER);
        nombreJugador.setFont(new Font("Serif", Font.BOLD, 18));
        nombreJugador.setForeground(Color.WHITE); // Ajuste de color para visibilidad
        puntajePanel.add(nombreJugador, BorderLayout.CENTER);

        JLabel puntajeJugador = new JLabel("Puntaje: " + puntaje, SwingConstants.CENTER);
        puntajeJugador.setForeground(Color.WHITE); // Ajuste de color para visibilidad
        puntajePanel.add(puntajeJugador, BorderLayout.SOUTH);

        panel.add(puntajePanel);
    }

    private Color getColorJugador(int index) {
        Color[] colores = {
            new Color(255, 0, 0, 180), // Rojo
            new Color(0, 255, 255, 180), // Cian
            new Color(255, 165, 0, 180), // Naranja
            new Color(0, 255, 0, 180) // Verde
        };
        return colores[index % colores.length];
    }

    private class BackgroundPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            String[] jugadores = {"Jugador 1", "Jugador 2", "Jugador 3", "Jugador 4"};
            int[] puntajes = {100, 150, 200, 250}; // Ejemplo de puntajes
            PantallaPuntaje pantallaPuntaje = new PantallaPuntaje(jugadores, puntajes);
            pantallaPuntaje.setVisible(true);
        });
    }
}
