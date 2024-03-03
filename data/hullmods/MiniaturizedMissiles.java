package data.hullmods;

import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;
import com.fs.starfarer.api.impl.campaign.ids.Stats;
import com.fs.starfarer.api.impl.hullmods.CompromisedStructure;

public class MiniaturizedMissiles extends BaseHullMod {

	public static final float MISSILE_FIRE_RATE = 3.0f;
	public static final float MISSILE_COUNT     = 3.0f;

	public static final float MISSILE_HP        = 0.25f;
	public static final float MISSILE_DAMAGE    = 0.40f;
	
	public void applyEffectsBeforeShipCreation(HullSize hullSize, MutableShipStatsAPI stats, String id) {
		stats.getMissileAmmoBonus().modifyMult(id, MISSILE_COUNT);
		stats.getMissileRoFMult().modifyMult(id, MISSILE_FIRE_RATE);

		stats.getMissileHealthBonus().modifyMult(id, MISSILE_HP);
		stats.getMissileWeaponDamageMult().modifyMult(id, MISSILE_DAMAGE);
	}
	
	public String getDescriptionParam(int index, HullSize hullSize, ShipAPI ship) {
		if (index == 0) return "" + (int) (100*MISSILE_FIRE_RATE) + "%";
		if (index == 1) return "" + (int) (100*MISSILE_COUNT) + "%";
		if (index == 2) return "" + (int) (100*MISSILE_HP) + "%";
		if (index == 3) return "" + (int) (100*MISSILE_DAMAGE) + "%";

		return null;
	}
}
