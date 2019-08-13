import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

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
				shipFile = "./Ship Files/Destroyers.tsv";
				break;
			case "CL":
				shipFile = "./Ship Files/Light Cruisers.tsv";
				break;
			case "CA":
				shipFile = "./Ship Files/Heavy Cruisers.tsv";
				break;
			case "LC":
				shipFile = "./Ship Files/Large Cruisers.tsv";
				break;
			case "BC":
				shipFile = "./Ship Files/Battlecruisers.tsv";
				break;
			case "BB":
				shipFile = "./Ship Files/Battleships.tsv";
				break;
			case "AB":
				shipFile = "./Ship Files/Aviation Battleships.tsv";
				break;
			case "MON":
				shipFile = "./Ship Files/Monitors.tsv";
				break;
			case "CVL":
				shipFile = "./Ship Files/Light Aircraft Carriers.tsv";
				break;
			case "CV":
				shipFile = "./Ship Files/Carriers.tsv";
				break;
			case "SUB":
				shipFile = "./Ship Files/Submarines.tsv";
				break;
			default:
				break;
		}
		return shipFile;
	}
	
	/*
	 * Checks which weapon file to open.
	 * Returns a string containing the name of the file.
	 * AA file has nothing, for exceptions
	 */
	public String checkWepFile(String weptype) {
		String wepFile = "";
		switch (weptype) {
			case "DDGUNS":
				wepFile = "./Weapons/DestroyerGuns.tsv";
				break;
			case "CLGUNS":
				wepFile = "./Weapons/Light Cruisers Guns.tsv";
				break;
			case "CAGUNS":
				wepFile = "./Weapons/HeavyCruiserGuns.tsv";
				break;
			case "CBGUNS":
				wepFile = "./Weapons/Large Cruiser Guns.tsv";
				break;
			case "BBGUNS":
				wepFile = "./Weapons/BattleshipGuns.tsv";
				break;
			case "AAGUNS":
				wepFile = "./Weapons/GunTypeExceptions.csv";
				break;
			case "SEAPLANE":
				wepFile = "./Weapons/GunTypeExceptions.csv";
				break;
			case "TORPEDOS":
				wepFile = "./Weapons/Torpedos.tsv";
				break;
			case "SUBTORPEDOS":
				wepFile = "./Weapons/Submarine Torpedos.tsv";
				break;
			default:
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
			String[] fields = line.split("	");
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
			String[] fields = line.split("	");
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
			String[] fields = line.split("	");
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
		System.out.println("The ship type is:" + shipType);
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
		case "MON":
			slottedWep = wepTypes.monitorOne(wepnum);
			break;
		case "DD":
			slottedWep = wepTypes.destroyerOfWorlds(wepnum);
			break;
		case "SUB":
			slottedWep = wepTypes.subOneAndTwo(wepnum);
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
		case "MON":
			slottedWep = wepTypes.monitorTwo(wepnum);
			break;
		case "DD":
			slottedWep = wepTypes.heavyCruiserTwo(wepnum);	
			break;
		case "SUB":
			slottedWep = wepTypes.subOneAndTwo(wepnum);
		default:
			break;
		}
		System.out.println("The string from the check method is: " + slottedWep);
		return slottedWep;
	}

