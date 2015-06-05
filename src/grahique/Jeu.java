package grahique;

import java.io.IOException;
import java.nio.file.Paths;

import map.Empty_Case;
import map.Normal_Case;
import map.Terrain;
import map.World;
import map.abstr_Case;

import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.window.Keyboard;
import org.jsfml.window.Keyboard.Key;
import org.jsfml.window.VideoMode;
import org.jsfml.window.event.Event;
import org.jsfml.window.event.Event.Type;

public class Jeu {

	public static RenderWindow app = new RenderWindow(new VideoMode(1200, 700),"Lightbot");
	public Texture maTexture = new Texture();
	public Sprite monSprite = new Sprite();
	public Texture maTextureBackground = new Texture();
	public Sprite monSpriteBackground = new Sprite();
	public Texture maTexturePerso = new Texture();
	public Sprite monSpritePerso = new Sprite();
	public static int x = 200;
	public static int y = 50;
	private int level;
	private final Menu2 frame;
	private World w = World.currentWorld;
	private Terrain t = w.get_terrain(level);
	private abstr_Case[][] cases = t.get_terrain();
	private int width_case = 80;
	private int height_case = 50;
	private static final int NB_MAX_CASE = 10;

	public static void processEvent(){
		app.setKeyRepeatEnabled(false);
		for(Event e : app.pollEvents()){
			if(e.type == Type.CLOSED){
				app.close();
			}

			if (Keyboard.isKeyPressed(Key.LEFT)){
				if(x>200)
					x -= 100;
			}
			if (Keyboard.isKeyPressed(Key.RIGHT)){
				if(x<700)
					x += 100;
			}
			if (Keyboard.isKeyPressed(Key.UP)){
				if(y>0)
					y -= 50;
			}
			if (Keyboard.isKeyPressed(Key.DOWN)){
				if(y<250)
					y += 50;
			}



		}
	}

	public void drawGrille(){
		try {
			maTextureBackground.loadFromFile(Paths.get("background.jpg"));
			monSpriteBackground.setTexture(maTextureBackground);
			app.draw(monSpriteBackground);
			for(int i=0;i<cases.length;i++){
				for(int j=0; j<cases[i].length;j++){
					if(cases[i][j] instanceof Normal_Case){
						maTexture.loadFromFile(Paths.get("square.png"));
					}
					else if(cases[i][j] instanceof Empty_Case){
						maTexture.loadFromFile(Paths.get("square_vide.png"));
					}
					monSprite.setTexture(maTexture);
					monSprite.setPosition(80+(width_case-5)*(j+((NB_MAX_CASE-4)/2)), 80+(height_case-5)*(i+((NB_MAX_CASE-4)/2)));
					app.draw(monSprite);
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		} // on charge la texture qui se trouve dans notre dossier assets
	}

	public void drawPerso(){
		try {
			maTexturePerso.loadFromFile(Paths.get("perso.gif"));
			monSpritePerso.setTexture(maTexturePerso);
			monSpritePerso.setPosition(x+40*3, y+25*4);
			app.draw(monSpritePerso);


		} catch (IOException e) {
			e.printStackTrace();
		} // on charge la texture qui se trouve dans notre dossier assets
	}

	public Jeu(Menu2 fenetre, int lvl){
		this.frame=fenetre;
		this.level = lvl+1;
		this.frame.setVisible(false);
		while(app.isOpen()){
			processEvent();
			drawGrille();
			drawPerso();
			app.display();
			//System.out.println(Mouse.getPosition().x + " " + Mouse.getPosition().y);
		}
	}


}