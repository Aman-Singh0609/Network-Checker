package monitor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Monitor {
    public static void main(String[] args) throws IOException, InterruptedException {
        List<String> devices = Files.readAllLines(Paths.get("devices.txt"));

        while (true) {
            for (String ip : devices) {
                Device device = new Device(ip);
                long latency = device.ping();

                LoggerUtil.log(ip, latency);

                if (latency == -1 || latency > 300) {
                    System.out.println("⚠️ ALERT: " + ip + " latency too high or not reachable.");
                    LoggerUtil.alert(ip + " latency = " + latency + "ms");
                } else {
                    System.out.println("✅ " + ip + " responded in " + latency + "ms");
                }
            }

            // Wait 60 seconds before next scan
            Thread.sleep(60000);
        }
    }
}

