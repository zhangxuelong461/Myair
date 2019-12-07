package myplane;

import java.io.RandomAccessFile;

public class Read {

	public static void read() {
		RandomAccessFile raf;
		try {
			raf = new RandomAccessFile("user.dat","r");
			int len = raf.readInt();
			World.bossIndex = len;
			System.out.println(len);
			if((len = raf.readInt())==1) {
				World.onePerson = true;
				World.hero = new Hero(190, 470);
				if(!(World.bossIndex>3)) {
					World.sky = new Sky(World.bossIndex);
				}else {
					World.sky = new Sky(3);
				}
				System.out.println(len);
			}else {
				World.twoPerson = true;
				World.hero = new Hero(100, 470);
				World.hero2 = new Hero(280, 470);
				if(!(World.bossIndex>3)) {
					World.sky = new Sky(World.bossIndex);
				}else {
					World.sky = new Sky(3);
				}
			}
			if(World.onePerson) {
				len = raf.readInt();
				World.hero.setLife(len);
			}else if(World.twoPerson){
				len = raf.readInt();
				World.hero.setLife(len);
				System.out.println(len);
				len = raf.readInt();
				System.out.println(len);
				World.hero2.setLife(len);
			}
			
			len = raf.readInt();
			World.energy = len;
			len = raf.readInt();
			World.energy2 = len;
			
			raf.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		read();
	}
}
