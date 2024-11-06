import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.util.Random;
import java.util.ArrayList;

class PantallaMemoria extends JFrame {

    private BufferedImage backgroundImage;
    private BufferedImage nuevaImagenFondo; // Variable de instancia para la nueva imagen de fondo
    private JLabel temporizadorLabel;
    private int tiempoRestante; // Tiempo restante en segundos
    private ArrayList<ImageIcon> imagenesMostradas; // Imágenes mostradas al usuario
    private ArrayList<ImageIcon> imagenesSeleccionadas; // Imágenes seleccionadas por el usuario
    private int puntos; // Puntos del usuario

    public PantallaMemoria() {
        setTitle("Juego de Memoria");
        setSize(800, 600); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        tiempoRestante = 2; // 2 segundos para la cuenta regresiva
        imagenesMostradas = new ArrayList<>();
        imagenesSeleccionadas = new ArrayList<>();
        puntos = 0;

        try {
            // Cargar la imagen de fondo desde la ruta correcta y oscurecerla
            backgroundImage = ImageIO.read(getClass().getResource("/Images/Fondo.jpg"));
            float scaleFactor = 0.5f; // 1.0f es el brillo original, menos de 1.0f lo oscurece
            RescaleOp op = new RescaleOp(scaleFactor, 0, null);
            backgroundImage = op.filter(backgroundImage, null); // Aplica el filtro de oscurecimiento
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Panel principal con fondo oscurecido
        JPanel panelConFondo = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Dibujar la imagen de fondo oscurecida
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
        panelConFondo.setLayout(new GridBagLayout()); // Usamos GridBagLayout para centrar los cuadros

        // Crear un GridBagConstraints
        GridBagConstraints gbc = new GridBagConstraints();

        // Título
        JLabel titulo = new JLabel("¡Apréndete esto!", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 36));
        titulo.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(20, 0, 0, 0); // Relleno superior
        panelConFondo.add(titulo, gbc);

        // Crear un panel para los cuadros de imágenes más pequeños
        JPanel panelCuadros = new JPanel();
        panelCuadros.setLayout(new GridLayout(1, 5, 10, 10)); // 1 fila, 5 columnas, con espaciado entre cuadros
        panelCuadros.setOpaque(false);

        // Ruta de las imágenes
        String rutaImagenes = "src/main/resources/Images/";
        String[] nombresImagenes = {
            "imagen_ODS1.png", "imagen_ODS2.png", "imagen_ODS3.png", "imagen_ODS4.png",
            "imagen_ODS5.png", "imagen_ODS6.png", "imagen_ODS7.png", "imagen_ODS8.png",
            "imagen_ODS9.png", "imagen_ODS10.png", "imagen_ODS11.png", "imagen_ODS12.png",
            "imagen_ODS13.png", "imagen_ODS14.png", "imagen_ODS15.png", "imagen_ODS16.png", "imagen_ODS17.png"
        };

        ArrayList<ImageIcon> imagenes = new ArrayList<>();

        // Cargar todas las imágenes
        for (String nombreImagen : nombresImagenes) {
            imagenes.add(new ImageIcon(rutaImagenes + nombreImagen));
        }

        Random random = new Random();
        // Añadir cuadros más pequeños
        for (int i = 1; i <= 5; i++) {
            JPanel cuadro = new JPanel() {
                // Sobrescribir paintComponent para dibujar la imagen de fondo
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    // Seleccionar una imagen aleatoria
                    int index = random.nextInt(imagenes.size());
                    ImageIcon imagenFondo = imagenes.get(index);
                    System.out.println(imagenFondo);
                    imagenesMostradas.add(imagenFondo); // Guardar la imagen mostrada
                    // Dibujar la imagen
                    g.drawImage(imagenFondo.getImage(), 0, 0, getWidth(), getHeight(), this);
                }
            };
            
            cuadro.setPreferredSize(new Dimension(100, 100)); 
            cuadro.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            System.out.println(imagenesMostradas);

            panelCuadros.add(cuadro);
        }

        // Configurar restricciones para centrar el panel de cuadros
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        panelConFondo.add(panelCuadros, gbc);

        // Crear y añadir el temporizador
        temporizadorLabel = new JLabel("Tiempo: 2", SwingConstants.CENTER);
        temporizadorLabel.setFont(new Font("Arial", Font.BOLD, 24));
        temporizadorLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weighty = 0;
        gbc.insets = new Insets(20, 0, 20, 0); // Espaciado extra
        panelConFondo.add(temporizadorLabel, gbc);

        // Iniciar el temporizador
        iniciarTemporizador();

