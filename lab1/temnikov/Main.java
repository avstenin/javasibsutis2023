import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        int numbServers = 2;
        String[] dnsAddresses = new String[numbServers];
        var input = new Scanner(System.in);

        DnsTest dnsTest = new DnsTest();

        boolean run = false;
        for (var i = 0; i < numbServers; ++i) {
            System.out.println("Enter DNS server address:"+(i+1)+"/"+numbServers);
            String ipAddress = input.next();
            if(DnsTest.isValidDNS(ipAddress)) {
                dnsTest.addAddress(ipAddress);
                run = true;
            } else {
                System.out.println("Invalid DNS address");
            }

        }
        if(run) {
            dnsTest.calcServerResponseTime();
            dnsTest.printTime();
        }
    }
}