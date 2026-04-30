package et.edu.astu.desktop.controller;

import et.edu.astu.common.dto.CreatedEmployeeResponse;
import et.edu.astu.common.dto.EmployeeRequest;
import et.edu.astu.desktop.http.HttpService;
import et.edu.astu.desktop.util.UIUtils;
import et.edu.astu.desktop.view.AdminDashboard;

import javax.swing.JOptionPane;

public class AdminController {
    private final AdminDashboard view;
    private final HttpService httpService;

    public AdminController(AdminDashboard view) {
        this.view = view;
        this.httpService = new HttpService();
        initController();
    }

    private void initController() {
        view.getCreateEmployeeButton().addActionListener(e -> createEmployee());
    }

    private void createEmployee() {
        String fName = view.getFirstName();
        String mName = view.getMiddleName();
        String lName = view.getLastName();

        if (fName.isEmpty() || lName.isEmpty()) {
            UIUtils.showError("First and Last name are required.");
            return;
        }

        try {
            EmployeeRequest request = new EmployeeRequest(fName, mName, lName);
            CreatedEmployeeResponse response = httpService.createEmployee(request);

            if (response != null) {
                String message = String.format("Employee Created Successfully!\n\nUsername: %s\nPassword: %s", 
                        response.username(), response.password());
                JOptionPane.showMessageDialog(view, message, "Success", JOptionPane.INFORMATION_MESSAGE);
                view.clearFields();
            } else {
                UIUtils.showError("Failed to create employee.");
            }
        } catch (Exception ex) {
            UIUtils.showError("Error: " + ex.getMessage());
        }
    }
}
