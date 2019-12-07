package myplane;

import java.awt.image.BufferedImage;
import java.util.Collection;

public class Bullet extends FlyingObject {
	private static BufferedImage[] images;
	private int ysteep;
	private int xsteep;
	private int doubleFire;
	static {
		images = new BufferedImage[4];
		for (int i = 0; i < images.length; i++) {
			images[i] = FlyingObject.loadImage("bullet" + i + ".png");
		}

	}

	public Bullet(int x, int y,int doubleFire) {
		super(10, 42, x, y);
		ysteep = 6;
		xsteep = 1;
		this.doubleFire = doubleFire;
	}

	@Override
	public BufferedImage getImage() {
		if (isLife()) {
			if (doubleFire < 5) {
				return images[0];
			} else if (doubleFire < 10) {
				return images[1];
			} else if (doubleFire < 15) {
				return images[2];
			} else {
				return images[3];
			}
		} else if (isDead()) {
			state = REMOVE;
		}
		return null;
	}
	int index = 0;
	public void bulletStep(int i, Bullet b,int size) {
		
		if (doubleFire < 15) {
			b.y -= ysteep;
		} else {
			if (i%size<1) {
				b.x -= xsteep;
				b.y -= ysteep;
			} else if (i%size>4) {
				b.x += xsteep;
				b.y -= ysteep;
			} else {
				b.y -= ysteep;
			}
		}
		index++;

	}

	public boolean outOfBounds() {
		return this.y <= -this.heigth;

	}

	@Override
	public void step() {
		// TODO Auto-generated method stub

	}
}
