package com.epam.monitoring;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.apache.log4j.Logger;

/**
 * Created by Veranika_Zhvaleuskay on 6/30/2016.
 */
public class MetaSpaceMonitoring {

    private static final Logger LOG = Logger.getLogger(MetaSpaceMonitoring.class);
    public static void executeTest(String PID) {

        MetaSpaceMonitoring obj = new MetaSpaceMonitoring();

        //in mac oxs
        String command = "jstat -class " + PID ;
        String command2 = "jmap -clstats " + PID;

        //in windows
        //String command = "ping -n 3 " + domainName;

        String output = obj.executeCommand(command);
        String output2 = obj.executeCommand(command2);

        LOG.info(output);
        LOG.info(output2);

    }

    private String executeCommand(String command) {

        StringBuffer output = new StringBuffer();

        Process p;
        try {
            p = Runtime.getRuntime().exec(command);
            p.waitFor();

            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(p.getInputStream()));

            String line = "";
            while ((line = reader.readLine())!= null) {
                output.append(line + "\n");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return output.toString();

    }

}