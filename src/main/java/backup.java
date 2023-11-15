import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class backup extends JFrame {
    private JButton backupButton;
    private JButton restoreButton;

    public backup() {
        setTitle("数据库备份系统");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        backupButton = new JButton("备份数据库");
        restoreButton = new JButton("恢复数据库");

        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        add(backupButton);
        add(restoreButton);

        backupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performBackup();
            }
        });

        restoreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performRestore();
            }
        });
    }

    private void performBackup() {
        try {
            String dbName = "kaoqin";  // 只使用数据库名称
            String dbUser = "root";
            String dbPass = "077418";
            String filePath = "C:/Users/ASUS/Desktop/kaoqin/backup.sql";

            ProcessBuilder processBuilder = new ProcessBuilder(
                    "D:/Program Files/MySQL/MySQL Server 8.0/bin/mysqldump",
                    "-u", dbUser,
                    "-p" + dbPass,
                    "--databases", dbName,
                    "--result-file=" + filePath
            );

            // Redirect output to file
            File backupFile = new File(filePath);
            processBuilder.redirectOutput(backupFile);

            Process process = processBuilder.start();
            int exitCode = process.waitFor();

            if (exitCode == 0) {
                JOptionPane.showMessageDialog(this, "Backup completed successfully.");
            } else {
                JOptionPane.showMessageDialog(this, "Backup failed. Check console for error messages.");
            }
        } catch (IOException | InterruptedException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error during backup process. Check console for error messages.");
        }
    }



    private void performRestore() {
        try {
            String dbName = "kaoqin";
            String dbUser = "root";
            String dbPass = "077418";
            String filePath = "C:/Users/ASUS/Desktop/kaoqin/backup.sql";

            String[] command = {
                    "D:/Program Files/MySQL/MySQL Server 8.0/bin/mysql",
                    dbName,
                    "-u" + dbUser,
                    "-p" + dbPass,
                    "-e", "source " + filePath
            };

            ProcessBuilder processBuilder = new ProcessBuilder(command);
            Process process = processBuilder.start();

            // Capture errors
            InputStream errorStream = process.getErrorStream();
            InputStreamReader errorStreamReader = new InputStreamReader(errorStream);
            BufferedReader errorBufferedReader = new BufferedReader(errorStreamReader);

            // Read errors and print
            StringBuilder errorMessage = new StringBuilder();
            String line;
            while ((line = errorBufferedReader.readLine()) != null) {
                errorMessage.append(line).append("\n");
            }

            int exitCode = process.waitFor();

            if (exitCode == 0) {
                JOptionPane.showMessageDialog(this, "Restore completed successfully.");
            } else {
                JOptionPane.showMessageDialog(this, "Restore failed. Check console for error messages:\n" + errorMessage.toString());
            }
        } catch (IOException | InterruptedException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error during restore process.");
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new backup().setVisible(true);
            }
        });
    }
}
