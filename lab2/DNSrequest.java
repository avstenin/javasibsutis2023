import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DNSrequest {

    String ipString;
    int ipTime;
    String date;

    public DNSrequest(String ipString,  int ipTime, String date){
        this.ipString = ipString;
        this.ipTime = ipTime;
        this.date = date;
    }

    public void correctIP(){
        String IPADDRESS_PATTERN = "(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)";
        Pattern pattern = Pattern.compile(IPADDRESS_PATTERN);
        Matcher matcher = pattern.matcher(ipString);

        if (matcher.find() == false) {
            System.out.println("Error ip. Try '0.0.0.0'");
            System.exit(0);
        }
    }
    
}