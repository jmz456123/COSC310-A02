import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ChatBotClient {
	public static void main(String args[]) throws UnknownHostException, IOException {
		  
		ChatBot aliceBot = new ChatBot(new Socket("10.33.28.62", 1201)); // change your current ip address
		  aliceBot.startConversation();
		 }
}
