package net.ghostrealms.server;

import net.ghostrealms.server.database.MySQL;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by River on 29-Nov-14 14:46.
 * Main JavaPlugin initialization
 */

public class BackendClient extends JavaPlugin {

    private static CronManager cronManager = new CronManager();
    private static MySQL mySQL;

    public static CronManager getCronManager() {
        return cronManager;
    }

    @Override
    public void onLoad() {

        String user = getConfig().getString("database.username");
        String pass = getConfig().getString("database.password");
        String host = getConfig().getString("database.hostname");
        int port = getConfig().getInt("database.port");

        mySQL = new MySQL(user, pass, host, port);
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

}
