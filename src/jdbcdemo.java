import java.sql.*;
import javax.sql.*;

public class jdbcdemo{

	public static void main(String args[]){
		String dbtime;
		String dbUrl = "jdbc:mysql://alan-wright.com/alanwrig_comics";
		String username = "alanwrig";
		String password = "@123Victory";
		String dbClass = "com.mysql.jdbc.Driver";
		String query = "Select * FROM comics";
		String resetId = "SET insert_id = 2";
		
		try {
		
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection (dbUrl, username, password);
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(resetId);
			stmt.executeUpdate("INSERT into comics (title, link, date) VALUES('title', 'link', '2012-2-2') ");
			rs = stmt.executeQuery(query);
			
			while (rs.next()) {
				dbtime = rs.getString(1);
				System.out.println(dbtime);
			} //end while
		
			con.close();
		} //end try
		
			catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	
	}  //end main

}  //end class