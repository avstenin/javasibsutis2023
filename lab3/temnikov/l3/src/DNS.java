import java.io.IOException;
import java.net.InetAddress;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class DNS implements Comparable<DNS>{
    private final String dnsIP;
    private final Duration time;

    public DNS(String newDnsIP, Duration newTime)
    {
        dnsIP = newDnsIP;
        time = newTime;
    }

    public Duration getTime()
    {
        return this.time;
    }

    public String getIP()
    {
        return this.dnsIP;
    }

    @Override
    public int compareTo(DNS o)
    {
        return Long.compare(this.time.toMillis(),o.time.toMillis());
    }

    public void printInfo() {
        System.out.println(dnsIP + " " + time.toMillis());
    }

    public static boolean isValidDNSServer(String dnsServer) {
        String[] parts = dnsServer.split("\\.");
        if (parts.length != 4) {
            return false;
        }

        for (String part : parts) {
            try {
                int number = Integer.parseInt(part);
                if (number < 0 || number > 255) {
                    return false;
                }
            } catch (NumberFormatException e) {
                return false;
            }
        }

        return true; // DNS сервер корректен
    }

    public static Duration pingDNS(String dnsServer) {
        try {
            InetAddress address = InetAddress.getByName(dnsServer);
            long startTime = System.currentTimeMillis();
            if (address.isReachable(5000)) {
                long endTime = System.currentTimeMillis();
                return Duration.ofMillis(endTime - startTime);
            } else {
                System.err.println("Host " + dnsServer + " is not reachable");
            }
        } catch (IOException e) {
            System.err.println("Error checking host " + dnsServer + ": " + e.getMessage());
        }
        return null;
    }

}
