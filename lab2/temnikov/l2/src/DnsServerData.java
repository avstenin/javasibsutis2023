import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DnsServerData {
    private static final String FILENAME = "dns_servers.txt";
    public static void writeDataToFile(DnsServer[] servers) {
        try {
            FileWriter writer = new FileWriter(FILENAME);
            for (DnsServer server : servers) {
                writer.write(server.getIpAddress() + "," + server.getResponseTime()[0] + "," + server.getResponseTime()[1] + "\n");
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file.");
            e.printStackTrace();
        }
    }

    public static List<DnsServer> readFilesFromDirectory() {
        List<DnsServer> servers = new ArrayList<>();
        try {
            File file = new File(FILENAME);
            Scanner fileScanner = new Scanner(file);
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] values = line.split(",");
                String ipAddress = values[0];
                double responseTimeOne = Double.parseDouble(values[1]);
                double responseTimeTwo = Double.parseDouble(values[2]);
                DnsServer server = new DnsServer(ipAddress, responseTimeOne, responseTimeTwo);
                servers.add(server);
            }
            fileScanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("The file could not be found.");
            e.printStackTrace();
        }
        return servers;
    }
}
