package data.hullmods;

import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;
import com.fs.starfarer.api.impl.campaign.ids.Stats;

public class ShieldCoilIntegration extends BaseHullMod {

	// BaseFlux * (Flux/damage) 
	// .80 * 1 / .7 = 1.14 (14% better shields net)
	// .85 * 1 / .7 = 1.21 (21% better shields net) In line with hardened shields which is standard cost
	// .90 * 1 / .7 = 1.28 (28% better shields net)

	private static final float CAPACITY_PENALTY      = 15f;
	private static final float CAPACITY_PENALTY_SMOD = 10f;
	private static final float SHIELD_BONUS          = 30f;
	
	public void applyEffectsBeforeShipCreation(HullSize hullSize, MutableShipStatsAPI stats, String id) {
	
		float cap_reduction = isSMod(stats) ? CAPACITY_PENALTY_SMOD : CAPACITY_PENALTY;
		float dam_reduction = SHIELD_BONUS;

		stats.getFluxCapacity().modifyMult(id, 1f - cap_reduction * 0.01f);
		stats.getShieldDamageTakenMult().modifyMult(id, 1f - dam_reduction * 0.01f);
		
	}

	public String getSModDescriptionParam(int index, HullSize hullSize) {
		if (index == 0) return "" + (int) (CAPACITY_PENALTY) + "%";
		if (index == 1) return "" + (int) (CAPACITY_PENALTY_SMOD) + "%";
		return null;
	}

	public String getDescriptionParam(int index, HullSize hullSize) {
		if (index == 0) return "" + (int) (CAPACITY_PENALTY) + "%";
		if (index == 1) return "" + (int) (SHIELD_BONUS) + "%";
		return null;
	}

	public boolean isApplicableToShip(ShipAPI ship) {
		return ship != null && ship.getShield() != null;
	}
	
	public String getUnapplicableReason(ShipAPI ship) {
		return "Ship has no shields";
	}
	
}



