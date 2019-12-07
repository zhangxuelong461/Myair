package myplane;

import java.awt.image.BufferedImage;

public class Maskant extends FlyingObject{

	
	public Maskant(int x, int y) {
		super(220, 220,x,y);

	}

	private static BufferedImage images[];
	
	static {
		images = new BufferedImage[4];
		for (int i = 0; i < images.length; i++) {
			images[i] = FlyingObject.loadImage("clean"+i+".png");
		}
	}

	int index = 0;
	public BufferedImage getImage() {
			return images[index++%4];
		
	}

	public void moveTo(int x, int y) {
		this.x = x-this.width/2;
		this.y = y-this.heigth/2;

	}
	
	@Override
	public void step() {
		
		
	}
	public void step2(int x, int y) {
		this.x=x-55;
		this.y=y-55;
		
	}
	
}
