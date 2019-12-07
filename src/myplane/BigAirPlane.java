package myplane;

import java.awt.image.BufferedImage;
import java.util.Random;

public class BigAirPlane extends FlyingObject implements Award{
	private static BufferedImage[] images;
	private int steep;
	private int life;
	private int index;
	static {
		images = new BufferedImage[3];
		for (int i = 0; i < images.length; i++) {
			images[i] = FlyingObject.loadImage("bigplane"+i+".png");
		}
	}
	
	public BigAirPlane(int index,boolean b) {
		super(120, 100,FlyingObject.AIRPLANE);
		this.index = index;
		steep = 2;
		if(b) {
			life = 15+index*15;
		}else {
			life = 10+index*10;
		}
	}

	Random rand = new Random();
	int type = rand.nextInt(3);
	public BufferedImage getImage() {
		if(isLife()) {
			return images[type];
		}else if (isDead()) {
			state = REMOVE;
		}
		return null;
	}
	public BigAirplaneBullet[] shoot() {
		int xstep = this.width/4;
		int ystep = 5;
		BigAirplaneBullet[] blt = new BigAirplaneBullet[2];
			blt[0]=new BigAirplaneBullet(this.x+0*xstep, this.y+this.heigth+ystep);
			blt[1]=new BigAirplaneBullet(this.x+3*xstep, this.y+this.heigth+ystep);
			return blt;

	}
	@Override
	public void step() {
		y+=steep;
		
	}
	public void subtractLife() {
		this.life--;
	}
	public int getLife() {
		return life;
	}
	@Override
	public int getScore() {
		return 10+10*index;
	}
}
