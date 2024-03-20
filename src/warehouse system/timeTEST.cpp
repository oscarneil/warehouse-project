#include <bits/stdc++.h>
#include <windows.h>
#include <stdlib.h>
#include <functional>

class CTimer{
	public:
		static bool initial;
		static LARGE_INTEGER startTime, endTime, fre;
		static double time;//CTimer::time get time
		static void init(){
			if (initial) return;
			QueryPerformanceFrequency(&CTimer::fre);
			initial = true;
		}
		static void start_timer() {
			if (!initial) return;
			QueryPerformanceCounter(&startTime);
		}
		static void stop_timer() {
			if (!initial) return;
			QueryPerformanceCounter(&endTime);
			time = ((double)endTime.QuadPart - (double)startTime.QuadPart) /fre.QuadPart;
		}
		static std::string getFormat() {	
			if (!initial) return " Please do init() first.";
			char tmp[100];
			sprintf(tmp, "this action spends about %f second(s).",(float)time);// string format
			std::string timeformat(tmp);
			return timeformat;
		}
		static std::string getFormat(std::string s) {
			if (!initial) return " Please do init() first.";
			char tmp[100];
			sprintf(tmp, s.c_str(),(float)time);// string format
			std::string timeformat(tmp);
			return timeformat;
		}
		static void calc(std::function<void()> func){
			if (!initial) init();
			start_timer();
			func();
			stop_timer();
			std::cout << getFormat() << std::endl;
		}
		static double calc(std::function<void()> func,std::string format){
			if (!initial) init();
			start_timer();
			func();
			stop_timer();
			std::cout << getFormat(format);
			return time;
		}
		static void calc_avg_time(std::function<void()> func,std::string format,int cnt){
			if (!initial) init();
			double ctime = 0;
			for (int i = 0 ; i < cnt ; i++){
				start_timer();
				func();
				stop_timer();
				ctime += time;
			}
			ctime /= cnt;
			
			char tmp[100];
			sprintf(tmp, format.c_str(),(float)ctime);// string format
			std::string timeformat(tmp);
			std::cout << timeformat;
		}
	
};
LARGE_INTEGER CTimer::startTime;
LARGE_INTEGER CTimer::endTime;
LARGE_INTEGER CTimer::fre;
double CTimer::time = 0;
bool CTimer::initial = false;

using namespace std;
#define V 18
#define L 30 
#define MP 10 
int dp[V][V];
int pi[V][V];
int stPoint;
int edPoint;
vector<bool> visited(V,false);
void addEdge(int u,int v,int w){
	dp[u][v] = w;
	dp[v][u] = w;
	pi[u][v] = u;
	pi[v][u] = v;
}

void floydWarshall(){
	for (int k = 0; k < V; k++) {
        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                if (dp[i][k] != INT_MAX && dp[k][j] != INT_MAX &&
                        dp[i][k] + dp[k][j] < dp[i][j]) {
                    dp[i][j] = dp[i][k] + dp[k][j];
                    pi[i][j] = pi[k][j];  // Update the predecessor of j on the path from i to j.
                }
            }
        }
    }
}

void init(int num){
    for (int i = 0; i < V; i++) {
    	for(int j = 0 ; j < V ; j++){
    		dp[i][j] = INT_MAX;
    		pi[i][j] = -1;
		}
        dp[i][i] = 0;  // Distance from a node to itself is 0.
    }
    
    addEdge(0, 1, 1);addEdge(0, 7, 1);addEdge(1, 0, 1);addEdge(1, 2, 1);addEdge(2, 1, 1);
    addEdge(2, 3, 1);addEdge(3, 2, 1);addEdge(3, 4, 1);addEdge(3, 8, 1);addEdge(4, 3, 1);
    addEdge(4, 5, 1);addEdge(5, 4, 1);addEdge(5, 6, 1);addEdge(6, 5, 1);addEdge(6, 9, 1);
    addEdge(7, 0, 1);addEdge(7, 10, 1);addEdge(8, 3, 1);addEdge(8, 13, 1);addEdge(9, 6, 1);
    addEdge(9, 16, 1);addEdge(10, 7, 1);addEdge(10, 11, 1);addEdge(11, 10, 1);addEdge(11, 12, 1);
    addEdge(12, 11, 1);addEdge(12, 13, 1);addEdge(13, 8, 1);addEdge(13, 12, 1);addEdge(13, 14, 1);
    addEdge(13, 17, 1);addEdge(14, 13, 1);addEdge(14, 15, 1);addEdge(15, 14, 1);addEdge(15, 16, 1);
    addEdge(16, 15, 1);addEdge(16, 9, 1);addEdge(17, 13, 1);
    
	
	/*for (int i = 0 ; i < V ; i++){
		int a = i;
		int b = rand() % (V);
		int l = rand() % (3);
		addEdge(a,b,l);
	} 
	for (int i = 0 ; i < num ; i++){
		// num edges
		int a = rand() % (V);
		int b = rand() % (V);
		int l = rand() % (3);
		addEdge(a,b,l);
	} */ 
    
    floydWarshall();
}
map<int,int> mp;
void dfs(int &minCost,int sum,int current,int depth,int n,int token){
	if (sum >= minCost) return;
	
	if (token != -1 && visited[token]) return;

	if (token != -1) visited[token] = true;
	if (depth >= n){
		if (dp[current][edPoint] + sum < minCost){
			minCost = dp[current][edPoint] + sum;
			cout << "update" << endl;
		}
		return;
	}
	priority_queue<pair<int,pair<int,int>>,
		vector<pair<int,pair<int,int>>> , 
		less<pair<int,pair<int,int>>>> pq; 
	for (int i = 0 ; i < n ; i++){
		int next = mp[i];
		if (visited[i]) continue;
		if (sum+dp[current][next] > minCost) continue;
		//pq.push({sum+dp[current][next],{next,i}});
		dfs(minCost,sum+dp[current][next],next,depth+1,n,i);
	}
	/*
	while(pq.size()){
		pair<int,pair<int,int>> ret = pq.top();pq.pop();
		
		dfs(minCost,ret.first,ret.second.first,depth+1,n,ret.second.second);
	}*/
	if (token != -1) visited[token] = false;
}

int main(){
	srand( time(NULL) );
	cout << " total vertex " << V << " edges " << V + L << " must pass " << MP <<  endl;

	CTimer::calc([&](){
		init(L);	                               
	},"FLOYD INIT spents %f second(s).\n");
	stPoint = edPoint = 17; //rand() % (V);
	/*vector<int> ls;
	for(int i = 0 ; i < V ; i++){
		ls.push_back(i);
	}*/
	vector<int> mustPass({1,5,15});
	/*
	for (int i = 0 ; i < MP ; i++){
		int pos = rand() % ls.size();
		int num = ls[pos];
		ls.erase(ls.begin()+pos,ls.begin()+pos+1);
		//cout << ls.size() << endl;
		mustPass.push_back(num);
	}
	*/
	
	int tot = mustPass.size();
	for (int i = 0 ; i < mustPass.size();i++){
		mp[i] = mustPass[i];
	}
	
	
	CTimer::calc([&](){
		int ans = INT_MAX;
		dfs(ans,0,stPoint,0,tot,-1);      
		cout << ans << endl;                     
	},"BACK-TRACKING spents %f second(s).\n");
} 
