package myplane;

import java.awt.image.BufferedImage;

public class Ability extends FlyingObject{
	private static BufferedImage image;
	private int speed;
	static {
			image = FlyingObject.loadImage("ability.png");
	}

	public Ability() {
		super(35, 75);
		speed = 5;
	}



	public BufferedImage getImage() {
		if(isLife()) {
		return image;
		}else if (isDead()) {
			state = REMOVE;
		}
		return null;
	}

	@Override
	public void step() {
		this.y-=speed;
		
	}
	public boolean outOfBounds() {
		return this.y <= -this.heigth;

	}
	
}
