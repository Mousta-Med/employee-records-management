package com.med.ui;

import com.med.RestClient;
import com.med.model.Employee;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class EmployeeForm extends JFrame {
    private RestClient restClient;
    private JTextField fullNameField;
    private JTextField jobTitleField;
    private JTextField departmentField;
    private JTextField hireDateField;
    private JTextField employmentStatusField;
    private JTextField contactInfoField;
    private JTextField addressField;
    private JTextArea resultArea;

    public EmployeeForm() {
        restClient = new RestClient();
        initUI();
    }

    private void initUI() {
        setTitle("Employee Management");
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(8, 2));

        JLabel fullNameLabel = new JLabel("Full Name:");
        JLabel jobTitleLabel = new JLabel("Job Title:");
        JLabel departmentLabel = new JLabel("Department:");
        JLabel hireDateLabel = new JLabel("Hire Date (yyyy-MM-dd):");
        JLabel employmentStatusLabel = new JLabel("Employment Status:");
        JLabel contactInfoLabel = new JLabel("Contact Info:");
        JLabel addressLabel = new JLabel("Address:");

        fullNameField = new JTextField();
        jobTitleField = new JTextField();
        departmentField = new JTextField();
        hireDateField = new JTextField();
        employmentStatusField = new JTextField();
        contactInfoField = new JTextField();
        addressField = new JTextField();

        JButton addButton = new JButton("Add Employee");
        JButton fetchButton = new JButton("Fetch Employees");

        resultArea = new JTextArea(10, 40);
        resultArea.setEditable(false);

        panel.add(fullNameLabel);
        panel.add(fullNameField);
        panel.add(jobTitleLabel);
        panel.add(jobTitleField);
        panel.add(departmentLabel);
        panel.add(departmentField);
        panel.add(hireDateLabel);
        panel.add(hireDateField);
        panel.add(employmentStatusLabel);
        panel.add(employmentStatusField);
        panel.add(contactInfoLabel);
        panel.add(contactInfoField);
        panel.add(addressLabel);
        panel.add(addressField);

        panel.add(addButton);
        panel.add(fetchButton);

        getContentPane().add(panel, BorderLayout.NORTH);
        getContentPane().add(new JScrollPane(resultArea), BorderLayout.CENTER);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Employee employee = new Employee();
                    employee.setFullName(fullNameField.getText());
                    employee.setJobTitle(jobTitleField.getText());
                    employee.setDepartment(departmentField.getText());
                    employee.setHireDate(new SimpleDateFormat("yyyy-MM-dd").parse(hireDateField.getText()));
                    employee.setEmploymentStatus(employmentStatusField.getText());
                    employee.setContactInfo(contactInfoField.getText());
                    employee.setAddress(addressField.getText());

                    Employee newEmployee = restClient.addEmployee(employee);
                    resultArea.append("Employee added: " + newEmployee.getFullName() + "\n");
                } catch (IOException | ParseException ex) {
                    resultArea.append("Error: " + ex.getMessage() + "\n");
                }
            }
        });

        fetchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    List<Employee> employees = restClient.getAllEmployees();
                    resultArea.append("Fetched Employees:\n");
                    for (Employee employee : employees) {
                        resultArea.append(employee.getId() + ": " + employee.getFullName() + " (" + employee.getJobTitle() + ")\n");
                    }
                } catch (IOException ex) {
                    resultArea.append("Error: " + ex.getMessage() + "\n");
                }
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            EmployeeForm form = new EmployeeForm();
            form.setVisible(true);
        });
    }
}
