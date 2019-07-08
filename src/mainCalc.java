import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JRadioButton;
import javax.swing.JComboBox;
import javax.swing.ButtonGroup;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JList;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class mainCalc extends JFrame {

	private JPanel contentPane;
	
	//Allows user to select which ship types to display
	private JComboBox shipTypeCBox;
	
	//Allows user to select specific ship from the selected ship type
	private JComboBox shipName;
	
	//The current selected ship type
	private String currentShipType;
	
	//The current ship selected
	private String currentShipName;
	
	//Allows user to select the weapon type for slot 1 for current ship
	private JComboBox weaponTypeCBox1;
	
	//Allows user to select the weapon type for slot 2 for current ship
	private JComboBox weaponTypeCBox2;
	
	//Allows user to select the weapon for slot 1 on current ship
	private JComboBox weaponNamesSlot1;
	
	//Allows user to select the weapon for slot 2 on current ship
	private JComboBox weaponSlot2;
	
	//Allows user to select the current world they are on
	private JComboBox currentWorldCBox;
	
	//Displays the description of the selected skill
	private JTextPane skillDescriptionBox;
	
	//Displays which skills the user have currently selected to apply stats to current ship
	private JList activeSkillList;
	
	//Allows user to select which skills to apply to current ship for damage calculations
	private JComboBox skillList;
	
	//An array to hold the current skills the user have selected
	private ArrayList<String> currentSkills;
	
	//Displays the ships the selected skill can be applied too
	private JTextPane equipableShips;
	
	//The current selected weapon type
	private String currentWeaponType;
	
	//The current selected weapon name
	private String currentWeaponName;
	
	//The current selected weapon type for slot 2
	private String currentWeaponTypeSlot2;
	
	//The current selected weapon for slot 2
	private String currentWeaponNameSlot2;
	
	//The current world
	private String theCurrentWorld;
	
	//The current enemy the user's ship is attacking
	private String theCurrentEnemy;
	
	//Allows user to select which enemy from a specific world they are fighting
	private JComboBox enemyNameCBox;
	
	//The current damage type being applied
	private int currentDMGType = -1; //0 = HE, 1 = AP
	
	//???
	private int currentWeaponNum = -1; 
	
	//Helper method calls
	private GUIutil guiUtil;
	
	//???
	private ArrayList<String> weaponTypeListSlot1;
	
	//Should first salvo be applied in damage calculation
	private boolean firstSalvo = false;
	
	//Should critical strkie be applied in damgage calculation
	private boolean critical = false;
	private boolean manual = false;
	
	private JButton removeButton;
	private JButton btnRemoveAll;
	private JLabel lblGunTypeSlot;
	private JLabel lblGunTypeSlot_1;
	private JLabel lblGunNameSlot;
	private JLabel lblGunNameSlot_1;
	private JLabel lblChapter;
	private JLabel lblEnemyName;
	private JLabel lblDangerLevel;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JLabel lblSkillList;
	private JLabel lblSkillDescription;
	private JLabel lblSkillUsers;
	private JLabel lblActiveSkills;
	
	private JRadioButton buttonHE;
	private JRadioButton buttonAP;
	private JRadioButton evenRadioButton;
	private JRadioButton oddRadioButton;
	private JTextPane textPane;
	private JTextPane textPane_1;
	private JLabel lblGunTypeSlot_2;
	private JLabel lblGunTypeSlot_3;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					mainCalc frame = new mainCalc();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
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
		if((compatibleWeapons.length() > 2) && ((!(compatibleWeapons.equals("SEAPLANE"))) && (!(compatibleWeapons.equals("TORPEDOS"))))) {
				
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
	
	/**
	 * A method to create the weapon slot combo box (to avoid clutter)
	 * Will be finished and used later if it can work.
	 * @author Kevin Nguyen
	 *
	@SuppressWarnings("unchecked")
	public static void insertWeaponTypes(JComboBox weaponTypeCBox, JComboBox weaponNamesCBox, String currentWeaponName) {
		//finished the action listener for weapons, copied from walter.
		String[] weaponTypeList = {"DDGUNS", "CLGUNS", "CAGUNS", "BBGUNS","TORPEDOS"};
		weaponTypeCBox = new JComboBox(weaponTypeList);
		weaponTypeCBox.setMaximumRowCount(5);
		weaponTypeCBox.setSelectedIndex(0);
		currentWeaponType = (String) weaponTypeCBox.getSelectedItem();
		weaponTypeCBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
	//			System.out.println("This is a test");
				try {
					currentWeaponType = (String) weaponTypeCBox.getSelectedItem();
					insertNames(weaponNamesCBox, false, currentWeaponType);
					//currentWeaponN = (String) weaponNamesSlot1.getSelectedItem();
	//				System.out.println(currentShipName);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}
	
		/*
	 * Method for the action listener to update the combo box whenever a different world is selected
	 */
	/*public static void insertEnemyNames(JComboBox<Object> comboBox, String theCurrentWorld) throws FileNotFoundException, IOException {
		ArrayList<String> initialUserChoice = null; 
		//System.out.println("Inserting name for weapon type: " + theType);
		GUIutil theList;
		theList = new GUIutil();
		initialUserChoice = theList.getAllEnemiesForSpecificWorld(theCurrentWorld, "Enemies.tsv", 1, 2);
		comboBox.setModel(new DefaultComboBoxModel<Object>(initialUserChoice.toArray()));
	} */
	/**
	 * @author: Kevin Nguyen (kvn96) action listeners for ships by Walter
	 * creating the gui design with the proper buttons
	 */
	@SuppressWarnings("unchecked")
	public mainCalc() throws FileNotFoundException, IOException {
		//GUIutil test environment
		
		/*GUIutil testCase = new GUIutil();
		ArrayList<String> ls = new ArrayList<String>(); 
		ls = createWeaponTypeList(testCase.checkWeaponSlot("CL",1, 2));
		for (int i = 0; i < ls.size(); i++) {
		     System.out.println(ls.toString());
		}*/ 	
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1250, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		//We're going to need to change these labels later into the actual names. 
		/* This was kinda annoying to understand. If my understanding from Gui util is correct
		 * Ships can only use certain weapon types, certain slots can only use certain types?
		 */
		//Currently cruisers only for now to avoid errors
		guiUtil = new GUIutil();
		String[] shipTypeList = {"CL", "CA", "LC", "BC", "BB", "AB", "MON", "DD"};
		shipTypeCBox = new JComboBox(shipTypeList);
		shipTypeCBox.setMaximumRowCount(10);
		shipTypeCBox.setSelectedIndex(0);
		
		currentShipType = (String) shipTypeCBox.getSelectedItem();

		
		//push test
		shipTypeCBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
//				System.out.println("This is a test");
				try {
					currentShipType = (String) shipTypeCBox.getSelectedItem();
					insertNames(shipName, true, currentShipType);
					currentShipName = (String) shipName.getSelectedItem();
					
					insertType(weaponTypeCBox1, 4, currentShipType, currentShipName, true);
					currentWeaponType = (String) weaponTypeCBox1.getSelectedItem();
					

					insertNames(weaponNamesSlot1, false, currentWeaponType);
					currentWeaponName = (String) weaponNamesSlot1.getSelectedItem();
					
					insertType(weaponTypeCBox2, 5, currentShipType, currentShipName, false);
					currentWeaponTypeSlot2 = (String) weaponTypeCBox2.getSelectedItem();
					
					
					
					insertNames(weaponSlot2, false, currentWeaponTypeSlot2);
					currentWeaponNameSlot2 = (String) weaponSlot2.getSelectedItem();


					System.out.println(currentShipName);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		JComboBox comboBox_2 = new JComboBox();
		
		//REMINDER STILL NEED LABELS ON TOP OF BUTTONS!!
		
		shipName = new JComboBox();
		insertNames(shipName,true, currentShipType);
		shipName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				currentShipName = (String) shipName.getSelectedItem();
				System.out.println("the current ship name: " + currentShipName);
				if(currentShipName.equals("Roon")) {
					//System.out.println("Not entering this check!!!");
					//The set enabled can be a method to reduce code reduncy if you want but it's only this
					buttonHE.setEnabled(true);
					buttonAP.setEnabled(true);
				} else if(currentShipName.equals("Friedrich der Grosse")) {
					buttonHE.setEnabled(false);
					buttonAP.setEnabled(false);
					
					//Any default for radio buttons?
					evenRadioButton.setSelected(true);
					evenRadioButton.setEnabled(true);
					oddRadioButton.setEnabled(true);
					
				} else {
					buttonHE.setSelected(true);
					buttonHE.setEnabled(false);
					buttonAP.setEnabled(false);
					evenRadioButton.setEnabled(false);
					oddRadioButton.setEnabled(false);
				} 
				try {
					insertType(weaponTypeCBox1, 4, currentShipType, currentShipName, true);
					currentWeaponType = (String) weaponTypeCBox1.getSelectedItem();
					insertNames(weaponNamesSlot1, false, currentWeaponType);
					
					insertType(weaponTypeCBox2, 5, currentShipType, currentShipName, false);
					currentWeaponTypeSlot2 = (String) weaponTypeCBox2.getSelectedItem();
					insertNames(weaponSlot2, false, currentWeaponTypeSlot2);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			}
		});
		
		//Hard Coded initial screen of weapon type will need to change later most likely.
		String[] weaponTypeList1 = {"CLGUNS"};
		weaponTypeCBox1 = new JComboBox<Object>(weaponTypeList1);
		weaponTypeCBox1.setMaximumRowCount(5);

		//weaponTypeCBox1.setSelectedIndex(0);
		currentWeaponType = (String) weaponTypeCBox1.getSelectedItem();
		//finished the action listener for weapons, copied from walter.
		weaponTypeCBox1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
//				System.out.println("This is a test");
				try {
					currentWeaponType = (String) weaponTypeCBox1.getSelectedItem();
					insertNames(weaponNamesSlot1, false, currentWeaponType);
					currentWeaponName = (String) weaponNamesSlot1.getSelectedItem();
//					System.out.println(currentShipName);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		weaponNamesSlot1 = new JComboBox();
		//A second insertNames method for the initial screen
		insertNames(weaponNamesSlot1, false, currentWeaponType);
		weaponNamesSlot1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				currentWeaponName = (String) weaponNamesSlot1.getSelectedItem();
//				System.out.println(currentShipName);
			}
		});
		
		
		
		String[] weaponTypeList2 = {"TORPEDOS"};
		weaponTypeCBox2 = new JComboBox(weaponTypeList2);

		//weaponTypeCBox1.setSelectedIndex(0);
		currentWeaponTypeSlot2 = (String) weaponTypeCBox2.getSelectedItem();
		//finished the action listener for weapons, copied from walter.
		weaponTypeCBox2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
