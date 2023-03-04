import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.HashMap;
import java.util.Map;

public class pingDNS {
    final static int pingCount = 5;
    final static int countOfAddresses = 3;
    public static void main(String[] args) {
        var input = new Scanner(System.in);

        Map<String, Integer> address_pingMap = new HashMap<>();
        ProcessBuilder processBuilder = new ProcessBuilder();
        for(int i = 0; i < countOfAddresses; ++i) {
            System.out.println("Enter DNS server address:");
            var dns_address = input.next();
            var arrayListOfTimes = 0;
            processBuilder.command("cmd.exe", "/c", "ping -n " + pingCount + " " + dns_address);
            try {
                Process process = processBuilder.start();
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line;

                while ((line = reader.readLine()) != null) {
                    var pattern = Pattern.compile("time=([0-9]+)ms");
                    var matcher = pattern.matcher(line);
                    if (matcher.find()) {
                        arrayListOfTimes += Integer.parseInt(matcher.group(1));
                    }
                }
            }catch (IOException e) {
                e.printStackTrace();
            }
            arrayListOfTimes /= pingCount;
            address_pingMap.put(dns_address, arrayListOfTimes);
        }
        address_pingMap.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .forEach(System.out::println);
    }
}