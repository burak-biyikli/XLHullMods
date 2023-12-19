package data.hullmods;

import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;
import com.fs.starfarer.api.impl.campaign.ids.Stats;

public class ConcentratedShields extends BaseHullMod {

	public static final float SHIELD_ARC   = 40f;
	public static final float SHIELD_BONUS = 10f;
	public static float PIERCE_MULT = 0.75f;
	
	public void applyEffectsBeforeShipCreation(HullSize hullSize, MutableShipStatsAPI stats, String id) {
		float multiplier = isSMod(stats) ? 2 : 1;
		float arc_reduction = SHIELD_ARC * multiplier;
		float dam_reduction = SHIELD_BONUS * multiplier;

		stats.getShieldArcBonus().modifyFlat(id, -arc_reduction);
		stats.getShieldDamageTakenMult().modifyMult(id, 1f - dam_reduction * 0.01f);
		stats.getDynamic().getStat(Stats.SHIELD_PIERCED_MULT).modifyMult(id, PIERCE_MULT);	
	}

	public String getSModDescriptionParam(int index, HullSize hullSize) {
		if (index == 0) return "" + (int) (SHIELD_BONUS*2) + "%";
		if (index == 1) return "" + (int) (SHIELD_ARC*2);
		return null;
	}

	public String getDescriptionParam(int index, HullSize hullSize) {
		if (index == 0) return "" + (int) SHIELD_BONUS + "%";
		if (index == 1) return "" + (int) SHIELD_ARC;
		return null;
	}

	public boolean isApplicableToShip(ShipAPI ship) {
		return ship != null && ship.getShield() != null;
	}
	
	public String getUnapplicableReason(ShipAPI ship) {
		return "Ship has no shields";
	}
	
}



