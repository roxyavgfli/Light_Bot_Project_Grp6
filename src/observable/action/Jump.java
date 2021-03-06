package observable.action;
import java.util.ArrayList;

import couleur.Couleur;
import exception.MouvementEx;
import exception.UnreachableCase;
import observable.int_Observable;
import observable.map.Coordonnees;
import observable.map.World;
import observable.map.abstr_Case;
import observable.robot.abstr_Robot;
import observer.int_Observer;

public class Jump implements int_Action, int_Observable{

	private Couleur color;

	@Override
	public Couleur getColor() {
		return this.color;
	}
	@Override
	public void setColor(Couleur color) {
		this.color = color;
	}
	private ArrayList<int_Observer> listObserver = new ArrayList<int_Observer>();

	public static Jump jump(){
		return new Jump();
	}
	public static Jump jump(Couleur col){
		return new Jump(col);
	}
	private Jump(Couleur col){
		this.color = col;
	}
	private Jump(){
		this.color = Couleur.GRIS;
	}
	/**
	 * deplace le robot sur la case devant lui si  :
	 * sa hauteur est supérieure a la hauteur actuelle de 1, ou inférieure
	 */
	@Override
	public void execute(abstr_Robot r) throws MouvementEx,UnreachableCase {

		abstr_Case c_prime = null;
		Coordonnees pos = r.getCurrent_Case().get_coordonnees();
		switch (r.getOrientation()) {
		case  TOP :
			c_prime = World.currentWorld.get_terrain(pos.get_n()).get_case(pos.get_x(),pos.get_y()-1);
			break;
		case  BOT :
			c_prime = World.currentWorld.get_terrain(pos.get_n()).get_case(pos.get_x(),pos.get_y()+1);;
			break;
		case  LEFT :
			c_prime = World.currentWorld.get_terrain(pos.get_n()).get_case(pos.get_x()-1,pos.get_y());
			break;
		case  RIGHT :
			c_prime = World.currentWorld.get_terrain(pos.get_n()).get_case(pos.get_x()+1,pos.get_y());
			break;
		}

		if (! this.isPossible(r,c_prime)){
			System.out.println("Sauter pour rien , c'est bien");
			r.setVoid();
		} else {
			r.setCurrent_Case(c_prime);
			this.notifyObserver();
		}

	}
	@Override
	public boolean isPossible(abstr_Robot r, abstr_Case c) {
		return ((!c.getClass().getSimpleName().equals("Empty_Case"))
				&& (r.get_couleur().equals(this.color) || this.getColor().equals(Couleur.GRIS))
				&& (r.getCurrent_Case().get_hauteur()+1 == c.get_hauteur()
				|| (c.get_hauteur() < r.getCurrent_Case().get_hauteur())));
	}

	@Override
	public void addObserver(int_Observer obs) {
		this.listObserver.add(obs);
	}
	@Override
	public void removeObserver() {
		this.listObserver = new ArrayList<int_Observer>();

	}
	@Override
	public void notifyObserver() {
		for(int_Observer obs : this.listObserver)
			obs.update(this);
	}
	@Override
	public int_Action Clone() {
		// TODO Auto-generated method stub
		int_Action temp = new Jump(this.getColor());
		return temp;
	}


}
