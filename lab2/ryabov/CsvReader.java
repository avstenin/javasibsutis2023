import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CsvReader {
    private final String fileName;

    public CsvReader(String fileName) {
        this.fileName = fileName;
    }

    public ArrayList<DnsInfos> read() throws IOException {
        var dnsInfos = new ArrayList<DnsInfos>();
        try (var reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            reader.readLine(); // read header
            while ((line = reader.readLine()) != null) {
                var splitLine = line.split(",");
                dnsInfos.add(new DnsInfos(splitLine[0], Double.parseDouble(splitLine[1].replaceAll("[^\\d.]", "")), splitLine[2]));
            }
        }
        return dnsInfos;
    }
}
