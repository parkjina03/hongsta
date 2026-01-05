package hongsta;

import java.sql.*;
import java.util.*;

public class ChatDAO {

    public static void send(long senderId, long receiverId, String msg) {
        String sql = "INSERT INTO chat(sender_id, receiver_id, message) VALUES (?,?,?)";
        try (Connection c = DBUtil.getConnection();
                PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, senderId);
            ps.setLong(2, receiverId);
            ps.setString(3, msg);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<ChatMessage> load(long me, long other) {
        List<ChatMessage> list = new ArrayList<>();

        String sql = """
                    SELECT u.username, c.message, c.created_at
                    FROM chat c
                    JOIN users u ON u.id = c.sender_id
                    WHERE (c.sender_id=? AND c.receiver_id=?)
                       OR (c.sender_id=? AND c.receiver_id=?)
                    ORDER BY c.created_at
                """;

        try (Connection c = DBUtil.getConnection();
                PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, me);
            ps.setLong(2, other);
            ps.setLong(3, other);
            ps.setLong(4, me);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ChatMessage m = new ChatMessage();
                m.sender = rs.getString(1);
                m.message = rs.getString(2);
                m.time = rs.getTimestamp(3);
                list.add(m);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
