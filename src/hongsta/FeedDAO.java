package hongsta;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FeedDAO {

    public void insertFeed(long userId, String content, String imagePath) {
        String sql = "INSERT INTO feed (user_id, content, image_path) VALUES (?, ?, ?)";

        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, userId);
            ps.setString(2, content);
            ps.setString(3, imagePath);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteFeed(long feedId, long userId) {
        String sql = "DELETE FROM feed WHERE feed_id = ? AND user_id = ?";

        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, feedId);
            ps.setLong(2, userId);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ===== 메인 (전체 피드) =====
    public List<Feed> getAllFeeds() {
        List<Feed> list = new ArrayList<>();
        String sql = "SELECT feed_id, user_id, content, image_path FROM feed ORDER BY feed_id DESC";

        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Feed f = new Feed();
                f.setFeedId(rs.getLong("feed_id"));
                f.setUserId(rs.getLong("user_id"));
                f.setContent(rs.getString("content"));
                f.setImagePath(rs.getString("image_path"));
                list.add(f);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // ===== 프로필 (내 피드만) =====
    public List<Feed> getMyFeeds(long userId) {
        List<Feed> list = new ArrayList<>();
        String sql = "SELECT feed_id, user_id, content, image_path " +
                "FROM feed WHERE user_id = ? ORDER BY feed_id DESC";

        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Feed f = new Feed();
                    f.setFeedId(rs.getLong("feed_id"));
                    f.setUserId(rs.getLong("user_id"));
                    f.setContent(rs.getString("content"));
                    f.setImagePath(rs.getString("image_path"));
                    list.add(f);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