//	/*
//	 * Check the ship type before checking the wepnum for third slot weapons.
//	 * USED FOR CARRIERS AND SUBS
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
			String[] fields = line.split("	");
			theList.add(fields[0]);
		}
		br.close();
		return theList;
	}
	
	/*
	 * Returns the name of skills in an array.
	 */
	public ArrayList<String> getSkillNames() throws FileNotFoundException, IOException {
		ArrayList<String> theSkills = new ArrayList<String>();
		BufferedReader br = new BufferedReader(new FileReader("Skill Stats.tsv"));
		String line = br.readLine();
		while ((line = br.readLine()) != null && !line.isEmpty()) {
			String[] fields = line.split("	");
			theSkills.add(fields[0]);
		}
		br.close();
		return theSkills;
	}
	/* Returns whatever field you want from whatever file
	 * this can also replace the top and definetly the two bottom methods (with a boolean) as they're almost the same 
	 * to avoid code redundancy but fuck it, this ain't school.
	 */
	public ArrayList<String> getSpecificFileAndElement(String theFileName, Integer theElement) throws FileNotFoundException, IOException {
		ArrayList<String> theSkills = new ArrayList<String>();
		BufferedReader br = new BufferedReader(new FileReader(theFileName));
		String line = br.readLine();
		while ((line = br.readLine()) != null && !line.isEmpty()) {
			String[] fields = line.split("	");
			theSkills.add(fields[theElement]);
		}
		br.close();
		return theSkills;
	}
	/* Returns all the elements of whatever column from whatever file you want. Used for the enmies.tsv file mainly
	 * If it doesn't look god the level can be in it's own jlist 
	 * 
	 * Do we show all the enemies of that world, or only unique names (with unique levels)?
	 * The method currently does the latter
	 */
	public static ArrayList<String> getAllEnemiesForSpecificWorld(String theElement, String theFile, Integer theField, Integer theLevel) throws FileNotFoundException, IOException {
			ArrayList<String> theList = new ArrayList<String>();
			String enemyPlusLevel = "";
			//For enemies with same level, remove from the enemy list since their health isn't factored
			BufferedReader br = new BufferedReader(new FileReader(theFile));
			String line = br.readLine();
			while ((line = br.readLine()) != null && !line.isEmpty()) {
				String[] fields = line.split("	");
				if (fields[0].equals(theElement)) {
					enemyPlusLevel = fields[theField] + ", Lvl " + fields[theLevel]; 
					theList.add(enemyPlusLevel);
				}
			}
			Set<String> nonDupedEnemySet = new LinkedHashSet<>(theList);
			theList.clear();
			theList.addAll(nonDupedEnemySet);
			br.close();
			return theList;
	}
	/*
	 * Very Simple method to read the file to help with change log,
	 * will change if we need more persistent variables outside popup
	 * If you want to options the file variable + change log can be a tsv folder
	 * 
	 * REMINDER TO CHANGE PersitentVariables.txt to true EVERY NEW UPDATE!!!  
	 * @author Kevin Nguyen
	 */
	public static boolean readPersistentVariables(int variableNum) throws IOException{
		boolean willPopUp = false;
		BufferedReader br = new BufferedReader(new FileReader("PersistentVariables.txt"));
		String line;
		while ((line = br.readLine()) != null && !line.isEmpty()) {
			String[] variables = line.split(";");
			System.out.println(variables);
			if (variables[variableNum].equals("popUpMSG=true")) {
				willPopUp = true;
			} 
		}
		//Overwrite the file so that the pop up no longer pops up on subsequent openings
		//these 3 lines will need 2b changed if we ever have/need more persistent stuff
		//done via:           .write(variables[i])
		Writer overWriteVariables = new FileWriter("PersistentVariables.txt", false);
		overWriteVariables.write("popUpMSG=false;");
		overWriteVariables.close();
		
		
		return willPopUp;
	}
	/*
	 * Method to write the version history to the joptionpane, will finish later
	 * need to make a joptionpane with jframe, jlist(?), and jscrollpane.  
	 * 
	 * If we're lazy we can manually write into the joptionpane or manually have line breaks in txt file.
	 */
	public static JScrollPane overWritePopUpWithVersionHistory() throws IOException{
		String theTxt = "";
		BufferedReader br = new BufferedReader(new FileReader("VersionHistory.txt"));
		String line;
		while ((line = br.readLine()) != null && !line.isEmpty()) {
			theTxt += line + "\n";
		}
		JTextArea text = new JTextArea(12, 40);
		text.setText(theTxt);
		text.setEditable(false);
		text.setCaretPosition(0);
		JScrollPane versionText = new JScrollPane(text);
		return versionText;
	}
	
	/*
	 */
	public void insertEnemyNames(JComboBox<Object> comboBox, String theCurrentWorld) throws FileNotFoundException, IOException {
		ArrayList<String> initialUserChoice = null; 
		//System.out.println("Inserting name for weapon type: " + theType);
		initialUserChoice = getAllEnemiesForSpecificWorld(theCurrentWorld, "Enemies.tsv", 1, 2);
		Collections.sort(initialUserChoice);
		comboBox.setModel(new DefaultComboBoxModel<Object>(initialUserChoice.toArray()));
	}
	/*
	 * Simple method to add all the worlds to comboBox
	 */
	public void addWorldNum(JComboBox<String> theWorldBox) {
		String theWorld = null;
		for (int i = 1; i <= 13; i++) {
			for(int d = 1; d <= 4; d++) {
				theWorld = (i+"-"+d);
				theWorldBox.addItem(theWorld);
			}
		}
	}
	
	
	//Aren't these two methods almost exactly the same? lol
	//I'm pretty sure we can also group the other ones aswell but whatever. - Kevin
	
	/*
	 * Returns the description of a skill.
	 */
	public String getSkillDescription(String skillName) throws FileNotFoundException, IOException {
		String skillDesc = "";
		BufferedReader br = new BufferedReader(new FileReader("Skill Stats.tsv"));
		String line = br.readLine();
		while ((line = br.readLine()) != null && !line.isEmpty()) {
			String[] fields = line.split("	");
			if (fields[0].equals(skillName)) {
				skillDesc = fields[1];
			}
		}
		br.close();
		return skillDesc;
	}
	
	/*
	 * Returns the ships that use said skill.
	 */
	public String getSkillUsers(String skillName) throws FileNotFoundException, IOException {
		String skillDesc = "";
		BufferedReader br = new BufferedReader(new FileReader("Skill Stats.tsv"));
		String line = br.readLine();
		while ((line = br.readLine()) != null && !line.isEmpty()) {
			String[] fields = line.split("	");
			if (fields[0].equals(skillName)) {
				skillDesc = fields[2];
			}
		}
		br.close();
		return skillDesc;
	}
	
	/*
	 * Returns the parameters of a skill. 
	 */
	public ArrayList<String> getSkillParameters(String skillName) throws FileNotFoundException, IOException {
		ArrayList<String> theList = new ArrayList<String>();
		BufferedReader br = new BufferedReader(new FileReader("Skill Stats.tsv"));
		String line = br.readLine();
		while ((line = br.readLine()) != null && !line.isEmpty()) {
			String[] fields = line.split("	");
			if (fields[0].equals(skillName)) {
				for (int i = 0; i < fields.length; i++) {
					theList.add(fields[i]);
				}
			}
		}
		br.close();
		return theList;
	}
	
	/*
	 * Returns the enemies of a world along with the related level.
	 */
	public ArrayList<String> getWorldEnemies(String world) throws FileNotFoundException, IOException {
		ArrayList<String> theEnemies = new ArrayList<String>();
		BufferedReader br = new BufferedReader(new FileReader("Enemies.tsv"));
		String line = br.readLine();
		while ((line = br.readLine()) != null && !line.isEmpty()) {
			String[] fields = line.split("	");
			if (fields[0].contentEquals(world)) {
				String temp = fields[1] + "," + fields[2];
				theEnemies.add(temp);
			}
		}
		br.close();
		return theEnemies;
	}
	/*
	 * Calculates the maximum danger level based on the selected chapter
	 */
	public int getDangerLevel(String world) {
		System.out.println("The current world is:" + world);

		int maxDangerLevel = 0;
		int currentChapter = 0;
		char worldNum = world.charAt(0);
		if(world.charAt(1) != '-') {
			maxDangerLevel = 10;
		} else {
				//gets the int via ascii conversion.
				currentChapter = worldNum - '0';
				//System.out.println("The current chapter is:" + currentChapter);
				if (currentChapter <= 4) {
					maxDangerLevel = 3;
				} else if (currentChapter <= 7) {
					maxDangerLevel = 5;
				} else if (currentChapter <= 9) {
					maxDangerLevel = 8;
				} else {
					maxDangerLevel = 10;
				}
			}
		return maxDangerLevel;
	}
	
	/*
	 * Returns the parameters of a enemy
	 */
	public ArrayList<String> getEnemyParameters(String enemy, String world) throws FileNotFoundException, IOException{
		//Adding in print statements to debug
		//System.out.println("In the enemy parameters method:  "+ enemy + " " + world);
		ArrayList<String> theParams = new ArrayList<String>();
		String[] enemyNameAndLevel = enemy.split(","); //Name and Level
		
		String parseOutLvl = enemyNameAndLevel[1].replaceAll("[^\\d.]", "");
		enemyNameAndLevel[1] = parseOutLvl;
		//System.out.println("the enemy name and level fields: " + Arrays.toString(enemyNameAndLevel));

		BufferedReader br = new BufferedReader(new FileReader("Enemies.tsv"));
		String line = br.readLine();
		while ((line = br.readLine()) != null && !line.isEmpty()) {
			String[] fields = line.split("	");
			//System.out.println("the fields: " + Arrays.toString(fields));
			if (fields[0].equals(world) && fields[1].equals(enemyNameAndLevel[0]) && fields[2].equals(enemyNameAndLevel[1])) {
				for (int i = 0; i < fields.length; i++) {
					theParams.add(fields[i]);
				}
			}
		}
		br.close();
		return theParams;
	}
	/**
	 * A method to get all the ship names onto the jcombo box
	 * Need help specifying which names for which ships
	 * @author Kevin Nguyen
	 * @param comboBox The first button for the ship/weapon names
	 * @param isShip a boolean variable to determine if we're getting the ship or weapon names
	 * @param theType string that's passed into the getWeaponList/getShipList method and gets the list of names from csv
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public static void insertNames(JComboBox comboBox,boolean isShip, String theType) throws FileNotFoundException, IOException {
		ArrayList<String> initialUserChoice = null; 
		GUIutil theList;
		theList = new GUIutil();
		//true = we're getting a ship name, false is a weapon
		if(isShip) {
			initialUserChoice = theList.getShipList(theType);
		} else {
			//Weapon lists method doesn't exist yet but i'm assuming it will be this.
			//Remember to uncomment this
			System.out.println("Inserting name for weapon type: " + theType);
			initialUserChoice = theList.getWeaponList(theType);
		}
		initialUserChoice.add("");
		Collections.sort(initialUserChoice);
		comboBox.setModel(new DefaultComboBoxModel<Object>(initialUserChoice.toArray()));
	}
	
	/**
	 * Method to insert the ship or weapon type into the appropriate combo box. 
	 * @author Kevin Nguyen
	 * @param comboBox the combo box
	 * @param weaponParamNum the weapon parameter number to get the appropriate type 
	 * @param shipType 
	 * @param shipName
	 * @param firstSlot slot check
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static void insertType(JComboBox<Object> comboBox, int weaponParamNum, String shipType, String shipName, boolean firstSlot) throws FileNotFoundException, IOException {
		ArrayList<String> weaponTypes = null; 
		String weaponNumString = null; 
		//String exceptionCheck = "";
		int currentWeaponNum;
		GUIutil theList;
		theList = new GUIutil();
		weaponNumString = theList.getGetSpecificWeaponParam(shipType, shipName, weaponParamNum);
		currentWeaponNum = Integer.parseInt(weaponNumString);
		
		//exceptionCheck = theList.checkSlotTwoWeps("BB", 2);
		//System.out.println("Grabbing for hardcoded Battleships " + exceptionCheck);
		if(firstSlot) {
			System.out.println("Current weapon num for slot 1 is: " + currentWeaponNum);
			System.out.println("The ship type for slot 1 is: " + shipType);
			weaponTypes = createWeaponTypeList(theList.checkSlotOneWeps(shipType, currentWeaponNum));
			
		} else {
			System.out.println("Current weapon num for slot 2 is: " + currentWeaponNum);
			System.out.println("The ship type for slot 2 is: " + shipType);
		
			
			weaponTypes = createWeaponTypeList(theList.checkSlotTwoWeps(shipType, currentWeaponNum));
			

		}
		comboBox.setModel(new DefaultComboBoxModel<Object>(weaponTypes.toArray()));
	} 
	
	/**
	 * Using Brians methods we check what types of weapons can be used in what slot. Whatever string it returns we convert that into
	 * an array to insert it into the combo box.
	 * Since he didn't want arrays in wepTypes this is the ghetto way. 
	 * @author Kevin Nguyen
	 * @param compatibleWeapons the string that is returned from checkWeapon methods.
	 * @return the parsed string for our methods. 
	 */
	public static ArrayList<String> createWeaponTypeList(String compatibleWeapons) {
		System.out.println("string is: " +compatibleWeapons + " Length is " + compatibleWeapons.length());
		ArrayList<String> weaponTypeArray = new ArrayList<String>();		
		String weaponType = "";
		String weaponType2 = "";
		//System.out.println("Current word for type list " + compatibleWeapons);
		if((compatibleWeapons.length() > 2) && ((!(compatibleWeapons.equals("SEAPLANE"))) && (!(compatibleWeapons.equals("TORPEDOS")) 
				&& (!(compatibleWeapons.equals("SUBTORPEDOS")))))) {
				
			for (int i = 0; i <= compatibleWeapons.length() - 1; i++) {
				if(i < 2) {
					weaponType += compatibleWeapons.charAt(i);
				}
				else if (i > 2) {
					weaponType2 += compatibleWeapons.charAt(i);
				}
			
			} 
				
			//System.out.println("Two weapon checks types" + weaponType + " " + weaponType2);
			weaponTypeArray.add((weaponType += "GUNS"));
			weaponTypeArray.add((weaponType2 += "GUNS"));
			} else {
				//System.out.println("hit else statement" + compatibleWeapons);
				if((compatibleWeapons.length() == 2)) {
					compatibleWeapons += "GUNS";
					weaponTypeArray.add(compatibleWeapons);
				} else {
					weaponTypeArray.add(compatibleWeapons);
				}
			}
		//System.out.println(weaponTypeArray);

		return weaponTypeArray;
	}
}