package data.hullmods;

import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;

//Ship Hullmod
public class OverdrivenFighters extends BaseHullMod {

	public static final float CARGO_MULT = 0.10f;
	public static final float SUPPLY_MULT = 0.10f;
	
	public static final float RANGE_PENALTY_MULT = 0.30f;
	public static final float FLUX_PENALTY_MULT  = 0.10f;
	public static final float SPEED_BONUS_MULT   = 0.30f;
	
	
	public void applyEffectsBeforeShipCreation(HullSize hullSize, MutableShipStatsAPI stats, String id) {
		stats.getCargoMod().modifyMult(id, 1f - CARGO_MULT);
		stats.getSuppliesPerMonth().modifyMult(id, 1f + SUPPLY_MULT);
		
		stats.getFighterWingRange().modifyMult(id, 1f - RANGE_PENALTY_MULT);
	}
	
	public void applyEffectsToFighterSpawnedByShip(ShipAPI fighter, ShipAPI ship, String id) {
		MutableShipStatsAPI fighterStats = fighter.getMutableStats();
		fighterStats.getFluxCapacity().modifyMult(id, 1.0f - FLUX_PENALTY_MULT);
		
		fighterStats.getMaxSpeed().modifyMult(id, 1.0f + SPEED_BONUS_MULT);
		fighterStats.getAcceleration().modifyMult(id, 1.0f + SPEED_BONUS_MULT );
		fighterStats.getDeceleration().modifyMult(id, 1.0f + SPEED_BONUS_MULT );
	}
	
	public boolean isApplicableToShip(ShipAPI ship) {
		int builtIn = ship.getNumFighterBays();
		if (builtIn <= 0) return false;
		return true;
	}
	
	public String getUnapplicableReason(ShipAPI ship) {
		return "Requires for the ship have fighter bays";
	}

	public String getSModDescriptionParam(int index, HullSize hullSize) {
		return null;
	}
		
	public String getDescriptionParam(int index, HullSize hullSize, ShipAPI ship) {
		if (index == 0) return "" + (int) (100*CARGO_MULT) + "%";
		if (index == 1) return "" + (int) (100*SUPPLY_MULT) + "%";
		
		if (index == 2) return "" + (int) (100*RANGE_PENALTY_MULT) + "%";
		if (index == 3) return "" + (int) (100*FLUX_PENALTY_MULT) + "%";
		if (index == 4) return "" + (int) (100*SPEED_BONUS_MULT) + "%";
		return null;
	}
}




