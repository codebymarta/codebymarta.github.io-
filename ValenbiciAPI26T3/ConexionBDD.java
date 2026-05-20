

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;      
import java.sql.DriverManager;    
import java.sql.SQLException;
import java.sql.Statement; 

public class ConexionBDD extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField textFieldNEstaciones;
    private JTextArea textAreaDatos; 
    private JLabel lblNewLabel_2;    
    private JTextField txtPrimeroObtenerDatos;

    private static Connection con;
    private static Statement s;
    private static DatosJson dJSon; 

    private static final String driver = "com.mysql.cj.jdbc.Driver"; 
    private static final String user = "administrador; 
    private static final String pass = "administrador"; 
    private static final String url = "jdbc:mysql://localhost:3306/valenbicibd"; 

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ConexionBDD frame = new ConexionBDD();
                    frame.setVisible(true); 
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public ConexionBDD() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        setBounds(100, 100, 450, 350); 
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5)); 
        setContentPane(contentPane);
        contentPane.setLayout(null); 
        
        JLabel lblNewLabel = new JLabel("Introduce numero estaciones a consultar:");
        lblNewLabel.setBounds(10, 11, 275, 14); 
        contentPane.add(lblNewLabel);

        textFieldNEstaciones = new JTextField();
        textFieldNEstaciones.setBounds(314, 8, 86, 20); 
        contentPane.add(textFieldNEstaciones);
        textFieldNEstaciones.setColumns(10);
        
        // BOTÓN DATOS
        JButton btnNewButtonDatos = new JButton("Datos");
        btnNewButtonDatos.setBounds(22, 58, 110, 23); 
        btnNewButtonDatos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    int n = Integer.parseInt(textFieldNEstaciones.getText());
                    dJSon = new DatosJson(); 
                    dJSon.mostrarDatos(n);
                    textAreaDatos.setText(dJSon.getDatos());
                } catch (Exception ex) {
                    textAreaDatos.setText("Error: Introduce un número válido.");
                }
            }
        });
        contentPane.add(btnNewButtonDatos);

        textAreaDatos = new JTextArea();
        textAreaDatos.setBounds(182, 57, 237, 100); 
        contentPane.add(textAreaDatos);
        
        // BOTÓN CONECTAR
        JButton jButtonConectarBDD = new JButton("Conectar");
        jButtonConectarBDD.setBounds(22, 171, 110, 23); 
        jButtonConectarBDD.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                conector(); 
            }
        });
        contentPane.add(jButtonConectarBDD);

        lblNewLabel_2 = new JLabel("Estado Conexión:");
        lblNewLabel_2.setBounds(182, 171, 237, 23); 
        contentPane.add(lblNewLabel_2);
        
        // BOTÓN AÑADIR 
        JButton btnAnadir = new JButton("Añadir a BDD");
        btnAnadir.setBounds(22, 205, 110, 23); // [cite: 497]
        contentPane.add(btnAnadir);

        txtPrimeroObtenerDatos = new JTextField();
        txtPrimeroObtenerDatos.setEditable(false);
        txtPrimeroObtenerDatos.setText("Primero obtener datos de estaciones y conectar BDD");
        txtPrimeroObtenerDatos.setBounds(142, 206, 280, 20); 
        contentPane.add(txtPrimeroObtenerDatos);
        
        JButton btnCerrar = new JButton("Cerrar conexión");
        btnCerrar.setBounds(248, 237, 130, 23); 
        contentPane.add(btnCerrar);
    }

    public Connection conector() {
        con = null;
        try {
            Class.forName(driver); 
            con = DriverManager.getConnection(url, user, pass); 
            if (con != null) {
                lblNewLabel_2.setText("Estado Conexión: Éxito");
            }
            return con; 
        } catch (ClassNotFoundException | SQLException e) {
            lblNewLabel_2.setText("Error de conexión: " + e.getMessage());
            return null;
        }
    }
}

				