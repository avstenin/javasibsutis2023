import java.io.Serializable;

public class ServerInfo implements Comparable, Serializable
{
    private Float averageTime = -1.0f;
    private String serverAddress;

    public ServerInfo(Float averageTime, String serverAddress) {
        this.averageTime = averageTime;
        this.serverAddress = serverAddress;
    }

    public Float getAverageTime() {
        return averageTime;
    }

    public String getServerAddress() {
        return serverAddress;
    }

    @Override
    public int compareTo(Object obj)
    {
        ServerInfo tmp = (ServerInfo)obj;
        if(this.averageTime < tmp.averageTime)
        {
            return -1;
        }
        else if(this.averageTime > tmp.averageTime)
        {
            return 1;
        }
        return 0;
    }
}