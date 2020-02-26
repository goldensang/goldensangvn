#include <bits/stdc++.h>
 
using namespace std;
 
int n , m;
long long s;
int main()
{
    cin >> n;
    for(int i = 1 , f;i <= n;i++){
        cin >> f;
        m = max(f , m);
        s += f;
    }
    cout << ((2 * m > s) ? (2 * m) : s);
    return 0;
}
