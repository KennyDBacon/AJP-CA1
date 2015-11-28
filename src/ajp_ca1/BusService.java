package ajp_ca1;

public class BusService {
    String servNum, bsCode, distance, busType;
    int direction, sequence;
    
    public BusService(String sn, int dir, int seq, String bsc, String dist, String type)
    {
        servNum = sn;
        direction = dir;
        sequence = seq;
        bsCode = bsc;
        distance = dist;
        busType = type;
    }
}
