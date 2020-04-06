
import java.io.File;

import org.alicebot.ab.Bot;
import org.alicebot.ab.MagicBooleans;

// after add Aiml files, run this class, it will load the file
public class AddAiml {

	private static final boolean TRACE_MODE = false;
	static String botName = "super";

	public static void main(String[] args) {
		try {

			String resourcesPath = getResourcesPath();
			System.out.println(resourcesPath);

			// the trace mode to trace the current status during the conversation
			// you may turn on it by change the TRACE_MODE value to TRUE
			MagicBooleans.trace_mode = TRACE_MODE;

			// create a chatbot, name is "super", and specify a root path for the bot's
			// files
			// The constructor method will load all the bot's categories, substitutions,
			// configuration files, and set and map definitions.
			Bot bot = new Bot("super", resourcesPath);

			// write AIML Files
			bot.writeAIMLFiles();

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
