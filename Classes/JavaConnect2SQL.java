package Interfata;
import java.sql.*;
import javax.swing.*;

 
public class JavaConnect2SQL {
	Connection conn = null;
	public static  Connection dbConnector() {
	try{
		//Class.forName("");
		String url = "jdbc:sqlserver://DESKTOP-K7NGFJP\\SQLEXPRESS;databaseName=TemaBD;integratedSecurity=true;encrypt=false";
		Connection conn = DriverManager.getConnection(url, "Stefan", "1234");
		System.out.println("Conn Succesfull");
		return conn;
		
	}catch(SQLException e){
		System.out.println("Conn Failed");
		e.printStackTrace();
		return null;
	}
		
	}

}
