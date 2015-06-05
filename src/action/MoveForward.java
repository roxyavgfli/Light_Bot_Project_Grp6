package action;
import map.*;
import robot.*;
import exception.MouvementEx;



public class MoveForward implements int_Action{
	
	public static void execute(Robot r) throws MouvementEx{
		abstr_Case c_prime = null;
		Coordonnees pos = r.getCurrent_Case().get_coordonnees();
		
		switch (r.getOrientation()) {  
	    	case  TOP :
	    		c_prime = World.currentWorld.get_terrain(pos.get_n()).get_case(pos.get_x(),pos.get_y()-1);
	    	case  BOT :
	    		c_prime = World.currentWorld.get_terrain(pos.get_n()).get_case(pos.get_x(),pos.get_y()+1);;
	    	case  LEFT :
	    		c_prime = World.currentWorld.get_terrain(pos.get_n()).get_case(pos.get_x()-1,pos.get_y());
	    	case  RIGHT :
	    		c_prime = World.currentWorld.get_terrain(pos.get_n()).get_case(pos.get_x()+1,pos.get_y());
		}
		
		if (! isPossible(r,c_prime)){
			throw (new MouvementEx("impossible d'avancer"));
		} else {
			r.setCurrent_Case(c_prime);
		}
		
	}
	
	public static boolean isPossible(Robot r, abstr_Case c){
		return (r.getCurrent_Case().get_hauteur() == c.get_hauteur());

	}
}
