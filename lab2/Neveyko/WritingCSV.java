import java.io.IOException;
import java.io.File;
import java.io.FileWriter;
public class WritingCSV {
    private File file;
    public WritingCSV() throws IOException {
        file = new File("dns_stat.csv");
        if (!file.exists()) {
            file.createNewFile();
            try (var writer = new FileWriter(file, true)) {
                writer.write("address,avg time\n");
            }
        }
    }
    public void write(String[] dns_info_array, int dns_count) throws IOException {
        try (var writer = new FileWriter(file, true)) {
            for (var i = 0; i < dns_count; ++i) {
                StringBuilder sb = new StringBuilder();
                writer.write(sb.append(dns_info_array[i]).
                        append(",").append(dns_info_array[i + dns_count]).
                        append(" ms,").append("\n").toString());
            }
        }
    }
}