package gjt.algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class backtracking {





    public static class answer{
        public double minCost;
        public ArrayList<Integer> ans;
        public ArrayList<Integer> tmp;
        public ArrayList<Boolean> visited;
        public HashMap<Integer,Integer> mirror = new HashMap<>();
        public int n;
        public int sz;
        public map mp;
        public answer(int sz,int n,map mp){
            this.minCost = Double.MAX_VALUE;
            ans = new ArrayList<>();
            tmp = new ArrayList<>();
            visited = new ArrayList<>();
            for (int i = 0 ; i < sz+1 ; i++){
                visited.add(false);
                ans.add(0);
            }
            this.mp = mp;
            this.n = n;
        }

        // 0 1 2 to real node id
        public void setMirror(int a,int b){
            this.mirror.put(a,b);
        }
        public int getValue(int i){
            return this.mirror.get(i);
        }


        //
        public boolean visited(int i){
            return this.visited.get(i);
        }
        public void setVisited(int i){
            if (i == -1) return;
            this.visited.set(i, true);
        }
        public void setUnVisited(int i){
            if (i == -1) return;
            this.visited.set(i ,false);
        }
        public void addToTmp(int i){
            this.tmp.add(i);
        }
        public void removeFromTmp(){
            this.tmp.remove(this.tmp.size() - 1);
        }
        public void tmpToAns(double totsum){
            this.minCost = totsum;
            this.ans.clear();
            for(int i = 0 ; i < this.tmp.size() ; i++){
                this.ans.add(this.tmp.get(i));
            }


        }
        @Override
        public String toString(){
            //System.out.println("min cost " + minCost);
            int last = mp.stPoint;

            for(int i = 0 ; i < n;i++){
                int current = this.mirror.get(ans.get(i));
                System.out.print("from "+last + " to "+current +" | " );
                int source = last;
                int target = current;
                System.out.print(source);
                while(source != target){
                    source = mp.pi[target][source];
                    System.out.print(" > " + source);
                }
                System.out.println();
                last = current;
            }
            return "" ;
        }
        public List<Integer> getPath(int source, int target) {
            if (mp.pi[target][source] == -1) {
                // There is no path from source to target
                return null;
            }
            List<Integer> path = new ArrayList<>();
            path.add(source);
            while(source != target){
                source = mp.pi[target][source];
                path.add(source);
            }
            return path;
        }
    }

    public static void dfs(answer bestAns,double sum,int current,int depth,int n,int token){
        if (sum > bestAns.minCost ){
            return;
        }
        if (token != -1 && bestAns.visited(token)) return;
        bestAns.setVisited(token);
        //System.out.println("D:"+depth+" current at " + current + " sum "+sum);

        if (depth >= n){
            // add end
            //System.out.println("-----");
            //System.out.println(current + " to " + edPoint + " cost " + dp[current][edPoint]);
            //System.out.println("sum is " + (dp[current][edPoint] + sum) + " BEST " + bestAns.minCost);
            // check
            if (bestAns.mp.dp[current][bestAns.mp.edPoint] + sum < bestAns.minCost){
                // best ans
                //System.out.println("if-----");
                bestAns.tmpToAns(bestAns.mp.dp[current][bestAns.mp.edPoint] + sum);

            }
            //System.out.println("-----");
            bestAns.setUnVisited(token);
            return;
        }

        for (int i = 0 ; i < n ; i++){
            // from current to dest
            int next = bestAns.getValue(i);
            //if(bestAns.visited(i)) continue;
            if (bestAns.visited(i)) {
                //System.out.println(" - " + i + " visited");
                continue;
            }

            bestAns.addToTmp(i);
            //System.out.println(current + " to " + next + " cost " + dp[current][next]);
            dfs(bestAns,sum+bestAns.mp.dp[current][next],next,depth+1,n,i);
            bestAns.removeFromTmp();
        }

        bestAns.setUnVisited(token);


    }
}
