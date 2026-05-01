package et.edu.astu.desktop.util;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;

public class UIUtils {
    public static final Color PRIMARY_COLOR = new Color(200, 150, 50);
    public static final Color PRIMARY_HOVER = new Color(67, 56, 202);
    public static final Color ACCENT_COLOR = new Color(16, 185, 129);
    public static final Color BACKGROUND_COLOR = new Color(243, 244, 246);
    public static final Color SURFACE_COLOR = Color.WHITE;
    public static final Color TEXT_PRIMARY = new Color(17, 24, 39);
    public static final Color TEXT_SECONDARY = new Color(107, 114, 128);
    public static final Color BORDER_COLOR = new Color(229, 231, 235);
    public static final Color DANGER = new Color(239, 68, 68);

    public static final Font HEADER_FONT = new Font("Inter", Font.BOLD, 28);
    public static final Font SUBHEADER_FONT = new Font("Inter", Font.BOLD, 18);
    public static final Font REGULAR_FONT = new Font("Inter", Font.PLAIN, 14);
    public static final Font LABEL_FONT = new Font("Inter", Font.BOLD, 13);

    public static JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(REGULAR_FONT);
        button.setBackground(ACCENT_COLOR);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 24, 10, 24));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        button.putClientProperty("JButton.buttonType", "roundRect");
        
        return button;
    }

    public static JButton createSecondaryButton(String text) {
        JButton button = new JButton(text);
        button.setFont(REGULAR_FONT);
        button.setBackground(TEXT_SECONDARY);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(BORDER_COLOR, 1),
                        BorderFactory.createEmptyBorder(10, 24, 10, 24)
                )
        );
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.putClientProperty("JButton.buttonType", "roundRect");
        return button;
    }

    public static JTextField createStyledTextField() {
        JTextField textField = new JTextField();
        textField.setFont(REGULAR_FONT);
        textField.putClientProperty("JTextField.placeholderText", "Enter value...");
        textField.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(BORDER_COLOR, 1),
                        BorderFactory.createEmptyBorder(8, 12, 8, 12)
                )
        );
        return textField;
    }


    public static JTextField createStyledTextField(String placeholder) {
        JTextField textField = new JTextField();
        textField.setFont(REGULAR_FONT);
        textField.putClientProperty("JTextField.placeholderText", placeholder);
        textField.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(BORDER_COLOR, 1),
                        BorderFactory.createEmptyBorder(8, 12, 8, 12)
                )
        );
        return textField;
    }

    public static JPasswordField createStyledPasswordField() {
        JPasswordField passwordField = new JPasswordField();
        passwordField.setFont(REGULAR_FONT);
        passwordField.putClientProperty("JTextField.placeholderText", "••••••••");
        passwordField.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(BORDER_COLOR, 1),
                        BorderFactory.createEmptyBorder(8, 12, 8, 12)
                )
        );
        return passwordField;
    }

    public static JPanel createCardPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(SURFACE_COLOR);
        panel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(BORDER_COLOR, 1),
                        BorderFactory.createEmptyBorder(24, 24, 24, 24)
                )
        );
        return panel;
    }

    public static JLabel createLabel(String text, Font font, Color color) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setForeground(color);
        return label;
    }

    public static void showError(String message) {
        JOptionPane.showMessageDialog(null, message, "System Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void showInfo(String message) {
        JOptionPane.showMessageDialog(null, message, "System Notification", JOptionPane.INFORMATION_MESSAGE);
    }
}
