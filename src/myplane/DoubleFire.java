package myplane;

import java.awt.image.BufferedImage;

public class DoubleFire extends Bee {
	private static BufferedImage image;
	private int index;
	static {
		image = FlyingObject.loadImage("bee0.png");
	}
	public DoubleFire(int index) {
		super(60, 130,FlyingObject.AIRPLANE);
		this.index = index;
	}
	public BufferedImage getImage() {
		if (isLife()) {
			return image;
		} else if (isDead()) {
			state = REMOVE;
		}
		return null;
	}
	@Override
	public int getScore() {
		return 2+2*index;
	}
}
