package data.hullmods;

import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.CombatEntityAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;
import com.fs.starfarer.api.impl.campaign.ids.Stats;
import com.fs.starfarer.api.impl.hullmods.Automated;

public class MakeshiftAutomatedCommand extends BaseHullMod {

	private static final float PENALTY_MULT = 0.15f;
	
	public void applyEffectsBeforeShipCreation(HullSize hullSize, MutableShipStatsAPI stats, String id) {
		
		stats.getFluxCapacity().modifyMult(id, (1f - PENALTY_MULT) );
		stats.getFluxDissipation().modifyMult(id, (1f - PENALTY_MULT) );
		
		System.out.println( "\n.\n.\n.\n.\n.\n.\n.\n.\n.\n." );
		System.out.println( stats.getVariant().getTags() );
		
		CombatEntityAPI cship = stats.getEntity();
		if (cship instanceof ShipAPI) {
            ShipAPI sship = (ShipAPI) cship;
            System.out.println( sship.getTags() );
        }
		
		System.out.println( "\n.\n.\n.\n.\n.\n.\n.\n.\n.\n." );
		
		//Does not work
		//new Automated().applyEffectsBeforeShipCreation(hullSize, stats, id);
		
	}
	
	public String getDescriptionParam(int index, HullSize hullSize) {
		if (index == 0) return "" + (int) (100*PENALTY_MULT) + "%";
		return null;
	}
	
	public boolean isApplicableToShip(ShipAPI ship) {
		return true; 
	}

	public String getUnapplicableReason(ShipAPI ship) {
		return null;
	}
	
	/*
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
    }*/


}
