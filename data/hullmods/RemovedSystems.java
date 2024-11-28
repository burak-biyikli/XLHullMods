package data.hullmods;

import java.util.HashMap;
import java.util.Map;

import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;
import com.fs.starfarer.api.impl.campaign.ids.HullMods;
import com.fs.starfarer.api.util.Misc;

public class RemovedSystems extends BaseHullMod {

	public void applyEffectsBeforeShipCreation(HullSize hullSize, MutableShipStatsAPI stats, String id) {
		stats.getSystemCooldownBonus().modifyMult(id, 0.0f );
		stats.getSystemRegenBonus().modifyMult(id, 0.0f );
		stats.getSystemUsesBonus().modifyFlat(id, -100.0f);
		stats.getSystemRangeBonus().modifyMult(id, 0.0f );
	}
	
	@Override
	public boolean isApplicableToShip(ShipAPI ship) {	
		if (ship.getVariant().hasHullMod("strippedsystems")) return false;
		return !isSMod(ship);	//Does not work as intended
	}

	public String getUnapplicableReason(ShipAPI ship) {
		if (ship.getVariant().hasHullMod("strippedsystems")) return "Cannot be used with stripped systems";
		return "Cannot be an smod";
	}
}
