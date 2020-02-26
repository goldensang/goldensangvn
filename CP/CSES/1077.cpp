#include <bits/stdc++.h>
#include <ext/pb_ds/assoc_container.hpp>
 
using namespace std;
using namespace __gnu_pbds;
#define ll long long
typedef tree<ll, null_type, less_equal<ll>, rb_tree_tag, tree_order_statistics_node_update> indexed_set;
 
ll n , k , a[200005] , b[200005];
indexed_set s;
int main()
{
    cin >> n >> k;
    for(int i = 1;i <= n;i++) cin >> a[i];
 
    for(int i = 1;i <= k;i++)
        s.insert(a[i]);
    ll sum = 0, pos = 1;
    ll pre = *s.find_by_order((k + 1) / 2 - 1);
    for(int i = 1;i <= k;i++)
        sum += abs(pre - a[i]);
    cout << sum;
    for(int i = k + 1;i <= n;i++){
        sum -= abs(pre - a[pos]);
        s.erase(s.find_by_order(s.order_of_key(a[pos++])));
        s.insert(a[i]);
        ll x = *s.find_by_order((k + 1) / 2 - 1);
        sum += abs(a[i] - x);
        if(k % 2 == 0) sum -= x - pre;
        pre = x;
        cout << " " << sum ;
    }
 
    return 0;
}
