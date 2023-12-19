package data.hullmods;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;
import com.fs.starfarer.api.impl.campaign.ids.HullMods; //Allows us to check if civgrade
//import com.fs.starfarer.api.util.Misc;

//Modded Hullmod
//Takes inspiration from aux thrust and Safty overides

public class AugmentedThrusters extends BaseHullMod {

	private static Map speed = new HashMap();
	static {
		speed.put(HullSize.FRIGATE, 50f);
		speed.put(HullSize.DESTROYER, 30f);
		speed.put(HullSize.CRUISER, 20f);
		speed.put(HullSize.CAPITAL_SHIP, 10f);
	}

	public static float MANEUVER_BONUS = 10f;

	public static Map magBonus = new HashMap();
	static {
		magBonus.put(HullSize.FRIGATE, 10f);
		magBonus.put(HullSize.DESTROYER, 20f);
		magBonus.put(HullSize.CRUISER, 30f);
		magBonus.put(HullSize.CAPITAL_SHIP, 50f);
	}

	public void applyEffectsBeforeShipCreation(HullSize hullSize, MutableShipStatsAPI stats, String id) {
		stats.getMaxSpeed().modifyFlat(id, (Float) speed.get(hullSize));
		stats.getAcceleration().modifyFlat(id, (Float) speed.get(hullSize) * 2f); //stats.getAcceleration().modifyPercent(id, MANEUVER_BONUS * 2f);
		stats.getDeceleration().modifyFlat(id, (Float) speed.get(hullSize) * 2f); //stats.getDeceleration().modifyPercent(id, MANEUVER_BONUS);

		stats.getTurnAcceleration().modifyPercent(id, MANEUVER_BONUS * 2f);
		stats.getMaxTurnRate().modifyPercent(id, MANEUVER_BONUS);

		if( isSMod(stats) ) {
			float dissipation = (Float) magBonus.get(hullSize);
			stats.getFluxDissipation().modifyFlat(id, dissipation);
		}
	}

	public String getSModDescriptionParam(int index, HullSize hullSize) {
		if (index == 0) return "" + ((Float) magBonus.get(HullSize.FRIGATE)).intValue();
		if (index == 1) return "" + ((Float) magBonus.get(HullSize.DESTROYER)).intValue();
		if (index == 2) return "" + ((Float) magBonus.get(HullSize.CRUISER)).intValue();
		if (index == 3) return "" + ((Float) magBonus.get(HullSize.CAPITAL_SHIP)).intValue();
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

	private Color color = new Color(255,100,255,255);
	@Override
	public void advanceInCombat(ShipAPI ship, float amount) {
		ship.getEngineController().fadeToOtherColor(this, color, null, 1f, 0.4f);
		ship.getEngineController().extendFlame(this, 0.25f, 0.25f, 0.25f);
	}

}