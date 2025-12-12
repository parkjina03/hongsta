package hongsta;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class FeedCard extends JPanel {

    public FeedCard(Feed feed, Runnable onDeleteRefresh) {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        if (feed.getImagePath() != null && !feed.getImagePath().isEmpty()) {
            File img = new File(feed.getImagePath());
            if (img.exists()) {
                ImageIcon icon = new ImageIcon(feed.getImagePath());
                Image scaled = icon.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
                add(new JLabel(new ImageIcon(scaled)), BorderLayout.NORTH);
            }
        }

        JTextArea text = new JTextArea(feed.getContent());
        text.setEditable(false);
        text.setLineWrap(true);
        text.setWrapStyleWord(true);
        text.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        add(text, BorderLayout.CENTER);

        if (feed.getUserId() == SessionUser.id) {
            JButton deleteBtn = new JButton("삭제");
            deleteBtn.addActionListener(e -> {
                new FeedDAO().deleteFeed(feed.getFeedId(), SessionUser.id);
                onDeleteRefresh.run();
            });

            JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            bottom.add(deleteBtn);
            add(bottom, BorderLayout.SOUTH);
        }
    }
}
