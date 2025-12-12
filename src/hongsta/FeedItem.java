package hongsta;

import java.sql.Timestamp;

public class FeedItem {
    public long id;
    public long userId;
    public String username;
    public String content;
    public Timestamp createdAt;

    public FeedItem(long id, long userId, String username, String content, Timestamp createdAt) {
        this.id = id;
        this.userId = userId;
        this.username = username;
        this.content = content;
        this.createdAt = createdAt;
    }
}
