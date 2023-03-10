
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

public class InterfInterogariSimple{

	JFrame frame;
	private JTable tabelSimple;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InterfInterogariSimple window = new InterfInterogariSimple();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	Connection connection = null;
	private JTextField text2000;
	// 
	/**
	 * Create the application.
	 * @wbp.parser.entryPoint
	 */
	public InterfInterogariSimple() throws SQLException {
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
				frame.getContentPane().setForeground(Color.WHITE);
				frame.setBounds(500, 100, 1300, 800);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.getContentPane().setLayout(null);
				
				text2000 = new JTextField();
				text2000.setBounds(790, 375, 70, 25);
				frame.getContentPane().add(text2000);
				text2000.setColumns(10);
				
				JScrollPane scrollPane_1 = new JScrollPane();
				scrollPane_1.setBounds(12, 25, 1258, 145);
				frame.getContentPane().add(scrollPane_1);
				
				JScrollPane scrollPane = new JScrollPane();
				scrollPane_1.setViewportView(scrollPane);
				tabelSimple = new JTable();
				scrollPane.setViewportView(tabelSimple);
				tabelSimple.addMouseListener(new MouseAdapter() {
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
							String querry = "SELECT Angajat.Nume FROM Angajat JOIN Client ON Angajat.AngajatID = Client.AngajatID GROUP BY Angajat.Nume HAVING COUNT(Client.ClientID) >= 2;";
							PreparedStatement pst = connection.prepareStatement(querry);
							ResultSet rs = pst.executeQuery();
							tabelSimple.setModel(DbUtils.resultSetToTableModel(rs));
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
				
				JLabel lblNewLabel = new JLabel("Afisati numele angajatului care cel putin 2 clienti");
				lblNewLabel.setFont(new Font("Calibri", Font.PLAIN, 20));
				lblNewLabel.setBounds(25, 190, 1200, 35);
				frame.getContentPane().add(lblNewLabel);
				
				JLabel lblAfisatiClientiiSi = new JLabel("Afisati clientii si cea mai scumpa masina pe care si-o pot permite");
				lblAfisatiClientiiSi.setFont(new Font("Calibri", Font.PLAIN, 20));
				lblAfisatiClientiiSi.setBounds(24, 280, 1246, 34);
				frame.getContentPane().add(lblAfisatiClientiiSi);
				
				JButton btn2 = new JButton("Arata Raspunsul");
				btn2.setBackground(Color.BLACK);
				btn2.setFont(new Font("Calibri", Font.PLAIN, 16));
				btn2.setForeground(Color.WHITE);
				btn2.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						try{
							String querry = "SELECT C.Nume, C.Prenume, M.Model FROM Client C JOIN Masina M ON M.Pret < C.Buget WHERE M.Client_ID IS NULL";
							PreparedStatement pst = connection.prepareStatement(querry);
							ResultSet rs = pst.executeQuery();
							tabelSimple.setModel(DbUtils.resultSetToTableModel(rs));
							pst.close();
							rs.close();
							
						}catch (Exception e){
							e.printStackTrace();
						}
					}
				});
				btn2.setBounds(25, 320, 140, 30);
				frame.getContentPane().add(btn2);
				
				JLabel lblAfisatNumelePrenumele = new JLabel("Afisat numele, prenumele si salariul angajatilor din locatia cu id 1 si au salariul cel putin de 2000");
				lblAfisatNumelePrenumele.setFont(new Font("Calibri", Font.PLAIN, 20));
				lblAfisatNumelePrenumele.setBounds(25, 370, 1246, 34);
				frame.getContentPane().add(lblAfisatNumelePrenumele);
				
				JLabel lblAfisatiNumarulDe = new JLabel("Afisati numarul de angajati din fiecare locatie cat si salariul mediu per locatie");
				lblAfisatiNumarulDe.setFont(new Font("Calibri", Font.PLAIN, 20));
				lblAfisatiNumarulDe.setBounds(25, 460, 1246, 34);
				frame.getContentPane().add(lblAfisatiNumarulDe);
				
				JLabel lblPentruFiecareMasina = new JLabel("Pentru fiecare masina din fiecare locatie afisati angajatul care o reprezinta si numele masinii cu conditia ca masina sa nu fie rezervata unui client");
				lblPentruFiecareMasina.setFont(new Font("Calibri", Font.PLAIN, 20));
				lblPentruFiecareMasina.setBounds(25, 550, 1246, 34);
				frame.getContentPane().add(lblPentruFiecareMasina);
				
