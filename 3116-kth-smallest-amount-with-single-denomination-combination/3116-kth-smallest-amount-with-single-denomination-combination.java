class Solution {
    public long findKthSmallest(int[] coins, int k) {
        int n = coins.length;
        long minCoin = Integer.MAX_VALUE;
        for (int c : coins) minCoin = Math.min(minCoin, c);
        
        long low = 1, high = minCoin * (long) k;
        
        while (low < high) {
            long mid = low + (high - low) / 2;
            if (count(coins, mid) >= k) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }
        
        return low;
    }
    
    // Count how many numbers in [1, x] are divisible by at least one coin
    private long count(int[] coins, long x) {
        int n = coins.length;
        long total = 0;
        
        // iterate over all non-empty subsets
        for (int mask = 1; mask < (1 << n); mask++) {
            long lcm = 1;
            int bits = Integer.bitCount(mask);
            boolean overflow = false;
            
            for (int i = 0; i < n; i++) {
                if ((mask & (1 << i)) != 0) {
                    lcm = lcmCapped(lcm, coins[i], x);
                    if (lcm > x) {
                        overflow = true;
                        break;
                    }
                }
            }
            
            if (!overflow) {
                long term = x / lcm;
                if (bits % 2 == 1) {
                    total += term;
                } else {
                    total -= term;
                }
            }
        }
        
        return total;
    }
    
    private long lcmCapped(long a, long b, long cap) {
        long g = gcd(a, b);
        long l = a / g * b; // safe enough: a,b <= 25^? but a already capped by cap+1 checks
        if (l > cap) return cap + 1; // sentinel: exceeds what we care about
        return l;
    }
    
    private long gcd(long a, long b) {
        while (b != 0) {
            long t = b;
            b = a % b;
            a = t;
        }
        return a;
    }
}