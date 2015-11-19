package ajp_ca1;

/**
 *
 * @author Kenny
 */
import java.io.*;
import java.util.*;

public class BusStopManager {
    String fileBusStop = "data\\lta-bus_stop_codes.csv";

    HashMap<String, BusStop> busStopHashMap;

    public BusStopManager()
    {
        busStopHashMap = new HashMap<>();
    }

    void Setup() throws IOException
    {
        BufferedReader brBusStop = new BufferedReader(new FileReader(fileBusStop));
        brBusStop.readLine();

        String line = "";

        while((line = brBusStop.readLine()) != null)
        {
            String [] data = line.split(",");

            BusStop bs = new BusStop(data[0], data[1], data[2]);

            busStopHashMap.put(data[0], bs);
        }
    }
}
