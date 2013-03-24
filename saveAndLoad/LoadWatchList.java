package saveAndLoad;

import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class LoadWatchList {
	public static String[][] load() {
		try {
			ObjectInputStream readLib = new ObjectInputStream(
					new FileInputStream("watchList"));

			String[][] watchList = (String[][]) readLib.readObject();
			return watchList;
		} catch (Exception e) {
			return new String[1000][5];
		}

	}
}
