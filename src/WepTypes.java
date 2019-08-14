/*
 * @author Brian Khang (Ekizochikku)
 * Class to hold cases for what weapons a ship will use in a slot
*/
public class WepTypes {

	/*
	 * Check what weapons can be used on a light cruisers first weapon slot
	 * @param wepnum
	 * @return theWep
	 */
	public String lightCruiserOne(int wepnum) {
		String theWep = "";
		switch (wepnum) {
			case 0:
				theWep = "DD";
				break;
			case 1:
				theWep = "CL";
				break;
			case 2:
				theWep = "DD/CL";
				break;
			default:
				break;
		}
		
		return theWep;
	}
	
	/*
	 * Check what weapons can be used on a light cruisers second weapon slot
	 * @param wepnum
	 * @return theWep
	 */
	public String lightCruiserTwo(int wepnum) {
		String theWep = "";
		switch (wepnum) {
			case 0:
				theWep = "TORPEDOS";
				break;
			case 1:
				theWep = "DD";
				break;
			case 2:
				theWep = "CL";
				break;
			case 3:
				theWep = "AA";
				break;
			case 4:
				theWep = "DD/CL";
				break;
			case 5:
				theWep = "CL/AA";
				break;
			default:
				break;
		}
		return theWep;
	}

	/*
	 * Check what weapons can be used on a destroyers first weapon slot
	 * @param wepnum
	 * @return theWep
	 */
	public String destroyerOfWorlds(int wepnum) {
		String theWep = "";
		switch (wepnum) {
			case 0:
				theWep = "DD";
				break;
			default:
				break;
		}
		return theWep;
	}
	/*
	 * Check what weapons can be used on a heavy cruisers first weapon slot
	 * @param wepnum
	 * @return theWep
	 */
	public String heavyCruiserOne(int wepnum) {
		String theWep = "";
		switch (wepnum) {
			case 0:
				theWep = "CA";
				break;
			case 1:
				theWep = "CL";
				break;
			case 2:
				theWep = "CA/CL";
				break;
			default:
				break;
		}
		return theWep;
	}
	
	/*
	 * Check what weapons can be used on a heavy cruisers second weapon slot
	 * @param wepnum
	 * @return theWep
	 */
	public String heavyCruiserTwo(int wepnum) {
		String theWep = "";
		switch (wepnum) {
		case 0:
			theWep = "TORPEDOS";
			break;
		case 1:
			theWep = "DD";
			break;
		case 2:
			theWep = "CL";
			break;
		case 3:
			theWep = "AA";
			break;
		case 4:
			theWep = "DD/CL";
			break;
		case 5:
			theWep = "CL/AA";
			break;
		default:
			break;
		}
		return theWep;
	}
	
	/*
	 * Check what weapons can be used on a large cruisers first weapon slot
	 * @param wepnum
	 * @return theWep
	 */
	public String largeCruiserOne(int wepnum) {
		String theWep = "";
		switch (wepnum) {
		case 0:
			theWep = "CB";
			break;
		case 1:
			theWep = "CA";
			break;
		case 2:
			theWep = "CB/CA";
			break;
		default:
			break;
		}
		return theWep;
	}
	
	/*
	 * Check what weapons can be used on a large cruisers second weapon slot
	 * @param wepnum
	 * @return theWep
	 */
	public String largeCruiserTwo(int wepnum) {
		String theWep = "";
		switch (wepnum) {
		case 0:
			theWep = "DD";
			break;
		case 1:
			theWep = "CL";
			break;
		case 2:
			theWep = "AA";
			break;
		default:
			break;
		}
		return theWep;
	}

	/*
	 * Check what weapons can be used on a battlecruisers first weapon slot
	 * @param wepnum
	 * @return theWep
	 */
	public String battlecruiserOne(int wepnum) {
		String theWep = "";
		switch (wepnum) {
		case 0:
			theWep = "BB";
			break;
		default:
			break;
		}
		return theWep;
	}

