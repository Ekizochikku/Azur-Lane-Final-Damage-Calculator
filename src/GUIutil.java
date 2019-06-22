
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*
 * Methods for the GUI to call. 
 */
public class GUIutil {
	
	/*
	 * @author: Brian Khang (Ekizochikku)
	 * Determines which ship file needs to be opened and read.
	 */
	public ArrayList<String> getShipList(String shiptype) throws FileNotFoundException, IOException {
		String shipFile = "";
		ArrayList<String> shipList = new ArrayList<String>();
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
		
		shipList = getEntityNames(shipFile);
		return shipList;
	}
	
	/*
	 * @author: Brian Khang (Ekizochikku)
	 * Determines which weapon file needs to be opened and read.
	 */
	public ArrayList<String> getWeaponList(String weptype) throws FileNotFoundException, IOException {
		String wepFile = "";
		ArrayList<String> wepList = new ArrayList<String>();
		switch (weptype) {
			case "DDGUNS":
				wepFile = "./Weapons/DestroyerGuns.csv";
				break;
			case "CLGUNS":
				wepFile = "./Weapons/Light Cruisers Guns.csv";
				break;
			case "CAGUNS":
				wepFile = "./Weapons/HeavyCruiserGuns.csv";
				break;
			case "BBGUNS":
				wepFile = "./Weapons/BattleshipGuns.csv";
				break;
			case "TORPEDOS":
				wepFile = "./Weapons/Torpedos.csv";
				break;
		}
		wepList = getEntityNames(wepFile);
		return wepList;
	}
	/*
	 * @author Brian Khang (Ekizochikku)
	 * Returns an array list containing the names of ships/weapons
	 */
	public ArrayList<String> getEntityNames(String theFile) throws FileNotFoundException, IOException {
		ArrayList<String> theList = new ArrayList<String>();
		BufferedReader br = new BufferedReader(new FileReader(theFile));
		String line = br.readLine(); //Skip the Header Line
		while ((line = br.readLine()) != null && !line.isEmpty()) {
			String[] fields = line.split(",");
			theList.add(fields[0]);
		}
		br.close();
		return theList;
	}
	
}
