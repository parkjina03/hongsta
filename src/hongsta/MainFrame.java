package hongsta;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private CardLayout card;
    private JPanel cardPanel;

    private FeedPanel feedPanel;
    private UploadPanel uploadPanel;
    private ChatPanel chatPanel;
    private ProfilePanel profilePanel;

    public MainFrame() {
        setTitle("Hongsta");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        card = new CardLayout();
        cardPanel = new JPanel(card);

        // 🔥 패널 생성 (절대 섞지 말 것)
        feedPanel = new FeedPanel();
        profilePanel = new ProfilePanel();
        uploadPanel = new UploadPanel(this);
        chatPanel = new ChatPanel();

        // 🔥 카드 등록 (이 이름이 전부임)
        cardPanel.add(feedPanel, "feed");
        cardPanel.add(uploadPanel, "upload");
        cardPanel.add(chatPanel, "chat");
        cardPanel.add(profilePanel, "profile");

        add(cardPanel, BorderLayout.CENTER);
        add(createNav(), BorderLayout.NORTH);

        showFeed();
        setVisible(true);
    }

    // ===== 화면 전환 메서드 =====
    public void showFeed() {
        feedPanel.loadFeed();
        card.show(cardPanel, "feed");
    }

    public void showProfile() {
        profilePanel.loadMyFeed();
        card.show(cardPanel, "profile");
    }

    private JPanel createNav() {
        JPanel nav = new JPanel(new GridLayout(1, 4));

        JButton feedBtn = new JButton("메인");
        JButton uploadBtn = new JButton("업로드");
        JButton chatBtn = new JButton("채팅");
        JButton profileBtn = new JButton("프로필");

        feedBtn.addActionListener(e -> showFeed());
        uploadBtn.addActionListener(e -> card.show(cardPanel, "upload"));
        chatBtn.addActionListener(e -> card.show(cardPanel, "chat"));

        // 🔥 여기 중요
        profileBtn.addActionListener(e -> showProfile());

        nav.add(feedBtn);
        nav.add(uploadBtn);
        nav.add(chatBtn);
        nav.add(profileBtn);

        return nav;
    }
}
