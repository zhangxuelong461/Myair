package myplane;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

public abstract class FlyingObject {

	public static final int LIFE = 0;
	public static final int DEAD = 1;
	public static final int REMOVE = 2;
	public static final int AIRPLANE = 0;
	public static final int BOSS = 1;
	public int state = LIFE;
	protected int width;
	protected int heigth;
	protected int x;
	protected int y;
	
	public abstract BufferedImage getImage();
	public abstract void step();
	
	public FlyingObject(int width, int heigth, int x, int y) {
		this.width = width;
		this.heigth = heigth;
		this.x = x;
		this.y = y;
	}
	public FlyingObject(int width, int heigth, int type) {
		this.width = width;
		this.heigth = heigth;
		Random rand = new Random();
		if(FlyingObject.AIRPLANE==type) {
			this.x = rand.nextInt(World.WIDTH-this.width);
		}else if(FlyingObject.BOSS==type) {
			this.x = World.WIDTH/2-this.width/2;
		}
		this.y = -this.heigth;
		
	}
	public FlyingObject(int width, int heigth) {
		this.width = width;
		this.heigth = heigth;
		Random rand = new Random();
		this.x = rand.nextInt(World.WIDTH-this.width);
		this.y = World.HEIGHT+rand.nextInt(World.HEIGHT*2);
		
	}
	public boolean outOfBounds() {
		return this.y >= World.HEIGHT;

	}
	public static BufferedImage loadImage(String fileName) {
		try {
			BufferedImage img = ImageIO.read(FlyingObject.class.getResource(fileName));
			return img;
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		
	}
	
	public boolean isLife() {
		return state == LIFE;
	}
	public boolean isDead() {
		return state == DEAD;
	}
	public boolean isRemove() {
		return state == REMOVE;
	}
	
	public void PaintObject(Graphics g) {
		g.drawImage(getImage(),x,y,null);
		
	}
	public boolean hit(FlyingObject other) {
		int x1 = this.x - other.width;
		int y1 = this.y - other.heigth;
		int x2 = this.x + this.width;
		int y2 = this.y + this.heigth;
		int x = other.x;
		int y = other.y;
		return x1 <= x && x2 >= x 
						&& 
						y1 <= y && y2 >= y;

	}
	public void goDead(){
		state = DEAD;
	}
}
