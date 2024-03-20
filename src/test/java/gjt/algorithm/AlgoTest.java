package gjt.algorithm;

import junit.framework.TestCase;

import java.util.HashMap;


public class AlgoTest extends TestCase {

    private int floyd_backtracking(map path_map,HashMap<Integer,Integer> idToNodeID){

        backtracking.answer ans = new backtracking.answer(path_map.V+1,5,path_map);

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
//        System.out.println(ans.ans.size());
//        System.out.print(ans);
        for (Integer i : ans.ans){
//            System.out.println(">"+i);
            int current = idToNodeID.get(i);
            algoRet.addPath(last, current, -1, ans.getPath(last, current));
            w += path_map.dp[last][current];
            last = current;
        }
        w += path_map.dp[last][path_map.edPoint];
        algoRet.addPath(last, path_map.edPoint, -1, ans.getPath(last, path_map.edPoint)); // back to start

        return w;
    }

    public void test(){
        map path_map = temp.graph;
        HashMap<Integer,Integer> idToNodeID = new HashMap<>();
        // init map
        /*
        1221221   (00) (01) (02) (03) (04) (05) (06)
        1001001   (07) xxxx xxxx (10) xxxx xxxx (13)
        1221221   (14) (15) (16) (17) (18) (19) (20)
           1                     (24)

           String[] map = {
                ".......",
                ".##.##.",
                ".......",
                "   .   ",
                "###.###",
                ".......",
                "   E   ",
        };
         */
        idToNodeID.put(0,1);
        idToNodeID.put(1,10);
        idToNodeID.put(2,17);
        idToNodeID.put(3,24);

        ACO test = new ACO();



        int f_w = floyd_backtracking(path_map,idToNodeID);
        int aco_w = test.solve(path_map,idToNodeID);

        System.out.println("最佳演算法:"+f_w);
        System.out.println("蟻群演算法:"+aco_w);

    }

}
