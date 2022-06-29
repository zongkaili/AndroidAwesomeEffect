package com.kelly.practice.lc.stock;

/**
 * Copyright (c) 2014-2021 Zuoyebang, All rights reserved.
 *
 * @author zongkaili | zongkaili@zuoyebang.com
 * @version 1.0.0 | 2021/8/4 | zongkaili 初始版本
 * @date 2021/8/4 9:50 上午
 * @description 121. 买卖股票的最佳时机，交易一次
 * 给定一个数组 prices ，它的第 i 个元素prices[i] 表示一支给定股票第 i 天的价格。
 * 你只能选择 某一天 买入这只股票，并选择在 未来的某一个不同的日子 卖出该股票。设计一个算法来计算你所能获取的最大利润。
 * 返回你可以从这笔交易中获取的最大利润。如果你不能获取任何利润，返回 0 。
 *
 * 示例 1：
 * 输入：[7,1,5,3,6,4]
 * 输出：5
 * 解释：在第 2 天（股票价格 = 1）的时候买入，在第 5 天（股票价格 = 6）的时候卖出，最大利润 = 6-1 = 5 。
 *      注意利润不能是 7-1 = 6, 因为卖出价格需要大于买入价格；同时，你不能在买入前卖出股票。
 *
 * 示例 2：
 * 输入：prices = [7,6,4,3,1]
 * 输出：0
 * 解释：在这种情况下, 没有交易完成, 所以最大利润为 0。
 */
public class BestTimeToBuyAndSellStocks {
    /**
     * 买卖股票的最佳时机，即收益最大
     * 交易一次
     * @param prices 每天的股票价格
     * @return 最大收益
     */
    public static int maxProfit(int[] prices) {
        //记录买入的最低价
        int minPrice = Integer.MAX_VALUE;
        //记录最大收益
        int maxProfit = 0;
        for (int price : prices) {
            if (price < minPrice) {
                minPrice = price;
            } else {
                maxProfit = Math.max(maxProfit, price - minPrice);
            }
        }
        return maxProfit;
    }

    public static void main(String[] args) {
        System.out.println("买卖股票的最大收益：" + maxProfit(new int[]{7,1,5,3,6,4}));
    }

}
