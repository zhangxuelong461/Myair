package myplane;

import java.io.RandomAccessFile;

public class File {
	public static void save(int bossIndex, Hero hero1,Hero hero2,int energy, int energy2,boolean b1, boolean b2) {
		try {
			RandomAccessFile raf = new RandomAccessFile("user.dat", "rw");
			raf.seek(0);
			raf.writeInt(bossIndex);
			if(b1) {
				raf.writeInt(1);
			}else {
				raf.writeInt(2);
			}
			raf.writeInt(hero1.getLife());
			if(b2) {
				raf.writeInt(hero2.getLife());
			}
			raf.writeInt(energy);
			raf.writeInt(energy2);

			raf.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
