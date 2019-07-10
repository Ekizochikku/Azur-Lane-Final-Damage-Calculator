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
import java.awt.Color;
import java.awt.SystemColor;

public class mainCalc extends JFrame {

	private JPanel contentPane;
	
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
	
	//Even and odd check
	private int evenOdd = -1; //0 for even, 1 for odd, -1 for non selected
	
	private JButton removeButton;
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
	private JTextPane slot1Pane;
	private JTextPane slot2Pane;
	private JLabel lblGunTypeSlot_2;
	private JLabel lblGunTypeSlot_3;
	
	//Double for the damage range
	private double finalMaxDamageSlot1 = 0.0;
	private double finalMaxDamageSlot2 = 0.0;
	private double finalMinDamageSlot1 = 0.0;
	private double finalMinDamageSlot2 = 0.0;
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
		setBounds(100, 100, 1250, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setName("Azur Lane By David Blaine");
		setContentPane(contentPane);
		//We're going to need to change these labels later into the actual names. 
		//Currently cruisers only for now to avoid errors
		guiUtil = new GUIutil();
		String[] shipTypeList = {"DD", "CL", "CA", "LC", "BC", "BB", "AB", "MON"};
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
					System.out.println(currentShipName);
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
				System.out.println("the current ship name: " + currentShipName);
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
					
				} else {
					buttonHE.setSelected(true);
					buttonHE.setEnabled(false);
					buttonAP.setEnabled(false);
					buttonGroup.clearSelection();

					evenRadioButton.setEnabled(false);
					oddRadioButton.setEnabled(false);
					evenOdd = -1;
					System.out.println("The current even odd:" + evenOdd);
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
		//A second insertNames method for the initial screen
		GUIutil.insertNames(weaponNamesSlot1, false, currentWeaponType);
		currentWeaponName = (String) weaponNamesSlot1.getSelectedItem();
		weaponNamesSlot1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				currentWeaponName = (String) weaponNamesSlot1.getSelectedItem();
//				System.out.println(currentShipName);
				if(!currentShipName.isBlank()) {
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
				if(currentWeaponName.isBlank()) {
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
		//A second insertNames method for the initial screen
		GUIutil.insertNames(weaponSlot2, false, currentWeaponTypeSlot2);
		currentWeaponNameSlot2 = (String) weaponSlot2.getSelectedItem();
		weaponSlot2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				currentWeaponNameSlot2 = (String) weaponSlot2.getSelectedItem();
//				System.out.println(currentShipName);
				if(!currentShipName.isBlank()) {
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
					String theMaxDangerLevel = Integer.toString(currentDangerLevel);
					dangerLevelTBox.setText(theMaxDangerLevel);
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
				System.out.println("the current enemy is" + theCurrentEnemy);
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
				System.out.println("Checking parameters:  " + "\n Current Ship type: " + currentShipType +  "\n Ship Name: " + currentShipName +  
						"\n Weapon Type Slot 1: " + currentWeaponType +  "\n Weapon Name Slot 1: " + currentWeaponName +  "\n Current Skills: " +
						currentSkills +  "\n is Critical: " + critical +  "\n World number: " + theCurrentWorld + "\n Enemy Name: " + theCurrentEnemy+   "\n damage type int (0 HE, 1 AP) : " + 
						currentDMGType +  "\n is manual" + manual +  "\n is first salvo: " + firstSalvo +  "\n current max danger Level: " + currentDangerLevel);
				
				System.out.println("Checking parameters SLot 2:  " +
						"\n Weapon Type Slot 2: " + currentWeaponTypeSlot2 +  "\n Weapon Name Slot 2: " + currentWeaponNameSlot2);
				try {
					//Will add more if statements to check each parameter later to avoid null pointer exceptions especially when slot 1 has a weapon but 2 doesn't
					//Ship slot hard coded in, no idea what that is yet.
					if (!currentWeaponName.isEmpty() && currentWeaponName != null) {
						finalMaxDamageSlot1 = finalDamage.getFinalDamage(currentShipType, currentShipName, currentWeaponType, currentWeaponName, 1
								,currentSkills, critical, theCurrentWorld, theCurrentEnemy, currentDMGType, manual, firstSalvo, currentDangerLevel, true);
						System.out.println("The final damage = " + finalMaxDamageSlot1 );
						String displayDamageSlot1 = Double.toString(finalMaxDamageSlot1);
						slot1Pane.setText(displayDamageSlot1 + " - ");
					} else {
						//System.out.println("Null check working!");
						slot1Pane.setText("No Gun Selected for this Slot.");
					}
					System.out.println("The weapon name for slot 2: " + currentWeaponNameSlot2);
					if (!currentWeaponNameSlot2.isEmpty() && currentWeaponNameSlot2 != null) {
						//System.out.println("Null check not working!");
						
						finalMaxDamageSlot2 = finalDamage.getFinalDamage(currentShipType, currentShipName, currentWeaponTypeSlot2, currentWeaponNameSlot2, 2
								,currentSkills, critical, theCurrentWorld, theCurrentEnemy, currentDMGType, manual, firstSalvo, currentDangerLevel, true);
						
						
						
						System.out.println("The final damage Slot 2 = " + finalMaxDamageSlot2 );
						String displayDamageSlot2 = Double.toString(finalMaxDamageSlot2);
						slot2Pane.setText(displayDamageSlot2 + " - " );
					}else {
						System.out.println("Null check working!");
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
				System.out.println("even is selected should display 0: " + evenOdd);
			}
		});
		
		oddRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				evenOdd = 1;
				System.out.println("Odd is selected should display 1 :" + evenOdd );
			}
		});
		
		lblSkillList = new JLabel("Skill List:");
		
		lblSkillDescription = new JLabel("Skill Description:");
		
		lblSkillUsers = new JLabel("Skill Users:");
		
		lblActiveSkills = new JLabel("Active Skills:");
		
		
		slot1Pane = new JTextPane();
		slot1Pane.setEditable(false);
		
		
		slot2Pane = new JTextPane();
		slot2Pane.setEditable(false);
		
		lblGunTypeSlot_2 = new JLabel("Slot 1 Damage Range:");
		
		lblGunTypeSlot_3 = new JLabel("Slot 2 Damage Range:");
		
		
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
																		.addComponent(lblEnemyName)
																		.addComponent(enemyNameCBox, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
																		.addComponent(dangerLevelTBox, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)))))
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
																.addComponent(slot1Pane, GroupLayout.DEFAULT_SIZE, 236, Short.MAX_VALUE)
																.addComponent(slot2Pane, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 236, Short.MAX_VALUE)
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
						.addComponent(lblDangerLevel))
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(3)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(weaponTypeCBox1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(weaponNamesSlot1, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
								.addComponent(dangerLevelTBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
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
										.addComponent(slot1Pane, GroupLayout.PREFERRED_SIZE, 51, GroupLayout.PREFERRED_SIZE))
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
					.addPreferredGap(ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
					.addComponent(calculateButton)
					.addGap(52))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap(415, Short.MAX_VALUE)
					.addComponent(slot2Pane, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
					.addGap(86))
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
