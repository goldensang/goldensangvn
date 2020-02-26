#include <bits/stdc++.h>
 
using namespace std;
#define ll long long
 
map<ll , ll> m;
ll n , a[200005] , k , res;
int main()
{
    m[0] = 1;
    cin >> n >> k;
    for(int i = 1;i <= n;i++){
        cin >> a[i];
        a[i] += a[i - 1];
        res += m[a[i] - k];
        m[a[i]]++;
    }
    cout << res;
}
