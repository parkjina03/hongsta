package hongsta;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ChatPanel extends JPanel {

    private long receiverId = -1;

    public ChatPanel() {
        setLayout(new BorderLayout());

        JPanel userListPanel = new JPanel();
        userListPanel.setLayout(new BoxLayout(userListPanel, BoxLayout.Y_AXIS));

        List<User> users = UserDAO.findAllExceptMe();
        for (User u : users) {
            JButton btn = new JButton(u.getUsername());
            btn.addActionListener(e -> {
                receiverId = u.getId();
                JOptionPane.showMessageDialog(this, u.getUsername() + " 선택됨");
            });
            userListPanel.add(btn);
        }

        add(new JScrollPane(userListPanel), BorderLayout.WEST);

        JTextArea chatLog = new JTextArea();
        chatLog.setEditable(false);

        JTextField input = new JTextField();
        JButton sendBtn = new JButton("전송");

        sendBtn.addActionListener(e -> {
            if (receiverId == -1) {
                JOptionPane.showMessageDialog(this, "대상을 선택하세요");
                return;
            }

            String msg = input.getText().trim();
            if (msg.isEmpty())
                return;

            // 🔥 여기만 바뀜
            ChatDAO.send(SessionUser.id, receiverId, msg);

            chatLog.append("나: " + msg + "\n");
            input.setText("");
        });

        JPanel bottom = new JPanel(new BorderLayout());
        bottom.add(input, BorderLayout.CENTER);
        bottom.add(sendBtn, BorderLayout.EAST);

        add(new JScrollPane(chatLog), BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);
    }
}
