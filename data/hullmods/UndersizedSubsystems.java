package data.hullmods;

import java.util.HashMap;
import java.util.Map;

import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;

public class UndersizedSubsystems extends BaseHullMod {

	private static final float PEAK_MULT = 0.20f;
	
	//Goal is 33% total loss in typical case, with a multiplier of 20%
	// (original - offset) * (4/5) = original * (2/3)
	// offset = original/6
	
	private static Map peak_offset = new HashMap();
	static {
		peak_offset.put(HullSize.FRIGATE, 30f);			// Typ 180, -60,  (-30,  -30)
		peak_offset.put(HullSize.DESTROYER, 50f);		// Typ 300, -100, (-50,  -50)
		peak_offset.put(HullSize.CRUISER, 80f);			// Typ 480, -160, (-80,  -80)
		peak_offset.put(HullSize.CAPITAL_SHIP, 120f);	// Typ 720, -240, (-120, -120)
	}
	
	public void applyEffectsBeforeShipCreation(HullSize hullSize, MutableShipStatsAPI stats, String id) {		
		stats.getPeakCRDuration().modifyFlat(id, 0.0f - (Float) peak_offset.get(hullSize));
		stats.getPeakCRDuration().modifyMult(id, 1.0f - PEAK_MULT);
	}
	
	public String getDescriptionParam(int index, HullSize hullSize) {
		if (index == 0) return "" + (int) (100*PEAK_MULT) + "%";
		if (index == 1) return "" + ((Float) peak_offset.get(HullSize.FRIGATE)).intValue();
		if (index == 2) return "" + ((Float) peak_offset.get(HullSize.DESTROYER)).intValue();
		if (index == 3) return "" + ((Float) peak_offset.get(HullSize.CRUISER)).intValue();
		if (index == 4) return "" + ((Float) peak_offset.get(HullSize.CAPITAL_SHIP)).intValue();
		return null;
	}
	
	public boolean isApplicableToShip(ShipAPI ship) {
		return ship != null && (ship.getHullSpec().getNoCRLossTime() < 10000 || ship.getHullSpec().getCRLossPerSecond(ship.getMutableStats()) > 0); 
	}

	public String getUnapplicableReason(ShipAPI ship) {
		return "Ship does not suffer from CR degradation";
	}

}
