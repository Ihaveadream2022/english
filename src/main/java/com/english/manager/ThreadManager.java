package com.english.manager;

import com.english.util.SpringUtil;

import java.util.TimerTask;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ThreadManager {

    private final ScheduledExecutorService executor = (ScheduledExecutorService) SpringUtil.getBean("scheduledExecutorService");

    private static final ThreadManager instance = new ThreadManager();

    private ThreadManager(){};

    public static ThreadManager getInstance() {
        return instance;
    }

    public void execute(TimerTask task) {
        executor.schedule(task, 10, TimeUnit.MILLISECONDS);
    }
}
