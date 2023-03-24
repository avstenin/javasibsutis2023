import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class DataWorking {

    public void writeData(String fileName, ArrayList<DNSrequest> dnSrequests) throws FileNotFoundException {
        PrintWriter file = new PrintWriter(new FileOutputStream(fileName));
        int sum = 0;
        file.println("DNS IP\tTime ms.\tDate");
        for (DNSrequest str : dnSrequests) {
            if (str.ipTime != 0)
                file.println(str.ipString + "\t" + str.ipTime + "\t" + str.date);
            sum += str.ipTime;
        }

        file.print("average time = " + (sum / dnSrequests.size()));
        file.close();
    }

    public ArrayList<DNSrequest> readData(String fileName) throws FileNotFoundException {
        ArrayList<DNSrequest> readArray = new ArrayList<>();
        try (Scanner s = new Scanner(new FileReader(fileName))) {
            while (s.hasNext()) {
                if (s.nextLine().contains(".*[a-zA-Z].*")){
                System.out.println("!!!" + s.nextLine());
                 String[] splitLine = s.nextLine().split("\t");
                 System.out.println("aaa " + splitLine[0] + "fdfdf");
                 System.out.println("bb " + splitLine[1]);
                 System.out.println("b " + splitLine[2]);
                 readArray.add(new DNSrequest(splitLine[0], Integer.parseInt(splitLine[1]),
                 splitLine[2]));
                }
            }
        }
        return readArray;
    }

    public void printData(ArrayList<DNSrequest> list) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).ipTime == 0)
                System.out.println(list.get(i).ipString + " not reachable!");
            else
                System.out.println(list.get(i).ipString + " time = " + list.get(i).ipTime);
        }
    }
}