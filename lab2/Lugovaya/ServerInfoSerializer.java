import java.io.*;

public class ServerInfoSerializer {
    public static void save(ServerInfo[] servers, String fileName) {
        try {
            FileOutputStream fileOut = new FileOutputStream(fileName);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(servers);
            out.close();
            fileOut.close();
            System.out.println("Serialized data is saved in " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
