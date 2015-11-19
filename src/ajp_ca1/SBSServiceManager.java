package ajp_ca1;

import com.google.common.collect.LinkedHashMultimap;
import java.io.*;

/**
 *
 * @author Kenny
 */
public class SBSServiceManager {
    String fileSBSService = "data\\lta-sbst_route.csv";
    
    LinkedHashMultimap<String, SBSService> sbsLinkedHashMultimap;
    
    public SBSServiceManager()
    {
        sbsLinkedHashMultimap = LinkedHashMultimap.create();
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
            
            SBSService sbsService = new SBSService(data[0], dir, seq, data[3], data[4]);
            
            sbsLinkedHashMultimap.put(data[0], sbsService);
        }
    }
}
