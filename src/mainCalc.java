import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JRadioButton;
import javax.swing.JComboBox;
import javax.swing.ButtonGroup;
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

public class mainCalc extends JFrame {

	private JPanel contentPane;
	private JComboBox shipTypeCBox;
	private JComboBox shipName;
	private String currentShipType;
	private String currentShipName;
	
	private JComboBox weaponTypeCBox1;
	private JComboBox weaponTypeCBox2;
	
	private JComboBox weaponNamesSlot1;
	private JComboBox weaponSlot2;
	
	private String currentWeaponType;
	private String currentWeaponName;
	
	private String currentWeaponTypeSlot2;
	private String currentWeaponNameSlot2;
	
	private int currentDMGType = -1; //0 = HE, 1 = AP
	private int currentWeaponNum = -1; 
	
	ArrayList<String> weaponTypeListSlot1;		

	private boolean firstSalvo = false;
	private boolean critical = false;
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
			initialUserChoice = theList.getWeaponList(theType);
		}
		
		comboBox.setModel(new DefaultComboBoxModel<Object>(initialUserChoice.toArray()));
	}
	
	public static void insertType(JComboBox<Object> comboBox, int weaponParamNum, String shipType, String shipName, boolean firstSlot) throws FileNotFoundException, IOException {
		ArrayList<String> weaponTypes = null; 
		String weaponNumString = null; 
		String exceptionCheck = "";
		int currentWeaponNum;
		GUIutil theList;
		theList = new GUIutil();
		weaponNumString = theList.getGetSpecificWeaponParam(shipType, shipName, weaponParamNum);
		currentWeaponNum = Integer.parseInt(weaponNumString);
		if(firstSlot) 
			weaponTypes = createWeaponTypeList(theList.checkSlotOneWeps(shipType, currentWeaponNum));
			
		else {
			System.out.println(currentWeaponNum);
			System.out.println(shipType);

			
			weaponTypes = createWeaponTypeList(theList.checkSlotTwoWeps(shipType, currentWeaponNum));
			

		}
		comboBox.setModel(new DefaultComboBoxModel<Object>(weaponTypes.toArray()));
	} 
	
	/**
	 * Using Brians methods we check what types of weapons can be used in what slot. Whatever string it returns we convert that into
	 * an array to insert it into the combo box.
	 * @author Kevin Nguyen
	 * @param compatibleWeapons the string that is returned from checkWeapon methods.
	 * @return the parsed string for our methods. 
	 */
	public static ArrayList<String> createWeaponTypeList(String compatibleWeapons) {
		ArrayList<String> weaponTypeArray = new ArrayList<String>();		
		String weaponType = "";
		String weaponType2 = "";
		boolean Torpedo = false;
		for (int i = 0; i <= 4; i++) {
			//System.out.println(compatibleWeapons.charAt(i));

			if(compatibleWeapons.charAt(i) == 'T') {
				System.out.println(compatibleWeapons.charAt(i));
				Torpedo = true;
				break;
			}
			if(i<2) {
				//System.out.println("Hello" + compatibleWeapons.charAt(i));
				weaponType += compatibleWeapons.charAt(i);
			}
			else if(i == 2 && compatibleWeapons.charAt(2) == ' ') {
				break;
			}
			else if(i>2){
				System.out.println(compatibleWeapons.charAt(i) + "why  ");
				weaponType2 += compatibleWeapons.charAt(i);
			}
		}
		if(Torpedo) {
			weaponTypeArray.add("TORPEDOS");
		} else {
			System.out.println(weaponType + " HELLO " + weaponType2);
			weaponTypeArray.add(weaponType + "GUNS");
			if(weaponType2 != "")
				weaponTypeArray.add(weaponType2 + "GUNS");
		}
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
		setBounds(100, 100, 750, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		//We're going to need to change these labels later into the actual names. 
		/* This was kinda annoying to understand. If my understanding from Gui util is correct
		 * Ships can only use certain weapon types, certain slots can only use certain types?
		 */
		//Currently cruisers only for now to avoid errors
		String[] shipTypeList = {"CL", "CA", "LC", "BC", "BB", "AB", "MON"};
		shipTypeCBox = new JComboBox(shipTypeList);
		shipTypeCBox.setMaximumRowCount(10);
		shipTypeCBox.setSelectedIndex(0);
		
		currentShipType = (String) shipTypeCBox.getSelectedItem();

		
		
		shipTypeCBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
//				System.out.println("This is a test");
				try {
					currentShipType = (String) shipTypeCBox.getSelectedItem();
					insertNames(shipName, true, currentShipType);
					currentShipName = (String) shipName.getSelectedItem();
					
					insertType(weaponTypeCBox1, 4, currentShipType, currentShipName, true);
					currentWeaponType = (String) weaponTypeCBox1.getSelectedItem();
					
					//Currently a bug with large cruisers for some reason
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
		weaponTypeCBox1 = new JComboBox(weaponTypeList1);
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
		
		
		
		
		JComboBox currentWorld = new JComboBox();
		JComboBox enemyName = new JComboBox();
		JComboBox dangerLevel = new JComboBox();
			
		
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
		JRadioButton buttonHE = new JRadioButton("HE");
		buttonHE.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				currentDMGType = 0;
//				System.out.println(currentDMGType);
			}
		});
		JRadioButton bulletAP = new JRadioButton("AP");
		bulletAP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				currentDMGType = 1;
//				System.out.println(currentDMGType);
			}
		});
		
		ButtonGroup group = new ButtonGroup();
		group.add(buttonHE);
		group.add(bulletAP);
		
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
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(165)
					.addComponent(calculateButton)
					.addContainerGap(180, Short.MAX_VALUE))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(shipTypeCBox, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
								.addComponent(weaponTypeCBox1, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
								.addComponent(weaponTypeCBox2, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
								.addComponent(weaponNamesSlot1, Alignment.TRAILING, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(weaponSlot2, Alignment.TRAILING, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(shipName, Alignment.TRAILING, 0, 150, Short.MAX_VALUE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(currentWorld, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED, 80, Short.MAX_VALUE)
									.addComponent(enemyName, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
									.addComponent(lblAmmoType)
									.addComponent(dangerLevel, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE))))
						.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(isCritical)
								.addComponent(isFirstSalvo))
							.addPreferredGap(ComponentPlacement.RELATED, 200, Short.MAX_VALUE)
							.addComponent(buttonHE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(bulletAP)))
					.addGap(72))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(shipTypeCBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(shipName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(currentWorld, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(enemyName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(20)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(weaponTypeCBox1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(weaponNamesSlot1, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
						.addComponent(dangerLevel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(20)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
							.addComponent(weaponTypeCBox2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(weaponSlot2, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, 75, Short.MAX_VALUE)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(isFirstSalvo)
						.addComponent(lblAmmoType))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(isCritical)
						.addComponent(buttonHE)
						.addComponent(bulletAP))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(calculateButton)
					.addGap(20))
		);
		contentPane.setLayout(gl_contentPane);
	}
}

