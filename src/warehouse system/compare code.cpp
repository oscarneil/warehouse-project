#include <bits/stdc++.h>
#include <windows.h>
#include <stdlib.h>
#include <functional>

#include <iostream>
#include <fstream>
#include <stdlib.h>
#include <time.h>
#include <stdio.h>
#include <vector>
#include <algorithm>
#include <windows.h>
#include <stdlib.h>
#include <functional>

#define m 10				//ant count
#define V 100			// total vertex
#define ED 10 			// line (edge)
#define MP 14 			// must pass
//define n 10				//must visit(pass)
const int NC_max = 100;		//??????
const double Alpha = 1;		//????????????
const double Beta = 5;		//??????????????
const double Rho = 0.1;		//???????
const double Q = 100;		//?????????


double D[MP][MP];			//??????????
double Eta[MP][MP];		//???????,?D??????
double DeltaTau[MP][MP];	//???????????
double Tau[MP][MP];		//??????????
int Tabu[m][MP];			//???,???????

double L_best[NC_max];		//??????????????
double L_ave[NC_max];		//??????????????
int R_best[NC_max][MP];		//???????????


using namespace std;

int dp[V][V];
int pi[V][V];
int stPoint;
int edPoint;
vector<bool> visited(V,false);


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
    
    /*addEdge(0, 1, 1);addEdge(0, 7, 1);addEdge(1, 0, 1);addEdge(1, 2, 1);addEdge(2, 1, 1);
    addEdge(2, 3, 1);addEdge(3, 2, 1);addEdge(3, 4, 1);addEdge(3, 8, 1);addEdge(4, 3, 1);
    addEdge(4, 5, 1);addEdge(5, 4, 1);addEdge(5, 6, 1);addEdge(6, 5, 1);addEdge(6, 9, 1);
    addEdge(7, 0, 1);addEdge(7, 10, 1);addEdge(8, 3, 1);addEdge(8, 13, 1);addEdge(9, 6, 1);
    addEdge(9, 16, 1);addEdge(10, 7, 1);addEdge(10, 11, 1);addEdge(11, 10, 1);addEdge(11, 12, 1);
    addEdge(12, 11, 1);addEdge(12, 13, 1);addEdge(13, 8, 1);addEdge(13, 12, 1);addEdge(13, 14, 1);
    addEdge(13, 17, 1);addEdge(14, 13, 1);addEdge(14, 15, 1);addEdge(15, 14, 1);addEdge(15, 16, 1);
    addEdge(16, 15, 1);addEdge(16, 9, 1);addEdge(17, 13, 1);*/
    
	
	for (int i = 0 ; i < V ; i++){
		int a = i;
		int b = rand() % (V);
		int l = rand() % (1000)+50;
		addEdge(a,b,l);
	} 
	for (int i = 0 ; i < num ; i++){
		// num edges
		int a = rand() % (V);
		int b = rand() % (V);
		int l = rand() % (1000)+50;
		addEdge(a,b,l);
	}

    floydWarshall();
}
vector<int> bestAns;
map<int,int> mp;
void dfs(int &minCost,int sum,int current,int depth,int n,int token,vector<int> &visited_list){
	if (sum > minCost) {
		//cout <<  sum << " over " << minCost << endl;
		return;
	}
	if (depth == n){
		if (dp[current][edPoint] + sum < minCost){
			minCost = dp[current][edPoint] + sum;
			//cout << "update" << minCost << endl;
			bestAns = vector<int>(visited_list);
		}
		
		//return;
	}
	if (visited[current]) {
		//cout << current << " killed " << endl;
		// end 
		
		
		
		return;
	}
	visited[current] = true;
	//if (token != -1 && visited[current]) return;

	//if (token != -1) visited[current] = true;
	
	//priority_queue<pair<int,pair<int,int>>,
	//	vector<pair<int,pair<int,int>>> , 
	//	less<pair<int,pair<int,int>>>> pq; 
	for (int i = 0 ; i < n ; i++){
		int next = mp[i];
		if (visited[next]) continue;
		//if (sum+dp[current][next] > minCost) continue;
		//pq.push({sum+dp[current][next],{next,i}});
		visited_list.push_back(next); 
		dfs(minCost,sum+dp[current][next],next,depth+1,n,i,visited_list);

		visited_list.pop_back();
		//if (visited_list.size() == 1) cout << "W" << sum << "|" << next << endl;
	}
	/*
	while(pq.size()){
		pair<int,pair<int,int>> ret = pq.top();pq.pop();
		
		dfs(minCost,ret.first,ret.second.first,depth+1,n,ret.second.second);
	}*/
	visited[current] = false;
	//if (token != -1) visited[token] = false;
}




