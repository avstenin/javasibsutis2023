import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class CsvWriter {
    private final File file;

    public CsvWriter(String fileName) throws IOException {
        file = new File(fileName);
        if (!file.exists()) {
            file.createNewFile();
            try (var writer = new FileWriter(file, true)) {
                writer.write("Address,Average time,Date\n");
            }
        }
    }

    public void write(ArrayList<DnsInfos> dnsInfos) throws IOException {
        try (var writer = new FileWriter(file, true)) {
            for (var dnsInfo : dnsInfos) {
                var builder = new StringBuilder();
                writer.write(builder.append(dnsInfo.address()).
                        append(",").append(dnsInfo.time()).
                        append(" ms,").append(dnsInfo.date()).
                        append("\n").toString());
            }
        }
    }
}
