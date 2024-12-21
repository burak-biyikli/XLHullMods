package data.hullmods;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;
import com.fs.starfarer.api.impl.campaign.ids.HullMods; //Allows us to check if civgrade

public class ReorientedThrusters extends BaseHullMod {

	private static Map speed = new HashMap();
	static {
		speed.put(HullSize.FRIGATE, 25f);  		//Slowest Combat 90 ->  22.5 -> 25
		speed.put(HullSize.DESTROYER, 15f); 	//Slowest Combat 55 -> 13.75 -> 15
		speed.put(HullSize.CRUISER, 10f); 		//Slowest Combat 30 ->  7.50 -> 10
		speed.put(HullSize.CAPITAL_SHIP, 5f); 	//Slowest Combat 23 ->  5.75 ->  5
	}

	public static float MANEUVER_BONUS = 150f;

	public void applyEffectsBeforeShipCreation(HullSize hullSize, MutableShipStatsAPI stats, String id) {
		stats.getMaxSpeed().modifyFlat(id, 0.0f - (Float) speed.get(hullSize));
		stats.getZeroFluxSpeedBoost().modifyFlat(id, (Float) speed.get(hullSize));
		stats.getAcceleration().modifyFlat(id, 0.0f - (Float) speed.get(hullSize) ); //stats.getAcceleration().modifyPercent(id, MANEUVER_BONUS * 2f);
		stats.getDeceleration().modifyFlat(id, 0.0f - (Float) speed.get(hullSize) ); //stats.getDeceleration().modifyPercent(id, MANEUVER_BONUS);

		stats.getTurnAcceleration().modifyPercent(id, MANEUVER_BONUS * 3f);
		stats.getMaxTurnRate().modifyPercent(id, MANEUVER_BONUS);
	}

	public String getSModDescriptionParam(int index, HullSize hullSize) {
		return null;
	}

	public String getDescriptionParam(int index, HullSize hullSize) {
		if (index == 0) return "" + ((Float) speed.get(HullSize.FRIGATE)).intValue();
		if (index == 1) return "" + ((Float) speed.get(HullSize.DESTROYER)).intValue();
		if (index == 2) return "" + ((Float) speed.get(HullSize.CRUISER)).intValue();
		if (index == 3) return "" + ((Float) speed.get(HullSize.CAPITAL_SHIP)).intValue();
		if (index == 4) return "" + (int) MANEUVER_BONUS + "%";
		return null;
	}

	@Override
	public boolean isApplicableToShip(ShipAPI ship) {
		if (ship.getVariant().hasHullMod(HullMods.CIVGRADE) && !ship.getVariant().hasHullMod(HullMods.MILITARIZED_SUBSYSTEMS)) return false;
		return true;
	}

	public String getUnapplicableReason(ShipAPI ship) {
		if (ship.getVariant().hasHullMod(HullMods.CIVGRADE) && !ship.getVariant().hasHullMod(HullMods.MILITARIZED_SUBSYSTEMS)) {
			return "Can not be installed on civilian ships";
		}
		return null;
	}

}
