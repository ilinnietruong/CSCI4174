/* CSCI4174: A4 - Exercise 1
A simulation on Playfair Substitution Cipher, where it contains plaintext encoding to cipher text and
cipher text decoding to plaintext.

Linh Truong(B00708389)
March 19th,2021 */

import java.util.LinkedHashSet;
import java.util.Scanner;
import java.util.Set;

public class PlayfairSubstitutionCipher {
    public static void main(String[] args) {
        Scanner kb = new Scanner(System.in);
        String key, encode, decode;
        int input;
        System.out.println("Please input a secret key:");
        key = kb.nextLine();
        System.out.println("Please enter either '1' to encrypt or '2' to decrypt: ");
        input = kb.nextInt();
        kb.nextLine();

        //repeat the command if the input is not neither 1 and 2
        while (input!=1 && input!=2) {
            System.out.println("Wrong input! Please try again.");
            System.out.println("Please enter either \n '1' to encrypt or '2' to decrypt: ");
            input = kb.nextInt();
            kb.nextLine();
        }
        //Plaintext to ciphertext
        if (input == 1) {
            System.out.println("Please input a plaintext: ");
            encode = kb.nextLine();
            char[][] keyMatrix = generateKeyMatrix(key.toUpperCase());
            System.out.println("The Key Matrix:");
            printKeyMatrix(keyMatrix);
            System.out.println("First line is plain text in pairs and second line is cipher text: ");
            for (int i = 0; i < messagePair(encode).length; i++) {
                System.out.print(messagePair(encode)[i] + "\t");
            }
            System.out.println();
            System.out.println(cipherText(encode, key));
            System.out.println("Cipher text: " + cipherText(encode, key));
        }
        //Cipher text to plaintext
        else if (input==2) {
            System.out.println("Please input a cipherText: ");
            decode = kb.nextLine();
            char[][] keyMatrix = generateKeyMatrix(key.toUpperCase());
            System.out.println("The Key Matrix:");
            printKeyMatrix(keyMatrix);
            System.out.println("First line is plain text in pairs and second line is cipher text: ");
            for (int i = 0; i < messagePair(decode).length; i++) {
                System.out.print(messagePair(decode)[i] + "\t");
            }
            System.out.println();
            System.out.println(plainText(decode, key));
            System.out.println("Plain text: " + plainText(decode, key));
        }
    }

