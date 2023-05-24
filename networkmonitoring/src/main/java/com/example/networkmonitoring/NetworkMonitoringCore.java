package com.example.networkmonitoring;

import java.util.HashMap;
import java.util.Map;
import java.io.File;
import java.io.FileInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Properties;
import com.sun.management.OperatingSystemMXBean;
import java.lang.management.ManagementFactory;

public class NetworkMonitoringCore {

    private Map<String, Double> monitoredParameters;
    private String configFilePath;

    public NetworkMonitoringCore(String configFilePath) {
        this.monitoredParameters = new HashMap<>();
        this.configFilePath = configFilePath;
        loadConfig();
    }

    private void loadConfig() {
        Properties properties = new Properties();

        try (FileInputStream fileInputStream = new FileInputStream(new File(configFilePath))) {
            properties.load(fileInputStream);

            String monitoredParametersStr = properties.getProperty("monitored_parameters");
            String[] monitoredParametersArray = monitoredParametersStr.split(",");
            
            for (String parameter : monitoredParametersArray) {
                monitoredParameters.put(parameter, 0.0);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void monitorParameters() {
        double cpuLoad = getCPULoad();
        monitoredParameters.put("CPU Load", cpuLoad);

        double memoryUsage = getMemoryUsage();
        monitoredParameters.put("Memory Usage", memoryUsage);

        double networkTraffic = getNetworkTraffic();
        monitoredParameters.put("Network Traffic", networkTraffic);
    }

    public Map<String, Double> getMonitoredParameters() {
        return monitoredParameters;
    }

    public double getCPULoad() {
        OperatingSystemMXBean osBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        double cpuLoad = osBean.getProcessCpuLoad();
        
        if (cpuLoad < 0) {
            cpuLoad = 0.0;
        }
    
        return cpuLoad;
    }

    public double getMemoryUsage() {
        OperatingSystemMXBean osBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        long totalMemory = osBean.getTotalMemorySize();
        long freeMemory = osBean.getFreeMemorySize();
        long usedMemory = totalMemory - freeMemory;
    
        double memoryUsage = (double) usedMemory / totalMemory;
    
        if (memoryUsage < 0) {
            memoryUsage = 0.0;
        }
    
        return memoryUsage;
    }

    public double getNetworkTraffic() {
        try {
            Process process = Runtime.getRuntime().exec("netstat -e");
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
    
            String line;
            long receivedBytes = 0;
            long sentBytes = 0;
    
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Bytes")) {
                    String[] tokens = line.split("\\s+");
                    receivedBytes = Long.parseLong(tokens[1]);
                    sentBytes = Long.parseLong(tokens[2]);
                    break;
                }
            }
    
            double networkTraffic = receivedBytes + sentBytes;
    
            if (networkTraffic < 0) {
                networkTraffic = 0.0;
            }
    
            return networkTraffic;
        } catch (Exception e) {
            e.printStackTrace();
            return 0.0;
        }
    }
}
