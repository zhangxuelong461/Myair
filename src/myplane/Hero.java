package myplane;

import java.awt.image.BufferedImage;

public class Hero extends FlyingObject {
	private static BufferedImage[] images; 
	public static boolean up = false;
	public static boolean down = false;
	public static boolean left = false;
	public static boolean right = false;
	private int life;
	protected int doubleFire;

	static {
		images = new BufferedImage[2];
		for (int i = 0; i < images.length; i++) {
			images[i] = FlyingObject.loadImage("hero"+i+".png");
		}
	}
	
	public Hero(int x, int y) {
		super(100, 100,x,y);
		life = 100;
		doubleFire = 0;
	}

	int heroIndex = 0;
	public BufferedImage getImage() {
		if(isLife()) {
			return images[heroIndex++%2];
		}else if (isDead()) {
			state = REMOVE;
		}
		return null;
	}
	
	public void moveTo(int x, int y) {
		this.x = x-this.width/2;
		this.y = y-this.heigth/2;

	}
	
	public Bullet[] shoot() {
		int xstep = this.width/6;
		int ystep = 5;
		if (doubleFire < 5) {
			Bullet[] blt = new Bullet[1];
			blt[0]=new Bullet(this.x+3*xstep, this.y+ystep,doubleFire);
			return blt;
		}else if (doubleFire < 10) {
			Bullet[] blt = new Bullet[2];
			blt[0]=new Bullet(this.x+2*xstep, this.y+ystep,doubleFire);
			blt[1]=new Bullet(this.x+4*xstep, this.y+ystep,doubleFire);
			return blt;
		}else if (doubleFire < 15) {
			Bullet[] blt = new Bullet[4];
			blt[0]=new Bullet(this.x+1*xstep, this.y+ystep,doubleFire);
			blt[1]=new Bullet(this.x+2*xstep, this.y+ystep,doubleFire);
			blt[2]=new Bullet(this.x+4*xstep, this.y+ystep,doubleFire);
			blt[3]=new Bullet(this.x+5*xstep, this.y+ystep,doubleFire);
			return blt;
		}else{
			Bullet[] blt = new Bullet[6];
			blt[0]=new Bullet(this.x+0*xstep, this.y+ystep,doubleFire);
			blt[1]=new Bullet(this.x+1*xstep, this.y+ystep,doubleFire);
			blt[2]=new Bullet(this.x+2*xstep, this.y+ystep,doubleFire);
			blt[3]=new Bullet(this.x+4*xstep, this.y+ystep,doubleFire);
			blt[4]=new Bullet(this.x+5*xstep, this.y+ystep,doubleFire);
			blt[5]=new Bullet(this.x+6*xstep, this.y+ystep,doubleFire);
			return blt;
		}
	}
	public void step() {
			if(up && y>0){
				y -= 5;
			}else if(down && y<World.HEIGHT-images[0].getHeight()){
				y += 5;
			}else{
				y += 0;
			}
			if(left && x>0){
				x -= 5;
			}else if(right && x<World.WIDTH-images[0].getWidth()){
				x += 5;
			}else{
				x += 0;
			}
		
	}
	public void addLife() {
		if(this.life<100) {
			this.life+=10;
			if(this.life>100) {
				this.life = 100;
			}
		}
//		System.out.println(life);
	}
	public void adddoubleFire() {
		
		if(doubleFire<20) {
			this.doubleFire+=5;
		}
//		System.out.println(doubleFire);
	}

	public void subtractDoubleFire() {
		if(doubleFire>=15) {
			this.doubleFire=10;
		}else if(doubleFire>=10) {
			this.doubleFire=5;
		}else {
			this.doubleFire = 0;
		}

	}
	
	public void subtractLife() {
		this.life-=3;
	}
	public void subLife() {
		this.life-=5;

	}
	public int getLife() {
		return this.life;
	}
	public void setLife(int life) {
		this.life=life;
	}

}
