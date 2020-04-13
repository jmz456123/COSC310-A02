Read me:
Based on Ab.jar: A chat bot that mimics a role of a friend to talk about favorite-food-related and hobby-ralated topics. 
---
A3 submission for UBC Okanagan COSC 310 2019 Winter session. Developed by Larry, Lily, Eric, and Athena. 
Library used is Ab.jar. also referred to as Alicebot, originally composed by Richard Wallace.
You can find more info on the amiml documents at Dr. Wallace's GitHub account https://github.com/drwallace/aiml-en-us-foundation-alice
## Important
Github has a limit of 100mb for uploading files  

You have to import stanford-corenlp-3.9.2-models.jar  

download from https://stanfordnlp.github.io/CoreNLP/download.html  

The needed jar:  

AB.jar (in the file)  

ejml-0.23.jar (in the file)  

stanford-corenlp-3.9.2.jar (in the file)  

stanford-corenlp-3.9.2-javadoc.jar (in the file)  

stanford-corenlp-3.9.2-sources.jar (in the file)  

stanford-corenlp-3.9.2-models.jar (need to download)  


## Quick Start

### Requirements and Installation

The project is based on java 8.
Download all the files in the repository. Then, in your IDE, import a maven project, and select the file: A2_new from the repository.
Download the language toolkit we used in the chatbot for sentiment-detection: Stanford NLP core. Here is the url:https://stanfordnlp.github.io/CoreNLP/download.html
Run ChatBot User.java to start your conversation with the chatbot.
### Example Usage

Let's run file named Chatbot.java in src/main/java, you will see the following
``
Human:
``

Type in words to communicate with the robot.

### More information on Ab.jar

Add the Ab.jar library to your own application. In your Java files, use
``
import org.alicebot.ab.*;
``
grammar of AIML files on https://docs.google.com/document/d/1DWHiOOcda58CflDZ0Wsm1CgP3Es6dpicb4MBbbpwzEk/pub
https://howtodoinjava.com/ai/java-aiml-chatbot-example/


The chatbot we built have 2 general topics, namely food and hobby. They are implemented using AIML,which is as same as A2.
Snippet:YOU ->:Do you like to sing
Corrected Text->:Do you like to sing
YOUR Sentiment: neutral
Robot : Yes, I love to sing. I would like to be a professional singer one day.

For the question of “add a feature that enables your agent to give at least 5 different 
reasonable responses when the user enters something outside the two topics,”since the chatbot is able to handle almost every topics, it is not necessary to give at least 5 different reasonable responses specifically. 
Snippet: 
YOU ->:You are out of topic
Corrected Text->:You are out of topics 
YOUR Sentiment: neutral
Robot : I don't know whether or not I am out of topics.     I am  a unknown.

YOU ->:I do not understand
Corrected Text->:I do not understand
YOUR Sentiment: neutral
Robot : Oh I'm sorry. Perhaps I can explain it again better.

YOU ->:what are you saying
Corrected Text->:what are you saying
YOUR Sentiment: neutral
Robot : I am saying whatever my Dr. Richard S. Wallace taught me to say.

YOU ->:Are you nervous?
Corrected Text->:Are you nervous?
YOUR Sentiment: neutral
Robot : I am calm.


YOU ->:are you tired
Corrected Text->:are you tired
YOUR Sentiment: negative->: I am sorry!
Robot : No I am wide awake and ready for more.


 We built the GUI using methods extended from JFrame. Recent history is viewable and a scroll bar is added to allow for long conversations. The GUI enable the user to type into a nicer interface and  view a recent history of the conversation. 


The open source AIML from Dr. Wallace handles almost all conversations. We used it so that if a user is off topic or an unexpected users' input shows up, the chatbot makes sure conversations flow seamlessly.


	A dictionary is introduced in the project for pattern matching so that when potential spelling mistakes occur, they are handled before being interpreted by the chatbot.
Snippet:
YOU ->:Do you lik to sing
Corrected Text->:Do you like  to sing
YOUR Sentiment: neutral
Robot : Yes, I love to sing. I would like to be a professional singer one day.


The language toolkit we used in the chatbot for sentiment-detection is Stanford NLP core. So far, we can only respond specifically to the sentiment. The chatbot would give a feedback according to different sentiments. For example, if the sentiment is negative, the chatbot would output "negative->: I am sorry!."
 We cannot adjust the response very naturally. Here is the url: https://stanfordnlp.github.io/CoreNLP/download.html
Snippet:
YOU ->:I feel sad
Corrected Text->:I feel sad
YOUR Sentiment: negative->: I am sorry!
Robot : What are you sad about?




The ChatBot.java is modified from the test code of A2. It is rewritten to have functions built in with sockets so that it can act either as a server or as a client. It is able to have a conversation with another chatbot.
snippet:
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
