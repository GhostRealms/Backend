package net.ghostrealms.server;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by River on 29-Nov-14 19:35.
 * Schedule Crons to be run at a certain interval
 */
public class CronManager {

    private Map<Cron, TimerTask> cronTimerTaskMap = new HashMap<Cron, TimerTask>();
    private Timer timer = new Timer();

    /**
     * Schedule a Cron Job into the System
     * @param cron
     */
    public void schedule(final Cron cron) {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
               cron.execute();
            }
        }, 0, cron.getInterval());
    }

    /**
     * Schedule a Cron job into the System, with a delay in MS
     * @param cron
     * @param delay
     */
    public void schedule(final Cron cron, final int delay) {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                cron.execute();
            }
        }, delay, cron.getInterval());
    }
}
