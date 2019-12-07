package myplane;

public class Hit {

	public void BulletHitAirplane(FlyingObject e, Bullet b, Boss boss) {
		if (e.isLife() && b.isLife() && e.hit(b)) {
			if (e instanceof Boss) {
				Boss o = (Boss) e;
				if (boss.y >= 10) {
					o.subtractLife();
					b.goDead();
				}
			}else if (!(e instanceof Bee)) {
				if (e instanceof BigAirPlane) {
					BigAirPlane b1 = (BigAirPlane) e;
					b1.subtractLife();
					b.goDead();
					if(b1.getLife()<=0) {
						b1.goDead();
						World.play3.playStop();
						World.play3.playStart();
						World.score+=b1.getScore();
					}
				}else {
					e.goDead();
					b.goDead();
					World.play3.playStop();
					World.play3.playStart();
					if(e instanceof Award)
						World.score+=((Award) e).getScore();
				}
			}
		}
	}
	
	
	int hitindex = 0;
	static int bossBullectIndex = 0;
	public void HeroHitAirplane(FlyingObject e,Hero hero,Maskant maskant) {
		if (e.isLife() && hero.isLife() && e.hit(hero)) {
			if (e instanceof Boss) {
				hitindex = 1;
				bossBullectIndex++;
				Boss o = (Boss) e;
				if(hitindex==1&&bossBullectIndex%100==0) {
					if (!(maskant != null && maskant.hit(e))) {
						hero.subLife();
						hero.subtractDoubleFire();
					}
					o.subtractLife();
				}
			} else if (!(e instanceof Bee)) {
				if (maskant != null && maskant.hit(e)) {
					e.goDead();
					World.play3.playStop();
					World.play3.playStart();
					World.score+=((Award) e).getScore();
				} else {
					e.goDead();
					World.play3.playStop();
					World.play3.playStart();
					hero.subtractLife();
					hero.subtractDoubleFire();
					World.score+=((Award) e).getScore();
				}
			} else if (e instanceof Life) {
				e.goDead();
				World.play3.playStop();
				World.play3.playStart();
				hero.addLife();
				World.score+=((Life) e).getScore();
				
			} else if (e instanceof DoubleFire) {
				e.goDead();
				World.play3.playStop();
				World.play3.playStart();
				hero.adddoubleFire();
				World.score+=((DoubleFire) e).getScore();
			}else {
				bossBullectIndex = 0;
				hitindex=0;
			}
		}
	}
	
	public void bossHit(boolean boosflag,boolean liggthflag,LightBullet[] lightBullets, BossBullect[] bossBullect, Maskant maskant,Hero hero){
		if (boosflag == false) {
			for (BossBullect b : bossBullect) {
				if (maskant != null && b.isLife() && maskant.hit(b)) {
					b.goDead();
					World.play3.playStop();
					World.play3.playStart();
				} else if (b.isLife() && hero.isLife() && b.hit(hero)) {
					b.goDead();
					hero.subLife();
					hero.subtractDoubleFire();
					World.play3.playStop();
					World.play3.playStart();
				}
			}
			if (liggthflag) {
				for (LightBullet b : lightBullets) {
					if (b.isLife() && hero.isLife() && b.hit(hero)) {
						if (!(maskant != null && b.isLife() && maskant.hit(b))) {
							hero.subtractLife();
							hero.subtractDoubleFire();
						}
					}
				}
			}

		}
	}
}
