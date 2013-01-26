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
				String image_link = comic.getImgURL();
				
				//Query for the comic
				String query = "SELECT image_link FROM comics WHERE image_link = \"" + image_link + "\"";
				ResultSet rs = stmt.executeQuery(query);
				
				//If it exists in the DB, skip
				if(rs.first()){
					System.out.println("Found");
					continue;
				}
				
				//Otherwise, get all data
				String queryBegin = "INSERT into comics (";
				String queryEnd = "VALUES (";
				
				//Title
				String title = comic.getTitle();
				if(!title.equals(null)){
					queryBegin += "title";
					queryEnd += "'" + title.replaceAll("\'", "\\'") + "'";
				}
				
				//Generic Title
				String gen_title = comic.getGenericTitle();
				if(!gen_title.equals(null)){
					queryBegin += ", generic_title";
					queryEnd += ", '" + gen_title.replaceAll("\'", "\\'") + "'";
				}
				
				//Issue Num
				int issue = comic.getIssueNum();
				if(issue > -1){
					queryBegin += ", issue_num";
					queryEnd += ", " + issue;
				}
				
				//Short Description
				String short_descr = comic.getShortDescription();
				if(!short_descr.equals(null)){
					queryBegin += ", short_description";
					queryEnd += ", '" + short_descr.replaceAll("\'", "\\'") + "'";
				}
				
				//Publisher
				String publisher = comic.getPublisher();
				if(!publisher.equals(null)){
					queryBegin += ", publisher";
					queryEnd += ", '" + publisher.replaceAll("\'", "\\'") + "'";
				}
				
				//Print Date
				String print_date = comic.getPrintPubDateNum();
				if(!print_date.equals(null)){
					queryBegin += ", print_date";
					queryEnd += ", '" + print_date + "'";
				}
				
				//Digital Date
				String digital_date = comic.getDigitalPubDateNum();
				if(!digital_date.equals(null)){
					queryBegin += ", digital_date";
					queryEnd += ", '" + digital_date + "'";
				}
				
				//Thumbnail URL
				String thumb = comic.getthumbURL();
				if(!thumb.equals(null)){
					queryBegin += ", thumbnail";
					queryEnd += ", '" + thumb.replaceAll("\'", "\\'") + "'";
				}
				
				//Long Description
				String descr = comic.getLongDescription();
				if(!descr.equals(null)){
					queryBegin += ", long_description";
					queryEnd += ", '" + descr.replaceAll("\'", "\\'") + "'";
				}
				
				//Link URL
				String link = comic.getLink();
				if(!link.equals(null)){
					queryBegin += ", link";
					queryEnd += ", '" + link.replaceAll("\'", "\\'") + "'";
				}
				
				//Cover Artists
				ArrayList<String> coverArtists = comic.getCoverArtists();
				if(!coverArtists.isEmpty()){
					queryBegin += ", cover_artist1";
					queryEnd += ", '" + coverArtists.get(0).replaceAll("\'", "\\'") + "'";
				}
				
				//Authors
				ArrayList<String> authors = comic.getAuthors();
				if(!authors.isEmpty()){
					queryBegin += ", author1";
					queryEnd += ", '" + authors.get(0).replaceAll("\'", "\\'") + "'";
				}
				
				//Inker
				ArrayList<String> inker = comic.getInkers();
				if(!inker.isEmpty()){
					queryBegin += ", inker1";
					queryEnd += ", '" + inker.get(0).replaceAll("\'", "\\'") + "'";
				}
				
				//Colors
				ArrayList<String> color = comic.getColors();
				if(!color.isEmpty()){
					queryBegin += ", color1";
					queryEnd += ", '" + color.get(0).replaceAll("\'", "\\'") + "'";
				}
				
				//Number of pages
				int pages = comic.getPageCount();
				if(pages > 0){
					queryBegin += ", page_count";
					queryEnd += ", " + pages;
				}
				
				//Is the comic digital?
				boolean isDigital = comic.isDigital();
				queryBegin += ", isDigital) ";
				queryEnd += ", " + isDigital + ")";
				
				query = queryBegin + queryEnd;
				
				
				//query = "INSERT into comics (title, generic_title, issue_num, short_description, publisher, print_date, digital_date, thumbnail, long_description, link, image_link, page_count, isDigital)" +
				//		"VALUES(" + title + ", " + gen_title + ", " + issue + ", " + short_descr + ", " + publisher + ", "+ print_date + ", "+ digital_date + ", "+ thumb + ", " + descr + ", " + link + ", " + image_link + ", " + pages + ", " + isDigital + ")";
				
				//query = "INSERT into comics(title, generic_title, issue_num, image_link) VALUES('" + title +"', '" + gen_title +"', " + issue +", '" + comic.getImgURL() + "')";
				
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