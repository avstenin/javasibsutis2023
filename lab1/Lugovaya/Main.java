import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.Collections;

public class Main {
    public static void main(String[] args) {

        ServerInfo[] servers = new ServerInfo[3];
        for (int i = 0; i < 3; i++){
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                System.out.print("Enter DNS server address: ");
                String hostname = reader.readLine();

                Process process = Runtime.getRuntime().exec("ping " + hostname);

                BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line;
                float averageResponseTime = -1;

                InetAddress dnsServer = InetAddress.getByName(hostname);
                boolean dnsServerReachable = dnsServer.isReachable(5000);

                if (dnsServerReachable){
                    while ((line = input.readLine()) != null) {
                        String[] words = line.split(" ");
                        for (String word : words) {
                            try {
                                float number = Float.parseFloat(word);
                                averageResponseTime = number;
                            } catch (NumberFormatException e) {
                            }
                        }
                    }
                }

                servers[i] = new ServerInfo(averageResponseTime, hostname);
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        Arrays.sort(servers, Collections.reverseOrder());

        for (int i = 0; i < servers.length; i++) {
            if (servers[i].getAverageTime()!=-1.0){
                System.out.println("Server " + servers[i].getServerAddress() + " has an average response time: " + servers[i].getAverageTime() + " ms");
            } else{
                System.out.println("Server " + servers[i].getServerAddress() +" is unavailable");
            }
        }

    }
}
