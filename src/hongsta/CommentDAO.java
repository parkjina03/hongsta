package hongsta;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommentDAO {

    public static List<Comment> findByPhoto(long photoId) throws SQLException {
        String sql = """
                    SELECT c.id, c.photo_id, u.username, c.content, c.created_at
                    FROM comment c
                    JOIN users u ON c.user_id = u.id
                    WHERE c.photo_id = ?
                    ORDER BY c.created_at ASC
                """;

        List<Comment> list = new ArrayList<>();

        try (Connection c = DBUtil.getConnection();
                PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, photoId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new Comment(
                            rs.getLong("id"),
                            rs.getLong("photo_id"),
                            rs.getString("username"),
                            rs.getString("content"),
                            rs.getTimestamp("created_at")));
                }
            }
        }
        return list;
    }

    public static void insert(long photoId, long userId, String content) throws SQLException {
        String sql = """
                    INSERT INTO comment (photo_id, user_id, content)
                    VALUES (?, ?, ?)
                """;

        try (Connection c = DBUtil.getConnection();
                PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, photoId);
            ps.setLong(2, userId);
            ps.setString(3, content);
            ps.executeUpdate();
        }
    }
}
