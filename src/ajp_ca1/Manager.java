package ajp_ca1;

import java.io.IOException;
import java.util.*;
import javax.swing.DefaultListModel;

/**
 *
 * @author Kenny
 */
public class Manager {
    BusStopManager busStopManager;
    SBSServiceManager sBSServiceManager;
    
    public Manager() throws IOException
    {
        busStopManager = new BusStopManager();
        sBSServiceManager = new SBSServiceManager();
        
        busStopManager.Setup();
        sBSServiceManager.Setup();
    }
        
    ArrayList<BusStop> SearchByStopDescription(String query)
    {
        ArrayList<BusStop> busStops = new ArrayList<>();
        for(Map.Entry<String, BusStop> busStopEntry : busStopManager.busStopHashMap.entrySet())
        {
            if(busStopEntry.getValue().bsDesc.contains(query))
            {
                busStops.add(busStopEntry.getValue());
            }
        }
        
        return busStops;
    }
    
    DefaultListModel SearchByServiceNum(String busStopCode)
    {
        DefaultListModel model = new DefaultListModel();
        for(Map.Entry<String, SBSService> sbsEntry : sBSServiceManager.sbsLinkedHashMultimap.entries())
        {
            if(sbsEntry.getValue().bsCode.equals(busStopCode))
            {
                model.addElement(sbsEntry.getValue().servNum);
            }
        }
        
        return model;
    }
    
    ArrayList<SBSService> SearchByServicesNumber(String query)
    {
        boolean isNew = true;
        ArrayList<SBSService> services = new ArrayList<>();
        for(Map.Entry<String, SBSService> sbsEntry : sBSServiceManager.sbsLinkedHashMultimap.entries())
        {
            if(sbsEntry.getValue().servNum.contains(query))
            {
                isNew = true;
                
                for(SBSService service : services)
                {
                    if(sbsEntry.getValue().servNum.equals(service.servNum))
                    {
                        isNew = false;
                        break;
                    }
                }
                
                if(isNew)
                {
                    services.add(sbsEntry.getValue());
                }
            }
        }
        
        return services;
    }
    
    DefaultListModel SearchRouteOfService(String serviceNum)
    {
        int currentSeq = 0;
        String route = "";
        DefaultListModel model = new DefaultListModel();
        for(Map.Entry<String, SBSService> sbsEntry : sBSServiceManager.sbsLinkedHashMultimap.entries())
        {            
            if(sbsEntry.getValue().servNum.equals(serviceNum))
            {
                if(sbsEntry.getValue().direction == 1)
                    route = "Route 1";
                else
                    route = "Route 2";
                
                /*
                if(currentSeq > sbsEntry.getValue().sequence)
                    break;
                
                model.addElement(busStopManager.busStopHashMap.get(sbsEntry.getValue().bsCode).bsDesc);
                */
                
                currentSeq = sbsEntry.getValue().sequence;
                
                model.addElement(busStopManager.busStopHashMap.get(sbsEntry.getValue().bsCode).bsDesc + " (" + route + ")");
            }
        }
        
        return model;
    }
    
    ArrayList CheckRoute(String boardingCode, String alightingCode)
    {
        ArrayList<String> serviceArrayList = new ArrayList<>();
        ArrayList<ArrayList<String>> routeArrayList = new ArrayList<>();
        
        ArrayList<String> dumpArrayList = new ArrayList<>();
        
        boolean isRoute = false;
        int seq = 0;
        
        for(SBSService service : sBSServiceManager.sbsLinkedHashMultimap.values())
        {
            if(!isRoute)
            {
                if(service.bsCode.equals(boardingCode))
                {
                    isRoute = true;
                    
                    dumpArrayList.clear();
                    seq = service.sequence;
                    
                    System.out.println(service.servNum);
                    serviceArrayList.add(service.servNum);
                    dumpArrayList.add(service.bsCode);
                    /*
                    seq = service.sequence;
                    serviceArrayList.add(service.servNum);
                    dumpArrayList.add(service.bsCode);
                                    
                    System.out.println(service.servNum);                    
                    System.out.println(service.bsCode);                    
                    */
                }   
            }
            else
            {
                if(seq < service.sequence)
                {
                    seq = service.sequence;
                    dumpArrayList.add(service.bsCode);
                }
                else
                {
                    isRoute = false;
                    System.out.println(dumpArrayList);
                    routeArrayList.add(dumpArrayList);
                }
            }
        }
                
        /*
        for(String serviceNum : sBSServiceManager.sbsLinkedHashMultimap.keySet())
        {
            if(!isRoute)
            {
                Set<SBSService> dumpHashSet = sBSServiceManager.sbsLinkedHashMultimap.get(serviceNum);
                
                if(service.getValue().bsCode.equals(boardingCode))
                {
                    isRoute = true;
                    dumpArrayList.clear();
                    
                    seq = service.getValue().sequence;
                    serviceArrayList.add(service.getValue().servNum);
                    dumpArrayList.add(service.getValue().bsCode);
                }   
            }
            else
            {
                if(seq < service.getValue().sequence)
                {
                    seq = service.getValue().sequence;
                    dumpArrayList.add(service.getValue().bsCode);
                }
                else
                {
                    isRoute = false;
                    
                    routeArrayList.add(dumpArrayList);
                }
            }
        }
        */
        isRoute = false;
        /*
        for(Map.Entry<String, SBSService> service : sBSServiceManager.sbsLinkedHashMultimap.entries())
        {
            if(!isRoute)
            {
                if(service.getValue().bsCode.equals((alightingCode)))
                {
                    isRoute = true;
                    
                    seq = service.getValue().sequence;
                    serviceNum = service.getValue().servNum;
                    dumpArrayList.clear();
                    dumpArrayList.add(service.getValue().bsCode);
                }
            }
            else
            {
                if(seq < service.getValue().sequence)
                {
                    seq = service.getValue().sequence;
                    dumpArrayList.add(service.getValue().bsCode);
                }
                else
                {
                    isRoute = false;
                    
                    alightingBusesHashMap.put(dumpArrayList, serviceNum);
                }
            }
        }
        
        boolean isTransferable = false;
        String transferStop = "";
        ArrayList stuff = new ArrayList();
        
        outerloop:        
        for(Map.Entry<ArrayList<String>, String> boardingBusEntry : boardingBusesHashMap.entrySet())
        {
            for(Map.Entry<ArrayList<String>, String> alightingBusEntry : alightingBusesHashMap.entrySet())
            {
                for(String boardingStop : boardingBusEntry.getKey())
                {
                    for(String alightingStop : alightingBusEntry.getKey())
                    {
                        if(boardingStop.equals(alightingStop))
                        {
                            stuff.add(boardingBusEntry.getValue());
                            stuff.add(alightingBusEntry.getValue());
                            stuff.add(boardingBusEntry.getKey());
                            stuff.add(alightingBusEntry.getKey());
                            transferStop = boardingBusEntry.getValue() + ", " + alightingBusEntry.getValue();
                            break outerloop;
                        }
                    }
                }
            }
        }
*/
        
        return null;
    }
}
