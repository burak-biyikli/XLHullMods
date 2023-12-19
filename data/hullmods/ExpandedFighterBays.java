package data.hullmods;

import java.util.HashMap;
import java.util.Map;

import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;

//Modded Hullmod
//Based on converted fighter bay and Converted Cargo Bay

public class ExpandedFighterBays extends BaseHullMod {

	public static int CREW_REQ_PER_BAY = 20;
	public static int CARGO_PER_BAY = 50;
	public static float MAINT_PER_BAY = 15f;

	private static Map SpeedPenalty = new HashMap();
	static {
		SpeedPenalty.put(HullSize.FRIGATE, 30f);
		SpeedPenalty.put(HullSize.DESTROYER, 20f);
		SpeedPenalty.put(HullSize.CRUISER, 10f);
		SpeedPenalty.put(HullSize.CAPITAL_SHIP, 5f);
	}

	public void applyEffectsBeforeShipCreation(HullSize hullSize, MutableShipStatsAPI stats, String id) {
		int builtIn = (int)(stats.getNumFighterBays().getBaseValue());
		int bays = (builtIn + 1)/2;
		int crewcost = CREW_REQ_PER_BAY * bays;
		int cargocost = CARGO_PER_BAY * bays;
		float supplycost = (MAINT_PER_BAY * bays)/100;


		stats.getNumFighterBays().modifyFlat(id, bays);
		stats.getMinCrewMod().modifyPercent(id, crewcost);

		if (isSMod(stats)) {
			stats.getCargoMod().modifyFlat(id, -(cargocost/2));
		} else {
			stats.getCargoMod().modifyFlat(id, -cargocost);
			stats.getSuppliesPerMonth().modifyMult(id, 1f + supplycost);
		}

		stats.getMaxSpeed().modifyFlat(id, -((Float) SpeedPenalty.get(hullSize)));
	}

	public boolean isApplicableToShip(ShipAPI ship) {
		int builtIn = ship.getNumFighterBays();
		if (builtIn <= 0) return false;
		return true;
	}
	
	public String getUnapplicableReason(ShipAPI ship) {
		return "Requires for the ship to already have fighter bays";
	}

	public String getSModDescriptionParam(int index, HullSize hullSize) {
		return null;
	}
		
	public String getDescriptionParam(int index, HullSize hullSize, ShipAPI ship) {
		if (index == 0) return "" + CREW_REQ_PER_BAY;
		if (index == 1) return "" + CARGO_PER_BAY;
		if (index == 2) return "" + ((int)MAINT_PER_BAY) + "%";
		if (index == 3) return "" + ((Float) SpeedPenalty.get(HullSize.FRIGATE)).intValue();
		if (index == 4) return "" + ((Float) SpeedPenalty.get(HullSize.DESTROYER)).intValue();
		if (index == 5) return "" + ((Float) SpeedPenalty.get(HullSize.CRUISER)).intValue();
		if (index == 6) return "" + ((Float) SpeedPenalty.get(HullSize.CAPITAL_SHIP)).intValue();
		return null;
	}
	
}



