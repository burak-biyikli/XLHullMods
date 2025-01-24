package data.hullmods;

import java.util.HashMap;
import java.util.Map;

import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;
import com.fs.starfarer.api.impl.campaign.ids.HullMods;
import com.fs.starfarer.api.util.Misc;

public class HybridVents extends BaseHullMod {

	public static final float ARMOR_PENALTY_MULT = 25f;
	private static final float FLUX_DISSIPATION_MULT = 50f;
	
	public void applyEffectsBeforeShipCreation(HullSize hullSize, MutableShipStatsAPI stats, String id) {
		float mult = (1f - ARMOR_PENALTY_MULT * 0.01f);
		stats.getArmorBonus().modifyMult(id, mult);
		stats.getFluxDissipation().modifyMult(id, 1.0f + FLUX_DISSIPATION_MULT * 0.01f);
	}
	
	public String getDescriptionParam(int index, HullSize hullSize) {
		if (index == 0) return "" + (int) FLUX_DISSIPATION_MULT + "%";
		if (index == 1) return "" + (int) ARMOR_PENALTY_MULT + "%";
		return null;
	}

	@Override
	public boolean isApplicableToShip(ShipAPI ship) {
		if(ship.getVariant().hasHullMod(HullMods.CIVGRADE) && !ship.getVariant().hasHullMod(HullMods.MILITARIZED_SUBSYSTEMS)) return false;		
		return true;
	}

	public String getUnapplicableReason(ShipAPI ship) {
		if (ship.getVariant().hasHullMod(HullMods.CIVGRADE) && !ship.getVariant().hasHullMod(HullMods.MILITARIZED_SUBSYSTEMS)) {
			return "Can not be installed on civilian ships";
		}
		return null;
	}


}
