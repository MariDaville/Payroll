import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EmployeeManagementGUI extends JFrame {
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JComboBox<String> employeeTypeComboBox;
    private JTextField paymentDetail1Field;
    private JTextField paymentDetail2Field;
    private DefaultTableModel tableModel;
    private JTable employeeTable;

    public EmployeeManagementGUI() {
        setTitle("Payroll Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(false);

        // Creates a input panel with Grid Bag Layout
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.BOTH;

        // Add components to input panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(new JLabel("First Name:"), gbc);
        gbc.gridx = 1;
        firstNameField = new JTextField();
        inputPanel.add(firstNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(new JLabel("Last Name:"), gbc);
        gbc.gridx = 1;
        lastNameField = new JTextField();
        inputPanel.add(lastNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        inputPanel.add(new JLabel("Employee Type:"), gbc);
        gbc.gridx = 1;
        employeeTypeComboBox = new JComboBox<>(new String[]{"Hourly", "Salaried", "Commission", "Base Plus Commission"});
        inputPanel.add(employeeTypeComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        inputPanel.add(new JLabel("Payment Detail 1:"), gbc);
        gbc.gridx = 1;
        paymentDetail1Field = new JTextField();
        inputPanel.add(paymentDetail1Field, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        inputPanel.add(new JLabel("Payment Detail 2:"), gbc);
        gbc.gridx = 1;
        paymentDetail2Field = new JTextField();
        inputPanel.add(paymentDetail2Field, gbc);

        // Create buttons to add them to the input panel
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        JButton addButton = new JButton("Add Employee");
        addButton.addActionListener(new AddButtonListener());
        inputPanel.add(addButton, gbc);

        gbc.gridx = 1;
        JButton removeButton = new JButton("Remove Employee");
        removeButton.addActionListener(new RemoveButtonListener());
        inputPanel.add(removeButton, gbc);

        gbc.gridx = 2;
        JButton generateButton = new JButton("Generate Pay Stub");
        generateButton.addActionListener(new GenerateButtonListener());

        inputPanel.add(generateButton, gbc);

        add(inputPanel, BorderLayout.NORTH);

        //the JTable that will be used to display employee information dynamically
        tableModel = new DefaultTableModel(new Object[]{"First Name", "Last Name", "Type of Employee", "Payment Detail 1", "Payment Detail 2"}, 0);
        employeeTable = new JTable(tableModel);
        add(new JScrollPane(employeeTable), BorderLayout.CENTER);

        // This is an instructional section on how to use this application
//        JPanel instructionPanel = new JPanel();
//        JTextArea instructionsArea = new JTextArea(5,30);
//        instructionsArea.setText("Instructions:\n\n" +
//                "1. Enter the employee's first name and last name.\n" +
//                "2. Select the employee type from the dropdown menu.\n" +
//                "3. Enter the payment details based on the employee type.\n" +
//                "4. Click 'Add Employee' to add the employee to the table.\n" +
//                "5. Select an employee from the table and click 'Remove Employee' to remove them.\n" +
//                "6. Click 'Generate Pay Stub' to generate pay stubs for all employees in the table.");
//        instructionsArea.setEditable(false);
//        instructionPanel.add(instructionsArea);
//        add(instructionPanel, BorderLayout.AFTER_LAST_LINE);

    JPanel instructionPanel2 = new JPanel();
    JTextArea instructionsArea2 = new JTextArea(5,3);
 instructionsArea2.setFont(new Font("Arial", Font.ROMAN_BASELINE, 12));
 instructionsArea2.setAlignmentX(Component.LEFT_ALIGNMENT);
 instructionsArea2.setBackground(Color.LIGHT_GRAY);
 instructionsArea2.setOpaque(false);
        instructionsArea2.setText(
                "Instructions:\n" +
                        "1. Enter the employee's first name and last name.\n" +
                        "2. Select the employee type from the dropdown menu.\n" +
                        "3. Enter the payment details based on the employee type.\n" +
                        "4. Click 'Add Employee' to add the employee to the table.\n" +
                        "5. Select an employee from the table and click 'Remove Employee' to remove them.\n" +
                        "6. Click 'Generate Pay Stub' to generate pay stubs for all employees in the table.\n\n" +
                "Please Note:\n" +
                " Payment Detail 1 and Payment Detail 2 are used" +
                " to capture specific payment-related information for different types of employees.\n" +
                " Their usage varies based on the employee type selected:.\n"
        + "1. For Hourly Employees, Payment Detail 1 is the wage per hour and Payment Detail 2 is the number of hours worked.\n"
        + "2. For Salaried Employees, Payment Detail 1 is the weekly salary.\n"
        + "3. For Commission Employees, Payment Detail 1 is the gross sales and Payment Detail 2 is the commission rate.\n"
        + "4. For Base Plus Commission Employees, Payment Detail 1 is the gross sales, Payment Detail 2 is the commission rate.\n"
        + "Please ensure that you enter the correct payment details for each employee type.");
        instructionsArea2.setEditable(false);
        instructionPanel2.add(instructionsArea2);
    add(instructionPanel2, BorderLayout.LINE_END);
}

// This is the action listener for the Add Employee button
    private class AddButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String employeeType = (String) employeeTypeComboBox.getSelectedItem();
            String paymentDetail1 = paymentDetail1Field.getText();
            String paymentDetail2 = paymentDetail2Field.getText();

            if (firstName.isEmpty() || lastName.isEmpty() || paymentDetail1.isEmpty() || (employeeType.equals("Base Plus Commission") && paymentDetail2.isEmpty())) {
                JOptionPane.showMessageDialog(EmployeeManagementGUI.this, "Please fill in all required fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                double payment1 = Double.parseDouble(paymentDetail1);
                if (payment1 < 0) {
                    throw new NumberFormatException("Negative value");
                }
                if (!paymentDetail2.isEmpty()) {
                    double payment2 = Double.parseDouble(paymentDetail2);
                    if (payment2 < 0) {
                        throw new NumberFormatException("Negative value");
                    }
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(EmployeeManagementGUI.this, "Payment details must be valid non-negative numbers.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            tableModel.addRow(new Object[]{firstName, lastName, employeeType, paymentDetail1, paymentDetail2});
        }
}

    // This is the action listener for the Remove Employee button
    private class RemoveButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRow = employeeTable.getSelectedRow();
            if (selectedRow != -1) {
                tableModel.removeRow(selectedRow);
            }
        }
    }

    // This is the action listener for the Generate Pay Stub button
    private class GenerateButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                String firstName = (String) tableModel.getValueAt(i, 0);
                String lastName = (String) tableModel.getValueAt(i, 1);
                String employeeType = (String) tableModel.getValueAt(i, 2);
                String paymentDetail1 = (String) tableModel.getValueAt(i, 3);
                String paymentDetail2 = (String) tableModel.getValueAt(i, 4);

                Employee employee;
                switch (employeeType) {
                    case "Hourly":
                        employee = new HourlyEmployee(firstName, lastName, "", Double.parseDouble(paymentDetail1), Double.parseDouble(paymentDetail2));
                        break;
                    case "Salaried":
                        employee = new SalariedEmployee(firstName, lastName, "", Double.parseDouble(paymentDetail1));
                        break;
                    case "Commission":
                        employee = new CommissionEmployee(firstName, lastName, "", Double.parseDouble(paymentDetail1), Double.parseDouble(paymentDetail2));
                        break;
                    case "Base Plus Commission":
                        employee = new BasePlusCommissionEmployee(firstName, lastName, "", Double.parseDouble(paymentDetail1), Double.parseDouble(paymentDetail2), Double.parseDouble(paymentDetail2));
                        break;
                    default:
                        continue;
                }


                employee.writeToFile();
            }
            JOptionPane.showMessageDialog(EmployeeManagementGUI.this, "Pay stubs generated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            EmployeeManagementGUI gui = new EmployeeManagementGUI();
            gui.setVisible(true);
        });
    }
}




/* Remember to get rid of this before submitting if we not gon use it */
/*
            import javax.swing.*;
            import javax.swing.table.DefaultTableModel;
            import java.awt.*;
            import java.awt.event.ActionEvent;
            import java.awt.event.ActionListener;

            public class EmployeeManagementGUI extends JFrame {
                private JTextField firstNameField;
                private JTextField lastNameField;
                private JComboBox<String> employeeTypeComboBox;
                private JTextField paymentDetail1Field;
                private JTextField paymentDetail2Field;
                private DefaultTableModel tableModel;
                private JTable employeeTable;

                public EmployeeManagementGUI() {
                    setTitle("Employee Management System");
                    setSize(800, 600);
                    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    setLayout(new BorderLayout());

                    // Create input panel
                    JPanel inputPanel = new JPanel(new GridLayout(6, 2));
                    inputPanel.add(new JLabel("First Name:"));
                    firstNameField = new JTextField();
                    inputPanel.add(firstNameField);
                    inputPanel.add(new JLabel("Last Name:"));
                    lastNameField = new JTextField();
                    inputPanel.add(lastNameField);
                    inputPanel.add(new JLabel("Employee Type:"));
                    employeeTypeComboBox = new JComboBox<>(new String[]{"Hourly", "Salaried", "Commission", "Base Plus Commission"});
                    inputPanel.add(employeeTypeComboBox);
                    inputPanel.add(new JLabel("Payment Detail 1:"));  // PaymentDetail1 and PaymentDetail2 are used to
                    paymentDetail1Field = new JTextField();                 // to capture specific payment-related information for
                    inputPanel.add(paymentDetail1Field);                    // different types of employees.
                    inputPanel.add(new JLabel("Payment Detail 2:"));  // Their usage varies based on the employee type selected
                    paymentDetail2Field = new JTextField();
                    inputPanel.add(paymentDetail2Field);

                    // Create buttons
                    JButton addButton = new JButton("Add Employee");
                    addButton.addActionListener(new AddButtonListener());
                    inputPanel.add(addButton);
                    JButton removeButton = new JButton("Remove Employee");
                    removeButton.addActionListener(new RemoveButtonListener());
                    inputPanel.add(removeButton);
                    JButton generateButton = new JButton("Generate Pay Stub");
                    generateButton.addActionListener(new GenerateButtonListener());
                    inputPanel.add(generateButton);

                    add(inputPanel, BorderLayout.NORTH);

                    // Create table to be used for displaying employee information dinamicaly
                    tableModel = new DefaultTableModel(new Object[]{"First Name", "Last Name", "Type", "Payment Detail 1", "Payment Detail 2"}, 0);
                    employeeTable = new JTable(tableModel);
                    add(new JScrollPane(employeeTable), BorderLayout.CENTER);
                }

                private class AddButtonListener implements ActionListener {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String firstName = firstNameField.getText();
                        String lastName = lastNameField.getText();
                        String employeeType = (String) employeeTypeComboBox.getSelectedItem();
                        String paymentDetail1 = paymentDetail1Field.getText();
                        String paymentDetail2 = paymentDetail2Field.getText();

                        if (firstName.isEmpty() || lastName.isEmpty() || paymentDetail1.isEmpty() || (employeeType.equals("Base Plus Commission") && paymentDetail2.isEmpty())) {
                            JOptionPane.showMessageDialog(EmployeeManagementGUI.this, "Please fill in all required fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        try {
                            Double.parseDouble(paymentDetail1);
                            if (!paymentDetail2.isEmpty()) {
                                Double.parseDouble(paymentDetail2);
                            }
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(EmployeeManagementGUI.this, "Payment details must be valid numbers.", "Input Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        tableModel.addRow(new Object[]{firstName, lastName, employeeType, paymentDetail1, paymentDetail2});
                    }
                }

                private class RemoveButtonListener implements ActionListener {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int selectedRow = employeeTable.getSelectedRow();
                        if (selectedRow != -1) {
                            tableModel.removeRow(selectedRow);
                        }
                    }
                }

                private class GenerateButtonListener implements ActionListener {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        for (int i = 0; i < tableModel.getRowCount(); i++) {
                            String firstName = (String) tableModel.getValueAt(i, 0);
                            String lastName = (String) tableModel.getValueAt(i, 1);
                            String employeeType = (String) tableModel.getValueAt(i, 2);
                            String paymentDetail1 = (String) tableModel.getValueAt(i, 3);
                            String paymentDetail2 = (String) tableModel.getValueAt(i, 4);

                            Employee employee;
                            switch (employeeType) {
                                case "Hourly":
                                    employee = new HourlyEmployee(firstName, lastName, "", Double.parseDouble(paymentDetail1), Double.parseDouble(paymentDetail2));
                                    break;
                                case "Salaried":
                                    employee = new SalariedEmployee(firstName, lastName, "", Double.parseDouble(paymentDetail1));
                                    break;
                                case "Commission":
                                    employee = new CommissionEmployee(firstName, lastName, "", Double.parseDouble(paymentDetail1), Double.parseDouble(paymentDetail2));
                                    break;
                                case "Base Plus Commission":
                                    employee = new BasePlusCommissionEmployee(firstName, lastName, "", Double.parseDouble(paymentDetail1), Double.parseDouble(paymentDetail2), Double.parseDouble(paymentDetail2));
                                    break;
                                default:
                                    continue;
                            }

                            employee.writeToFile();
                        }
                    }
                }

                public static void main(String[] args) {
                    SwingUtilities.invokeLater(() -> {
                        EmployeeManagementGUI gui = new EmployeeManagementGUI();
                        gui.setVisible(true);
                    });
                }
            }*/