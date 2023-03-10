package Interfata;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.UIManager;

public class Meniu {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Meniu window = new Meniu();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws SQLException 
	 */
	public Meniu() throws SQLException {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws SQLException 
	 */
	private void initialize() throws SQLException {
		InterfAngajat interfAng = new InterfAngajat();
		InterfLocatie interfLoc = new InterfLocatie();
		InterfClient interfCli = new InterfClient();
		InterfInterogariSimple interfIS = new InterfInterogariSimple();
		InterfInterogariComplexe interfIC = new InterfInterogariComplexe();
		
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.WHITE);
		frame.getContentPane().setForeground(Color.BLACK);
		frame.setBounds(10, 100, 500, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton angajatBtn = new JButton("Angajat");
		angajatBtn.setForeground(Color.WHITE);
		angajatBtn.setBackground(Color.BLACK);
		angajatBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				interfAng.frame.setVisible(true);
				interfLoc.frame.setVisible(false);
				interfCli.frame.setVisible(false);
				interfIS.frame.setVisible(false);
				interfIC.frame.setVisible(false);
				//InterfAngajat.main(null);
			}
		});
		angajatBtn.setBounds(180, 100, 150, 50);
		frame.getContentPane().add(angajatBtn);
		
		JButton clientBtn = new JButton("Client");
		clientBtn.setForeground(Color.WHITE);
		clientBtn.setBackground(Color.BLACK);
		clientBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				interfAng.frame.setVisible(false);
				interfLoc.frame.setVisible(false);
				interfCli.frame.setVisible(true);
				interfIS.frame.setVisible(false);
				interfIC.frame.setVisible(false);
				//InterfClient.main(null);
			}
		});
		clientBtn.setBounds(180, 150, 150, 50);
		frame.getContentPane().add(clientBtn);
		
		JButton locatieBtn = new JButton("Locatie");
		locatieBtn.setForeground(Color.WHITE);
		locatieBtn.setBackground(Color.BLACK);
		locatieBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				interfAng.frame.setVisible(false);
				interfLoc.frame.setVisible(true);
				interfCli.frame.setVisible(false);
				interfIS.frame.setVisible(false);
				interfIC.frame.setVisible(false);
			}
		});
		locatieBtn.setBounds(180, 200, 150, 50);
		frame.getContentPane().add(locatieBtn);
		
		JButton masinaBtn = new JButton("Masina");
		masinaBtn.setBackground(Color.BLACK);
		masinaBtn.setForeground(Color.WHITE);
		masinaBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		masinaBtn.setBounds(180, 250, 150, 50);
		frame.getContentPane().add(masinaBtn);
		
		JButton configuratieBtn = new JButton("Configuratie");
		configuratieBtn.setForeground(Color.WHITE);
		configuratieBtn.setBackground(Color.BLACK);
		configuratieBtn.setBounds(180, 350, 150, 50);
		frame.getContentPane().add(configuratieBtn);
		
		JButton dotariBtn = new JButton("Dotari");
		dotariBtn.setForeground(Color.WHITE);
		dotariBtn.setBackground(Color.BLACK);
		dotariBtn.setBounds(180, 300, 150, 50);
		frame.getContentPane().add(dotariBtn);
		
		JLabel lblCampuriDisponibileAle = new JLabel("Campuri disponibile ale bazei de date");
		lblCampuriDisponibileAle.setHorizontalAlignment(SwingConstants.CENTER);
		lblCampuriDisponibileAle.setForeground(Color.BLACK);
		lblCampuriDisponibileAle.setFont(new Font("Calibri", Font.BOLD, 20));
		lblCampuriDisponibileAle.setBounds(12, 24, 458, 61);
		frame.getContentPane().add(lblCampuriDisponibileAle);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.DARK_GRAY);
		panel.setBounds(12, 629, 458, 111);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("*apasati pe budonul din sectiunea dorita pentru a putea vedea datele din\r\n");
		lblNewLabel.setToolTipText("");
		lblNewLabel.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setBounds(10, 10, 434, 22);
		panel.add(lblNewLabel);
		
		JLabel lblBazaDeDate = new JLabel("baza de date a obiectului respectiv. De asemenea datele pot fi sterse sau");
		lblBazaDeDate.setLabelFor(lblNewLabel);
		lblBazaDeDate.setVerticalAlignment(SwingConstants.TOP);
		lblBazaDeDate.setToolTipText("");
		lblBazaDeDate.setForeground(Color.WHITE);
		lblBazaDeDate.setBounds(10, 30, 434, 22);
		panel.add(lblBazaDeDate);
		
		JLabel lblEditate = new JLabel("editate");
		lblEditate.setVerticalAlignment(SwingConstants.TOP);
		lblEditate.setToolTipText("");
		lblEditate.setForeground(Color.WHITE);
		lblEditate.setBounds(10, 50, 434, 22);
		panel.add(lblEditate);
		
		JButton btnInterogSimple = new JButton("Interogari Simple");
		btnInterogSimple.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				interfAng.frame.setVisible(false);
				interfLoc.frame.setVisible(false);
				interfCli.frame.setVisible(false);
				interfIS.frame.setVisible(true);
				interfIC.frame.setVisible(false);
			}
		});
		btnInterogSimple.setForeground(Color.WHITE);
		btnInterogSimple.setBackground(Color.BLACK);
		btnInterogSimple.setBounds(180, 400, 150, 50);
		frame.getContentPane().add(btnInterogSimple);
		
		JButton btnInterogComplexe = new JButton("Interogari Complexe");
		btnInterogComplexe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				interfAng.frame.setVisible(false);
				interfLoc.frame.setVisible(false);
				interfCli.frame.setVisible(false);
				interfIS.frame.setVisible(false);
				interfIC.frame.setVisible(true);
			}
		});
		btnInterogComplexe.setBackground(Color.BLACK);
		btnInterogComplexe.setForeground(Color.WHITE);
		btnInterogComplexe.setBounds(180, 450, 150, 50);
		frame.getContentPane().add(btnInterogComplexe);
	}
}
