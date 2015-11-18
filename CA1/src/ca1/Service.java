package ca1;

public class Service {
    int direction, sequence;
    String serviceNum, bsCode, distance;
    
    public Service(String serNum, int dir, int seq, String bsc, String dist)
    {
        serviceNum = serNum;
        direction = dir;
        sequence = seq;
        bsCode = bsc;
        distance = dist;
    }
}