/**



**/







void ValueInit(vector<int> & mustvisit)		//???????
{
	for (int i = 0; i < MP; i++)			//??? D[n][n]
	{
		for (int j = 0; j < MP; j++)
		{
			int ri = mustvisit[i];
			int rj = mustvisit[j];
			if (i != j){
				D[i][j] = dp[ri][rj] / 1.0;
				//cout << dp[ri][rj] << " " << ri << " " << rj << endl;
			}	
				//pow(pow((C[i][0] - C[j][0]), 2) + pow((C[i][1] - C[j][1]), 2), 0.5);
			else
				D[i][j] = INT_MAX;
		}
	}

	for (int i = 0; i < MP; i++)			//??? Eta[n][n]
		for (int j = 0; j < MP; j++)
			Eta[i][j] = 1.0 / D[i][j];

	for (int i = 0; i < MP; i++)			//??? DeltaEta[n][n]
		for (int j = 0; j < MP; j++)
			DeltaTau[i][j] = 0;

	for (int i = 0; i < MP; i++)			//??? Tau[n][n]
		for (int j = 0; j < MP; j++)
			Tau[i][j] = 1.0;

	for (int i = 0; i < m; i++)			//??? Tabu[m][n]
		for (int j = 0; j < MP; j++)
			Tabu[i][j] = 0;
}

void ValueDisplayTabu(int (*p)[MP])	//???,???????, ????
{
	for (int i = 0; i < m; i++)
	{
		for (int j = 0; j < MP; j++)
		{
			cout << *(*(p + i) + j) << ' ';
		}
		cout << endl;
	}
}

void ValueDisplayTau(double(*p)[MP])		//??????,????
{
	for (int i = 0; i < MP; i++)
	{
		for (int j = 0; j < MP; j++)
		{
			cout << *(*(p + i) + j) << ' ';
		}
		cout << endl;
	}
}

double rnd(double lower, double uper)	//??lower?uper?????double?????
{
	return  (rand() / (double)RAND_MAX) * (uper - lower) + lower;
}

