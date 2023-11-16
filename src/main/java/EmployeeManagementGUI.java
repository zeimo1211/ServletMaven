import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EmployeeManagementGUI extends JFrame {
    private JTextField wnoField, wnameField, genderField, wphoneField, wjobField, wstateField, waddressField, wkeywordField ,wissuperField, wdnoField;
    private JButton addButton, updateButton, deleteButton, backupButton, restoreButton;

    public EmployeeManagementGUI() {
        super("考勤后台管理系统");

        // Create GUI components
        JLabel wnoLabel = new JLabel("员工号:");
        JLabel wnameLabel = new JLabel("姓名:");
        JLabel genderLabel = new JLabel("性别:");
        JLabel wphoneLabel = new JLabel("电话:");
        JLabel wjobLabel = new JLabel("职务:");
        JLabel wdnoLabel = new JLabel("部门:");
        JLabel wstateLabel = new JLabel("状态:");
        JLabel waddressLabel = new JLabel("地址:");
        JLabel wkeywordLabel = new JLabel("密码:");
        JLabel wissuperLabel = new JLabel("权限:");

        wnoField = new JTextField(20);
        wnameField = new JTextField(20);
        genderField = new JTextField(20);
        wphoneField = new JTextField(20);
        wjobField = new JTextField(20);
        wdnoField = new JTextField(20);
        wstateField = new JTextField(20);
        waddressField = new JTextField(20);
        wkeywordField = new JTextField(20);
        wissuperField = new JTextField(20);

        addButton = new JButton("添加");
        updateButton = new JButton("修改");
        deleteButton = new JButton("删除");
        backupButton = new JButton("备份数据库");
        restoreButton = new JButton("恢复数据库");


        // Set layout using GroupLayout
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(wnoLabel)
                        .addComponent(wnameLabel)
                        .addComponent(genderLabel)
                        .addComponent(wphoneLabel)
                        .addComponent(wjobLabel)
                        .addComponent(wstateLabel)
                        .addComponent(waddressLabel)
                        .addComponent(wkeywordLabel)
                        .addComponent(wissuperLabel)
                        .addComponent(wdnoLabel))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(wnoField)
                        .addComponent(wnameField)
                        .addComponent(genderField)
                        .addComponent(wphoneField)
                        .addComponent(wjobField)
                        .addComponent(wstateField)
                        .addComponent(waddressField)
                        .addComponent(wkeywordField)
                        .addComponent(wissuperField)
                        .addComponent(wdnoField)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(addButton)
                                .addComponent(updateButton)
                                .addComponent(deleteButton))
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(backupButton)
                                .addComponent(restoreButton))
                )
        );

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(wnoLabel)
                        .addComponent(wnoField))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(wnameLabel)
                        .addComponent(wnameField))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(genderLabel)
                        .addComponent(genderField))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(wphoneLabel)
                        .addComponent(wphoneField))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(wjobLabel)
                        .addComponent(wjobField))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(wstateLabel)
                        .addComponent(wstateField))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(waddressLabel)
                        .addComponent(waddressField))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(wkeywordLabel)
                        .addComponent(wkeywordField))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(wissuperLabel)
                        .addComponent(wissuperField))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(wdnoLabel)
                        .addComponent(wdnoField))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(addButton)
                        .addComponent(updateButton)
                        .addComponent(deleteButton))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(backupButton)
                        .addComponent(restoreButton))
        );



        // Add action listener for the add button
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addEmployee();
            }
        });

        // Add action listener for the update button
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateEmployee();
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteEmployee();
            }
        });

        // Add action listener for the backup button
        backupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performBackup();
            }
        });

        // Add action listener for the restore button
        restoreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performRestore();
            }
        });


        // Set frame properties
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(330, 400);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void addEmployee() {
        try {
            // Load the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connect to the database
            String url = "jdbc:mysql://localhost:3306/kaoqin?useSSL=false&serverTimezone=UTC";
            String username = "root";
            String password = "077418";
            Connection connection = DriverManager.getConnection(url, username, password);

            // Prepare the SQL statement
            String sql1 = "INSERT INTO worker (wno, wname, gender, wphone, wjob, wstate, waddress, keyword, issuper) VALUES (?, ?, ?, ?, ?, ?, ?,?,?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql1)) {
                // Set the values for the parameters
                preparedStatement.setString(1, wnoField.getText());
                preparedStatement.setString(2, wnameField.getText());
                preparedStatement.setString(3, genderField.getText());
                preparedStatement.setString(4, wphoneField.getText());
                preparedStatement.setString(5, wjobField.getText());
                preparedStatement.setString(6, wstateField.getText());
                preparedStatement.setString(7, waddressField.getText());
                preparedStatement.setString(8, wkeywordField.getText());
                preparedStatement.setString(9, wissuperField.getText());

                // Execute the SQL statement
                preparedStatement.executeUpdate();

                // Display a success message
                JOptionPane.showMessageDialog(this, "员工信息添加成功");
            }
            String sql2 = "INSERT INTO department_worker (wno, dno) VALUES (?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql2)) {
                // Set the values for the parameters
                preparedStatement.setString(1, wnoField.getText());
                preparedStatement.setString(2, wdnoField.getText());

                // Execute the SQL statement
                preparedStatement.executeUpdate();

                // Display a success message
                JOptionPane.showMessageDialog(this, "员工所属部门信息添加成功!");
            }
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error adding employee: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateEmployee() {
        try {
            // Load the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connect to the database
            String url = "jdbc:mysql://localhost:3306/kaoqin?useSSL=false&serverTimezone=UTC";
            String username = "root";
            String password = "077418";
            Connection connection = DriverManager.getConnection(url, username, password);

            // Prepare the SQL statement
            String updateSql1 = "UPDATE worker SET wname=?, gender=?, wphone=?, wjob=?, wstate=?, waddress=?, keyword=?,issuper=? WHERE wno=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(updateSql1)) {
                // Set the values for the parameters
                preparedStatement.setString(1, wnameField.getText());
                preparedStatement.setString(2, genderField.getText());
                preparedStatement.setString(3, wphoneField.getText());
                preparedStatement.setString(4, wjobField.getText());
                preparedStatement.setString(5, wstateField.getText());
                preparedStatement.setString(6, waddressField.getText());
                preparedStatement.setString(7, wkeywordField.getText());
                preparedStatement.setString(8, wissuperField.getText());
                preparedStatement.setString(9, wnoField.getText());

                // Execute the SQL statement
                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, "员工信息修改成功");
                } else {
                    JOptionPane.showMessageDialog(this, "没有该员工号！！", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

            String updateSql2 = "UPDATE department_worker SET dno=? WHERE wno=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(updateSql2)) {
                // Set the values for the parameters
                preparedStatement.setString(1, wdnoField.getText());
                preparedStatement.setString(2, wnoField.getText());

                // Execute the SQL statement
                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, "员工所属部门信息添加成功");
                } else {
                    JOptionPane.showMessageDialog(this, "没有该员工号！！", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error updating employee: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteEmployee() {
        try {
            // Load the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connect to the database
            String url = "jdbc:mysql://localhost:3306/kaoqin?useSSL=false&serverTimezone=UTC";
            String username = "root";
            String password = "077418";
            Connection connection = DriverManager.getConnection(url, username, password);

            // Prepare the SQL statement for deleting from department_worker table
            String deleteSql2 = "DELETE FROM department_worker WHERE wno=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(deleteSql2)) {
                // Set the values for the parameters
                preparedStatement.setString(1, wnoField.getText());

                // Execute the SQL statement
                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    // If the record is found and deleted in department_worker table, proceed to delete from worker table
                    String deleteSql1 = "DELETE FROM worker WHERE wno=?";
                    try (PreparedStatement preparedStatementWorker = connection.prepareStatement(deleteSql1)) {
                        // Set the values for the parameters
                        preparedStatementWorker.setString(1, wnoField.getText());

                        // Execute the SQL statement
                        int workerRowsAffected = preparedStatementWorker.executeUpdate();

                        if (workerRowsAffected > 0) {
                            JOptionPane.showMessageDialog(this, "员工信息删除成功");
                        } else {
                            JOptionPane.showMessageDialog(this, "没有找到该员工号在 worker 表中的记录", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "没有找到该员工号在 department_worker 表中的记录", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error deleting employee: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
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
                new EmployeeManagementGUI();
            }
        });
    }
}
