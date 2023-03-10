
package Interfata;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import net.proteanit.sql.DbUtils;
import java.awt.Font;
import java.awt.Color;

public class InterfInterogariComplexe{

	JFrame frame;
	private JTable tabelComplexe;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InterfInterogariComplexe window = new InterfInterogariComplexe();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	Connection connection = null;
	private JTextField textFieldNume;
	private JTextField textFieldPrenume;
	// 
	/**
	 * Create the application.
	 * @wbp.parser.entryPoint
	 */
	public InterfInterogariComplexe() throws SQLException {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() throws SQLException {
		// conexiunea la baza de date
				connection = JavaConnect2SQL.dbConnector();
		//		
				frame = new JFrame();
				frame.getContentPane().setBackground(Color.WHITE);
				frame.setBounds(500, 150, 1300, 600);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.getContentPane().setLayout(null);
				
				textFieldNume = new JTextField();
				textFieldNume.setBounds(300, 505, 110, 25);
				frame.getContentPane().add(textFieldNume);
				textFieldNume.setColumns(10);
				
				textFieldPrenume = new JTextField();
				textFieldPrenume.setBounds(550, 505, 110, 25);
				frame.getContentPane().add(textFieldPrenume);
				textFieldPrenume.setColumns(10);
				
				JScrollPane scrollPane_1 = new JScrollPane();
				scrollPane_1.setBounds(12, 25, 1258, 145);
				frame.getContentPane().add(scrollPane_1);
				
				JScrollPane scrollPane = new JScrollPane();
				scrollPane_1.setViewportView(scrollPane);
				tabelComplexe = new JTable();
				scrollPane.setViewportView(tabelComplexe);
				tabelComplexe.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
					}
				});
				
				// afisare date din baza de date Angajat
				JButton btn1 = new JButton("Arata Raspunsul");
				btn1.setForeground(Color.WHITE);
				btn1.setFont(new Font("Calibri", Font.PLAIN, 16));
				btn1.setBackground(Color.BLACK);
				btn1.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						try{
							String querry = "SELECT cl.Nume, cl.Prenume, cl.Salariu, COUNT(*) AS num_clients FROM ( SELECT a.AngajatID, a.Nume, a.Prenume, Salariu FROM Angajat a JOIN Client c ON a.AngajatID = c.AngajatID ) AS cl GROUP BY cl.Nume, cl.Prenume, cl.Salariu ORDER BY num_clients DESC";
							PreparedStatement pst = connection.prepareStatement(querry);
							ResultSet rs = pst.executeQuery();
							tabelComplexe.setModel(DbUtils.resultSetToTableModel(rs));
							pst.close();
							rs.close();
							
						}catch (Exception e){
							e.printStackTrace();
						}
					}
				});
				//------------------------------------------------------------------------------
				
				btn1.setBounds(25, 230, 140, 30);
				frame.getContentPane().add(btn1);
				
				JLabel lblNewLabel = new JLabel("Afisati numele, prenumele si salariile angajatilor in functie de cati clienti au");
				lblNewLabel.setFont(new Font("Calibri", Font.PLAIN, 20));
				lblNewLabel.setBounds(25, 190, 1200, 35);
				frame.getContentPane().add(lblNewLabel);
				
				JLabel lblSaSeAfiseze = new JLabel("Sa se afiseze lista tuturor angajatilor si clientilor pe care ii reprezinta daca angajatii au salariul mai mare decat 2000");
				lblSaSeAfiseze.setFont(new Font("Calibri", Font.PLAIN, 20));
				lblSaSeAfiseze.setBounds(24, 280, 1246, 34);
				frame.getContentPane().add(lblSaSeAfiseze);
				
				JButton btn2 = new JButton("Arata Raspunsul");
				btn2.setForeground(Color.WHITE);
				btn2.setFont(new Font("Calibri", Font.PLAIN, 16));
				btn2.setBackground(Color.BLACK);
				btn2.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						try{
							String querry = "SELECT NumeAngajat, PrenumeAngajat, NumeClient, PrenumeClient FROM (SELECT a.Nume AS NumeAngajat, a.Prenume AS PrenumeAngajat, c.Nume AS NumeClient, c.Prenume AS PrenumeClient, a.Salariu FROM Angajat a JOIN Client c ON a.AngajatID = c.AngajatID) t WHERE t.Salariu	 > 2000;";
							PreparedStatement pst = connection.prepareStatement(querry);
							ResultSet rs = pst.executeQuery();
							tabelComplexe.setModel(DbUtils.resultSetToTableModel(rs));
							pst.close();
							rs.close();
							
						}catch (Exception e){
							e.printStackTrace();
						}
					}
				});
				btn2.setBounds(25, 320, 140, 30);
				frame.getContentPane().add(btn2);
				
				JLabel lblAfisatiDoarMasinile = new JLabel("Afisati doar masinile care nu au client id iar pretul lor este mai mic decat bugetul a cel putin 2 clienti");
				lblAfisatiDoarMasinile.setFont(new Font("Calibri", Font.PLAIN, 20));
				lblAfisatiDoarMasinile.setBounds(25, 370, 1246, 34);
				frame.getContentPane().add(lblAfisatiDoarMasinile);
				
				JLabel lblAfisazaNumelePrenumele = new JLabel("Afisaza numele, prenumele si modelele masinilor tuturor clientilor care sunt repartizati la angajatul ");
				lblAfisazaNumelePrenumele.setFont(new Font("Calibri", Font.PLAIN, 20));
				lblAfisazaNumelePrenumele.setBounds(25, 460, 1246, 34);
				frame.getContentPane().add(lblAfisazaNumelePrenumele);
				
				JButton btn3 = new JButton("Arata Raspunsul");
				btn3.setForeground(Color.WHITE);
				btn3.setFont(new Font("Calibri", Font.PLAIN, 16));
				btn3.setBackground(Color.BLACK);
				btn3.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						try{
							String querry = "SELECT Model, Pret, NumarClienti FROM (SELECT m.Model, m.Pret, (SELECT COUNT(c.ClientID) FROM Client c WHERE c.Buget > m.Pret) AS NumarClienti FROM Masina m WHERE m.Client_ID IS NULL) AS T WHERE NumarClienti >= 2;";
							PreparedStatement pst = connection.prepareStatement(querry);
							ResultSet rs = pst.executeQuery();
							tabelComplexe.setModel(DbUtils.resultSetToTableModel(rs));
							pst.close();
							rs.close();
							
						}catch (Exception e){
							e.printStackTrace();
						}
					}
				});
				btn3.setBounds(25, 410, 140, 30);
				frame.getContentPane().add(btn3);
				
				JButton btn4 = new JButton("Arata Raspunsul");
				btn4.setBackground(Color.BLACK);
				btn4.setForeground(Color.WHITE);
				btn4.setFont(new Font("Calibri", Font.PLAIN, 16));
				btn4.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						try{
							String querry = "SELECT c.Nume, c.Prenume, m.Model FROM Client c JOIN Masina m ON c.ClientID = m.Client_ID WHERE c.AngajatID = (SELECT AngajatID FROM Angajat WHERE Nume = ? AND Prenume = ?);";
							PreparedStatement pst = connection.prepareStatement(querry);
							pst.setString(1, textFieldNume.getText()); // Marean
							pst.setString(2, textFieldPrenume.getText()); // Norocel
							ResultSet rs = pst.executeQuery();
							tabelComplexe.setModel(DbUtils.resultSetToTableModel(rs));
							pst.close();
							rs.close();
							
						}catch (Exception e){
							e.printStackTrace();
						}
					}
				});
				btn4.setBounds(25, 500, 140, 30);
				frame.getContentPane().add(btn4);
				
				
				JLabel lblNume = new JLabel("Nume:");
				lblNume.setFont(new Font("Calibri", Font.PLAIN, 16));
				lblNume.setBounds(245, 505, 50, 25);
				frame.getContentPane().add(lblNume);
				
				JLabel lblPrenume = new JLabel("Prenume:");
				lblPrenume.setFont(new Font("Calibri", Font.PLAIN, 16));
				lblPrenume.setBounds(480, 505, 70, 25);
				frame.getContentPane().add(lblPrenume);
	}
}
