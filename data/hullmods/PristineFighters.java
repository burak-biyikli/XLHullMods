package data.hullmods;

import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;

//Fighter Hullmod
public class PristineFighter extends BaseHullMod {

	public static final float FLUX_BONUS_MULT = 0.15f;
	public static final float ARMOR_BONUS_MULT = 0.10f;
	public static final float HULL_BONUS_MULT = 0.15f;
	public static final float SPEED_BONUS_MULT = 0.10f;
	
	public void applyEffectsBeforeShipCreation(HullSize hullSize, MutableShipStatsAPI stats, String id) {
		stats.getFluxCapacity().modifyMult(id, 1.0f + FLUX_BONUS_MULT);
		//stats.getShieldDamageTakenMult().modifyMult(id, 1.0f - FLUX_BONUS_MULT);
		stats.getArmorDamageTakenMult().modifyMult(id, 1.0f - ARMOR_BONUS_MULT);
		stats.getHullDamageTakenMult().modifyMult(id, 1.0f - HULL_BONUS_MULT);
		stats.getMaxSpeed().modifyMult(id, 1.0f + SPEED_BONUS_MULT);
	}
	
	public void applyEffectsToFighterSpawnedByShip(ShipAPI fighter, ShipAPI ship, String id){
		/* Example from DefectiveManufactory
		public static float SPEED_REDUCTION = 0.25F;
		public static float DAMAGE_INCREASE = 0.25F;
		
		float effect = ship.getMutableStats().getDynamic().getValue("dmod_effect_mult");
		MutableShipStatsAPI stats = fighter.getMutableStats();
		stats.getMaxSpeed().modifyMult(id, 1.0F - SPEED_REDUCTION * effect);
		stats.getArmorDamageTakenMult().modifyPercent(id, DAMAGE_INCREASE * 100.0F * effect);
		stats.getShieldDamageTakenMult().modifyPercent(id, DAMAGE_INCREASE * 100.0F * effect);
		stats.getHullDamageTakenMult().modifyPercent(id, DAMAGE_INCREASE * 100.0F * effect);
		fighter.setLightDHullOverlay();
		*/

		applyEffectsBeforeShipCreation(fighter.getHullSize(), fighter.getMutableStats(), id);
	}
	
	public String getDescriptionParam(int index, HullSize hullSize, ShipAPI ship) {
		if (index == 0) return "" + (int) (100*FLUX_BONUS_MULT) + "%";
		if (index == 1) return "" + (int) (100*ARMOR_BONUS_MULT) + "%";
		if (index == 2) return "" + (int) (100*HULL_BONUS_MULT) + "%";
		if (index == 3) return "" + (int) (100*SPEED_BONUS_MULT) + "%";
		return null;
	}

	public boolean isApplicableToShip(ShipAPI ship) {
		return ship.isFighter();
	}
	
	public String getUnapplicableReason(ShipAPI ship) {
		return "Can only be added to fighters, not ships";
	}
}

//Ship Hullmod
public class PristineFighters extends BaseHullMod {

	public static final float CARGO_MULT = 0.10f;
	public static final float CREW_MULT = 0.10f;
	public static final float SUPPLY_MULT = 0.10f;
	
	
	public void applyEffectsBeforeShipCreation(HullSize hullSize, MutableShipStatsAPI stats, String id) {
		stats.getCargoMod().modifyMult(id, 1f - CARGO_MULT);
		stats.getMinCrewMod().modifyMult(id, 1f + CREW_MULT);
		stats.getSuppliesPerMonth().modifyMult(id, 1f + SUPPLY_MULT);;
	}
	
	public void applyEffectsToFighterSpawnedByShip(ShipAPI fighter, ShipAPI ship, String id) {
		new PristineFighter().applyEffectsToFighterSpawnedByShip(fighter, ship, id);
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
		if (index == 1) return "" + (int) (100*CREW_MULT) + "%";
		if (index == 2) return "" + (int) (100*SUPPLY_MULT) + "%";
		return null;
	}
}