    //Method 1: Accepts a secret key (String of characters) as argument and generates and returns a key matrix (2D
    //array).
    public static char[][] generateKeyMatrix(String key) {
        key = key.toUpperCase();
        //if there is a space in the key, then ignore the space.
        if(key.contains(" ")) {
            key= key.replaceAll("\\s+","");
        }

        char [][] matrix = new char[5][5];
        String keyString = key +"ABCDEFGHIKLMNOPQRSTUVWXYZ";
        int counter=0;

        //if the string contain "J", then replace J to I
        if(keyString.contains("J")) {
            keyString=keyString.replace("J","I");
        }

        //no duplication in the string
        char[] keyChar = keyString.toCharArray();
        Set<Character> setCharacter = new LinkedHashSet<Character>();

        //add key char into set
        for (char c : keyChar) {
            setCharacter.add(c);
        }
        // Add the character into stringbuilder
        StringBuilder sb = new StringBuilder();
        for (Character ch : setCharacter) {
            sb.append(ch);
        }

        keyString = sb.toString();
        //add characters into 2D array
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                char charKey = keyString.charAt(counter);
                matrix[i][j] = charKey;
                counter++;
            }
        }
        return matrix;
    }

    //Method 2 - 1/2: Accepts plaintext (String of characters) and the key  as arguments, and generates and returns
    //the cipher characters.
    public static String cipherCharacter(String plainText, String key) {
        String [] pairString = messagePair(plainText);
        char[][] keyMatrix = generateKeyMatrix(key);

        //convert the strings inside the string array into characters
        StringBuilder sb = new StringBuilder();
        for (String value : pairString) {
            sb.append(value);
        }

        //fetch two characters
        String s = sb.toString();
        char a = s.charAt(0);
        char b =  s.charAt(1);

        String cipherCharacters="";
        int row1,row2,column1,column2;
        row1=rowPosition(a,key); //position of the first character in the row
        row2=rowPosition(b,key); //position of the second character in the row

        column1 = columnPosition(a,key); //position of the first character in the column
        column2 = columnPosition(b,key); //position of the second character in the column

        //if the first character and the second character are on the same row, c
        // choose the encode character one downward
        if(row1 == row2) {

            column1++;
            column2++;

            //if either character is off the grid, then use the first character on the
            // key matrix
            if(column1 > 4) {
                column1 = 0;
            }

            if(column2 > 4) {
                column2 = 0;
            }
            //get the character from the keymatrix and add on
            cipherCharacters+= keyMatrix[row1][column1];
            cipherCharacters+= keyMatrix[row2][column2];
        }

        //if the first character and the second character are on the same column, then
        // choose the encode character on the right
        else if(column1 == column2) {
            row1++;
            row2++;

            //if either character is off the grid, then use the first character on the
            // key matrix
            if(row1 > 4) {
                row1 = 0;
            }

            if(row2 > 4) {
                row2 = 0;
            }
            //fetch the characters
            cipherCharacters += keyMatrix[row1][column2];
            cipherCharacters += keyMatrix[row2][column1];
        }
        //neither characters are on the same row and columns
        else {
            cipherCharacters+= keyMatrix[row1][column2];
            cipherCharacters+= keyMatrix[row2][column1];
        }
        return cipherCharacters;
    }
    //Method 2 - 2/2: Accepts plaintext (String of characters) and the key  as arguments, and generates and returns
    //the cipher characters.
    public static String cipherText(String plainText,String key) {
        StringBuilder cipherText= new StringBuilder();
        String [] pairString = messagePair(plainText);
        //fetch the strings from the string array, and place it in String builder
        StringBuilder sb = new StringBuilder();
        for (String value : pairString) {
            sb.append(value);
        }
        //create the ciphertext from the cipher characters
        String s=sb.toString();
        for(int i=0;i < s.length()-1; i=i+2) {
            cipherText.append(cipherCharacter(s.substring(i, i + 2), key));
            cipherText.append("\t");
        }
        return cipherText.toString();
    }

    //Method 3 - 1/2:  Accepts ciphertext (String of characters) and the key matrix as arguments, and generates and returns
    //the.plainText characters
     public static String plainCharacter(String cipherText,String key) {
        String [] pairString = messagePair(cipherText);
        char[][] keyMatrix = generateKeyMatrix(key);

        //convert the strings inside the string array into characters
        StringBuilder sb = new StringBuilder();
        for (String value : pairString) {
            sb.append(value);
        }

        String s=sb.toString();

        //fetch two characters at a time
        char a = s.charAt(0);
        char b=  s.charAt(1);
        int row1,row2,column1,column2;
        String pc=""; //plain characters

        row1=rowPosition(a,key);
        row2=rowPosition(b,key);
        column1=columnPosition(a,key);
        column2=columnPosition(b,key);

        //if the first character and the second character are on the same row, then
         // choose the encode character one upward
        if(row1 == row2){
             column1--;
             column2--;
             //if either character from their column is  out of
            // the bound from index 0, use index 4
             if(column1 < 0) {
                 column1 = 4;
             }

             if(column2 < 0) {
                 column2 = 4;
             }
             //fetch the character and build on from the previous character
             pc+=keyMatrix[row1][column1];
             pc+=keyMatrix[row2][column2];
        }
        //if the first character and the second character are on the same column, then
        // choose the encode character on the left
        else if(column1 == column2){
            row1--;
            row2--;

            //if either character from their row is  out of
            // the bound from index 0, use index 4
            if(row1 < 0) {
                row1 = 4;
            }

            if(row2 < 0) {
                row2 = 4;
            }
            //fetch the character and build on from the previous character
            pc+=keyMatrix[row1][column2];
            pc+=keyMatrix[row2][column1];
        }
        //neither characters are on the same row and columns
        else{
            //fetch the character and build on from the previous character
            pc+=keyMatrix[row1][column2];
            pc+=keyMatrix[row2][column1];
        }
        return pc;
    }
    //Method 3 - 2/2: Accepts cipher text (String of characters) and the key  as arguments, and generates and returns
    //the plain text.
    public static String plainText(String cipherText, String key) {
        StringBuilder plainText= new StringBuilder();
        String [] pairString = messagePair(cipherText);
        //fetch the strings from the string array, and place it in String builder
        StringBuilder sb = new StringBuilder();
        for (String value : pairString) {
            sb.append(value);
        }
        //create the ciphertext from the cipher characters
        String s=sb.toString();
        for(int i=0; i < s.length()-1; i=i+2) {
            plainText.append(plainCharacter(cipherText.substring(i, i + 2), key));
            plainText.append("\t");

        }
        return plainText.toString();
    }

    //Pair the cipherText or plainText characters into two.
    public static String[] messagePair(String message) {
        message = message.toUpperCase();
        //if there is a space in the key, then ignore the space.
        if(message.contains(" "))
        {
            message = message.replaceAll("\\s+","");
        }
        String[] pairList = new String[message.length()];
        int i = 0;
        //in the string, if there is a character that has the same character as the next character,
        //then add character,"X" between the two identical character.
        for (int j = 0; j < message.length()-1; j++) {
            if (message.charAt(j) == message.charAt(j + 1)) {
                StringBuilder sb = new StringBuilder(message);
                sb.insert(j+1, 'X');
                message = sb.toString();
            }
        }

        //if the string length is odd, then add "z" at the end
        if (message.length() % 2 == 1) {
            message = message + "Z";
        }

        /*Pair each characters into two.
        Reference:
        A. Lathi, “Split a string after each two characters,” Stack Overflow, 01-Aug-2013. [Online].
         Available: https://stackoverflow.com/questions/17993729/split-a-string-after-each-two-characters.[Accessed: 16-Mar-2021]. */
        while (i < message.length()) {
            pairList = message.split("(?<=\\G.{2})");
            i++;
        }
        return pairList;
    }
    //print out the key Matrix
    public static void printKeyMatrix(char[][] matrix) {
        for(int j = 0; j < matrix.length; j++) {
            for(int i = 0; i < matrix.length; i++) {
                System.out.print("\t" + matrix[j][i]);
            }
            System.out.println();
        }
    }

    //return an integer from the location of character in the row
    static int rowPosition(char a, String key) {
        char[][] keyMatrix = generateKeyMatrix(key);
        for(int i=0; i < keyMatrix.length;i++) {
            for(int j=0;j < keyMatrix[i].length;j++) {
                if(keyMatrix[i][j] == a) {
                    return i; //get the position from the row in key matrix
                }
            }
        }
        return -1; //return an error if theres no position in the row
    }

    //return an integer from the location of character in the column
    static int columnPosition(char a, String key) {
        char[][] keyMatrix = generateKeyMatrix(key);
        for(int i = 0; i < keyMatrix.length; i++) {
            for(int j = 0; j < keyMatrix[i].length; j++) {
                if(keyMatrix[i][j] == a) {
                    return j; //get the position from the column in key matrix
                }
            }
        }
        return -1; //return an error if theres no position in the row
    }
} //end
