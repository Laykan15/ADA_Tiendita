package API_almacen;

import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import API_almacen.Productos.DescuentoService;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JRadioButton;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import Gen_Code.Codigo;

public class Main extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtPrecio;
    private List<Productos> productos = new ArrayList<>(); // Lista para almacenar los productos
    private DescuentoService descuentoService = new DescuentoService(); // Instancia del servicio de descuento
    private Codigo Code= new Codigo();
    private String Codigo;
    private String Ticket;
    private String Cod_Ticket;
    private int var;
    private String Abuscar;
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
        setBounds(100, 100, 450, 469);
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
        btnAgregar.setBounds(162, 267, 147, 21);
        contentPane.add(btnAgregar);

        JButton btnPagar = new JButton("Pagar e imprimir");
        btnPagar.setBounds(162, 295, 147, 21);
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
        
        JLabel lblImagen = new JLabel("");
        lblImagen.setBounds(261, 97, 165, 167);
        contentPane.add(lblImagen);

        // Acción para seleccionar un producto y mostrar el precio
        comboProducto.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int index = comboProducto.getSelectedIndex();
                if (index >= 0) {
                    Productos productoSeleccionado = productos.get(index);
                    txtPrecio.setText(String.valueOf(productoSeleccionado.getPrecio()));
                }
                try {
                	Productos productoSeleccionado = productos.get(index);
                	String Produc = productoSeleccionado.getNombre();
    				String PS= Produc +".png";
    				BufferedImage imagen = null;
    				
    					imagen = ImageIO.read(new File("src/imagenes/"+ PS));//Cargar imagen dependiendo del modelo
    					ImageIcon icono = new ImageIcon(imagen);
    					lblImagen.setIcon(icono);
    				} catch (IIOException e1 ) {
    					e1.printStackTrace();
    				}catch (IOException e1 ) {
    					e1.printStackTrace();
    				}
            }
        });

        // Acción para añadir al carrito con descuento si aplica
        btnAgregar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int index = comboProducto.getSelectedIndex();
                if (index >= 0) {
                    Productos productoSeleccionado = productos.get(index);

                    descuentoService.aplicarDescuento(productoSeleccionado);
                    
                    Codigo = Code.Codigo_Produc(productoSeleccionado.getNombre(), productoSeleccionado.getCategoria(), (int) productoSeleccionado.getPrecio());
                   
                    textArea.append(productoSeleccionado.getNombre() + " - $" + productoSeleccionado.getPrecio() + " | "+Codigo + "\n");
                    
                    if(Ticket == null) {
                    	Ticket = Codigo;//Para buscar linea en bin
                    }else{
                    	
                    }
                    
                    
                    try {
                        BufferedWriter Guar_Bin = new BufferedWriter(new FileWriter("src/Archivos/Gen_Codi.txt", true)); // true para agregar al archivo
                        // String cadena = "En un lugar de la mancha";
                        
                        Guar_Bin.write(var + Codigo);  // Escribe el valor de 'var' y 'Codigo' en el archivo
                        //Guar_Bin.newLine();  // Agrega una nueva línea después de escribir
                        String cadena = Integer.toString(var);
                        Abuscar = cadena + Codigo;
                        
                        var++;  // Incrementa 'var' para la siguiente línea
                        
                        Guar_Bin.close();
                    } catch (IOException er) {
                        System.out.println(er.getMessage());
                        er.printStackTrace();
                    }
    			
                }
            }
        });

        // Acción para "pagar" e imprimir el contenido del carrito
        btnPagar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Carrito de compras:\n" + textArea.getText());
                try (BufferedWriter flujoSalida = new BufferedWriter(new FileWriter("src/Archivos/Gen_Codi.txt", true))) {
                    flujoSalida.newLine();// Agrega salto de linea, permitiendo la funcion de historial de conpras
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
               
                String lineaALeer = Abuscar;

                try (BufferedReader lector = new BufferedReader(new FileReader("src/Archivos/Gen_Codi.txt"))) {
                	
                    String linea;
                    
                    
                    while ((linea = lector.readLine()) != null) {
                        if (linea.contains(lineaALeer)) {  // Verifica si la línea contiene el texto buscado
                            //System.out.println("Línea encontrada: " + linea);
                            Cod_Ticket = linea;
                            System.out.println("Línea encontrada: " + Cod_Ticket);// Imprime la línea completa
                            break;  // Detiene la lectura después de encontrar la línea
                        }
                    }
                   
                    
                    JPanel panel = new JPanel();
                    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // Establecer layout vertical

                    
                    JLabel label2 = new JLabel("<html>" + textArea.getText().replace("\n", "<br><br>") + "</html>"); 
                    panel.add(label2);

                    // Agregar una imagen a un JLabel
                    try {
                        BufferedImage image = ImageIO.read(new File("src/imagenes/Codigo_Bar.png")); 
                        JLabel labelImagen = new JLabel(new ImageIcon(image));
                        panel.add(labelImagen);
                    } catch (IOException e9) {
                        e9.printStackTrace();
                    }

                    // Agregar un JLabel
                    JLabel label1 = new JLabel(Cod_Ticket); 
                    panel.add(label1);

                    // Mostrar el diálogo
                    JOptionPane.showMessageDialog(null, panel, "Ticket", JOptionPane.INFORMATION_MESSAGE);
                    
                   
                 } catch (IOException e3) {
                     e3.printStackTrace();
                 }
                
            }
        });
    }

    // Método para cargar los productos desde el archivo y llenar el JComboBox
    private void cargarProductos(JComboBox<String> comboProducto) {
        String Almacen = "src/Archivos/Almacen.txt"; // Ruta del archivo

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