	/*
	 * Check what weapons can be used on a battlecruisers second weapon slot
	 * @param wepnum
	 * @return theWep
	 */
	public String battlecruiserTwo(int wepnum) {
		String theWep = "";
		switch (wepnum) {
		case 0:
			theWep = "DD";
			break;
		case 1:
			theWep = "CL";
			break;
		case 2:
			theWep = "DD/CL";
			break;
		default:
			break;
		}
		return theWep;
	}

	/*
	 * Check what weapons can be used on a battleships first weapon slot
	 * @param wepnum
	 * @return theWep
	 */
	public String battleshipOne(int wepnum) {
		String theWep = "";
		switch (wepnum) {
		case 0:
			theWep = "BB";
			break;
		default:
			break;
		}
		return theWep;
	}
	
	/*
	 * Check what weapons can be used on a battleships second weapon slot
	 * @param wepnum
	 * @return theWep
	 */
	public String battleshipTwo(int wepnum) {
		String theWep = "";
		switch (wepnum) {
		case 0:
			theWep = "BB";
			break;
		case 1:
			theWep = "AA";
			break;
		case 2:
			theWep = "DD/CL";
			break;
		default:
			break;
		}
		return theWep;
	}
	
	/*
	 * Check what weapons can be used on a aviation battleships first weapon slot
	 * @param wepnum
	 * @return theWep
	 */
	public String aviationBBOne(int wepnum) {
		String theWep = "";
		switch (wepnum) {
		case 0:
			theWep = "BB";
			break;
		default:
			break;
		}
		return theWep;
	}

	/*
	 * Check what weapons can be used on a aviation battleships second weapon slot
	 * @param wepnum
	 * @return theWep
	 */
	public String aviationBBTwo(int wepnum) {
		String theWep = "";
		switch (wepnum) {
		case 0:
			theWep = "SEAPLANE";
			break;
		default:
			break;
		}
		return theWep;
	}

	/*
	 * Check what weapons can be used on a monitors first weapon slot
	 * @param wepnum
	 * @return theWep
	 */
	public String monitorOne(int wepnum) {
		String theWep = "";
		switch (wepnum) {
		case 0:
			theWep = "BB";
			break;
		default:
			break;
		}
		return theWep;
	}

	/*
	 * Check what weapons can be used on a monitors second weapon slot
	 * @param wepnum
	 * @return theWep
	 */
	public String monitorTwo(int wepnum) {
		String theWep = "";
		switch (wepnum) {
		case 0:
			theWep = "DD";
			break;
		default:
			break;
		}
		return theWep;
	}
	
	/*
	 * Check what weapons can be used on a submarines first and second weapon slot
	 * @param wepnum
	 * @return theWep
	 */
	public String subOneAndTwo(int wepnum) {
		return "SUBTORPEDOS";
	}
	
	/*
	 * Check what weapons can be used on a submarines third weapon slot
	 * @param wepnum
	 * @return theWep
	 */
	public String subThree(int wepnum) {
		return "DD";
	}
	
	/*
	 * Check what weapons can be used on light/normal aircraft carriers in their first and second slots
	 * @param wepnum
	 * @return theWep
	 */
	public String carriersOneAndTwo(int wepnum) {
		String theWep = "";
		switch (wepnum) {
		case 0:
			theWep = "FIGHTERP";
			break;
		case 1:
			theWep = "BOMBERP";
			break;
		case 2:
			theWep = "TORPEDOP";
			break;
		case 3:
			theWep = "SEAPLANE";
			break;
		case 4:
			theWep = "AA";
			break;
		case 5:
			theWep = "CL";
			break;
		default:
			break;
		}
		return theWep;
	}
	
	/*
	 * Check what weapons can be used on light/normal aircraft carriers in their third slot
	 * @param wepnum
	 * @return theWep
	 */
	public String carriersThree(int wepnum) {
		String theWep = "";
		switch (wepnum) {
		case 0:
			theWep = "FIGHTERP";
			break;
		case 1:
			theWep = "BOMBERP";
			break;
		case 2:
			theWep = "TORPEDOP";
			break;
		case 3:
			theWep = "SEAPLANE";
			break;
		case 4:
			theWep = "AA";
			break;
		case 5:
			theWep = "CL";
			break;
		case 6:
			theWep = "BOMBERP/CL";
			break;
		default:
			break;
		}
		return theWep;
	}
	
}