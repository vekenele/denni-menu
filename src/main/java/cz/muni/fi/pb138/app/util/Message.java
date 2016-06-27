package cz.muni.fi.pb138.app.util;

/**
 * The type Message.
 *
 * @author Peter Neupauer
 */
public class Message {

    /**
     * The enum Level.
     */
    public enum Level {
        SUCCESS,
        INFO,
        WARNING,
        DANGER
    }

    private Level level;
    private String message;

    /**
     * Instantiates a new Message.
     *
     * @param level   the level
     * @param message the message
     */
    public Message(Level level, String message) {
        this.level = level;
        this.message = message;
    }

    public Level getLevel() {
        return level;
    }

    public String getMessage() {
        return message;
    }

    public String getLevelAsText() {
        return level.toString();
    }
}
