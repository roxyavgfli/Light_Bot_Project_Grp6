package View;


import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.JOptionPane;

import observable.action.Activate;
import observable.action.MoveForward;
import observable.action.int_Action;
import observable.map.Empty_Case;
import observable.map.Illuminated_Case;
import observable.map.Normal_Case;
import observable.map.Painted_Case;
import observable.map.Teleporter_Case;
import observable.map.Terrain;
import observable.map.World;
import observable.map.abstr_Case;
import observable.robot.Orientation;
import observable.robot.abstr_Robot;

import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.system.Vector2i;
import org.jsfml.window.Keyboard;
import org.jsfml.window.Keyboard.Key;
import org.jsfml.window.Mouse;
import org.jsfml.window.Mouse.Button;
import org.jsfml.window.event.Event;
import org.jsfml.window.event.Event.Type;

import couleur.Couleur;
import exception.ActionEx;
import exception.MouvementEx;
import exception.UnreachableCase;

public class Jeu {

	public Texture maTexture = new Texture();
	public Sprite monSprite = new Sprite();
	public Texture maTextureBouton = new Texture();
	public Sprite monSpriteBouton = new Sprite();
	public Texture maTextureBackground = new Texture();
	public Sprite monSpriteBackground = new Sprite();
	public Texture maTexturePerso = new Texture();
	public Sprite monSpritePerso = new Sprite();
	public static HashMap<Sprite,String> liste_sprite = new HashMap<Sprite, String>();
	private int level;
	private static World w = World.currentWorld;
	private static abstr_Robot r = Jeu.w.get_robot(0);
	private Terrain t = Jeu.w.get_terrain(this.level);
	private abstr_Case[][] cases = this.t.get_terrain();
	private int width_case = 80;
	private int height_case = 50;
	private static final int NB_MAX_CASE = 10;
	private int indice_tele=0;

