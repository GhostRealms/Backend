package net.ghostrealms.server;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by River on 29-Nov-14 18:41.
 * Class used for creating and running repeated jobs on a scheduled interval
 * Nicknamed: Crons
 */

public class Cron {

    public static Map<String, Cron> cronMap = new HashMap<String, Cron>();

    public static Cron getCronByName(String name) {
        return cronMap.get(name);
    }

    //Define the Interval to run the jobs. This is in MILLISECONDS
    private int interval = -1;
    //Name of the Cron
    //CronJob = default
    private String name = "CronJob";

    //List of Jobs to be Executed at the specified interval
    private List<Job> executors = null;

    public Cron(int interval, String name) {
        if(interval <= 0) {
            throw new IllegalArgumentException("Interval must be greater than 0 ms");
        } else {
            this.interval = interval;
        }
        this.name = name;
        cronMap.put(name, this);
    }

    public Cron(int interval, String name, Job[] jobs) {
        if(interval <= 0) {
            throw new IllegalArgumentException("Interval must be greater than 0 ms");
        } else {
            this.interval = interval;
        }
        this.name = name;
        Collections.addAll(executors, jobs);
        cronMap.put(name, this);
    }

    public void execute() {
        Thread thread = new Thread( new Runnable() {
            @Override
            public void run() {
                for(Job job : executors) {
                    job.execute();
                }
            }
        });
        thread.start();
    }

    public void addJob(Job job) {
        executors.add(job);
    }

    public void addJobs(Job[] jobs) {
        Collections.addAll(executors, jobs);
    }

    public int getInterval() {
        return interval;
    }
}