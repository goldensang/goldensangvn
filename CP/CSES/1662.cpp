#include <bits/stdc++.h>
 
using namespace std;
#define ll long long
 
ll n , a[200005], l [200005];
ll c2(ll x){
    return (x * (x - 1)) / 2;
}
int main()
{
    cin >> n;
    for(int i = 1;i <= n;i++) {
        cin >> a[i];
        if(i) (a[i] = a[i] + a[i - 1] + n * n) % n;
    }
    for(int i = 1;i <= n;i++)
        l[(a[i] + n * n)% n]++;
    ll res = 0;
    l[0]++;
    for(int i = 0;i < n;i++)
        res += c2(l[i]);
    cout << res;
    return 0;
}
