package et.edu.astu.desktop;

import et.edu.astu.desktop.controller.AuthController;
import et.edu.astu.desktop.view.LoginView;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.formdev.flatlaf.FlatIntelliJLaf;

public class MainApp {

    public static void main(String[] args) {
        try {
            FlatIntelliJLaf.setup();
            UIManager.put("Button.arc", 10);
            UIManager.put("Component.arc", 10);
            UIManager.put("TextComponent.arc", 10);
            UIManager.put("CheckBox.arc", 10);
            UIManager.put("ProgressBar.arc", 10);
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            LoginView loginView = new LoginView();
            new AuthController(loginView);
            loginView.setVisible(true);
        });
    }
}
