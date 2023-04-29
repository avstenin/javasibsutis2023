import java.io.*;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;

public class FileDns {
    public static void writeDataToFile(ArrayList<DNS> DNSPairs) {
        String filename = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(Calendar.getInstance().getTime());
        filename += ".txt";
        if (!DNSPairs.isEmpty()) {
            try (FileWriter file = new FileWriter(filename)) {
                for (DNS val : DNSPairs) {
                    if (val.getTime().toMillis() == Duration.ofDays(1).toMillis()) {
                        file.write("Host " + val.getIP() + " is not reachable\n");
                    } else {
                        file.write("Ping to " + val.getIP() + " is " + val.getTime().toMillis() + " ms\n");
                    }
                }
            } catch (Exception e) {
                System.err.println("Error write to file - " + filename);
                e.printStackTrace();
            }
        }
    }

    public static List<String> getMeasurementFiles() {
        List<String> measurementFiles = new ArrayList<>();
        String searchPattern = "\\d{4}-\\d{2}-\\d{2}_\\d{2}-\\d{2}-\\d{2}\\.txt";
        searchFiles(new File("."), searchPattern, measurementFiles);
        return measurementFiles;
    }

    public static void searchFiles(File directory, String searchPattern, List<String> result) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    searchFiles(file, searchPattern, result);
                } else if (file.getName().matches(searchPattern)) {
                    result.add(file.getName());
                }
            }
        }
    }

    public static void outFileHistory () {
        List<String> measurementFiles = getMeasurementFiles();

        if (measurementFiles.isEmpty()) {
            System.out.println("История измерений не найдена.");
        } else {
            System.out.println("История измерений по дате:");
            for (String filename : measurementFiles) {
                String dateTime = filename.substring(0, filename.lastIndexOf('.'));
                System.out.println(dateTime);
            }
        }
    }

    public static ArrayList<DNS> readDNSFromFile(String filename) {
        ArrayList<DNS> DNSPairs = new ArrayList<>();
        try (Scanner fileScanner = new Scanner(new File(filename))) {
            while (fileScanner.hasNextLine()) {
                String dnsServer = fileScanner.nextLine();
                if (DNS.isValidDNSServer(dnsServer)) {
                    Duration pingTime = DNS.pingDNS(dnsServer);
                    if (pingTime != null) {
                        DNS dns = new DNS(dnsServer, pingTime);
                        DNSPairs.add(dns);
                    }
                } else {
                    System.out.println("Некорректный DNS сервер: " + dnsServer);
                }
            }
        } catch (Exception e) {
            System.out.println("Ошибка при чтении файла: " + filename);
            return null;
        }
        return DNSPairs;
    }

}