	/**********CONSTRUCTEURS************/
	public Jeu(int lvl){
		this.level = lvl;
		while(Menu.app.isOpen()){
			Menu.app.clear();
			Jeu.processEvent();
			this.drawGrilleISO();
			//drawPerso();
			try {
				this.draw_bouton();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.draw_procedure();
			Menu.app.display();
			if(World.currentWorld.is_cleared()){
				//				JOptionPane.showMessageDialog(null, "Fin");
				//				Menu.app.close();
			}
			//System.out.println(Mouse.getPosition().x + " " + Mouse.getPosition().y);
		}
	}

	public static void processEvent(){
		Menu.app.setKeyRepeatEnabled(false);
		for(Event e : Menu.app.pollEvents()){
			if(e.type == Type.CLOSED){
				Menu.app.close();

			}

			if (Keyboard.isKeyPressed(Key.LEFT)){
				Jeu.r.setOrientation(Orientation.orientation.LEFT);
				try {
					MoveForward.move_forward().execute(Jeu.r);
				} catch (MouvementEx e1) {
					// TODO Auto-generated catch block
					System.out.println(e1.getMessage());
				} catch (UnreachableCase e1) {
					// TODO Auto-generated catch block
					System.out.println(e1.getMessage());
				}
			}
			if (Keyboard.isKeyPressed(Key.RIGHT)){
				Jeu.r.setOrientation(Orientation.orientation.RIGHT);
				try {
					MoveForward.move_forward().execute(Jeu.r);
				} catch (MouvementEx e1) {
					// TODO Auto-generated catch block
					System.out.println(e1.getMessage());
				} catch (UnreachableCase e1) {
					// TODO Auto-generated catch block
					System.out.println(e1.getMessage());
				}
			}
			if (Keyboard.isKeyPressed(Key.SPACE)){
				try {
					Activate.activate().execute(Jeu.r);
				} catch (MouvementEx e1) {
					// TODO Auto-generated catch block
					System.out.println(e1.getMessage());
				} catch (UnreachableCase e1) {
					// TODO Auto-generated catch block
					System.out.println(e1.getMessage());
				}
			}
			if (Keyboard.isKeyPressed(Key.UP)){
				Jeu.r.setOrientation(Orientation.orientation.TOP);
				try {
					MoveForward.move_forward().execute(Jeu.r);
				} catch (MouvementEx e1) {
					// TODO Auto-generated catch block
					System.out.println(e1.getMessage());
				} catch (UnreachableCase e1) {
					// TODO Auto-generated catch block
					System.out.println(e1.getMessage());
				}
			}
			if (Keyboard.isKeyPressed(Key.DOWN)){
				Jeu.r.setOrientation(Orientation.orientation.BOT);
				try {
					MoveForward.move_forward().execute(Jeu.r);
				} catch (MouvementEx e1) {
					// TODO Auto-generated catch block
					System.out.println(e1.getMessage());
				} catch (UnreachableCase e1) {
					// TODO Auto-generated catch block
					System.out.println(e1.getMessage());
				}
			}

			if (Keyboard.isKeyPressed(Key.SPACE)){
				Jeu.r.run();
			}

			if (e.type == Event.Type.MOUSE_BUTTON_PRESSED && Mouse.isButtonPressed(Button.LEFT)) {
				e.asMouseEvent();
				Vector2i pos = Mouse.getPosition(Menu.app);
				try {
					Jeu.detect_move(pos);
				} catch (ActionEx e1) {
					System.out.println(e1.getMessage());
				}

			}

		}
	}

	private static void detect_move(Vector2i pos) throws ActionEx {
		int x = pos.x;
		int y = pos.y;
		Iterator<Sprite> keySetIterator = Jeu.liste_sprite.keySet().iterator();
		while(keySetIterator.hasNext()){
			Sprite s = keySetIterator.next();
			FloatRect rect = s.getGlobalBounds();
			System.out.println(rect.left+" "+rect.top+" "+rect.width+" "+rect.height);
			if(x>=rect.left && x<=rect.left+rect.width &&
					y>=rect.top && y<=rect.top+rect.height){
				if(Jeu.liste_sprite.get(s).equals("MoveForward"))
					//					Jeu.r.add_Action_User_Actions(TurnLeft.turn_left());
					System.out.println("Avance");
				else if(Jeu.liste_sprite.get(s).equals("TurnRIght"))
					//					Jeu.r.add_Action_User_Actions(TurnRIght.turn_right());
					System.out.println("Droite");
				else if(Jeu.liste_sprite.get(s).equals("TurnLeft"))
					//					Jeu.r.add_Action_User_Actions(TurnRIght.turn_right());
					System.out.println("Gauche");
				else if(Jeu.liste_sprite.get(s).equals("Activate"))
					//					Jeu.r.add_Action_User_Actions(TurnRIght.turn_right());
					System.out.println("Allumer");
				else if(Jeu.liste_sprite.get(s).equals("Jump"))
					//					Jeu.r.add_Action_User_Actions(TurnRIght.turn_right());
					System.out.println("Sauter");
			}
		}
	}


	public void draw_popup(String msg){
		JOptionPane.showMessageDialog(null, msg);
	}

	/**
	 * Used to display a robot
	 * @param rob
	 */
	public void display_robot(abstr_Robot rob){
		try {
			if(Jeu.r.getOrientation() == Orientation.orientation.BOT)
				this.maTexturePerso.loadFromFile(Paths.get("Images/Jeu/gif/images_fixes/3.png"));
			else if(Jeu.r.getOrientation() == Orientation.orientation.LEFT)
				this.maTexturePerso.loadFromFile(Paths.get("Images/Jeu/gif/images_fixes/15.png"));
			else if(Jeu.r.getOrientation() == Orientation.orientation.RIGHT)
				this.maTexturePerso.loadFromFile(Paths.get("Images/Jeu/gif/images_fixes/7.png"));
			else if(Jeu.r.getOrientation() == Orientation.orientation.TOP)
				this.maTexturePerso.loadFromFile(Paths.get("Images/Jeu/gif/images_fixes/11.png"));

			this.monSpritePerso.setTexture(this.maTexturePerso);
			this.monSpritePerso.setPosition(rob.getCurrent_Case().get_coordonnees().get_x(),rob.getCurrent_Case().get_coordonnees().get_y());
			Menu.app.draw(this.monSpritePerso);


		} catch (IOException e) {
			System.out.println(e.getMessage());
		} // on charge la texture qui se trouve dans notre dossier assets
	}


	public void drawPerso(int X, int Y){
		try {
			if(Jeu.r.getOrientation() == Orientation.orientation.BOT)
				this.maTexturePerso.loadFromFile(Paths.get("Images/Jeu/gif/images_fixes/3.png"));
			else if(Jeu.r.getOrientation() == Orientation.orientation.LEFT)
				this.maTexturePerso.loadFromFile(Paths.get("Images/Jeu/gif/images_fixes/15.png"));
			else if(Jeu.r.getOrientation() == Orientation.orientation.RIGHT)
				this.maTexturePerso.loadFromFile(Paths.get("Images/Jeu/gif/images_fixes/7.png"));
			else if(Jeu.r.getOrientation() == Orientation.orientation.TOP)
				this.maTexturePerso.loadFromFile(Paths.get("Images/Jeu/gif/images_fixes/11.png"));

			this.monSpritePerso.setTexture(this.maTexturePerso);
			this.monSpritePerso.setPosition(X,Y);
			Menu.app.draw(this.monSpritePerso);

		} catch (IOException e) {
			System.out.println(e.getMessage());
		} // on charge la texture qui se trouve dans notre dossier assets
	}
	public void draw_bouton() throws IOException{
		LinkedList<int_Action> actions = r.get_possible().get();
		String tab[]=  {"TurnLeft","TurnRIght","MoveForward","Activate","Jump","Bug"};

		for(int i=0;i<tab.length;i++){
			Texture maTextureBouton = new Texture();
			Sprite monSpriteBouton = new Sprite();
			if(i<5)
				maTextureBouton.loadFromFile(Paths.get("Images/Jeu/bouton/"+tab[i]+".png"));
			Jeu.liste_sprite.put(monSpriteBouton,"");
			monSpriteBouton.setTexture(maTextureBouton);
			monSpriteBouton.setPosition(10+100*i,610);
			Jeu.liste_sprite.put(monSpriteBouton,tab[i]);
			Menu.app.draw(monSpriteBouton);
		}
	}



	public void draw_procedure(){
		try {
			String tab[]=  {"Fond_Main","Fond_P1","Fond_P2"};
			for(int i=0;i<tab.length;i++){
				Sprite monSpriteBouton = new Sprite();
				this.maTexture.loadFromFile(Paths.get("Images/Jeu/background/"+tab[i]+".png"));
				monSpriteBouton.setTexture(this.maTexture);
				Menu.app.draw(monSpriteBouton);
			}
			int nombre_bouton[] = {r.get_tailleMain(),r.get_tailleP1(),r.get_tailleP2()};
			int pos_bouton[] = {44,324,530};
			int y;
			for(int i=0; i<pos_bouton.length;i++){
				y=0;
				for(int x=0; x<nombre_bouton[i];x++){
					if(x%4==0){
						y++;
					}
					Sprite monSprite = new Sprite();
					this.maTexture.loadFromFile(Paths.get("Images/Jeu/background/Fond_Bouton.png"));
					monSprite.setPosition(884+(x%4*(70+4)), pos_bouton[i] + (y-1)*(70+10));
					monSprite.setTexture(this.maTexture);
					Menu.app.draw(monSprite);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}


	}


	public void typeCases(abstr_Case cases){
		try{
			if(cases instanceof Normal_Case){
				this.maTexture.loadFromFile(Paths.get("Images/Jeu/Cases/Square_normal.png"));
			}
			else if(cases instanceof Empty_Case){
				this.maTexture.loadFromFile(Paths.get("Images/Jeu/Cases/Square_vide.png"));
			}
			else if(cases instanceof Teleporter_Case){
				this.maTexture.loadFromFile(Paths.get("Images/Jeu/Cases/case_teleporteur/Case_pointeur_"+ this.indice_tele+".png"));
				this.indice_tele++;
				if( this.indice_tele>=9){
					this.indice_tele=0;
				}
			}
			else if(cases instanceof Painted_Case){
				if(cases.get_couleur()== Couleur.VERT)
					this.maTexture.loadFromFile(Paths.get("Images/Jeu/Cases/Square_vert.png"));
				else if(cases.get_couleur()== Couleur.GRIS)
					this.maTexture.loadFromFile(Paths.get("Images/Jeu/Cases/Square_normal.png"));
				else if(cases.get_couleur()== Couleur.ROUGE)
					this.maTexture.loadFromFile(Paths.get("Images/Jeu/Cases/Square_rouge.png"));
			}
			else if(cases instanceof Illuminated_Case){
				if(((Illuminated_Case) cases).get_active()==false){
					this.maTexture.loadFromFile(Paths.get("Images/Jeu/Cases/Square_allumable2.png"));
				}else{
					this.maTexture.loadFromFile(Paths.get("Images/Jeu/Cases/Square_allume.png"));
				}
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} // on charge la texture qui se trouve dans notre dossier assets


	}

	public void display_world(World world){

	}

	public void display_terrain(Terrain terrain) throws IOException{
		//			this.maTextureBackground.loadFromFile(Paths.get("background.jpg"));
		//			this.monSpriteBackground.setTexture(this.maTextureBackground);
		//			Menu.app.draw(this.monSpriteBackground);
		//
		//			int Xtemp,Ytemp;
		//			int taille_abs =  terrain.get_terrain()[0].length;
		//			int taille_ord =  terrain.get_terrain().length;
		//
		//			System.out.println("*********");
		//			for(int X=taille_abs-1;X>=0;X--){
		//				Ytemp=0;//taille_ord-1;
		//				System.out.println("X = "+X);
		//				for(Xtemp=X;Xtemp<taille_abs;Xtemp++){
		//					if(Ytemp<0){
		//						break;
		//					}
		//					System.out.println("(Xtemp,Ytemp) = ("+Xtemp+","+Ytemp+")");
		//					this.affichageISO(Xtemp,Ytemp);
		//					Ytemp++;
		//				}
		//			}
		//			for(int Y=1;Y<taille_ord;Y++){//-2 car on a géré le cas 0 avec X juste au dessus
		//				Xtemp=0;
		//				System.out.println("Y = "+Y);
		//				for(Ytemp=Y;Ytemp<taille_ord;Ytemp++){
		//					if(Xtemp>=taille_abs){
		//						break;
		//					}
		//					this.affichageISO(Xtemp,Ytemp);
		//					System.out.println("(Xtemp,Ytemp) = ("+Xtemp+","+Ytemp+")");
		//					Xtemp++;
		//				}
		//			}// on charge la texture qui se trouve dans notre dossier assets
	}


	public void drawGrilleISO(){
		try{
			this.maTextureBackground.loadFromFile(Paths.get("Images/Jeu/background2.jpg"));
			this.monSpriteBackground.setTexture(this.maTextureBackground);
			Menu.app.draw(this.monSpriteBackground);

			int Xtemp,Ytemp;
			int taille_abs =  World.currentWorld.get_terrain(0).get_terrain()[0].length;
			int taille_ord =  World.currentWorld.get_terrain(0).get_terrain().length;
			//
			//		System.out.println("***************");
			for(int X=taille_abs-1;X>=0;X--){
				Ytemp=0;//taille_ord-1;
				//	System.out.println("X = "+X);
				for(Xtemp=X;Xtemp<taille_abs;Xtemp++){
					if(Ytemp<0){
						break;
					}
					//	System.out.println("(Xtemp,Ytemp) = ("+Xtemp+","+Ytemp+")");
					this.affichageISO(Xtemp,Ytemp);
					Ytemp++;
				}
			}
			for(int Y=1;Y<taille_ord;Y++){//-2 car on a géré le cas 0 avec X juste au dessus
				Xtemp=0;
				//		System.out.println("Y = "+Y);
				for(Ytemp=Y;Ytemp<taille_ord;Ytemp++){
					if(Xtemp>=taille_abs){
						break;
					}
					this.affichageISO(Xtemp,Ytemp);
					//		System.out.println("(Xtemp,Ytemp) = ("+Xtemp+","+Ytemp+")");
					Xtemp++;
				}
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} // on charge la texture qui se trouve dans notre dossier assets
	}

	public void affichageISO(int X, int Y){

		try {
			abstr_Case Ma_case = World.currentWorld.get_terrain(0).get_case(X,Y);
			int x = Jeu.r.getCurrent_Case().get_coordonnees().get_x();
			int y = Jeu.r.getCurrent_Case().get_coordonnees().get_y();
			int hauteur_max = Ma_case.get_hauteur();
			int taille_abs =  World.currentWorld.get_terrain(0).get_terrain()[0].length;
			int taille_ord =  World.currentWorld.get_terrain(0).get_terrain().length;
			int PosX = Menu.app.getSize().x/2 +59*(Y+X)-taille_abs*60-180;
			int PosY = Menu.app.getSize().y/2 +18*(Y-X)-taille_ord*18+100;

			for(int hauteur=1; hauteur<hauteur_max;hauteur++){
				try {
					this.maTexture.loadFromFile(Paths.get("Images/Jeu/Cases/Square_empile.png"));
					this.monSprite.setTexture(this.maTexture);
					this.monSprite.setPosition(PosX,PosY-26*hauteur);
					Menu.app.draw(this.monSprite);
				} catch (IOException e) {
					System.out.println(e.getMessage());
				}
			}
			this.typeCases(Ma_case);
			this.monSprite.setTexture(this.maTexture);
			this.monSprite.setPosition(PosX,PosY-26*hauteur_max);
			Menu.app.draw(this.monSprite);

			//Si le pingouin est sur cette case, alors on l'affiche à la hauteur maximale de celle-ci
			if ((x == X) && (y == Y)){
				this.drawPerso(PosX+30+10,PosY-26*hauteur_max+25);
			}

		} catch (UnreachableCase e1) {
			System.out.println(e1.getMessage());
		}
	}

}