//				System.out.println("This is a test");
				try {
					currentWeaponTypeSlot2 = (String) weaponTypeCBox2.getSelectedItem();
					insertNames(weaponSlot2, false, currentWeaponTypeSlot2);
					currentWeaponNameSlot2 = (String) weaponSlot2.getSelectedItem();
//					System.out.println(currentShipName);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		weaponSlot2 = new JComboBox();
		//A second insertNames method for the initial screen
		insertNames(weaponSlot2, false, currentWeaponTypeSlot2);
		weaponSlot2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				currentWeaponNameSlot2 = (String) weaponSlot2.getSelectedItem();
//				System.out.println(currentShipName);
			}
		});
		
		
		
		

		JComboBox dangerLevel = new JComboBox();
		
		
		currentWorldCBox = new JComboBox<String>();
		theCurrentWorld = "1-1";
		//Populate it with all worlds found in the enemies.tsv file
		guiUtil.addWorldNum(currentWorldCBox);
		
		//theWorlds = guiUtil.getSpecificFileAndElement("Enemies.tsv", 0);
		//currentWorldCBox.setModel(new DefaultComboBoxModel<Object>(theWorlds.toArray()));
		
		currentWorldCBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				theCurrentWorld = (String) currentWorldCBox.getSelectedItem();
				//System.out.println(theCurrentWorld);
				try {
					//System.out.println("The current world: " + theCurrentWorld);
					guiUtil.insertEnemyNames(enemyNameCBox, theCurrentWorld);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		enemyNameCBox = new JComboBox<Object>();
		List<String> theEnemies = new ArrayList<String>();
		theEnemies = GUIutil.getAllEnemiesForSpecificWorld(theCurrentWorld, "Enemies.tsv", 1, 2);
		enemyNameCBox.setModel(new DefaultComboBoxModel<Object>(theEnemies.toArray()));
		enemyNameCBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				theCurrentEnemy = (String) enemyNameCBox.getSelectedItem();
				//System.out.println(theCurrentEnemy);
			}
		});
		
		
		
		
		//The checkbox button for the user to determine if it's a critical hit, and first salvo
		JCheckBox isCritical = new JCheckBox("Critical Hit");
		isCritical.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				critical = !critical;
