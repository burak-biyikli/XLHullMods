package data.hullmods;

import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;
import com.fs.starfarer.api.impl.campaign.ids.Stats;
import com.fs.starfarer.api.impl.hullmods.CompromisedStructure;

public class WeightReduction extends BaseHullMod {
	public static final float ARMOR_PENALTY_MULT = 0.8f;
	public static final float HULL_PENALTY_MULT = 0.8f;
	
	public void applyEffectsBeforeShipCreation(HullSize hullSize, MutableShipStatsAPI stats, String id) {
		stats.getArmorBonus().modifyMult(id, ARMOR_PENALTY_MULT);
		stats.getHullBonus().modifyMult(id, HULL_PENALTY_MULT);
	}
	
	public String getDescriptionParam(int index, HullSize hullSize, ShipAPI ship) {
		if (index == 0) return "" + (int) (100*ARMOR_PENALTY_MULT) + "%";
		if (index == 1) return "" + (int) (100*HULL_PENALTY_MULT) + "%";

		return null;
	}
}
