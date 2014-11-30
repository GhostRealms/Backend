package net.ghostrealms.server;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by River on 29-Nov-14 14:46.
 * Main JavaPlugin initialization
 */

public class BackendClient extends JavaPlugin {

    private static CronManager cronManager = new CronManager();

    public static CronManager getCronManager() {
        return cronManager;
    }

    @Override
    public void onLoad() {
        //Cover MySQL Setup
        Thread thread = new Thread(new SQLSetup());
        thread.run();
        //End MySQL Setup See @SQLSetup for More details
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

}