//				System.out.println(critical);
			}
		});
		JCheckBox isFirstSalvo = new JCheckBox("First Salvo");
		isFirstSalvo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				firstSalvo = !firstSalvo;
//				System.out.println(firstSalvo);
			}
		});
		//Button group for the weapon damage type
		buttonHE = new JRadioButton("HE");
		buttonHE.setSelected(true);
		buttonHE.setEnabled(false);
		buttonHE.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				currentDMGType = 0;
//				System.out.println(currentDMGType);
			}
		});
		buttonAP = new JRadioButton("AP");
		buttonAP.setEnabled(false);
		buttonAP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				currentDMGType = 1;
//				System.out.println(currentDMGType);
			}
		});
		
		ButtonGroup group = new ButtonGroup();
		group.add(buttonHE);
		group.add(buttonAP);
		
		/*Button to determine final calculation
		* For now does nothing as Subject to change 
		* we could have it change to entire screen
		* or have the calculated damage number appear on the right side
		*/
		JButton calculateButton = new JButton("Calculate");
		calculateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Calculate Data
			}
		});
			
		
		JLabel lblAmmoType = new JLabel("Ammo Type");
		
		/*
		*The gui components, this will be in it's own class later.
		*/
		currentSkills = new ArrayList<String>();
		
		skillList = new JComboBox();
		AutoCompletion ac = new AutoCompletion(skillList);
		ac.enable(skillList);
		skillList.setMaximumRowCount(10);
		skillList.addItem("");
		ArrayList<String> skillNames = guiUtil.getSkillNames();
		Collections.sort(skillNames);
		for(String skillName: skillNames) {
			skillList.addItem(skillName);
		}
		skillList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				skillDescriptionBox.removeAll();
				try {
					skillDescriptionBox.setText(guiUtil.getSkillDescription((String) skillList.getSelectedItem()));
					equipableShips.setText(guiUtil.getSkillUsers((String) skillList.getSelectedItem()));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		JButton addSkill = new JButton("Add Skill");
		addSkill.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				currentSkills.add("This is a test");
				currentSkills.add((String) skillList.getSelectedItem());
				activeSkillList.removeAll();
				Collections.sort(currentSkills);
				activeSkillList.setListData(currentSkills.toArray());
			}
		});
		
		JScrollPane descScrollPane = new JScrollPane();
		JScrollPane equipScrollPane = new JScrollPane();
		JScrollPane activeSkillScrollPane = new JScrollPane();
		//We should also have a button to delete all
		removeButton = new JButton("Remove Skill");
		removeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				List<String> skills = activeSkillList.getSelectedValuesList();
				for(String skill: skills) {
					currentSkills.remove(skill);
				}
				activeSkillList.removeAll();
				Collections.sort(currentSkills);
				activeSkillList.setListData(currentSkills.toArray());
			}
		});
		
		JButton btnRemoveAll = new JButton("Remove All");
		btnRemoveAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				currentSkills.clear();
				activeSkillList.removeAll();
				activeSkillList.setListData(currentSkills.toArray());
			}
		});
		
		
		equipableShips = new JTextPane();
		equipableShips.setEditable(false);
		equipableShips.setText("No Ships Available");
		equipableShips.setText(guiUtil.getSkillUsers((String) skillList.getSelectedItem()));
		

		JCheckBox isManual = new JCheckBox("Manual");
		isManual.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				manual = !manual;
