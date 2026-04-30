package et.edu.astu.desktop.controller;

import et.edu.astu.common.dto.AdminLoginResponse;
import et.edu.astu.common.dto.LoginRequest;
import et.edu.astu.common.dto.LoginResponse;
import et.edu.astu.desktop.http.HttpService;
import et.edu.astu.desktop.util.Session;
import et.edu.astu.desktop.util.UIUtils;
import et.edu.astu.desktop.view.AdminDashboard;
import et.edu.astu.desktop.view.EmployeeDashboard;
import et.edu.astu.desktop.view.LoginView;

public class AuthController {
    private final LoginView view;
    private final HttpService httpService;

    public AuthController(LoginView view) {
        this.view = view;
        this.httpService = new HttpService();
        initController();
    }

    private void initController() {
        view.getAdminLoginButton().addActionListener(e -> loginAsAdmin());
        view.getEmployeeLoginButton().addActionListener(e -> loginAsEmployee());
    }

    private void loginAsAdmin() {
        String username = view.getAdminUsername();
        String password = view.getAdminPassword();

        if (username.isEmpty() || password.isEmpty()) {
            UIUtils.showError("Please fill in all fields.");
            return;
        }

        try {
            AdminLoginResponse response = httpService.loginAsAdmin(new LoginRequest(username, password));
            System.out.println(response);
            if (response != null && response.succeed()) {
                Session.setSession(null, username, "ADMIN");
                new AdminDashboard().setVisible(true);
                view.dispose();
            } else {
                UIUtils.showError("Invalid credentials.");
            }
        } catch (Exception ex) {
            UIUtils.showError("Login failed: " + ex.getMessage());
        }
    }

    private void loginAsEmployee() {
        String username = view.getEmployeeUsername();
        String password = view.getEmployeePassword();

        if (username.isEmpty() || password.isEmpty()) {
            UIUtils.showError("Please fill in all fields.");
            return;
        }

        try {
            LoginResponse response = httpService.loginAsEmployee(new LoginRequest(username, password));
            if (response != null && response.token() != null) {
                Session.setSession(response.token(), username, "EMPLOYEE");
                new EmployeeDashboard().setVisible(true);
                view.dispose();
            } else {
                UIUtils.showError("Invalid credentials.");
            }
        } catch (Exception ex) {
            UIUtils.showError("Login failed: " + ex.getMessage());
        }
    }
}
