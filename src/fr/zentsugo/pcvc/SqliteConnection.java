package fr.zentsugo.pcvc;

import java.sql.*;
import javax.swing.*;

public class SqliteConnection {

	Connection connection = null;

	public static Connection dbConnector() {

		try {
			
			Class.forName("org.sqlite.JDBC");
			
			Connection connection = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\ZentsuGo\\Desktop\\AdminsData.sqlite");
			
			LoginWindow.lblServerConnection.setText("Server Connection : Yes");
			
			return connection;
			
		} catch (Exception e) {
			
			LoginWindow.lblServerConnection.setText("Server Connection : No");
			
			return null;
			
		}
		

	}

}
