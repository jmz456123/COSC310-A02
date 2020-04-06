import java.awt.BorderLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import javax.management.loading.PrivateClassLoader;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.alicebot.ab.Bot;
import org.alicebot.ab.Chat;
import org.alicebot.ab.History;
import org.alicebot.ab.MagicBooleans;
import org.alicebot.ab.MagicStrings;

public class ChatBot extends JFrame {
	private final boolean TRACE_MODE = false;
	private String botName = "super";
	private Bot bot;
	private Chat chatSession;
	private JTextArea chatArea = new JTextArea();
	private JTextField chatBox = new JTextField();
	private Socket s = null;
	private ServerSocket ss = null;
	private JPanel jp = new JPanel();
	private JScrollPane scroll;
	public ChatBot() {// default constructor setting up the GUI
		iniGUI();
		String resourcesPath = getResourcesPath();
		MagicBooleans.trace_mode = TRACE_MODE;
		bot = new Bot("super", resourcesPath);
		chatSession = new Chat(bot);
		bot.brain.nodeStats();// this sets up the chatBot for conversation
	}

	public void startConversation() {
		chatBox.addActionListener(new ActionListener() {// similar to a while loop, this method keeps asking user for input
			   public void actionPerformed(ActionEvent arg0) {
			    String correctedText = "", text = "";
			    if (s == null)// read from user
			     text = chatBox.getText();// similar to in.nextLine() from Scanner
			    else// read from server
			     try (BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));) {
			      // initialize streams for reading
			      text = in.readLine();
			     } catch (IOException e) {
			      e.printStackTrace();
			     }
			    correctedText = getCorrectedText(text);
			    chatArea.append("\nYOU ->:" + text + "\nCorrected Text->:" + correctedText + "\n");
			    // chatArea.append prints thing on the screen
			    chatBox.setText("");// flush text away
			    // ------
			     chatArea.append("YOUR Sentiment: " + getSentiment(correctedText) + "\n");
			    // print sentiment suggestion
			    chatArea.append(answerUser(correctedText));
			   }
			  });
	}

	public ChatBot(Socket s) {// these two constructors allows initializations for client or server
		this();
		this.s = s;
	}

	public ChatBot(ServerSocket ss) {//
		this();
		this.ss = ss;
		try {
			s = ss.accept();
			PrintWriter out = new PrintWriter(s.getOutputStream());
			out.println("Hi, I'm Larry.");// start conversation
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void iniGUI() {// initialize the frame using JFrame
		chatArea.setLineWrap(true); //Set line wrap 
		chatArea.setWrapStyleWord(true); //// set to wrap the word as a whole (i.e. not cut the word in half)
		//create a scroll with Jtextarea
		scroll= new JScrollPane(chatArea,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		setResizable(false);
		setLayout(null);
		setSize(500, 600);
		
		setTitle("ChatBot-Group16"); // set title of the window
		
		add(scroll); // add scroll and Jtextarea to window
		add(chatBox); // add JtextField to window
		// ignore the above, use the four below to manipulate GUI
		add(scroll,BorderLayout.CENTER); // set scroll layout
		scroll.setBounds(10, 10, 470, 500); // set scroll postion 
		chatBox.setSize(470, 30); // set JtextFied size
		chatBox.setLocation(10, 520); // set JtextFied location
		
		
		
	}

	private String answerUser(String textLine) {// return answers based only on Ab.jar
		if ((textLine == null) || (textLine.length() < 1))
			textLine = MagicStrings.null_input;
		if (textLine.equals("q")) {
			if (textLine.equals("wq"))
				bot.writeQuit();
			if (s != null)
				try {
					s.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			System.exit(0);
		} else {// if the input is valid
			String request = textLine;
			if (MagicBooleans.trace_mode)
				System.out.println("STATE=" + request + ":THAT=" + ((History) chatSession.thatHistory.get(0)).get(0)
						+ ":TOPIC=" + chatSession.predicates.get("topic") + "\n");
			// give feedback for user inputs
			String response = chatSession.multisentenceRespond(request);
			while (response.contains("&lt;"))
				response = response.replace("&lt;", "<");
			while (response.contains("&gt;"))
				response = response.replace("&gt;", ">");
			if (s != null)
				try (PrintWriter out = new PrintWriter(s.getOutputStream());) {
					out.println(response);
				} catch (IOException e) {
					e.printStackTrace();
				}
			return "Robot : " + response + "\n";// return results GUI
		}
		return "Unexpected Error";// not going to reach it
	}

	// get Sentiment, return a String with sentiment and reaction
	// 0:very negative, 1:negative 2:neutral 3:positive and 4:very positive.
	private String getSentiment(String text) {
		String Sentiment = "";
		int answer = -1;
		
		// input the text, and output the sentiment into answer
		// 0:very negative, 1:negative 2:neutral 3:positive and 4:very positive.
		ArrayList<String> tweets = new ArrayList<String>();
		tweets.add(text);
		NLP.init();
		for (String tweet : tweets) {
			answer = NLP.findSentiment(tweet);
		}
		
		// Specify the output for each case
		switch (answer) {
		case 0:
			Sentiment = "very negative->: Shut up!";
			break;
		case 1:
			Sentiment = "negative->: I am sorry!";
			break;
		case 2:
			Sentiment = "neutral";
			break;
		case 3:
			Sentiment = "positive->: Thank you!";
			break;
		case 4:
			Sentiment = "very positive->: Thank you so much! You are such a good person!";
			break;

		default:
			break;
		}
		
		return Sentiment; // return case
	}

	
	// get the uncrrected text, and correct each word in the text, then return the corrected text
	private String getCorrectedText(String text) {
		SpellCheck spellCheck = null;
		try {
			spellCheck = new SpellCheck("corpus.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
		int index = 0, dif;
		String[] splited = text.split("\\s+");
		String[] splited2 = text.split("\\s+");
		String correctWord = "";
		String outputString = "";
		for (int i = 0; i < splited.length; i++) {
			char[] charText = text.toCharArray();
			correctWord = spellCheck.spellCheck(splited[i]);
			if (!correctWord.equals(splited2[i])) {
				index = text.indexOf(splited2[i]);
				if (correctWord.length() == splited2[i].length())
					for (int j = 0; j < splited2[i].length(); j++)
						charText[index + j] = correctWord.charAt(0 + j);
				else {
					dif = correctWord.length() - splited2[i].length();
					Math.abs(dif);
					for (int j = 0; j < index; j++)
						outputString += charText[j];
					outputString += correctWord + " ";
					for (int x = index + splited2[i].length(); x < charText.length; x++)
						outputString += charText[x];
					text = outputString;
					outputString = "";
				}
			}
		}
		return text;

	}
	
	// get ResourcesPath 
	private String getResourcesPath() {
		File currDir = new File(".");
		String path = currDir.getAbsolutePath();
		path = path.substring(0, path.length() - 2);
		System.out.println(path);
		String resourcesPath = path + File.separator + "src" + File.separator + "main" + File.separator + "resources";
		return resourcesPath;
	}
}
