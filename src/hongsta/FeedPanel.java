package hongsta;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class FeedPanel extends JPanel {

    private JPanel feedContainer;

    public FeedPanel() {
        setLayout(new BorderLayout());

        feedContainer = new JPanel();
        feedContainer.setLayout(new BoxLayout(feedContainer, BoxLayout.Y_AXIS));

        JScrollPane scroll = new JScrollPane(feedContainer);
        add(scroll, BorderLayout.CENTER);

        loadFeed();
    }

    public void loadFeed() {
        feedContainer.removeAll();

        List<Feed> feeds = new FeedDAO().getAllFeeds();
        for (Feed f : feeds) {
            feedContainer.add(new FeedCard(f, this::loadFeed));
            feedContainer.add(Box.createVerticalStrut(10));
        }

        feedContainer.revalidate();
        feedContainer.repaint();
    }
}
