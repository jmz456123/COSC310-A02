

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.alicebot.ab.Bot;
import org.alicebot.ab.Chat;
import org.alicebot.ab.History;
import org.alicebot.ab.MagicBooleans;
import org.alicebot.ab.MagicStrings;
import org.alicebot.ab.utils.IOUtils;

public class ChatbotThree extends JFrame {
	private static final boolean TRACE_MODE = false;
	static String botName = "super";
	private static Bot bot;
	private static Chat chatSession;
	private static JTextArea chatArea = new JTextArea();
	private static JTextField chatBox = new JTextField();

	public ChatbotThree() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		setResizable(false);
		setLayout(null);
		setSize(500, 600);
		setTitle("ChatBot");
		add(chatArea);
		add(chatBox);
		chatArea.setSize(470, 500);
		chatArea.setLocation(10, 10);
		chatBox.setSize(470, 30);
		chatBox.setLocation(10, 520);
	}

	private static void bot1(String text) {
		chatArea.append("-" + "\n");
	}

	public static void main(String[] args) {
		try {
			// AddAiml.main(null);//invoke methods in addaiml.java

			String resourcesPath = getResourcesPath();
			// System.out.println(resourcesPath);// print resourcePath

			MagicBooleans.trace_mode = TRACE_MODE;
			bot = new Bot("super", resourcesPath);
			chatSession = new Chat(bot);
			bot.brain.nodeStats();
			String textLine = "";

			new ChatbotThree();
			chatBox.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					String text = chatBox.getText();
					chatArea.append("YOU ->:" + text + "\n");
					chatBox.setText("");
					// chatArea.append(chatString(bot1, chatSession, text));
					answerUser(text);
				}
			});

			//
			// while (true) {
			// System.out.print("Human : ");
			// textLine = IOUtils.readInputTextLine();
			// answerUser(textLine);
			//
			// }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void answerUser(String textLine) {
		if ((textLine == null) || (textLine.length() < 1))
			textLine = MagicStrings.null_input;
		if (textLine.equals("q")) {
			System.exit(0);
		} else if (textLine.equals("wq")) {
			bot.writeQuit();
			System.exit(0);
		} else {
			String request = textLine;
			if (MagicBooleans.trace_mode)
				chatArea.append("STATE=" + request + ":THAT=" + ((History) chatSession.thatHistory.get(0)).get(0)
						+ ":TOPIC=" + chatSession.predicates.get("topic") + "\n");
			String response = chatSession.multisentenceRespond(request);
			while (response.contains("&lt;"))
				response = response.replace("&lt;", "<");
			while (response.contains("&gt;"))
				response = response.replace("&gt;", ">");
			chatArea.append("Robot : " + response + "\n");
		}
	}

	private static String getResourcesPath() {
		File currDir = new File(".");
		String path = currDir.getAbsolutePath();
		path = path.substring(0, path.length() - 2);
		System.out.println(path);
		String resourcesPath = path + File.separator + "src" + File.separator + "main" + File.separator + "resources";
		return resourcesPath;
	}
}