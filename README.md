# COSC310-A02

Based on Ab.jar: A chat bot that mimics a role of a friend to talk about favorite-food-related topics 
---
A2 submission for UBC Okanagan COSC 310 2019 Winter session. Developed by Larry, Lily, Eric, and Athena. 
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

stanford-corenlp-3.9.2-models.jar (need download)  


## Quick Start

### Requirements and Installation

The project is based on java 8.
Download all the files in the repository. Then, in your IDE, import a maven project, and select the file: A2_new from the repository.

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


The chatbot we built have 2 general topics, namely food and hobby. They are implemented using AIML,which is as same as A2

	A2 does not have a GUI, everything was done in the console. We built the GUI using methods extended from JFrame. Recent history is viewable and a scroll bar is added to allow for long conversations.

The open source AIML from Dr. Wallace handles almost all conversations. We used it so that if a user is off topic or an unexpected users' input shows up, the chatbot makes sure conversations flow seamlessly.

	A dictionary is introduced in the project for pattern matching so that when potential spelling mistakes occur, they are handled before being interpreted by the chatbot.


The language toolkit we used in the chatbot for sentiment-detection is Stanford NLP core. So far, we can only respond specifically to the sentiment. We cannot adjust the response very naturally.


	The ChatBot.java is modified from the test code of A2. It is rewritten to have functions built in with sockets so that it can act either as a server or as a client. It is able to have a conversation with another chatbot.

