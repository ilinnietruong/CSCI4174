/* CSCI4174: A3 Q2
* Standard ACL simulator
* Linh Truong B00708389
* March 8th,2021
*/
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class standardACL {
    public static void main(String[] args) throws FileNotFoundException {
        ArrayList<String> ACLStatus = new ArrayList<>();
        ArrayList<String> ipArray = new ArrayList<>();
        ArrayList<String> sourceIPAddressesArray = new ArrayList();


        String standardACL = "";
        String sourceIPAddresses = "";

        //Textfiles
        Scanner kb = new Scanner(new File("standardTextFile1.txt"));
        Scanner kb2 = new Scanner(new File("standardTextFile2.txt"));

        //Looking at the first textfile
        while (kb.hasNext()) {
            standardACL = kb.next();
            //containing either deny or permit and store it in the ACl status arraylist
            if (standardACL.contains("deny") || standardACL.contains("permit")) {
                ACLStatus.add(standardACL);
            }
            //Store the ip addresses without the ., and each are indepentend variable
            /*Got the regex code from: https://stackoverflow.com/questions/5667371/validate-ipv4-address-in-java"*/
            String ipPattern = "^((0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)\\.){3}(0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)$";
            if (standardACL.matches(ipPattern)) {
                String[] numOnly = standardACL.split("\\.");
                for (int i = 0; i < numOnly.length; i++) {
                    ipArray.add(numOnly[i]);
                }
            }
        }
        kb.close();

        ArrayList<String> sourceIPNum = new ArrayList<>();

        //Look at the second textfile,
        while (kb2.hasNext()) {
            sourceIPAddresses = kb2.next();
            sourceIPAddressesArray.add(sourceIPAddresses);
            /*Got the regex code from: https://stackoverflow.com/questions/5667371/validate-ipv4-address-in-java"*/
            String ipPattern = "^((0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)\\.){3}(0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)$";
            if (sourceIPAddresses.matches(ipPattern)) {
                String[] sourceNum = sourceIPAddresses.split("\\.");
                for (int i = 0; i < sourceNum.length; i++) {
                    sourceIPNum.add(sourceNum[i]);
                }
            }
        }
        kb.close();


        int[] ipAddr = new int[4];
        int[] ipAddr2 = new int[4];
        int[] mask1 = new int[4];
        int[] mask2 = new int[4];

        int[] source1 = new int[4];
        int[] source2 = new int[4];
        int[] source3 = new int[4];

        int i, j;

        String status1 = ACLStatus.get(0);
        String status2 = ACLStatus.get(1);

        //split the ipArray into 4 arrays
        for (i = 0; i < 4; i++) {
            ipAddr[i] = Integer.parseInt(ipArray.get(i));
            mask1[i] = Integer.parseInt(ipArray.get(i + 4));
            ipAddr2[i] = Integer.parseInt(ipArray.get(i + 8));
            mask2[i] = Integer.parseInt(ipArray.get(i + 12));
        }

        //split the sourceIpNum into 3 sources array
        for (j = 0; j < 4; j++) {
            source1[j] = Integer.parseInt(sourceIPNum.get(j));
            source2[j] = Integer.parseInt(sourceIPNum.get(j + 4));
            source3[j] = Integer.parseInt(sourceIPNum.get(j + 8));
        }

        //if the first line contains deny, then if either of the mask host contains 0, if ip addresses and source contain
        //the same integer,deny. Otherwise, permit.
        if (status1.equals("deny")) {
            if (mask1[2] == 0) {
                if (ipAddr[2] == source1[2]) {
                    System.out.println("Packet from " + sourceIPAddressesArray.get(0) + " deny");
                } else if (ipAddr[0] == source1[0]) {
                    System.out.println("Packet from " + sourceIPAddressesArray.get(0) + " permit");
                }
                if (ipAddr[2] == source2[2]) {
                    System.out.println("Packet from " + sourceIPAddressesArray.get(1) + " deny");
                } else {
                    System.out.println("Packet from " + sourceIPAddressesArray.get(1) + " permit");
                }
                if (ipAddr[2] == source3[2]) {
                    System.out.println("Packet from " + sourceIPAddressesArray.get(2) + " deny");
                } else if (ipAddr[0] == source3[0]) {
                    System.out.println("Packet from " + sourceIPAddressesArray.get(2) + " permit");
                }
            } else if (mask1[3] == 0) {
                if (ipAddr[3] == source1[3]) {
                    System.out.println("Packet from " + sourceIPAddressesArray.get(0) + " deny");
                } else {
                    System.out.println("Packet from " + sourceIPAddressesArray.get(0) + " permit");
                }
                if (ipAddr[3] == source2[3]) {
                    System.out.println("Packet from " + sourceIPAddressesArray.get(1) + " deny");
                } else {
                    System.out.println("Packet from " + sourceIPAddressesArray.get(1) + " permit");
                }
                if (ipAddr[3] == source3[3]) {
                    System.out.println("Packet from " + sourceIPAddressesArray.get(2) + " deny");
                } else {
                    System.out.println("Packet from " + sourceIPAddressesArray.get(2) + " permit");
                }
            }
        }
        //Same as above, but permit.
        else if(status1.equals("permit")) {
            if (mask1[2] == 0) {
                if (ipAddr[2] == source1[2]) {
                    System.out.println("Packet from " + sourceIPAddressesArray.get(0) + " permit");
                } else if (ipAddr[0] == source1[0]) {
                    System.out.println("Packet from " + sourceIPAddressesArray.get(0) + " deny");
                }
                if (ipAddr[2] == source2[2]) {
                    System.out.println("Packet from " + sourceIPAddressesArray.get(1) + " permit");
                } else if (ipAddr[0] == source2[0]) {
                    System.out.println("Packet from " + sourceIPAddressesArray.get(1) + " deny");
                }
                if (ipAddr[2] == source3[2]) {
                    System.out.println("Packet from " + sourceIPAddressesArray.get(2) + " permit");
                } else if (ipAddr[0] == source3[0]) {
                    System.out.println("Packet from " + sourceIPAddressesArray.get(2) + " deny");
                }
            } else if (mask1[3] == 0) {
                if (ipAddr[3] == source1[3]) {
                    System.out.println("Packet from " + sourceIPAddressesArray.get(0) + " permit");
                } else if (ipAddr[0] == source3[0]) {
                    System.out.println("Packet from " + sourceIPAddressesArray.get(0) + " deny");
                }
                if (ipAddr[3] == source2[3]) {
                    System.out.println("Packet from " + sourceIPAddressesArray.get(1) + " permit");
                } else if (ipAddr[0] == source2[0]) {
                    System.out.println("Packet from " + sourceIPAddressesArray.get(1) + " deny");
                }
                if (ipAddr[3] == source3[3]) {
                    System.out.println("Packet from " + sourceIPAddressesArray.get(2) + " permit");
                } else if (ipAddr[0] == source3[0]) {
                    System.out.println("Packet from " + sourceIPAddressesArray.get(2) + " deny");
                }
            }
        }
        //if the second line contain deny, otherwise, the same as the first if statement.
        else if (status2.equals("deny")) {
            if (mask2[2] == 0) {
                if (ipAddr2[2] == source1[2]) {
                    System.out.println("Packet from " + sourceIPAddressesArray.get(0) + " deny");
                } else if (ipAddr2[0] == source1[0]) {
                    System.out.println("Packet from " + sourceIPAddressesArray.get(0) + " permit");
                }
                if (ipAddr2[2] == source2[2]) {
                    System.out.println("Packet from " + sourceIPAddressesArray.get(1) + " deny");
                } else {
                    System.out.println("Packet from " + sourceIPAddressesArray.get(1) + " permit");
                }
                if (ipAddr2[2] == source3[2]) {
                    System.out.println("Packet from " + sourceIPAddressesArray.get(2) + " deny");
                } else if (ipAddr2[0] == source3[0]) {
                    System.out.println("Packet from " + sourceIPAddressesArray.get(2) + " permit");
                }

            } else if (mask2[3] == 0) {
                if (ipAddr2[3] == source1[3]) {
                    System.out.println("Packet from " + sourceIPAddressesArray.get(0) + " deny");
                } else if (ipAddr2[0] == source1[0]) {
                    System.out.println("Packet from " + sourceIPAddressesArray.get(0) + " permit");
                }
                if (ipAddr2[3] == source2[3]) {
                    System.out.println("Packet from " + sourceIPAddressesArray.get(1) + " deny");
                } else if (ipAddr2[0] == source2[0])  {
                    System.out.println("Packet from " + sourceIPAddressesArray.get(1) + " permit");
                }
                if (ipAddr2[3] == source3[3]) {
                    System.out.println("Packet from " + sourceIPAddressesArray.get(2) + " deny");
                } else if (ipAddr2[0] == source3[0])  {
                    System.out.println("Packet from " + sourceIPAddressesArray.get(2) + " permit");
                }
            }
        }
        //same as above but permit.
        else if(status2.equals("permit")){
            if (mask2[2] == 0) {
                if (ipAddr2[2] == source1[2]) {
                    System.out.println("Packet from " + sourceIPAddressesArray.get(0) + " permit");
                } else if (ipAddr2[0] == source1[0]) {
                    System.out.println("Packet from " + sourceIPAddressesArray.get(0) + " deny");
                }
                if (ipAddr2[2] == source2[2]) {
                    System.out.println("Packet from " + sourceIPAddressesArray.get(1) + " permit");
                } else if (ipAddr2[0] == source2[0]) {
                    System.out.println("Packet from " + sourceIPAddressesArray.get(1) + " deny");
                }
                if (ipAddr2[2] == source3[2]) {
                    System.out.println("Packet from " + sourceIPAddressesArray.get(2) + " permit");
                } else if (ipAddr2[0] == source3[0]) {
                    System.out.println("Packet from " + sourceIPAddressesArray.get(2) + " deny");
                }
            }

            else if (mask1[3] == 0) {
                if (ipAddr2[3] == source1[3]) {
                        System.out.println("Packet from " + sourceIPAddressesArray.get(0) + " permit");
                } else if (ipAddr2[0] == source3[0]) {
                    System.out.println("Packet from " + sourceIPAddressesArray.get(0) + " deny");
                }
                if (ipAddr2[3] == source2[3]) {
                    System.out.println("Packet from " + sourceIPAddressesArray.get(1) + " permit");
                } else {
                    System.out.println("Packet from " + sourceIPAddressesArray.get(1) + " deny");
                }
                if (ipAddr2[3] == source3[3]) {
                    System.out.println("Packet from " + sourceIPAddressesArray.get(2) + " permit");
                }
                else if (ipAddr2[0] == source3[0]) {
                    System.out.println("Packet from " + sourceIPAddressesArray.get(2) + " deny");
                }
            }
        }
        //If the source's network are not same as the ip address.
        if (ipAddr[0] != source1[0] ) {
            System.out.println("Packet from " + sourceIPAddressesArray.get(0) + " deny");
        }
        if (ipAddr[0] != source2[0]) {
            System.out.println("Packet from " + sourceIPAddressesArray.get(1) + " deny");
        }
        if (ipAddr[0] != source3[0]) {
                System.out.println("Packet from " + sourceIPAddressesArray.get(2) + " deny");
        }
    }
} //end






