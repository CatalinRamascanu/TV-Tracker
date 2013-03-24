package viewInterface;

import java.awt.GridLayout;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import queryPackage.ShowInfo;

public class MoreInfo extends JFrame {
	public MoreInfo(String serialId) {
		super("Mai multe informatii");
		add(moreInfoPanel(serialId));

		pack();
	}

	public JPanel moreInfoPanel(String id) {
		JPanel moreInfo = new JPanel(new GridLayout(0, 2));

		String name = "";
		String status = "";
		String link = "";
		String hour = "";
		String network = "";
		String day = "";

		ShowInfo query = new ShowInfo();
		List<String> read = query.start(id);

		for (String ext : read) {
			if (ext.contains("<status>")) {
				status = ext.substring(ext.indexOf("<status>") + 8,
						ext.indexOf("</status>"));
			}

			if (ext.contains("<showname>")) {
				name = ext.substring(ext.indexOf("<showname>") + 10,
						ext.indexOf("</showname>"));
			}

			if (ext.contains("<showlink>")) {
				link = ext.substring(ext.indexOf("<showlink>") + 10,
						ext.indexOf("</showlink>"));
			}

			if (ext.contains("<airtime>")) {
				hour = ext.substring(ext.indexOf("<airtime>") + 9,
						ext.indexOf("</airtime>"));
			}

			if (ext.contains("<network")) {
				network = ext.substring(23, ext.indexOf("</network>"));
			}

			if (ext.contains("<airday>")) {
				day = ext.substring(ext.indexOf("<airday>") + 8,
						ext.indexOf("</airday>"));
			}
		}

		moreInfo.add(new JLabel("Nume serial: "));
		moreInfo.add(new JLabel(name));

		moreInfo.add(new JLabel("Status serial: "));
		moreInfo.add(new JLabel(status));

		moreInfo.add(new JLabel("Link serial: "));
		moreInfo.add(new JLabel(link));

		moreInfo.add(new JLabel("Ora serial: "));
		moreInfo.add(new JLabel(hour));

		moreInfo.add(new JLabel("Zi serial: "));
		moreInfo.add(new JLabel(day));

		moreInfo.add(new JLabel("Retea serial: "));
		moreInfo.add(new JLabel(network));

		return moreInfo;
	}
}
