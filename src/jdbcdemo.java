import java.sql.*;
import javax.sql.*;

public class jdbcdemo{

public static void main(String args[]){
String dbtime;
String dbUrl = "jdbc:mysql://alan-wright.com/alanwrig_comics";
String dbClass = "com.mysql.jdbc.Driver";
String query = "Select * FROM users";

try {

Class.forName("com.mysql.jdbc.Driver");
Connection con = DriverManager.getConnection (dbUrl);
Statement stmt = con.createStatement();
ResultSet rs = stmt.executeQuery(query);

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