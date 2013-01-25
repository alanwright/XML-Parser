import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.sql.*;

public class MySqlUpdater{
	String dbtime;
	String dbUrl = "jdbc:mysql://alan-wright.com/alanwrig_comics";
	String username = "alanwrig";
	String password = "@123Victory";
	String dbClass = "com.mysql.jdbc.Driver";
	
	public MySqlUpdater(List<xmlItem> xmlItems) {
		
		try {
			
			Class.forName("com.mysql.jdbc.Driver");
			
			//Open connection
			Connection con = DriverManager.getConnection (dbUrl, username, password);
			
			//Create statement
			Statement stmt = con.createStatement();
			int count = -1;
			for(xmlItem comic : xmlItems){
				
				count++;
				if(count > 10)
					break;
				
				//Get the imgurl for the comic, this should be unique.
				System.out.println(comic.getImgURL());
				String image_link = comic.getImgURL();
				System.out.println(image_link);
				
				//Query for the comic
				String query = "SELECT image_link FROM comics WHERE image_link = \"" + image_link + "\"";
				ResultSet rs = stmt.executeQuery(query);
				
				//If it exists in the DB, skip
				if(rs.first()){
					System.out.println("Found");
					continue;
				}
				
				//Otherwise, get all data
				String title = comic.getTitle();
				String gen_title = comic.getGenericTitle();
				int issue = comic.getIssueNum();
				//String short_descr = comic.getShortDescription();
				//String publisher = comic.getPublisher();
				//String print_date = comic.getPrintPubDateNum();
				//String digital_date = comic.getDigitalPubDateNum();
				//String thumb = comic.getthumbURL();
				//String descr = comic.getLongDescription();
				//String link = comic.getLink();
				//ArrayList<String> coverArtists = comic.getCoverArtists();
				//if(coverArtists.size() > 0)
				//	String cover = coverArtists.get(0);
				//String author = comic.getAuthors().get(0);
				//String inker = comic.getInkers().get(0);
				//String color = comic.getColors().get(0);
				//int pages = comic.getPageCount();
				//boolean isDigital = comic.isDigital();
				
				//query = "INSERT into comics (title, generic_title, issue_num, short_description, publisher, print_date, digital_date, thumbnail, long_description, link, image_link, page_count, isDigital)" +
				//		"VALUES(" + title + ", " + gen_title + ", " + issue + ", " + short_descr + ", " + publisher + ", "+ print_date + ", "+ digital_date + ", "+ thumb + ", " + descr + ", " + link + ", " + image_link + ", " + pages + ", " + isDigital + ")";
				
				query = "INSERT into comics(title, generic_title, issue_num, image_link) VALUES('" + title +"', '" + gen_title +"', " + issue +", '" + comic.getImgURL() + "')";
				
				System.out.println(query);
				stmt.executeUpdate(query);
				//rs = stmt.executeQuery(query);
				
			}
			con.close();
		} //end try
		
			catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}

	public static void main(String args[]){
//		String dbtime;
//		String dbUrl = "jdbc:mysql://alan-wright.com/alanwrig_comics";
//		String username = "alanwrig";
//		String password = "@123Victory";
//		String dbClass = "com.mysql.jdbc.Driver";
//		String queryAll = "Select * FROM comics";
//		String resetId = "SET insert_id = 2";
//		
//		try {
//		
//			Class.forName("com.mysql.jdbc.Driver");
//			
//			//Open connection
//			Connection con = DriverManager.getConnection (dbUrl, username, password);
//			
//			//Create statement
//			Statement stmt = con.createStatement();
//			
//			String query = "SELECT image_link FROM comics WHERE image_link = \"temp\"";
//			ResultSet rs = stmt.executeQuery(query);
//			
//			if(rs.first())
//				System.out.println("Found");
//			
//			//stmt.executeUpdate("INSERT into comics (title, link, date) VALUES('title', 'link', '2012-2-2') ");
//			//rs = stmt.executeQuery(query);
//			
//			while (rs.next()) {
//				dbtime = rs.getString(1);
//				System.out.println(dbtime);
//			} //end while
//		
//			con.close();
//		} //end try
//		
//			catch(ClassNotFoundException e) {
//			e.printStackTrace();
//		}
//		catch(SQLException e) {
//			e.printStackTrace();
//		}
//	
	}  //end main

}  //end class