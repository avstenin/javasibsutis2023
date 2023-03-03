import java.io.IOException;
import java.util.Collections;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (var input = new Scanner(System.in)) {
            System.out.print("Enter count of DNS servers: ");
            var queryNumber = Integer.parseInt(input.next());
            var speedMeasurer = new DnsSpeedMeasurer();
            for (var i = 0; i < queryNumber; ++i) {
                System.out.println("Enter DNS server address:");
                speedMeasurer.add(input.next());
            }
            Collections.sort(speedMeasurer.dnsInfos());
            var writer = new CsvWriter("statistics.csv");
            writer.write(speedMeasurer.dnsInfos());
            CsvPrinter.print(new CsvReader("statistics.csv"));
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}