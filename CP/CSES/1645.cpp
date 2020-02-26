#include <bits/stdc++.h>
 
using namespace std;
#define ll long long
 
ll n , a[200005];
stack<ll> s;
int main()
{
    cin >> n;
    for(int i = 1;i <= n;i++){
        cin >> a[i];
        while(s.size() > 0 && a[i] <= a[s.top()])
            s.pop();
        if(s.size() > 0) cout << s.top() << " " ;
        else cout << "0 ";
        s.push(i);
    }
    return 0;
}
