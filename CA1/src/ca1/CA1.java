package ca1;

import com.google.common.base.Ascii;
import java.io.*;
import java.util.*;
import com.google.common.collect.*;

public class CA1 {
    public static void main(String[] args) throws IOException{
        String fileBusStop = "data\\lta-bus_stop_codes.csv";
        String fileService = "data\\lta-sbst_route.csv";
        
        BufferedReader brBusStop = new BufferedReader(new FileReader(fileBusStop));
        BufferedReader brService = new BufferedReader(new FileReader(fileService));
        
        brBusStop.readLine();
        brService.readLine();
        
        String line = "";
        
        HashMap<String, BusStop> busStopHashMap = new HashMap<>();
        while ((line = brBusStop.readLine()) != null)
        {
            String[] data = line.split(",");
            
            BusStop bs = new BusStop(data[0], data[1], data[2]);
            
            busStopHashMap.put(data[0], bs);
        }
        
        ListMultimap<String, Service> serviceListMultimap = ArrayListMultimap.create();
        
        //HashMap<String, Service> serviceHashMap = new HashMap<>();
        while((line = brService.readLine()) != null)
        {
            String[] data = line.split(",");
            
            int dir = Integer.parseInt(data[1]);
            int seq = Integer.parseInt(data[2]);
            
            Service svc = new Service(data[0], dir, seq, data[3], data[4]);
            
            serviceListMultimap.put(data[0], svc);
        }
        
        /*
        for (Map.Entry<String, BusStop> entry : busStopHashMap.entrySet())
        {
            System.out.println("Code: " + entry.getValue().bsCode);
            System.out.println("Road Desc: " + entry.getValue().roadDescription);
            System.out.println("BS Desc: " + entry.getValue().bsDescription);
            System.out.println("");
        }
        
        for(Map.Entry<String, Service> entry : serviceListMultimap.entries())
        {
            System.out.println("Service Number: " + entry.getValue().serviceNum);
            System.out.println("Direction: " + entry.getValue().direction);
            System.out.println("Route Sequence: " + entry.getValue().sequence);
            System.out.println("BusStop Code: " + entry.getValue().bsCode);
            System.out.println("Distance: " + entry.getValue().distance);
            System.out.println("");
        }*/
        
        Set s = new HashSet();
        System.out.print("Search: ");
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        String query = input.readLine();
        
        System.out.println("");
        
        //ArrayList<String> serviceArrayList = new ArrayList<String>();
        HashSet<String> serviceHashSet = new HashSet<>();
        for(Map.Entry<String, BusStop> busStop : busStopHashMap.entrySet())
        {
            if(busStop.getValue().bsDescription.contains(query))
            {
                for(Map.Entry<String, Service> service : serviceListMultimap.entries())
                {
                    if(service.getValue().bsCode.equals(busStop.getValue().bsCode))
                    {
                        //serviceArrayList.add(service.getValue().serviceNum);
                        serviceHashSet.add(service.getValue().serviceNum);
                    }
                }
            }
        }
        
        TreeSet<String> serviceTreeSet = new TreeSet<>();
        serviceTreeSet.addAll(serviceHashSet);
        
        //Collections.sort(serviceArrayList);
        System.out.println("Service Available");
        int index = 1;
        for(String service : serviceTreeSet)
        {
            System.out.println(index + ". " + service);
            index++;
        }
    }
}
