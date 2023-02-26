import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
Система доменных имен (DNS, англ. Domain Name System) позволяет
обращаться к хосту по имени домена, например mail.com – домен второго
уровня, www.mail.com – третьего. Каждый запрос от ПК пользователя с
указанием такого имени сначала обращается к системе DNS, основной и
резервный адрес которой указан на сетевом адаптере. Часто запросы к DNS
могут быть продолжительны по времени прохождения пакетов по сети,
необходимо определить какой из 3-х заданных DNS адресов с клавиатуры
имеет наименьшее среднее время отклика.

Паста для ввода:

www.mail.ru
www.amazon.com
www.google.com

*/

public class Main {
    // list for test
    private static final String commandToPing = "ping";
    private static final int amountOfSites = 3;
    public static void main(String[] args) throws Exception {
        Scanner input = new Scanner(System.in);
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < amountOfSites; ++i) {
            System.out.println("Please enter a address " + (i + 1) + ":");
            list.add(input.next());
        }
        System.out.println("Result:");
        for (String i: list) {
            String time = getAverageTimeToPingSite(i);
            System.out.println("Average time to ping site \"" + i + "\" " + "is " + time);
        }
    }

    private static String getAverageTimeToPingSite(String site) throws Exception {
        ProcessBuilder builder = new ProcessBuilder(
                "cmd.exe", "/c", commandToPing + " " + site);
        builder.redirectErrorStream();
        Process process = builder.start();
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(process.getInputStream()));
        String answer = "";
        String result = "";
        String[] b = {};
        while (true) {
            answer = reader.readLine();
            if (answer == null) {
                break;
            }
            b = answer.split(" ");
        }
        result = "Average time = " + b[b.length - 2] + "ms";
        return result;
    }
}