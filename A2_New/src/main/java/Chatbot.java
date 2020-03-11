
import java.io.File;
import java.util.Scanner;

import org.alicebot.ab.Bot;
import org.alicebot.ab.Chat;
import org.alicebot.ab.History;
import org.alicebot.ab.MagicBooleans;
import org.alicebot.ab.MagicStrings;
import org.alicebot.ab.utils.IOUtils;

public class Chatbot {
	private static final boolean TRACE_MODE = false;
	static String botName = "super";

	public static void main(String[] args) {
		try {
			// update the aiml library so that more conversations can be made available
			Scanner in = new Scanner(System.in);
			System.out.print("Update templates? (Yes/No): ");
			if (in.next().equalsIgnoreCase("Yes"))
				AddAiml.main(null);

			String resourcesPath = getResourcesPath();

			System.out.println("ResourcesPath:" + resourcesPath);

			// the trace mode to trace the current status during the conversation
			// you may turn on it by change the TRACE_MODE value to TRUE
			System.out.println("ResourcesPath:" + resourcesPath);

			// This code is able to trace and print how each info is processed during the
			// conversation
			// by setting TRACE_MODE value to TRUE, the info will be printed

			MagicBooleans.trace_mode = TRACE_MODE;

			// create a chatbot, name is "super", and specify a root path for the bot's
			// files
			// The constructor method will load all the bot's categories, substitutions,
			// configuration files, and set and map definitions.
			Bot bot = new Bot("super", resourcesPath);

			Chat chatSession = new Chat(bot);// Create a client chat session

			// show the stats of bot
			// ex.28545 nodes 22303 singletons 4848 leaves 0 shortcuts 1394 n-ary 28544
			// branches 0.99996495 average branching
			bot.brain.nodeStats();

			String textLine = "";

			while (true) {
				System.out.print("Human : ");
				textLine = IOUtils.readInputTextLine();// get uses input

				if ((textLine == null) || (textLine.length() < 1))
					textLine = IOUtils.readInputTextLine();// get user input

				if ((textLine == null) || (textLine.length() < 1))
					textLine = MagicStrings.null_input; // if textLine is empty, testLine = NORESP

				// if enter q, exit, if enter wq,
				if (textLine.equals("q")) {
					System.exit(0);
				} else if (textLine.equals("wq")) {
					bot.writeQuit();
					System.exit(0);
				} else {
					String request = textLine;
					if (MagicBooleans.trace_mode)// if trace_mode is true, return the status
						System.out.println(
								"STATE=" + request + ":THAT=" + ((History) chatSession.thatHistory.get(0)).get(0)
										+ ":TOPIC=" + chatSession.predicates.get("topic"));

					String response = chatSession.multisentenceRespond(request);// get bot's replies

					while (response.contains("&lt;"))
						response = response.replace("&lt;", "<");
					while (response.contains("&gt;"))
						response = response.replace("&gt;", ">");
					System.out.println("Robot : " + response);// print bot's replies
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// get the resources path
	private static String getResourcesPath() {
		File currDir = new File(".");
		String path = currDir.getAbsolutePath();
		path = path.substring(0, path.length() - 2);
		System.out.println(path);
		String resourcesPath = path + File.separator + "src" + File.separator + "main" + File.separator + "resources";
		return resourcesPath;
	}

}
