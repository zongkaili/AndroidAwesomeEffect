package com.kelly.practice.lc.stock;

/**
 * Copyright (c) 2014-2021 Zuoyebang, All rights reserved.
 *
 * @author zongkaili | zongkaili@zuoyebang.com
 * @version 1.0.0 | 2021/8/6 | zongkaili 初始版本
 * @date 2021/8/6 2:13 下午
 * @description 188. 买卖股票的最佳时机 IV
 * 给定一个整数数组prices ，它的第 i 个元素prices[i] 是一支给定的股票在第 i 天的价格。
 * 设计一个算法来计算你所能获取的最大利润。你最多可以完成 k 笔交易。
 * 注意：你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。
 * <p>
 * 示例 1：
 * 输入：k = 2, prices = [2,4,1]
 * 输出：2
 * 解释：在第 1 天 (股票价格 = 2) 的时候买入，在第 2 天 (股票价格 = 4) 的时候卖出，这笔交易所能获得利润 = 4-2 = 2 。
 * <p>
 * 示例 2：
 * 输入：k = 2, prices = [3,2,6,5,0,3]
 * 输出：7
 * 解释：在第 2 天 (股票价格 = 2) 的时候买入，在第 3 天 (股票价格 = 6) 的时候卖出, 这笔交易所能获得利润 = 6-2 = 4 。
 * 随后，在第 5 天 (股票价格 = 0) 的时候买入，在第 6 天 (股票价格 = 3) 的时候卖出, 这笔交易所能获得利润 = 3-0 = 3 。
 * <p>
 */
public class BestTimeToBuyAndSellStocks4 {
    /**
     * 买卖股票的最佳时机 IV，即收益最大
     * 最多k笔交易
     *
     * @param k      最多的交易笔数
     * @param prices 每天的股票价格
     * @return 最大收益
     */
    public static int maxProfit(int k, int[] prices) {
        int ans = 0;
        return ans;
    }

    public static void main(String[] args) {
        System.out.println("买卖股票的最大收益IV：" + maxProfit(2, new int[]{3, 3, 5, 0, 0, 3, 1, 4}));
    }

}
