package myplane;

import java.awt.image.BufferedImage;


public class Boss extends FlyingObject implements Award{

	private static BufferedImage[] images;
	private int life;
	private int index;
	private int xSpeed,ySpeed;
	
	static {
		images = new BufferedImage[4];
		for (int i = 0; i < images.length; i++) {
			images[i] = FlyingObject.loadImage("boss_"+i+".png");
		}
	}
	public Boss() {
		super(300, 225,FlyingObject.BOSS);
	}
	
	public Boss(int index,boolean b) {
		super(300, 225,FlyingObject.BOSS);
		if(b) {
			life = 150+index*150;
		}else {
			life = 1000+index*1000;
		}
		this.index = index;
		xSpeed = 1;
		ySpeed = 2;
	}

	@Override
	public BufferedImage getImage() {
		if(isLife()) {
			return images[index];
		}else if(isDead()) {
			state = REMOVE;
		}
		return null;
	}

	
	public void step() {
		
		if(y<10) {
			y+=ySpeed;
		}else {
			x+=xSpeed;
		}
		if(x<0||this.x>World.WIDTH-this.width) {
			xSpeed*=-1;
		}
	}
	
	public BossBullect[] shoot() {
		int xstep = this.width/8;
		int ystep = 5;
			BossBullect[] blt = new BossBullect[8];
			blt[0]=new BossBullect(this.x+0*xstep, this.heigth+ystep);
			blt[1]=new BossBullect(this.x+1*xstep, this.heigth+ystep);
			blt[2]=new BossBullect(this.x+2*xstep, this.heigth+ystep);
			blt[3]=new BossBullect(this.x+3*xstep, this.heigth+ystep);
			blt[4]=new BossBullect(this.x+5*xstep, this.heigth+ystep);
			blt[5]=new BossBullect(this.x+6*xstep, this.heigth+ystep);
			blt[6]=new BossBullect(this.x+7*xstep, this.heigth+ystep);
			blt[7]=new BossBullect(this.x+8*xstep, this.heigth+ystep);
			return blt;

	}
	public LightBullet[] LightShoot() {
		int ystep = 15;
		LightBullet[] blt = new LightBullet[2];
		blt[0]=new LightBullet(this.x, this.heigth-ystep);
		blt[1]=new LightBullet(this.x+this.width-42, this.heigth-ystep);
		return blt;
		
	}
	

	public void subtractLife() {
		life--;

	}
	public void subLife() {
		life-=50;
		
		System.out.println(life);
	}
	public int getLife() {
		return life;
		
	}

	@Override
	public int getScore() {
		return 500;
	}
}