				JLabel lblAratatiPrimi = new JLabel("Aratati primi 2 cei mai priceputi angajati (cei care au sub tutela cele mai multe masini) si numele locatiei unde se afla acestia");
				lblAratatiPrimi.setFont(new Font("Calibri", Font.PLAIN, 20));
				lblAratatiPrimi.setBounds(25, 640, 1246, 34);
				frame.getContentPane().add(lblAratatiPrimi);
				
				JButton btn3 = new JButton("Arata Raspunsul");
				btn3.setFont(new Font("Calibri", Font.PLAIN, 16));
				btn3.setForeground(Color.WHITE);
				btn3.setBackground(Color.BLACK);
				btn3.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						try{
							String querry = "SELECT Angajat.Nume, Angajat.Prenume, Angajat.Salariu FROM Angajat JOIN Locatie ON Angajat.LocatieID = Locatie.LocatieID Where Locatie.LocatieID = 1 GROUP BY Angajat.Nume, Angajat.Prenume, Angajat.Salariu, Locatie.NumeLocatie HAVING  Angajat.Salariu > ?;";
						
							PreparedStatement pst = connection.prepareStatement(querry);
							pst.setString(1, text2000.getText());
							ResultSet rs = pst.executeQuery();
							tabelSimple.setModel(DbUtils.resultSetToTableModel(rs));
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
				btn4.setForeground(Color.WHITE);
				btn4.setFont(new Font("Calibri", Font.PLAIN, 16));
				btn4.setBackground(Color.BLACK);
				btn4.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						try{
							String querry = "SELECT Locatie.NumeLocatie, COUNT(Angajat.AngajatID) AS NumarAngajati, AVG(Angajat.Salariu) AS SalariuMediu FROM Angajat JOIN Locatie ON Angajat.LocatieID = Locatie.LocatieID GROUP BY Locatie.NumeLocatie;";
							PreparedStatement pst = connection.prepareStatement(querry);
							ResultSet rs = pst.executeQuery();
							tabelSimple.setModel(DbUtils.resultSetToTableModel(rs));
							pst.close();
							rs.close();
							
						}catch (Exception e){
							e.printStackTrace();
						}
					}
				});
				btn4.setBounds(25, 500, 140, 30);
				frame.getContentPane().add(btn4);
				
				JButton btn5 = new JButton("Arata Raspunsul");
				btn5.setForeground(Color.WHITE);
				btn5.setFont(new Font("Calibri", Font.PLAIN, 16));
				btn5.setBackground(Color.BLACK);
				btn5.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						try{
							String querry = "SELECT Nume, Prenume, Model, NumeLocatie FROM Masina LEFT JOIN Angajat ON Masina.Angajat_ID = Angajat.AngajatID LEFT JOIN Locatie ON Masina.LocatieID = Locatie.LocatieID WHERE Client_ID IS NULL";
							PreparedStatement pst = connection.prepareStatement(querry);
							ResultSet rs = pst.executeQuery();
							tabelSimple.setModel(DbUtils.resultSetToTableModel(rs));
							pst.close();
							rs.close();
							
						}catch (Exception e){
							e.printStackTrace();
						}
					}
				});
				btn5.setBounds(25, 590, 140, 30);
				frame.getContentPane().add(btn5);
				
				JButton btn6 = new JButton("Arata Raspunsul");
				btn6.setBackground(Color.BLACK);
				btn6.setForeground(Color.WHITE);
				btn6.setFont(new Font("Calibri", Font.PLAIN, 16));
				btn6.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						try{
							String querry = "SELECT top 2 Angajat.Nume, Angajat.Prenume, Locatie.NumeLocatie, COUNT(Masina.MasinaID) AS NumarMasini FROM Angajat JOIN Masina ON Angajat.AngajatID = Masina.Angajat_ID JOIN Locatie ON Angajat.LocatieID = Locatie.LocatieID GROUP BY Angajat.Nume, Angajat.Prenume, Locatie.NumeLocatie ORDER BY COUNT(Masina.MasinaID) DESC";
							PreparedStatement pst = connection.prepareStatement(querry);
							ResultSet rs = pst.executeQuery();
							tabelSimple.setModel(DbUtils.resultSetToTableModel(rs));
							pst.close();
							rs.close();
							
						}catch (Exception e){
							e.printStackTrace();
						}
					}
				});
				btn6.setBounds(25, 680, 140, 30);
				frame.getContentPane().add(btn6);
				
	}
}
