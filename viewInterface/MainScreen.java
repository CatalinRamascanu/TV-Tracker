package viewInterface;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import queryPackage.EpisodeList;
import queryPackage.Query;
import saveAndLoad.LoadWatchList;
import saveAndLoad.SaveWatchList;

public class MainScreen extends JFrame {
	List<String> lines;
	final static JTextField serialNameField = new JTextField(20);
	JPanel moviePanel = new JPanel(new GridLayout(0, 1));
	JPanel searchPanel = new JPanel(new GridLayout(0, 2));
	JTable table;
	String[][] watchListRowData;
	static int watchListNumber = 0;

	public MainScreen() {
		super("Tema facultativa");

		watchListRowData = LoadWatchList.load();
		watchListNumber = getMatrixSize();

		setLayout(new GridBagLayout());

		GridBagConstraints c = new GridBagConstraints();
		c.gridwidth = 2;
		c.gridheight = 1;
		c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 1;
		c.insets = new Insets(5, 5, 5, 5);

		JLabel serialNameLabel = new JLabel("Numele serialului: ");

		JButton searchButton = new JButton("Cauta");
		JButton addToWatchList = new JButton(
				"Add selected serial to watch list");
		JButton viewWatchList = new JButton("View watch list");

		searchPanel.add(serialNameLabel);
		searchPanel.add(serialNameField);
		searchPanel.add(searchButton);

		JScrollPane movieScrollPane = new JScrollPane(moviePanel);
		movieScrollPane.setPreferredSize(new Dimension(500, 200));
		add(searchPanel, c);

		c.gridy = 2;
		c.gridheight = 5;
		add(movieScrollPane, c);

		c.gridy = 7;
		c.gridheight = 1;
		c.gridwidth = 1;
		add(addToWatchList, c);
		c.gridx = 1;
		add(viewWatchList, c);

		pack();

		// adding button listeners

		searchButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				Query urlReader = new Query();
				lines = urlReader.start(serialNameField.getText());
				moviePanel.setSize(200, 400);
				createPanel();
			}
		});

		addToWatchList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int row = table.getSelectedRow();

				String name = (String) table.getValueAt(row, 0);
				String country = (String) table.getValueAt(row, 1);
				String showId = (String) table.getValueAt(row, 2);
				String status = (String) table.getValueAt(row, 3);

				watchListRowData[watchListNumber][0] = name;
				watchListRowData[watchListNumber][1] = country;
				watchListRowData[watchListNumber][2] = showId;
				watchListRowData[watchListNumber++][3] = status;

				try {
					SaveWatchList.save(watchListRowData);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});

		viewWatchList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				WatchList watch = new WatchList(watchListRowData);
				watch.setVisible(true);
			}
		});

	}

	public int getMatrixSize() {
		int size = 0;

		for (int i = 0; i < 1000; i++) {
			if (watchListRowData[i][0] != null)
				size += 1;
			else
				return size;
		}
		return size;
	}

	public void createPanel() {
		moviePanel.removeAll();
		List<String> serialShowId = new LinkedList<String>();
		List<String> serialName = new LinkedList<String>();
		List<String> serialCountry = new LinkedList<String>();
		List<String> serialStatus = new LinkedList<String>();

		for (String ext : lines) {
			if (ext.contains("<showid>")) {
				serialShowId.add(ext.substring(8, ext.indexOf("</")));
			}

			if (ext.contains("<name>")) {
				serialName.add(ext.substring(6, ext.indexOf("</")));
			}

			if (ext.contains("<country>")) {
				serialCountry.add(ext.substring(9, ext.indexOf("</")));
			}

			if (ext.contains("<status>")) {
				serialStatus.add(ext.substring(8, ext.indexOf("</")));
			}

		}

		String[] columnNames = { "show name", "show id", "show country",
				"show status" };
		String[][] rowData = new String[1000][5];

		for (int i = 0; i < serialCountry.size(); i++) {

			String name = serialName.get(i);
			String showId = serialShowId.get(i);
			String country = serialCountry.get(i);
			String status = serialStatus.get(i);

			rowData[i][0] = name;
			rowData[i][1] = country;
			rowData[i][2] = showId;
			rowData[i][3] = status;
		}
		table = new JTable(rowData, columnNames);

		// table.setEnabled(false);
		moviePanel.add(table);
		moviePanel.revalidate();
		moviePanel.repaint();
	}

}
