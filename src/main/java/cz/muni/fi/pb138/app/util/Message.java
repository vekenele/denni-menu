package cz.muni.fi.pb138.app.util;

/**
 * @author Peter Neupauer
 */
public class Message {

    public enum Level {
        SUCCESS, INFO, WARNING, DANGER
    }

    private Level level;
    private String message;

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
