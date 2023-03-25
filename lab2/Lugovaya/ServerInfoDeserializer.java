import java.io.*;

public class ServerInfoDeserializer {
    public static ServerInfo[] load(String fileName) {
        ServerInfo[] servers = null;
        try {
            FileInputStream fileIn = new FileInputStream(fileName);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            servers = (ServerInfo[]) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return servers;
    }
}