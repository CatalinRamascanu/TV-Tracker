package saveAndLoad;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class SaveWatchList {

	public static void save(String[][] watchList) throws IOException {

		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(
				"watchList"));

		out.writeObject(watchList);

		out.close();

	}

}
