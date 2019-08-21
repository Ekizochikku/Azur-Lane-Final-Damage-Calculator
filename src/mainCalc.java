import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
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
import javax.swing.JOptionPane;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.NumberFormatter;


import javax.swing.event.ListSelectionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.Color;
import java.awt.Component;
import java.awt.SystemColor;
import javax.swing.JFormattedTextField;
import java.awt.Font;

public class mainCalc extends JFrame {

	private JPanel contentPane;
	
	//the nodes killed textbox
	private JFormattedTextField nodesKilledTextField;
	//label for the above text field 
	JLabel nodeKilledLabel;
	//checkbox for the armor break
	JCheckBox isArmorBroken;
	
	//Allows user to select which ship types to display
	private JComboBox<?> shipTypeCBox;
	
	//Allows user to select specific ship from the selected ship type
	private JComboBox<String> shipName;
	
	//The current selected ship type
	private String currentShipType;
	
	//The current ship selected
	private String currentShipName;
	
	//Allows user to select the weapon type for slot 1 for current ship
	private JComboBox<Object> weaponTypeCBox1;
	
	//Allows user to select the weapon type for slot 2 for current ship
	private JComboBox<Object> weaponTypeCBox2;
	
	//Allows user to select the weapon for slot 1 on current ship
	private JComboBox<String> weaponNamesSlot1;
	
	//Allows user to select the weapon for slot 2 on current ship
	private JComboBox<String> weaponSlot2;
	
	//Allows user to select the current world they are on
	private JComboBox<String> currentWorldCBox;
	
	//Displays the description of the selected skill
	private JTextPane skillDescriptionBox;
	
	//Displays which skills the user have currently selected to apply stats to current ship
	private JList activeSkillList;
	
	//Allows user to select which skills to apply to current ship for damage calculations
	private JComboBox skillList;
	
	//An array to hold the current skills the user have selected
	private ArrayList currentSkills;
	
	//Displays the ships the selected skill can be applied too
	private JTextPane equipableShips;
	
	//The current selected weapon name
	private String currentWeaponName = null;
	
	//the calculate button
	JButton calculateButton; 
	
	//the array list that holds the 
	private ArrayList enemyParameters;
	
	
	//The current selected weapon for slot 2
	private String currentWeaponNameSlot2 = null;
	
	//Hard coded these in for the initial screen
	
	//The current world
	private String theCurrentWorld;
	//The current enemy the user's ship is attacking
	private String theCurrentEnemy = "Mahan Ship, Lvl 2";
	//The current selected weapon type for slot 2
	private String currentWeaponTypeSlot2 = "TORPEDOS";
	//The current selected weapon type
	private String currentWeaponType = "DDGUNS";
	
	
	//Allows user to select which enemy from a specific world they are fighting
	private JComboBox<Object> enemyNameCBox;
	
	//The current damage type being applied
	private int currentDMGType = 0; //0 = HE, 1 = AP
	
	private JTextField dangerLevelTBox;
	private int currentDangerLevel = 3;
	
	//Helper method calls
	private GUIutil guiUtil;
	
	//Should first salvo be applied in damage calculation
	private boolean firstSalvo = false;
	
	//Should critical strike be applied in damage calculation
	private boolean critical = false;
	private boolean manual = false;
	
	//toggle button for the armor break skill
	private boolean armorBreak = false;
	
	//Even and odd check
	private int evenOdd = -1; //0 for even, 1 for odd, -1 for non selected
	
	private JButton removeButton;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JRadioButton buttonHE;
	private JRadioButton buttonAP;
	private JRadioButton evenRadioButton;
	private JRadioButton oddRadioButton;
	private JTextPane slot1Pane;
	private JTextPane slot2Pane;
	private JTextField armorTextField;
	private JTextField typeTextField;
	private JTextField healthTextField;
	private JTextField AntiairTextField;
	private JTextField nationTextField;
	private JLabel lblBombsDropped1;
	private JLabel lblBombsDropped2;
	private JLabel lblTorpedosDropped;
	private JTextField textFieldP1B1;
	private JTextField textFieldP1B2;
	private JTextField textFieldP1T;
	private JTextField textFieldP2B1;
	private JTextField textFieldP2B2;
	private JTextField textFieldP2T;
	private JTextField textFieldP3B1;
	private JTextField textFieldP3B2;
	private JTextField textFieldP3T;
	private JLabel lblPlane1;
	private JLabel lblPlane2;
	private JLabel lblPlane3;
	private JLabel lblNewLabel;
	private JLabel lblSlotDamage;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					mainCalc frame = new mainCalc();
					frame.setVisible(true);
					//to prevent elements moving out of p
					frame.setResizable(false);
					boolean popUp = GUIutil.readPersistentVariables(0);
					if(popUp) {
						JScrollPane versionUpdate = GUIutil.overWritePopUpWithVersionHistory();
						JOptionPane.showMessageDialog(frame, versionUpdate, "Version History",  
								JOptionPane.INFORMATION_MESSAGE);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
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
					guiUtil.insertNames(weaponNamesCBox, false, currentWeaponType);
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
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1250, 703);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setName("Azur Lane By David Blaine");
		setContentPane(contentPane);
		//We're going to need to change these labels later into the actual names. 
		//Currently cruisers only for now to avoid errors
		guiUtil = new GUIutil();
		String[] shipTypeList = {"DD", "CL", "CA", "LC", "BC", "BB", "AB", "MON", "SUB", "CV"};
		shipTypeCBox = new JComboBox<Object>(shipTypeList);
		shipTypeCBox.setMaximumRowCount(10);
		shipTypeCBox.setSelectedIndex(0);
		
