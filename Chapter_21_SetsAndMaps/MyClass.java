package Chapter_21_SetsAndMaps;

import java.util.*;
import java.io.*;

public class MyClass {

    public static void main(String[] args) throws IOException {
        //creates a buffered reader for this file

            BufferedReader input = new BufferedReader( new FileReader(args[0]));



        ArrayList<String> totalWords = new ArrayList<>();
        //splits each line of the file into words that do not have numbers or non-word characters
       try {
          String line = input.readLine();
       //   while (input.hasNextLine()) {
            String [] words = line.split("[0-9]+|\\W+");
            for (String word : words) {
                totalWords.add(word.toLowerCase());
            }
        //  }
       }
        catch(FileNotFoundException error) {
             error.printStackTrace();
        }
        //adds the words to a tree map
        Map<String, Integer> wordCounts = new TreeMap<>();
        for (String word : totalWords) {
            if (word.length() > 0) {
                //increments the count if the word is already in the map
                if (wordCounts.containsKey(word)) {
                    wordCounts.put(word, wordCounts.get(word) + 1);
                }
                //adds the word with count 1 if it is not in the map
                else {
                    wordCounts.put(word,  1);
                }
            }
        }

        wordCounts.forEach((k, v) -> System.out.println(v + "\t" + k));

    }

}
