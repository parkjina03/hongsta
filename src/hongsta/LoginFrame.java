package hongsta;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {

    public LoginFrame() {
        setTitle("Login");
        setSize(350, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTextField idField = new JTextField();
        JPasswordField pwField = new JPasswordField();

        JButton loginBtn = new JButton("로그인");
        JButton signupBtn = new JButton("회원가입");

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("아이디"));
        panel.add(idField);
        panel.add(Box.createVerticalStrut(10));

        panel.add(new JLabel("비밀번호"));
        panel.add(pwField);
        panel.add(Box.createVerticalStrut(15));

        panel.add(loginBtn);
        panel.add(Box.createVerticalStrut(5));
        panel.add(signupBtn);

        add(panel);

        loginBtn.addActionListener(e -> {
            String id = idField.getText();
            String pw = new String(pwField.getPassword());

            UserDAO dao = new UserDAO();
            if (dao.login(id, pw)) {
                dispose();
                new MainFrame();
            } else {
                JOptionPane.showMessageDialog(this, "로그인 실패");
            }
        });

        signupBtn.addActionListener(e -> {
            dispose();
            new SignupFrame();
        });

        setVisible(true);
    }
}
