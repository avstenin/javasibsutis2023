import java.io.*;
import java.util.*;

public class FileScanner {
    public static void scan(String directoryName) {
        File directory = new File(directoryName);
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    scan(file.getAbsolutePath());
                } else {
                    if (file.getName().endsWith(".ser")) {
                        ServerInfo[] servers = ServerInfoDeserializer.load(file.getAbsolutePath());

                        if (servers != null){
                            for (int i = 0; i < servers.length; i++) {
                                if (servers[i].getAverageTime()!=-1.0){
                                    System.out.println("Server " + servers[i].getServerAddress() + " has an average response time: " + servers[i].getAverageTime() + " ms");
                                } else{
                                    System.out.println("Server " + servers[i].getServerAddress() +" is unavailable");
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
