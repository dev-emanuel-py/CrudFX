package application;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBUtil {
	
	private static DBUtil instance;
	private static Connection connection = null;
	
	
	public DBUtil() {}
	
	public static DBUtil getInstance() {
		
		if (connection == null) {
			instance = new DBUtil();
		}
		return instance;
	}
	
	public Connection getConnection() {
		
		if(connection == null) {
			try {
				String driveName = "com.mysql.cj.jdbc.Driver";
				Class.forName(driveName);
				String url = "jdbc:mysql://localhost/crud";
				String user = "root";
				String senha = "root";
				connection = DriverManager.getConnection(url, user, senha);
			}catch(Exception e) {
				System.out.println("ERRO: "+e.toString());
			}
		}
		return connection;
	}
 
}
