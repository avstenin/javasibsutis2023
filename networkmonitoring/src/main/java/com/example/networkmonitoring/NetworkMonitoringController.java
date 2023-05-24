package com.example.networkmonitoring;

import org.springframework.stereotype.Controller;
import java.util.Map;

@Controller
public class NetworkMonitoringController {

    private NetworkMonitoringCore monitoringCore;

    public String renderHomePage() {
        Map<String, Double> parameters = monitoringCore.getMonitoredParameters();

        StringBuilder htmlBuilder = new StringBuilder();
        htmlBuilder.append("<html><body>");
        htmlBuilder.append("<h1>Network Monitoring Dashboard</h1>");

        htmlBuilder.append("<h2>Monitored Parameters:</h2>");
        htmlBuilder.append("<ul>");
        for (Map.Entry<String, Double> entry : parameters.entrySet()) {
            htmlBuilder.append("<li>")
                       .append(entry.getKey())
                       .append(": ")
                       .append(entry.getValue())
                       .append("</li>");
        }
        htmlBuilder.append("</ul>");

        htmlBuilder.append("</body></html>");

        return htmlBuilder.toString();
    }
}