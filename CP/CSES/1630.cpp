#include <bits/stdc++.h>
 
using namespace std;
#define ll long long
typedef pair<ll , ll> ii;
ll n;
vector<ii> a;
int main()
{
    cin >> n; a.resize(n);
    for(int i = 0;i < n;i++)
        cin >> a[i].first >> a[i].second;
    sort(a.begin() , a.end());
    ll res = 0 , sum = 0;
    for(int i = 0;i < n;i++)
        sum += a[i].first , res += (a[i].second - sum);
    cout << res;
    return 0;
}
