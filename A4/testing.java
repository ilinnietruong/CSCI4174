import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class testing {
    public static void main(String[] args) throws FileNotFoundException {
        ArrayList<String> ACLStatus = new ArrayList<>();
        ArrayList<String> ipArray = new ArrayList<>();

        ArrayList<String> sourceIPAddressesArray = new ArrayList();


        String standardACL = "";
        String sourceIPAddresses = "";


        Scanner kb = new Scanner(new File("standardTextFile1.txt"));
        Scanner kb2 = new Scanner(new File("standardTextFile2.txt"));

        while (kb.hasNext()) {

            standardACL = kb.next();
            if (standardACL.contains("deny") || standardACL.contains("permit")) {
                ACLStatus.add(standardACL);
            }
            /*Got the regex code from: https://stackoverflow.com/questions/5667371/validate-ipv4-address-in-java"*/
            String ipPattern = "^((0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)\\.){3}(0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)$";
            if (standardACL.matches(ipPattern)) {
                ipArray.add(standardACL);
            }
        }
        kb.close();

        ArrayList<String> sourceIPNum = new ArrayList<>();

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

        String mask1 ;
        String mask2;
        String ip1;
        String ip2;

        int i,j,k;

        ip1 = ipArray.get(0);
        mask1 = ipArray.get(1);
        ip2 = ipArray.get(2);
        mask2= ipArray.get(3);


        if(mask1.contains("0") || mask2.contains("0"))
        {
            int indexmask1 = mask1.indexOf("0");
            int indexmask2 = mask2.indexOf("0");


            if(ACLStatus.contains("deny"))
           {
               for(i=0; i<ip1.length(); i++)
               {

               }
           }
        }
        System.out.println(ip1+" " +mask1+" "+ip2+" "+mask2);




    }
}
