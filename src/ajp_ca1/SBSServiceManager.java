package ajp_ca1;

import java.io.*;
import java.util.*;

/**
 *
 * @author Kenny
 */
public class SBSServiceManager {
    String fileSBSService = "data\\lta-sbst_route.csv";
    
    Map<String, SBSService> sbsHashMap;
    //HashMap<String, SBSService> sbsHashMap = new HashMap<>();
    
    public SBSServiceManager()
    {
        sbsHashMap = new LinkedHashMap();
    }
    
    void Setup() throws IOException
    {
        BufferedReader brSBSService = new BufferedReader(new FileReader(fileSBSService));
        brSBSService.readLine();
        
        String line = "";
        
        while((line = brSBSService.readLine()) != null)
        {
            String data[] = line.split(",");
            
            int dir = Integer.parseInt(data[1]);
            int seq = Integer.parseInt(data[2]);
            
            String key = data[0] + "," + data[3];
            SBSService sbsService = new SBSService(data[0], dir, seq, data[3], data[4]);
            
            sbsHashMap.put(key, sbsService);
        }
    }
}
