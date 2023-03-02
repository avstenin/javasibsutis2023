import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class CsvWriter {
    private final File file;

    public CsvWriter(String path) throws IOException {
        file = new File(path);
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
                        append(" ms,").append(new Date()).
                        append("\n").toString());
            }
        }
    }
}
