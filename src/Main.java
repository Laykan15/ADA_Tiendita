import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import API_almacen.Productos.DescuentoService;

public class Main extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtPrecio;
    private List<Productos> productos = new ArrayList<>(); // Lista para almacenar los productos
    private DescuentoService descuentoService = new DescuentoService(); // Instancia del servicio de descuento
    private JTextArea textArea; // Área de texto para mostrar el carrito

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Main frame = new Main();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public Main() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewLabel = new JLabel("Producto");
        lblNewLabel.setBounds(10, 68, 95, 13);
        contentPane.add(lblNewLabel);

        JComboBox<String> comboProducto = new JComboBox<String>();
        comboProducto.setBounds(75, 64, 131, 21);
        contentPane.add(comboProducto);

        JLabel lblPrecio = new JLabel("Precio:");
        lblPrecio.setBounds(216, 68, 65, 13);
        contentPane.add(lblPrecio);

        JButton btnAgregar = new JButton("Añadir al carrito");
        btnAgregar.setBounds(217, 93, 147, 21);
        contentPane.add(btnAgregar);

        JButton btnPagar = new JButton("Pagar e imprimir");
        btnPagar.setBounds(217, 136, 147, 21);
        contentPane.add(btnPagar);

        textArea = new JTextArea();
        textArea.setBounds(59, 91, 147, 147);
        contentPane.add(textArea);

        JLabel lblTitulo = new JLabel("Tiendita");
        lblTitulo.setBounds(162, 10, 65, 13);
        contentPane.add(lblTitulo);

        JRadioButton rdblimpieza = new JRadioButton("Limpieza");
        rdblimpieza.setBounds(10, 29, 103, 21);
        contentPane.add(rdblimpieza);

        JRadioButton rdbfiesta = new JRadioButton("Fiesta");
        rdbfiesta.setBounds(141, 29, 103, 21);
        contentPane.add(rdbfiesta);

        JRadioButton rdbbebes = new JRadioButton("Bebes");
        rdbbebes.setBounds(261, 29, 103, 21);
        contentPane.add(rdbbebes);

        txtPrecio = new JTextField();
        txtPrecio.setBounds(268, 65, 96, 19);
        contentPane.add(txtPrecio);
        txtPrecio.setColumns(10);

        // Cargar productos desde el archivo al iniciar la GUI
        cargarProductos(comboProducto);

        // Acción para seleccionar un producto y mostrar el precio
        comboProducto.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int index = comboProducto.getSelectedIndex();
                if (index >= 0) {
                    Productos productoSeleccionado = productos.get(index);
                    txtPrecio.setText(String.valueOf(productoSeleccionado.getPrecio()));
                }
            }
        });

        // Acción para añadir al carrito con descuento si aplica
        btnAgregar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int index = comboProducto.getSelectedIndex();
                if (index >= 0) {
                    Productos productoSeleccionado = productos.get(index);

                    // Aplicar descuentos si se seleccionó una categoría
                    if (rdblimpieza.isSelected()) {
                        productoSeleccionado.setCategoria("limpieza");
                    } if (rdbbebes.isSelected()) {
                        productoSeleccionado.setCategoria("Bebé");
                    } else if (rdbfiesta.isSelected()) {
                        productoSeleccionado.setCategoria("fiesta");
                    }

                    descuentoService.aplicarDescuento(productoSeleccionado);
                    textArea.append(productoSeleccionado.getNombre() + " - $" + productoSeleccionado.getPrecio() + "\n");
                }
            }
        });

        // Acción para "pagar" e imprimir el contenido del carrito
        btnPagar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Carrito de compras:\n" + textArea.getText());
            }
        });
    }

    // Método para cargar los productos desde el archivo y llenar el JComboBox
    private void cargarProductos(JComboBox<String> comboProducto) {
        String Almacen = "src/API_almacen/Almacen.txt"; // Ruta del archivo

        try (BufferedReader br = new BufferedReader(new FileReader(Almacen))) {
            String linea;
            br.readLine(); // Omitir la primera línea si es encabezado
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(","); 
                
                // Verificar que la línea tenga al menos tres elementos
                if (datos.length >= 3) {
                    String nombre = datos[0].trim();
                    String categoria = datos[1].trim();
                    double precio = Double.parseDouble(datos[2].trim());
                    Productos producto = new Productos(nombre, categoria, precio);
                    productos.add(producto); // Añadir el producto a la lista
                    comboProducto.addItem(nombre); // Añadir el nombre del producto al JComboBox
                } else {
                    System.out.println("Línea malformada: " + linea);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

