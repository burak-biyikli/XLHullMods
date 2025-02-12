package data.hullmods;

import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;

import com.fs.starfarer.api.combat.FighterLaunchBayAPI;
import com.fs.starfarer.api.combat.FighterWingAPI;
import com.fs.starfarer.api.loading.FighterWingSpecAPI;
import com.fs.starfarer.api.combat.ShipVariantAPI;
import com.fs.starfarer.api.combat.ShipHullSpecAPI; 

import com.fs.starfarer.api.Global;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

public class UnmannedHangar extends BaseHullMod {

	public static float CR_THRESHOLD_UNINSTALLABLE = 70.0F;
	
	public static Map bayBonus = new HashMap();
	static {
		bayBonus.put(HullSize.FRIGATE, 1f);
		bayBonus.put(HullSize.DESTROYER, 1f);
		bayBonus.put(HullSize.CRUISER, 2f);
		bayBonus.put(HullSize.CAPITAL_SHIP, 2f);
	}
  
	public void applyEffectsBeforeShipCreation(ShipAPI.HullSize hullSize, MutableShipStatsAPI stats, String id) {
		if (stats.getVariant().hasHullMod("converted_bay"))
			return;
			 
		float numBays = (Float) bayBonus.get(hullSize);
		numBays += stats.getDynamic().getMod("converted_hangar_mod").computeEffective(0.0F);
		stats.getNumFighterBays().modifyFlat(id, numBays);
		 
		boolean crewIncrease = (stats.getDynamic().getMod("converted_hangar_no_crew_increase").computeEffective(0.0F) <= 0.0F);
		boolean rearmIncrease = (stats.getDynamic().getMod("converted_hangar_no_rearm_increase").computeEffective(0.0F) <= 0.0F);
		boolean dpIncrease = (stats.getDynamic().getMod("converted_hangar_no_dp_increase").computeEffective(0.0F) <= 0.0F);
		boolean refitPenalty = (stats.getDynamic().getMod("converted_hangar_no_refit_penalty").computeEffective(0.0F) <= 0.0F);
	}

	public boolean isApplicableToShip(ShipAPI ship) {
		//System.out.println( "" );
	 	//System.out.println( ship.getVariant().getFittedWings() ); //Seems similar to ship.getVariant().getWings()
	 	//System.out.println( ship.getVariant().getNonBuiltInWings() );
	 	//System.out.println( ship.getHullSpec().getFighterBays() );
		if (ship != null && ship.getHullSpec().getCRToDeploy() > CR_THRESHOLD_UNINSTALLABLE)
      		return false; 
		if (ship != null && ship.getVariant().hasHullMod("converted_bay") )
        	return false;  
        if (ship != null && ship.getHullSpec().getFighterBays() > 0){
        	if ( ship.getVariant().getNonBuiltInWings().size() == 0 && ship.getVariant().getFittedWings().size() != 0) return true;
        	return false;
        }
        if (ship != null && ship.getHullSpec().isPhase())
        	return false;
        if (ship != null && ship.getVariant().hasHullMod("converted_hangar")) 
        	return false;
    	return true;
	}
	
	public String getUnapplicableReason(ShipAPI ship) {
		if (ship != null && ship.getHullSpec().getCRToDeploy() > CR_THRESHOLD_UNINSTALLABLE)
		  return "Ship's combat readiness lost per deployment is too high";  
		if (ship != null && ship.getVariant().hasHullMod("converted_bay"))
		  return "Ship already had fighter bays"; 		
		if (ship != null && ship.getHullSpec().getFighterBays() > 0){
        	if ( ship.getVariant().getNonBuiltInWings().size() == 0 && ship.getVariant().getFittedWings().size() != 0) return null;
        	return "Ship already has configureable fighter bays";
        }
		if (ship != null && ship.getHullSpec().isPhase())
        	return "Can not be installed on a phase ship";
        if (ship != null && ship.getVariant().hasHullMod("converted_hangar")) 
        	return "Won't add additional bays to a ship that already has converted hangar";
    	return null;
	}

	public String getSModDescriptionParam(int index, HullSize hullSize) {
		return null;
	}
		
	public String getDescriptionParam(int index, HullSize hullSize, ShipAPI ship) {
		if (index == 0) return "" + ((Float) bayBonus.get(HullSize.FRIGATE)).intValue();
		if (index == 1) return "" + ((Float) bayBonus.get(HullSize.DESTROYER)).intValue();
		if (index == 2) return "" + ((Float) bayBonus.get(HullSize.CRUISER)).intValue();
		if (index == 3) return "" + ((Float) bayBonus.get(HullSize.CAPITAL_SHIP)).intValue();
		return null;
	}
    
    @Override
    public void advanceInCombat(ShipAPI ship, float amount) {
        
        if(ship.getOriginalOwner()==-1){
            return; //supress in refit
        }
        
        
        for(FighterLaunchBayAPI bay : ship.getLaunchBaysCopy()){
            if(bay.getWing()!=null){
            	//System.out.println( bay.getWing().getSpec().getTags() );
            	//System.out.println( bay.getWing().getSpec().getVariant().getHullSpec().getMinCrew() );
            	
            	if( bay.getWing().getSpec().getVariant().getHullSpec().getMinCrew() > 0 ){
            		bay.setCurrRate(0.30F);
            		bay.setFastReplacements(0);
            	}
            }
        }
        return;
    }
}




