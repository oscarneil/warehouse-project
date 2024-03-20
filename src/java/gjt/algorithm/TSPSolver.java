package gjt.algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import gjt.usblab.Server.Server;


public class TSPSolver {
    // 20240124 將 AppromixateTSP 改成 TSPSolver 並且需要建立 和依靠 gjt.algorithm.map
    public static HashMap<Integer,AlgorithmResult> mp = new HashMap<>();

    // get from current to next path
    // if nextResult return true need to do
    public ItemPath getCurrent(int code){
        return mp.get(code).getCurrent();
    }
    public String getColor(int code){
        return mp.get(code).LEDcolor;
    }
    /**
     * iterator
     * @param code - qr code
     * @return
     */
    public boolean nextResult(int code){
        return mp.get(code).next();
    }

    public boolean test(int code){
        return mp.get(code).test();
    }
    public boolean isLast(int code){
        return mp.get(code).isLast();
    }


    static Object lock = new Object();
    /***
     * 這邊主要處理 抓取 要借用的物品
     * @param idToNodeID
     * @param idToRiNo
     * @param code
     * @return 資料數量 (必須經過物品的數量)
     */
    private int SQLgetData(HashMap<Integer,Integer> idToNodeID,HashMap<Integer,Integer> idToRiNo,int code){
        ArrayList<HashMap<String,Object>> ret = Server.getInstance().sqlConnection.cmdFetchData(
                "SELECT * from PreBorrow INNER JOIN RegisterItem on PreBorrow.RiNo = RegisterItem.RiNo INNER JOIN Shelf on Shelf.shNo = RegisterItem.shNo WHERE QRCodeNo = '"+code+"';",
                "RiNo",
                "graphId");
        int n = ret.size();
        int id = 0;
        for (HashMap<String,Object> tmp : ret){
            int RiNo = (int)tmp.get("RiNo");
            int graphId = (int)tmp.get("graphId");
            idToNodeID.put(id, graphId);
            idToRiNo.put(id, RiNo);
            id++;
        }
        return n;
    }



    public void solve(int code){


        //
        map path_map = temp.graph; // 這邊定義 哪邊取得現有地圖
        //



        // -- init --
        // get nodes
        synchronized(lock){
            if (mp.containsKey(code)) return;
            
            HashMap<Integer,Integer> idToNodeID = new HashMap<>();
            HashMap<Integer,Integer> idToRiNo = new HashMap<>();

            int n = SQLgetData(idToNodeID,idToRiNo,code); // 會從資料庫 抓取 必須經過的點 對於 path_map 無任何關係， path_map 單純是一種Graph資料結構
            // idToNodeID 抓到 圖上的 特定 節點 ID
            // idToRiNo   用於往後找到原先 ID

            if (n > 10){
                // 利用蟻群演算法

            }else{
                // 利用最佳解演算法
                backtracking.answer ans = new backtracking.answer(path_map.V+1,n,path_map);

                // preprocessing
                for (int i = 0 ; i < n ; i++){
                    ans.setMirror(i, idToNodeID.get(i));
                    //System.out.println(" set "+i + " - " + idToNodeID.get(i) );
                }


                // able solved under 1 sec
                backtracking.dfs(ans, 0,path_map.stPoint,0,n,-1); // 0 is for end
                AlgorithmResult algoRet = new AlgorithmResult("GREEN");// color 這邊可以額外去指定他應該要是啥顏色



                int last = path_map.stPoint;
                //System.out.println(ans.ans.size());
                for (Integer i : ans.ans){
                    int RiNo = idToRiNo.get(i);
                    int current = idToNodeID.get(i);
                    algoRet.addPath(last, current, RiNo, ans.getPath(last, current));
                    //System.out.println("????");
                    last = current;
                }

                algoRet.addPath(last, path_map.edPoint, -1, ans.getPath(last, path_map.edPoint)); // back to start
                mp.put(code,algoRet);
            }

            
        }
    }
}