void solve(vector<int> &mustvisit){
	//???:????????
	ValueInit(mustvisit);
	int n = MP;
	int NC = 0;
	while(NC < NC_max)
	{
		//???:?m???????n????
		vector<int> temp;
		for (int i = 0; i < ceil((double)m / (double)MP); i++)
		{
			for (int j = 0; j < MP; j++)
				temp.push_back(j);
		}

		random_shuffle(temp.begin(), temp.end());	//??temp????????

		for (int i = 0; i < m; i++)
		{
			Tabu[i][0] = temp[i];
		}

		//???:m??????????n???????,???????
		for (int j = 1; j < MP; j++)
		{
			for (int i = 0; i < m; i++)
			{
				vector<int> visited;	//?i??????????
				vector<int> J;			//?i?????????
				vector<double> P;		//?i????????????

				double Psum = 0.0;		//????
				double rate = 0.0;		//???
				double choose = 0.0;	//????????
				int to_visit;			//????????

				for (int k = 0; k < j; k++)
					visited.push_back(Tabu[i][k]);	//visited???

				for (int k = 0; k < MP; k++)
				{
					if (find(visited.begin(), visited.end(), k) == visited.end())	//?visited?????t
					{
						J.push_back(k);				//J???
						P.push_back(0.0);			//P???
					}
				}
				
				for (int k = 0; k < P.size(); k++)	//???????????
				{
					P[k] = pow(Tau[visited.back()][J[k]], Alpha) * pow(Eta[visited.back()][J[k]], Beta);
					Psum += P[k];
				}
				
				rate = rnd(0.0, Psum);				//???????,??????????
				for (int k = 0; k < P.size(); k++)
				{
					choose += P[k];
					if (choose > rate)
					{
						to_visit = J[k];
						break;
					}
				}

				Tabu[i][j] = to_visit;				//?????
			}
		}

		//???:???????????????
		double L[m];	//????????????,????
		for (int i = 0; i < m; i++)
		{
			//L[i] = 0.0;
			L[i] = dp[stPoint][mustvisit[(int)Tabu[i][0]]];
			//cout << L[i] << endl;
		}
		for (int i = 0; i < m; i++)
		{
			for (int j = 0; j < MP - 1; j++)
			{
				L[i] += D[Tabu[i][j]][Tabu[i][j + 1]];
			}
	//		L[i] += D[Tabu[i][0]][Tabu[i][n - 1]];
			
			L[i] += dp[mustvisit[   Tabu[i][n-1]   ]][stPoint];
		}
		
		
		double min_value = L[0];	//?????????????????????
		double sum_value = L[0];	//????????????????????
		int min_index = 0;			//??????????????????
		for (int i = 1; i < m; i++)
		{
			sum_value += L[i];
			if (L[i] < min_value)
			{
				min_value = L[i];
				min_index = i;
			}
		}

		L_best[NC] = min_value;						//??????????
		L_ave[NC] = sum_value / m;					//??????????

		for (int i = 0; i < MP; i++)
		{
			R_best[NC][i] = Tabu[min_index][i];		//???????????
		}
		
		//cout << NC << ": L_best is " << L_best[NC] << ' ' << "L_ave is " << L_ave[NC] << endl;	//????????

		NC++;	//????

		//???:?????
		for (int i = 0; i < m; i++)
		{
			for (int j = 0; j < MP - 1; j++)
			{
				DeltaTau[Tabu[i][j]][Tabu[i][j + 1]] += Q / L[i];	//????????????????
			}
			DeltaTau[Tabu[i][MP - 1]][Tabu[i][0]] += Q / L[i];
		}

		for (int i = 0; i < MP; i++)
		{
			for (int j = 0; j < MP; j++)
			{
				Tau[i][j] = (1 - Rho) * Tau[i][j] + DeltaTau[i][j];	//???????,???????
			}
		}

		for (int i = 0; i < m; i++)			//?????
			for (int j = 0; j < MP; j++)
				Tabu[i][j] = 0;
	}
	
	//???:??????
	double min_L = L_best[0];			//?????????
	int min_L_index = 0;				//????????????
	int Shortest_Route[MP];				//??????????
	for (int i = 0; i < NC; i++)
	{
		if (L_best[i] < min_L)
		{
			min_L = L_best[i];
			min_L_index = i;
		}
	}

	cout << "The length of the shortest route is " << min_L << endl;
	cout << "The number of iteration is " << min_L_index << endl;
	cout << "The Shortest route is: " << endl << "start" << endl;
	int deb_l = stPoint;
	int w = 0;
	cout << stPoint;
	for (int i = 0; i < MP; i++)		//??????????
	{
		
		
		Shortest_Route[i] = R_best[min_L_index][i];
		w += dp[deb_l][mustvisit[Shortest_Route[i]]];
		cout << " -> " << mustvisit[Shortest_Route[i]];
		deb_l = mustvisit[Shortest_Route[i]];
	}
	w += dp[deb_l][stPoint];
	cout << " -> "  << stPoint << endl;
	cout << w << endl;
}


/**

**/



int main(){
	srand( time(NULL) );
	cout << " total vertex " << V << " edges " << V + ED << " must pass " << MP <<  endl;
	stPoint = edPoint = rand() % (V);
	cout << "start at " << stPoint << endl;
	CTimer::calc([&](){
		init(ED);	                               
	},"FLOYD INIT spents %f second(s).\n");
	
	vector<int> ls;
	for(int i = 0 ; i < V ; i++){
		ls.push_back(i);
	}
	vector<int> mustPass;//({1,5,15});
	
	for (int i = 0 ; i < MP ; i++){
		int pos = rand() % ls.size();
		while(ls[pos] == stPoint){
			pos = rand() % ls.size();
		}
		int num = ls[pos];
		ls.erase(ls.begin()+pos,ls.begin()+pos+1);
		//cout << ls.size() << endl;
		mustPass.push_back(num);
	}
	
	
	int tot = mustPass.size();
	for (int i = 0 ; i < mustPass.size();i++){
		mp[i] = mustPass[i];
	}
	CTimer::calc([&](){
		solve(mustPass);                    
	},"ACO spents %f second(s).\n");
	
	CTimer::calc([&](){
		int ans = INT_MAX;
		visited.clear();
		bestAns.clear();
		vector<int> visited_list;
		dfs(ans,0,stPoint,0,tot,-1,visited_list);      
		
		int deb_l;
		deb_l = stPoint;
		int w = 0;
		cout << stPoint;
		for (int i = bestAns.size() - 1 ; i >= 0;i--){
			w += dp[deb_l][bestAns[i]];
			deb_l = bestAns[i];
			cout << " -> " << bestAns[i];
		}
		w += dp[deb_l][stPoint];
		cout << " -> "<< stPoint << endl;
		cout << w << endl;
		cout << ans << endl;            
	},"BACK-TRACKING spents %f second(s).\n");
	
	
} 
