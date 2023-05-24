import java.time.Duration;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ArrayList<DNS> DNSPairs = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println();
            System.out.println("Выберите действие:");
            System.out.println("1) Написать DNS сервера с клавиатуры");
            System.out.println("2) Считать из файла");
            System.out.println("3) Посмотреть историю поиска");
            System.out.println("4) Посмотреть результаты текущего поиска");
            System.out.println("5) Выйти из программы");

            choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                        System.out.print("Введите DNS сервер: ");
                        String dnsServer = scanner.nextLine();
                        if (DNS.isValidDNSServer(dnsServer)) {
                            Duration pingTime = DNS.pingDNS(dnsServer);
                            if (pingTime != null) {
                                DNS dns = new DNS(dnsServer, pingTime);
                                DNSPairs.add(dns);
                            }
                        } else {
                            System.out.println("Некорректный DNS сервер.");
                        }
                    break;
                case 2:
                    System.out.print("Введите имя файла: ");
                    String filename = scanner.nextLine();
                    ArrayList<DNS> readDNSPairs = FileDns.readDNSFromFile(filename);
                    if (readDNSPairs != null) {
                        DNSPairs.addAll(readDNSPairs);
                    }
                    break;
                case 3:
                    FileDns.outFileHistory();
                    break;
                case 4:
                    if(!DNSPairs.isEmpty()) {
                        for (DNS dns : DNSPairs) {
                            dns.printInfo();
                        }
                    } else {
                        System.out.println("Список пуст");
                    }
                    break;
                case 5:
                    FileDns.writeDataToFile(DNSPairs);
                    System.out.println("Данные сохранены в файл. Программа завершена.");
                    break;
                default:
                    System.out.println("Некорректный выбор.");
                    break;
            }

        } while (choice != 5);

        FileDns.writeDataToFile(DNSPairs);
    }
}
