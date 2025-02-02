package data.hullmods;

import java.util.HashMap;
import java.util.Map;

import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;
import com.fs.starfarer.api.impl.campaign.ids.Stats;

public class UndersizedDriveField extends BaseHullMod {

	public static Map burnPenalty = new HashMap(); 
	static {
		burnPenalty.put(HullSize.FRIGATE, 3f);  	//Normally 	9-10
		burnPenalty.put(HullSize.DESTROYER, 2f); 	//			8-9
		burnPenalty.put(HullSize.CRUISER, 2f);		//			7-9
		burnPenalty.put(HullSize.CAPITAL_SHIP, 1f);	//			7-8
	}
	//Normally augmented drive costs 8/16/24/40 for 2. Undoes this and is worth about 12/16/24/20
	
	public static float CORONA_EFFECT_PENALTY = 0.33f;  //Undoes solar shielding worth about 3/6/9/15
	
	//Total 'value' of 15/22/33/35
	//Haircut value of 10/14.33/22/22.66
	
	public void applyEffectsBeforeShipCreation(HullSize hullSize, MutableShipStatsAPI stats, String id) {

		stats.getMaxBurnLevel().modifyFlat(id, 0.0f - ((Float) burnPenalty.get(hullSize)) );		
		stats.getDynamic().getStat(Stats.CORONA_EFFECT_MULT).modifyMult(id, 1.0f + CORONA_EFFECT_PENALTY);
		
	}
	
	
	public String getDescriptionParam(int index, HullSize hullSize) {
		if (index == 0) return "" + ((Float) burnPenalty.get(HullSize.FRIGATE)).intValue();
		if (index == 1) return "" + ((Float) burnPenalty.get(HullSize.DESTROYER)).intValue();
		if (index == 2) return "" + ((Float) burnPenalty.get(HullSize.CRUISER)).intValue();
		if (index == 3) return "" + ((Float) burnPenalty.get(HullSize.CAPITAL_SHIP)).intValue();
		if (index == 4) return "" + (int) (CORONA_EFFECT_PENALTY*100.0f) + "%";
		return null;
	}
}




