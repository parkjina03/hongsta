package hongsta;

import java.sql.*;

public class LikeDAO {

    public static boolean isLiked(long photoId, long userId) throws SQLException {
        String sql = "SELECT 1 FROM photo_like WHERE photo_id=? AND user_id=?";
        try (Connection c = DBUtil.getConnection();
                PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, photoId);
            ps.setLong(2, userId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    public static int countLikes(long photoId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM photo_like WHERE photo_id=?";
        try (Connection c = DBUtil.getConnection();
                PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, photoId);
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return rs.getInt(1);
            }
        }
    }

    public static void toggle(long photoId, long userId) throws SQLException {
        if (isLiked(photoId, userId)) {
            String del = "DELETE FROM photo_like WHERE photo_id=? AND user_id=?";
            try (Connection c = DBUtil.getConnection();
                    PreparedStatement ps = c.prepareStatement(del)) {
                ps.setLong(1, photoId);
                ps.setLong(2, userId);
                ps.executeUpdate();
            }
        } else {
            String ins = "INSERT INTO photo_like(photo_id, user_id) VALUES(?, ?)";
            try (Connection c = DBUtil.getConnection();
                    PreparedStatement ps = c.prepareStatement(ins)) {
                ps.setLong(1, photoId);
                ps.setLong(2, userId);
                ps.executeUpdate();
            }
        }
    }
}
