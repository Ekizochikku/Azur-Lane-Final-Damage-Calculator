import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/*
 * @author Brian Khang (Ekizochikku)
 * Class that holds parts of formuals for the final damage calculation formula.
 * 
 * PARAMETERS
 * SHIPS
 * [0-Shipname, 1-Faction, 2-Class, 3-Shiptype, 4-Wep1, 5-Wep2, 6-Slot1eff, 7-Slot2eff, 8-Slot3eff, 9-Health, 10-Firepower, 11-Torpedo, 12-Antiair, 13-Skill1, 14-Skill2, 15-Skill3, 16-Skill4, 17-Skill5]
 * WEAPONS //NOT PLANES
 * [0-Weaponname, 1-Firepower/Torpedo stat, 2-Antiair stat, 3-Damage, 4-Coefficient, 5-Ammotype/Attribute, 6-Damage to Light, 7-Damage to Medium, 8-Damage to Heavy. 
 * ENEMIES
 * [0-World, 1-Enemy Name, 2-Level, 3-Health, 4-Armor, 5-Ship Type, 6-Nation]
 * SKILLS
 * [0-Skill Name, 1-Description, 2-Ship Names, 3-Injure Ratio, 4-Damage Ratio,5-Buff to Cannon Damage, 6-Buff to Torpedo Damage, 7-Buff to Air Damage,
 * 8-Buff to Firepower, 9-Buff to Torpedo, 9-Buff to Air, 10-Buff to Anti-Air, 11-Damage to HMS, 12-Damage to USS, 13-Damage to IJN, 14-Damage to KMS,
 * 15-Damage to ROC, 16-Damage to FFNF,17-Damage to MNF, 18-Damage to SIRENS, 19-Damage to DD, 20-Damage to CL, 21-Damage to CA, 22-Damage to LC,
 * 23-Damage to BC, 24-Damage to BB, 25-Damage to AB, 26-Damage to CVL, 27-Damage to CV, 28-Damage to SUB, 29-HE Buff, 30-AP Buff, 31-Initial Enhance,
 * 32-Manual Enhance, 33-Injure by Cannon, 34-Injure by Torpedo, 35-Injure by Air,36-Damage by Cannon, 37-Damage by Torpedo, 38-Damage by Air, 39-Combo Damage,
 * 40-Cannon Critical Damage, 41-Torpedo Critical Damage, 42-Air Critical Damage, 43-Salvo Bonus]
 */
public class Calculations {
	
	GUIutil gt = new GUIutil();
	
