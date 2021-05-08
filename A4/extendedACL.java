/* CSCI4174: A3 Q2
 * Extended ACL simulator
 * Linh Truong B00708389
 * March 8th,2021
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class extendedACL {
    public static void main(String[] args) throws FileNotFoundException {
        ArrayList<String> ACLStatus = new ArrayList<>();
        ArrayList<String> ipArray = new ArrayList<>();
        ArrayList<String> portArray = new ArrayList<>();
        ArrayList<String> ipSourceAddresses = new ArrayList<>();
        ArrayList<String> ipDestinationAddresses = new ArrayList<>();
        ArrayList<String> secondPort = new ArrayList<>();

        ArrayList<String> sourceAddresses = new ArrayList<>();
        ArrayList<String> destinationAddresses= new ArrayList<>();

        String extentedACL = "";
        String ipSourcePort = "";
        String ipPort = "";
        String ipDestPort= "";

        //Port number between 1-100
        String numbersOnly = "^[1-9][0-9]?$|^100$";


        Scanner kb = new Scanner(new File("extendedTextFile1.txt"));
        Scanner kb2 = new Scanner(new File("extendedTextFile2.txt"));

        while (kb.hasNext()) {
            extentedACL = kb.next();
            if (extentedACL.contains("deny") || extentedACL.contains("permit")) {
                ACLStatus.add(extentedACL);
            }
            /*Got the regex code from: https://stackoverflow.com/questions/5667371/validate-ipv4-address-in-java"*/
            String ipPattern = "^((0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)\\.){3}(0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)$";
            if (extentedACL.matches(ipPattern)) {
                String[] numOnly = extentedACL.split("\\.");
                for (int i = 0; i < numOnly.length; i++) {
                    ipArray.add(numOnly[i]);
                }
            }

            //Getting the port number
            //if it contains the range 20-21, then split the two integers as their own integer
            if(extentedACL.contains("20-21"))
            {
                String [] portNum = extentedACL.split("\\-");
                for(int i=0; i< portNum.length; i++) {
                    portArray.add(portNum[i]);
                }
            }
            //if there is no range
            else if(extentedACL.matches(numbersOnly))
            {
                portArray.add(extentedACL);
            }
        }
        kb.close();

        //Looking at the second text file
        while (kb2.hasNext()) {
            ipSourcePort= kb2.next();
            sourceAddresses.add(ipSourcePort);
            ipDestPort = kb2.next();
            destinationAddresses.add(ipDestPort);
            ipPort = kb2.next();
            secondPort.add(ipPort);

            /*Got the regex code from: https://stackoverflow.com/questions/5667371/validate-ipv4-address-in-java"*/
            String ipPattern = "^((0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)\\.){3}(0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)$";
            //only fetch IP address in an array, and then only use only the integers
            if (ipSourcePort.matches(ipPattern) && ipDestPort.matches(ipPattern)) {
                String[] ipNum = ipSourcePort.split("\\.");
                String [] ipDestNum = ipDestPort.split("\\.");
                for (int i = 0; i < ipNum.length; i++) {
                    ipSourceAddresses.add(ipNum[i]);
                    ipDestinationAddresses.add(ipDestNum[i]);
                }
            }
        }
        kb.close();
        int i,j;

        //Creating Arrays from file text 1
        int[] ipAddrSource1 = new int[4];
        int[] ipAddrDestination1 = new int[4];
        int[] maskSource1 = new int[4];
        int[] maskDestination1 = new int[4];
        int [] ipAddrSource2 = new int[4];
        int [] ipAddrDestination2 = new int [4];
        int [] maskSource2 = new int [4];
        int [] maskDestination2 = new int [4];

        //Creating arrays from file text 2
        int[] source1 = new int[4];
        int[] destination1 = new int [4];
        int[] source2 = new int[4];
        int[] destination2 = new int [4];
        int[] source3 = new int[4];
        int[] destination3 = new int [4];

        //Splitting the ipArray into 4 integers.
        for (i = 0; i < 4; i++) {
            ipAddrSource1[i] = Integer.parseInt(ipArray.get(i));
            maskSource1[i] = Integer.parseInt(ipArray.get(i + 4));
            ipAddrDestination1[i] = Integer.parseInt(ipArray.get(i + 8));
            maskDestination1[i] = Integer.parseInt(ipArray.get(i + 12));

            ipAddrSource2[i] = Integer.parseInt(ipArray.get(i + 16));
            maskSource2[i] = Integer.parseInt(ipArray.get(i + 20));
            ipAddrDestination2[i] = Integer.parseInt(ipArray.get(i + 24));
            maskDestination2[i] = Integer.parseInt(ipArray.get(i + 28));
        }

        //Splitting the ipSourceAddresses arraylist and ipDestinationAddresses arraylist into 4 integers.
        for(j=0;j<4; j++)
        {
            source1[j] = Integer.parseInt(ipSourceAddresses.get(j));
            destination1[j] = Integer.parseInt(ipDestinationAddresses.get(j));
            source2 [j] = Integer.parseInt(ipSourceAddresses.get(j+4));
            destination2[j] = Integer.parseInt(ipDestinationAddresses.get(j+4));
            source3[j] = Integer.parseInt(ipSourceAddresses.get(j+8));
            destination3[j] = Integer.parseInt(ipDestinationAddresses.get(j+8));
        }

        String status1 = ACLStatus.get(0); //Fetching the first line of the status(permit or deny)
        String status2 = ACLStatus.get(1); //Fetching the second line of the status(permit or deny)


        if(status1.equals("deny")) {
            //if the port number is the same as the port number as the destination port number, then denied.
            for (i = 0; i < portArray.size(); i++) {
                if (portArray.get(i).equals(secondPort.get(0))) {
                    System.out.println("Packet from " + sourceAddresses.get(0) + " to " + destinationAddresses.get(0) + " on port "
                            + secondPort.get(0) + " denied");
                }
                if (portArray.get(i).equals(secondPort.get(1))) {
                    System.out.println("Packet from " + sourceAddresses.get(1) + " to " + destinationAddresses.get(1) + " on port "
                            + secondPort.get(1) + " denied");
                }
                if (portArray.get(i).equals(secondPort.get(2))) {
                    System.out.println("Packet from " + sourceAddresses.get(2) + " to " + destinationAddresses.get(1) + " on port "
                            + secondPort.get(2) + " denied");
                }
            }
          //if the first mask in line 1 in textfile1 is equal to 0, then if both ip and source/destination are the same, then denied.
            if (maskSource1[2] == 0 || maskDestination2[2] == 0 || maskSource1[3] == 0 || maskDestination1[3] == 0) {
                if (ipAddrSource1[2] == source1[2] || ipAddrDestination1[2] == source1[2] ||
                        ipAddrSource1[3] == source1[3] || ipAddrDestination1[3] == source1[3] ) {
                    System.out.println("Packet from " + sourceAddresses.get(0) + " to " + destinationAddresses.get(0) + " on port "
                                + secondPort.get(0) + " denied");
                } else if (!portArray.get(0).equals(secondPort.get(0))) {
                    System.out.println("Packet from " + sourceAddresses.get(0) + " to " + destinationAddresses.get(0) + " on port "
                            + secondPort.get(0) + " permitted");
                }

                if (ipAddrSource1[2] == source2[2] || ipAddrDestination1[2] == source2[2] ||
                        ipAddrSource1[3] == source2[3] || ipAddrDestination1[3] == source2[3]) {
                    System.out.println("Packet from " + sourceAddresses.get(1) + " to " + destinationAddresses.get(1) + " on port "
                            + secondPort.get(1) + " denied");
                } else if (!portArray.get(0).equals(secondPort.get(1))) {
                    System.out.println("Packet from " + sourceAddresses.get(1) + " to " + destinationAddresses.get(1) + " on port "
                            + secondPort.get(1) + " permitted");
                }
                if (ipAddrSource1[2] == source3[2] || ipAddrDestination1[2] == source3[2] ||
                        ipAddrSource1[3] == source3[3] || ipAddrDestination1[3] == source3[3]) {
                    System.out.println("Packet from " + sourceAddresses.get(2) + " to " + destinationAddresses.get(2) + " on port "
                            + secondPort.get(2) + " denied");
                } else if (!portArray.get(0).equals(secondPort.get(2))) {
                    System.out.println("Packet from " + sourceAddresses.get(2) + " to " + destinationAddresses.get(2) + " on port "
                            + secondPort.get(2) + " permitted");
                }
            }
            //if the first mask in line 1 in textfile1 is equal to 0, then if both ip and source/destination are the same, then permitted.
            else if(status1.equals("permit")) {
                for (i = 0; i < portArray.size(); i++) {
                    if (portArray.get(i).equals(secondPort.get(0))) {
                        System.out.println("Packet from " + sourceAddresses.get(0) + " to " + destinationAddresses.get(0) + " on port "
                                + secondPort.get(0) + " permitted");
                    }
                    if (portArray.get(i).equals(secondPort.get(1))) {
                        System.out.println("Packet from " + sourceAddresses.get(1) + " to " + destinationAddresses.get(1) + " on port "
                                + secondPort.get(1) + " permitted");
                    }
                    if (portArray.get(i).equals(secondPort.get(2))) {
                        System.out.println("Packet from " + sourceAddresses.get(2) + " to " + destinationAddresses.get(1) + " on port "
                                + secondPort.get(2) + " permitted");
                    }
                }
                if (maskSource1[2] == 0 || maskDestination1[2] == 0 || maskSource1[3] == 0 || maskDestination1[3] == 0) {
                    if (ipAddrSource1[2] == source1[2] || ipAddrDestination1[2] == source1[2] ||
                            ipAddrSource1[3] == source1[3] || ipAddrDestination1[3] == source1[3]) {
                        System.out.println("Packet from " + sourceAddresses.get(0) + " to " + destinationAddresses.get(0) + " on port "
                                + secondPort.get(0) + " permitted");
                    } else if (!portArray.get(0).equals(secondPort.get(0))) {
                        System.out.println("Packet from " + sourceAddresses.get(0) + " to " + destinationAddresses.get(0) + " on port "
                                + secondPort.get(0) + " deny");
                    }

                    if (ipAddrSource1[2] == source2[2] || ipAddrDestination1[2] == source2[2] ||
                            ipAddrSource1[3] == source2[3] || ipAddrDestination1[3] == source2[3]) {
                        System.out.println("Packet from " + sourceAddresses.get(1) + " to " + destinationAddresses.get(1) + " on port "
                                + secondPort.get(1) + " permitted");
                    } else if (!portArray.get(0).equals(secondPort.get(1))) {
                        System.out.println("Packet from " + sourceAddresses.get(1) + " to " + destinationAddresses.get(1) + " on port "
                                + secondPort.get(1) + " denied");
                    }
                    if (ipAddrSource1[2] == source3[2] || ipAddrDestination1[2] == source3[2] ||
                            ipAddrSource1[3] == source3[3] || ipAddrDestination1[3] == source3[3]) {
                        System.out.println("Packet from " + sourceAddresses.get(2) + " to " + destinationAddresses.get(2) + " on port "
                                + secondPort.get(2) + " permitted");
                    } else if (!portArray.get(0).equals(secondPort.get(2))) {
                        System.out.println("Packet from " + sourceAddresses.get(2) + " to " + destinationAddresses.get(2) + " on port "
                                + secondPort.get(2) + " denied");
                    }
                }
            }
            //same as status1.equals("permit"), but looking at the second line.
            else if(status2.equals("deny")) {
                if (maskSource2[2] == 0 || maskDestination2[2] == 0 || maskSource2[3] == 0 || maskDestination2[3] == 0) {
                    if (ipAddrSource2[2] == source1[2] || ipAddrDestination2[2] == source1[2] ||
                            ipAddrSource2[3] == source1[3] || ipAddrDestination2[3] == source1[3] ) {
                        System.out.println("Packet from " + sourceAddresses.get(0) + " to " + destinationAddresses.get(0) + " on port "
                                + secondPort.get(0) + " denied");
                    } else if (!portArray.get(0).equals(secondPort.get(0))) {
                        System.out.println("Packet from " + sourceAddresses.get(0) + " to " + destinationAddresses.get(0) + " on port "
                                + secondPort.get(0) + " permitted");
                    }

                    if (ipAddrSource2[2] == source2[2] || ipAddrDestination2[2] == source2[2] ||
                            ipAddrSource2[3] == source2[3] || ipAddrDestination2[3] == source2[3]) {
                        System.out.println("Packet from " + sourceAddresses.get(1) + " to " + destinationAddresses.get(1) + " on port "
                                + secondPort.get(1) + " denied");
                    } else if (!portArray.get(0).equals(secondPort.get(1))) {
                        System.out.println("Packet from " + sourceAddresses.get(1) + " to " + destinationAddresses.get(1) + " on port "
                                + secondPort.get(1) + " permitted");
                    }
                    if (ipAddrSource2[2] == source3[2] || ipAddrDestination2[2] == source3[2] ||
                            ipAddrSource2[3] == source3[3] || ipAddrDestination2[3] == source3[3]) {
                        System.out.println("Packet from " + sourceAddresses.get(2) + " to " + destinationAddresses.get(2) + " on port "
                                + secondPort.get(2) + " denied");
                    } else if (!portArray.get(0).equals(secondPort.get(2))) {
                        System.out.println("Packet from " + sourceAddresses.get(2) + " to " + destinationAddresses.get(2) + " on port "
                                + secondPort.get(2) + " permitted");
                    }
                }
            }
            //when status2 == permit
            else{
                if (maskSource2[2] == 0 || maskDestination2[2] == 0 || maskSource2[3] == 0 || maskDestination2[3] == 0) {
                    if (ipAddrSource2[2] == source1[2] || ipAddrDestination2[2] == source1[2] ||
                            ipAddrSource2[3] == source1[3] || ipAddrDestination2[3] == source1[3]) {
                        System.out.println("Packet from " + sourceAddresses.get(0) + " to " + destinationAddresses.get(0) + " on port "
                                + secondPort.get(0) + " permitted");
                    } else if (!portArray.get(0).equals(secondPort.get(0))) {
                        System.out.println("Packet from " + sourceAddresses.get(0) + " to " + destinationAddresses.get(0) + " on port "
                                + secondPort.get(0) + " deny");
                    }
                    if (ipAddrSource2[2] == source2[2] || ipAddrDestination2[2] == source2[2] ||
                            ipAddrSource2[3] == source2[3] || ipAddrDestination2[3] == source2[3]) {
                        System.out.println("Packet from " + sourceAddresses.get(1) + " to " + destinationAddresses.get(1) + " on port "
                                + secondPort.get(1) + " permitted");
                    } else if (!portArray.get(0).equals(secondPort.get(1))) {
                        System.out.println("Packet from " + sourceAddresses.get(1) + " to " + destinationAddresses.get(1) + " on port "
                                + secondPort.get(1) + " denied");
                    }
                    if (ipAddrSource2[2] == source3[2] || ipAddrDestination2[2] == source3[2] ||
                            ipAddrSource2[3] == source3[3] || ipAddrDestination2[3] == source3[3]) {
                        System.out.println("Packet from " + sourceAddresses.get(2) + " to " + destinationAddresses.get(2) + " on port "
                                + secondPort.get(2) + " permitted");
                    } else if (!portArray.get(0).equals(secondPort.get(2))) {
                        System.out.println("Packet from " + sourceAddresses.get(2) + " to " + destinationAddresses.get(2) + " on port "
                                + secondPort.get(2) + " denied");
                    }
                }
            }
        }
    }
}

