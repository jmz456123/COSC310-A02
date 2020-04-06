import java.io.IOException;
import java.net.ServerSocket;

public class ChatBotServer {
	public static void main(String args[]) throws IOException {
		ChatBot aliceBot = new ChatBot(new ServerSocket(1201));
		aliceBot.startConversation();
	}
}
