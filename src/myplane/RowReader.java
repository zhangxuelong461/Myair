package myplane;

import java.io.RandomAccessFile;
import java.util.Arrays;

public class RowReader {
	public static int[] rowReader() {
		int[] row = new int[10];
		try {
			RandomAccessFile raf = new RandomAccessFile("score.dat", "r");
			for (int i = 0; i < raf.length()/4; i++) {
				row[i] = raf.readInt();
			}
			Arrays.sort(row);
			raf.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return row;
	}
	public static void main(String[] args) {
		rowReader();
	}
}