		currentShipType = (String) shipTypeCBox.getSelectedItem();

		
		//push test
		shipTypeCBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
//				System.out.println("This is a test");
				calculateButton.setEnabled(false);
				try {
					currentShipType = (String) shipTypeCBox.getSelectedItem();
					if(currentShipType == "CV") {
						threeBythreeSwitch(true);
					} else {
						threeBythreeSwitch(false);
					}
					GUIutil.insertNames(shipName, true, currentShipType);
					currentShipName = (String) shipName.getSelectedItem();
					if(currentShipName != "") {
						GUIutil.insertType(weaponTypeCBox1, 4, currentShipType, currentShipName, true);
						currentWeaponType = (String) weaponTypeCBox1.getSelectedItem();
						

						GUIutil.insertNames(weaponNamesSlot1, false, currentWeaponType);
						currentWeaponName = (String) weaponNamesSlot1.getSelectedItem();
						
						GUIutil.insertType(weaponTypeCBox2, 5, currentShipType, currentShipName, false);
						currentWeaponTypeSlot2 = (String) weaponTypeCBox2.getSelectedItem();
						
						
						
						GUIutil.insertNames(weaponSlot2, false, currentWeaponTypeSlot2);
						currentWeaponNameSlot2 = (String) weaponSlot2.getSelectedItem();
					}
					//System.out.println(currentShipName);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
				
		shipName = new JComboBox<String>();
		GUIutil.insertNames(shipName,true, currentShipType);
		shipName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				calculateButton.setEnabled(false);
				currentShipName = (String) shipName.getSelectedItem();
				boolean skillExist = currentSkills.contains("Just Gettin' Fired Up");
				System.out.println("Skill check " + skillExist + " current ship name " + currentShipName);
				//System.out.println("the current ship name: " + currentShipName);
				if(currentShipName.equals("Roon")) {
					//System.out.println("Not entering this check!!!");
					//The set enabled can be a method to reduce code reduncy if you want but it's only this
					buttonHE.setEnabled(true);
					buttonAP.setEnabled(true);
					//Test reminder: Friedrich is on BB ship type
				} else if(currentShipName.equals("Friedrich der Grosse")) {
					buttonHE.setEnabled(false);
					buttonAP.setEnabled(false);
					
					//Any default for radio buttons?
					evenRadioButton.setSelected(true);
					evenOdd = 1;
					evenRadioButton.setEnabled(true);
					oddRadioButton.setEnabled(true);
				} else if(currentShipName.equals("Alabama") && skillExist) {
					nodesKilledTextField.setEnabled(true);
					nodeKilledLabel.setEnabled(true);
					isArmorBroken.setEnabled(true);
					
				} else {
					buttonHE.setSelected(true);
					buttonHE.setEnabled(false);
					buttonAP.setEnabled(false);
					buttonGroup.clearSelection();

					evenRadioButton.setEnabled(false);
					oddRadioButton.setEnabled(false);
					evenOdd = -1;
					//System.out.println("The current even odd:" + evenOdd);
					nodesKilledTextField.setEnabled(false);
					nodesKilledTextField.setText("");
					nodeKilledLabel.setEnabled(false);
					isArmorBroken.setEnabled(false);
					isArmorBroken.setSelected(false);
				} 
				if(currentShipName != "") {
					try {
						GUIutil.insertType(weaponTypeCBox1, 4, currentShipType, currentShipName, true);
						currentWeaponType = (String) weaponTypeCBox1.getSelectedItem();
						GUIutil.insertNames(weaponNamesSlot1, false, currentWeaponType);
						
						GUIutil.insertType(weaponTypeCBox2, 5, currentShipType, currentShipName, false);
						currentWeaponTypeSlot2 = (String) weaponTypeCBox2.getSelectedItem();
						GUIutil.insertNames(weaponSlot2, false, currentWeaponTypeSlot2);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		
		//Hard Coded initial screen of weapon type will need to change later most likely.
		String[] weaponTypeList1 = {"DDGUNS"};
		weaponTypeCBox1 = new JComboBox<Object>(weaponTypeList1);
		weaponTypeCBox1.setMaximumRowCount(5);

		//weaponTypeCBox1.setSelectedIndex(0);
		currentWeaponType = (String) weaponTypeCBox1.getSelectedItem();
		//finished the action listener for weapons, copied from walter.
		weaponTypeCBox1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
//				System.out.println("This is a test");
				if(currentWeaponNameSlot2.isEmpty()) {
					calculateButton.setEnabled(false);
				}
				try {
					currentWeaponType = (String) weaponTypeCBox1.getSelectedItem();
					GUIutil.insertNames(weaponNamesSlot1, false, currentWeaponType);
					currentWeaponName = (String) weaponNamesSlot1.getSelectedItem();
//					System.out.println(currentShipName);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		weaponNamesSlot1 = new JComboBox<String>();
		weaponNamesSlot1.setFont(new Font("Tahoma", Font.BOLD, 10));
		//A second insertNames method for the initial screen
		GUIutil.insertNames(weaponNamesSlot1, false, currentWeaponType);
		currentWeaponName = (String) weaponNamesSlot1.getSelectedItem();
		weaponNamesSlot1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				currentWeaponName = (String) weaponNamesSlot1.getSelectedItem();
//				System.out.println(currentShipName);
				if(!currentShipName.isEmpty()) {
					calculateButton.setEnabled(true);
				}
			}
		});
		
		
		
		String[] weaponTypeList2 = {"TORPEDOS"};
		weaponTypeCBox2 = new JComboBox<Object>(weaponTypeList2);

		//weaponTypeCBox1.setSelectedIndex(0);
		currentWeaponTypeSlot2 = (String) weaponTypeCBox2.getSelectedItem();
		//finished the action listener for weapons, copied from walter.
		weaponTypeCBox2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
//				System.out.println("This is a test");
				if(currentWeaponName.isEmpty()) {
					calculateButton.setEnabled(false);
				}
				try {
					currentWeaponTypeSlot2 = (String) weaponTypeCBox2.getSelectedItem();
					GUIutil.insertNames(weaponSlot2, false, currentWeaponTypeSlot2);
					currentWeaponNameSlot2 = (String) weaponSlot2.getSelectedItem();
//					System.out.println(currentShipName);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		weaponSlot2 = new JComboBox<String>();
		weaponSlot2.setFont(new Font("Tahoma", Font.BOLD, 10));
		//A second insertNames method for the initial screen
		GUIutil.insertNames(weaponSlot2, false, currentWeaponTypeSlot2);
		currentWeaponNameSlot2 = (String) weaponSlot2.getSelectedItem();
		weaponSlot2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				currentWeaponNameSlot2 = (String) weaponSlot2.getSelectedItem();
//				System.out.println(currentShipName);
				if(!currentShipName.isEmpty()) {
					calculateButton.setEnabled(true);
				}
			}
		});
		
		
		
		

		dangerLevelTBox = new JTextField();
		dangerLevelTBox.setEditable(false);
		dangerLevelTBox.setBackground(SystemColor.controlHighlight);
		//hard code initial screen
		dangerLevelTBox.setText("3");

		
		
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
					currentDangerLevel = guiUtil.getDangerLevel(theCurrentWorld);
					theCurrentEnemy = (String) enemyNameCBox.getSelectedItem();

					String theMaxDangerLevel = Integer.toString(currentDangerLevel);
					dangerLevelTBox.setText(theMaxDangerLevel);
					
					enemyParameters = new ArrayList<String>();
					enemyParameters = GUIutil.getEnemyParameters(theCurrentEnemy, theCurrentWorld);
					healthTextField.setText((String) enemyParameters.get(3));
					armorTextField.setText((String) enemyParameters.get(4));
					AntiairTextField.setText((String) enemyParameters.get(5));
					typeTextField.setText((String) enemyParameters.get(6));
					nationTextField.setText((String) enemyParameters.get(7));
					
					} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		enemyNameCBox = new JComboBox<Object>();
		List<String> theEnemies = new ArrayList<String>();
		theEnemies = GUIutil.getAllEnemiesForSpecificWorld(theCurrentWorld, "Enemies.tsv", 1, 2);
		Collections.sort(theEnemies);
		enemyNameCBox.setModel(new DefaultComboBoxModel<Object>(theEnemies.toArray()));
		enemyNameCBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				theCurrentEnemy = (String) enemyNameCBox.getSelectedItem();
				//System.out.println("the current enemy is" + theCurrentEnemy);
				try {
					enemyParameters = new ArrayList<String>();
					enemyParameters = GUIutil.getEnemyParameters(theCurrentEnemy, theCurrentWorld);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				healthTextField.setText((String) enemyParameters.get(3));
				armorTextField.setText((String) enemyParameters.get(4));
				AntiairTextField.setText((String) enemyParameters.get(5));
				typeTextField.setText((String) enemyParameters.get(6));
				nationTextField.setText((String) enemyParameters.get(7));
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
		calculateButton = new JButton("Calculate");
		calculateButton.setEnabled(false);

		calculateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Calculate Data
				Calculations finalDamage = new Calculations();
				//Checking if all the parameters are correct
				/*System.out.println("Checking all the parameters:  " + "\n Current Ship type: " + currentShipType +  "\n Ship Name: " + currentShipName +  
						"\n Weapon Type Slot 1: " + currentWeaponType +  "\n Weapon Name Slot 1: " + currentWeaponName +  "\n Current Skills: " +
						currentSkills +  "\n is Critical: " + critical +  "\n World number: " + theCurrentWorld + "\n Enemy Name: " + theCurrentEnemy+   "\n damage type int (0 HE, 1 AP) : " + 
						currentDMGType +  "\n is manual" + manual +  "\n is first salvo: " + firstSalvo +  "\n current max danger Level: " + currentDangerLevel + "\n current even odd: " + evenOdd);*/
				
				/*hi System.out.println("Checking parameters SLot 2:  " +
						"\n Weapon Type Slot 2: " + currentWeaponTypeSlot2 +  "\n Weapon Name Slot 2: " + currentWeaponNameSlot2);*/
				try {
					//Will add more if statements to check each parameter later to avoid null pointer exceptions especially when slot 1 has a weapon but 2 doesn't
					//Ship slot hard coded in, no idea what that is yet.
					if (!currentWeaponName.isEmpty() && currentWeaponName != null) {
						Double finalMaxDamageSlot1 = finalDamage.getFinalDamage(currentShipType, currentShipName, currentWeaponType, currentWeaponName, 1
								,currentSkills, critical, theCurrentWorld, theCurrentEnemy, currentDMGType, manual, firstSalvo, currentDangerLevel, evenOdd, 2, armorBreak);
						Double finalMinDamageSlot1 = finalDamage.getFinalDamage(currentShipType, currentShipName, currentWeaponType, currentWeaponName, 1
								,currentSkills, critical, theCurrentWorld, theCurrentEnemy, currentDMGType, manual, firstSalvo, currentDangerLevel, evenOdd, 0, armorBreak);
						
						System.out.println("The final max damage = " + finalMaxDamageSlot1 );
						System.out.println("The final min damage = " + finalMinDamageSlot1 );

						String displayDamageSlot1 = Double.toString(finalMaxDamageSlot1);
						String displayMinDamageSlot1 = Double.toString(finalMinDamageSlot1);
						
						//Nodes killed test case 
						System.out.println("Current value in the nodes text field: " + 
						nodesKilledTextField.getValue());
						
						slot1Pane.setText(displayMinDamageSlot1 + " - " + displayDamageSlot1);
					} else {
						//System.out.println("Null check working!");
						slot1Pane.setText("No Gun Selected for this Slot.");
					}
					System.out.println("The weapon name for slot 2: " + currentWeaponNameSlot2);
					if (!currentWeaponNameSlot2.isEmpty() && currentWeaponNameSlot2 != null) {
						//System.out.println("Null check not working!");
						//Nodes killed test case 
						//System.out.println(nodesKilledTextField.getValue());
						
						Double finalMaxDamageSlot2 = finalDamage.getFinalDamage(currentShipType, currentShipName, currentWeaponTypeSlot2, currentWeaponNameSlot2, 2
								,currentSkills, critical, theCurrentWorld, theCurrentEnemy, currentDMGType, manual, firstSalvo, currentDangerLevel, evenOdd, 2, armorBreak);
						
						Double finalMinDamageSlot2 = finalDamage.getFinalDamage(currentShipType, currentShipName, currentWeaponTypeSlot2, currentWeaponNameSlot2, 2
								,currentSkills, critical, theCurrentWorld, theCurrentEnemy, currentDMGType, manual, firstSalvo, currentDangerLevel, evenOdd, 0, armorBreak);
						
						System.out.println("The final damage Slot 2 = " + finalMaxDamageSlot2 );
						String displayDamageSlot2 = Double.toString(finalMaxDamageSlot2);
						String displayMinDamageSlot2 = Double.toString(finalMinDamageSlot2);

						slot2Pane.setText(displayMinDamageSlot2 + " - " + displayDamageSlot2);
						
						
					}else {
						//System.out.println("Null check working!");
						slot2Pane.setText("No Gun Selected for this Slot.");
					}
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
			
		
		JLabel lblAmmoType = new JLabel("Ammo Type");
		
		/*
		*The gui components, this will be in it's own class later.
		*/
		currentSkills = new ArrayList<String>();
		
		skillList = new JComboBox<String>();
		AutoCompletion.enable(skillList);
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
				updateSkillDescription(0);
			}
		});
		
		JButton addSkill = new JButton("Add Skill");
		addSkill.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				currentSkills.add("This is a test");
				currentSkills.add((String) skillList.getSelectedItem());
				//if statement for the nodes killed and armor break stuff
				//don't know if working yet as alabama not on the list
					if(( ((String)skillList.getSelectedItem()).equals("Final AP Drive")) 
							&& currentShipName != null &&
							(currentShipName.equals("Baltimore"))) {
						System.out.println("entered correctly!");
						nodesKilledTextField.setEnabled(true);
						nodeKilledLabel.setEnabled(true);
						isArmorBroken.setEnabled(true);
					}
					
				System.out.println("Adding skill " + skillList.getSelectedItem() + "current ship name:" + currentShipName);
				updateActiveSkills();
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
					
					//checks if Just Gettin' Fired Up exists in the skill list
					//which will keep the nodes stuff enabled or disable if DNE
					boolean skillExist = currentSkills.contains("Just Gettin' Fired Up");
					if(!skillExist) {
						nodesKilledTextField.setEnabled(false);
						nodesKilledTextField.setText("");
						nodeKilledLabel.setEnabled(false);
						isArmorBroken.setEnabled(false);
						isArmorBroken.setSelected(false);
					}
					
				}
				updateActiveSkills();
				updateSkillDescription(0);
			}
		});
		
		JButton btnRemoveAll = new JButton("Remove All");
		btnRemoveAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				currentSkills.clear();
				updateActiveSkills();
				updateSkillDescription(0);
				nodesKilledTextField.setEnabled(false);
				nodesKilledTextField.setText("");
				nodeKilledLabel.setEnabled(false);
				isArmorBroken.setEnabled(false);
				isArmorBroken.setSelected(false);
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
		
		AutoCompletion.enable(shipName);
		AutoCompletion.enable(weaponNamesSlot1);
		AutoCompletion.enable(weaponSlot2);
		
		evenRadioButton = new JRadioButton("Even");
		buttonGroup.add(evenRadioButton);
		oddRadioButton = new JRadioButton("Odd");
		buttonGroup.add(oddRadioButton);
		
		evenRadioButton.setEnabled(false);
		oddRadioButton.setEnabled(false);
		
		evenRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				evenOdd = 0;
