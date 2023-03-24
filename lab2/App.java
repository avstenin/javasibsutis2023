import java.io.FileNotFoundException;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class App {

    static Comparator<DNSrequest> comparator = new Comparator<DNSrequest>() {
        @Override
        public int compare(DNSrequest left, DNSrequest right) {
            return left.ipTime - right.ipTime;
        }
    };

    public static void main(String[] args) throws FileNotFoundException {

        ArrayList<DNSrequest> ipObject = new ArrayList<DNSrequest>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
        Scanner in = new Scanner(System.in);

        System.out.print("Enter count of DNS servers: ");        
        int queryNumber = Integer.parseInt(in.nextLine());

        for (int i = 0; i < queryNumber; i++) {
            System.out.print("Input a ip[" + (i + 1) + "]:");
            ipObject.add(new DNSrequest(in.nextLine(), 0, ""));
            ipObject.get(i).correctIP();
        }
        in.close();
        for (int i = 0; i < queryNumber; i++)
            try {
                InetAddress address = InetAddress.getByName(ipObject.get(i).ipString);
                long startTime = System.currentTimeMillis();
                boolean reachable = address.isReachable(1000);
                long endTime = System.currentTimeMillis();
                if (reachable == true) {
                    ipObject.set(i, new DNSrequest(ipObject.get(i).ipString, Math.toIntExact(endTime - startTime), sdf.format((new Date())).toString()));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        Collections.sort(ipObject, comparator);

        for (int i = 0; i < queryNumber; i++) {
            if (ipObject.get(i).ipTime == 0)
                System.out.println(ipObject.get(i).ipString + " not reachable!");
            else
                System.out.println(ipObject.get(i).ipString + " time = " + ipObject.get(i).ipTime);
        }
        DataWorking mew = new DataWorking();
        mew.writeData("text.txt", ipObject);
        mew.printData(mew.readData("text.txt"));
    }
}