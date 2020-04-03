import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Solution {

    private final HashMap<String, Integer> wordMap = new HashMap<String, Integer>();

    public Solution(String file) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader(file));
        Pattern p = Pattern.compile("\\w+");
        for(String temp = ""; temp != null; temp = in.readLine()){
            Matcher m = p.matcher(temp.toLowerCase());
            while(m.find()) wordMap.put((temp = m.group()), wordMap.containsKey(temp) ? wordMap.get(temp) + 1 : 1);
        }
        in.close();
    }

    private final ArrayList<String> edits(String word) {
        ArrayList<String> result = new ArrayList<String>();
        //deleting one character from string
        for(int i=0; i < word.length(); ++i) result.add(word.substring(0, i) + word.substring(i+1));
        //swapping adjacent characters in string
        for(int i=0; i < word.length()-1; ++i) result.add(word.substring(0, i) + word.substring(i+1, i+2) + word.substring(i, i+1) + word.substring(i+2));
        //replacing one character in string with a-z
        for(int i=0; i < word.length(); ++i) for(char c='a'; c <= 'z'; ++c) result.add(word.substring(0, i) + String.valueOf(c) + word.substring(i+1));
        //inserting a-z in string
        for(int i=0; i <= word.length(); ++i) for(char c='a'; c <= 'z'; ++c) result.add(word.substring(0, i) + String.valueOf(c) + word.substring(i));
        return result;
    }

    public String spellCheck(String word) {
        if(wordMap.containsKey(word))
            return word;
        ArrayList<String> list = edits(word);
        int maxNumberOfOccurrences = 0;
        String currentString = word;
        for(String s : list) {
            if(wordMap.containsKey(s)) {
                if (wordMap.get(s) < maxNumberOfOccurrences) {
                    continue;
                }
                if (wordMap.get(s) > maxNumberOfOccurrences) {
                    maxNumberOfOccurrences = wordMap.get(s);
                    currentString = s;
                } else {
                    if (s.compareTo(currentString) < 0) {
                        currentString = s;
                    }
                }
            }
        }
        return currentString;
   }

    

}