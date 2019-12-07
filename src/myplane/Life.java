package myplane;

import java.awt.image.BufferedImage;

public class Life extends Bee {
	private static BufferedImage image;
	private int index;
	
static {
		image = FlyingObject.loadImage("bee1.png");
	}
	public Life(int index) {
		super(73, 67,FlyingObject.AIRPLANE);
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
