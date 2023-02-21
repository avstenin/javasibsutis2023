import java.io.IOException;
import java.util.Scanner;

public class Main {
    final private static int queryNumber = 3;

    public static void main(String[] args) {
        try (var input = new Scanner(System.in)) {
            var speedMeasurer = new DNSSpeedMeasurer();
            for (var i = 0; i < queryNumber; ++i) {
                System.out.println("Enter DNS server address:");
                var ipAddress = input.next();
                try {
                    speedMeasurer.add(ipAddress);
                } catch (IOException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            speedMeasurer.printResults();
        }
    }
}