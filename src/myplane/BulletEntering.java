package myplane;

import java.util.Arrays;

public class BulletEntering {

	public FlyingObject[] bulletentering(int bulletAction,int remainder,FlyingObject[] bullet,FlyingObject fly){
		FlyingObject[] bs = null;
		if(fly instanceof Boss) {
			Boss o = (Boss) fly;
			bs = o.shoot();//创建一个新的英雄机子弹数组用来获取子弹
		}else if(fly instanceof Hero){
			Hero o = (Hero) fly;
			bs = o.shoot();
		}else{
		BigAirPlane o = (BigAirPlane) fly;
		bs = o.shoot();
	}
			
		if(bulletAction%remainder==0) {//每0.2秒子弹入场
			bullet = Arrays.copyOf(bullet, bullet.length + bs.length);//给原来的英雄机子弹数组扩容，扩容的大小是新子弹数组的长度
			System.arraycopy(bs, 0, bullet, bullet.length - bs.length, bs.length);//将新英雄机子弹的数据复制给原英雄机子弹数组
		}
		return bullet;
	}
}
