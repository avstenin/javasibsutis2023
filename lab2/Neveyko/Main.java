import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (var input = new Scanner(System.in)){
            System.out.print("Enter count of DNS servers: ");
            var dns_count = Integer.parseInt(input.next());
            String[] dns_info_array = new String[2 * dns_count];
            for (var i = 0; i < dns_count; ++i) {
                System.out.println("Enter DNS server address: ");
                dns_info_array[i] = input.next();
            }
            var dns_info_filling = new DnsInfoFilling();
            dns_info_filling.fillingInfo(dns_info_array, dns_count);

            var writer = new WritingCSV();
            writer.write(dns_info_array, dns_count);

            var reader = new ReadingCSV();
            reader.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}