package Chapter_21_SetsAndMaps;
/* 
 * Donna Bui - October 19, 2022 - Professor Henry Estrada's COMSC 076 @ EVC
 * This program rewrites Listing 21.9 to read the text from a text file (passed as a command-line argument). 
 * It will format the text to include only valid letters/words; removing all special characters and numbers. 
 * Then, it will print the formatted words in alphabetical order along with the number of times the word occurs in the file.
 */
import java.util.*;
import java.io.*;
import java.nio.file.*;

public class CountOccurenceOfWords1 {

  public static void main(String[] args) {

	  List<String> txt = new ArrayList<>(); // Uses a List to store the text contents of the file. 
	  // List is used instead of an ArrayList so we don't have to typecast when using Arrays.asList below
	  try {
		  String fileContent = Files.readString(Paths.get(args[0])); // readString, in my opinion, is more effective than BufferedReader or Scanner
		  txt = Arrays.asList(fileContent.split("[\\s+\\p{Punct}+\\p{Digit}]")); // Simple and efficient! Converts and formats the file's contents all in 1 line
		  /* This uses the regex \s which removes all kinds of white space such as [ \t\n\x0B\f\r]  
		   * It also uses \p{Punct} and \p{Digit} which removes the entire range of special characters and numbers: [!"#$%&'()*+,-./:;<=>?@[\]^_`{|}~] and all digits from 0-9
		   */
	  } 
	  catch (IOException e) { // try/catch block for all file operations!
		  e.printStackTrace();
	  }

    // Everything else below is source code from the textbook - Creates a TreeMap to hold words as key and count as value
    Map<String, Integer> map = new TreeMap<>();
 
    for (int i = 0; i < txt.size(); i++) {
      String key = txt.get(i).toLowerCase();
      
      if (key.length() > 0) {
        if (!map.containsKey(key)) {
          map.put(key, 1);
        }
        else {
          int value = map.get(key);
          value++;
          map.put(key, value);
        }
      }
    }

    // Display key and value for each entry - I switched out k and v so that the value would print in front of the word.
    map.forEach((k, v) -> System.out.println(v + "\t" + k)); 
  }
}