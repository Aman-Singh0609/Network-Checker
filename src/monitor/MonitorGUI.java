package monitor;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class MonitorGUI extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;

    public MonitorGUI() {
        setTitle("Network Health Monitor");
        setSize(600, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        
        tableModel = new DefaultTableModel(new Object[]{"Device", "Status", "Latency (ms)"}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        
        JButton refreshBtn = new JButton("Refresh");
        refreshBtn.addActionListener(e -> updateTable());
        add(refreshBtn, BorderLayout.SOUTH);

        
        table.setDefaultRenderer(Object.class, new StatusRenderer());

        updateTable();
    }

    private void updateTable() {
        tableModel.setRowCount(0); 
        try {
            List<String> devices = Files.readAllLines(Paths.get("devices.txt"));
            for (String device : devices) {
                long latency = PingUtility.pingDevice(device);
                String status = (latency >= 0 && latency < 150) ? "Healthy" : "Unreachable / Slow";
                tableModel.addRow(new Object[]{device, status, latency >= 0 ? latency : "Timeout"});
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Failed to read devices.txt", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static class StatusRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus,
                                                       int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            String status = (String) table.getValueAt(row, 1);
            if (status.contains("Healthy")) {
                c.setBackground(Color.GREEN);
            } else {
                c.setBackground(Color.PINK);
            }
            return c;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MonitorGUI gui = new MonitorGUI();
            gui.setVisible(true);
        });
    }
}
