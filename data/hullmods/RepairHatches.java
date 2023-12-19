package data.hullmods;

import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;

public class RepairHatches extends BaseHullMod {

	public static final float ARMOR_PENALTY_MULT = 20f;
	public static final float REPAIR_RATE = 2f;
	public static final float REPAIR_MAX = 60f;

	
	public void applyEffectsBeforeShipCreation(HullSize hullSize, MutableShipStatsAPI stats, String id) {
		stats.getHullCombatRepairRatePercentPerSecond().modifyFlat(id, REPAIR_RATE * 0.01f);
		stats.getMaxCombatHullRepairFraction().modifyFlat(id, REPAIR_MAX * 0.01f); 
		float mult = (1f - ARMOR_PENALTY_MULT * 0.01f);
		stats.getArmorBonus().modifyMult(id, mult);
	}
	
	public String getDescriptionParam(int index, HullSize hullSize, ShipAPI ship) {
		if (index == 0) return "" + (int) ARMOR_PENALTY_MULT + "%";
		if (index == 1) return "" + (int) REPAIR_RATE + "%";
		if (index == 2) return "" + (int) REPAIR_MAX + "%";
		return null;
	}
}
