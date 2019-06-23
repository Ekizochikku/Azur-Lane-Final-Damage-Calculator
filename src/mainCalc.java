import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
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
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JTextArea;

public class mainCalc extends JFrame {

	private JPanel contentPane;
	private JComboBox shipType;
	private JComboBox shipName;
	private String currentShipType;
	private String currentShipName;

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
	 * A test method to get all the ship names onto the jcombo box
	 * Need help specifying which names for which ships
	 * @author Kevin Nguyen
	 * @param comboBox The first button for the ship/weapon names
	 * @param isShip a boolean variable to determine if we're getting the ship or weapon names
	 * @param theType string that's passed into the getWeaponList method and gets the list of names from csv
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
	
	
	
	/*
	 * @author: Kevin Nguyen (kvn96)
	 * creating the gui design with the proper buttons
	 */
	public mainCalc() throws FileNotFoundException, IOException {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 750, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		String[] shipTypeList = {"DD", "CL", "CA", "LC", "BC", "BB", "AB", "MON", "CVL", "CV"};
		shipType = new JComboBox(shipTypeList);
		shipType.setMaximumRowCount(10);
		shipType.setSelectedIndex(0);
		currentShipType = (String) shipType.getSelectedItem();
		shipType.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
//				System.out.println("This is a test");
				try {
					currentShipType = (String) shipType.getSelectedItem();
					insertNames(shipName, true, currentShipType);
					currentShipName = (String) shipName.getSelectedItem();
//					System.out.println(currentShipName);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		JComboBox comboBox_2 = new JComboBox();
		
		//REMINDER STILL NEED LABELS ON TOP OF BUTTONS!!
		
		
		shipName = new JComboBox();
		shipName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				currentShipName = (String) shipName.getSelectedItem();
//				System.out.println(currentShipName);
			}
		});
		//testing the first button. 
		insertNames(shipName, true,  currentShipType);
		currentShipName = (String) shipName.getSelectedItem();
		
		JComboBox weaponNames = new JComboBox();
		
		JComboBox weaponType = new JComboBox();
		JComboBox currentWorld = new JComboBox();
		
		JComboBox enemyName = new JComboBox();
		JComboBox dangerLevel = new JComboBox();
			
		
		//The checkbox button for the user to determine if it's a critical hit, and first salvo
		JCheckBox isCritical = new JCheckBox("Critical Hit");
		JCheckBox isFirstSalvo = new JCheckBox("First Salvo");

		
		
		//Button group for the weapon damage types
		JRadioButton bulletType1 = new JRadioButton("HE");
		JRadioButton bulletType2 = new JRadioButton("AP");
		
		ButtonGroup group = new ButtonGroup();
		group.add(bulletType1);
		group.add(bulletType2);
		
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
								.addComponent(shipType, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
								.addComponent(weaponType, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
								.addComponent(weaponNames, Alignment.TRAILING, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
							.addComponent(bulletType1)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(bulletType2)))
					.addGap(72))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(shipType, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(shipName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(currentWorld, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(enemyName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(20)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(weaponType, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(weaponNames, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
						.addComponent(dangerLevel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, 75, Short.MAX_VALUE)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(isFirstSalvo)
						.addComponent(lblAmmoType))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(isCritical)
						.addComponent(bulletType1)
						.addComponent(bulletType2))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(calculateButton)
					.addGap(20))
		);
		contentPane.setLayout(gl_contentPane);
	}
}