//				System.out.println("even is selected should display 0: " + evenOdd);
			}
		});
		
		oddRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				evenOdd = 1;
//				System.out.println("Odd is selected should display 1 :" + evenOdd );
			}
		});
		
		slot1Pane = new JTextPane();
		slot1Pane.setFont(new Font("Yu Gothic", Font.BOLD, 15));
		slot1Pane.setEditable(false);
		
		
		slot2Pane = new JTextPane();
		slot2Pane.setFont(new Font("Yu Gothic", Font.BOLD, 15));
		slot2Pane.setEditable(false);
		
		//All the labels
		JLabel shipTypeLbl = new JLabel("Ship Type:");
		JLabel shipNameLbl = new JLabel("Ship Name:");
		JLabel lblGunTypeSlot = new JLabel("Weapon Type Slot 1:");
		JLabel lblGunTypeSlot_1 = new JLabel("Weapon Type Slot 2:");
		JLabel lblGunNameSlot = new JLabel("Weapon Name Slot 1:");
		JLabel lblGunNameSlot_1 = new JLabel("Weapon Name Slot 2:");
		JLabel lblChapter = new JLabel("Chapter:");
		JLabel lblEnemyName = new JLabel("Enemy Name:");
		JLabel lblDangerLevel = new JLabel("Danger Lvl:");
		JLabel lblGunTypeSlot_2 = new JLabel("Slot 1 Damage Range:");
		JLabel lblGunTypeSlot_3 = new JLabel("Slot 2 Damage Range:");
		JLabel lblSkillList = new JLabel("Skill List:");
		JLabel lblSkillDescription = new JLabel("Skill Description:");
		JLabel lblSkillUsers = new JLabel("Skill Users:");
		JLabel lblActiveSkills = new JLabel("Active Skills:");
		JLabel lblArmor = new JLabel("Armor:");
		JLabel healthTextFieldLabel = new JLabel("Health:");
		JLabel lblAntiAir = new JLabel("Anti Air:");
		JLabel lblEnemyShipType = new JLabel("Type:");
		JLabel lblNation = new JLabel("Nation:");
		
		lblBombsDropped1 = new JLabel("Bombs 1 Dropped:");
		lblBombsDropped2 = new JLabel("Bombs 2 Dropped:");
		lblTorpedosDropped = new JLabel("Torpedos Dropped:");
		
		lblPlane1 = new JLabel("Plane 1");
		lblPlane2 = new JLabel("Plane 2");
		lblPlane3 = new JLabel("Plane 3");
				
		isArmorBroken = new JCheckBox("Armor Broken");
		isArmorBroken.setEnabled(false);
		isArmorBroken.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				armorBreak = !armorBreak;
			}
		});
	    
		//Nodes killed field
		
		/**
		 * @author Kevin Nguyen
		 * Creates the nodes killed text field and overrrides the source code to allow blanks
		 */
		NumberFormat format = NumberFormat.getInstance();
	    NumberFormatter formatter = new NumberFormatter(format) {
	    	/**
			 * no idea what this is for but it told me to
			 */
			private static final long serialVersionUID = 1L;

			//Overrides the number formatter so that it allows blanks
	    	//Before it didn't allow you to delete the last digit which was stupid
	    	@Override
	    	public Object stringToValue(String string)
                    throws ParseException {
                    if (string == null || string.length() == 0) {
                        return null;
                    }
						return super.stringToValue(string);
	    	}
        };
	    
	    formatter.setValueClass(Integer.class);
	    formatter.setMinimum(0);
	    formatter.setMaximum(99);
	    formatter.setAllowsInvalid(false);
	    formatter.setCommitsOnValidEdit(true);

		nodesKilledTextField = new JFormattedTextField(formatter);
		nodesKilledTextField.setEnabled(false);
	
		nodeKilledLabel = new JLabel("Nodes Killed:");
		nodeKilledLabel.setEnabled(false);
		
		//The 3x3 Chart
		//redundant code that was made by window builder not me 
		//will put them in a method and all in array to make them nicer later
		armorTextField = new JTextField();
		armorTextField.setBackground(SystemColor.controlHighlight);
		armorTextField.setEditable(false);
		armorTextField.setColumns(10);
		
		typeTextField = new JTextField();
		typeTextField.setColumns(10);
		typeTextField.setEditable(false);
		typeTextField.setBackground(SystemColor.controlHighlight);
		
		healthTextField = new JTextField();
		healthTextField.setColumns(10);
		healthTextField.setEditable(false);
		healthTextField.setBackground(SystemColor.controlHighlight);
		
		AntiairTextField = new JTextField();
		AntiairTextField.setColumns(10);
		AntiairTextField.setEditable(false);
		AntiairTextField.setBackground(SystemColor.controlHighlight);
		
		nationTextField = new JTextField();
		nationTextField.setColumns(10);
		nationTextField.setEditable(false);
		nationTextField.setBackground(SystemColor.controlHighlight);
		
		textFieldP1B1 = new JFormattedTextField(formatter);
		textFieldP1B1.setColumns(10);
		
		textFieldP1B2 = new JFormattedTextField(formatter);
		textFieldP1B2.setColumns(10);
		
		textFieldP1T = new JFormattedTextField(formatter);
		textFieldP1T.setColumns(10);
		
		textFieldP2B1 = new JFormattedTextField(formatter);
		textFieldP2B1.setColumns(10);
		
		textFieldP2B2 = new JFormattedTextField(formatter);
		textFieldP2B2.setColumns(10);
		
		textFieldP2T = new JFormattedTextField(formatter);
		textFieldP2T.setColumns(10);
		
		textFieldP3B1 = new JFormattedTextField(formatter);
		textFieldP3B1.setColumns(10);
		
		textFieldP3B2 = new JFormattedTextField(formatter);
		textFieldP3B2.setColumns(10);
		
		textFieldP3T = new JFormattedTextField(formatter);
		textFieldP3T.setColumns(10);
		threeBythreeSwitch(false);
		
		lblNewLabel = new JLabel("Weapon Type Slot 3:");
		lblNewLabel.setEnabled(false);
		
		JComboBox<Object> comboBox = new JComboBox<Object>(new Object[]{});
		comboBox.setEnabled(false);
		
		JLabel lblWeaponTypeSlot = new JLabel("Weapon Name Slot 3:");
		lblWeaponTypeSlot.setEnabled(false);
		
		JComboBox<String> comboBox_1 = new JComboBox<String>();
		comboBox_1.setFont(new Font("Tahoma", Font.PLAIN, 10));
		
		lblSlotDamage = new JLabel("Slot 3 Damage Range:");
		lblSlotDamage.setEnabled(false);
		
		JTextPane textPane = new JTextPane();
		textPane.setFont(new Font("Yu Gothic", Font.BOLD, 15));
		textPane.setEnabled(false);
		textPane.setEditable(false);
		
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(4)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(shipTypeLbl)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addComponent(shipTypeCBox, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
										.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
											.addComponent(weaponTypeCBox1, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
											.addComponent(weaponTypeCBox2, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE))
										.addComponent(lblGunTypeSlot)
										.addComponent(lblGunTypeSlot_1)
										.addComponent(lblNewLabel)
										.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addComponent(lblWeaponTypeSlot, GroupLayout.PREFERRED_SIZE, 151, GroupLayout.PREFERRED_SIZE)
										.addComponent(lblGunNameSlot_1)
										.addGroup(gl_contentPane.createSequentialGroup()
											.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
												.addComponent(shipName, 0, 174, Short.MAX_VALUE)
												.addComponent(weaponNamesSlot1, 0, 174, Short.MAX_VALUE)
												.addComponent(shipNameLbl)
												.addComponent(lblGunNameSlot))
											.addPreferredGap(ComponentPlacement.RELATED)
											.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
												.addComponent(lblDangerLevel, GroupLayout.DEFAULT_SIZE, 67, Short.MAX_VALUE)
												.addComponent(currentWorldCBox, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
												.addComponent(lblChapter)
												.addGroup(gl_contentPane.createSequentialGroup()
													.addComponent(dangerLevelTBox, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
													.addGap(9))))
										.addComponent(comboBox_1, 0, 247, Short.MAX_VALUE)
										.addComponent(weaponSlot2, 0, 247, Short.MAX_VALUE))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addComponent(healthTextFieldLabel)
										.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
											.addComponent(healthTextField, GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
											.addComponent(lblEnemyName)
											.addComponent(enemyNameCBox, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
											.addGroup(gl_contentPane.createSequentialGroup()
												.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
													.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
														.addComponent(armorTextField, GroupLayout.DEFAULT_SIZE, 67, Short.MAX_VALUE)
														.addComponent(typeTextField, GroupLayout.DEFAULT_SIZE, 67, Short.MAX_VALUE))
													.addComponent(lblArmor)
													.addComponent(lblEnemyShipType))
												.addPreferredGap(ComponentPlacement.UNRELATED)
												.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
													.addGroup(gl_contentPane.createSequentialGroup()
														.addComponent(nationTextField, GroupLayout.DEFAULT_SIZE, 73, Short.MAX_VALUE)
														.addPreferredGap(ComponentPlacement.RELATED))
													.addGroup(gl_contentPane.createSequentialGroup()
														.addComponent(lblNation)
														.addPreferredGap(ComponentPlacement.RELATED))
													.addComponent(lblAntiAir)
													.addComponent(AntiairTextField, GroupLayout.DEFAULT_SIZE, 73, Short.MAX_VALUE))))))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_contentPane.createSequentialGroup()
											.addPreferredGap(ComponentPlacement.RELATED)
											.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
												.addComponent(lblAmmoType)
												.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
													.addGroup(gl_contentPane.createSequentialGroup()
														.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
															.addComponent(isFirstSalvo)
															.addComponent(isManual)
															.addComponent(nodeKilledLabel))
														.addGap(18)
														.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
															.addComponent(isArmorBroken, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
															.addComponent(isCritical)
															.addComponent(nodesKilledTextField, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE))
														.addPreferredGap(ComponentPlacement.RELATED))
													.addGroup(gl_contentPane.createSequentialGroup()
														.addComponent(buttonHE)
														.addPreferredGap(ComponentPlacement.RELATED)
														.addComponent(buttonAP)
														.addPreferredGap(ComponentPlacement.UNRELATED)
														.addComponent(evenRadioButton)
														.addPreferredGap(ComponentPlacement.RELATED)
														.addComponent(oddRadioButton))))
											.addGap(163))
										.addGroup(gl_contentPane.createSequentialGroup()
											.addGap(42)
											.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
												.addComponent(lblTorpedosDropped)
												.addComponent(lblBombsDropped2)
												.addComponent(lblBombsDropped1))
											.addPreferredGap(ComponentPlacement.RELATED)
											.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
												.addComponent(textFieldP1B1, GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
												.addComponent(textFieldP1B2, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
												.addComponent(textFieldP1T, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
												.addComponent(lblPlane1, GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE))
											.addPreferredGap(ComponentPlacement.RELATED)
											.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
												.addComponent(textFieldP3T, 0, 0, Short.MAX_VALUE)
												.addComponent(textFieldP2B2, 0, 0, Short.MAX_VALUE)
												.addComponent(textFieldP2B1, 0, 0, Short.MAX_VALUE)
												.addComponent(lblPlane2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
											.addGap(4)
											.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
												.addComponent(textFieldP3B1, GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
												.addComponent(textFieldP2T, GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
												.addComponent(textFieldP3B2, GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
												.addComponent(lblPlane3, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
											.addGap(90)))
									.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
										.addGroup(gl_contentPane.createSequentialGroup()
											.addComponent(lblGunTypeSlot_2)
											.addGap(101))
										.addGroup(gl_contentPane.createSequentialGroup()
											.addComponent(lblGunTypeSlot_3)
											.addPreferredGap(ComponentPlacement.RELATED, 101, Short.MAX_VALUE))
										.addGroup(gl_contentPane.createSequentialGroup()
											.addComponent(slot1Pane, GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
											.addPreferredGap(ComponentPlacement.RELATED))
										.addGroup(gl_contentPane.createSequentialGroup()
											.addComponent(slot2Pane, GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
											.addPreferredGap(ComponentPlacement.RELATED))
										.addGroup(gl_contentPane.createSequentialGroup()
											.addComponent(lblSlotDamage, GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
											.addPreferredGap(ComponentPlacement.RELATED))
										.addGroup(gl_contentPane.createSequentialGroup()
											.addComponent(textPane, GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
											.addPreferredGap(ComponentPlacement.RELATED)))))
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(95)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addComponent(descScrollPane, GroupLayout.DEFAULT_SIZE, 318, Short.MAX_VALUE)
										.addComponent(lblSkillDescription)
										.addComponent(lblSkillList)
										.addGroup(gl_contentPane.createSequentialGroup()
											.addComponent(skillList, GroupLayout.PREFERRED_SIZE, 224, GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(addSkill))
										.addComponent(lblSkillUsers)
										.addComponent(lblActiveSkills)
										.addComponent(activeSkillScrollPane, GroupLayout.PREFERRED_SIZE, 318, GroupLayout.PREFERRED_SIZE)
										.addComponent(equipableShips, GroupLayout.PREFERRED_SIZE, 312, GroupLayout.PREFERRED_SIZE)))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(141)
									.addComponent(btnRemoveAll)
									.addGap(18)
									.addComponent(removeButton)))))
					.addGap(314)
					.addComponent(equipScrollPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(453)
					.addComponent(calculateButton)
					.addContainerGap(760, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
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
						.addComponent(lblDangerLevel)
						.addComponent(healthTextFieldLabel))
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(3)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(weaponTypeCBox1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(weaponNamesSlot1, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
								.addComponent(dangerLevelTBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(healthTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblGunTypeSlot_1)
								.addComponent(lblGunNameSlot_1)
								.addComponent(lblAntiAir)
								.addComponent(lblArmor, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(weaponTypeCBox2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(weaponSlot2, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
								.addComponent(AntiairTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(armorTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblEnemyShipType, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblNation)
								.addComponent(lblNewLabel)
								.addComponent(lblWeaponTypeSlot))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(typeTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(nationTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
								.addComponent(comboBox_1, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(descScrollPane, GroupLayout.PREFERRED_SIZE, 137, GroupLayout.PREFERRED_SIZE)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblSkillUsers)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(6)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblPlane1)
								.addComponent(lblPlane2)
								.addComponent(lblPlane3))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblGunTypeSlot_2)
								.addComponent(lblBombsDropped1)
								.addComponent(textFieldP1B1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(textFieldP2B1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(textFieldP3B1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addComponent(equipableShips, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(27)
									.addComponent(lblActiveSkills))
								.addComponent(slot1Pane, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(textFieldP3B2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(textFieldP2B2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(textFieldP1B2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblBombsDropped2))
							.addPreferredGap(ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(textFieldP1T, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(textFieldP3T, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(textFieldP2T, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblTorpedosDropped))))
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblAmmoType)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(buttonHE)
								.addComponent(buttonAP)
								.addComponent(evenRadioButton)
								.addComponent(oddRadioButton))
							.addGap(18)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(isFirstSalvo)
								.addComponent(isCritical))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(isArmorBroken)
								.addComponent(isManual))
							.addGap(18)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(nodeKilledLabel)
								.addComponent(nodesKilledTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(equipScrollPane, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(5)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
										.addComponent(activeSkillScrollPane, GroupLayout.PREFERRED_SIZE, 94, GroupLayout.PREFERRED_SIZE)
										.addGroup(gl_contentPane.createSequentialGroup()
											.addComponent(lblGunTypeSlot_3)
											.addPreferredGap(ComponentPlacement.UNRELATED)
											.addComponent(slot2Pane, GroupLayout.PREFERRED_SIZE, 52, GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(ComponentPlacement.UNRELATED)
											.addComponent(lblSlotDamage)))))
							.addGap(18)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(textPane, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
									.addComponent(btnRemoveAll)
									.addComponent(removeButton)))
							.addGap(16)))
					.addGap(18)
					.addComponent(calculateButton)
					.addGap(103))
		);
		skillDescriptionBox = new JTextPane();
		descScrollPane.setViewportView(skillDescriptionBox);
		skillDescriptionBox.setEditable(false);
		skillDescriptionBox.setText("No Skill Selected");
		skillDescriptionBox.setText(guiUtil.getSkillDescription((String) skillList.getSelectedItem()));
		
		activeSkillList = new JList<String>();
		activeSkillList.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				updateSkillDescription(0);
			}
		});
		activeSkillList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				updateSkillDescription(1);
			}
		});
		activeSkillScrollPane.setViewportView(activeSkillList);
		activeSkillList.setVisibleRowCount(10);
		contentPane.setLayout(gl_contentPane);
	}
	/**
	 * simple method to sets the 3x3 labels and boxes off or on
	 * might change to array later
	 * @param option
	 */
	protected void threeBythreeSwitch(boolean option) {
		if(option) {
			lblBombsDropped1.setEnabled(true);
			lblBombsDropped2.setEnabled(true);
			lblTorpedosDropped.setEnabled(true);
			lblPlane1.setEnabled(true);
			lblPlane2.setEnabled(true);
			lblPlane3.setEnabled(true);
			textFieldP3T.setEnabled(true);
			textFieldP3B2.setEnabled(true);
			textFieldP3B1.setEnabled(true);
			textFieldP2T.setEnabled(true);
			textFieldP2B1.setEnabled(true);
			textFieldP2B2.setEnabled(true);
			textFieldP1T.setEnabled(true);
			textFieldP1B2.setEnabled(true);
			textFieldP1B1.setEnabled(true);		
		} else {
			lblBombsDropped1.setEnabled(false);
			lblBombsDropped2.setEnabled(false);
			lblTorpedosDropped.setEnabled(false);
			lblPlane1.setEnabled(false);
			lblPlane2.setEnabled(false);
			lblPlane3.setEnabled(false);
			textFieldP3T.setEnabled(false);
			textFieldP3B2.setEnabled(false);
			textFieldP3B1.setEnabled(false);
			textFieldP2T.setEnabled(false);
			textFieldP2B1.setEnabled(false);
			textFieldP2B2.setEnabled(false);
			textFieldP1T.setEnabled(false);
			textFieldP1B2.setEnabled(false);
			textFieldP1B1.setEnabled(false);	
		}
		
	}
	/**
	 * Method to update the skill description text.
	 * @author Walter Hanson
	 * @param theOption is which skill location to display description for.
	 */
	protected void updateSkillDescription(int theOption) {
		try {
			if (theOption == 0) {
				skillDescriptionBox.setText(guiUtil.getSkillDescription((String) skillList.getSelectedItem()));
				equipableShips.setText(guiUtil.getSkillUsers((String) skillList.getSelectedItem()));
			}
			else {
				skillDescriptionBox.setText(guiUtil.getSkillDescription((String) activeSkillList.getSelectedValue()));
				equipableShips.setText(guiUtil.getSkillUsers((String) activeSkillList.getSelectedValue()));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/**
	 * Method to update the active skill JList.
	 * @author Walter Hanson
	 */
	@SuppressWarnings("unchecked")
	protected void updateActiveSkills() {
		activeSkillList.removeAll();
		Collections.sort(currentSkills);
		activeSkillList.setListData(currentSkills.toArray());
	}
}
