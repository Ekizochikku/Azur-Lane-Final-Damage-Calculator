import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class CarrierCalculations {
	
	GUIutil gt;
	// Array List holding an array list of skills
	ArrayList<ArrayList<String>> skills;
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
	
	public CarrierCalculations(ArrayList<String> skillList, String shipType, String shipName, String wepType, String wepName, String enemy, String world,
			int bomb1, int bomb2, int torpedos) throws FileNotFoundException, IOException{
		for (int i = 0; i < skillList.size(); i++) {
			ArrayList<String> skillP = gt.getSkillParameters(skillList.get(i));
			skills.add(skillP);
		}
		sp = gt.getShipParams(shipType, shipName);
		wp = gt.getWepParams(wepType, wepName);
		ep = gt.getEnemyParameters(enemy, world);
		this.bomb1 = bomb1;
		this.bomb2 = bomb2;
		this.torpedos = torpedos;
			
	}
	
	public double getFinalDamage(String shipType, String shipName, String wepType, String wepName, int shipSlot, ArrayList<String> skillList, boolean crit, String world,
			String enemy, int ammoType, boolean manual, boolean firstSalvo, int dangerLvl, int evenOdd, int removeRandom, boolean armorBreak) throws FileNotFoundException, IOException {
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
			double ammoBuffStat = 0;
			// Corrected Damage Section
			correctedDamageStat = getCorrectedDamage(sp, wp, skillList, shipName, shipType, shipSlot, wepType);
			
			// Scaling Weapon Buffs (WeaponTypeMod)
			if (!(wepType.equals("DD GUNS")) || !(wepType.equals("CL GUNS")) || !(wepType.equals("CA GUNS")) || !(wepType.equals("BB GUNS")) || !(wepType.equals("TORPEDOS"))) {
				weaponTypeModStat = 1;
			} else {
				weaponTypeModStat = getWeaponTypeMod(wepType);
			}
			
			// Critical Damage
			if (crit) {
				criticalDamageStat = getCriticalDamage(shipName, wepType, wepName, skillList, evenOdd);
			}
			
			// Armor Modifier
			armorModStat = getArmorModifier(wp, ep, skillList, shipName, wepType, ammoType);
			
			// Enhancing Damage
			if (firstSalvo) {
				enhancingDmgStat = getEnhancingDmg(manual);
			}
			
			// Combo Damage
			if (shipName.equals("U-47") && skillList.contains("The Bull of Scapa Flow")) {
				comboStat += 0.40;
			}
			
			// Level Difference
			lvlDiffStat = getLevelDifference(ep, dangerLvl);
			
			// Injure Ratio
			injRatStat = getInjureRatio();
			
			// Damage Ratio
			dmgRatStat = getDamageRatio(shipName, wepType, skillList, evenOdd, ep, armorBreak);
			
			// Damage to Nation
			dmgNatStat = getDamageToNation(ep);
			
			// Damage to Type
			dmgTypeStat = getDamageToType(shipName, ep, skillList);
			
			// Ammo Type Buff
			if ((ammoType == 0) || (ammoType == 1)) {
				ammoBuffStat = getBuffToAmmo(ammoType);
			}
			double intermediateDmg = (correctedDamageStat + removeRandom) * weaponTypeModStat * criticalDamageStat * armorModStat * (1 + injRatStat) * (1 + dmgRatStat) * lvlDiffStat * 
					(1 + dmgNatStat) * (1 + dmgTypeStat) * (1 + ammoBuffStat - 0) * airDmgRedStat * (1 + comboStat);
			double temp1 = Math.max(1, Math.floor(intermediateDmg));
			double temp2 = Math.floor(temp1 * enhancingDmgStat);
			finalDmg = Math.floor(temp2 * dmgRedStat);
		}
		
		return finalDmg;
	}
}
