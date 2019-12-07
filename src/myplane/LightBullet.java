package myplane;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import myplane.FlyingObject;

public class LightBullet extends FlyingObject {

	private static BufferedImage images[];
	private int xSpeed;
	static {
		images = new BufferedImage[2];
		for (int i = 0; i < images.length; i++) {
			images[i] = FlyingObject.loadImage("bossLight"+i+".png");
		}
	}
	public LightBullet(int x,int y) {
		super(42, 691, x, y);
		xSpeed = 1;
	}

	int ligthindex = 0;
	public BufferedImage getImage() {
		if(isLife()) {
			return images[ligthindex++%2];
		}if(isDead()) {
			state = REMOVE;
		}
		return null;
	}

	@Override
	public void step() {
		x+=xSpeed;
		if(x<0||this.x>World.WIDTH-this.width) {
			xSpeed*=-1;
		}
		
	}
//	@Override
//	public void PaintObject(Graphics g) {
//		g.drawImage(getImage(),x,y-30,null);
//		g.drawImage(getImage(),this.x+new Boss().width-this.width,y-30,null);
//	}

}
