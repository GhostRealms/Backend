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
     * Schedule a Cron into the System
     * @param cron Cron to be registered
     */
    public void schedule(final Cron cron) {
        cronTimerTaskMap.put(cron, new TimerTask() {
            @Override
            public void run() {
                cron.execute();
            }
        });

        timer.scheduleAtFixedRate(cronTimerTaskMap.get(cron), 0, cron.getInterval());
    }

    /**
     * Schedule a Cron into the System, with a delay in MS
     * @param cron Cron to be registered
     * @param delay delay to be used
     */
    public void schedule(final Cron cron, final int delay) {
        cronTimerTaskMap.put(cron, new TimerTask() {
            @Override
            public void run() {
                cron.execute();
            }
        });

        timer.scheduleAtFixedRate(cronTimerTaskMap.get(cron), delay, cron.getInterval());
    }

    /**
     * Cancel a Cron
     * @param cron Cron to be cancelled
a    */
    public void cancel(Cron cron){
        TimerTask taskToCancel = cronTimerTaskMap.get(cron);
        taskToCancel.cancel();
    }
}
