package net.ghostrealms.server.log;

/**
 * Created by River on 02-Dec-14.
 * Interface for creating internal logger objects
 */
public abstract class RealmsLogger {

    enum Level {TRIVIAL,LOW,NORMAL,HIGH,CRITICAL}

    public RealmsLogger getLogger() {
        return this;
    }

    public abstract void log(Level level, String message);
    public abstract void trivial(String message);
    public abstract void low(String message);
    public abstract void normal(String message);
    public abstract void high(String message);
    public abstract void critical(String message);

}
