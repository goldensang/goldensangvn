#include <bits/stdc++.h>
 
using namespace std;
 
#define ll long long
 
ll t, l[200005];
string s;
 
int main()
{
    cin >> t;
    while(t--){
        ll res = 0;
        cin >> s;
        l[0] = -1;
        ll prev = (-1)* (s[0] != '1');
        for(int i = 1;i < s.size();i++)
            if(s[i] == '1') l[i] = prev, prev = i; else l[i] = prev;
        for(int i = 0;i < s.size();i++){
            ll sum = 0, cnt = 0, j = i;
            while(j >= 0){
                sum += (1LL << cnt) * (s[j] == '1');
                if((sum >= i - j + 1 && sum <= i - l[j]))
                    res++;
                cnt = (i - l[j]);
                j = l[j];
                if(cnt > 60) break;
            }
 
        }
        cout << res << '\n';
    }
    return 0;
}
