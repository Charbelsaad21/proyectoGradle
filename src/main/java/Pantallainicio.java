import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;

public class Pantallainicio extends JFrame {

    private BufferedImage backgroundImage;

    public Pantallainicio() {
        try {
            // Cargar la imagen de fondo desde la ruta correcta
            backgroundImage = ImageIO.read(getClass().getResource("/Images/Fondo.jpg"));
            float scaleFactor = 0.5f; // 1.0f es el brillo original, menos de 1.0f lo oscurece
            RescaleOp op = new RescaleOp(scaleFactor, 0, null);
            backgroundImage = op.filter(backgroundImage, null);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Crear un JPanel personalizado que dibuje la imagen como fondo
        JPanel panelConFondo = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Dibujar la imagen de fondo ANTES de los componentes
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };

        panelConFondo.setLayout(new GridBagLayout()); // Para centrar los componentes
        panelConFondo.setOpaque(false); // Hacer el panel transparente

        // Configuración de la ventana
        setTitle("Pantalla de Inicio");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Crear el título "Juego De Memoria"
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;  // Posicionarlo en la columna 0
        gbc.gridy = 0;  // Posicionarlo en la fila 0
        gbc.insets = new Insets(20, 0, 20, 0); // Espaciado alrededor del título
        JLabel titulo = new JLabel("Juego De Memoria");
        titulo.setFont(new Font("Arial", Font.BOLD, 32)); // Cambiar tamaño y estilo de la fuente
        titulo.setForeground(Color.WHITE); // Cambiar el color de la fuente a blanco
        panelConFondo.add(titulo, gbc); // Añadir el título al panel

        // Crear el campo de texto para el nombre del jugador
        gbc.gridy++;  // Mover hacia abajo
        JTextField nombreTextField = new JTextField(15);
        nombreTextField.setFont(new Font("Arial", Font.PLAIN, 20));
        nombreTextField.setHorizontalAlignment(JTextField.CENTER);
        nombreTextField.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelConFondo.add(nombreTextField, gbc);

        // Crear el botón de "Jugar"
        gbc.gridy++;  // Mover hacia abajo
        JButton iniciarJuegoButton = new JButton("Jugar");
        iniciarJuegoButton.setFont(new Font("Arial", Font.BOLD, 20));
        iniciarJuegoButton.setBackground(Color.LIGHT_GRAY);
        iniciarJuegoButton.setForeground(Color.BLACK);
        panelConFondo.add(iniciarJuegoButton, gbc);

        // Acción del botón para iniciar el juego
        iniciarJuegoButton.addActionListener(e -> {
            // Cierra la ventana de inicio
            Pantallainicio.this.dispose(); // Cierra la ventana actual

            // Abre la pantalla de jugadores
            PantallaJugadores pantallaJugadores = new PantallaJugadores(nombreTextField.getText());
            pantallaJugadores.setVisible(true); // Mostrar la nueva ventana
        });

        // Añadir el panel con fondo a la ventana
        add(panelConFondo);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Pantallainicio inicio = new Pantallainicio();
            inicio.setVisible(true); // Mostrar la ventana de inicio
        });
    }
}
