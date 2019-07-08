/*
 * @author Brian Khang (Ekizochikku)
 * Class to hold cases for what weapons a ship will use in a slot
 * WILL ADD CARRIERS LATER!
 *
 *
 *REMEMBER TO INCLUDE ALL THE CASES BRIAN >:( - Kevin
 */
public class WepTypes {

	/*
	 * Check what weapons can be used on a light cruisers first weapon slot
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
	 * Check what weapons can be used on a light cruisers second weapon slot
	 */
	public String destroyerOfWorlds(int wepnum) {
		String theWep = "";
		switch (wepnum) {
			case 0:
				theWep = "DD";
				break;
		}
		return theWep;
	}
	/*
	 * Check what weapons can be used on a heavy cruisers first weapon slot
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
		default:
			break;
		}
		return theWep;
	}
	
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
	
	public String aviationBBOne(int wepnum) {
		String theWep = "";
		switch (wepnum) {
		case 0:
			theWep = "BB";
		default:
			break;
		}
		return theWep;
	}
	
	public String aviationBBTwo(int wepnum) {
		String theWep = "";
		switch (wepnum) {
		case 0:
			theWep = "SEAPLANE";
		default:
			break;
		}
		return theWep;
	}
	
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
 * CARRIERS METHODS GO HERE
 */
	
}