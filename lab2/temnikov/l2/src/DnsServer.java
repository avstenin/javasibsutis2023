import java.util.Arrays;

public class DnsServer {
    private String ipAddress = "";
    private Double[] responseTime;

    public DnsServer() {
        responseTime = new Double[2];
    }

    public DnsServer(String str, double newResponseTimeOne, double newResponseTimeTwo) {
        this();
        setResponseTime(newResponseTimeOne, newResponseTimeTwo);
        setIpAddress(str);
    }
    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String newIpAddress) {
        ipAddress = newIpAddress;
    }

    public Double[] getResponseTime() {
        return Arrays.copyOf(responseTime, responseTime.length);
    }

    public void setResponseTime(double newResponseTimeOne, double newResponseTimeTwo) {
        responseTime[0] = newResponseTimeOne;
        responseTime[1] = newResponseTimeTwo;
    }

    public void setResponseTimeOne(double newResponseTimeOne) {
        responseTime[0] = newResponseTimeOne;
    }

    public void setResponseTimeTwo(double newResponseTimeTwo) {
        responseTime[1] = newResponseTimeTwo;
    }
}
