import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JRadioButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

public class Main extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtPrecio;

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
		
		JComboBox comboProducto = new JComboBox();
		comboProducto.setBounds(75, 64, 131, 21);
		contentPane.add(comboProducto);
		
		JLabel lblPrecio = new JLabel("Precio:");
		lblPrecio.setBounds(216, 68, 65, 13);
		contentPane.add(lblPrecio);
		
		JButton btnAgregar = new JButton("Añadir al carrito");
		btnAgregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnAgregar.setBounds(217, 93, 147, 21);
		contentPane.add(btnAgregar);
		
		JButton btnPagar = new JButton("Pagar e imprimir");
		btnPagar.setBounds(217, 136, 147, 21);
		contentPane.add(btnPagar);
		
		JTextArea textArea = new JTextArea();
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
	}
}
