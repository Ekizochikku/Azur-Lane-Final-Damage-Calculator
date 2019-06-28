import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/*
 * @author Brian Khang (Ekizochikku)
 * Methods for the GUI to call. 
 */
public class GUIutil {
		
		WepTypes wepTypes = new WepTypes();
	/*
	 * Determines which ship file needs to be opened and read.
	 * Returns a list that contains all ships of a certain ship type.
	 */
	public ArrayList<String> getShipList(String shiptype) throws FileNotFoundException, IOException {
		String shipFile = checkShipFile(shiptype);
		ArrayList<String> shipList = new ArrayList<String>();
		shipList = getEntityNames(shipFile);
		return shipList;
	}
	
	/*
	 * Determines which weapon file needs to be opened and read.
	 * Returns a list that contains all weapons of a certain weapon type.
	 */
	public ArrayList<String> getWeaponList(String weptype) throws FileNotFoundException, IOException {
		String wepFile = checkWepFile(weptype);
		ArrayList<String> wepList = new ArrayList<String>();
		wepList = getEntityNames(wepFile);
		return wepList;
	}
	
	/*
	 * Check which file needs to be opened for ships.
	 * Returns a string containing the name of the file.
	 */
	public String checkShipFile(String shiptype) {
		String shipFile = "";
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
		return shipFile;
	}
	
	/*
	 * Checks which weapon file to open.
	 * Returns a string containing the name of the file.
	 */
	public String checkWepFile(String weptype) {
		String wepFile = "";
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
		return wepFile;
	}
	
	/*
	 * Returns a ship's lvl 120 parameters
	 */
	public ArrayList<String> getShipParams(String shiptype, String shipname) throws FileNotFoundException, IOException {
		ArrayList<String> theParams = new ArrayList<String>();
		String theFile = checkShipFile(shiptype);
		BufferedReader br = new BufferedReader(new FileReader(theFile));
		String line = br.readLine(); //Skip Header Line
		while ((line = br.readLine()) != null && !line.isEmpty()) {
			String[] fields = line.split(",");
			if (fields[0].equals(shipname)) {
				for (int i = 0; i < fields.length; i++) {
					theParams.add(fields[i]);
				}
			}
			
		}
		br.close();
		return theParams;
	}
	/* @author Kevin Nguyen
	 * Returns the parameter mainly to use the check weapon type methods
	 * Basically Brains code except you return i == 4 or 5
	 * Will cause errors on certain ship types easy to fix but right now i'm lazy
	 * Can change to make it better by changing the third parameter to the name of the parameter you want but again i'm lazy right now
	 * @param weaponSlot always either 4 or 5 to get the weaponNum
	 */
	public String getGetSpecificWeaponParam(String shiptype, String shipname, int weaponSlot) throws FileNotFoundException, IOException {
		String theParams = "";
		String theFile = checkShipFile(shiptype);
		BufferedReader br = new BufferedReader(new FileReader(theFile));
		String line = br.readLine(); //Skip Header Line
		while ((line = br.readLine()) != null && !line.isEmpty()) {
			String[] fields = line.split(",");
			if (fields[0].equals(shipname)) {
				for (int i = 0; i < fields.length; i++) {
					if(i == weaponSlot) {
						theParams += fields[i];
					}
				}
			}
			
		}
		br.close();
		return theParams;
	}
	/*
	 * Returns a weapon's parameters
	 */
	public ArrayList<String> getWepParams(String weptype, String wepname) throws FileNotFoundException, IOException {
		ArrayList<String> theParams = new ArrayList<String>();
		String theFile = checkWepFile(weptype);
		BufferedReader br = new BufferedReader(new FileReader(theFile));
		String line = br.readLine(); //Skip Header Line
		while ((line = br.readLine()) != null && !line.isEmpty()) {
			String[] fields = line.split(",");
			if (fields[0].equals(wepname)) {
				for (int i = 0; i < fields.length; i++) {
					theParams.add(fields[i]);
				}
			}
			
		}
		br.close();
		return theParams;
	}
	
	/*
	 * Check a ships weapon slots and see which weapons can be used
	 */
	public String checkWeaponSlot(String shipType, int slot, int wepnum) {
		String theWep = "";
		if (slot == 1) {
			theWep = checkSlotOneWeps(shipType, wepnum);
		} else if (slot == 2) {
			theWep = checkSlotTwoWeps(shipType, wepnum);
//		} else if (slot == 3) {
//			theWep = checkSlotThreeWeps(shipType, wepnum);
		} else {
			return null;
		}
		return theWep;
	}
	
	/*
	 * Check the ship type before checking the wepnum for first slot weapons
	 * ADD CARRIERS LATER
	 */
	public String checkSlotOneWeps(String shipType, int wepnum) {
		String slottedWep = "";
		switch (shipType) {
		case "CL":
			slottedWep = wepTypes.lightCruiserOne(wepnum);
			break;
		case "CA":
			slottedWep = wepTypes.heavyCruiserOne(wepnum);
			break;
		case "LC":
			slottedWep = wepTypes.largeCruiserOne(wepnum);
			break;
		case "BC":
			slottedWep = wepTypes.battlecruiserOne(wepnum);
			break;
		case "BB":
			slottedWep = wepTypes.battleshipOne(wepnum);
			break;
		case "AB":
			slottedWep = wepTypes.aviationBBOne(wepnum);
			break;
		case "Monitor":
			slottedWep = wepTypes.monitorOne(wepnum);
			break;
		default:
			break;
		}
		return slottedWep;
	}
	
	/*
	 * Check the ship type before checking the wepnum for second slot weapons
	 */
	public String checkSlotTwoWeps(String shipType, int wepnum) {
		String slottedWep = "";
		switch (shipType) {
		case "CL":
			slottedWep = wepTypes.lightCruiserTwo(wepnum);
			break;
		case "CA":
			slottedWep = wepTypes.heavyCruiserTwo(wepnum);
			break;
		case "LC":
			slottedWep = wepTypes.largeCruiserTwo(wepnum);
			break;
		case "BC":
			slottedWep = wepTypes.battlecruiserTwo(wepnum);
			break;
		case "BB":
			slottedWep = wepTypes.battleshipTwo(wepnum);
			break;
		case "AB":
			slottedWep = wepTypes.aviationBBTwo(wepnum);
			break;
		case "Monitor":
			slottedWep = wepTypes.monitorTwo(wepnum);
			break;
		default:
			break;
		}
		return slottedWep;
	}

//	/*
//	 * Check the ship type before checking the wepnum for third slot weapons.
//	 * USED FOR CARRIERS
//	 */
//	public String checkSlotThreeWeps(String shipType, int wepnum) {
//		
//	}
	
	
	/*
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
