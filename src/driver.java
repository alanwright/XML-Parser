import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class driver{
	
	private static String XML_URL = "http://feeds.feedburner.com/ComixologyDigitalComics?fmt=xml";
	private static boolean PRINT_OUT = true;
	private static long totalTime = 0;
	private static List<xmlItem> xmlItems;
	
	//Testing...
	public static void main(String[] args){
		
		try{
			//Parse
			xmlParser xmlReader = new xmlParser(XML_URL);
			long initTime = System.currentTimeMillis();
			xmlItems = xmlReader.getItems();
			long finalTime = System.currentTimeMillis();
			
			System.out.println("XML parsing of " + xmlItems.size() + " comics complete in " +
					(finalTime - initTime) + " milliseconds.");
			
			//HTML Parsing
			int count = 0;
			for(xmlItem comic : xmlItems){
				try{
					Document doc = Jsoup.connect(xmlItems.get(count).getLink()).get();
					
					totalTime += parseImage(comic, doc);
					totalTime += parseLongDescription(comic, doc);
					totalTime += parseCredits(comic, doc);
					
					//Backspace last print
					if(count > 99)
						System.out.print("\b\b\b");
					else if(count > 9)
						System.out.print("\b\b");
					else if(count > 0)
						System.out.print("\b");
				} catch(SocketTimeoutException e){
					System.out.println("timeout: " + count);
				}

				count++;
				System.out.print(count);
			}
			
			totalTime/=1000;
			System.out.println("HTML parsing of " + count + " comics complete in " + totalTime + " seconds.");
			
		} catch(Exception e){
			e.printStackTrace();
			System.out.println("Could not parse XML File");
			return;
		}
		
		if(PRINT_OUT){
			try{
				System.out.print("Writing output to file...");
				//Open Output
				FileOutputStream fstream = new FileOutputStream("XML_Parse_out.txt");
				PrintStream out = new PrintStream(fstream);
				
				//Print items
				int count = 0;
				for(xmlItem x : xmlItems){
					out.println(x.toString());
					
					count++;
				}
				
				//Finish
				out.close();
				System.out.println("Complete!");
			} catch(FileNotFoundException e){
				System.out.println("File not found!");
			}
		}
		
	}
	
public static long parseImage(xmlItem comic, Document doc){
		
		long initTime = System.currentTimeMillis();
		
		//paragraph with a class
		Elements pics = doc.select("img[src]");
		
		for(Element x: pics){
			Elements temp =  x.getElementsByAttributeValue("id", "max_img1");
			if(temp.size() != 0){
				//System.out.println("! " +temp.attr("src"));
				String url = temp.attr("src");
				try{
					BufferedImage image = ImageIO.read(new URL(url));
	
					ImageIO.write(image, "jpg" , new File("out1.jpg"));
				}catch(IOException e){
					System.out.println("Could not save image @ url: " + url);
				}
				break;
			}
		}
		
		return System.currentTimeMillis() - initTime;
	}
	
	public static long parseLongDescription(xmlItem comic, Document doc){
		
		long initTime = System.currentTimeMillis();
		
		//paragraph with a class
		Elements paragraphs = doc.select("p[class]");
		
		//Loop through all p tags
		for(Element x: paragraphs){
			
			//Get the p with the right class
			Elements temp =  x.getElementsByAttributeValue("class", "description_text");
			if(temp.size() != 0){
				comic.setLongDescription(x.text());
				break;
			}
		}
		
		return System.currentTimeMillis() - initTime;
	}
	
public static long parseCredits(xmlItem comic, Document doc){
		
		long initTime = System.currentTimeMillis();
		
		//heading tags
		Elements h4s = doc.select("h4[class]");
		
		//Indicate if the field is provided
		boolean cover = false, author = false, artist = false, inker = false, colors = false, pages = false, printDate = false, digitalDate = false;
		
		//Loop through all h4 tags
		int numTrue = 0;
		for(Element x: h4s){
			
			//Get the h4 for credits
			Elements temp =  x.getElementsByAttributeValue("class", "item_header");
			
			//Check if cover artist is provided
			if(temp.text().equalsIgnoreCase("Cover by")){
				cover = true;
				numTrue++;
			}
			//Check if authors are provided
			else if(temp.text().equalsIgnoreCase("Written by")){
				author = true;
				numTrue++;
			}
			//Check if Artist is provided
			else if(temp.text().equalsIgnoreCase("Art by") || temp.text().equalsIgnoreCase("Pencils") ){
				artist = true;
				numTrue++;
			}
			//Check if Inkers are provided
			else if(temp.text().equalsIgnoreCase("Inks")){
				inker = true;
				numTrue++;
			}
			//Check if colors are provided
			else if(temp.text().equalsIgnoreCase("Colored by")){
				colors = true;
				numTrue++;
			}
			//Check for pages
			else if(temp.text().equalsIgnoreCase("Page Count")){
				pages = true;
				numTrue++;
			}
			//Check for release date. 
			else if(temp.text().equalsIgnoreCase("Print Release Date")){
				printDate = true;
				numTrue++;
			}
			//Check for digital release date
			else if(temp.text().equalsIgnoreCase("Digital Release Date")){
				digitalDate = true;
				numTrue++;
			}
		}
		
		//Debug
		//System.out.println(cover + " " + author + " " + artist + " " + inker + " " + colors);
		
		//Now we know what lists there are
		//Parse in order of: cover, author, artist, inker, colors
		Elements lists = doc.select("ul[class]");
		
		//Loop through unordered lists
		int count = 0;
		for(Element x : lists){
			
			Elements temp =  x.getElementsByAttributeValue("class", "credits-list");
			
			if(temp.size() > 0){
				if(cover && count == 0){
					String[] names = x.getElementsByTag("a").html().split("\\\n");
					//System.out.println("Cover: " + Arrays.toString(names));
					comic.setCoverArtists(new ArrayList<String>(Arrays.asList(names)));
					count++;
					cover = false;
				}
				else if( author && count <= 1){
					String[] names = x.getElementsByTag("a").html().split("\\\n");
					//System.out.println("Author: " + Arrays.toString(names));
					comic.setAuthors(new ArrayList<String>(Arrays.asList(names)));
					count++;
					author = false;
				}
				else if(artist && count <= 2){
					String[] names = x.getElementsByTag("a").html().split("\\\n");
					//System.out.println("Artist: " + Arrays.toString(names));
					comic.setArtists(new ArrayList<String>(Arrays.asList(names)));
					count++;
					artist = false;
				}
				else if(inker && count <=3){
					String[] names = x.getElementsByTag("a").html().split("\\\n");
					//System.out.println("Inker: " + Arrays.toString(names));
					comic.setInkers(new ArrayList<String>(Arrays.asList(names)));
					count++;
					inker = false;
				}
				else if(colors && count <= 4){
					String[] names = x.getElementsByTag("a").html().split("\\\n");
					//System.out.println("Colors: " + Arrays.toString(names));
					comic.setColors(new ArrayList<String>(Arrays.asList(names)));
					count++;
					colors = false;
					break;
				}
				else if(count+3 >=numTrue){
					break;
				}
			}
		}
			
		//Get about comics
		Elements spans = doc.select("span[class]");
		
		//Loop through about comics
		count = 0;
		for(Element x : spans){
			Elements temp =  x.getElementsByAttributeValue("class", "cmx_itemPageCount");
			
			if(temp.size() > 0){
				if(pages && count == 0){
					//Format: "XXX Pages"
					int index = temp.text().indexOf("Pages");
					int pageCount = Integer.parseInt(temp.text().substring(0, index-1));
//					System.out.println("Pages: " + pageCount);
					comic.setPageCount(pageCount);
					count++;
					pages = false;
				}
				else if(printDate && count <= 1){
					String printDateNum = temp.text();
					//System.out.println("Print Date: " + printDateNum);
					comic.setPrintPubDateNum(printDateNum);
					count++;
					printDate = false;
				}
				else if(digitalDate && count <= 2){
					String digitalDateNum = temp.text();
					//System.out.println("Digital Date: " + printDateNum);
					comic.setDigitalPubDateNum(digitalDateNum);
					count++;
					digitalDate = false;
					comic.setDigital(true); //Comic is digital
					break;
				}
				else if(count + 5 >= numTrue)
					break;
			}
		}

		
		return System.currentTimeMillis() - initTime;
	}
}