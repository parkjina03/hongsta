package hongsta;

import java.sql.Timestamp;

public class ChatMessage {
    public String sender;
    public String message;
    public Timestamp time;

    @Override
    public String toString() {
        return sender + " : " + message;
    }
}
