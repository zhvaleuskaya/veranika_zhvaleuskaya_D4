package com.epam.monitoring.task;

import com.epam.monitoring.MetaSpaceMonitoring;

import java.util.TimerTask;

/**
 * Created by Veranika_Zhvaleuskay on 6/30/2016.
 */
public class MonitoringTask extends TimerTask {


    private String PID;

    public MonitoringTask(String PID) {
        this.PID = PID;
    }

    // run is a abstract method that defines task performed at scheduled time.
    public void run() {

        MetaSpaceMonitoring.executeTest(PID);
    }
}

