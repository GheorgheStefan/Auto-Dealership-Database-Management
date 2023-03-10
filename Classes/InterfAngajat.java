package Interfata;

import java.awt.EventQueue;

import net.proteanit.sql.DbUtils;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

// necesare pentru conecarea la baza de date
import java.sql.*;
import java.util.Vector;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import net.proteanit.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Font;
import java.awt.Color; 

public class InterfAngajat {

	JFrame frame;
	private JTable tabelAngajat;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InterfAngajat window = new InterfAngajat();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	Connection connection = null;
	
	// Definire Campuri
	private JTextField textFieldSalariu;
	private JTextField textFieldAdresa;
	private JTextField textFieldTelefon;
	private JTextField textFieldPrenume;
	private JTextField textFieldNume;
	//
	
	/**
	 * Create the application.
	 * @throws SQLException 
	 */
	public InterfAngajat() throws SQLException {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws SQLException 
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
		JComboBox<String> comboBox = new JComboBox();
		comboBox.setFont(new Font("Calibri", Font.PLAIN, 16));
		comboBox.setForeground(Color.WHITE);
		comboBox.setBackground(Color.BLACK);
		tabelAngajat = new JTable();
		tabelAngajat.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// selectare date din tabel si popularea campurilor care ne ajuta la editare (Insert/Update)
				int row = tabelAngajat.getSelectedRow();
				textFieldNume.setText(tabelAngajat.getModel().getValueAt(row, 1).toString());
				textFieldPrenume.setText(tabelAngajat.getModel().getValueAt(row, 2).toString());
				textFieldTelefon.setText(tabelAngajat.getModel().getValueAt(row, 3).toString());
				textFieldAdresa.setText(tabelAngajat.getModel().getValueAt(row, 4).toString());
				textFieldSalariu.setText(tabelAngajat.getModel().getValueAt(row, 5).toString());
				String idAng = tabelAngajat.getModel().getValueAt(row, 6).toString();
				comboBox.setPrototypeDisplayValue("none");
				
				//System.out.println(tabelAngajat.getModel().getValueAt(row, 1).toString());
			}
		});
		scrollPane.setViewportView(tabelAngajat);
		
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {			
			}
		});
		comboBox.setBounds(760, 496, 120, 25);
		frame.getContentPane().add(comboBox);
		
		// Populare comboBox cu id-urile locatiilor----
		String name;
		Statement stm = connection.createStatement();
		ResultSet rs = stm.executeQuery("Select LocatieID from Locatie");
		
		while(rs.next()){
			name = rs.getString("LocatieID");
			comboBox.addItem(name);
		}
		stm.close();
		rs.close();
		//-----------------------------------------------
		
		// afisare date din baza de date Client
		JButton showDataBtn = new JButton("Show Data");
		showDataBtn.setFont(new Font("Calibri", Font.PLAIN, 20));
		showDataBtn.setForeground(Color.WHITE);
		showDataBtn.setBackground(Color.BLACK);
		showDataBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try{
					String querry = "select * from Angajat";
					PreparedStatement pst = connection.prepareStatement(querry);
					ResultSet rs = pst.executeQuery();
					tabelAngajat.setModel(DbUtils.resultSetToTableModel(rs));
					tabelAngajat.removeColumn(tabelAngajat.getColumnModel().getColumn(0));//Ascunde ID-ul
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
				int row = tabelAngajat.getSelectedRow();
				String value = tabelAngajat.getModel().getValueAt(row, 0).toString();
				String querry = "Delete from Angajat Where Angajatid="+value;
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
		insertBtn.setFont(new Font("Calibri", Font.PLAIN, 20));
		insertBtn.setForeground(Color.WHITE);
		insertBtn.setBackground(Color.BLACK);
		insertBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try{
					String querry = "insert into Angajat(Nume, Prenume, Telefon, Adresa, Salariu, LocatieID) values (?, ?, ?, ?, ?, ?)";
					PreparedStatement pst = connection.prepareStatement(querry);
					pst.setString(1, textFieldNume.getText());
					pst.setString(2, textFieldPrenume.getText());
					pst.setString(3, textFieldTelefon.getText());
					pst.setString(4, textFieldAdresa.getText());
					pst.setString(5, textFieldSalariu.getText());
					pst.setString(6, comboBox.getSelectedItem().toString());
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
				int row = tabelAngajat.getSelectedRow();
				String idAngaj = tabelAngajat.getModel().getValueAt(row, 0).toString();
				String querry ="Update Angajat set Nume = ?, Prenume = ?, Telefon = ?, Adresa = ?, Salariu = ?, LocatieID = ? Where AngajatID = " +idAngaj;
				PreparedStatement pst = connection.prepareStatement(querry);
				pst.setString(1, textFieldNume.getText());
				pst.setString(2, textFieldPrenume.getText());
				pst.setString(3, textFieldTelefon.getText());
				pst.setString(4, textFieldAdresa.getText());
				pst.setString(5, textFieldSalariu.getText());
				String idLoc = comboBox.getSelectedItem().toString();
				pst.setString(6, idLoc);
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
		
		textFieldSalariu = new JTextField();
		textFieldSalariu.setFont(new Font("Calibri", Font.PLAIN, 16));
		textFieldSalariu.setBounds(504, 440, 120, 25);
		frame.getContentPane().add(textFieldSalariu);
		textFieldSalariu.setColumns(10);
		
		textFieldAdresa = new JTextField();
		textFieldAdresa.setFont(new Font("Calibri", Font.PLAIN, 16));
		textFieldAdresa.setBounds(62, 495, 240, 25);
		frame.getContentPane().add(textFieldAdresa);
		textFieldAdresa.setColumns(10);
		
		textFieldTelefon = new JTextField();
		textFieldTelefon.setFont(new Font("Calibri", Font.PLAIN, 16));
		textFieldTelefon.setBounds(760, 440, 120, 25);
		frame.getContentPane().add(textFieldTelefon);
		textFieldTelefon.setColumns(10);
		
		textFieldPrenume = new JTextField();
		textFieldPrenume.setFont(new Font("Calibri", Font.PLAIN, 16));
		textFieldPrenume.setBounds(288, 440, 120, 25);
		frame.getContentPane().add(textFieldPrenume);
		textFieldPrenume.setColumns(10);
		
		textFieldNume = new JTextField();
		textFieldNume.setFont(new Font("Calibri", Font.PLAIN, 16));
		textFieldNume.setBounds(62, 440, 120, 25);
		frame.getContentPane().add(textFieldNume);
		textFieldNume.setColumns(10);
		
		JLabel lblPtIdFk = new JLabel("Locatie_ID");
		lblPtIdFk.setFont(new Font("Calibri", Font.PLAIN, 16));
		lblPtIdFk.setBounds(680, 499, 70, 20);
		frame.getContentPane().add(lblPtIdFk);
		
		JLabel lblNume = new JLabel("Nume");
		lblNume.setFont(new Font("Calibri", Font.PLAIN, 16));
		lblNume.setBounds(12, 443, 60, 20);
		frame.getContentPane().add(lblNume);
		
		JLabel lblPrenume = new JLabel("Prenume");
		lblPrenume.setFont(new Font("Calibri", Font.PLAIN, 16));
		lblPrenume.setBounds(220, 443, 60, 20);
		frame.getContentPane().add(lblPrenume);
		
		JLabel lblNewLabel = new JLabel("Telefon");
		lblNewLabel.setFont(new Font("Calibri", Font.PLAIN, 16));
		lblNewLabel.setBounds(690, 443, 60, 20);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblAdresa = new JLabel("Adresa");
		lblAdresa.setFont(new Font("Calibri", Font.PLAIN, 16));
		lblAdresa.setBounds(12, 498, 60, 20);
		frame.getContentPane().add(lblAdresa);
		
		JLabel lblNewLabel_1 = new JLabel("Salariu");
		lblNewLabel_1.setFont(new Font("Calibri", Font.PLAIN, 16));
		lblNewLabel_1.setBounds(455, 443, 60, 20);
		frame.getContentPane().add(lblNewLabel_1);
	}
}
