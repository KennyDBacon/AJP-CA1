package ajp_ca1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class BusServiceManager {
    String fileSBSService = "data\\lta-sbst_route.csv";
    String fileSMRTService = "data\\lta-smrt_route.csv";
    
    Map<String, BusService> serviceHashMap;
    
    public BusServiceManager()
    {
        serviceHashMap = new LinkedHashMap<>();
    }
    
    void Setup() throws IOException
    {
        BufferedReader brSBSService = new BufferedReader(new FileReader(fileSBSService));
        BufferedReader brSMRTService = new BufferedReader(new FileReader(fileSMRTService));
        brSBSService.readLine();
        brSMRTService.readLine();
        
        String line = "";
        String[] data;
        
        while((line = brSBSService.readLine()) != null)
        {
            data = line.split(",");
            AddService(data, "SBS");
        }
        
        while((line = brSMRTService.readLine()) != null)
        {
            data = line.split(",");
            AddService(data, "SMRT");
        }
    }
    
    private void AddService(String[] data, String type)
    {
        int dir = Integer.parseInt(data[1]);
        int seq = Integer.parseInt(data[2]);

        String key = data[0] + "," + data[3] + "," + data[1];
        BusService busService = new BusService(data[0], dir, seq, data[3], data[4], type);

        serviceHashMap.put(key, busService);
    }
}
