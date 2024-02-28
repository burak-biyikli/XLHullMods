package data.hullmods;

import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;

public class ExplorationPackage extends BaseHullMod {

	public static final float SUPPLY_USE = 10f;
	public static final float FUEL_USE   =  5f;
	public static final float CARGO_CAP  = 15f;
	public static final float FUEL_CAP   = 25f;

	
	public void applyEffectsBeforeShipCreation(HullSize hullSize, MutableShipStatsAPI stats, String id) {
		stats.getSuppliesPerMonth().modifyMult(id, 1 - SUPPLY_USE * 0.01f);
		stats.getFuelUseMod().modifyMult(id, 1 - FUEL_USE * 0.01f); 
		stats.getCargoMod().modifyMult(id, 1 + CARGO_CAP * 0.01f); 
		stats.getFuelMod().modifyMult(id, 1 + FUEL_CAP * 0.01f); 


	}
	
	public String getDescriptionParam(int index, HullSize hullSize, ShipAPI ship) {
		if (index == 0) return "" + (int) CARGO_CAP + "%";
		if (index == 1) return "" + (int) FUEL_CAP + "%";
		if (index == 2) return "" + (int) SUPPLY_USE + "%";
		if (index == 3) return "" + (int) FUEL_USE + "%";
		return null;
	}
}
