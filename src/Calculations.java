import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/*
 * @author Brian Khang (Ekizochikku)
 * Class that holds parts of formulas for the final damage calculation formula.
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


/*
 * SHIP THAT USE SALVO BONUS NEEDS CLARITY. FOR NOW ADDED THE BONUS SALVO DAMAGE TO THE DMG RATIO SINCE IT COUNTS AS THEIR GUN BEING FIRED LIKE A DD OR CL, UNLESS STATED OTHERWISE IN THE SKILLS.
 */
public class Calculations {
	
	GUIutil gt = new GUIutil();
	ArrayList<ArrayList<String>> multiSkills = new ArrayList<ArrayList<String>>();
	
	/**
	 * Method that calculates the final damage a single shot/torpedo/bomb will do on a target.
	 * Equation is broken up into different methods.
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
	 * @return finalDmg
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public double getFinalDamage(String shipType, String shipName, String wepType, String wepName, int shipSlot, ArrayList<String> skillList, boolean crit, String world,
			String enemy, int ammoType, boolean manual, boolean firstSalvo, int dangerLvl, int evenOdd, int removeRandom, boolean armorBreak, String noteColor, ArrayList<String> aux1, ArrayList<String> aux2) throws FileNotFoundException, IOException {
		//If statement to avoid index out of bounds if one of the weapon slots is empty
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
			ArrayList<String> sp = gt.getShipParams(shipType, shipName);
			ArrayList<String> wp = gt.getWepParams(wepType, wepName);
			ArrayList<String> ep = gt.getEnemyParameters(enemy, world);
			
			for (int i = 0; i < skillList.size(); i++) {
				ArrayList<String> skillP = gt.getSkillParameters(skillList.get(i));
				multiSkills.add(skillP);
			}
			// Corrected Damage Section
			correctedDamageStat = getCorrectedDamage(sp, wp, skillList, shipName, shipType, shipSlot, wepType, wepName, aux1, aux2);
			
			// Scaling Weapon Buffs (WeaponTypeMod)
			if (!(wepType.equals("DD GUNS")) || !(wepType.equals("CL GUNS")) || !(wepType.equals("CA GUNS")) || !(wepType.equals("BB GUNS")) || !(wepType.equals("TORPEDOS"))) {
				weaponTypeModStat = 1;
			} else {
				weaponTypeModStat = getWeaponTypeMod(wepType, shipName, armorBreak, skillList, ep);
			}
			
			// Critical Damage
			if (crit || (shipName.equals("Bismarck") && skillList.contains("Wahrheit"))) {
				criticalDamageStat = getCriticalDamage(shipName, wepType, wepName, skillList, evenOdd);
			}
			
			// Armor Modifier
			armorModStat = getArmorModifier(wp, ep, skillList, shipName, wepType, ammoType, noteColor);
			
			// Enhancing Damage
			if (firstSalvo) {
				enhancingDmgStat = getEnhancingDmg(manual, shipName, skillList, firstSalvo);
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
			dmgRatStat = getDamageRatio(shipName, wepType, skillList, evenOdd, ep, armorBreak, firstSalvo);
			
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
	/*
	 * Method to get the value of a certain stat from the skill parameters.
	 */
	public double getStackedStats(int theIndex, double startingValue) {
		double value = startingValue;
		for (int i = 0; i < multiSkills.size(); i++) {
			ArrayList<String> sp = multiSkills.get(i);
			value += Double.parseDouble(sp.get(theIndex));
		}
		return value;
	}
	
