import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EmployeeManagementGUI extends JFrame {
    private JTextField wnoField, wnameField, genderField, wphoneField, wjobField, wstateField, waddressField;

    public EmployeeManagementGUI() {
        super("员工管理系统");

        // Create GUI components
        JLabel wnoLabel = new JLabel("员工号:");
        JLabel wnameLabel = new JLabel("姓名:");
        JLabel genderLabel = new JLabel("性别:");
        JLabel wphoneLabel = new JLabel("电话:");
        JLabel wjobLabel = new JLabel("职业:");
        JLabel waddressLabel = new JLabel("地址:");

        wnoField = new JTextField(20);
        wnameField = new JTextField(20);
        genderField = new JTextField(20);
        wphoneField = new JTextField(20);
        wjobField = new JTextField(20);
        waddressField = new JTextField(20);

        JButton addButton = new JButton("添加");

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
                        .addComponent(waddressLabel))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(wnoField)
                        .addComponent(wnameField)
                        .addComponent(genderField)
                        .addComponent(wphoneField)
                        .addComponent(wjobField)
                        .addComponent(waddressField)
                        .addComponent(addButton))
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
                        .addComponent(waddressLabel)
                        .addComponent(waddressField))
                .addComponent(addButton)
        );

        // Add action listener for the add button
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addEmployee();
            }
        });

        // Set frame properties
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
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
            String sql = "INSERT INTO worker (wno, wname, gender, wphone, wjob, wstate, waddress, keyword, issuper) VALUES (?, ?, ?, ?, ?, ?, ?,?,?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                // Set the values for the parameters
                preparedStatement.setString(1, wnoField.getText());
                preparedStatement.setString(2, wnameField.getText());
                preparedStatement.setString(3, genderField.getText());
                preparedStatement.setString(4, wphoneField.getText());
                preparedStatement.setString(5, wjobField.getText());
                preparedStatement.setString(6, "在岗");
                preparedStatement.setString(7, waddressField.getText());
                preparedStatement.setString(8, "123456");
                preparedStatement.setString(9, "false");

                // Execute the SQL statement
                preparedStatement.executeUpdate();

                // Display a success message
                JOptionPane.showMessageDialog(this, "Employee added successfully!");
            }
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error adding employee: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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
