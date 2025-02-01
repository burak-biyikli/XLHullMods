package data.hullmods;

import java.util.HashMap;
import java.util.Map;

import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;
import com.fs.starfarer.api.impl.campaign.ids.Stats;

public class MarineBarracks extends BaseHullMod {

	public static Map magBonus = new HashMap();
	static {
		magBonus.put(HullSize.FRIGATE, 20f);
		magBonus.put(HullSize.DESTROYER, 40f);
		magBonus.put(HullSize.CRUISER, 80f);
		magBonus.put(HullSize.CAPITAL_SHIP, 160f);
	}
	
	public void applyEffectsBeforeShipCreation(HullSize hullSize, MutableShipStatsAPI stats, String id) {
		stats.getDynamic().getMod(Stats.FLEET_GROUND_SUPPORT).modifyFlat(id, (Float) magBonus.get(hullSize) );
		//stats.getDynamic().getMod(Stats.FLEET_BOMBARD_COST_REDUCTION).modifyFlat(id, GROUND_BONUS);
		
		if( isSMod(stats) ) {
			stats.getDynamic().getMod(Stats.FLEET_GROUND_SUPPORT).modifyFlat(id, 2.0f * (Float) magBonus.get(hullSize) );
		}
	}
	
	public String getDescriptionParam(int index, HullSize hullSize) {
		if (index == 0) return "" + ((Float) magBonus.get(HullSize.FRIGATE)).intValue();
		if (index == 1) return "" + ((Float) magBonus.get(HullSize.DESTROYER)).intValue();
		if (index == 2) return "" + ((Float) magBonus.get(HullSize.CRUISER)).intValue();
		if (index == 3) return "" + ((Float) magBonus.get(HullSize.CAPITAL_SHIP)).intValue();
		return null;
	}
	
	public String getSModDescriptionParam(int index, HullSize hullSize) {
		if (index == 0) return "3.0";
		return null;
	}
}




