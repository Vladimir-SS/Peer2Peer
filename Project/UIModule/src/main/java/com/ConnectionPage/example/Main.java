package org.example.
import javax.swing.;
import java.awt.;
import javax.swing.table.DefaultTableModel;

public class CustomTableModel extends DefaultTableModel {
    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
    private void scanButtonHandler(){
        new Thread(()-> {
            DefaultTableModel model = (DefaultTableModel) foundConnectionsTable.getModel();
            model.setRowCount(0);
            model.addRow(new String[]{"Sync Devices...", ""});
            DataController.findDevices();

            var data = DataController.getLastFoundDevices();

            model.setRowCount(0);
            if(data.size() == 0)
                model.addRow(new String[]{"No devices were found.", ""});
            else
                data.forEach(model::addRow);
        }).start();
    }

    private JTable SyncronizedFilesTables(List<String[]> data) {
        DefaultTableModel model = new CustomTableModel();
        model.addColumn("Device");
        model.addColumn("IP");
        data.forEach(model::addRow);

        final JTable table = new JTable(model);
        table.setFont(new Font( "Courier New",Font.BOLD,dimension+15 ));
        table.setRowHeight(25);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);

        return table;
    }
    scanButton.addActionListener(event -> scanButtonHandler());
}