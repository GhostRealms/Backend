package net.ghostrealms.server;

import net.ghostrealms.server.database.Cache;
import net.ghostrealms.server.database.MySQL;
import net.ghostrealms.server.log.RealmsLogger;

/**
 * Created by River on 30-Nov-14.
 * Provides getters for common API components for the backend systems
 */
public class GhostAPI {

    private static RealmsLogger realmsLogger;
    private static Cache cacheInterface;
    private static MySQL sqlInterface;

    protected static void setLogger(RealmsLogger logger) {
        realmsLogger = logger;
    }

    protected static void setCache(Cache cache) {
        cacheInterface = cache;
    }

    protected static void setSQLInstance(MySQL sql) {
        sqlInterface = sql;
    }

    public static RealmsLogger getLogger() {
        return realmsLogger;
    }

    public static Cache getCacheInterface() {
        return cacheInterface;
    }

    public static MySQL getMySQLInterface() {
        return sqlInterface;
    }

}
