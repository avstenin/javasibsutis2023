import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.regex.Pattern;

public class DnsInfoFilling {
    final static String pingCount = "5";
    public void fillingInfo(String[] dns_info_array, int dns_count) throws IOException {
        ProcessBuilder processBuilder = new ProcessBuilder();
        for (var i = 0; i < dns_count; ++i) {
            var isWindows = System.getProperty("os.name").toLowerCase().startsWith("windows");
            processBuilder.command("cmd.exe", "/c", "ping ", isWindows ? "-n" : "-c", pingCount, dns_info_array[i]);

            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            int dns_ping = 0;
            while ((line = reader.readLine()) != null) {
                var pattern = Pattern.compile("time=([0-9]+)ms");
                var matcher = pattern.matcher(line);
                if (matcher.find()) {
                    dns_ping += Integer.parseInt(matcher.group(1));
                }
            }
            dns_ping /= Integer.parseInt(pingCount);
            dns_info_array[i + dns_count] = Integer.toString(dns_ping);
        }
    }
}