import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
public class ReadingCSV {
    public void read() throws IOException {
        var reader = new BufferedReader(new FileReader("dns_stat.csv"));
        String line;
        var lines_count = 0;
        while ((line = reader.readLine()) != null) {
            if (lines_count != 0) {
                String[] dns_data = line.split(",");
                System.out.println("address = " + dns_data[0] + ", avg time = " + dns_data[1]);
            }
            ++lines_count;
        }
    }
}