	public double getCorrectedDamage(ArrayList<String> sp, ArrayList<String> wp, ArrayList<String> skillList, String shipName, String shipType, int shipSlot, String wepType, String wepName, ArrayList<String> aux1, ArrayList<String> aux2) {
		double finalDmg = 0;
		double wepDmg = Double.parseDouble(wp.get(3)); // Weapon damage
		double wepCoff = Double.parseDouble(wp.get(4)); // Weapon coefficient
		double slotEff = 0; // Ship slot efficiency
		
		// Get the ship slot efficiency
		if (shipSlot == 1) {
			// First slot gun exception
			if (shipName.equals("Azuma") && wp.get(0).equals("Triple 310mm (Type 0 Prototype") && skillList.contains("Barrage Gunnery Manual")) {
				slotEff = Double.parseDouble(sp.get(6)) + 0.12;
			} else if (shipName.equals("Seattle") && skillList.contains("A Bow's String Has 2 Lines!")) {
				slotEff = Double.parseDouble(sp.get(6)) + 0.15;
			} else if (shipName.equals("Le Triomphant") && skillList.contains("Sword or Shield")) {
				slotEff = Double.parseDouble(sp.get(6)) + 0.20;
			} else if (shipName.equals("Kitakaze") && skillList.contains("Kitakaze Style - Unanimous Slash")) {
				slotEff = Double.parseDouble(sp.get(6) + 0.15);
			} else {
				slotEff = Double.parseDouble(sp.get(6)); // Normal 
			}
			// Tashkent Exception for multiple ships; Trajectory Marking
			if (skillList.contains("Trajectory Marking")) {
				slotEff += 0.1;
			}
			
			// Gangut Exception
			if (shipName.equals("Gangut") && skillList.contains("Long Live the Revolution!") && wepName.equals("Triple 305mm (Pattern 1907)")) {
				slotEff += 0.80;
			}
			
			// Reno Exception
			if (shipName.equals("Reno") && skillList.contains("Reno Barrage")) {
				slotEff += 0.10;
			}
			
			
		} else if (shipSlot == 2) {
			slotEff = Double.parseDouble(sp.get(7));
		} else {
			slotEff = Double.parseDouble(sp.get(8)); // FOR CARRIERS AND PLANES LATER.
		}
		
		// Get the related stats from the ship and weapon parameters and aux gear.
		double stat = 0;
		if (wepType.equals("TORPEDOS")) {
			stat = Double.parseDouble(sp.get(11)) + Double.parseDouble(wp.get(1)) + Double.parseDouble(aux1.get(3)) + Double.parseDouble(aux2.get(3));
		} else if (wepType.equals("PLANES")) {
			stat = 0; // PLACEHOLDER FOR AIRCRAFT
		} else {
			stat = Double.parseDouble(sp.get(10)) + Double.parseDouble(wp.get(1)) + Double.parseDouble(aux1.get(2)) + Double.parseDouble(aux2.get(2));
		}
			
		// L'Opiniatre Exception
		if (shipName.equals("L'Opiniatre")) {
			if (skillList.contains("A Witch Who Never Admits Defeat") && wepType.equals("TORPEDOS")) {
				stat = Double.parseDouble(sp.get(11)) + 40; 
			} else if (skillList.contains("A Witch Who Never Admits Defeat") && !wepType.equals("TORPEDOS")) {
				stat = Double.parseDouble(sp.get(10)) + 40;
			} else {
				System.out.println("No Stat Increase.");
			}
		}
		
		// Fading Memories of Glory Exception
		if (skillList.contains("Fading Memories of Glory") && wepType.equals("TORPEDOS")) {
			stat = 0;
		}
		
		// Stat increase from skills.
		double skillStat = 1;
		if (wepType.equals("TORPEDOS")) {
			skillStat = getStackedStats(9, 1);
		} else if (wepType.equals("PLANES")) {
			System.out.println(""); // PLACEHOLDER FOR AIRCRAFT
		} else {
			skillStat = getStackedStats(8, 1);
		}
		
		// Exceptions
		// North Carolina
		if (shipName.equals("North Carolina") && skillList.contains("AA Firepower") && wepType.equals("CANNON")) {
			skillStat += Double.parseDouble(sp.get(10)) * 0.30;
			
		// Z46	
		} else if (shipName.equals("Z46") && skillList.contains("Iron Wing Annihilation") && wepType.equals("CANNON")) {
			skillStat += Double.parseDouble(sp.get(10)) * 0.15;
			
		// Sirius
		// Does not need to specify because weapons will always be a cannon or a torpedo.
		} else if (shipName.equals("Sirius") && skillList.contains("Mark of Sirius")) {
			skillStat += 0.21;
			
		// Biloxi
		} else if (shipName.equals("Biloxi") && skillList.contains("Air-Surface Switch") && wepType.equals("CANNON")) {
			if (wepName.equals("Twin 127mm (5\"/38 Mk 38")) {
				skillStat -= .05;
			} else if (!wepName.equals("Twin 127mm (5\"/38 Mk 38")) {
				skillStat += .15;
			} else {
				System.out.println("Not firepower being calculated");
			}
			
		// Dido and Queen Elizabeth ; For the Queen. Ignore weapon type specification since BB/BC use cannons
		} else if (shipName.equals("Queen Elizabeth") && skillList.contains("For the Queen")) {
			skillStat += 0.07;
		} else {
			System.out.println("No Exceptions for skill stats");
		}
		
		// Scaling Damage. Exceptions for Monarch and Izumo.
		double scaling = 1;
		if (shipName.equals("Monarch") || shipName.equals("Izumo")) {
			scaling = 1.2;
		}
		double finalStat = stat * skillStat * scaling;
		finalDmg = wepDmg * wepCoff * slotEff * (1 + finalStat/100);
		return finalDmg;
	}
	
