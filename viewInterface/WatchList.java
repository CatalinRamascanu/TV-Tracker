package viewInterface;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import queryPackage.EpisodeList;

public class WatchList extends JFrame {
	JTable table;

	public WatchList(String[][] rowData) {
		super("Watch List");
		setLayout(new GridLayout(0, 1));

		JPanel watchListTable = new JPanel(new FlowLayout());
		JButton moreDetails = new JButton("Mai mult");
		String[] columnNames = { "show name", "show id", "show country",
				"show status", "next episode" };

		for (int i = 0; i < MainScreen.watchListNumber; i++) {
			rowData[i][4] = nextEpisodeDate(rowData[i][2]);
		}

		table = new JTable(rowData, columnNames);

		watchListTable.add(table);

		JScrollPane watchListScrollPane = new JScrollPane(watchListTable);
		watchListScrollPane.setPreferredSize(new Dimension(500, 200));

		add(watchListScrollPane);
		add(moreDetails);
		pack();
		// Event listeners
		moreDetails.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {
				int row = table.getSelectedRow();
				MoreInfo infoWindow = new MoreInfo((String) table.getValueAt(
						row, 2));
				infoWindow.setVisible(true);
			}

		});
	}

	public String nextEpisodeDate(String serialId) {
		List<String> nextEpisodeList = new LinkedList<String>();
		String nextEpisodeDate = "Terminat";
		String timeStamp = new SimpleDateFormat("yyyy-MM-dd").format(Calendar
				.getInstance().getTime());

		EpisodeList urlReader = new EpisodeList();
		nextEpisodeList = urlReader.start(serialId);

		for (String ext : nextEpisodeList) {
			if (ext.contains("<airdate>")) {

				String currentDate = ext
						.substring(ext.indexOf("<airdate>") + 9,
								ext.indexOf("</airdate>"));
				if (timeStamp.compareTo(currentDate) < 0)
					return currentDate;
			}
		}
		return nextEpisodeDate;

	}
}
