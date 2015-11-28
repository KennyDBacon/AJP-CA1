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
        for(Map.Entry<String, SBSService> sbsEntry : sBSServiceManager.sbsHashMap.entrySet())
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
        for(Map.Entry<String, SBSService> sbsEntry : sBSServiceManager.sbsHashMap.entrySet())
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
    
    DefaultListModel ShowRouteOfService(String serviceNum)
    {
        int currentSeq = 0;
        String route = "";
        
        DefaultListModel model = new DefaultListModel();
        
        String[] data;
        SBSService sbs;
        //for(Map.Entry<String, SBSService> sbsEntry : sBSServiceManager.sbsHashMap.entrySet())
        for(String keyString : sBSServiceManager.sbsHashMap.keySet())
        {
            data = keyString.split(",");
            //if(sbsEntry.getValue().servNum.equals(serviceNum))
            if(data[0].equals(serviceNum))
            {
                sbs = sBSServiceManager.sbsHashMap.get(keyString);
                
                if(sbs.direction == 1)
                    route = "Route 1";
                else
                    route = "Route 2";
                
                /*
                if(currentSeq > sbsEntry.getValue().sequence)
                    break;
                
                model.addElement(busStopManager.busStopHashMap.get(sbsEntry.getValue().bsCode).bsDesc);
                */
                
                currentSeq = sbs.sequence;
                
                model.addElement(sbs.bsCode + " - " + busStopManager.busStopHashMap.get(sbs.bsCode).bsDesc + " (" + route + ")");
            }
        }
        
        return model;
    }
    
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
            case 2: announceText = "<html>No direct service. Require one transfer to reach destination.<br>Take bus no." + option.get(1) + " and transfer at " + option.get(2) + "</html>";
                return announceText;
            // Not transferable
            case 3: announceText = "No service is available";
                return announceText;
        }
        
        return null;
    }
    
    HashMap<String, ArrayList<String>> boardingBusesHashMap;
    HashMap<String, ArrayList<String>> alightingBusesHashMap;
    private ArrayList CheckRoute(String boardingCode, String alightingCode)
    {
        ArrayList infoList = new ArrayList();
        
        if(boardingCode.equals(alightingCode))
        {
            infoList.add(0);
            
            return infoList;
        }
        else
        {
            boardingBusesHashMap = new HashMap<>();
            alightingBusesHashMap = new HashMap<>();

            String[] keyString;
            
            // Get all available boarding bus
            for(String sbsKeySet : sBSServiceManager.sbsHashMap.keySet())
            {
                keyString = sbsKeySet.split(",");

                if(keyString[1].equals(boardingCode))
                {
                    GetBoardingBus(sbsKeySet);
                }
            }

            // Get all available destination bus
            for(String sbsKeySet : sBSServiceManager.sbsHashMap.keySet())
            {
                keyString = sbsKeySet.split(",");

                if(keyString[1].equals(alightingCode))
                {
                    GetAlightingBus(keyString, sBSServiceManager.sbsHashMap.get(sbsKeySet).direction);
                }
            }
            
            // Check if direct bus is available
            for(String boardingBusKey : boardingBusesHashMap.keySet())
            {
                for(String alightingBusKey : alightingBusesHashMap.keySet())
                {
                    if(boardingBusKey.equals(alightingBusKey))
                    {
                        infoList.add(1);
                        
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
                                infoList.add(2);

                                // Add the service bus number
                                infoList.add(alightingLine.getKey());

                                // Add the transfer stop code
                                infoList.add(boardingStop);

                                return infoList;
                            }
                        }
                    }
                }
            }
            
            infoList.add(3);
            
            return infoList;
        }
    }
    
    void GetBoardingBus(String key)
    {
        ArrayList<String> busStops = new ArrayList<>();
        
        boolean isRoute = false;
        String prevServNum = "";
        int tempSeq = 0;
        
        for(Map.Entry<String, SBSService> boardSbsEntry : sBSServiceManager.sbsHashMap.entrySet())
        {
            if(!isRoute)
            {
                if(boardSbsEntry.getKey().equals(key))
                {
                    isRoute = true;
                    prevServNum = boardSbsEntry.getValue().servNum;
                    tempSeq = boardSbsEntry.getValue().sequence;
                }
            }
            else
            {
                if(tempSeq < boardSbsEntry.getValue().sequence)
                {
                    busStops.add(boardSbsEntry.getValue().bsCode);

                    prevServNum = boardSbsEntry.getValue().servNum;
                    tempSeq = boardSbsEntry.getValue().sequence;
                }
                else
                {
                    boardingBusesHashMap.put(prevServNum, busStops);
                    break;
                }
            }
        }
    }
    
    void GetAlightingBus(String[] keyString, int direction)
    {
        ArrayList<String> busStops = new ArrayList<>();
        
        String[] tempKey;
        for(Map.Entry<String, SBSService> alightSbsEntry : sBSServiceManager.sbsHashMap.entrySet())
        {
            tempKey = alightSbsEntry.getKey().split(",");
            
            // If NO, check if service number is the right service number
            if(tempKey[0].equals(keyString[0]))
            {
                // If YES, check if going the right direction
                if(alightSbsEntry.getValue().direction == direction)
                {
                    // Check if the current bus stop is the alighting number
                    if(tempKey[1].equals(keyString[1]))
                    {
                        alightingBusesHashMap.put(keyString[0], busStops);
                        break;
                    }
                    else
                    {
                        busStops.add(alightSbsEntry.getValue().bsCode);
                    }
                }
            }
        }
    }
}