//				System.out.println(manual);
			}
		});
		
		
		//All the labels
		JLabel shipTypeLbl = new JLabel("Ship Type:");
		JLabel shipNameLbl = new JLabel("Ship Name:");
		lblGunTypeSlot = new JLabel("Gun Type Slot 1:");
		lblGunTypeSlot_1 = new JLabel("Gun Type Slot 2:");
		lblGunNameSlot = new JLabel("Gun Name Slot 1:");
		lblGunNameSlot_1 = new JLabel("Gun Name Slot 2:");
		lblChapter = new JLabel("Chapter:");
		lblEnemyName = new JLabel("Enemy Name:");
		lblDangerLevel = new JLabel("Danger Level:");
		
		ac.enable(shipName);
		
		evenRadioButton = new JRadioButton("Even");
		buttonGroup.add(evenRadioButton);
		oddRadioButton = new JRadioButton("Odd");
		buttonGroup.add(oddRadioButton);
		
		evenRadioButton.setEnabled(false);
		oddRadioButton.setEnabled(false);
		
		lblSkillList = new JLabel("Skill List:");
		
		lblSkillDescription = new JLabel("Skill Description:");
		
		lblSkillUsers = new JLabel("Skill Users:");
		
		lblActiveSkills = new JLabel("Active Skills:");
		
		textPane = new JTextPane();
		
		textPane_1 = new JTextPane();
		
		lblGunTypeSlot_2 = new JLabel("Gun Type Slot 1 Damage:");
		
		lblGunTypeSlot_3 = new JLabel("Gun Type Slot 2 Damage:");
		
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
										.addGroup(gl_contentPane.createSequentialGroup()
											.addGap(4)
											.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
												.addComponent(shipTypeLbl)
												.addGroup(gl_contentPane.createSequentialGroup()
													.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
														.addGroup(gl_contentPane.createSequentialGroup()
															.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
																.addComponent(shipTypeCBox, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
																.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
																	.addComponent(weaponTypeCBox1, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
																	.addComponent(weaponTypeCBox2, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE))
																.addComponent(lblGunTypeSlot)
																.addComponent(lblGunTypeSlot_1))
															.addPreferredGap(ComponentPlacement.RELATED)
															.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
																.addComponent(lblGunNameSlot_1)
																.addGroup(gl_contentPane.createSequentialGroup()
																	.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
																		.addGroup(gl_contentPane.createSequentialGroup()
																			.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
																				.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
																					.addComponent(shipName, 0, 148, Short.MAX_VALUE)
																					.addComponent(weaponNamesSlot1, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
																					.addComponent(weaponSlot2, 0, 148, Short.MAX_VALUE))
																				.addComponent(shipNameLbl))
																			.addPreferredGap(ComponentPlacement.RELATED)
																			.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
																				.addComponent(currentWorldCBox, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
																				.addComponent(lblChapter)))
																		.addComponent(lblGunNameSlot))
																	.addGap(18)
																	.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
																		.addComponent(lblDangerLevel)
																		.addComponent(dangerLevel, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
																		.addComponent(lblEnemyName)
																		.addComponent(enemyNameCBox, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)))))
														.addGroup(gl_contentPane.createSequentialGroup()
															.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
																.addGroup(gl_contentPane.createSequentialGroup()
																	.addComponent(buttonHE)
																	.addPreferredGap(ComponentPlacement.RELATED)
																	.addComponent(buttonAP)
																	.addGap(18)
																	.addComponent(evenRadioButton)
																	.addPreferredGap(ComponentPlacement.RELATED)
																	.addComponent(oddRadioButton))
																.addGroup(gl_contentPane.createSequentialGroup()
																	.addPreferredGap(ComponentPlacement.RELATED)
																	.addComponent(lblAmmoType)))
															.addGap(112)
															.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
																.addComponent(textPane, GroupLayout.DEFAULT_SIZE, 236, Short.MAX_VALUE)
																.addComponent(textPane_1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 236, Short.MAX_VALUE)
																.addComponent(lblGunTypeSlot_2, Alignment.LEADING)
																.addComponent(lblGunTypeSlot_3, Alignment.LEADING))))
													.addGap(95)
													.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
														.addComponent(descScrollPane, GroupLayout.DEFAULT_SIZE, 318, Short.MAX_VALUE)
														.addComponent(lblSkillDescription)
														.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
															.addGroup(gl_contentPane.createSequentialGroup()
																.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
																	.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
																		.addComponent(lblActiveSkills)
																		.addComponent(activeSkillScrollPane, GroupLayout.PREFERRED_SIZE, 318, GroupLayout.PREFERRED_SIZE))
																	.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
																		.addComponent(lblSkillUsers)
																		.addComponent(equipableShips, GroupLayout.PREFERRED_SIZE, 312, GroupLayout.PREFERRED_SIZE)))
																.addPreferredGap(ComponentPlacement.RELATED))
															.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
																.addComponent(lblSkillList)
																.addGroup(gl_contentPane.createSequentialGroup()
																	.addComponent(skillList, GroupLayout.PREFERRED_SIZE, 224, GroupLayout.PREFERRED_SIZE)
																	.addPreferredGap(ComponentPlacement.RELATED)
																	.addComponent(addSkill))))))))
										.addGroup(gl_contentPane.createSequentialGroup()
											.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
												.addComponent(isCritical)
												.addComponent(isManual))
											.addPreferredGap(ComponentPlacement.RELATED, 609, Short.MAX_VALUE)
											.addComponent(btnRemoveAll)
											.addGap(18)
											.addComponent(removeButton)
											.addGap(69)))
									.addGap(314)
									.addComponent(equipScrollPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addComponent(isFirstSalvo)))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(395)
							.addComponent(calculateButton)))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(30)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(shipTypeLbl)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblChapter)
							.addComponent(lblEnemyName))
						.addComponent(shipNameLbl)
						.addComponent(lblSkillList))
					.addGap(6)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(shipTypeCBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(shipName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(currentWorldCBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(addSkill)
						.addComponent(skillList, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
						.addComponent(enemyNameCBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblGunTypeSlot)
						.addComponent(lblGunNameSlot)
						.addComponent(lblSkillDescription)
						.addComponent(lblDangerLevel))
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(3)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(weaponTypeCBox1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(weaponNamesSlot1, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
								.addComponent(dangerLevel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblGunTypeSlot_1)
								.addComponent(lblGunNameSlot_1))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(weaponTypeCBox2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(weaponSlot2, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(descScrollPane, GroupLayout.PREFERRED_SIZE, 61, GroupLayout.PREFERRED_SIZE)))
					.addGap(18)
					.addComponent(lblSkillUsers)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(40)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(equipScrollPane, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
									.addGap(130))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
										.addGroup(gl_contentPane.createSequentialGroup()
											.addGap(22)
											.addComponent(lblActiveSkills)
											.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
										.addGroup(gl_contentPane.createSequentialGroup()
											.addComponent(lblGunTypeSlot_2)
											.addPreferredGap(ComponentPlacement.UNRELATED)))
									.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
										.addComponent(activeSkillScrollPane, GroupLayout.PREFERRED_SIZE, 94, GroupLayout.PREFERRED_SIZE)
										.addGroup(gl_contentPane.createSequentialGroup()
											.addComponent(lblAmmoType)
											.addGap(7)
											.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
												.addComponent(buttonHE)
												.addComponent(buttonAP)
												.addComponent(evenRadioButton)
												.addComponent(oddRadioButton))
											.addGap(23)
											.addComponent(isFirstSalvo))
										.addComponent(textPane, GroupLayout.PREFERRED_SIZE, 51, GroupLayout.PREFERRED_SIZE))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
											.addComponent(removeButton)
											.addComponent(btnRemoveAll))
										.addComponent(isCritical)
										.addComponent(lblGunTypeSlot_3))
									.addGap(17)))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(isManual))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(14)
							.addComponent(equipableShips, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)))
					.addPreferredGap(ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
					.addComponent(calculateButton)
					.addGap(52))
				.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
					.addContainerGap(415, Short.MAX_VALUE)
					.addComponent(textPane_1, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
					.addGap(86))
		);
		skillDescriptionBox = new JTextPane();
		descScrollPane.setViewportView(skillDescriptionBox);
		skillDescriptionBox.setEditable(false);
		skillDescriptionBox.setText("No Skill Selected");
		skillDescriptionBox.setText(guiUtil.getSkillDescription((String) skillList.getSelectedItem()));
		
		activeSkillList = new JList();
		activeSkillList.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				try {
					skillDescriptionBox.setText(guiUtil.getSkillDescription((String) skillList.getSelectedItem()));
					equipableShips.setText(guiUtil.getSkillUsers((String) skillList.getSelectedItem()));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		activeSkillList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				try {
					skillDescriptionBox.setText(guiUtil.getSkillDescription((String) activeSkillList.getSelectedValue()));
					equipableShips.setText(guiUtil.getSkillUsers((String) activeSkillList.getSelectedValue()));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		activeSkillScrollPane.setViewportView(activeSkillList);
		activeSkillList.setVisibleRowCount(10);
		contentPane.setLayout(gl_contentPane);
	}
}
