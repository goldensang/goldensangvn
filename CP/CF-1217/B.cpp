    #include <bits/stdc++.h>
     
    using namespace std;
    #define ll long long
    typedef pair<ll, ll> ii;
     
    ll t, n, x, h, d;
     
    int main()
    {
        cin >> t;
        while(t--){
            cin >> n >> x;
            ll a = 0, b = 0;
            for(int i = 0;i < n;i++){
                cin >> d >> h;
                a = max(a, d - h);
                b = max(b, d);
            }
            x -= b;
            if(x <= 0) cout << "1\n";
            else if(a <= 0) cout << "-1\n";
            else {
                ll res = floor(x / a * 1.0) + 2 - (x % a == 0);
                cout << res << "\n";
            }
     
        }
    }
