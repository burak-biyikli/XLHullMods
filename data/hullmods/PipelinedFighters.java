package data.hullmods;

import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;
import com.fs.starfarer.api.impl.campaign.ids.Stats;

public class PipelinedFighters extends BaseHullMod {

	public static float REPLACEMENT_RATE_PENALTY_MODIFIER = 75f; //33% would be 'fair' since if originally dead for 4s now only 3s, so each second needs to impact a third more
	public static float REFIT_RATE_BONUS = 25f;
	
	
	public void applyEffectsBeforeShipCreation(HullSize hullSize, MutableShipStatsAPI stats, String id) {
		stats.getDynamic().getStat(Stats.REPLACEMENT_RATE_DECREASE_MULT).modifyMult(id, 1f + REPLACEMENT_RATE_PENALTY_MODIFIER / 100f);
		stats.getDynamic().getStat(Stats.REPLACEMENT_RATE_INCREASE_MULT).modifyMult(id, 1f - REPLACEMENT_RATE_PENALTY_MODIFIER / 100f);
		
		stats.getFighterRefitTimeMult().modifyMult(id, 1f - REFIT_RATE_BONUS / 100f);
	}
		
	public String getDescriptionParam(int index, HullSize hullSize) {
		if (index == 0) return "" + (int) REPLACEMENT_RATE_PENALTY_MODIFIER + "%";
		if (index == 1) return "" + (int) REFIT_RATE_BONUS + "%";
		return null;
	}
	
	public boolean isApplicableToShip(ShipAPI ship) {
		int baysModified = (int) ship.getMutableStats().getNumFighterBays().getModifiedValue();
		if (baysModified <= 0) return false; // only count removed bays, not added bays for this
		
		int bays = (int) ship.getMutableStats().getNumFighterBays().getBaseValue();
//		if (ship != null && ship.getVariant().getHullSpec().getBuiltInWings().size() >= bays) {
//			return false;
//		}
		return ship != null && bays > 0; 
	}
	
	public String getUnapplicableReason(ShipAPI ship) {
		return "Ship does not have standard fighter bays";
	}
	
}