	/*
	 * WeaponTypeMod using the Injure by x and Damage by x.
	 */
	public double getWeaponTypeMod(String wepType, String shipName, boolean armorBreak, ArrayList<String> skillList, ArrayList<String> ep) {
		double buffDamage = 1;
		if (wepType.equals("TORPEDOS")) {
			buffDamage += getStackedStats(35, 0) + getStackedStats(38, 0);
		} else if (wepType.equals("PLANES")) {
			System.out.println();
		} else {
			buffDamage += getStackedStats(34, 0) + getStackedStats(37, 0);
		}
		
		// Exceptions
		if (shipName.equals("Nakiri Ayame") && skillList.contains("Demon Cutter Asura-Rakshasa")) {
			buffDamage += 0.08;
		} else if (shipName.equals("Baltimore") && skillList.contains("APsolute Ammunition") && ep.get(4).equals("H")) {
			buffDamage += 0.08;
		} else {
			System.out.println("No Armor Breaks");
		}
		
		return buffDamage;
	}
	
	/*
	 * Critical Damage Boost
	 */
	public double getCriticalDamage(String shipName, String wepType, String wepName, ArrayList<String> skillList, int evenOdd) {
		double critBuff = 0;
		if (wepType.equals("TORPEDOS")) {
			critBuff = getStackedStats(42, 1.5);
		} else {
			critBuff = getStackedStats(41, 1.5);
			if (shipName.equals("Jean Bart") && wepName.equals("Quadruple 380mm (Mle 1935)") && skillList.contains("Final Shot")) {
				critBuff += 0.50;
			}
			if (shipName.equals("Friedrich der Grosse") && skillList.contains("Sonata of Chaos") && evenOdd == 0) {
				critBuff += 0.50;
			}
		}
		return critBuff;
	}
	