	/**
	 * Gets the final damage for the ship types
	 * Changed Brians comment to be more detailed so i don't have the remember each paameter
	 * @param shipType theShipType
	 * @param shipName name of the ship
	 * @param wepType
	 * @param wepName
	 * @param shipSlot
	 * @param skillList
	 * @param crit
	 * @param world
	 * @param enemy
	 * @param ammoType
	 * @param manual
	 * @param firstSalvo
	 * @param dangerLvl
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public double getFinalDamage(String shipType, String shipName, String wepType, String wepName, int shipSlot, ArrayList<String> skillList, boolean crit, String world,
			String enemy, int ammoType, boolean manual, boolean firstSalvo, int dangerLvl, int evenOdd, int removeRandom) throws FileNotFoundException, IOException {
		//If statement to avoid index out of bounds if one of the weapon slots is empty
		double finalDmg;
		if (!wepName.isEmpty() && wepName != null) { 
			ArrayList<String> sp = gt.getShipParams(shipType, shipName);
			ArrayList<String> wp = gt.getWepParams(wepType, wepName);
			ArrayList<String> ep = gt.getEnemyParameters(enemy, world);
			
			// Corrected Damage Formula
			double cd = correctedDamage(sp, wp, wepType, shipType, shipSlot, skillList, shipName);
			System.out.println("The damage after correctedDamage:" + cd);
			
			// Scaling Weapon Buffs (WeaponTypeMod)
			double wtm = 0;
			if (!(wepType.equals("DD GUNS")) || !(wepType.equals("CL GUNS")) || !(wepType.equals("CA GUNS")) || !(wepType.equals("BB GUNS")) || !(wepType.equals("TORPEDOS"))) {
				wtm = 1;
			} else {
				wtm = scalingWeaponBuff(wepType, skillList);
			}
			
			// Critical Damage
			double crd = 1;
			if (crit) {
				crd = criticalDamage(shipName, wepType, wepName, skillList, evenOdd);
				System.out.println("The damage after correctedDamage:" + cd);
			}
			
			// Armor Modifier
			double am = armorModifier(shipName, wepType, wp, ep, ammoType, skillList);
			
			// Air Damage Reduction
			double adr = 1; // WILL CHANGE WHEN PLANES ARE A FACTOR
			
			// Enhancing Damage
			double enhD = 1;
			if (firstSalvo) {
				enhancingDamage(skillList, manual);
			}
			
			// Combo Damage
			double combo = 1;
			if (shipName.equals("U-47")) { // Will create a method once other ships get combo damage skills
				combo += 0.4;
			}
			
			// Level Difference
			double lvlDiff = levelDifference(ep, dangerLvl);
			
			// Danger Level Reduction (Danger Level Mod)
			// No method needed because only care about damage to enemy ship. not reducing damage own ship takes
			double dmgRed = 1;
			
			// Injure Ratio
			double injRat = injureRatio(skillList);
			
			// Damage Ratio
			double dmgRat = damageRatio(shipName, wepType, skillList, evenOdd );
			
			// Damage to Nation
			double dmgNat = damageToNation(ep, skillList);
			
			// Damage to Type
			double dmgType = damageToType(shipName, ep, skillList);
			
			// Ammo Type Buff
			double ammoBuff = 0;
			if (!(ammoType == 0) || !(ammoType == 1)) {
				ammoBuff = buffToAmmo(skillList, ammoType);
			}
			// Calculate the final damage
			System.out.println("All the values within intermediate Dmg: \n Corrected Dmg: "+ cd + "\n weapon Scaling: " + wtm + "\n critical damage: " + crd + 
				"\n armor modifier"	+ am + "\n injure ratio " + (1+injRat) + "\n" + (1+dmgRat) + "\n Level Difference: " + lvlDiff + "\n Damage to nation: " + (1 + dmgType) +
				 "\n ammo buff: "+ (1 + ammoBuff - 0) +"\n air damage reduction: "+ adr +"\n combo damage: "+ (1 + combo));
			double intermediateDmg = (cd + removeRandom) * wtm * crd * am * (1 + injRat) * (1 + dmgRat) * lvlDiff * (1 + dmgNat) * (1 + dmgType) * (1 + ammoBuff - 0) * adr * (1 + combo);
			System.out.println("The intermediate damage" + intermediateDmg);
			double temp1 = Math.max(1, Math.floor(intermediateDmg));
			double temp2 = Math.floor(temp1 * enhD);
			finalDmg = Math.floor(temp2 * dmgRed);
		//for some reason it's not entering here
		} else {
			System.out.println("no weapon selected!");
			finalDmg = 0.0;
		}
		return finalDmg;
	}
	
	/*
	 * Returns a double that calculates the corrected damage of a ship based on the equipped weapon.
	 */
	public double correctedDamage(ArrayList<String> sp, ArrayList<String> wp, String wepType, String shipType, int shipSlot, ArrayList<String> skillList, String shipName) throws FileNotFoundException, IOException {
		double finalDamage = 0;
		double weaponDamage = Double.parseDouble(wp.get(3));
		double coefficient = Double.parseDouble(wp.get(4)); // Weapon coefficient
		double slotEfficiency = 0; // Efficiency of the ship slot
		
		//the sp is wrong
		if (shipSlot == 1) {
			// Azuma Exception
			if (shipName.equals("Azuma") && wp.get(0).equals("Triple 310mm (Type 0 Prototype")) {
				for (int i = 0; i < skillList.size(); i++) {
					if (skillList.get(i).equals("Barrage Gunnery Manual")) {
						slotEfficiency = Double.parseDouble(sp.get(6)) + 0.12;
					}
				}
			// Seattle Exception
			} else if (shipName.equals("Seattle")){
				for (int i = 0; i < skillList.size(); i++) {
					if (skillList.get(i).equals("A Bow's String Has 2 Lines!")) {
						slotEfficiency = Double.parseDouble(sp.get(6)) + 0.15;
					}
				}
			// Le Triomphant Exception
			} else if (shipName.equals("Le Triomphant")) {
				for (int i = 0; i < skillList.size(); i++) {
					if (skillList.get(i).equals("Offensive Configuration")) {
						slotEfficiency = Double.parseDouble(sp.get(6)) + 0.20;
					}
				}
			} else {
				slotEfficiency = Double.parseDouble(sp.get(6));
				System.out.println("The ship parameters: " + sp);
				System.out.println("The slot efficiency: " + slotEfficiency);

			}
		} else if (shipSlot == 2) {
			//potentially these values
			slotEfficiency = Double.parseDouble(sp.get(7));
		} else { // For planes later on
			slotEfficiency = Double.parseDouble(sp.get(8));
		}
		
		//Stat to add in. Firepower for Guns, Torpedo for Torpedos
		double stat = 0;
		if (wepType.equals("TORPEDOS")) {
			stat = Double.parseDouble(sp.get(11)) + Double.parseDouble(wp.get(1));
		} else { // for guns
			stat = Double.parseDouble(sp.get(10)) + Double.parseDouble(wp.get(1));
		} // Add in if-else for planes later
		
		// L'Opiniatre Exception
		if (shipName.equals("L'Opiniatre")) {
			for (int i = 0; i < skillList.size(); i++) {
				if (skillList.get(i).equals("A Witch Who Never Admits Defeat") && wepType.equals("TORPEDOS")) {
					stat = Double.parseDouble(sp.get(11)) + 40; 
				} else {
					stat = Double.parseDouble(sp.get(10)) + 40; 
				} 
			}
		}
		
		double skillStat = 1; // Stat increase from skills.
		for (int i = 0; i < skillList.size(); i++) {
			ArrayList<String> holding = new ArrayList<String>();
			holding = gt.getSkillParameters(skillList.get(i));
			if (wepType.equals("TORPEDOS")) {
				skillStat += Double.parseDouble(holding.get(9));
			} else {
				// Exception for North Carolina
				if (shipName.equals("North Carolina") && holding.get(0).equals("AA Firepower")) {
					skillStat += Double.parseDouble(sp.get(10)) * 0.30;
				//Exception for Z46
				} else if (shipName.equals("Z46") && holding.get(0).equals("Iron Wing Annihilation")) {
					skillStat += Double.parseDouble(sp.get(10)) * 0.15;
				} else {
					skillStat += Double.parseDouble(holding.get(8));	
				}
			}
		}
		
		// Scaling damage. Exceptions to Monarch and Izumo.
		double scaling = 1;
		if (shipName.equals("Monarch") || shipName.equals("Izumo")) {
			scaling = 1.2;
		}
		double finalStat = stat * skillStat * scaling;
		
		//ADD PLANE SCALING HERE
		System.out.println("Weapon damage: " + weaponDamage + "\n Coefficient :" + coefficient + "\n slotEfficiency " + slotEfficiency + "\n final stat: " + finalStat);
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
				buffDamage += Double.parseDouble(holding.get(33) + Double.parseDouble(holding.get(36)));
			}
		}
		return buffDamage;
	}
	
	/*
	 * Returns the a double with the skill multiplier for critical hits
	 * Crit resist will be added later when enemies have a crit resist stat.
	 */
	public double criticalDamage(String shipName, String wepType, String wepName, ArrayList<String> skillList, int evenOdd) throws FileNotFoundException, IOException {
		double critBuff = 1.5;
		for (int i = 0; i < skillList.size(); i++) {
			ArrayList<String> holding = new ArrayList<String>();
			holding = gt.getSkillParameters(skillList.get(i));
			if (wepType.equals("TORPEDOS")) {
				critBuff += Double.parseDouble(holding.get(41));
			} else { // ADD AIR DAMAGE AND PLANES ABOVER HERE
				//Exception for Jean Bart.
				if (shipName.equals("Jean Bart") && wepName.equals("Quadruple 380mm (Mle 1935)")) {
					critBuff += 0.5;
				} else if (shipName.equals("Friedrich der Grosse") && skillList.get(i).equals("Sonata of Chaos") && evenOdd == 0) {
					critBuff += 0.5;
				} else {
					critBuff += Double.parseDouble(holding.get(40));
				}
			}
		}
		return critBuff;
	}
	
	/*
	 * Returns a double with how much a weapon should do to a certain enemie's armor.
	 */
	public double armorModifier(String shipName, String wepType, ArrayList<String> wp, ArrayList<String> ep, int ammoType, ArrayList<String> skillList) {
		double armorMod = 0;
		System.out.println(ep);
		ArrayList<String> enemyParam = ep;
		String enemyArmor = enemyParam.get(4);
		if (enemyArmor.equals("L")) {
			armorMod = Double.parseDouble(wp.get(6));
		} else if (enemyArmor.equals("M")) {
			armorMod = Double.parseDouble(wp.get(7));
		} else { // Heavy Armor
			armorMod = Double.parseDouble(wp.get(8));
		}
		if (!shipName.equals("Kawakaze") || !shipName.equals("Roon") || !shipName.equals("Massachusetts") || !shipName.equals("Kitikaze")) {
			return armorMod;
		} else {
			// Exceptions
			for (int i = 0; i < skillList.size(); i++) {
				if (shipName.equals("Kawakaze") && skillList.get(i).equals("Piecring Torpedo Strike")) {
					if (wepType.equals("TORPEDOS")) {
						armorMod = 1.15;
					}
				}
				if (shipName.equals("Roon") && skillList.get(i).equals("Professional Reloader")) {
					if (wepType != "TORPEDOS") { // ADD NOT PLANES CHECK HERE LATER
						if (ammoType == 1) {
							if (enemyArmor.equals("L")) {
								armorMod = 1.35;
							} else if (enemyArmor.equals("M")) {
								armorMod = .95;
							} else { // Heavy Armor
								armorMod = .70;
							}
						} else { // Ammo is AP
							if (enemyArmor.equals("L")) {
								armorMod = .75;
							} else if (enemyArmor.equals("M")) {
								armorMod = 1.10;
							} else { // Heavy Armor
								armorMod = .75;
							}
						}
					}
				}
				if (shipName.equals("Massachusetts") && skillList.get(i).equals("2,700 Pounds of Justice")) {
					if (enemyArmor.equals("L")) {
						armorMod = .65;
					} else if (enemyArmor.equals("M")) {
						armorMod = 1.35;
					} else {
						armorMod = 1.15;
					}
				}
				if (shipName.equals("Kitakaze") && skillList.get(i).equals("Kitakaze Style - Unanimous Slash")) {
					if (!wepType.equals("TORPEDOS")) { // ADD PLANES IF HERE LATER
							armorMod = 1.15;
					}
				}
				if (shipName.equals("Black Heart") && skillList.get(i).equals("Tricolor Oder")) {
					armorMod = 1.00;
				}
			}
		}
		return armorMod;
	}
	
	/*
	 * Returns a double of the enhancing damage multiplier.
	 */
	public double enhancingDamage(ArrayList<String> skillList, boolean manual) throws FileNotFoundException, IOException {
		double enhance = 0;
		if (manual) {
			enhance = 1.2;
		} else {
			enhance = 1;
		}
		for (int i = 0; i < skillList.size(); i++) {
			ArrayList<String> holding = new ArrayList<String>();
			holding = gt.getSkillParameters(skillList.get(i));
			if (manual) {
				enhance += Double.parseDouble(holding.get(32)) + Double.parseDouble(holding.get(31));
			} else {
				enhance += Double.parseDouble(holding.get(31));
			}
		}
		return enhance;
	}
	
	/*
	 * Returns a double of the bonus damage that will be gained based off the level difference and danger level.
	 */
	public double levelDifference(ArrayList<String> ep, int dangerLvl) {
		double lvlDiff = 1 + Math.min(25, Math.max(-25, 120 - Integer.parseInt(ep.get(2) + dangerLvl))) * 0.02;
		return lvlDiff;
	}
	
	/*
	 * Returns a double  of the bonus damage from injure ratio from skills.
	 */
	
	public double injureRatio(ArrayList<String> skillList) throws FileNotFoundException, IOException {
		double ratio = 0;
		for (int i = 0; i < skillList.size(); i++) {
			ArrayList<String> holding = new ArrayList<String>();
			holding = gt.getSkillParameters(skillList.get(i));
			ratio += Double.parseDouble(holding.get(3));
		}
		return ratio;
	}
	
	/*
	 * Returns a double of the bonus damage from injure ratio from skills.
	 */
	public double damageRatio(String shipName, String wepType, ArrayList<String> skillList, int evenOdd) throws FileNotFoundException, IOException {
		double ratio = 0;
		for (int i = 0; i < skillList.size(); i++) {
			ArrayList<String> holding = new ArrayList<String>();
			holding = gt.getSkillParameters(skillList.get(i));
			if (wepType.equals("TORPEDOS") && holding.get(6).equals("1")) {
				ratio += Double.parseDouble(holding.get(4));
			} else if (!wepType.equals("TORPEDOS") && holding.get(5).equals("1")) {
				if (shipName.equals("Friedrich der Grosse") && skillList.get(i).equals("Sonata of Chaos") && evenOdd == 1) {
					ratio += 0.2;
				} else {
					ratio += Double.parseDouble(holding.get(4));
				}
			} else {
				ratio += 0; //holder
			}
			// ADD AIR AND PLANES HERE LATER
		}
		return ratio;
	}
	
	/*
	 * Returns a double of bonus damage to a nation.
	 */
	public double damageToNation(ArrayList<String> ep, ArrayList<String> skillList) throws FileNotFoundException, IOException {
		double dmgToNat = 0;
		String nation = ep.get(6);
		if (nation.equals("NULL")) {
			return dmgToNat;
		} else {
			for (int i = 0; i < skillList.size(); i++) {
				ArrayList<String> holding = new ArrayList<String>();
				holding = gt.getSkillParameters(skillList.get(i));
				switch (nation) {
				case "HMS":
					dmgToNat += Double.parseDouble(holding.get(11));
					break;
				case "USS":
					dmgToNat += Double.parseDouble(holding.get(12));
					break;
				case "IJN":
					dmgToNat += Double.parseDouble(holding.get(13));
					break;
				case "KMS":
					dmgToNat += Double.parseDouble(holding.get(14));
					break;
				case "ROC":
					dmgToNat += Double.parseDouble(holding.get(15));
					break;
				case "FFNF":
					dmgToNat += Double.parseDouble(holding.get(16));
					break;
				case "MNF":
					dmgToNat += Double.parseDouble(holding.get(17));
					break;
				case "SIREN":
					dmgToNat += Double.parseDouble(holding.get(18));
					break;
				default:
					break;
				}
			}
		}
		return dmgToNat;
	}
	
	/*
	 * Returns a double of bonus damage to a ship type.
	 */
	public double damageToType(String shipName, ArrayList<String> ep, ArrayList<String> skillList) throws FileNotFoundException, IOException {
		double dmgToType = 0;
		String shipType = ep.get(5);
		for (int i = 0; i < skillList.size(); i++) {
			ArrayList<String> holding = new ArrayList<String>();
			holding = gt.getSkillParameters(skillList.get(i));
			// Exception for Karlsruhe.
			if (shipName.equals("Karlsruhe(Retrofit)") && holding.get(0).equals("Disturbance Strategy")) {
				if (ep.get(5).equals("TB") || ep.get(5).equals("SS") || ep.get(5).equals("GS")) {
					dmgToType += .25;
				}
			// Exception for Aurora	
			} else if (shipName.equals("Aurora") && holding.get(0).equals("Silver Phantom")) {
				if (ep.get(5).equals("TB") || ep.get(5).equals("SS") || ep.get(5).equals("GS") || ep.get(5).equals("DD")) {
					dmgToType += .25;
				}
			} else {
				switch (shipType) {
				case "DD":
					dmgToType += Double.parseDouble(holding.get(19));
					break;
				case "CL":
					dmgToType += Double.parseDouble(holding.get(20));
					break;
				case "CA":
					dmgToType += Double.parseDouble(holding.get(21));
					break;
				case "LC":
					dmgToType += Double.parseDouble(holding.get(22));
					break;
				case "BC":
					dmgToType += Double.parseDouble(holding.get(23));
					break;
				case "BB":
					dmgToType += Double.parseDouble(holding.get(24));
					break;
				case "AB":
					dmgToType += Double.parseDouble(holding.get(25));
					break;
				case "CVL":
					dmgToType += Double.parseDouble(holding.get(26));
					break;
				case "CV":
					dmgToType += Double.parseDouble(holding.get(27));
					break;
				case "SUB":
					dmgToType += Double.parseDouble(holding.get(28));
					break;
				default:
					break;
				} // Monitors ignored for now
			}
			
		}
		return dmgToType;
	}
	
	/*
	 * Returns a double of how much an ammo type is buffed.
	 */
	public double buffToAmmo(ArrayList<String> skillList, int ammoType) throws FileNotFoundException, IOException {
		double bta = 0;
		for (int i = 0; i < skillList.size(); i++) {
			ArrayList<String> holding = new ArrayList<String>();
			holding = gt.getSkillParameters(skillList.get(i));
			if (ammoType == 0) {
				bta += Double.parseDouble(holding.get(29));
			} else {
				bta += Double.parseDouble(holding.get(30));
			}
		}
		return bta;
	}
}		
