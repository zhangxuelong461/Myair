package myplane;

import java.awt.image.BufferedImage;

public class BossBullect extends FlyingObject {

	private static BufferedImage image;
	private int speed;
	
	static {
		image = FlyingObject.loadImage("bossbullet.png");
	}
	
	public BossBullect(int x, int y) {
		super(24, 95, x, y);
		speed = 3;
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
		y+=speed;
		
	}

}
