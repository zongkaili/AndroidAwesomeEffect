package com.kelly.practice.lc.number;

import java.util.Stack;

/**
 * author: zongkaili
 * data: 2022/9/18
 * desc: 224. 基本计算器
 * 给你一个字符串表达式 s ，请你实现一个基本计算器来计算并返回它的值。
 * 注意: 不允许使用任何将字符串作为数学表达式计算的内置函数，比如 eval() 。
 * <p>
 * 示例 1：
 * 输入：s = "1+1"
 * 输出：2
 * <p>
 * 示例 2：
 * 输入：s = "2-1+2"
 * 输出：3
 * <p>
 * 示例 3：
 * 输入：s = "(1+(4+5+2)-3)+(6+8)"
 * 输出：23
 */
class BasicCalculator {
    public int calculate(String s) {
        // 使用栈来储存字符串表达式中的数字
        Stack<Integer> stack = new Stack<>();

        // 为了方便计算，所有的操作都视为加法操作
        // 那么原先的减法操作就相当于是加一个负数
        // 默认都是正数
        int sign = 1;
        // 保存计算的结果
        int res = 0;
        int length = s.length();
        // 获取字符串里面的每个字符
        for (int i = 0; i < length; i++) {
            char ch = s.charAt(i);
            // 如果当前字符是数字的话
            if (Character.isDigit(ch)) {
                // 那么可以通过 - '0' 这个操作把字符转换为整数
                // 相当于转换成了 ascii 码进行的数字运算
                int value = ch - '0';
                // 去查看当前字符的后一位是否存在
                // 如果操作并且后一位依旧是数字，那么就需要把后面的数字累加上来
                while (i + 1 < length && Character.isDigit(s.charAt(i + 1))) {
                    // i 向后移动，直到遇到非数字为止
                    i++;
                    // i 每向后移动一位，当前的值就需要乘以 10
                    // 比如 s 为 "123+456"
                    // 此时 i = 0，那么 value 为 1
                    // i = 1，那么 value 为 1 * 10 + 2 = 12
                    // i = 2，那么 value 为 12 * 10 + 3 = 123
                    value = value * 10 + s.charAt(i) - '0';
                }
                // 那么把获取到的数累加到结果 res 上
                res = res + sign * value;

                // 如果是 '+'，sign为正
            } else if (ch == '+') {
                sign = 1;
                // 如果是 '-'，sign为负
            } else if (ch == '-') {
                sign = -1;
                // 如果是 '('
            } else if (ch == '(') {
                // 根据数学计算规则，需要先计算括号里面的数字
                // 而什么时候计算呢？ 遇到 ) 为止
                // 所以，在遇到 ) 之前需要把之前计算好的结果存储起来再计算
                // ( ) 直接的计算规则和一开始是一样的

                // 1、先把 ( 之前的结果存放到栈中
                stack.push(res);

                // 2、重新初始化 res 为 0
                res = 0;
                // 3、把 ( 左边的操作符号存放到栈中
                // 比如如果是 5 - （ 2 + 3 ） ，那么就是把 -1 存放进去
                // 比如如果是 5 +（ 2 + 3 ） ，那么就是把 1 存放进去
                stack.push(sign);

                // 4、为了方便计算，所有的操作都视为加法操作
                // 那么原先的减法操作就相当于是加一个负数，默认都是正数
                sign = 1;

                // 如果是 ')'
            } else if (ch == ')') {
                // 那么就需要把栈中存放的元素取出来了
                // 在 ch == '（' 这个判断语句中，每次都会往栈中存放两个元素
                // 1、先存放左括号外面的结果
                // 2、再存放左括号外面的符号

                // 1、先获取栈顶元素，即左括号外面的符号，查看是 + 还是 -
                int formerSign = stack.peek();

                // 把栈顶元素弹出
                stack.pop();

                // 2、再获取栈顶元素，即左括号外面的结果
                int formerRes = stack.peek();

                // 把栈顶元素弹出
                stack.pop();

                // 那结果就是括号外面的结果 + 括号里面的结果，至于是加正数还是负数，取决于左括号外面的符号
                res = formerRes + formerSign * res;

            }
        }

        // 返回计算好的结果
        return res;
    }
}
