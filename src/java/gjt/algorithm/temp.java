package gjt.algorithm;

import java.util.Arrays;

public class temp {
    public static map graph;
    static{

        // init map
        /*
        1221221   (00) (01) (02) (03) (04) (05) (06)
        1001001   (07) xxxx xxxx (10) xxxx xxxx (13)
        1221221   (14) (15) (16) (17) (18) (19) (20)
           1                     (21)
         */

        // `.` -> walkable
        // `#` -> shelf
        // ` ` -> not walkable
        // `E` -> entry/end point
        String[] map = {
                ".......",
                ".##.##.",
                ".......",
                "   .   ",
                "###.###",
                ".......",
                "   E   ",
        };
        int m = map.length;
        int n = map[0].length();
        int mxl = Math.max(m, n);
        int V = (mxl)*(mxl);

        graph = new map(V);


        for (int i = 0; i < V; i++) {
            Arrays.fill(graph.dp[i], Double.MAX_VALUE);
            Arrays.fill(graph.pi[i], -1);
            graph.dp[i][i] = 0;  // Distance from a node to itself is 0.
        }

        // decode map
        for (int i = 0 ; i < m ; i++){
            for(int j = 0 ; j < map[i].length() ; j++){
                int up_index = (i-1)*mxl+j;
                int left_index = i*mxl+j-1;
                int right_index = i*mxl+j+1;
                int down_index = (i+1)*mxl+j;
                int current_index = i*mxl+j;
                if (map[i].charAt(j) == '#' || map[i].charAt(j) == ' ') continue;
                if (map[i].charAt(j) == 'E'){
                    graph.stPoint = graph.edPoint = current_index;
                }
                if (i > 0 && map[i-1].charAt(j) == '.'){ // up
                    graph.addEdge(up_index, current_index, 1);
                }
                if (j > 0 && map[i].charAt(j-1) == '.'){ // left
                    graph.addEdge(left_index, current_index, 1);
                }
                if (i+1 < m && map[i+1].charAt(j) == '.'){ // down
                    graph.addEdge(down_index, current_index, 1);
                }
                if (j+1 < map[i].length() && map[i].charAt(j+1) == '.'){ // right
                    graph.addEdge(right_index, current_index, 1);
                }
            }
        }

        graph.floyd();
        System.out.println(" floyd finished ");
    }
}
