import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of DNS servers: ");
        int numbDnsServers = scanner.nextInt();
        DnsTest dnsTest = new DnsTest();
        for (int i = 0; i < numbDnsServers; i++) {
            System.out.print("Enter the IP address of DNS server " + (i+1) + ": ");
            String dnsAddress = scanner.next();
            if (DnsTest.isValidDNS(dnsAddress)) {
                dnsTest.addAddress(dnsAddress);
            } else {
                System.out.println("Invalid DNS address, skipping.");
            }
        }

        dnsTest.calcServerResponseTime();

        DnsServer[] servers = new DnsServer[numbDnsServers];
        Map<String, Double> responseTimes = dnsTest.getResponseTimes();
        int index = 0;
        for (String dnsAddress : responseTimes.keySet()) {
            double responseTime = responseTimes.get(dnsAddress);
            servers[index] = new DnsServer(dnsAddress, responseTime, 0.0);
            index++;
        }

        DnsServerData.writeDataToFile(servers);

        List<DnsServer> serversFromFile = DnsServerData.readFilesFromDirectory();
        for (DnsServer server : serversFromFile) {
            System.out.println(server.getIpAddress() + ": " + Arrays.toString(server.getResponseTime()));
        }
    }

}
