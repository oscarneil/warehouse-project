#include <bits/stdc++.h>
using namespace std;
bool isLeap(int year){
	if (year % 4 == 0){
		if (year % 100 == 0 && year % 400 != 0) return false;
		if (year % 100 == 0 && year % 400 == 0) return true;
		if (year % 100 != 0) return true;
	}
	return false;
}
class DATE{
	public:
		int year;
		int month;
		int date;
		DATE(int year,int month,int date):year(year),month(month),date(date){}
	
		bool after229(){
			if (!isLeap(year)) return false;
			else {
			// check after 2/29
				if (month > 2) return true;
				if (month == 2 && date >= 29) return true;
				return false;
			}
		}
		bool before229(){
			if (!isLeap(year)) return false;
		
			if (month < 2) return true;
			if (month == 2 && date <= 29) return true;
			else return false;
		}
};

DATE getformat(string s){
	string a;
	int type = 0;
	int date = 0;
	int year = 0;
	for (int i = 0 ; i < s.size() ; i++){
	
		if (type == 0 && s[i] == ' '){
			type = 1;
			
		}else if (type == 1 && s[i] == ','){
			type = 2;
		}else if (type == 2 && s[i] == ' '){
			continue;
		}
	
		else if (type == 0){
			a.push_back(s[i]);
		}
		else if (type == 1){
			date = date * 10 + s[i] - '0';
		}	
		else if (type == 2){
			year = year * 10 + s[i] - '0';
		}	
		
	}
	map<string,int> tmp;
	tmp["January"] = 1;tmp["February"] = 2;tmp["March"] = 3;tmp["April"] = 4;tmp["May"] = 5;tmp["June"] = 6;
	tmp["July"] = 7;tmp["August"] = 8;tmp["September"] = 9;tmp["October"] = 10;tmp["November"] = 11;tmp["December"] = 12;
	//cout << year << "/" << tmp[a] << "/" << date << endl;
	
	return DATE(year,tmp[a],date);
}

int solve(DATE st,DATE ed){
	int cnt = 0;
	if (st.before229()) {
		cnt++;
	}
	if (ed.after229()) {
		cnt++;
	}
	if (st.before229() && ed.after229() && st.year == ed.year) cnt--;
	for (int y = st.year+1 ; y < ed.year;y++){
		if(isLeap(y)) cnt++;
	}
	return cnt;
}
int main(){
	int x = 40;
	
	int v = x & (-x);
	cout << v;
}
