package myplane;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Sky extends FlyingObject {
	private static BufferedImage[] images; 
	private int steep;
	int index;
	int y1;

	static {
		images = new BufferedImage[4];
		for (int i = 0; i < images.length; i++) {
			images[i] = FlyingObject.loadImage("bg_"+i+".jpg");
		}
	}
	
	public Sky(int index) {
		super(World.WIDTH, World.HEIGHT, 0, 0);
		steep = 1;
		y1 = -heigth;
		this.index = index;
	}

	public BufferedImage getImage() {
		return images[index];
	}


	public void PaintObject(Graphics g) {
		g.drawImage(getImage(),x,y,null);
		g.drawImage(getImage(),x,y1,null);
	}

	@Override
	public void step() {
		y+=steep;
		y1+=steep;
		if(y>=World.HEIGHT) {
			y = -heigth;
		}
		if(y1>=World.HEIGHT) {
			y1 = -heigth;
		}
	}
	
}
