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
	
	// Gui Util Object
	GUIutil gt = new GUIutil();
	// Array List holding an array list of skills
	ArrayList<ArrayList<String>> multiSkills = new ArrayList<ArrayList<String>>();;
	// Array list of ship parameters
	ArrayList<String> sp = new ArrayList<String>();
	// Array List of weapon Parameters
	ArrayList<String> wp = new ArrayList<String>();;
	// Array List of enemy parameters
	ArrayList<String> ep = new ArrayList<String>();;
	// Amount of ordinance dropped by planes
	int bomb1;
	int bomb2;
	int torpedos;
	String shipType;
	String shipName;
	String wepType;
	String wepName;
	
	/*
	 * Consturctor for carrier calculations.
	 * Bomb1 is the amount of bombs dropped, same with bomb2 and torpedos.
	 */
	public CarrierCalculations(ArrayList<String> skillList, String shipType, String shipName, 
			String wepType, String wepName, String enemy, String world,
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
			int dangerLvl, int removeRandom) throws FileNotFoundException, IOException {
		////////////////////////////////////////////////////////////////////////////////////////////////////
		//Ordinance will be what is changing.
		double totalFinalDmg = 0;
		// Bomb One on plane, if exists
		if (Double.parseDouble(wp.get(2)) != 0) {
			totalFinalDmg += getFinalDamage(shipSlot, skillList, crit, world, dangerLvl, removeRandom, "bombOne", 1);
		}
		// Bomb Two on plane, if exists
		if (Double.parseDouble(wp.get(6)) != 0) {
			totalFinalDmg += getFinalDamage(shipSlot, skillList, crit, world, dangerLvl, removeRandom, "bombTwo", 2);
		}
		// Torpedo on plane, if exists
		if (Double.parseDouble(wp.get(10)) != 0) {
			totalFinalDmg += getFinalDamage(shipSlot, skillList, crit, world, dangerLvl, removeRandom, "torpedo", 3);
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
			//Can't divide using integers must make everything double!
			double airDmgRedStat = 150.0 / (150.0 + Double.parseDouble(ep.get(5))); // 1 for now. Number will change when carriers and plane damage is added in.
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
			correctedDamageStat = getCorrectedDamage(skillList, shipName, shipType, shipSlot, ordinance);
			
			// Scaling Weapon Buffs (WeaponTypeMod)
			weaponTypeModStat = getWeaponTypeMod(wepType);
			
			// Critical Damage
			if (crit) {
				criticalDamageStat = getCriticalDamage(shipName, wepType, wepName, skillList);
			}
			
			// Armor Modifier
			armorModStat = getArmorModifier(wp, ep, skillList, shipName, wepType, ordinance);
			
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
			
			//Debug Print out statements
			System.out.println("Intermediate Damage:" + intermediateDmg);
			System.out.println(correctedDamageStat + " " + removeRandom + " " +weaponTypeModStat + " " +criticalDamageStat +" " +
			armorModStat + " " +(1+ injRatStat) +" " + (1+ dmgRatStat) +" " + lvlDiffStat + " " +
					(1 + dmgNatStat) +" " + (1+ dmgTypeStat) +" " + (1+ ammoBuffStat) +" " + airDmgRedStat + " " + (1+ comboStat));
			
			
			double temp1 = Math.max(1, Math.floor(intermediateDmg));
			double temp2 = Math.floor(temp1 * enhancingDmgStat);
			System.out.println("temp2:" + temp2 + " dmgRedStat:" + dmgRedStat);
			finalDmg = Math.floor(temp2 * dmgRedStat);
		}
		
		return finalDmg;
	}
	
	
	private double getCorrectedDamage(ArrayList<String> skillList,
			String shipName, String shipType, int shipSlot, String ordinance) {
		double finalDmg = 0;
		double wepDmg = 0;
		double wepCoff = 1;
		double effSlot = 0;
		
		// Some planes can carry multiple bombs
		switch(ordinance) {
		case "bombOne":
			wepDmg = Double.parseDouble(wp.get(2));
		case "bombTwo":
			wepDmg = Double.parseDouble(wp.get(6));
		case "torpedo":
			wepDmg = Double.parseDouble(wp.get(10));
		default:
			break;
		}
		// Efficiency slot
		// Weapon is in slot 1
		if (shipSlot == 1) {
			effSlot = Double.parseDouble(sp.get(7));
		// Weapon is in slot 2
		} else if (shipSlot == 2) {
			effSlot = Double.parseDouble(sp.get(8));
		// Weapon is in slot 3
		} else {
			effSlot = Double.parseDouble(sp.get(9));
		}
		
		double statAttacker = Double.parseDouble(sp.get(14)) + Double.parseDouble(wp.get(1));
		double statBuff = getStackedStats(10, 1);
		double finalStatAttacker = statAttacker * statBuff * 0.80;
		finalDmg = wepDmg * wepCoff * effSlot * (1 + (finalStatAttacker/100));
		
		return finalDmg;
	}
	
	private double getWeaponTypeMod(String wepType) {
		double buffDamage = 1;
		buffDamage += getStackedStats(36, 0) + getStackedStats(39, 0);
		return buffDamage;
	}
	
	private double getCriticalDamage(String shipName, String wepType, String wepName, ArrayList<String> skillList) {
		double critBuff = 0;
		critBuff = getStackedStats(43, 1.5);
		return critBuff;
	}
	
	private double getArmorModifier(ArrayList<String> wp, ArrayList<String> ep, ArrayList<String> skillList,
			String shipName, String wepType, String ordinance) {
		double armorMod = 0;
		String enemyArmor = ep.get(4);
		if (ordinance.equals("bombOne")) {
			if (enemyArmor.equals("L")) {
				armorMod = Double.parseDouble(wp.get(3));
			} else if (enemyArmor.equals("M")) {
				armorMod = Double.parseDouble(wp.get(4));
			} else {
				armorMod = Double.parseDouble(wp.get(5));
			}
		} else if (ordinance.equals("BombTwo")) {
			if (enemyArmor.equals("L")) {
				armorMod = Double.parseDouble(wp.get(7));
			} else if (enemyArmor.equals("M")) {
				armorMod = Double.parseDouble(wp.get(8));
			} else {
				armorMod = Double.parseDouble(wp.get(9));
			}
		} else {
			if (enemyArmor.equals("L")) {
				armorMod = Double.parseDouble(wp.get(11));
			} else if (enemyArmor.equals("M")) {
				armorMod = Double.parseDouble(wp.get(12));
			} else {
				armorMod = Double.parseDouble(wp.get(13));
			}
		}
		
		return armorMod;
	}
	
	/*
	 * Returns a double of the bonus damage that will be gained based off the level difference and danger level.
	 */
	public double getLevelDifference(ArrayList<String> ep, int dangerLvl) {
		double lvlDiff = 1 + Math.min(25, Math.max(-25, 120 - Integer.parseInt(ep.get(2) + dangerLvl))) * 0.02;
		return lvlDiff;
	}
	
	/*
	 * Returns a double  of the bonus damage from injure ratio from skills.
	 */
	
	public double getInjureRatio() throws FileNotFoundException, IOException {
		double ratio = 0;
		ratio = getStackedStats(3, 0);
		return ratio;
	}
	
	/*
	 * Returns a double of the bonus damage from injure ratio from skills.
	 */
	public double getDamageRatio(String shipName, String wepType, ArrayList<String> skillList,ArrayList<String> ep) throws FileNotFoundException, IOException {
		double ratio = 0;
		for (int i = 0; i < skillList.size(); i++) {
			ArrayList<String> holding = new ArrayList<String>();
			if (holding.get(6).equals("7")) {
				ratio += Double.parseDouble(holding.get(4));
			}
		}
		return ratio;
	}
	
	/*
	 * Returns damage done to a nation
	 */
	public double getDamageToNation(ArrayList<String> ep) {
		double dmgToNat = 0;
		String nation = ep.get(6);
		if (nation.equals("NULL")) {
			return dmgToNat;
		} else {
			switch (nation) {
			case "HMS":
				dmgToNat = getStackedStats(12, 0);
				break;
			case "USS":
				dmgToNat = getStackedStats(13, 0);
				break;
			case "IJN":
				dmgToNat = getStackedStats(14, 0);
				break;
			case "KMS":
				dmgToNat = getStackedStats(15, 0);
				break;
			case "ROC":
				dmgToNat = getStackedStats(16, 0);
				break;
			case "FFNF":
				dmgToNat = getStackedStats(17, 0);
				break;
			case "MNF":
				dmgToNat = getStackedStats(18, 0);
				break;
			case "SIREN":
				dmgToNat = getStackedStats(19, 0);
				break;
			default:
				break;
			}
		}
		return dmgToNat;
	}
	
	/*
	 * Returns a double of bonus damage to a ship type.
	 */
	public double getDamageToType(String shipName, ArrayList<String> ep, ArrayList<String> skillList) throws FileNotFoundException, IOException {
		double dmgToType = 0;
		String shipType = ep.get(5);
		switch (shipType) {
			case "DD":
				dmgToType = getStackedStats(20, 0);
				break;
			case "CL":
				dmgToType = getStackedStats(21, 0);
				break;
			case "CA":
				dmgToType = getStackedStats(22, 0);
				break;
			case "LC":
				dmgToType = getStackedStats(23, 0);
				break;
			case "BC":
				dmgToType = getStackedStats(24, 0);
				break;
			case "BB":
				dmgToType = getStackedStats(25, 0);
				break;
			case "AB":
				dmgToType = getStackedStats(26, 0);
				break;
			case "CVL":
				dmgToType = getStackedStats(27, 0);
				break;
			case "CV":
				dmgToType = getStackedStats(28, 0);
				break;
			case "SUB":
				dmgToType = getStackedStats(29, 0);
				break;
			default:
				break;
			}
		return dmgToType;
	}

	public double getStackedStats(int theIndex, double startingValue) {
		double value = startingValue;
		for (int i = 0; i < multiSkills.size(); i++) {
			ArrayList<String> sp = multiSkills.get(i);
			value += Double.parseDouble(sp.get(theIndex));
		}
		return value;
	}
	
	//Getters and Setters for testing/debugging purposes in the gui
	public GUIutil getGt() {
		return gt;
	}
	public void setGt(GUIutil gt) {
		this.gt = gt;
	}
	public ArrayList<String> getWp() {
		return wp;
	}
	public void setWp(ArrayList<String> wp) {
		this.wp = wp;
	}
	public ArrayList<String> getEp() {
		return ep;
	}
	public void setEp(ArrayList<String> ep) {
		this.ep = ep;
	}
	public int getBomb1() {
		return bomb1;
	}
	public void setBomb1(int bomb1) {
		this.bomb1 = bomb1;
	}
	public int getBomb2() {
		return bomb2;
	}
	public void setBomb2(int bomb2) {
		this.bomb2 = bomb2;
	}
	public int getTorpedos() {
		return torpedos;
	}
	public void setTorpedos(int torpedos) {
		this.torpedos = torpedos;
	}
	public String getWepType() {
		return wepType;
	}
	public void setWepType(String wepType) {
		this.wepType = wepType;
	}
	public String getWepName() {
		return wepName;
	}
	public void setWepName(String wepName) {
		this.wepName = wepName;
	}
}
