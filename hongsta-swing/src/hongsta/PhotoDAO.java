package hongsta;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class PhotoDAO {

    public boolean insertPhoto(long userId, String content, String imagePath) {
        String sql = """
                    INSERT INTO photo (user_id, content, image_path, created_at)
                    VALUES (?, ?, ?, NOW())
                """;

        try (Connection c = DBUtil.getConnection();
                PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, userId);
            ps.setString(2, content);
            ps.setString(3, imagePath);

            return ps.executeUpdate() == 1;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
