package monitor;

import java.io.IOException;

public class PingUtility {
    public static long pingDevice(String ipAddress) {
        try {
            long startTime = System.currentTimeMillis();
            Process process = Runtime.getRuntime().exec("ping -n 1 " + ipAddress);
            int returnVal = process.waitFor();
            long endTime = System.currentTimeMillis();

            if (returnVal == 0) {
                return endTime - startTime;
            } else {
                return -1;
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return -1;
        }
    }
}

