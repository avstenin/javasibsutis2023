import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner input = new Scanner(System.in);
//      Input DNS to connect
        ArrayList<String> sites = new ArrayList<>();
        for (int i = 0; i < 3; ++i) {
            System.out.println("Please enter a address " + (i + 1) + ":");
            sites.add(input.next());
        }
        int[] pingTime = {0,0,0};
        for (int i=0; i<3; i++) {
            System.out.println("Connecting site " + sites.get(i));
            pingTime[i] = getAverageTimeToPingSite(sites.get(i));
        }
//      Sorting sites by ping time
        for (int j = 0; j<2;j++) {
            for (int i = 1; i < 3; i++) {
                if (pingTime[i] > pingTime[i - 1]) {
                    int t = pingTime[i - 1];
                    pingTime[i - 1] = pingTime[i];
                    pingTime[i] = t;
                    Collections.swap(sites, i, i - 1);
                }
            }
        }
//      check for
        for (int i=0; i<3; i++) {
            if (pingTime[i]>10000){
                System.out.println("Site " + sites.get(i) + " is unreachable");
            }
            else {
                System.out.println("Average response time for site \"" + sites.get(i) + "\" " + "is " + pingTime[i] + "ms");
            }
        }
    }

    private static int getAverageTimeToPingSite(String site) throws Exception {
//      boolean isWindows = System.getProperty("os.name").toLowerCase().startsWith("windows");
        ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", "ping " + site);
        builder.redirectErrorStream();
        Proces s process = builder.start();
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        String[] b = {};
        while ((line = reader.readLine()) != null) {
            b = line.split(" ");
        }
        String result = b[b.length - 2];
        if (result.contains("%")) {
            result = "10001";
        }
        return Integer.parseInt(result);
    }
}