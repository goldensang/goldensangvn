#include <bits/stdc++.h>
 
using namespace std;
 
#define ll long long
 
ll n , l , r , a[200005] , it[800005];
void buildIT(ll i, ll l ,ll r){
    if(l == r){
        it[i] = a[l];
        return;
    }
    ll mid = (l + r) / 2;
    buildIT(i * 2 , l , mid);
    buildIT(i * 2 + 1 , mid + 1 , r);
    it[i] = min(it[i * 2] , it[i * 2 + 1]);
}
ll get(ll i, ll l , ll r , ll u , ll v){
    if(r < u || l > v) return 2e18;
    if(l >= u && r <= v) return it[i];
    ll mid = (l + r) / 2;
    return min(get(i * 2, l , mid , u , v) , get(i * 2 + 1 , mid + 1 , r , u , v));
}
int main()
{
    cin >> n >> l >> r;
    for(int i = 1;i <= n;i++){
        cin >> a[i];
        if(i) a[i] += a[i - 1];
    }
    buildIT(1, 0 , n);
    ll res = -2e18;
    for(int i = l;i <= n;i++){
        res = max(res, a[i] - get(1 , 0 , n , max(0LL , i - r) , i - l));
    }
    cout << res;
    return 0;
}
