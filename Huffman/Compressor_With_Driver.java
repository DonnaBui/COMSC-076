package GroupProjectHuffman;

import java.io.*;
import java.nio.*;
import java.nio.file.*;
import java.util.*;


public class Compressor_With_Driver {
	
  private Path inputFilePath;
  private String inputFileContent;
  private File inputFile;

  public Compressor_With_Driver(File inputFile) throws IOException {
    this.inputFile = inputFile;
    this.inputFilePath = Path.of(inputFile.getAbsolutePath());
    this.inputFileContent = Files.readString(inputFilePath);
  }

  public void compress(File outputFile) throws IOException, ClassNotFoundException {
	  
    // creates a file path to the source file and reads all of it
    int[] counts = getCharacterFrequency(inputFileContent); // Gets frequency of each character
    Tree tree = getHuffmanTree(counts); // Create a Huffman tree
    String[] codes = getCode(tree.root); // Fill tree with codes
    String[] huffmanCode = getHuffmanCode(inputFileContent, codes); // Converts String to Huffman Code

    FileOutputStream fileOutput = new FileOutputStream(outputFile);

    // Creates an object output stream and writes the 'codes' to the file
    // The index of the Huffman Code corresponds with the ASCII value of its
    // character
    // Ex. code[10] = "10000"
    // (char) 10 = "\n"
    // "\n" = "10000"
    ObjectOutputStream objectOutput = new ObjectOutputStream(fileOutput);
    objectOutput.writeObject(codes);

    // Creates a bit output stream and writes each huffman code as a byte
    // Ex. writing "01001" would write the byte "01001000" to the file
    BitOutputStream bitOutput = new BitOutputStream(fileOutput);
    for (String s : huffmanCode) {
      bitOutput.writeBit(s);
    }

  }

  // turns a string into an array of the corresponding huffman codes of each
  // character
  public static String[] getHuffmanCode(String content, String[] codes) {
    char[] array = content.toCharArray();
    String[] output = new String[array.length];
    for (int i = 0; i < array.length; i++) {
      output[i] = codes[(int) (array[i])];
    }
    return output;

  }

  // implementation is from the textbook
  /**
   * Get Huffman codes for the characters
   * This method is called once after a Huffman tree is built
   */
  public static String[] getCode(Tree.Node root) {
    if (root == null)
      return null;
    String[] codes = new String[128];
    assignCode(root, codes);
    return codes;
  }

  // implementation is from the textbook
  /* Recursively get codes to the leaf node */
  private static void assignCode(Tree.Node root, String[] codes) {
    if (root.left != null) {
      root.left.code = root.code + "0";
      assignCode(root.left, codes);

      root.right.code = root.code + "1";
      assignCode(root.right, codes);
    } else {
      codes[(int) root.element] = root.code;
    }
  }

  // implementation is from the textbook, but a queue is used instead of a heap
  /** Get a Huffman tree from the codes */
  public static Tree getHuffmanTree(int[] counts) {
    // Create a queue to hold trees
    PriorityQueue<Tree> queue = new PriorityQueue<Tree>();
    for (int i = 0; i < counts.length; i++) {
      if (counts[i] > 0)
        queue.add(new Tree(counts[i], (char) i)); // A leaf node tree
    }

    while (queue.size() > 1) {
      Tree t1 = queue.remove(); // Remove the smallest weight tree
      Tree t2 = queue.remove(); // Remove the next smallest weight
      queue.add(new Tree(t1, t2)); // Combine two trees
    }

    return queue.remove(); // The final tree
  }

  // gets the character frequency of a string
  // this implementation is from the textbook
  public static int[] getCharacterFrequency(String text) {
    int[] counts = new int[128]; // 128 ASCII characters

    // loops through each character in the string and increments it's corresponding
    // ASCII value by one
    for (int i = 0; i < text.length(); i++)
      counts[(int) text.charAt(i)]++; // Count the character in text

    return counts;
  }

  // this implemtatoin of the Huffman Tree class is from the textbook
  /** Define a Huffman coding tree */
  public static class Tree implements Comparable<Tree>, Serializable {
    Node root; // The root of the tree

    /** Create a tree with two subtrees */
    public Tree(Tree t1, Tree t2) {
      root = new Node();
      root.left = t1.root;
      root.right = t2.root;
      root.weight = t1.root.weight + t2.root.weight;
    }

    /** Create a tree containing a leaf node */
    public Tree(int weight, char element) {
      root = new Node(weight, element);
    }

    @Override /** Compare trees based on their weights */
    public int compareTo(Tree t) {
      if (root.weight < t.root.weight) // Purposely reverse the order
        return -1;
      else if (root.weight == t.root.weight)
        return 0;
      else
        return 1;
    }

    public class Node implements Serializable {
      char element; // Stores the character for a leaf node
      int weight; // weight of the subtree rooted at this node
      Node left; // Reference to the left subtree
      Node right; // Reference to the right subtree
      String code = ""; // The code of this node from the root

      /** Create an empty node */
      public Node() {
      }

      /** Create a node with the specified weight and character */
      public Node(int weight, char element) {
        this.weight = weight;
        this.element = element;
      }
    }
  }

  public static class BitOutputStream {
    private FileOutputStream output;
    private int bits;
    private int byteSize;

    // Constructor
    public BitOutputStream(File file) throws IOException {
      output = new FileOutputStream(file);
    }

    public BitOutputStream(FileOutputStream output) {
      this.output = output;
    }

    // writes a string of bits to the output stream
    public void writeBit(String bitString) throws IOException {
      for (int i = 0; i < bitString.length(); i++)
        writeBit(bitString.charAt(i));
      if (byteSize != 8 && byteSize != 0) {
        bits = bits << 8 - byteSize;
        output.write(bits);
        byteSize = 0;
      }
    }

    // write a bit to the output stream
    public void writeBit(char bit) throws IOException {
      // left shifts bits by one bit
      bits = bits << 1;

      // if char bit == 1, then use the 'Bitwise inclusive OR operator' to change 0
      // --> 1
      if (bit == '1')
        bits = bits | 1;

      // indexes byteSize by 1 (will be used to check if byte is full later on)
      byteSize++;

      // if byteSize == 8, meaning the byte is full, write the bits to the file and
      // reset byteSize to 0
      if (byteSize == 8) {
        output.write(bits);
        byteSize = 0;
      }
    }

    /**
     * Write the last byte and close the stream. If the last byte is not full, right
     * shift with zeros
     */
    public void close() throws IOException {
      // if the last byte is not full or not empty, fill remaining bits with zeros
      if (byteSize != 0) {
        bits = bits << 8 - byteSize;
        output.write(bits);
      }

      // This makes use of the close() method for a FileOutputStream object
      output.close();
    }
  }
}

