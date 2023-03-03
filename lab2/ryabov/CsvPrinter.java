import java.io.IOException;

public class CsvPrinter {
    static void print(CsvReader reader) throws IOException {
        var dnsInfos = reader.read();
        System.out.println("-----------------------------------");
        for (var dnsInfo : dnsInfos) {
            System.out.println("Address: " + dnsInfo.address());
            System.out.println("Average time: " + dnsInfo.time() + " ms");
            System.out.println("Date: " + dnsInfo.date());
            System.out.println("-----------------------------------");
        }
    }
}
