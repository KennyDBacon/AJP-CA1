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
    BusServiceManager busServiceManager;
    
    public Manager() throws IOException
    {
        busStopManager = new BusStopManager();
        busServiceManager = new BusServiceManager();
        
        busStopManager.Setup();
        busServiceManager.Setup();
    }
        
    // -- > //
    ArrayList<BusStop> SearchByStopDescription(String query)
    {
        String currentBusStop = "";
        
        query = query.toLowerCase();
        
        ArrayList<BusStop> busStops = new ArrayList<>();
        for(Map.Entry<String, BusStop> busStopEntry : busStopManager.busStopHashMap.entrySet())
        {
            currentBusStop = busStopEntry.getValue().bsDesc.toLowerCase();
            
            if(currentBusStop.contains(query))
            {
                busStops.add(busStopEntry.getValue());
            }
        }
        
        return busStops;
    }
    
    DefaultListModel ShowServicesOfStop(String busStopCode)
    {
        DefaultListModel model = new DefaultListModel();
        for(Map.Entry<String, BusService> busEntry : busServiceManager.serviceHashMap.entrySet())
        {
            if(busEntry.getValue().bsCode.equals(busStopCode))
            {
                model.addElement(busEntry.getValue().servNum + " (" + busEntry.getValue().busType + ")");
            }
        }
        
        return model;
    }
    
    // -- > //
    
    ArrayList<BusService> SearchByServicesNumber(String query)
    {
        boolean isNew = true;
        ArrayList<BusService> services = new ArrayList<>();
        for(Map.Entry<String, BusService> busEntry : busServiceManager.serviceHashMap.entrySet())
        {
            if(busEntry.getValue().servNum.contains(query))
            {
                isNew = true;
                
                for(BusService service : services)
                {
                    if(busEntry.getValue().servNum.equals(service.servNum))
                    {
                        isNew = false;
                        break;
                    }
                }
                
                if(isNew)
                {
                    services.add(busEntry.getValue());
                }
            }
        }
        
        return services;
    }
    
    DefaultListModel ShowRouteOfService(String serviceNum)
    {
        int currentSeq = 0;
        String route = "";
        
        DefaultListModel model = new DefaultListModel();
        
        String[] data;
        BusService busService;
        for(String keyString : busServiceManager.serviceHashMap.keySet())
        {
            data = keyString.split(",");
            if(data[0].equals(serviceNum))
            {
                busService = busServiceManager.serviceHashMap.get(keyString);
                
                if(busService.direction == 1)
                    route = "Route 1";
                else
                    route = "Route 2";
                
                /*
                if(currentSeq > sbsEntry.getValue().sequence)
                    break;
                
                model.addElement(busStopManager.busStopHashMap.get(sbsEntry.getValue().bsCode).bsDesc);
                */
                
                currentSeq = busService.sequence;
                
                model.addElement(busService.bsCode + " - " + busStopManager.busStopHashMap.get(busService.bsCode).bsDesc + " (" + route + ")");
            }
        }
        
        return model;
    }
    
    // -- > //
    
    String CheckAvailableRoute(String boardingCode, String alightingCode)
    {
        ArrayList option = CheckRoute(boardingCode, alightingCode);
        
        String announceText = "";
        switch((int)option.get(0))
        {
            // Same boarding and alighting code
            case 0: announceText = "The boarding stop and the alighting stop is the same";
                return announceText;
            // Direct bus available
            case 1: announceText = "Direct service available. Take bus no." + option.get(1);
                return announceText;
            // Transferable
            case 2: announceText = "<html>No direct service. Require one transfer to reach destination.<br>"
                                 + "Take bus no." + option.get(1) + " and transfer at " + option.get(2) + ", then take " + option.get(3) + ".</html>";
                return announceText;
            // Not transferable
            case 3: announceText = "No service is available";
                return announceText;
        }
        
        return null;
    }
    
    HashMap<String, ArrayList<String>> boardingBusesHashMap;
    HashMap<String, ArrayList<String>> alightingBusesHashMap;
    ArrayList<String> CheckRoute(String boardingCode, String alightingCode)
    {
        ArrayList<String> infoList = new ArrayList<>();
        
        if(boardingCode.equals(alightingCode))
        {
            infoList.add("0");
            
            return infoList;
        }
        else
        {
            boardingBusesHashMap = new HashMap<>();
            alightingBusesHashMap = new HashMap<>();

            String[] keyString;
            
            // Get all available boarding bus
            for(String busString : busServiceManager.serviceHashMap.keySet())
            {
                if(busServiceManager.serviceHashMap.get(busString).bsCode.equals(boardingCode))
                {
                    GetBoardingBus(busString);
                }
            }

            // Get all available destination bus
            for(String busString : busServiceManager.serviceHashMap.keySet())
            {
                if(busServiceManager.serviceHashMap.get(busString).bsCode.equals(alightingCode))
                {
                    GetAlightingBus(busString);
                }
            }
            
            // Check if direct bus is available
            for(String boardingBusKey : boardingBusesHashMap.keySet())
            {
                for(String alightingBusKey : alightingBusesHashMap.keySet())
                {
                    if(boardingBusKey.equals(alightingBusKey))
                    {
                        infoList.add("1");
                        
                        // Add the direct service bus number
                        infoList.add(boardingBusKey);
                        
                        return infoList;
                    }
                }
            }
            
            // If not check if transfer is available
            for(Map.Entry<String, ArrayList<String>> boardingLine : boardingBusesHashMap.entrySet())
            {
                for(Map.Entry<String, ArrayList<String>> alightingLine : alightingBusesHashMap.entrySet())
                {
                    for(String boardingStop : boardingLine.getValue())
                    {
                        for(String alightingStop : alightingLine.getValue())
                        {
                            if(boardingStop.equals(alightingStop))
                            {
                                infoList.add("2");

                                String[] data = boardingLine.getKey().split(",");
                                // Add boarding service bus number
                                infoList.add(data[0]);

                                // Add the transfer stop code
                                // Note: for testing transfer, use 66009 as boarding and 17101 as alighting
                                for(BusStop busStop : busStopManager.busStopHashMap.values())
                                {
                                    if(busStop.bsCode.equals(boardingStop))
                                    {
                                        infoList.add(busStop.bsDesc);
                                        break;
                                    }
                                }
                                
                                data = alightingLine.getKey().split(",");
                                // Add transfer bus number
                                infoList.add(data[0]);

                                return infoList;
                            }
                        }
                    }
                }
            }
            
            infoList.add("3");
            
            return infoList;
        }
    }
    
    void GetBoardingBus(String key)
    {
        ArrayList<String> busStops = new ArrayList<>();
        
        boolean isRoute = false;
        String prevServNum = "";
        int tempSeq = 0;
        
        for(Map.Entry<String, BusService> boardEntry : busServiceManager.serviceHashMap.entrySet())
        {
            if(!isRoute)
            {
                if(boardEntry.getKey().equals(key))
                {
                    isRoute = true;
                    //prevServNum = boardEntry.getValue().servNum;
                    tempSeq = boardEntry.getValue().sequence;
                }
            }
            else
            {
                if(tempSeq < boardEntry.getValue().sequence)
                {
                    busStops.add(boardEntry.getValue().bsCode);

                    //prevServNum = boardEntry.getValue().servNum;
                    tempSeq = boardEntry.getValue().sequence;
                }
                else
                {
                    if(!busStops.isEmpty())
                    {
                        boardingBusesHashMap.put(key, busStops);
                    }
                    
                    break;
                }
            }
        }
    }
    
    void GetAlightingBus(String keyString)
    {
        ArrayList<String> busStops = new ArrayList<>();
        
        String[] key = keyString.split(",");
        String[] tempKey;
        for(Map.Entry<String, BusService> alightEntry : busServiceManager.serviceHashMap.entrySet())
        {
            tempKey = alightEntry.getKey().split(",");
            
            // check if service number is the right service number
            if(tempKey[0].equals(key[0]))
            {
                // If YES, check if going the right direction
                if(alightEntry.getValue().direction == Integer.parseInt(key[2]))
                {
                    // Check if the current bus stop is the alighting number
                    if(tempKey[1].equals(key[1]))
                    {
                        alightingBusesHashMap.put(keyString, busStops);
                        break;
                    }
                    else
                    {
                        busStops.add(alightEntry.getValue().bsCode);
                    }
                }
            }
        }
    }
}
