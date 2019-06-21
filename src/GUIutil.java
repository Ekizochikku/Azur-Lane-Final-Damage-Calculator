/*
 * Methods for the GUI to call. 
 */
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*
 * @author: Brian Khang (Ekizochikku)
 * Determines which ship list needs to be opened and read.
 */
public class GUIutil {
	
	private String shipFile;
	private ArrayList<String> shipList;

	public ArrayList<String> getShipList(String shiptype) throws FileNotFoundException, IOException {
		switch (shiptype) {
			case "DD":
				shipFile = "./Ship Files/Destroyers.csv";
				break;
			case "CL":
				shipFile = "./Ship Files/Light Cruisers.csv";
				break;
			case "CA":
				shipFile = "./Ship Files/Heavy Cruisers.csv";
				break;
			case "LC":
				shipFile = "./Ship Files/Large Cruisers.csv";
				break;
			case "BC":
				shipFile = "./Ship Files/Battlecruisers.csv";
				break;
			case "BB":
				shipFile = "./Ship Files/Battleships.csv";
				break;
			case "AB":
				shipFile = "./Ship Files/Aviation Battleships.csv";
				break;
			case "MON":
				shipFile = "./Ship Files/Monitors.csv";
				break;
			case "CVL":
				shipFile = "./Ship Files/Light Aircraft Carriers.csv";
				break;
			case "CV":
				shipFile = "./Ship Files/Carriers.csv";
				break;
		}
		
		shipList = getShipNames(shipFile);
		return shipList;
	}
	
	/*
	 * @author Brian Khang (Ekizochikku)
	 * Returns an array list containing the ship names.
	 */
	public ArrayList<String> getShipNames(String shipFile) throws FileNotFoundException, IOException {
		ArrayList<String> theShipList = new ArrayList<String>();
		BufferedReader br = new BufferedReader(new FileReader(shipFile));
		String line = br.readLine(); //Skip the Header Line
		while ((line = br.readLine()) != null && !line.isEmpty()) {
			String[] fields = line.split(",");
			theShipList.add(fields[0]);
		}
		br.close();
		return theShipList;
	}
	
}
