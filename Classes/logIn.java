package Interfata;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.border.CompoundBorder;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.JSplitPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class logIn {

	private JFrame frame;
	private JFrame frmLoginSystem;
	private JTextField txtUsername;
	private JPasswordField txtPassword;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					logIn window = new logIn();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public logIn() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 700, 400); // initializare vereastra (distanta de la stanga la drepta, de sus in jos, latime, inaltime)
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setBackground(Color.BLACK);
		lblUsername.setBounds(125, 109, 63, 33);
		frame.getContentPane().add(lblUsername);
		
		JLabel lblParola = new JLabel("Parola:");
		lblParola.setBackground(Color.WHITE);
		lblParola.setBounds(125, 176, 63, 33);
		frame.getContentPane().add(lblParola);
		
		txtUsername = new JTextField();
		txtUsername.setBounds(210, 114, 219, 28);
		frame.getContentPane().add(txtUsername);
		txtUsername.setColumns(10);
		
		JButton btnNewButton = new JButton("LogIn");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String password = txtPassword.getText();
				String username = txtUsername.getText();
				
				if (password.contains("admin1") && username.contains("admin1")	){
					
					System.out.println("Ai reusit!");	
					Meniu.main(null);
					//Interogare.main(null);
					frame.setVisible(false); // 
					
				}
				else{
					JOptionPane.showMessageDialog(null,"Invalid", "Login error", JOptionPane.ERROR_MESSAGE);

				}

			}
		});
		btnNewButton.setBounds(125, 257, 97, 25);
		frame.getContentPane().add(btnNewButton);
		
		JLabel lblProiectBazeDe = new JLabel("Proiect Baze de Date");
		lblProiectBazeDe.setBounds(234, 44, 195, 33);
		frame.getContentPane().add(lblProiectBazeDe);
		
		txtPassword = new JPasswordField();
		txtPassword.setBounds(212, 178, 219, 28);
		frame.getContentPane().add(txtPassword);
		
		JButton btnNewButton_1 = new JButton("Reset");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtPassword.setText(null);
				txtUsername.setText(null);
			}
		});
		btnNewButton_1.setBounds(268, 257, 97, 25);
		frame.getContentPane().add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Exit");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				frmLoginSystem = new JFrame("Exit");
				if (JOptionPane.showConfirmDialog(frmLoginSystem,  "Doriti sa iesiti?" , "Login Systems",
						JOptionPane.YES_NO_OPTION)== JOptionPane.YES_NO_OPTION){
					System.exit(0);
				}
			}
		});
		btnNewButton_2.setBounds(414, 257, 97, 25);
		frame.getContentPane().add(btnNewButton_2);
	}
}