        // Añadir el panel con fondo a la ventana
        add(panelConFondo);
    }

    // Método para iniciar el temporizador
    private void iniciarTemporizador() {
        Timer timer = new Timer(1000, new ActionListener() { // Se ejecuta cada 1000 ms (1 segundo)
            @Override
            public void actionPerformed(ActionEvent e) {
                if (tiempoRestante > 0) {
                    tiempoRestante--;
                    temporizadorLabel.setText("Tiempo: " + tiempoRestante);
                } else {
                    ((Timer) e.getSource()).stop(); // Detener el temporizador cuando llegue a 0
                    cambiarPantalla(); // Llamar al método para cambiar de pantalla
                }
            }
        });
        timer.start(); // Iniciar el temporizador
    }

    private void cambiarPantalla() {
        // Cierra la ventana actual
        this.dispose();
    
        // Crear la nueva ventana
        JFrame nuevaPantalla = new JFrame("Nueva Pantalla");
        nuevaPantalla.setSize(800, 600);
        nuevaPantalla.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        nuevaPantalla.setLocationRelativeTo(null);
    
        // Cargar y oscurecer la imagen de fondo para la nueva pantalla
        try {
            nuevaImagenFondo = ImageIO.read(getClass().getResource("/Images/Fondo.jpg"));
            float scaleFactor = 0.5f; // 1.0f es el brillo original, menos de 1.0f lo oscurece
            RescaleOp op = new RescaleOp(scaleFactor, 0, null);
            nuevaImagenFondo = op.filter(nuevaImagenFondo, null); // Aplica el filtro de oscurecimiento
        } catch (IOException e) {
            e.printStackTrace();
        }
    
        // Crear el panel con fondo para la nueva pantalla
        JPanel panelConFondoNueva = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (nuevaImagenFondo != null) {
                    g.drawImage(nuevaImagenFondo, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        panelConFondoNueva.setLayout(new BorderLayout());
    
        // Panel central con 5 cuadros grises para mover las imágenes seleccionadas
        JPanel panelCentro = new JPanel();
        panelCentro.setLayout(new GridLayout(1, 5, 10, 10)); // Igual que el layout del scroll
        panelCentro.setOpaque(false); // Hacer el panel transparente
        panelCentro.setPreferredSize(new Dimension(600, 100)); // Ajustar tamaño del contenedor para los cuadros
    
        ArrayList<JPanel> cuadrosCentro = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            JPanel cuadro = new JPanel();
            cuadro.setPreferredSize(new Dimension(100, 100)); // Mismo tamaño de 100x100
            cuadro.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            cuadro.setBackground(Color.GRAY); // Fondo gris indicando que está vacío
            cuadrosCentro.add(cuadro);
            panelCentro.add(cuadro); // Añadir los cuadros grises al panel central
        }
    
        // Centrar el panel central en la pantalla
        JPanel panelCentroContainer = new JPanel(new GridBagLayout());
        panelCentroContainer.setOpaque(false); // Hacerlo transparente
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(20, 0, 20, 0); // Espaciado superior e inferior
        panelCentroContainer.add(panelCentro, gbc);
    
        panelConFondoNueva.add(panelCentroContainer, BorderLayout.CENTER);
    
        // Panel inferior con scroll para las imágenes pequeñas
        JPanel panelImagenes = new JPanel();
        panelImagenes.setLayout(new GridLayout(1, 0, 10, 10)); // Filas ajustables en scroll
        panelImagenes.setOpaque(false); // Hacer que el panel sea transparente
    
        // Crear el JScrollPane con panel transparente
        JScrollPane scrollPane = new JScrollPane(panelImagenes);
        scrollPane.setPreferredSize(new Dimension(800, 150)); // Tamaño ajustado para scroll
        scrollPane.setOpaque(false); // Hacer que el JScrollPane sea transparente
        scrollPane.getViewport().setOpaque(false); // Hacer que el contenido del viewport sea transparente
        scrollPane.setBorder(BorderFactory.createEmptyBorder()); // Eliminar el borde blanco del JScrollPane
    
        panelConFondoNueva.add(scrollPane, BorderLayout.SOUTH);
    
        // Ruta de las imágenes (esto es solo un ejemplo, cambia según sea necesario)
        String rutaImagenes = "src/main/resources/Images/";
        String[] nombresImagenes = {
            "imagen_ODS1.png", "imagen_ODS2.png", "imagen_ODS3.png", "imagen_ODS4.png",
            "imagen_ODS5.png", "imagen_ODS6.png", "imagen_ODS7.png", "imagen_ODS8.png",
            "imagen_ODS9.png", "imagen_ODS10.png", "imagen_ODS11.png", "imagen_ODS12.png",
            "imagen_ODS13.png", "imagen_ODS14.png", "imagen_ODS15.png", "imagen_ODS16.png", "imagen_ODS17.png"
        };
    
        ArrayList<ImageIcon> imagenes = new ArrayList<>();
        for (String nombreImagen : nombresImagenes) {
            ImageIcon icon = new ImageIcon(rutaImagenes + nombreImagen);
    
            // Redimensionar las imágenes a 100x100
            Image image = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            ImageIcon imagenRedimensionada = new ImageIcon(image);
    
            imagenRedimensionada.setDescription(rutaImagenes + nombreImagen);
            JButton botonImagen = new JButton(imagenRedimensionada);
            botonImagen.setPreferredSize(new Dimension(100, 100)); // Botones del scroll con tamaño 100x100
            botonImagen.setBorder(BorderFactory.createEmptyBorder()); // Eliminar bordes del botón
            botonImagen.setContentAreaFilled(false); // Evitar que se llene el fondo del botón
            botonImagen.setFocusPainted(false); // Quitar borde de foco al seleccionar
    
            botonImagen.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    moverImagenAlCentro(imagenRedimensionada, cuadrosCentro); // Mover imagen redimensionada
                    if (imagenesSeleccionadas.size() < 5) { // Verificar que no se hayan seleccionado 5 imágenes
                        imagenesSeleccionadas.add(imagenRedimensionada); // Guardar la imagen seleccionada
                        System.out.println("Imagen seleccionada: " + imagenRedimensionada.getDescription()); // Imprimir la imagen seleccionada
                        if (imagenesSeleccionadas.size() == 5) {
                            calcularPuntos(); // Calcular puntos al seleccionar 5 imágenes
                        }
                    } else {
                        System.out.println("Ya se han seleccionado 5 imágenes."); // Mensaje de error
                    }
                }
            });
            panelImagenes.add(botonImagen); // Añadir la imagen redimensionada al scroll
        }
    
        nuevaPantalla.add(panelConFondoNueva);
        nuevaPantalla.setVisible(true);
    }
    
    // Método para mover las imágenes al centro y escalarlas al tamaño del cuadro sin bordes
    private void moverImagenAlCentro(ImageIcon imagen, ArrayList<JPanel> cuadrosCentro) {
        for (JPanel cuadro : cuadrosCentro) {
            if (cuadro.getBackground() == Color.GRAY) { // Espacio vacío encontrado
                // Limpiar el contenido del panel (por si tiene algo anterior)
                cuadro.removeAll();
                
                // Obtener las dimensiones del cuadro central
                int anchoCuadro = cuadro.getWidth();
                int altoCuadro = cuadro.getHeight();
                
                // Escalar la imagen para que se ajuste al tamaño del cuadro
                Image imagenEscalada = imagen.getImage().getScaledInstance(anchoCuadro, altoCuadro, Image.SCALE_SMOOTH);
                ImageIcon imagenRedimensionada = new ImageIcon(imagenEscalada);
                
                // Crear un JLabel con la imagen escalada
                JLabel etiquetaImagen = new JLabel(imagenRedimensionada);
                
                // Establecer layout nulo para asegurarnos de que ocupa todo el espacio del panel
                cuadro.setLayout(null);
                etiquetaImagen.setBounds(0, 0, anchoCuadro, altoCuadro);  // Ajustar el tamaño del JLabel al tamaño del panel
                
                // Añadir la imagen al cuadro central
                cuadro.add(etiquetaImagen);

                // Refrescar el panel para que se muestre correctamente
                cuadro.revalidate();
                cuadro.repaint();

                // Cambiar el fondo para indicar que está ocupado
                cuadro.setBackground(Color.WHITE);
                break;
            }
        }
    }

    private void calcularPuntos() {
        for (int i = 0; i < imagenesMostradas.size() / 2; i++) {
            ImageIcon temp = imagenesMostradas.get(i);
            imagenesMostradas.set(i, imagenesMostradas.get(imagenesMostradas.size() - 1 - i));
            imagenesMostradas.set(imagenesMostradas.size() - 1 - i, temp);
        }
        // Asegurarse de que se comparan las imágenes correctas
        for (int i = 0; i < imagenesSeleccionadas.size(); i++) {
            if (imagenesSeleccionadas.get(i) != null) { // Verificar que la imagen no sea null
                System.out.println("Comparando: " + imagenesMostradas.get(i).getDescription() + " con " + imagenesSeleccionadas.get(i).getDescription()); // Imprimir comparación
                if (i < imagenesMostradas.size() && imagenesMostradas.get(i).getDescription().equals(imagenesSeleccionadas.get(i).getDescription())) {
                    puntos += 10; // 10 puntos por cada imagen correcta
                }
            } else {
                System.out.println("La imagen seleccionada en la comparación es null."); // Mensaje de error
            }
        }
        System.out.println("Puntos totales: " + puntos); // Imprimir puntos totales
        mostrarPuntos(); // Mostrar los puntos al usuario
    }

    private void mostrarPuntos() {
        // Mostrar mensaje con el puntaje
        JOptionPane.showMessageDialog(this, "Puntos: " + puntos);

        String[] nombresJugadores = {"jose","Charbel","Luis","Samuel"};
        int[] puntajes = {10,20,0,10};

    
        
        // Crear y mostrar la pantalla de puntajes
        PantallaPuntaje pantallaPuntaje = new PantallaPuntaje(nombresJugadores, puntajes); // Suponiendo que Puntaje recibe los puntos en el constructor
        pantallaPuntaje.setVisible(true);
        
        // Cerrar la ventana actual
        this.dispose();
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PantallaMemoria pantallaMemoria = new PantallaMemoria();
            pantallaMemoria.setVisible(true);
        });
    }
}