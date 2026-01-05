package hongsta;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ProfilePanel extends JPanel {

    private JPanel gridPanel;

    public ProfilePanel() {
        setLayout(new BorderLayout());

        // ===== 상단 영역 =====
        JPanel top = new JPanel();
        top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));
        top.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel username = new JLabel("@" + SessionUser.username);
        username.setFont(new Font("맑은 고딕", Font.BOLD, 18));
        top.add(username);

        top.add(Box.createVerticalStrut(10));

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JButton logoutBtn = new JButton("로그아웃");
        JButton deleteBtn = new JButton("회원 탈퇴");

        logoutBtn.addActionListener(e -> {
            SessionUser.clear();
            SwingUtilities.getWindowAncestor(this).dispose();
            new LoginFrame();
        });

        deleteBtn.addActionListener(e -> deleteAccount());

        btnPanel.add(logoutBtn);
        btnPanel.add(deleteBtn);
        top.add(btnPanel);

        add(top, BorderLayout.NORTH);

        // ===== 내 게시글 =====
        gridPanel = new JPanel(new GridLayout(0, 3, 10, 10));
        gridPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        add(new JScrollPane(gridPanel), BorderLayout.CENTER);
    }

    public void loadMyFeed() {
        gridPanel.removeAll();

        List<Feed> feeds = new FeedDAO().getMyFeeds(SessionUser.id);
        for (Feed f : feeds) {
            gridPanel.add(new FeedCard(f, this::loadMyFeed));
        }

        revalidate();
        repaint();
    }

    private void deleteAccount() {
        int result = JOptionPane.showConfirmDialog(
                this,
                "정말 탈퇴하시겠습니까?\n모든 데이터가 삭제됩니다.",
                "회원 탈퇴",
                JOptionPane.YES_NO_OPTION);

        if (result == JOptionPane.YES_OPTION) {
            new UserDAO().deleteUser(SessionUser.id);
            SessionUser.clear();
            SwingUtilities.getWindowAncestor(this).dispose();
            new LoginFrame();
        }
    }
}
