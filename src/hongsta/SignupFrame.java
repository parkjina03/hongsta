package hongsta;

import javax.swing.*;
import java.awt.*;

public class SignupFrame extends JFrame {

    public SignupFrame() {
        setTitle("Signup");
        setSize(350, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTextField idField = new JTextField();
        JPasswordField pwField = new JPasswordField();

        JButton signupBtn = new JButton("회원가입");
        JButton backBtn = new JButton("뒤로가기");

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("아이디"));
        panel.add(idField);
        panel.add(Box.createVerticalStrut(10));

        panel.add(new JLabel("비밀번호"));
        panel.add(pwField);
        panel.add(Box.createVerticalStrut(15));

        panel.add(signupBtn);
        panel.add(Box.createVerticalStrut(5));
        panel.add(backBtn);

        add(panel);

        signupBtn.addActionListener(e -> {
            String id = idField.getText();
            String pw = new String(pwField.getPassword());

            UserDAO dao = new UserDAO();
            if (dao.signup(id, pw)) {
                JOptionPane.showMessageDialog(this, "회원가입 성공");
                dispose();
                new LoginFrame();
            } else {
                JOptionPane.showMessageDialog(this, "회원가입 실패");
            }
        });

        backBtn.addActionListener(e -> {
            dispose();
            new LoginFrame();
        });

        setVisible(true);
    }
}
