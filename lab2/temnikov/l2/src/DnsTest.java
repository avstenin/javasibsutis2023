import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DnsTest {
    private List<String> dnsAddresses;
    private Map<String, Double> responseTimes;
    DnsTest() {
        dnsAddresses = new ArrayList<String>();
        responseTimes = new HashMap<String, Double>();
    }

    public void calcServerResponseTime() {
        for(int i = 0; i < dnsAddresses.size(); i++) {
            try {
                InetAddress address = InetAddress.getByName(dnsAddresses.get(i));
                long startTime = System.currentTimeMillis();
                if (address.isReachable(5000)) {
                    long endTime = System.currentTimeMillis();
                    responseTimes.put(dnsAddresses.get(i), (double)(endTime - startTime));
                } else {
                    System.err.println("Host " + dnsAddresses.get(i) + " is not reachable");
                }
            } catch (IOException e) {
                System.err.println("Error checking host " + dnsAddresses.get(i) + ": " + e.getMessage());
            }
        }
    }

    public void addAddress(String newAddress) {
        dnsAddresses.add(newAddress);
    }

    public void printTime() {
        System.out.println(responseTimes);
    }

    public Map<String, Double> getResponseTimes() {
        return new HashMap<String, Double>(responseTimes);
    }

    public static boolean isValidDNS(String dnsName) {
        boolean isValid = false;

        String regex = "^([a-zA-Z0-9]([a-zA-Z0-9\\-]{0,61}[a-zA-Z0-9])?\\.)+[a-zA-Z]{2,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(dnsName);
        isValid = matcher.matches();
        if(!isValid) {
            try {
                InetAddress address = InetAddress.getByName(dnsName);
                isValid = true;
            } catch (UnknownHostException e) {
                isValid = false;
            }
        }
        return isValid;
    }
}
