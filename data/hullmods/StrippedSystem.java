package data.hullmods;

import java.util.HashMap;
import java.util.Map;

import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;
import com.fs.starfarer.api.impl.campaign.ids.HullMods;
import com.fs.starfarer.api.util.Misc;

public class StrippedSystem extends BaseHullMod {

	public static final float PENALTY_MULT = 0.30f;
	public static final float PENALTY_INC  = 3.0f;
	
	public void applyEffectsBeforeShipCreation(HullSize hullSize, MutableShipStatsAPI stats, String id) {
	
		stats.getSystemCooldownBonus().modifyMult(id, 1 - PENALTY_MULT);
		stats.getSystemRegenBonus().modifyMult(id, 1 - PENALTY_MULT);
		stats.getSystemUsesBonus().modifyFlat(id, -PENALTY_INC);
		stats.getSystemRangeBonus().modifyMult(id, 1 - PENALTY_MULT);
		
	}
	
	public String getDescriptionParam(int index, HullSize hullSize) {
		if (index == 0) return "" + (int) (100*PENALTY_MULT) + "%";
		if (index == 1) return "" + (int) (100*PENALTY_MULT) + "%";
		if (index == 2) return "" + (int) (100*PENALTY_INC) + "%";
		if (index == 3) return "" + (int) (100*PENALTY_MULT) + "%";
		return null;
	}

	@Override
	public boolean isApplicableToShip(ShipAPI ship) {	
		return true;
	}

	public String getUnapplicableReason(ShipAPI ship) {
		return null;
	}


}
