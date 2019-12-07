package myplane;

import java.awt.image.BufferedImage;

public class BigAirplaneBullet extends FlyingObject {

	private static BufferedImage image;
	private int speed;
	
	static {
		image = FlyingObject.loadImage("but.png");
	}
	
	public BigAirplaneBullet(int x, int y) {
		super(10, 22, x, y);
		speed = 6;
	}

	@Override
	public BufferedImage getImage() {
		if(isLife()) {
			return image;
		}else if(isDead()){
			state = REMOVE;
		}
		return null;
	}

	@Override
	public void step() {
	}
	public void bigAirplaneBullet(Hero hero1, Hero hero2,boolean twoPerson){
		
		y+=speed;
		if(twoPerson) {
			if(hero1.y<hero2.y) {
				step(hero1);
			}else if(hero1.y>hero2.y){
				step(hero2);
			}
		}else {
			step(hero1);
		}
	}
	private void step(Hero hero) {
		if (this.x>hero.width/2+hero.x) {
			this.x-=2;
		}else if(this.x<hero.width/2+hero.x) {
			this.x+=2;
		}else {
			this.x=hero.width/2+hero.x;
		}
	}
}
