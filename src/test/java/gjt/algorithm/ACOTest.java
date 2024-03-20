package gjt.algorithm;

import junit.framework.TestCase;

import java.util.HashMap;

public class ACOTest extends TestCase {

    public void testMain() {
        map path_map = temp.graph;
        System.out.println(path_map.V);

        HashMap<Integer,Integer> idToNodeID = new HashMap<>();
        idToNodeID.put(0,1);
        idToNodeID.put(1,16);
        idToNodeID.put(2,4);
        idToNodeID.put(3,19);
        idToNodeID.put(4,13);
        int n = idToNodeID.size();


        ACO test = new ACO();
        test.solve(path_map,idToNodeID);
    }
}