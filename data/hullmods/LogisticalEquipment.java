package data.hullmods;

import java.util.HashMap;
import java.util.Map;

import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;
import com.fs.starfarer.api.impl.campaign.ids.HullMods;
import com.fs.starfarer.api.util.Misc;

public class LogisticalEquipment extends BaseHullMod {


	// Game is balanced to roughly 6 OP per DP
	// This will be slightly inefficent relative on purpose
	
	private static Map dp_bonus = new HashMap();
	static {
		dp_bonus.put(HullSize.FRIGATE, 		1.0f); // 7 OP
		dp_bonus.put(HullSize.DESTROYER, 	2.0f); //14 OP
		dp_bonus.put(HullSize.CRUISER, 		3.0f); //21 OP
		dp_bonus.put(HullSize.CAPITAL_SHIP, 4.0f); //28 OP
	}

	//Local Helper function	
	public float computeCRMult(float suppliesPerDep, float dpMod) {
		return 1.0F + dpMod / suppliesPerDep;
	}
	
	public void applyEffectsBeforeShipCreation(HullSize hullSize, MutableShipStatsAPI stats, String id) {
	
		float dpMod = (Float) dp_bonus.get(hullSize);
		
		stats.getDynamic().getMod("deployment_points_mod").modifyFlat(id, -dpMod);
		
		if (stats.getFleetMember() != null) {
		   float perDep = stats.getFleetMember().getHullSpec().getSuppliesToRecover();
		   float mult = this.computeCRMult(perDep, dpMod);
		   //stats.getCRPerDeploymentPercent().modifyMult(id, mult);
		}
		
		stats.getSuppliesToRecover().modifyFlat(id, dpMod);
	}
	
	public String getDescriptionParam(int index, HullSize hullSize) {
		if (index == 0) return "" + ((Float) dp_bonus.get(HullSize.FRIGATE)).intValue();
		if (index == 1) return "" + ((Float) dp_bonus.get(HullSize.DESTROYER)).intValue();
		if (index == 2) return "" + ((Float) dp_bonus.get(HullSize.CRUISER)).intValue();
		if (index == 3) return "" + ((Float) dp_bonus.get(HullSize.CAPITAL_SHIP)).intValue();
		return null;
	}

	
	@Override
	public boolean isApplicableToShip(ShipAPI ship) {	
		return true;
	}

	public String getUnapplicableReason(ShipAPI ship) {
		return null;
	}
}

