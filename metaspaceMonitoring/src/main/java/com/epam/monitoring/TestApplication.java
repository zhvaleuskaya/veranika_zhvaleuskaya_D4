package com.epam.monitoring;

import com.epam.monitoring.model.Person;
import com.epam.monitoring.task.MonitoringTask;

import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

/**
 * Created by Veranika_Zhvaleuskay on 6/30/2016.
 */
public class TestApplication {
    public static void main(String[] args) {

        List<Person> list = new ArrayList<Person>();
        for (int i = 0; i <= 100000; i++) {
            list.add(new Person("Jim", "Knopf"));
        }

        Timer timer = new Timer();


        // Schedule to run after every 3 second(3000 millisecond)
        timer.scheduleAtFixedRate(new MonitoringTask(getProcessId("PID")),1000 ,1000);

    }
    private static String getProcessId(final String fallback) {
        // Note: may fail in some JVM implementations
        // therefore fallback has to be provided

        // something like '<pid>@<hostname>', at least in SUN / Oracle JVMs
        final String jvmName = ManagementFactory.getRuntimeMXBean().getName();
        final int index = jvmName.indexOf('@');

        if (index < 1) {
            // part before '@' empty (index = 0) / '@' not found (index = -1)
            return fallback;
        }

        try {
            return Long.toString(Long.parseLong(jvmName.substring(0, index)));
        } catch (NumberFormatException e) {
            // ignore
        }
        return fallback;
    }
}
