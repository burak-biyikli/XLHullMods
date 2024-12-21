package data.hullmods;

import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;
import com.fs.starfarer.api.impl.campaign.ids.Stats;

public class ShieldCoilIntegration extends BaseHullMod {

	private static final float CAPACITY_PENALTY      = 20f;
	private static final float CAPACITY_PENALTY_SMOD = 15f;
	private static final float SHIELD_BONUS          = 20f;
	
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



