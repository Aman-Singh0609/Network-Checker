package monitor;

import java.net.InetAddress;

public class Device {
    private String ip;

    public Device(String ip) {
        this.ip = ip;
    }

    public long ping() {
        try {
            long start = System.currentTimeMillis();
            boolean reachable = InetAddress.getByName(ip).isReachable(3000); // 3s timeout
            long latency = System.currentTimeMillis() - start;
            return reachable ? latency : -1;
        } catch (Exception e) {
            return -1;
        }
    }

    public String getIp() {
        return ip;
    }
}

