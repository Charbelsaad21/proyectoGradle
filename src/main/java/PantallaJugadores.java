import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;

public class PantallaJugadores extends JFrame {
    private BufferedImage backgroundImage;

    public PantallaJugadores(String nombreJugador) {
        setTitle("Jugadores Listos");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        try {
            backgroundImage = ImageIO.read(getClass().getResource("/Images/Fondo.jpg"));
            float scaleFactor = 0.5f;
            RescaleOp op = new RescaleOp(scaleFactor, 0, null);
            backgroundImage = op.filter(backgroundImage, null);
        } catch (IOException e) {
            e.printStackTrace();
        }

        JPanel mainPanel = new BackgroundPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayout(1, 4, 10, 0));
        agregarJugador(panel1, nombreJugador, new Color(255, 0, 0, 180)); // Rojo más fuerte
        agregarJugador(panel1, "", new Color(0, 255, 255, 180)); // Cian más fuerte
        agregarJugador(panel1, "", new Color(255, 165, 0, 180)); // Naranja más fuerte
        agregarJugador(panel1, "", new Color(0, 255, 0, 180)); // Verde más fuerte
        panel1.setBorder(new EmptyBorder(80, 60, 80, 60));


        mainPanel.add(panel1, BorderLayout.CENTER);
        mainPanel.setOpaque(false);

        // Contenedor del botón "Listo" en la parte inferior centrada
        JPanel botonPanel = new JPanel();
        botonPanel.setOpaque(false);  
        botonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        botonPanel.setBorder(new EmptyBorder(0, 0, 20, 0)); // Ajuste para subir el botón ligeramente

        JButton botonListo = new JButton("Listo");
        botonListo.setFont(new Font("Arial", Font.BOLD, 16));
        botonListo.setBackground(Color.LIGHT_GRAY);
        botonListo.setForeground(Color.BLACK);
        botonListo.setPreferredSize(new Dimension(100, 40)); 
        botonPanel.add(botonListo);

        mainPanel.add(botonPanel, BorderLayout.SOUTH);
        panel1.setOpaque(false);

        botonListo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PantallaJugadores.this.dispose();
                SwingUtilities.invokeLater(() -> {
                    PantallaMemoria pantallaMemoria = new PantallaMemoria();
                    pantallaMemoria.setVisible(true);
                });
            }
        });

        add(mainPanel, BorderLayout.CENTER);
    }

    private void agregarJugador(JPanel panel, String nombre, Color color) {
        JPanel jugadorPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(color);
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        jugadorPanel.setOpaque(false);
        jugadorPanel.setLayout(new BorderLayout());
        jugadorPanel.setBackground(color);
        jugadorPanel.setPreferredSize(new Dimension(80, 60));
        jugadorPanel.setBorder(new EmptyBorder(0, 5, 0, 5));

        JLabel nombreJugador = new JLabel(nombre, SwingConstants.CENTER);
        nombreJugador.setFont(new Font("Serif", Font.BOLD, 18));
        jugadorPanel.add(nombreJugador, BorderLayout.CENTER);

        JLabel estadoJugador = new JLabel("¡Listo!", SwingConstants.CENTER);
        jugadorPanel.add(estadoJugador, BorderLayout.SOUTH);
        
        panel.add(jugadorPanel);
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
            PantallaJugadores jugadores = new PantallaJugadores("");
            jugadores.setVisible(true);
        });
    }
}
