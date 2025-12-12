package hongsta;

import java.sql.Timestamp;

public class Comment {
    public final long id;
    public final long photoId;
    public final String username;
    public final String content;
    public final Timestamp createdAt;

    public Comment(long id, long photoId, String username,
            String content, Timestamp createdAt) {
        this.id = id;
        this.photoId = photoId;
        this.username = username;
        this.content = content;
        this.createdAt = createdAt;
    }
}
