/*
 * @author Brian Khang (Ekizochikku)
 * Class to hold cases for what weapons a ship will use in a slot
 * WILL ADD CARRIERS LATER!
 */
public class WepTypes {

	/*
	 * Check what weapons can be used on a light cruisers first weapon slot
	 */
	public String lightCruiserOne(int wepnum) {
		String theWep = "";
		switch (wepnum) {
			case 0:
				theWep = "DD Guns";
				break;
			case 1:
				theWep = "CL Guns";
				break;
			case 2:
				theWep = "DD/CL Guns";
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
				theWep = "Torpedos";
				break;
			case 1:
				theWep = "DD Guns";
				break;
			case 2:
				theWep = "CL Guns";
				break;
			case 3:
				theWep = "AA Guns";
				break;
			case 4:
				theWep = "DD/CL Guns";
				break;
			case 5:
				theWep = "CL/AA Guns";
				break;
			default:
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
				theWep = "CA Guns";
				break;
			case 1:
				theWep = "CL Guns";
				break;
			case 2:
				theWep = "CA/CL Guns";
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
			theWep = "Torpedos";
			break;
		case 1:
			theWep = "DD Guns";
			break;
		case 2:
			theWep = "CL Guns";
			break;
		case 3:
			theWep = "AA Guns";
			break;
		case 4:
			theWep = "DD/CL Guns";
			break;
		case 5:
			theWep = "CL/AA Guns";
		default:
			break;
		}
		return theWep;
	}
	
	public String largeCruiserOne(int wepnum) {
		String theWep = "";
		switch (wepnum) {
		case 0:
			theWep = "CB Guns";
			break;
		case 1:
			theWep = "CA Guns";
			break;
		case 2:
			theWep = "CB/CA Guns";
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
			theWep = "DD Guns";
			break;
		case 1:
			theWep = "CL Guns";
			break;
		case 2:
			theWep = "AA Guns";
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
			theWep = "BB Guns";
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
			theWep = "DD Guns";
			break;
		case 1:
			theWep = "CL Guns";
			break;
		case 2:
			theWep = "DD/CL Guns";
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
			theWep = "BB Guns";
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
			theWep = "BB Guns";
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
			theWep = "BB Guns";
		default:
			break;
		}
		return theWep;
	}
	
	public String aviationBBTwo(int wepnum) {
		String theWep = "";
		switch (wepnum) {
		case 0:
			theWep = "Seaplane";
		default:
			break;
		}
		return theWep;
	}
	
	public String monitorOne(int wepnum) {
		String theWep = "";
		switch (wepnum) {
		case 0:
			theWep = "BB Guns";
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
			theWep = "DD Guns";
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
