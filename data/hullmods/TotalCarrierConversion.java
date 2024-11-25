package data.hullmods;

import java.util.HashMap;
import java.util.Map;

import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;

import com.fs.starfarer.api.combat.ShipHullSpecAPI;
import com.fs.starfarer.api.combat.ShipVariantAPI;


public class TotalCarrierConversion extends BaseHullMod {

	public static float CARGO_REQ_PER_BAY = 50.0f;
	public static float CREW_REQ_PER_BAY = 20.0f;
	public static float SUPPLIES_REQ_PER_BAY = 1.5f;

	private static Map SpeedPenalty = new HashMap();
	static {
		SpeedPenalty.put(HullSize.FRIGATE, 20f);
		SpeedPenalty.put(HullSize.DESTROYER, 15f);
		SpeedPenalty.put(HullSize.CRUISER, 10f);
		SpeedPenalty.put(HullSize.CAPITAL_SHIP, 5f);
	}

	public void applyEffectsBeforeShipCreation(HullSize hullSize, MutableShipStatsAPI stats, String id) {

		float BaseCargoCap = stats.getVariant().getHullSpec().getCargo();
		float bays = (float)((int)((BaseCargoCap/CARGO_REQ_PER_BAY)));		
		
		float cargocost    = CARGO_REQ_PER_BAY * bays;
		float crewcost     = CREW_REQ_PER_BAY * bays;
		float supplycost   = SUPPLIES_REQ_PER_BAY * bays;

		stats.getNumFighterBays().modifyFlat(id, bays);
		
		stats.getCargoMod().modifyFlat(id, -cargocost);
		stats.getMinCrewMod().modifyFlat(id, crewcost);
		stats.getSuppliesPerMonth().modifyFlat(id, supplycost);
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
		if (index == 0) return "" + ((Float) CARGO_REQ_PER_BAY).intValue();
		if (index == 1) return "" + ((Float) CREW_REQ_PER_BAY).intValue();
		if (index == 2) return "" + SUPPLIES_REQ_PER_BAY;
		if (index == 3) return "" + ((Float) SpeedPenalty.get(HullSize.FRIGATE)).intValue();
		if (index == 4) return "" + ((Float) SpeedPenalty.get(HullSize.DESTROYER)).intValue();
		if (index == 5) return "" + ((Float) SpeedPenalty.get(HullSize.CRUISER)).intValue();
		if (index == 6) return "" + ((Float) SpeedPenalty.get(HullSize.CAPITAL_SHIP)).intValue();
		return null;
	}
	
}



