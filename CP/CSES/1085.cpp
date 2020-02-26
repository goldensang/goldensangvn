#include <bits/stdc++.h>
 
using namespace std;
#define ll long long
 
ll n , k , a[200005];
bool check(ll x){
    ll cur = 0 , cnt = 0 , pre = 0;
    for(int i = 1;i <= n + 1;i++){
        if(i == n + 1 && cur > 0){
            cnt++;
            continue;
        }
        cur += a[i];
        if(cur > x) cur = a[i], cnt++;
        else if(cur == x) cur = 0 , cnt++;
    }
    return (cnt <= k);
}
int main()
{
    cin >> n >> k;
    ll sum = 0;
    for(int i = 1;i <= n;i++)
        cin >> a[i];
    ll l = *max_element(a + 1, a + n + 1), r = 1e18, res = 1e18;
    while(l <= r){
        ll mid = (l + r) / 2;
        if(check(mid))
            r = mid - 1 , res = min(res, mid);
        else l = mid + 1;
    }
    cout << res;
}
