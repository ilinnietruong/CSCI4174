/* CSCI4174: A4 - Exercise 2
A simulation on Matrix Transposition  Cipher, where it contains plaintext encoding to cipher text and
cipher text decoding to plaintext.

Linh Truong(B00708389)
March 19th,2021 */

import java.util.Scanner;
public class MatrixTranspositionCipher {
    public static void main(String[] args) {
        Scanner kb = new Scanner(System.in);
        int key,input;
        String plainText,cipherText;
        System.out.println("Please input: '1' to encode.'2' to decode.");
        input = kb.nextInt();

        //repeat the command if the input is not neither 1 and 2
        while(input != 1 && input != 2) {
            System.out.println("ERROR.Wrong input.Please try again.");
            System.out.println("Please input: '1' to encode.'2 to decode.");
            input=kb.nextInt();
        }
        //Plaintext to ciphertext
        if(input == 1) {
            System.out.println("Enter the plain text: ");
            plainText = kb.next();
            System.out.println("Enter the amount of keys: ");
            int amount = kb.nextInt();
            int[] keyArray = new int[amount];
            System.out.println("Enter the keys: ");
            for (int i = 0; i < amount; i++) {
                key = kb.nextInt();
                keyArray[i] = key;
            }
            System.out.println("Plain text:" + plainText);
            System.out.println("Encrypted message: " + Encrypt(plainText, keyArray));
        }
        //cipher text to plain text
        else if(input == 2) {
            System.out.println("Enter the cipher text: ");
            cipherText = kb.next();
            System.out.println("Enter the amount of keys: ");
            int amount = kb.nextInt();
            int[] keyArray = new int[amount];
            System.out.println("Enter the keys: ");
            for (int i = 0; i < amount; i++) {
                key = kb.nextInt();
                keyArray[i] = key;
            }
            System.out.println("Cipher text:" + cipherText);
            System.out.println("Decrypted message: " + Decrypt(cipherText, keyArray));
        }
    }

    //Method 1: Accepts plaintext (String of characters) and a key (integer array representing the permutation of the
    //columns) as arguments, and generates and returns the ciphertext (String of characters).
    public static String Encrypt(String plainText, int[] key) {
        StringBuilder cipherText= new StringBuilder();
        int count = 0;
        int col = maxNum(key);
        String padding= "%";

        //If the plain text is not divisible by the column, then plain text will add
        // % until it is divisible.
        if(plainText.length() % col != 0) {
            while (count < plainText.length()) {
                padding += padding ;
                count++;
            }
            plainText = plainText + padding ;
        }
        //reset to 0
        count = 0;
        char [][] matrix = new char[col][col];
       //input the plaintext into the matrix
        for(int i=0;i<matrix.length;i++){
            for(int j=0; j<matrix[i].length;j++){
                matrix[i][j] = plainText.charAt(count);
                count++;
            }
        }
        printMatrix(matrix);
        //Read the columns according to the key
        for(int i : key){
            for(char[] ch: matrix) {
                cipherText.append(ch[i - 1]);
            }
        }
        return cipherText.toString();
    }

    /*Method 2: Accepts ciphertext (String of characters) and a key
    (integer array representing the permutation of the
    columns) as arguments, and generates and returns the plaintext (String of characters).*/
    public static String Decrypt(String cipherText, int[] key) {
        StringBuilder plainText= new StringBuilder();
        int col = maxNum(key);
        int row = cipherText.length()/col;
        char [][] matrix = new char[row][col];
        int count = 0;
        //input the cipher text in matrix
        for(int i:key){
            for(int j=0; j < matrix.length;j++) {
                //place the characters in their destination key
                matrix[j][i-1] = cipherText.charAt(count);
                count++;
            }
        }
        //print the matrix
        printMatrix(matrix);
        //Transform characters into string
        for (char[] chars : matrix) {
            for (char letter : chars) {
                plainText.append(letter);
            }
        }
        //Replace '%' into space.
        if(plainText.toString().contains("%")){
            plainText = new StringBuilder(plainText.toString().replaceAll("%", " "));
        }
        return plainText.toString();
    }

    //Find the max number in the array, this will be the amount of column needed.
    public static int maxNum(int [] key) {
        int max= key[0];
        for (int j : key) {
            if (j > max) {
                max = j;
            }
        }
        return max;
    }

    //print the matrix
    public static void printMatrix(char[][] matrix) {
        System.out.println("Matrix:");
        for (char[] chars : matrix) {
            for (char a : chars) {
                System.out.print("\t" + a);
            }
            System.out.println();
        }
    }
}
