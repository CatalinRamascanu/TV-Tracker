package queryPackage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

public class EpisodeList {

	private static final String SEARCH_TEMPLATE = "http://services.tvrage.com/feeds/episode_list.php?sid=%s";

	public List<String> start(String show) {
		List<String> lines = null;
		try {
			URL showURL = new URL(String.format(SEARCH_TEMPLATE, show));
			// Se deschide conexiunea de la URL-ul specificat.
			HttpURLConnection connection = (HttpURLConnection) showURL
					.openConnection();
			lines = readFromConnection(connection);
		} catch (MalformedURLException e) {
			System.err.println("URL prost formatat: " + e.getMessage());
		} catch (IOException e) {
			System.err
					.println("Nu s-a putut deschide o conexiune cu linkul dorit");
		}
		return lines;
	}

	private List<String> readFromConnection(HttpURLConnection connection)
			throws IOException {
		// Se obtine input stream-ul asociat conexiunii
		InputStream urlInputStream = connection.getInputStream();
		InputStreamReader urlInputReader = new InputStreamReader(urlInputStream);
		BufferedReader connectionReader = new BufferedReader(urlInputReader);
		List<String> lines = new LinkedList<String>();
		String line;
		while ((line = connectionReader.readLine()) != null) {
			lines.add(line);
		}
		return lines;
	}
}
