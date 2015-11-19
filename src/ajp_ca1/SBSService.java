package ajp_ca1;

/**
 *
 * @author Kenny
 */
public class SBSService {
    String servNum, bsCode, distance;
    int direction, sequence;
    
    public SBSService(String sn, int dir, int seq, String bsc, String dist)
    {
        servNum = sn;
        direction = dir;
        sequence = seq;
        bsCode = bsc;
        distance = dist;
    }
}
