import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class CarrierCalculations {
	/*
	 * Ship Params:
	 * []
	 * 
	 * Plane Params:
	 * [0-Shipname, 1-Aviation, 2-BombOneDmg, 3-OneToL, 4-OneToM, 5-OneToH, 6-BombTwoDmg, 7-TwoToL, 8-TwoToM, 9-TwoToH, 10-TorpedoDmg,
	 * 11-TorpToL, 12-TorpToM, 13-TorpToH]
	 */
	
	GUIutil gt;
	// Array List holding an array list of skills
	ArrayList<ArrayList<String>> multiSkills;
	// Array list of ship parameters
	ArrayList<String> sp;
	// Array List of weapon Parameters
	ArrayList<String> wp;
	// Array List of enemy parameters
	ArrayList<String> ep;
	// Amount of ordinance dropped by planes
	int bomb1;
	int bomb2;
	int torpedos;
	String shipType;
	String shipName;
	String wepType;
	String wepName;
	
	public CarrierCalculations(ArrayList<String> skillList, String shipType, String shipName, String wepType, String wepName, String enemy, String world,
			int bomb1, int bomb2, int torpedos) throws FileNotFoundException, IOException{
		for (int i = 0; i < skillList.size(); i++) {
			ArrayList<String> skillP = gt.getSkillParameters(skillList.get(i));
			multiSkills.add(skillP);
		}
		sp = gt.getShipParams(shipType, shipName);
		wp = gt.getWepParams(wepType, wepName);
		ep = gt.getEnemyParameters(enemy, world);
		this.bomb1 = bomb1;
		this.bomb2 = bomb2;
		this.torpedos = torpedos;
		this.shipType = shipType;
		this.shipName = shipName;
		this.wepType = wepType;
		this.wepName = wepName;
			
	}
	public double getFinalTotalDamage(int shipSlot, ArrayList<String> skillList, boolean crit, String world,
			int dangerLvl, int removeRandom, String ordinance) throws FileNotFoundException, IOException {
		////////////////////////////////////////////////////////////////////////////////////////////////////
		//Ordinance will be what is changing.
		double totalFinalDmg = 0;
		// Bomb One on plane, if exists
		if (Integer.parseInt(wp.get(2)) != 0) {
			totalFinalDmg += getFinalDamage(shipSlot, skillList, crit, world, dangerLvl, removeRandom, ordinance, 1);
		}
		// Bomb Two on plane, if exists
		if (Integer.parseInt(wp.get(6)) != 0) {
			totalFinalDmg += getFinalDamage(shipSlot, skillList, crit, world, dangerLvl, removeRandom, ordinance, 2);
		}
		// Torpedo on plane, if exists
		if (Integer.parseInt(wp.get(10)) != 0) {
			totalFinalDmg += getFinalDamage(shipSlot, skillList, crit, world, dangerLvl, removeRandom, ordinance, 3);
		}
		return totalFinalDmg;
		
	}
	
	/*
	 * armorSlot used to determine which armor slot percentage to use.
	 */
	public double getFinalDamage(int shipSlot, ArrayList<String> skillList, boolean crit, String world,
			int dangerLvl, int removeRandom, String ordinance, int armorSlot) throws FileNotFoundException, IOException {
		///////////////////////////////////////////////////////////////////////////////////////////////////
		double finalDmg = 0;
		if (!wepName.isEmpty() && wepName != null) {
			double correctedDamageStat = 0;
			double weaponTypeModStat = 0;
			double criticalDamageStat = 1; // Default at 1
			double armorModStat = 0;
			double airDmgRedStat = 1; // 1 for now. Number will change when carriers and plane damage is added in.
			double enhancingDmgStat = 1; // Default at 1
			double comboStat = 1; // Only ship with combo damage atm is U-47 so if she and her skill is selected, add 0.4.
			double lvlDiffStat = 0;
			double dmgRedStat = 1; // Care about dealing dmg to enemy, not dmg self is taking.
			double injRatStat = 0;
			double dmgRatStat = 0;
			double dmgNatStat = 0;
			double dmgTypeStat = 0;
			double ammoBuffStat = 1;
			// Corrected Damage Section
			correctedDamageStat = getCorrectedDamage(sp, wp, skillList, shipName, shipType, shipSlot);
			
			// Scaling Weapon Buffs (WeaponTypeMod)
			weaponTypeModStat = getWeaponTypeMod(wepType);
			
			// Critical Damage
			if (crit) {
				criticalDamageStat = getCriticalDamage(shipName, wepType, wepName, skillList);
			}
			
			// Armor Modifier
			armorModStat = getArmorModifier(wp, ep, skillList, shipName, wepType);
			
			// Enhancing Damage
			// Not needed. Carriers only.
			
			// Combo Damage
			if (shipName.equals("U-47") && skillList.contains("The Bull of Scapa Flow")) {
				comboStat += 0.40;
			}
			
			// Level Difference
			lvlDiffStat = getLevelDifference(ep, dangerLvl);
			
			// Injure Ratio
			injRatStat = getInjureRatio();
			
			// Damage Ratio
			dmgRatStat = getDamageRatio(shipName, wepType, skillList, ep);
			
			// Damage to Nation
			dmgNatStat = getDamageToNation(ep);
			
			// Damage to Type
			dmgTypeStat = getDamageToType(shipName, ep, skillList);
			
			// Ammo Type Buff
			// Not needed. Only bombs and torpedos being used.
			double intermediateDmg = (correctedDamageStat + removeRandom) * weaponTypeModStat * criticalDamageStat * armorModStat * (1 + injRatStat) * (1 + dmgRatStat) * lvlDiffStat * 
					(1 + dmgNatStat) * (1 + dmgTypeStat) * (1 + ammoBuffStat - 0) * airDmgRedStat * (1 + comboStat);
			double temp1 = Math.max(1, Math.floor(intermediateDmg));
			double temp2 = Math.floor(temp1 * enhancingDmgStat);
			finalDmg = Math.floor(temp2 * dmgRedStat);
		}
		
		return finalDmg;
	}
	
	private double getCorrectedDamage(ArrayList<String> sp, ArrayList<String> wp, ArrayList<String> skillList,
			String shipName, String shipType, int shipSlot) {
		double finalDmg = 0;
		
		
		return finalDmg;
	}

	public double getStackedStats(int theIndex, double startingValue) {
		double value = startingValue;
		for (int i = 0; i < multiSkills.size(); i++) {
			ArrayList<String> sp = multiSkills.get(i);
			value += Double.parseDouble(sp.get(theIndex));
		}
		return value;
	}
}
