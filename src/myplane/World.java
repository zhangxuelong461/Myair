package myplane;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class World extends JPanel{
	public static PlayMusic play1=new PlayMusic("ready.wav");
	public static PlayMusic play2=new PlayMusic("s.wav");//2943365587
	public static PlayMusic play3=new PlayMusic("boom.wav");
	public static PlayMusic shoot=new PlayMusic("shoot.wav");
    public static PlayMusic play4=new PlayMusic("menu.wav");

	public static final long serialVersionUID = 1L;// 序列版本号
	public static final int START = 0;// 开始状态
	public static final int RUNNING = 1;// 运行状态
	public static final int PAUSE = 2;// 暂时状态
	public static final int GAME_OVER = 3;// 死亡状态
	private int state = START;// 默认状态
	public static final int WIDTH = 512;// 窗口宽度
	public static final int HEIGHT = 768;// 窗口高度
	public static int score = 0;
	public static int maxscore=0;
	public static int bossIndex = 0;// 控制关卡，切换背景图，boss的切换
	private double bossLife;// boss的生命，用来画boss的血条
	public static int energy = 0;// 能量，控制英雄机的技能发送
	public static int energy2 = 0;// 能量，控制英雄机的技能发送
	private boolean boosflag = true;// 控制boss入场后其他敌机不能入场
	public static Sky sky = new Sky(bossIndex);// 创建天空对象
	public static Hero hero;// 创建英雄机对象
	public static Hero hero2;
	public static boolean twoPerson = false;
	public static boolean onePerson = false;
	private List<FlyingObject> enemies = new LinkedList<FlyingObject>();// 创建敌机集合用来储存所有敌机
	private Bullet[] bullet = {};// 创建英雄机子弹数组
	private Bullet[] bullet2 = {};// 创建英雄机子弹数组
	private BossBullect[] bossBullect = {};// 创建boss子弹数组
	private BigAirplaneBullet[] bigAirplaneBullet= {};
	private LightBullet[] lightBullets;// 创建激光数组
	private Boss boss;// 创建boss
	private Maskant maskant;// 创建英雄机护盾
	private Maskant maskant2;// 创建英雄机护盾
	private Ability[] ability = {};// 创建英雄机大招
	private static int[] row = {};
	private static BufferedImage start;// 开始状态
	private static BufferedImage pause;// 暂停状态
	private static BufferedImage gemeover;// 游戏结束状态
	static {
		start = FlyingObject.loadImage("start.jpg");// 加载开始状态图片
		pause = FlyingObject.loadImage("pause.png");// 加载暂停状态图片
		gemeover = FlyingObject.loadImage("gameover.jpg");// 加载游戏结束状态图片
		play4.playStart();
	}

	public FlyingObject nextOne(int nextOneAction) {// 创建所有敌机对象
		Random rand = new Random();
		if (boosflag) {
			if (nextOneAction % 450 == 0) {// 每45秒创建boss
				boosflag = false;// boss入场，把boosflag设置为false,控制其余敌机不入场
				boss = new Boss(bossIndex%4,twoPerson);
				bossLife = boss.getLife();// 获取boss的初始总血量，后面用来画血条
				return boss;
			}
			int type = rand.nextInt(35);// 用来控制其余敌机入场几率
			if (type < 15) {// 创建小敌机
				return new AirPlane(bossIndex);
			} else if (type < 30) {// 创建大敌机
				return new BigAirPlane(bossIndex,twoPerson);
			} else if (type < 32) {// 创建增加英雄机火力
				return new DoubleFire(bossIndex);
			} else {// 创建增加英雄机生命
				return new Life(bossIndex);
			}
		} else {
			int type = rand.nextInt(5);// 用来控制其余敌机入场几率
			if (type < 3) {// 创建增加英雄机火力
				return new DoubleFire(bossIndex);
			} else {// 创建增加英雄机生命
				return new Life(bossIndex);
			}
		}
	}

	int enemiesAction = 0;// 控制敌机入场的变量

	public void enemiesAction() {// 控制敌机入场
		enemiesAction++;
		if (boosflag) {// 为true表示boss未创建，敌机入场，为false敌机不入场
			if (enemiesAction % 60 == 0) {// 没0.6秒入场一个敌机
				FlyingObject obj = nextOne(enemiesAction);// 调用nextOne方法将enemiesAction传过去控制boss的创建和敌机的创建
				enemies.add(obj);// 将获得的敌机存入集合
			}
		} else if (enemiesAction % 500 == 0) {
			FlyingObject obj = nextOne(enemiesAction);
			enemies.add(obj);// 将获得的奖励存入集合
		}

	}

	public void stepAction() {// 控制移动
		sky.step();// 天空移动
		if(twoPerson) {
			hero2.step();
		}
		if(maskant2!=null) {
			maskant2.step2(hero2.x, hero2.y);
		}
		SteepAction steep = new SteepAction();
		steep.heroBullet(bullet, hero);
		steep.Ariplane(enemies);
		if (twoPerson) {
			steep.heroBullet(bullet2, hero2);
		}
		
		
		if(bigAirplaneBullet.length>0) {
			for (int i = 0; i < bigAirplaneBullet.length; i++) {
				bigAirplaneBullet[i].bigAirplaneBullet(hero,hero2,twoPerson);
			}
		}
		
		steep.bullet(bossBullect, bossBullect.length);
		if (ability.length > 0) {// 判断英雄机大招是否释放
			steep.bullet(ability, ability.length);

		}
	}

	int bulletAction = 0;// 控制英雄机子弹入场的变量

	public void bulletAction() {// 英雄机子弹入场
		bulletAction++;
		if(hero.isLife())
		bullet = (Bullet[]) new BulletEntering().bulletentering(bulletAction, 10, bullet, hero);
		if (twoPerson&&hero2.isLife()) {
			bullet2 = (Bullet[]) new BulletEntering().bulletentering(bulletAction, 10, bullet2, hero2);
		}
	}
	
	int bossbulletAction = 0;// 控制boss子弹入场的变量
	public void bossbulletAction() {// boss子弹入场
		if (boosflag == false) {// 判断boss是否入场，若已入场则产生子弹，否则不产生
			bossbulletAction++;
			bossBullect = (BossBullect[]) new BulletEntering().bulletentering(bossbulletAction, 300, bossBullect, boss);
		}
	}
	
	int bigAirplaneBulletAction=0;
	public void bigAirplaneBullet() {
		for (FlyingObject e : enemies) {
			if(e instanceof BigAirPlane) {
				bigAirplaneBulletAction++;
				BigAirPlane bigAirPlane = (BigAirPlane) e;
				bigAirplaneBullet = (BigAirplaneBullet[]) new BulletEntering().bulletentering(bigAirplaneBulletAction, 100, bigAirplaneBullet, bigAirPlane);
			}
			}
	}
	int liggthIndex = 1;// 控制boss激光入场的时间变量
	boolean liggthflag = false;// 用来关闭激光的

	public void liggthBulletAction() {// 控制boss激光入场
		if (boosflag == false) {// 判断boss是否入场，若已入场则产生激光，否则不产生
			if (boss.y >= 10) {// boss完全入场则开始记时，控制激光入场
				liggthIndex++;
			}
			if (liggthIndex % 1500 == 0) {// 每15秒激光入场
				liggthflag = true;// 开启激光
				liggthIndex = 1;// 将时间重新设置
			}
			if (liggthflag) {// 激光入场
				lightBullets = boss.LightShoot();
				if (liggthIndex % 500 == 0) {// 入场五秒后关闭激光
					liggthflag = false;
					liggthIndex = 0;
				}

			}

		}
	}

	public void outOfBoundsAction() {// 判断是否越界
		List<FlyingObject> enemylives = new LinkedList<FlyingObject>();// 创建一个新的敌机数组
		for (FlyingObject e : enemies) {// 遍历敌机数组
			if (!e.outOfBounds() && !e.isRemove()) {// 如果没有越界并且敌机没有删出
				enemylives.add(e);// 将敌机存入新的敌机数组
			}
		}
		enemies = enemylives;// 将新的敌机数组存入原敌机数组
		
		OutBoundAction out = new OutBoundAction();
		bullet = (Bullet[]) out.outBoundAction(bullet);
		bullet2 = (Bullet[]) out.outBoundAction(bullet2);
		bossBullect = (BossBullect[]) out.outBoundAction(bossBullect);

		if (ability.length > 0) {// 判断大招是否产生
			ability = (Ability[]) out.AbilityOutBoundAction(ability);
		}
		if(bigAirplaneBullet.length>0) {
			bigAirplaneBullet = (BigAirplaneBullet[]) out.outBoundAction(bigAirplaneBullet);
		}
	}

	int hitindex = 0;
	int bossBullectIndex = 0;

	public void bulletBangAction() {//碰撞
		Hit hit = new Hit();
		for (Bullet b : bullet) {
			for (FlyingObject e : enemies) {
				hit.BulletHitAirplane(e, b,boss);
				hit.HeroHitAirplane(e, hero, maskant);

			}
		}
		for (Bullet b : bullet2) {
			for (FlyingObject e : enemies) {
				hit.BulletHitAirplane(e, b,boss);
				hit.HeroHitAirplane(e, hero2, maskant2);
			}
		}

		hit.bossHit(boosflag, liggthflag, lightBullets, bossBullect, maskant, hero);
		if(twoPerson)
		hit.bossHit(boosflag, liggthflag, lightBullets, bossBullect, maskant2, hero2);

		for (Bullet b : bullet) {
			for (BossBullect bossbtl : bossBullect) {
				if (b.isLife() && bossbtl.isLife() && b.hit(bossbtl)) {
					bossBullectIndex++;
					b.goDead();
					if (bossBullectIndex % 3 == 0) {
						bossbtl.goDead();
						play3.playStop();
						play3.playStart();
					}
				}
			}
		}
		for (Bullet b : bullet2) {
			for (BossBullect bossbtl : bossBullect) {
				if (b.isLife() && bossbtl.isLife() && b.hit(bossbtl)) {
					bossBullectIndex++;
					b.goDead();
					if (bossBullectIndex % 3 == 0) {
						bossbtl.goDead();
						play3.playStop();
						play3.playStart();
					}
				}
			}
		}

		for (Ability a : ability) {
			for (BossBullect b : bossBullect) {
				if (b.isLife() && b.hit(a)) {
					b.goDead();
					a.goDead();
					play3.playStop();
					play3.playStart();
				}
			}
		}
		for (Ability a : ability) {
			for (FlyingObject e : enemies) {
				if (e instanceof Boss) {
					Boss b = (Boss) e;
					if (b.isLife() && e.hit(a)) {
						b.subLife();
						a.goDead();
						play3.playStop();
						play3.playStart();
					}
				} else if (!(e instanceof Bee) && e.isLife() && e.hit(a)) {
					e.goDead();
					a.goDead();
					play3.playStop();
					play3.playStart();
				}
			}
		}
		for (Bullet b : bullet) {
			for (BigAirplaneBullet b1 : bigAirplaneBullet) {
				if(b.isLife()&&b1.isLife()&&b.hit(b1)) {
					b.goDead();
					b1.goDead();
					play3.playStop();
					play3.playStart();
					
				}
				if(b1.isLife()&&hero.isLife()&&b1.hit(hero)) {
					hero.subtractDoubleFire();
					hero.subtractLife();
					b1.goDead();
					play3.playStop();
					play3.playStart();
				}
			}
		}
		for (Bullet b : bullet2) {
			for (BigAirplaneBullet b1 : bigAirplaneBullet) {
				if(b.isLife()&&b1.isLife()&&b.hit(b1)) {
					b.goDead();
					b1.goDead();
					play3.playStop();
					play3.playStart();
				}
				if(b1.isLife()&&hero.isLife()&&b1.hit(hero2)) {
					hero2.subtractDoubleFire();
					hero2.subtractLife();
					b1.goDead();
					play3.playStop();
					play3.playStart();
				}
			}
		}
		

		if(boss!=null&&!boss.isRemove())
		BossHit();
		heroGoDead(hero);//
		if(twoPerson) {
		heroGoDead(hero2);
		}
		if(state==GAME_OVER) {
			play2.playStop();
			play4.playStart();
			shoot.playStop();
			RowWrite.rowWrite(score);
		}
	}
	private void heroGoDead(Hero e) {
		if (e.getLife() <= 0) {
			e.goDead();
			play3.playStop();
			play3.playStart();
			if(twoPerson) {
				if(hero.getLife()<=0&&hero2.getLife()<=0) {
					state = GAME_OVER;
				}
			}else {
					state = GAME_OVER;
				}
		}
	}
	
	int abilityAction = 0;
	int abilityIndex = 0;
	boolean abilityFlag = false;

	private void abilityAction() {
		if (abilityFlag) {
			ability = new Ability[20];
			while (true) {
				abilityAction++;
				if (abilityAction % 200 == 0) {
					if (abilityIndex < ability.length) {
						ability[abilityIndex] = new Ability();
						abilityIndex++;
					} else {
						abilityAction = 0;
						abilityIndex = 0;
						abilityFlag = false;
						break;
					}
				}
			}
		}
	}

	int addEnergy = 0;
	private void addEnergy() {
		addEnergy++;
		if (addEnergy % 100 == 0) {
			if (energy < 100&&hero.isLife()) {
				energy += 2;
			}
			
		if(twoPerson&&energy2<100&&hero2.isLife()) {
				energy2+=2;
			}
			}
	}

	public void action() {
		MouseAdapter l = new MouseAdapter() {

			public void mouseMoved(MouseEvent e) {
				if (state == RUNNING) {
					int x = e.getX();
					int y = e.getY();
					hero.moveTo(x, y);
					if (maskant != null) {
						maskant.moveTo(x, y);
					}
				}
			}

			public void mouseExited(MouseEvent e) {
				if (state == RUNNING) {
					play2.playStop();
					play4.playStop();
					shoot.playStop();
					state = PAUSE;//
				}
			}

			public void mouseEntered(MouseEvent e) {
				if (state == PAUSE){
					play2.playSloop();
					state = RUNNING;
				}
			}

			int rowIndex = 0;
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					System.out.println(111);
					if (energy >= 50 && state == RUNNING && hero.isLife()) {
						maskant = new Maskant(hero.x - 55, hero.y - 55);
						energy -= 50;
					}
				} else if (e.getButton() == MouseEvent.BUTTON3) {
					if (energy > 70 && state == RUNNING && hero.isLife()) {
						abilityFlag = true;
						energy -= 70;
					}
				}
				if (e.getX() < 223 && e.getX()>46 && e.getY() > 589&&e.getY()<634
						&&twoPerson == false&&onePerson==false) {
					hero = new Hero(190, 470);
					onePerson = true;
					File.save(rowIndex, hero, hero2, rowIndex, rowIndex, onePerson, twoPerson);
				} else if (e.getX() > 291 &&e.getX()<476 && e.getY() > 587&&e.getY()<636
						&&onePerson==false&&twoPerson == false) {
					hero = new Hero(100, 470);
					hero2 = new Hero(280, 470);
					twoPerson = true;
					File.save(rowIndex, hero, hero2, rowIndex, rowIndex, onePerson, twoPerson);
				}else if (e.getX() > 170 && e.getX()<353 && e.getY()>533 && e.getY() < 579
						&&onePerson==false&&twoPerson == false){
					Read.read();
					
				}else if (e.getX() > 403 && e.getX()<505 && e.getY()>542 && e.getY() < 579
						&&onePerson==false&&twoPerson == false){
					if(rowIndex==0) {
						row = RowReader.rowReader();
						rowIndex++;
					}else {
						row = new int[0];
						rowIndex=0;
					}
					return;
				}else {
					if(state!=GAME_OVER)
					return;
				}
				switch (state) {
				case START:
						state = RUNNING;
						play1.playStart();
						play2.playSloop();
						play4.playStop();
					break;
				case GAME_OVER:
					score = 0;
					bossIndex = 0;
					sky = new Sky(bossIndex);
					hero = new Hero(190, 470);
					enemies.clear();
					bullet = new Bullet[0];
					bossBullect = new BossBullect[0];
					bigAirplaneBullet=new BigAirplaneBullet[0];
					boosflag = true;
					liggthIndex = 1;
					liggthflag = false;
					energy = 0;
					energy2 = 0;
					ability = new Ability[0];
					enemiesAction = 0;
					bossbulletAction = 0;
					bossBullectIndex = 0;
					bossLightIndex = 0;
					bulletAction = 0;
					state = START;
					onePerson = false;
					twoPerson = false;
					break;
				}
				
				
				if(onePerson||twoPerson) {
					row = RowReader.rowReader();
					Arrays.sort(row);
					maxscore = row[row.length-1];
					row = new int[0];
				}

			}
		};
		this.addMouseListener(l);
		this.addMouseMotionListener(l);
		Timer timer = new Timer();
		int intervel = 10;
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				if (state == RUNNING) {
					enemiesAction();
					bulletAction();
					bossbulletAction();
//					bigAirplaneBulletAction();
					bigAirplaneBullet();
					liggthBulletAction();
					abilityAction();
					addEnergy();
					stepAction();
					outOfBoundsAction();
					bulletBangAction();
				}
				repaint();
			}
		}, intervel, intervel);
	}

	int bossLightIndex = 0;
	int maskantIndex = 0;
	int maskant2Index = 0;

	public void paint(Graphics g) {
		try {
			sky.PaintObject(g);
			if(state==RUNNING)
				hero.PaintObject(g);

			if (twoPerson) {
				hero2.PaintObject(g);
				for (Bullet b : bullet2) {
					b.PaintObject(g);
				}
			}
			for (FlyingObject e : enemies) {
				e.PaintObject(g);
			}
			if(bigAirplaneBullet.length>0) {
				for (int i = 0; i < bigAirplaneBullet.length; i++) {
					bigAirplaneBullet[i].PaintObject(g);
				}
			}
			for (Bullet b : bullet) {
				b.PaintObject(g);
			}

			
			g.setFont(new Font("宋体", Font.BOLD, 13));
			g.drawString("生命", 20, 50);
			g.drawRect(50, 43, 101, 11);
			g.drawString("能量", 20, 80);
			g.drawRect(50, 70, 101, 11);
			if (twoPerson && state == RUNNING) {
				g.drawString("生命", 20, 108);
				g.drawRect(50, 96, 101, 11);
				g.drawString("能量", 20, 135);
				g.drawRect(50, 123, 101, 11);
			}
			g.drawString("分数:" + score, 20, 25);

			g.drawString("最高分数:" + maxscore, 400, 25);

			int HeroLife = 100;// 英雄机血条
			if(state == RUNNING)
			if (hero.getLife() > HeroLife * 0.8) {
				g.setColor(Color.GREEN);
				g.fillRect(50, 43, hero.getLife(), 10);
			} else if (hero.getLife() > HeroLife * 0.4) {
				g.setColor(Color.YELLOW);
				g.fillRect(50, 43, hero.getLife(), 10);
			} else {
				g.setColor(Color.RED);
				g.fillRect(50, 43, hero.getLife(), 10);
			}
			if(twoPerson) {
			if (hero2.getLife() > HeroLife * 0.8) {
				g.setColor(Color.GREEN);
				g.fillRect(51, 97, hero2.getLife(), 10);
			} else if (hero2.getLife() > HeroLife * 0.4) {
				g.setColor(Color.YELLOW);
				g.fillRect(51, 97, hero2.getLife(), 10);
			} else {
				g.setColor(Color.RED);
				g.fillRect(51, 97, hero2.getLife(), 10);
			}
			}
			if (boosflag == false) {
				bossLightIndex++;
				for (int i = 0; i < bossBullect.length; i++) {
					bossBullect[i].PaintObject(g);
				}
				if (liggthflag) {
					for (LightBullet b : lightBullets) {
						b.PaintObject(g);
					}
				}

				g.drawRect(boss.x, boss.y - 10, boss.width + 1, 11);
				double b = boss.getLife() / bossLife;// boss血条
				if (boss.getLife() > bossLife * 0.8) {
					g.setColor(Color.GREEN);
					g.fillRect(boss.x, boss.y - 9, (int) (b * boss.width), 10);
				} else if (boss.getLife() > bossLife * 0.4) {

					g.setColor(Color.YELLOW);
					g.fillRect(boss.x, boss.y - 9, (int) (b * boss.width), 10);
				} else {
					g.setColor(Color.RED);
					g.fillRect(boss.x, boss.y - 9, (int) (b * boss.width), 10);
				}
			}

			switch (state) {
			case World.START:
				g.drawImage(start, 0, 0, null);
				break;
			case World.PAUSE:
				g.drawImage(pause, 0, 0, null);
				break;
			case World.GAME_OVER:
				g.drawImage(gemeover, 0, 0, null);
				break;

			default:
				break;
			}

			if(state == RUNNING) {
			if (energy < 20) {
				g.setColor(Color.RED);
				g.fillRect(50, 71, energy, 10);
			} else if (energy < 50) {
				g.setColor(Color.YELLOW);
				g.fillRect(50, 71, energy, 10);
			} else if (energy < 70) {
				g.setColor(Color.CYAN);
				g.fillRect(50, 71, energy, 10);
			} else {
				g.setColor(Color.lightGray);
				g.fillRect(50, 71, energy, 10);
			}
			}
			if(twoPerson&&state==RUNNING) {
			if (energy2 < 20) {
				g.setColor(Color.RED);
				g.fillRect(50, 123, energy2, 10);
			} else if (energy2 < 50) {
				g.setColor(Color.YELLOW);
				g.fillRect(50, 123, energy2, 10);
			} else if (energy2 < 70) {
				g.setColor(Color.CYAN);
				g.fillRect(50, 123, energy2, 10);
			} else {
				g.setColor(Color.lightGray);
				g.fillRect(50, 123, energy2, 10);
			}
			}
			if (maskant != null) {
				maskantIndex++;
				maskant.PaintObject(g);
				if (maskantIndex % 500 == 0) {
					maskant = null;
					maskantIndex = 0;
				}
			}
			if (maskant2 != null) {
				maskant2Index++;
				maskant2.PaintObject(g);
				if (maskant2Index % 500 == 0) {
					maskant2 = null;
					maskant2Index = 0;
				}
			}

			if (ability.length > 0) {
				for (Ability a : ability) {
					a.PaintObject(g);
				}
			}
			
			if (state == GAME_OVER||row.length>0) {
				row = RowReader.rowReader();
				g.setFont(new Font("微软雅黑", Font.PLAIN, 18));
				g.setColor(Color.CYAN);
				g.drawString("排行榜", 220, 180);
				for (int i = row.length - 1; i >= 0; i--) {
					if (i==0) {
						g.drawString((row.length - i) + "：" + row[i], 210, 200 + (row.length - i) * 30);
					} else {
						g.drawString("  " + (row.length - i) + "：" + row[i], 210, 200 + (row.length - i) * 30);
					}
				}
			}
		} catch (Exception e) {
		}

	}

	private void BossHit() {
			if ( boss!=null&&boss.getLife() <= 0 ) {
				score+=boss.getScore();
				enemies.clear();
				bossIndex++;
				bullet = new Bullet[0];
				bossBullect = new BossBullect[0];
				boosflag = true;
				liggthIndex = 1;
				liggthflag = false;
				ability = new Ability[0];
				enemiesAction = 0;
				bossbulletAction = 0;
				bossBullectIndex = 0;
				bossLightIndex = 0;
				bulletAction = 0;
				boss=null;
				if(!(bossIndex>3)) {
					sky = new Sky(bossIndex);
				}
				if(twoPerson) {
					if(hero.isRemove()) {
						hero = new Hero(100, 470);
						hero.setLife(25);
					}else if(hero2.isRemove()){
						hero2 = new Hero(280, 470);
						hero2.setLife(25);
					}
					}
				File.save(bossIndex, hero, hero2, energy, energy2, onePerson, twoPerson);
		}
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		World world = new World();
		frame.add(world);
		frame.setSize(WIDTH, HEIGHT);
		// frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setAlwaysOnTop(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.addKeyListener(new KeyListener(){
			@Override
			public void keyPressed(KeyEvent e) {
				
				if(world.state==RUNNING){
					int i = e.getKeyCode();
					switch(i){
					case KeyEvent.VK_UP		: Hero.up = true;break;
					case KeyEvent.VK_DOWN	: Hero.down = true;break;
					case KeyEvent.VK_LEFT	: Hero.left = true;break;
					case KeyEvent.VK_RIGHT	: Hero.right = true;break;
					case KeyEvent.VK_SHIFT:
						if (World.energy2 >= 50 && world.state == RUNNING && World.hero2.isLife()) {
							world.maskant2 = new Maskant(World.hero2.x - 55, World.hero2.y - 55);
							World.energy2 -= 50;
						}
						break;
					case KeyEvent.VK_CONTROL:
						if (World.energy2 > 70 && world.state == RUNNING && World.hero2.isLife()) {
							world.abilityFlag = true;
							World.energy2 -= 70;
						}
						break;
					}
				}
			}
			@Override
			public void keyReleased(KeyEvent e) {
				if(world.state==RUNNING){
					switch(e.getKeyCode()){
					case KeyEvent.VK_UP		: Hero.up = false;break;
					case KeyEvent.VK_DOWN	: Hero.down = false;break;
					case KeyEvent.VK_LEFT	: Hero.left = false;break;
					case KeyEvent.VK_RIGHT	: Hero.right = false;break;
					}
				}
			}
			public void keyTyped(KeyEvent e) {
			}
		});
		world.action();

	}

}
