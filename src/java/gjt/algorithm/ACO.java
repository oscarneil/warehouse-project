package gjt.algorithm;

import java.util.*;

public class ACO {
    HashMap<Integer,Integer> idToNodeID = new HashMap<>();
    int n;
    static final int m = 100;                 // Number of ants
    static final int NC_max = 1000;            // Maximum number of iterations
    static final double Alpha = 1;            // Importance of pheromone
    static final double Beta = 5;             // Importance of heuristic factor
    static final double Rho = 0.1;            // Pheromone evaporation coefficient
    static final double Q = 100;              // Pheromone intensity coefficient

    // City coordinates
    double[][] D;          // Adjacency matrix
    double[][] Eta;        // Heuristic factor (inverse of distance)
    double[][] DeltaTau;   // Change in heuristic factor
    double[][] Tau;        // Pheromone concentration on the paths
    int[][] Tabu;             // Tabu list to store visited paths

    double[] L_best;     // Store the shortest length for each iteration
    double[] L_ave;      // Store the average length for each iteration
    int[][] R_best;      // Store the best route for each iteration

    // Initialize variables
    void valueInit(map mp) { // n = must pass (must visit)
        D = new double[n][n];          // Adjacency matrix
        Eta = new double[n][n];        // Heuristic factor (inverse of distance)
        DeltaTau = new double[n][n];   // Change in heuristic factor
        Tau = new double[n][n];        // Pheromone concentration on the paths
        Tabu = new int[m][n];             // Tabu list to store visited paths

        L_best = new double[NC_max];     // Store the shortest length for each iteration
        L_ave = new double[NC_max];      // Store the average length for each iteration
        R_best = new int[NC_max][n];      // Store the best route for each iteration

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int ri = idToNodeID.get(i);
                int rj = idToNodeID.get(j);
                if (i != j)
                    D[i][j] = mp.dp[ri][rj];
                else
                    D[i][j] = Double.MIN_VALUE;
            }
        }

        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                Eta[i][j] = 1.0 / D[i][j];

        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                DeltaTau[i][j] = 0;

        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                Tau[i][j] = 1.0;

        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                Tabu[i][j] = 0;
    }

    // Display tabu list
    void valueDisplayTabu(int[][] tabu) {
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(tabu[i][j] + " ");
            }
            System.out.println();
        }
    }

    // Display pheromone concentration
    void valueDisplayTau(double[][] tau) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(tau[i][j] + " ");
            }
            System.out.println();
        }
    }

    // Generate a random double between lower and upper
    double rnd(double lower, double upper) {
        return Math.random() * (upper - lower) + lower;
    }
    public int solve(map mp,HashMap<Integer,Integer> idToNodeID){
        this.idToNodeID = idToNodeID;
        this.n = idToNodeID.size();

        valueInit(mp);

        int NC = 0;
        while (NC < NC_max) {
            // Step 2: Place m ants randomly on n cities
            List<Integer> temp = new ArrayList<>();
            for (int i = 0; i < Math.ceil((double) m / (double) n); i++) {
                for (int j = 0; j < n; j++)
                    temp.add(j);
            }

            Collections.shuffle(temp);

            for (int i = 0; i < m; i++) {
                Tabu[i][0] = temp.get(i);
            }

            // Step 3: Ants choose the next city based on probability function
            for (int j = 1; j < n; j++) {
                for (int i = 0; i < m; i++) {
                    List<Integer> visited = new ArrayList<>();
                    List<Integer> J = new ArrayList<>();
                    List<Double> P = new ArrayList<>();

                    double Psum = 0.0;
                    double rate;
                    double choose = 0.0;
                    int toVisit = 0;

                    for (int k = 0; k < j; k++)
                        visited.add(Tabu[i][k]);

                    for (int k = 0; k < n; k++) {
                        if (!visited.contains(k)) {
                            J.add(k);
                            P.add(0.0);
                        }
                    }

                    for (int k = 0; k < P.size(); k++) {
                        P.set(k, Math.pow(Tau[visited.get(visited.size() - 1)][J.get(k)], Alpha)
                                * Math.pow(Eta[visited.get(visited.size() - 1)][J.get(k)], Beta));
                        Psum += P.get(k);
                    }

                    rate = rnd(0.0, Psum);
                    for (int k = 0; k < P.size(); k++) {
                        choose += P.get(k);
                        if (choose > rate) {
                            toVisit = J.get(k);
                            break;
                        }
                    }

                    Tabu[i][j] = toVisit;
                }
            }

            // Step 4: Record the route data for ants in this iteration
            double[] L = new double[m];
            for (int i = 0; i < m; i++) {
                // dp[stPoint][mustvisit[(int)Tabu[i][0]]];
                L[i] = mp.dp[mp.stPoint][idToNodeID.get(Tabu[i][0])];
            }
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n - 1; j++) {
                    L[i] += D[Tabu[i][j]][Tabu[i][j + 1]];
                }
                //L[i] += D[Tabu[i][0]][Tabu[i][n - 1]];
                L[i] += mp.dp[idToNodeID.get(Tabu[i][n-1])][mp.stPoint];
            }

            double minValue = L[0];
            double sumValue = L[0];
            int minIndex = 0;
            for (int i = 1; i < m; i++) {
                sumValue += L[i];
                if (L[i] < minValue) {
                    minValue = L[i];
                    minIndex = i;
                }
            }

            L_best[NC] = minValue;
            L_ave[NC] = sumValue / m;

            for (int i = 0; i < n; i++) {
                R_best[NC][i] = Tabu[minIndex][i];
            }

            //System.out.println(NC + ": L_best is " + L_best[NC] + ", L_ave is " + L_ave[NC]);

            NC++;

            // Step 5: Update pheromones
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n - 1; j++) {
                    DeltaTau[Tabu[i][j]][Tabu[i][j + 1]] += Q / L[i];
                }
                DeltaTau[Tabu[i][n - 1]][Tabu[i][0]] += Q / L[i];
            }

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    Tau[i][j] = (1 - Rho) * Tau[i][j] + DeltaTau[i][j];
                }
            }

            for (int i = 0; i < m; i++)
                for (int j = 0; j < n; j++)
                    Tabu[i][j] = 0;
        }

        // Step 6: Display the results
        double minL = L_best[0];
        int minLIndex = 0;
        int[] shortestRoute = new int[n];
        for (int i = 0; i < NC; i++) {
            if (L_best[i] < minL) {
                minL = L_best[i];
                minLIndex = i;
            }
        }

        //System.out.println("The length of the shortest route is " + minL);
        //System.out.println("The number of iterations is " + minLIndex);
        //System.out.print("The Shortest route is: \nstart");

        int deb_l = mp.stPoint;
        int w = 0;
        //System.out.println(mp.stPoint);
        for (int i = 0 ; i < n ; i++){
            shortestRoute[i] = R_best[minLIndex][i];
            //w += dp[deb_l][mustvisit[Shortest_Route[i]]];
            w += mp.dp[deb_l][idToNodeID.get(shortestRoute[i])];
            //System.out.print(" -> " + idToNodeID.get(shortestRoute[i]));
            deb_l = idToNodeID.get(shortestRoute[i]);
        }
        w += mp.dp[deb_l][mp.edPoint];
        //System.out.println(" -> "+mp.stPoint);
        //System.out.println(w);
        return w;
    }
}