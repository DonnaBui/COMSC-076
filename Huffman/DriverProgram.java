package GroupProjectHuffman;

import java.io.*;
import java.util.*;
import java.nio.*;
import java.nio.file.*;

/** Usage in command line (Assuming the file is under the same project OR package directory for Eclipse IDE): 
 * > java DriverProgram inputFileName.txt outputFileName.txt action
 * 
 * The parameter for action has to be either "compress" or "decompress" 
 * Examples: 
 * > java DriverProgram inputFile.txt outputFile.txt compress
 * > java DriverProgram inputFile.txt outputFile.txt decompress
 * 
 * 
 * Alternatively, users can also provide their own path as long as there are no spaces in the path itself.
 * Example:
 * > java DriverProgram C:\Users\UserName\inputFile.txt C:\Users\UserName\inputFile.txt compress
 * 
 * Example of one that will NOT work:
 *  > java DriverProgram C:\Users\User Name\inputFile.txt C:\Users\User Name\inputFile.txt compress
 * Note the space in between User Name. This will lead to the program prompting the user to input a new valid path
 * The prompt will accept any path with a space in it similar to the one above, but the same path will not work in the command line.
 * 
 * 
 * If the file is not under the same project directory OR the user did not define a valid path in the command line, 
 * the program will prompt the user to enter their own path. Below are two examples of valid paths (confirmed to work for eclipse IDE): 
 * C:\Users\UserName\eclipse-workspace\EVC COMSC076\inputFile.txt
 * C:\Users\User Name\eclipse-workspace\EVC COMSC076\inputFile.txt
 * 
 **/

public class DriverProgram {
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		
		// Variables
		Scanner sc = new Scanner(System.in);
		String inputPath = System.getProperty("user.dir") + File.separator + args[0]; // Assuming the file is under the .java file directory
		File inputFile;
		String outputPath = System.getProperty("user.dir") + File.separator + args[1];
		File outputFile;
		String choice = args[2].toLowerCase();

		while (new File(inputPath).exists() == false) { // Check if the file exists, if not, loop prompt user to input a new file until it's valid
			System.out.println("The file at " + inputPath + " does not exist. Please input a valid file path: ");
			inputPath = sc.nextLine().strip();
		} 
		inputFile = new File(inputPath);

		// In case the user doesn't input a valid option in the command line
		if (!choice.equals("compress") && !choice.equals("decompress")) { 
			// Ask user if they want to compress or decompress the file until they input a valid option
			System.out.println("Would you like to (C) compress or (D) decompress the file?");
			choice = sc.nextLine().strip().toUpperCase();
			while (!(choice.equals("C")) && !(choice.equals("D"))) {
				System.out.println("Invalid option.");
				System.out.println("Please enter either C to compress and D to decompress.");
				choice = sc.nextLine().strip().toUpperCase();
			}  
		}
		
		// If user did not define a valid output file, create one for them.
		if (new File(outputPath).exists() == true) {
			outputFile = new File(outputPath);
		}
		else {
			System.out.println("The file at " + outputPath + " does not exist.");
			outputFile = new File("newOutputFile_" + (int)(Math.random()*10000000)  ); // Math.random further ensures we won't encounter any duplicate/already existing files with the same name
			while (outputFile.exists() == true) { // gotta be extra sure the soon-to-be created output file doesn't already exist
				outputFile = new File("newOutputFile_" + (int)(Math.random()*10000000) );
			}
			outputFile.createNewFile(); // After we fully ensure that the file doesn't already exist, we can safely create it and notify the user of the new output file path.
			outputPath = (outputFile.getAbsolutePath());
			System.out.println("Created new output file at " + outputPath);

			/** These two are used for debugging, we can remove them later */
			// System.out.println(outputFile.exists());
			// System.out.println(new File(outputPath).exists());
		}

		// Determine the proceeding action based on the user's selection: C for compress, D for decompress
		if (choice.equals("C") || choice.equals("compress")) {
			System.out.println("Compressing file " + inputFile.getName() + "...");
			Compressor_With_Driver compressor = new Compressor_With_Driver(inputFile); 
			compressor.compress(outputFile);

		}
		else if (choice.equals("D") || choice.equals("decompress")) {
			System.out.println("Decompressing file " + inputFile.getName() + "...");
			/* Same thing as above; call the method for part 2 (decompressing a file)
			 * Do something like this: 
			 * Deompress_a_File decompressor = new Decompress_a_File(); 
			 * decompressor.decompress(inputFile, outputFile);
			 */
		}
	}
}
