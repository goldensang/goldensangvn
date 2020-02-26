#include <bits/stdc++.h>
 
using namespace std;
#define ll long long
 
ll n , t , a[200005];
ll calc(ll x){
    ll ans = 0;
    for(int i = 1;i <= n;i++) ans += x / a[i];
    return ans;
}
int main()
{
    cin >> n >> t;
    for(int i = 1;i <= n;i++)
        cin >> a[i];
 
    ll l = 0 , r = 1e13 , res = 1e18;
    while(l <= r){
        ll mid = (l + r) / 2;
        if(calc(mid) >= t) r = mid - 1, res = min(res , mid);
        else l = mid + 1;
    }
    cout << res;
 
}
