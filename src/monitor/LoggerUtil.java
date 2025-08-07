package monitor;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class LoggerUtil {
    public static void log(String ip, long latency) {
        try (FileWriter fw = new FileWriter("ping_logs.csv", true)) {
            fw.write(ip + "," + latency + "," + LocalDateTime.now() + "\n");
        } catch (IOException e) {
            System.out.println("Logging failed: " + e.getMessage());
        }
    }

    public static void alert(String message) {
        try (FileWriter fw = new FileWriter("alert_log.txt", true)) {
            fw.write(LocalDateTime.now() + " ALERT: " + message + "\n");
        } catch (IOException e) {
            System.out.println("Alert logging failed: " + e.getMessage());
        }
    }
}

