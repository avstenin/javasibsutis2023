import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Pattern;

record DNSInfos(String address, Double time) implements Comparable<DNSInfos> {
    @Override
    public int compareTo(DNSInfos o) {
        return this.time.compareTo(o.time);
    }
}

public class DNSSpeedMeasurer {
    final private static int pingNumber = 10;
    private final ArrayList<DNSInfos> dnsInfos = new ArrayList<>();

    private Double getTimeFromPingInfo(String message) {
        var pattern = Pattern.compile("time=([\\.0-9]+)");
        var matcher = pattern.matcher(message);
        if (!matcher.find()) {
            return 0.;
        }
        return Double.parseDouble(matcher.group(1));
    }

    public void add(String dnsAddress) throws IOException, InterruptedException {
        var isWindows = System.getProperty("os.name").toLowerCase().startsWith("windows");
        var builder = new ProcessBuilder("ping", isWindows ? "-n" : "-c", Integer.toString(pingNumber), dnsAddress);
        var process = builder.start();
        var reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line = null;
        double time = 0;
        while ((line = reader.readLine()) != null) {
            time += getTimeFromPingInfo(line);
        }
        time /= pingNumber;
        dnsInfos.add(new DNSInfos(dnsAddress, time));
    }

    public void printResults() {
        Collections.sort(dnsInfos);
        System.out.println("Address\tAverage time");
        for (var dnsInfo : dnsInfos) {
            System.out.println(dnsInfo.address() + "\t" + dnsInfo.time() + " ms");
        }
    }
}
