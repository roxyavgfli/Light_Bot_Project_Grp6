package observable.action_list;

import java.util.ArrayList;
import java.util.LinkedList;

import exception.ActionEx;
import exception.MouvementEx;
import exception.UnreachableCase;
import observable.int_Observable;
import observable.action.int_Action;
import observable.robot.abstr_Robot;
import observer.int_Observer;

public class Execution_list implements int_Observable{

	private LinkedList<Sequence_List> Run_List = new LinkedList<Sequence_List>();

	private ArrayList<int_Observer> listObserver = new ArrayList<int_Observer>();

	public void add(Sequence_List next){
		this.Run_List.add(next);
	}
	public void addFirst(Sequence_List new_h){
		this.Run_List.addFirst(new_h);
		System.out.println(new_h);
		System.out.println("RUN LIST ICI : "+this.Run_List);
	}

	public void initFirst(Sequence_List main){
		Sequence_List temp = new Sequence_List(null);
		System.out.println(main);
		for(int i = 0; i < main.size(); i++){
			temp.addActionSubSequence(main.get(i).Clone());
		}
		this.addFirst(temp);
		System.out.println("RUN LIST LALALA : "+this.Run_List);
	}

	public void removeFirst(){
		this.Run_List.removeFirst();
	}

	public void run(abstr_Robot r) throws MouvementEx, UnreachableCase, ActionEx{
		System.out.println("Ich bin in run");
		System.out.println(this.Run_List);
		if(this.Run_List.size()>0){
			System.out.println("Ich bin in run : first if");
			System.out.println("taille run list : "+this.Run_List.size());
			System.out.println("taille de la première liste a run : "+ this.Run_List.getFirst().size());
			if(this.Run_List.getFirst().size() > 0){
				System.out.println("Ich bin in run : second if");
				System.out.println("EXEC LIST ROBOT EST : "+r.get_activable());
				if (r.get_activable()){
					int_Action temp = this.Run_List.getFirst().removeFirst();
					System.out.println(temp);
					temp.execute(r);
				}else{
					System.out.println("Je passe mon tour je suis pas actif");
				}
			}
			else{
				this.Run_List.removeFirst();
				this.run(r);
			}
		}
		else{
			System.out.println("J'AI FINIS TA RACE");
			r.set_activable(false);
			//World.currentWorld.get_ordonnanceur().removeRobotFromOrdo(r); MODIF ICI
			this.notifyObserver();
			//throw new ActionEx("Liste robot vide");
		}
	}

	public int_Action getFirstAct(){
		return this.Run_List.getFirst().getListActions().getFirst();
	}
	@Override
	public String toString(){
		String str = new String();
		for (int i = 0; i < this.Run_List.size(); i++){
			str = str+" | "+this.Run_List.get(i).toString();
		}
		return str;
	}
	public void clear() {
		while (this.size()>0){
			this.Run_List.removeFirst();
		}
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
		//System.out.println(this.listObserver.toString());
		for(int_Observer obs : this.listObserver)
			obs.update(this);
	}

	public int size(){
		return this.Run_List.size();
	}
}
