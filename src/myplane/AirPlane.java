package myplane;

import java.awt.image.BufferedImage;
import java.util.Random;

public class AirPlane extends FlyingObject implements Award{
	private static BufferedImage[] images; 
	private int steep;
	private int index;
	
	static {
		images = new BufferedImage[4];
		for (int i = 0; i < images.length; i++) {
			images[i] = FlyingObject.loadImage("airplane"+i+".png");
		}
	}
	
	public AirPlane(int index) {
		super(60, 40,FlyingObject.AIRPLANE);
		steep = 3;
		this.index = index;
	}

	Random rand = new Random();
	int type = rand.nextInt(4);
	public BufferedImage getImage() {
		if(isLife()) {
			return images[type];
		}else if (isDead()) {
			state = REMOVE;
		}
		return null;
	}
	
	public void step() {
		y+=steep;
		
	}

	@Override
	public int getScore() {
		return 5+5*index;
	}

}
