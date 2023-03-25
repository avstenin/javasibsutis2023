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
                Process process = Runtime.getRuntime().exec("ping -n 10 " + dnsAddresses.get(i));
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line;
                long sum = 0;
                int count = 0;
                double time = -1.0;
                while ((line = reader.readLine()) != null) {
                    if (line.contains("time=")) {
                        int startIndex = line.indexOf("time=")+5;
                        int endIndex = line.indexOf("ms", startIndex);
                        String averageTimeStr = line.substring(startIndex, endIndex).trim();
                        time += Long.parseLong(averageTimeStr);
                    }
                }
                time /= 10;
                responseTimes.put(dnsAddresses.get(i), time);
            } catch (IOException e) {
                System.err.println("Error sending ping request to " + dnsAddresses.get(i) + ": " + e.getMessage());
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
