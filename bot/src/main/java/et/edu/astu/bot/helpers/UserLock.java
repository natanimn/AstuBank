package et.edu.astu.bot.helpers;

import java.util.concurrent.ConcurrentHashMap;

public abstract class UserLock {
    private static ConcurrentHashMap<Long, Object> lock;

    static {
        lock = new ConcurrentHashMap<>();
    }

    public static ConcurrentHashMap<Long, Object> getLock() {
        return lock;
    }
}
