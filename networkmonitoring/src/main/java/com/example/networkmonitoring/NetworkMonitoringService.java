package com.example.networkmonitoring;

import java.util.Map;

public class NetworkMonitoringService {
    private NetworkMonitoringCore monitoringCore;
    private NetworkMonitoringDatabase monitoringDatabase;

    public NetworkMonitoringService(String configFilePath, String jdbcUrl, String username, String password) {
        this.monitoringCore = new NetworkMonitoringCore(configFilePath);
        this.monitoringDatabase = new NetworkMonitoringDatabase(jdbcUrl, username, password);
    }

    public void monitorParameters() {
        Map<String, Double> parameters = monitoringCore.getMonitoredParameters();
        updateParameters(parameters);
        monitoringDatabase.saveParameters(parameters);
    }

    private void updateParameters(Map<String, Double> parameters) {
        parameters.forEach((parameterName, parameterValue) -> {
            if (parameterName.equals("cpu_load")) {
                double cpuLoad = monitoringCore.getCPULoad();
                parameters.put(parameterName, cpuLoad);
            } else if (parameterName.equals("memory_usage")) {
                double memoryUsage = monitoringCore.getMemoryUsage();
                parameters.put(parameterName, memoryUsage);
            } else if (parameterName.equals("network_traffic")) {
                double networkTraffic = monitoringCore.getNetworkTraffic();
                parameters.put(parameterName, networkTraffic);
            }
        });
    }
}