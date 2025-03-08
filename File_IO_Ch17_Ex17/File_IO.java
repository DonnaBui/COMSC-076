package File_IO_Ch17_Ex17;
/* 
 * Donna Bui - September 13, 2022 - Professor Henry Estrada's COMSC 076 @ EVC
 * This exercise was from Programming Exercise 17.17 on page 739 of the course text (11th Edition). 
 * The program will take a string of bits and create a file called testOutput.dat which the bits will be outputted to.
 * Once it completes outputting the bits to the file, it will convert & print the content in UTF-8, or human-readable text
 */

import java.io.*;
public class File_IO {
	
	    public static void main(String[] args) throws Exception {
	    	
	    	// Creates a file called testOutput.dat and calls the writeBit method on the given string
	    	BitOutputStream output = new BitOutputStream(new File("testOutput.dat"));
	    	output.writeBit("010000100100001001101");
	    	output.close();
	    	System.out.println("Bit successfully written to file");
	    	
	    	// Prints the contents of the testOutput file
	    	try (BufferedReader content = new BufferedReader(new InputStreamReader( 
	    		new FileInputStream("testOutput.dat"), "UTF-8")); 
	    		) { // converts the content of the testOutput file to human-readable text
	    		    String value;
	    		    System.out.println("File Content: ");
	    		    while ((value = content.readLine()) != null)
	    		    System.out.print(value);
	    	}
	    }
	    
	    
	   public static class BitOutputStream {
	        private FileOutputStream output;
	        int bits; // stores the bit characters used in writeBit() to be written into the output file
	        int index; /* counts how many "chars" are in the current bit, also what position it's at in the bit- 
	         			it gets set back to 0 when it reaches 8 */ 

	        /** Main constructor */
	        public BitOutputStream(File file) throws IOException {
	        	output = new FileOutputStream(file);
	        }

	        /** Takes each character out of a string & calls second writeBit method for each character */
	        public void writeBit(String bitString) throws IOException {
	        	for (int i = 0; i < bitString.length(); i++)
	        		writeBit(bitString.charAt(i));
	        }

	        /** Writes each character into the output file in bit form */
	        public void writeBit(char bit) throws IOException { 
	        	 bits = bits << 1; // shifts the bytes left
	        	 index++; // increases the count by 1
	        	 
	             if (bit == '1')
	                 bits = bits | 1;
	             	
	             if (index == 8) { // checks if the bit has reached 8 (maximum for a byte is 8)
	                 output.write(bits); // writes the bits to the output file
	                 index = 0; // resets the char count to 0
	             }

	         }
	        
	       /** Write the last byte and close the stream. If the last byte is not full, right-shift with zeros         */
	        public void close( ) throws IOException {
	            if (index > 0) { // checks if the char count is greater than 0
	                bits = bits << 8 - index; // add 0's to end of byte
	                output.write(bits);
	            }
	        	output.close();  // This makes use of the close() method for a FileOutputStream object
	       }
	        
	  }
	   
}

