package com.kelly.practice.lc.stock;

/**
 * Copyright (c) 2014-2021 Zuoyebang, All rights reserved.
 *
 * @author zongkaili | zongkaili@zuoyebang.com
 * @version 1.0.0 | 2021/8/4 | zongkaili 初始版本
 * @date 2021/8/4 5:27 下午
 * @description 123. 买卖股票的最佳时机 III，两笔交易
 * 给定一个数组，它的第 i 个元素是一支给定的股票在第 i 天的价格。
 * 设计一个算法来计算你所能获取的最大利润。你最多可以完成两笔交易。
 * 注意：你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。
 * <p>
 * 示例1:
 * 输入：prices = [3,3,5,0,0,3,1,4]
 * 输出：6
 * 解释：在第 4 天（股票价格 = 0）的时候买入，在第 6 天（股票价格 = 3）的时候卖出，这笔交易所能获得利润 = 3-0 = 3 。
 * 随后，在第 7 天（股票价格 = 1）的时候买入，在第 8 天 （股票价格 = 4）的时候卖出，这笔交易所能获得利润 = 4-1 = 3 。
 * <p>
 * 示例 2：
 * 输入：prices = [1,2,3,4,5]
 * 输出：4
 * 解释：在第 1 天（股票价格 = 1）的时候买入，在第 5 天 （股票价格 = 5）的时候卖出, 这笔交易所能获得利润 = 5-1 = 4 。
 * 注意你不能在第 1 天和第 2 天接连购买股票，之后再将它们卖出。
 * 因为这样属于同时参与了多笔交易，你必须在再次购买前出售掉之前的股票。
 * <p>
 * 示例 3：
 * 输入：prices = [7,6,4,3,1]
 * 输出：0
 * 解释：在这个情况下, 没有交易完成, 所以最大利润为 0。
 * <p>
 * 示例 4：
 * 输入：prices = [1]
 * 输出：0
 */
public class BestTimeToBuyAndSellStocks3 {
    /**
     * 买卖股票的最佳时机 III，即收益最大
     * 两笔交易
     * 解法1：动态规划，穷举
     * <p>
     * 由于最多可以完成两笔交易，因此在任意一天结束之后，会处于以下五个状态中的一种：
     * 1.未进行过任何操作； -- 收益为0，不做记录
     * 2.只进行过一次买操作；-- 收益为：-prices[i]
     * 3.进行了一次买操作和一次卖操作，即完成了一笔交易；-- 收益为：上次买入的收益 + prices[i]
     * 4.在完成了一笔交易的前提下，进行了第二次买操作；-- 收益为：上次卖出的收益 - prices[i]
     * 5.完成了全部两笔交易。-- 收益为：第二次买入的收益 + prices[i]
     *
     * @param prices 每天的股票价格
     * @return 最大收益
     */
    public static int maxProfit(int[] prices) {
        //第一次买入的收益
        int buy1 = -prices[0];
        //第一次卖出的收益
        int sell1 = 0;
        //第二次买入的收益
        int buy2 = -prices[0];
        //第二次卖出的收益
        int sell2 = 0;
        for (int i = 1; i < prices.length; i++) {
            //任意一天结束之后的收益都需要比较下未操作和操作的最大收益
            buy1 = Math.max(buy1, -prices[i]);
            sell1 = Math.max(sell1, buy1 + prices[i]);
            buy2 = Math.max(buy2, sell1 - prices[i]);
            sell2 = Math.max(sell2, buy2 + prices[i]);
        }
        return sell2;
    }

    public static void main(String[] args) {
        System.out.println("买卖股票的最大收益III：" + maxProfit(new int[]{3, 3, 5, 0, 0, 3, 1, 4}));
    }

}
