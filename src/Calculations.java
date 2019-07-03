import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/*
 * Class that holds methods to begin calculations.
 * PARAMETERS
 * SHIPS
 * [0-Shipname, 1-Faction, 2-Class, 3-Shiptype, 4-Slot1eff, 5-Slot2eff, 6-Slot3eff, 7-Health, 8-Firepower, 9-Torpedo, 10-Antiair, 11-Skill1, 12-Skill2, 13-Skill3, 14-Skill4, 15-Skill5]
 * WEAPONS //NOT PLANES
 * [0-Weaponname, 1-Firepower/Torpedo stat, 2-Antiair stat, 3-Damage, 4-Coefficient, 5-Ammotype/Attribute, 6-Damage to Light, 7-Damage to Medium, 8-Damage to Heavy. 
 * ENEMIES
 * [0-World, 1-Enemy Name, 2-Level, 3-Health, 4-Armor, 5-Ship Type]
 * SKILLS
 * [0-Skill Name, 1-Description, 2-Ship Names, 3-Injure Ratio, 4-Damage Ratio,5-Buff to Cannon Damage, 6-Buff to Torpedo Damage, 7-Buff to Air Damage,
 * 8-Buff to Firepower, 9-Buff to Torpedo, 9-Buff to Air, 10-Buff to Anti-Air, 11-Damage to HMS, 12-Damage to USS, 13-Damage to IJN, 14-Damage to KMS,
 * 15-Damage to ROC, 16-Damage to FFNF,17-Damage to MNF, 18-Damage to SIRENS, 19-Damage to DD, 20-Damage to CL, 21-Damage to CA, 22-Damage to LC,
 * 23-Damage to BC, 24-Damage to BB, 25-Damage to AB, 26-Damage to CVL, 27-Damage to CV, 28-Damage to SUB, 29-HE Buff, 30-AP Buff, 31-Initial Enhance,
 * 32-Manual Enhance, 33-Injure by Cannon, 34-Injure by Torpedo, 35-Injure by Air,36-Damage by Cannon, 37-Damage by Torpedo, 38-Damage by Air, 39-Combo Damage,
 * 40-Overall Critical Damage, 41-Cannon Critical Damage, 42-Torpedo Critical Damage, 43-Air Critical Damage, 44-Salvo Bonus]
 */
public class Calculations {
	
	GUIutil gt = new GUIutil();
	
	/*
	 * Returns a double that is the final damage.
	 */
	public double getFinalDamage(String shipType, String shipName, String wepType, String wepName, int shipSlot, ArrayList<String> skillList) throws FileNotFoundException, IOException {
		ArrayList<String> sp = gt.getShipParams(shipType, shipName);
		ArrayList<String> wp = gt.getWepParams(wepType, wepName);
		// Corrected Damage Formula
		double cd = correctedDamage(sp, wp, wepType, shipType, shipSlot, skillList, shipName);
		// Scaling Weapon Buffs (WeaponTypeMod)
		double wtm = 0;
		if (!(wepType.equals("DD GUNS")) || !(wepType.equals("CL GUNS")) || !(wepType.equals("CA GUNS")) || !(wepType.equals("BB GUNS")) || !(wepType.equals("TORPEDOS"))) {
			wtm = 1;
		} else {
			wtm = scalingWeaponBuff(wepType, skillList);
		}
		
	}
	
	public double correctedDamage(ArrayList<String> sp, ArrayList<String> wp, String wepType, String shipType, int shipSlot, ArrayList<String> skillList, String shipName) throws FileNotFoundException, IOException {
		double finalDamage = 0;
		double weaponDamage = Double.parseDouble(wp.get(3));
		double coefficient = Double.parseDouble(wp.get(4)); // Weapon coefficient
		double slotEfficiency = 0; // Efficiency of the ship slot
		if (shipSlot == 1) {
			slotEfficiency = Double.parseDouble(sp.get(4));
		} else if (shipSlot == 2) {
			slotEfficiency = Double.parseDouble(sp.get(5));
		} else { // For planes later on
			slotEfficiency = Double.parseDouble(sp.get(6));
		}
		
		//Stat to add in. Firepower for Guns, Torpedo for Torpedos
		double stat = 0;
		if (wepType.equals("TORPEDOS")) {
			stat = Double.parseDouble(sp.get(9)) + Double.parseDouble(wp.get(1));
		} else { // for guns
			stat = Double.parseDouble(sp.get(8)) + Double.parseDouble(wp.get(1));
		} // Add in if-else for planes later
		
		double skillStat = 1; // Stat increase from skills.
		for (int i = 0; i < skillList.size(); i++) {
			ArrayList<String> holding = new ArrayList<String>();
			holding = gt.getSkillParameters(skillList.get(i));
			if (wepType.equals("TORPEDOS")) {
				skillStat += Double.parseDouble(holding.get(9));
			} else {
				skillStat += Double.parseDouble(holding.get(8));
			}
		}
		
		// Scaling damage. Exception two Monarch and Izumo
		double scaling = 1;
		if (shipName.equals("Monarch") || shipName.equals("Izumo")) {
			scaling = 1.2;
		}
		double finalStat = stat * skillStat * scaling;
		//ADD PLANE SCALING HERE
		finalDamage = weaponDamage * coefficient * slotEfficiency * (1 + finalStat/100);
		return finalDamage;
	}
	
	
	/*
	 * Injure by x and Damage by x will always use enemies as the target
	 * WeaponTypeMod
	 */
	public double scalingWeaponBuff(String wepType, ArrayList<String> skillList) throws FileNotFoundException, IOException {
		double buffDamage = 1;
		for (int i = 0; i < skillList.size(); i++) {
			ArrayList<String> holding = new ArrayList<String>();
			holding = gt.getSkillParameters(skillList.get(i));
			if (wepType.equals("TORPEDOS")) {
				buffDamage += Double.parseDouble(holding.get(34) + Double.parseDouble(holding.get(37)));
			} else { //ADD PLANES AND AIR DAMAGE ABOVE HERE WHEN IMPLEMENTING.
				
			}
		}
	}
}
