package net.ghostrealms.server;

import net.ghostrealms.server.database.Cache;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by River on 29-Nov-14 14:46.
 * Main JavaPlugin initialization
 */

public class BackendClient extends JavaPlugin {

    private static CronManager cronManager = new CronManager();
    private Cache cache;

    public static CronManager getCronManager() {
        return cronManager;
    }

    @Override
    public void onLoad() {

        String user = getConfig().getString("database.username");
        String pass = getConfig().getString("database.password");
        String host = getConfig().getString("database.hostname");
        int port = getConfig().getInt("database.port");


        String redis_host = getConfig().getString("database.cache.host");

        cache = new Cache(redis_host);

        GhostAPI.setCache(cache);

    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

}
