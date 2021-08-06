package com.kelly.effect.leetcode.stock;

/**
 * Copyright (c) 2014-2021 Zuoyebang, All rights reserved.
 *
 * @author zongkaili | zongkaili@zuoyebang.com
 * @version 1.0.0 | 2021/8/4 | zongkaili 初始版本
 * @date 2021/8/4 9:50 上午
 * @description 122. 买卖股票的最佳时机 II，多次交易
 * 给定一个数组 prices ，其中 prices[i] 是一支给定股票第 i 天的价格。
 * 设计一个算法来计算你所能获取的最大利润。你可以尽可能地完成更多的交易（多次买卖一支股票）。
 * 注意：你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。
 * <p>
 * 示例 1:
 * 输入: prices = [7,1,5,3,6,4]
 * 输出: 7
 * 解释: 在第 2 天（股票价格 = 1）的时候买入，在第 3 天（股票价格 = 5）的时候卖出, 这笔交易所能获得利润 = 5-1 = 4 。
 * 随后，在第 4 天（股票价格 = 3）的时候买入，在第 5 天（股票价格 = 6）的时候卖出, 这笔交易所能获得利润 = 6-3 = 3 。
 * <p>
 * 示例 2:
 * 输入: prices = [1,2,3,4,5]
 * 输出: 4
 * 解释: 在第 1 天（股票价格 = 1）的时候买入，在第 5 天 （股票价格 = 5）的时候卖出, 这笔交易所能获得利润 = 5-1 = 4 。
 * 注意你不能在第 1 天和第 2 天接连购买股票，之后再将它们卖出。因为这样属于同时参与了多笔交易，你必须在再次购买前出售掉之前的股票。
 * <p>
 * 示例3:
 * 输入: prices = [7,6,4,3,1]
 * 输出: 0
 * 解释: 在这种情况下, 没有交易完成, 所以最大利润为 0。
 */
public class BestTimeToBuyAndSellStocks2 {
    /**
     * 买卖股票的最佳时机 II，即收益最大
     * 可交易多次
     * 解法1：动态规划，穷举
     *
     * @param prices 每天的股票价格
     * @return 最大收益
     */
    public static int maxProfit(int[] prices) {
        int n = prices.length;
        int[][] dp = new int[n][2];
        /*
         dp[i][0] 表示第 i 天交易完后手里没有股票的最大利润
         dp[i][1] 表示第 i 天交易完后手里持有一支股票的最大利润（i 从 0 开始）
         */
        //第一天没有持股票的收益用0表示
        dp[0][0] = 0;
        //第一天持有股票即买了一只股票，则收益为负的第一天的股价
        dp[0][1] = -prices[0];
        //每天都有持有股票和不持有股票两种情况，且都与前一天的收益有关
        for (int i = 1; i < n; i++) {
            /*
              第i天不持有股票：
                 a. 第 i-1 天就不持有股票，收益 = dp[i-1][0]
                 b. 第 i-1 天持有股票，第i天卖出，收益 = dp[i-1][1] + prices[i]
             */
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] + prices[i]);
            /*
              第i天持有股票：
                 a. 第 i-1 天就持有股票，收益 = dp[i-1][1]
                 b. 第 i-1 天不持有股票，第i天买入，收益 = dp[i-1][0] - prices[i]
             */
            dp[i][1] = Math.max(dp[i - 1][1], dp[i - 1][0] - prices[i]);
        }
        //最后一天不持有股票的收益肯定大于持有股票的收益，所以直接返回最后一天不持有股票的收益
        return dp[n - 1][0];
    }

    /**
     * 买卖股票的最佳时机 II，即收益最大
     * 可交易多次
     * 解法2：贪心
     *
     * @param prices 每天的股票价格
     * @return 最大收益
     */
    public static int maxProfit2(int[] prices) {
        int ans = 0;
        for (int i = 1; i < prices.length; i++) {
            ans += Math.max(0, prices[i] - prices[i-1]);
        }
        return ans;
    }

    public static void main(String[] args) {
        System.out.println("买卖股票的最大收益II：" + maxProfit2(new int[]{7, 1, 5, 3, 6, 4}));
    }

}
