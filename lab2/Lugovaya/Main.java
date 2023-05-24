import java.io.*;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter number of DNS servers: ");
        int n = scanner.nextInt();

        ServerInfo[] servers = new ServerInfo[n];
        for (int i = 0; i < n; i++){
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

        System.out.println("Save data? yes/no");

        String input = scanner.next();

        if (Objects.equals(input, "yes")){
            System.out.println("Enter the name of the file:");

            String name = scanner.next();

            ServerInfoSerializer.save(servers, "./src/dir/"+name);

            FileScanner.scan("./src/dir");
        }

    }
}
