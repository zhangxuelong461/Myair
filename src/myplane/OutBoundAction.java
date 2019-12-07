package myplane;

import java.util.Arrays;

public class OutBoundAction {
	public FlyingObject[] outBoundAction(FlyingObject[] bullet) {
		int index = 0;//控制新英雄机子弹数组的下标
		FlyingObject[] bulletFire = {};
		if(bullet instanceof Bullet[]) {
			bulletFire= new Bullet[bullet.length];
		}else if(bullet instanceof BossBullect[]){
			bulletFire= new BossBullect[bullet.length];
		}else if(bullet instanceof Ability[]){
			bulletFire= new Ability[bullet.length];
		}else {
			bulletFire= new BigAirplaneBullet[bullet.length];
		}
		for (int i = 0; i < bullet.length; i++) {//判断英雄机子弹是否越界。过程同上
			FlyingObject e = bullet[i];
			if (!e.outOfBounds()) {
				bulletFire[index] = bullet[i];
				index++;
			}
		}
		bullet = Arrays.copyOf(bulletFire, index);
		return bullet;
	}
	public FlyingObject[] AbilityOutBoundAction(FlyingObject[] bullet) {
		int index = 0;//控制新英雄机子弹数组的下标
		FlyingObject[] bulletFire = {};
			bulletFire= new Ability[bullet.length];
		for (int i = 0; i < bullet.length; i++) {//判断英雄机子弹是否越界。过程同上
			FlyingObject e = bullet[i];
			if (!e.outOfBounds()&&!e.isRemove()) {
				bulletFire[index] = bullet[i];
				index++;
			}
		}
		bullet = Arrays.copyOf(bulletFire, index);
		return bullet;
	}

}
