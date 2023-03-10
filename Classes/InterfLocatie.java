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
import java.awt.Color;
import java.awt.Font;

public class InterfLocatie {

	JFrame frame;
	private JTable tabelLocatie;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InterfLocatie window = new InterfLocatie();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	Connection connection = null;
	private JTextField textFieldAdresa;
	private JTextField textFieldProgram;
	private JTextField textFieldNumeLocatie;
	//

	/**
	 * Create the application.
	 */
	public InterfLocatie() throws SQLException {
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
				frame.setBounds(500, 200, 920, 670);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.getContentPane().setLayout(null);
				
				JScrollPane scrollPane_1 = new JScrollPane();
				scrollPane_1.setBounds(12, 13, 880, 400);
				frame.getContentPane().add(scrollPane_1);
				
				JScrollPane scrollPane = new JScrollPane();
				scrollPane_1.setViewportView(scrollPane);
				tabelLocatie = new JTable();
				tabelLocatie.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						// selectare date din tabel si popularea campurilor care ne ajuta la editare (Insert/Update)
						int row = tabelLocatie.getSelectedRow();
						textFieldNumeLocatie.setText(tabelLocatie.getModel().getValueAt(row, 1).toString());
						textFieldProgram.setText(tabelLocatie.getModel().getValueAt(row, 2).toString());
						textFieldAdresa.setText(tabelLocatie.getModel().getValueAt(row, 3).toString());
					}
				});
				scrollPane.setViewportView(tabelLocatie);
				
				// afisare date din baza de date Locatie
				JButton showDataBtn = new JButton("Show Data");
				showDataBtn.setFont(new Font("Calibri", Font.PLAIN, 20));
				showDataBtn.setForeground(Color.WHITE);
				showDataBtn.setBackground(Color.BLACK);
				showDataBtn.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						try{
							String querry = "select * from Locatie";
							PreparedStatement pst = connection.prepareStatement(querry);
							ResultSet rs = pst.executeQuery();
							tabelLocatie.setModel(DbUtils.resultSetToTableModel(rs));	
							tabelLocatie.removeColumn(tabelLocatie.getColumnModel().getColumn(0)); // ascunde ID-ul

							pst.close();
							rs.close();
							
						}catch (Exception e){
							e.printStackTrace();
						}
					}
				});
				//------------------------------------------------------------------------------
				
				showDataBtn.setBounds(555, 533, 170, 70);
				frame.getContentPane().add(showDataBtn);
				
				// Adaugare functionabilitate de stergere a datelor din baza de date 
				JButton deleteBtn = new JButton("Delete");
				deleteBtn.setFont(new Font("Calibri", Font.PLAIN, 20));
				deleteBtn.setForeground(Color.WHITE);
				deleteBtn.setBackground(Color.BLACK);
				deleteBtn.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
					try{
						int row = tabelLocatie.getSelectedRow();
						String value = tabelLocatie.getModel().getValueAt(row, 0).toString();
						String querry = "Delete from Locatie Where LocatieID="+value;
						PreparedStatement pst = connection.prepareStatement(querry);
						pst.execute();
						pst.close();
						showDataBtn.doClick();


						}
					catch (Exception e){
						e.printStackTrace();
					}
					}
				});
				deleteBtn.setBounds(180, 533, 170, 70);
				frame.getContentPane().add(deleteBtn);
				//
				
				// operatia de insert a noi date
				JButton insertBtn = new JButton("Insert");
				insertBtn.setForeground(Color.WHITE);
				insertBtn.setFont(new Font("Calibri", Font.PLAIN, 20));
				insertBtn.setBackground(Color.BLACK);
				insertBtn.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						try{
							String querry = "insert into Locatie(NumeLocatie, Program, Adresa) values (?, ?, ?)";
							PreparedStatement pst = connection.prepareStatement(querry);
							pst.setString(1, textFieldNumeLocatie.getText());
							pst.setString(2, textFieldProgram.getText());
							pst.setString(3, textFieldAdresa.getText());
							pst.execute();
							JOptionPane.showMessageDialog(null, "Data saved!");
							pst.close();
							showDataBtn.doClick();

						}catch (Exception e){
							e.printStackTrace();
						}
					}
				});
				insertBtn.setBounds(722, 533, 170, 70);
				frame.getContentPane().add(insertBtn);
				
				// configurare buton de update care sa faca update dupa id ul angajatului
				JButton updateBtn = new JButton("Update");
				updateBtn.setFont(new Font("Calibri", Font.PLAIN, 20));
				updateBtn.setForeground(Color.WHITE);
				updateBtn.setBackground(Color.BLACK);
				updateBtn.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try {
						int row = tabelLocatie.getSelectedRow();
						String idAngaj = tabelLocatie.getModel().getValueAt(row, 0).toString();
						String querry ="Update Locatie set NumeLocatie = ?, Program = ?, Adresa = ? Where LocatieID = " +idAngaj;
						PreparedStatement pst = connection.prepareStatement(querry);
						pst.setString(1, textFieldNumeLocatie.getText());
						pst.setString(2, textFieldProgram.getText());
						pst.setString(3, textFieldAdresa.getText());
						pst.executeUpdate();
						pst.close();
						JOptionPane.showMessageDialog(null, "Update reusit!");
						showDataBtn.doClick();
						
						
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				});
				updateBtn.setBounds(12, 533, 170, 70);
				frame.getContentPane().add(updateBtn);
				
				textFieldAdresa = new JTextField();
				textFieldAdresa.setBounds(650, 440, 240, 25);
				frame.getContentPane().add(textFieldAdresa);
				textFieldAdresa.setColumns(10);
				
				textFieldProgram = new JTextField();
				textFieldProgram.setBounds(348, 440, 120, 25);
				frame.getContentPane().add(textFieldProgram);
				textFieldProgram.setColumns(10);
				
				textFieldNumeLocatie = new JTextField();
				textFieldNumeLocatie.setBounds(62, 440, 120, 25);
				frame.getContentPane().add(textFieldNumeLocatie);
				textFieldNumeLocatie.setColumns(10);
				
				JLabel lblNume = new JLabel("Nume");
				lblNume.setFont(new Font("Calibri", Font.PLAIN, 16));
				lblNume.setBounds(12, 443, 60, 20);
				frame.getContentPane().add(lblNume);
				
				JLabel lblPrenume = new JLabel("Program");
				lblPrenume.setFont(new Font("Calibri", Font.PLAIN, 16));
				lblPrenume.setBounds(276, 443, 60, 20);
				frame.getContentPane().add(lblPrenume);
				
				JLabel lblAdresa = new JLabel("Adresa");
				lblAdresa.setFont(new Font("Calibri", Font.PLAIN, 16));
				lblAdresa.setBounds(578, 443, 60, 20);
				frame.getContentPane().add(lblAdresa);
	}

}
