package myplane;

import java.util.List;

public class SteepAction {

	public void heroBullet(Bullet[] bullet, Hero hero) {
		for (int i = 0; i<bullet.length;i++) {//遍历英雄机子弹数组
			Bullet b = bullet[i];
			b.bulletStep(i,b,hero.shoot().length);//英雄机子弹移动，参数：i:当前子弹在数组中的位置，b:当前子弹的对象，hero.shoot().length：每次产生最大火力的数组
		}
	}
	public void bullet(FlyingObject[] bullet, int length) {
		for (int i = 0; i<length;i++) {//遍历英雄机子弹数组
			bullet[i].step();
		}
	}
	
	public void Ariplane(List<FlyingObject> enemies) {
		for (FlyingObject e : enemies) {//遍历敌机数组
			e.step();//敌机移动
		}
	}

}
