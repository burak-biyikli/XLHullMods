package data.hullmods;

import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;
import com.fs.starfarer.api.impl.campaign.ids.Stats;
import com.fs.starfarer.api.impl.hullmods.CompromisedStructure;

public class FluxCoilInsulation extends BaseHullMod {

	
	private static final float CAPACITY_MULT = 1.50f;
	private static final float DISSIPATION_MULT = 0.80f;
	private static final float DISSIPATION_MULT_SMOD = 0.90f;

	//Base Flux Cap:  10 20 30 50
	//Base Flux Disp: 10 20 30 50
	// Mod Flux Cap:  15 30 45 75  (+5 +10 +15 +25)
	// Mod Flux Disp: 10 20 30 50  (-1  -2  -3  -5)
	
	public void applyEffectsBeforeShipCreation(HullSize hullSize, MutableShipStatsAPI stats, String id) {		
		stats.getFluxCapacity().modifyMult(id, CAPACITY_MULT);

		if( isSMod(stats) ) {
			stats.getFluxDissipation().modifyMult(id, DISSIPATION_MULT_SMOD);
		} else {
			stats.getFluxDissipation().modifyMult(id, DISSIPATION_MULT);
		}
	}
	
	public String getDescriptionParam(int index, HullSize hullSize) {
		if (index == 0) return "" + (int) ((100 * CAPACITY_MULT)-100) + "%";
		if (index == 1) return "" + (int) (100 * DISSIPATION_MULT) + "%";
		return null;
	}

	public String getSModDescriptionParam(int index, HullSize hullSize) {
		if (index == 1) return "" + (int) (100 * DISSIPATION_MULT_SMOD) + "%";
		return null;
	}

}
