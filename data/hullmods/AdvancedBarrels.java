package data.hullmods;

import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;
import com.fs.starfarer.api.impl.campaign.ids.HullMods;

public class AdvancedBarrels extends BaseHullMod {

	public static final float BALLISTIC_RANGE_BONUS = 200f;
	public static final float BALLISTIC_SPEED_BONUS = 25f;
	public static final float BALLISTIC_FLUX_PENALTY = 10f;
	public static final float BALLISTIC_TURN_PENALTY = 10f;
	
	public void applyEffectsBeforeShipCreation(HullSize hullSize, MutableShipStatsAPI stats, String id) {
		stats.getBallisticWeaponRangeBonus().modifyFlat(id, BALLISTIC_RANGE_BONUS);
		stats.getBallisticProjectileSpeedMult().modifyMult(id, 1f + BALLISTIC_SPEED_BONUS * 0.01f);
		stats.getBallisticWeaponFluxCostMod().modifyMult(id, 1f + BALLISTIC_FLUX_PENALTY * 0.01f);
		stats.getWeaponTurnRateBonus().modifyPercent(id, -BALLISTIC_TURN_PENALTY);
	}
	
	public String getDescriptionParam(int index, HullSize hullSize) {
		if (index == 0) return "" + (int) BALLISTIC_RANGE_BONUS;
		if (index == 1) return "" + (int) BALLISTIC_SPEED_BONUS + "%";
		if (index == 2) return "" + (int) BALLISTIC_FLUX_PENALTY + "%";
		if (index == 3) return "" + (int) BALLISTIC_TURN_PENALTY + "%";
		return null;
	}
}
