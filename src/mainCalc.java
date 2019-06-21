

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JRadioButton;
import javax.swing.JComboBox;
import javax.swing.ButtonGroup;
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
	 * Create the frame.
	 */
	public mainCalc() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JComboBox shipName = new JComboBox();
		JComboBox weaponNames = new JComboBox();
		
		JComboBox shipList = new JComboBox();
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
		
		JLabel lblAmmoType = new JLabel("Ammo Type");
		
	
		
		/*
		 * @author: Kevin Nguyen (kvn96)
		 * creating the gui design with the proper buttons
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
								.addComponent(shipName, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
								.addComponent(weaponType, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
								.addComponent(weaponNames, Alignment.TRAILING, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(shipList, Alignment.TRAILING, 0, 60, Short.MAX_VALUE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(currentWorld, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED, 80, Short.MAX_VALUE)
									.addComponent(enemyName, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE))
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
						.addComponent(shipName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(shipList, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
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

