package data.hullmods;

import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;
import com.fs.starfarer.api.impl.campaign.ids.Stats;
import com.fs.starfarer.api.impl.hullmods.CompromisedStructure;

public class UndersizedPowerGrid extends BaseHullMod {

	private static final float PENALTY_MULT = 0.15f;
	
	public void applyEffectsBeforeShipCreation(HullSize hullSize, MutableShipStatsAPI stats, String id) {
		
		stats.getFluxCapacity().modifyMult(id, (1f - PENALTY_MULT) );
		stats.getFluxDissipation().modifyMult(id, (1f - PENALTY_MULT) );
		
	}
	
	public String getDescriptionParam(int index, HullSize hullSize) {
		if (index == 0) return "" + (int) (100*PENALTY_MULT) + "%";
		return null;
	}
	
	public boolean isApplicableToShip(ShipAPI ship) {
		return true; 
	}

	public String getUnapplicableReason(ShipAPI ship) {
		return null;
	}


}
