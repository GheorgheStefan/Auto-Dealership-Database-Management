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

public class InterfClient {

	JFrame frame;
	private JTable tabelClient;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InterfClient window = new InterfClient();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	Connection connection = null;
	private JTextField textFieldTelefon;
	private JTextField textFieldPrenume;
	private JTextField textFieldNume;
	private JTextField textFieldBuget;
	// 
	/**
	 * Create the application.
	 */
	public InterfClient() throws SQLException {
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
				JComboBox<String> comboBox = new JComboBox();
				comboBox.setBackground(Color.BLACK);
				comboBox.setForeground(Color.WHITE);
				comboBox.setFont(new Font("Calibri", Font.PLAIN, 16));
				tabelClient = new JTable();
				tabelClient.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						// selectare date din tabel si popularea campurilor care ne ajuta la editare (Insert/Update)
						int row = tabelClient.getSelectedRow();
						textFieldNume.setText(tabelClient.getModel().getValueAt(row, 1).toString());
						textFieldPrenume.setText(tabelClient.getModel().getValueAt(row, 2).toString());
						textFieldTelefon.setText(tabelClient.getModel().getValueAt(row, 3).toString());
						textFieldBuget.setText(tabelClient.getModel().getValueAt(row, 4).toString());
						comboBox.setPrototypeDisplayValue("none");
					}
				});
				scrollPane.setViewportView(tabelClient);
				
				comboBox.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {			
					}
				});
				comboBox.setBounds(410, 500, 120, 25);
				frame.getContentPane().add(comboBox);
				
				// Populare comboBox cu id-urile angajatiilor----
				String name;
				Statement stm = connection.createStatement();
				ResultSet rs = stm.executeQuery("Select AngajatID from Angajat");
				
				while(rs.next()){
					name = rs.getString("AngajatID");
					comboBox.addItem(name);
				}
				stm.close();
				rs.close();
				//-----------------------------------------------
				
				// afisare date din baza de date Angajat
				JButton showDataBtn = new JButton("Show Data");
				showDataBtn.setForeground(Color.WHITE);
				showDataBtn.setFont(new Font("Calibri", Font.PLAIN, 20));
				showDataBtn.setBackground(Color.BLACK);
				showDataBtn.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						try{
							String querry = "select * from Client";
							PreparedStatement pst = connection.prepareStatement(querry);
							ResultSet rs = pst.executeQuery();
							tabelClient.setModel(DbUtils.resultSetToTableModel(rs));
							tabelClient.removeColumn(tabelClient.getColumnModel().getColumn(0));// Ascunde ID-ul
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
				deleteBtn.setForeground(Color.WHITE);
				deleteBtn.setFont(new Font("Calibri", Font.PLAIN, 20));
				deleteBtn.setBackground(Color.BLACK);
				deleteBtn.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
					try{
						int row = tabelClient.getSelectedRow();
						String value = tabelClient.getModel().getValueAt(row, 0).toString();
						String querry = "Delete from Client Where ClientID="+value;
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
							String querry = "insert into Client(Nume, Prenume, Telefon, Buget, AngajatID) values (?, ?, ?, ?, ?)";
							PreparedStatement pst = connection.prepareStatement(querry);
							pst.setString(1, textFieldNume.getText());
							pst.setString(2, textFieldPrenume.getText());
							pst.setString(3, textFieldTelefon.getText());
							pst.setString(4, textFieldBuget.getText());
							pst.setString(5, comboBox.getSelectedItem().toString());
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
				
				// configurare buton de update care sa faca update dupa id ul clientului
				JButton updateBtn = new JButton("Update");
				updateBtn.setForeground(Color.WHITE);
				updateBtn.setFont(new Font("Calibri", Font.PLAIN, 20));
				updateBtn.setBackground(Color.BLACK);
				updateBtn.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try {
						int row = tabelClient.getSelectedRow();
						String idClient = tabelClient.getModel().getValueAt(row, 0).toString();
						String querry ="Update Client set Nume = ?, Prenume = ?, Telefon = ?, Buget = ?, AngajatID = ? Where ClientID = " +idClient;
						PreparedStatement pst = connection.prepareStatement(querry);
						pst.setString(1, textFieldNume.getText());
						pst.setString(2, textFieldPrenume.getText());
						pst.setString(3, textFieldTelefon.getText());
						pst.setString(4, textFieldBuget.getText());
						String idLoc = comboBox.getSelectedItem().toString();
						pst.setString(5, idLoc);
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
				
				textFieldTelefon = new JTextField();
				textFieldTelefon.setBounds(740, 440, 120, 25);
				frame.getContentPane().add(textFieldTelefon);
				textFieldTelefon.setColumns(10);
				
				textFieldPrenume = new JTextField();
				textFieldPrenume.setBounds(270, 440, 120, 25);
				frame.getContentPane().add(textFieldPrenume);
				textFieldPrenume.setColumns(10);
				
				textFieldNume = new JTextField();
				textFieldNume.setBounds(60, 440, 120, 25);
				frame.getContentPane().add(textFieldNume);
				textFieldNume.setColumns(10);
				
				JLabel lblPtIdFk = new JLabel("AngajatID");
				lblPtIdFk.setFont(new Font("Calibri", Font.PLAIN, 16));
				lblPtIdFk.setBounds(340, 500, 70, 20);
				frame.getContentPane().add(lblPtIdFk);
				
				JLabel lblNume = new JLabel("Nume");
				lblNume.setFont(new Font("Calibri", Font.PLAIN, 16));
				lblNume.setBounds(12, 443, 60, 20);
				frame.getContentPane().add(lblNume);
				
				JLabel lblPrenume = new JLabel("Prenume");
				lblPrenume.setFont(new Font("Calibri", Font.PLAIN, 16));
				lblPrenume.setBounds(200, 443, 60, 20);
				frame.getContentPane().add(lblPrenume);
				
				JLabel lblNewLabel = new JLabel("Telefon");
				lblNewLabel.setFont(new Font("Calibri", Font.PLAIN, 16));
				lblNewLabel.setBounds(680, 443, 60, 20);
				frame.getContentPane().add(lblNewLabel);
				
				textFieldBuget = new JTextField();
				textFieldBuget.setColumns(10);
				textFieldBuget.setBounds(520, 440, 120, 25);
				frame.getContentPane().add(textFieldBuget);
				
				JLabel lblBuget = new JLabel("Buget");
				lblBuget.setFont(new Font("Calibri", Font.PLAIN, 16));
				lblBuget.setBounds(470, 443, 60, 20);
				frame.getContentPane().add(lblBuget);
	}
}
