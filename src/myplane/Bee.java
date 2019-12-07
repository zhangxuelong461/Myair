package myplane;

import java.util.Random;

public abstract class Bee extends FlyingObject implements Award{
	private int xSpeed;
	private int ySpeed;

	Random rand = new Random();

	public Bee(int x,int y,int type) {
		super(x, y,type);
		xSpeed = 1;
		ySpeed = 2;

	}



	int type = rand.nextInt(2);
	public void step() {
		if (type == 0) {
			x += xSpeed;
			y += ySpeed;
		} else {
			x -= xSpeed;
			y += ySpeed;
		}
		if(x<0 || x>World.WIDTH-this.width) {
			xSpeed*=-1;
		}
	}

}
