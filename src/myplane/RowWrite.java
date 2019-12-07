package myplane;

import java.io.RandomAccessFile;
import java.util.Arrays;

public class RowWrite {

	public static void rowWrite(int score) {
		try {
			RandomAccessFile raf = new RandomAccessFile("score.dat", "rw");
			int[] row = new int[10];
//			raf.writeInt(3);
//			raf.writeInt(2);
			for (int i = 0; i < raf.length()/4; i++) {
				row[i] = raf.readInt();
			}
			Arrays.sort(row);
				if(row[0]<score) {
					row[0] = score;
			}
			raf.seek(0);
			for (int i = 0; i < row.length; i++) {
				raf.writeInt(row[i]);
			}
			raf.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		rowWrite(2);
	}
}