	/*
	 * Armor mod. Determine how much to do against a certain armor type.
	 */
	public double getArmorModifier(ArrayList<String> wp, ArrayList<String> ep, ArrayList<String> skillList, String shipName, String wepType, int ammoType, String noteColor) {
		double armorMod = 0;
		String enemyArmor = ep.get(4);
		if (enemyArmor.equals("L")) {
			armorMod = Double.parseDouble(wp.get(6));
		} else if (enemyArmor.equals("M")) {
			armorMod = Double.parseDouble(wp.get(7));
		} else { // Heavy Armor
			armorMod = Double.parseDouble(wp.get(8));
		}
		if (!shipName.equals("Kawakaze") || !shipName.equals("Roon") || !shipName.equals("Massachusetts") || !shipName.equals("Kitikaze") || !shipName.equals("Baltimore")
				|| !shipName.equals("Gascogne (Muse)") || !shipName.equals("Admiral Hipper (Muse)") || !shipName.equals("Cleveland (Muse)") || !shipName.equals("Sheffield (Muse)")) {
			return armorMod;
		} else {
			if (shipName.equals("Kawakaze") && skillList.contains("Impartial Destruction")) {
				if (wepType.equals("TORPEDOS")) {
					armorMod = 1.15;
				}
			}
			if (shipName.equals("Roon") && skillList.contains("Expert Loader")) {
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
			if (shipName.equals("Massachusetts") && skillList.contains("2,700 Pounds of Justice")) {
				if (enemyArmor.equals("L")) {
					armorMod = .65;
				} else if (enemyArmor.equals("M")) {
					armorMod = 1.35;
				} else {
					armorMod = 1.15;
				}
			}
			if (shipName.equals("Kitakaze") && skillList.contains("Kitakaze Style - Unanimous Slash")) {
				if (!wepType.equals("TORPEDOS")) { 
						armorMod = 1.15;
				}
			}
			if (shipName.equals("Black Heart") && skillList.contains("Tricolor Oder")) {
				armorMod = 1.00;
			}
			if (shipName.equals("Baltimore") && skillList.contains("APsolute Ammunition")) {
				if (enemyArmor.equals("L")) {
					armorMod = .85;
				} else if (enemyArmor.equals("M")) {
					armorMod = 1.20;
				} else {
					armorMod = .85;
				}
			}
			if (shipName.equals("Sheffield (Muse)") && skillList.contains("Precise Arrow") && noteColor.equals("Blue")) {
				if (enemyArmor.equals("L")) {
					armorMod = 1.20;
				} else if (enemyArmor.equals("M")) {
					armorMod = 1.20;
				} else {
					armorMod = .90;
				}
			}
			if (shipName.equals("Cleveland (Muse)") && skillList.contains("Spiritual Chasing") && noteColor.equals("Purple")) {
				if (enemyArmor.equals("L")) {
					armorMod = 1.20;
				} else if (enemyArmor.equals("M")) {
					armorMod = 1.20;
				} else {
					armorMod = 1.00;
				}
			}
			if (shipName.equals("Gascogne (Muse)") && skillList.contains("Coeur Battant") && noteColor.equals("Red")) {
				if (enemyArmor.equals("L")) {
					armorMod = 1.40;
				} else if (enemyArmor.equals("M")) {
					armorMod = 1.15;
				} else {
					armorMod = 1.15;
				}
			}
			if (shipName.equals("Gascogne (Muse)") && skillList.contains("Coeur Battant") && noteColor.equals("Blue")) {
				if (enemyArmor.equals("L")) {
					armorMod = 1.00;
				} else if (enemyArmor.equals("M")) {
					armorMod = 1.30;
				} else {
					armorMod = 1.30;
				}
			}
			if (shipName.equals("Admiral Hipper (Muse)") && skillList.contains("Passionate Fever") && noteColor.equals("Red")) {
				if (enemyArmor.equals("L")) {
					armorMod = 1.10;
				} else if (enemyArmor.equals("M")) {
					armorMod = 1.20;
				} else {
					armorMod = 1.00;
				}
			}
			return armorMod;
		}
	}
	
	/*
	 * Return enhancing damage for BBs/BCs and their salvo.
	 */
	public double getEnhancingDmg(boolean manual, String shipName, ArrayList<String> skillList, boolean firstSalvo) {
		double enhance = 0;
		if (manual) {
			enhance = 1.2;
		} else {
			enhance = 1;
		}
		if (manual) {
			enhance += getStackedStats(33, 0) + getStackedStats(32, 0);
		} else {
			enhance += getStackedStats(32, 0);
		}
		
		// Exceptions
		if (shipName.equals("Massachusetts") && skillList.contains("2,700 Pounds of Justice")) {
			enhance += 0.30;
		} else if (shipName.equals("Little Renown") && skillList.contains("Knight's Shooting Training")) {
			enhance += 0.20;
		} else if (shipName.equals("Duke of York") && skillList.contains("Trepidation of Destruction") && firstSalvo) {
			enhance += 0.50;
		}
		return enhance;
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
	 * Returns a double of the bonus damage from damage ratio from skills.
	 */
	public double getDamageRatio(String shipName, String wepType, ArrayList<String> skillList, int evenOdd, ArrayList<String> ep, boolean armorBreak, boolean firstSalvo) throws FileNotFoundException, IOException {
		double ratio = 0;
		for (int i = 0; i < skillList.size(); i++) {
			ArrayList<String> holding = new ArrayList<String>();
			holding = gt.getSkillParameters(skillList.get(i));
			if (wepType.equals("TORPEDOS") && holding.get(6).equals("1")) {
				ratio += Double.parseDouble(holding.get(4));
			} else if (!wepType.equals("TORPEDOS") && holding.get(5).equals("1")) {
				if (shipName.equals("Friedrich der Grosse") && skillList.get(i).equals("Sonata of Chaos") && evenOdd == 1) {
					ratio += 0.2;
				} else if (shipName.equals("Minato Aqua") && skillList.contains("Failen Angel")) {
					//1.5 of the 2% is 1
					ratio += .01;
				} else {
					ratio += Double.parseDouble(holding.get(4));
				}
			} else {
				ratio += 0; //holder
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
			// Exception for Karlsruhe.
			if (shipName.equals("Karlsruhe(Retrofit)") && skillList.contains("Disturbance Strategy")) {
				if (ep.get(5).equals("TB") || ep.get(5).equals("SS") || ep.get(5).equals("GS")) {
					dmgToType += .25;
				}
			// Exception for Aurora	
			} 
			if (shipName.equals("Aurora") && skillList.contains("Silver Phantom")) {
				if (ep.get(5).equals("TB") || ep.get(5).equals("SS") || ep.get(5).equals("GS") || ep.get(5).equals("DD")) {
					dmgToType += .25;
				}
			}// Monitors ignored for now
		return dmgToType;
	}
	
	/*
	 * Returns a double of how much an ammo type is buffed.
	 */
	public double getBuffToAmmo(int ammoType) throws FileNotFoundException, IOException {
		double bta = 0;
			if (ammoType == 0) {
				bta = getStackedStats(30, 0);
			} else {
				bta = getStackedStats(31, 0);
			}
		return bta;
	}
}		
