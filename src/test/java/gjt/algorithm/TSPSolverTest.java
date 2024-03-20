package gjt.algorithm;

import junit.framework.TestCase;

import java.util.HashMap;

public class TSPSolverTest extends TestCase {

    public void testSolve() {
        map path_map = temp.graph;
        System.out.println(path_map.V);
        backtracking.answer ans = new backtracking.answer(path_map.V+1,5,path_map);

        HashMap<Integer,Integer> idToNodeID = new HashMap<>();
        idToNodeID.put(0,1);
        idToNodeID.put(1,16);
        idToNodeID.put(2,4);
        idToNodeID.put(3,19);
        idToNodeID.put(4,13);
        int n = idToNodeID.size();
        // preprocessing
        for (int i = 0 ; i < n ; i++){
            ans.setMirror(i, idToNodeID.get(i));
            //System.out.println(" set "+i + " - " + idToNodeID.get(i) );
        }

        backtracking.dfs(ans, 0,path_map.stPoint,0,n,-1); // 0 is for end

        AlgorithmResult algoRet = new AlgorithmResult("GREEN");// color 這邊可以額外去指定他應該要是啥顏色
        int w = 0;
        int last = path_map.stPoint;
        System.out.println(ans.ans.size());
        System.out.print(ans);
        for (Integer i : ans.ans){
            System.out.println(">"+i);
            int current = idToNodeID.get(i);
            algoRet.addPath(last, current, -1, ans.getPath(last, current));
            w += path_map.dp[last][current];
            last = current;
        }
        w += path_map.dp[last][path_map.edPoint];
        algoRet.addPath(last, path_map.edPoint, -1, ans.getPath(last, path_map.edPoint)); // back to start

        System.out.println(w);

    }
}