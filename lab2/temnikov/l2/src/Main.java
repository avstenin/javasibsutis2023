import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of DNS servers: ");
        int numbDnsServers = scanner.nextInt();
        String[] dnsServers = new String[numbDnsServers];
        for(int i = 0; i < numbDnsServers; ++i) {
            System.out.print("Enter the IP address of DNS server " + (i+1) + ": ");
            dnsServers[i] = scanner.next();
        }
    }
}