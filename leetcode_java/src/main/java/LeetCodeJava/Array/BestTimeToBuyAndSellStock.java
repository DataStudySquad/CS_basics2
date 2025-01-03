package LeetCodeJava.Array;

// https://leetcode.com/problems/best-time-to-buy-and-sell-stock/

public class BestTimeToBuyAndSellStock {

    // V0
    public int maxProfit(int[] prices) {

        if (prices.length == 1){
            return 0;
        }

        int profit = 0;
        int local_min = Integer.MAX_VALUE;
        int local_max = -1;

        for(int x : prices){
            //System.out.println("x = " + x + ", local_min = " + local_min + ", local_max = " + local_max + ", profit = " + profit);
            if (local_min == Integer.MAX_VALUE){
                local_min = x;
            }else if (local_min > x){
                local_min = x;
            }else if(x > local_min){
                local_max = x;
                profit = Math.max(profit, local_max - local_min);
                // already "sold", can't reuse local_max, so make it as initial value again
                local_max = -1;
            }
        }

        return profit;
    }

    // V0'
    public int maxProfit_0_1(int[] prices) {

        if (prices.length == 0){
            return 0;
        }

        int res = 0;
        int min = -1;
        int max = -1;

        for (int i : prices){
            int cur = i;
            //System.out.println("cur = " + cur);
            if (min == -1){
                min = cur;
                continue;
            }
            if (min > cur){
                min = cur;
                continue;
            }
            if (max == -1){
                max = cur;
            }
            if (cur > max){
                max = cur;
            }
            int tmp = max - min;
            //System.out.println("max = " + max + " min = " + min + " tmp = " + tmp);
            /** NOTE : need to reset max val after get "revenue", so we don't reuse previous max val */
            max = -1;
            res = Math.max(tmp, res);
        }

        return res;
    }

    // V0''
    public int maxProfit_0_2(int[] prices) {

        int minVal = (int) Math.pow(10, 4);
        int maxVal = 0;
        int maxProfit = 0;
        //HashSet<Integer> seen = new HashSet<Integer>();
        for (int i = 0; i < prices.length; i++){
            int cur = prices[i];
            //seen.add(cur);
            if (cur < minVal){
                minVal = cur;
            }else{
                maxVal = cur;
                maxProfit = Math.max( maxVal - minVal, maxProfit);
            }
        }
        return maxProfit > 0 ? maxProfit : 0;
    }

    // V1
    // https://leetcode.com/problems/best-time-to-buy-and-sell-stock/solutions/1735493/java-c-best-ever-explanation-could-possible/
    public int maxProfit_2(int[] prices) {
        int lsf = Integer.MAX_VALUE;
        int op = 0;
        int pist = 0;

        for(int i = 0; i < prices.length; i++){
            if(prices[i] < lsf){
                lsf = prices[i];
            }
            pist = prices[i] - lsf;
            if(op < pist){
                op = pist;
            }
        }
        return op;
    }

}
