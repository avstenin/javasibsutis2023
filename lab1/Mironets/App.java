import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class App {

    String ipString;
    int ipTime;

    App(String ipString,  int ipTime){
        this.ipString = ipString;
        this.ipTime = ipTime;
    }

    static int ipNum = 3;

    static Comparator<App> comparator = new Comparator<App>() {
        @Override
        public int compare(App left, App right) {
            return left.ipTime - right.ipTime; 
        }
    };

    public static void main(String[] args) {

        ArrayList<App> ipObject = new ArrayList<App>(ipNum);

        Scanner in = new Scanner(System.in);
        for (int i = 0; i < ipNum; i++) {
            System.out.print("Input a ip[" + (i+1) + "]:");
            ipObject.add(new App(in.nextLine(), 0));

            String IPADDRESS_PATTERN = "(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)";
            Pattern pattern = Pattern.compile(IPADDRESS_PATTERN);
            Matcher matcher = pattern.matcher(ipObject.get(i).ipString);

            if (matcher.find() == false) {
                System.out.println("Error ip. Try '0.0.0.0'");
                System.exit(0);
            }
        }
        in.close();

        for (int i = 0; i < ipNum; i++)
        try {
            InetAddress address = InetAddress.getByName(ipObject.get(i).ipString);

            long startTime = System.currentTimeMillis();
            boolean reachable = address.isReachable(10000);
            long endTime = System.currentTimeMillis();
            if (reachable == true) {
                ipObject.set(i, new App(ipObject.get(i).ipString, Math.toIntExact(endTime - startTime))); 
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Collections.sort(ipObject, comparator);

        for (int i = 0; i < ipNum; i++){
            if (ipObject.get(i).ipTime == 0) 
            System.out.println(ipObject.get(i).ipString + " not reachable!");
            else System.out.println(ipObject.get(i).ipString + " time = " + ipObject.get(i).ipTime);
        }
    }
}