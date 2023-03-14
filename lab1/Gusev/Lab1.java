import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.HashMap;
import java.util.Map;


public class Lab1 {
    public static void main(String[] args) {
        var scan = new Scanner(System.in);
        int countPing = 5;
        int countDnsAddress = 3;
        double avgTime = 0;

        Map<String, Double> dnsAddresses = new HashMap<String, Double>();
        ProcessBuilder builder = new ProcessBuilder();

        String[] dnsAddress = new String[countDnsAddress];

        for (var i = 0; i < countDnsAddress; i++) {
            System.out.println("Введите DNS адрес:");
            dnsAddress[i] = scan.next();
        }

        for (var i = 0; i < countDnsAddress; i++) {
            builder.command("cmd.exe", "/c", "ping -n " + Integer.toString(countPing) + " " + dnsAddress[i]);

            try {
                Process p = builder.start();
                BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
                String line;

                while ((line = r.readLine()) != null) {
                    var pattern = Pattern.compile("time=([0-9]+)ms");
                    var matcher = pattern.matcher(line);

                        if (matcher.find()) {
                             avgTime += Double.parseDouble(matcher.group(1));
                        }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            avgTime /= countPing;
            dnsAddresses.put(dnsAddress[i], avgTime);;
        }
        dnsAddresses.entrySet().stream().sorted(Map.Entry.<String, Double>comparingByValue().reversed());

        for(Map.Entry map: dnsAddresses.entrySet()) {
            System.out.println(map.getKey() + " Time "+ map.getValue());
        }
    }
}
