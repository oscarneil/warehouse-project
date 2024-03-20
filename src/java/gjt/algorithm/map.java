package gjt.algorithm;

/**
 *
 * 定義地圖
 * Approximate TSP 要取用地圖時 都像此取用
 *
 */

public class map {
    // 維護 dp 表 和相連圖
    // 給 基本驗算法運算
    public double[][] dp;
    public int[][] pi;
    public int V;
    public int stPoint;
    public int edPoint;
    private boolean updated = false;
    public void addEdge(int u, int v, double weight){
        dp[u][v] = weight;
        dp[v][u] = weight;
        pi[u][v] = u;
        pi[v][u] = v;
        updated = false;
    }
    public map(int V,int stPoint,int edPoint){ // 對一個圖 Graph 的定義
        this.V = V;
        this.stPoint = stPoint;
        this.edPoint = edPoint;
        dp = new double[V][V];
        pi = new int[V][V];
        updated = false;
    }
    public map(int V){
        this.V = V;
        dp = new double[V][V];
        pi = new int[V][V];
        updated = false;
    }
    public void setStartPoint(int stPoint){
        this.stPoint = stPoint;
    }

    public void setEndPoint(int edPoint){
        this.edPoint = edPoint;
    }


    public void floyd(){
        for (int k = 0; k < V; k++) {
            for (int i = 0; i < V; i++) {
                for (int j = 0; j < V; j++) {
                    if (dp[i][k] != Double.MAX_VALUE && dp[k][j] != Double.MAX_VALUE &&
                            dp[i][k] + dp[k][j] < dp[i][j]) {
                        dp[i][j] = dp[i][k] + dp[k][j];
                        pi[i][j] = pi[k][j];
                    }
                }
            }
        }
        updated = true;
    }

    public void check(){
        if (!updated) assert false : "並未更新 需使用 map.floyd()";
    }

    public double dist(int from,int to){
        return dp[from][to];
    }




}