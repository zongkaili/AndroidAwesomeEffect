package com.kelly.practice.lc;

import java.util.Deque;
import java.util.LinkedList;

/**
 * author: zongkaili
 * data: 2022/6/26
 * desc: 394. 字符串解码
 * 给定一个经过编码的字符串，返回它解码后的字符串。
 * 编码规则为: k[encoded_string]，表示其中方括号内部的 encoded_string 正好重复 k 次。注意 k 保证为正整数。
 * 你可以认为输入字符串总是有效的；输入字符串中没有额外的空格，且输入的方括号总是符合格式要求的。
 * 此外，你可以认为原始数据不包含数字，所有的数字只表示重复的次数 k ，例如不会出现像 3a 或 2[4] 的输入。
 * <p>
 * 示例 1：
 * 输入：s = "3[a]2[bc]"
 * 输出："aaabcbc"
 * <p>
 * 示例 2：
 * 输入：s = "3[a2[c]]"
 * 输出："accaccacc"
 * <p>
 * 示例 3：
 * 输入：s = "2[abc]3[cd]ef"
 * 输出："abcabccdcdcdef"
 * <p>
 * 示例 4：
 * 输入：s = "abc3[cd]xyz"
 * 输出："abccdcdcdxyz"
 */
class DecodeString {
    /**
     * 时间复杂度 O(N)，一次遍历 s
     * 空间复杂度 O(N)，辅助栈在极端情况下需要线性空间。
     */
    public String decodeString(String s) {
        // 创建数字栈，在遍历编码字符串过程中记录出现的数字
        Deque<Integer> numStack = new LinkedList<>();
        // 创建字符栈，在遍历编码字符串过程中记录出现的字符串
        Deque<StringBuilder> strStack = new LinkedList<>();

        // 在访问编码字符串的过程中，用来记录访问到字符串之前出现的数字，一开始为 0
        int digit = 0;
        // 在访问编码字符串的过程中，把得到的结果存放到 res 中
        StringBuilder res = new StringBuilder();
        // 从头到尾遍历编码字符串
        for (int i = 0; i < s.length(); i++) {
            // 在遍历过程中，字符会出现 4 种情况

            // 先获取此时访问的字符
            char ch = s.charAt(i);

            // 1、如果是数字，需要把字符转成整型数字
            // 注意数字不一定是 1 位，有可能是多位
            // 比如  123a，在字母 a 的前面出现了 123，表示 a 重复出现 123 次
            // 那么一开始 ch 为 1，当访问到 ch 为 2 的时候，1 和 2 要组成数字 12
            // 再出现 3 ，12 和 3 组成数字 123
            if (Character.isDigit(ch)) {
                // 先将字符转成整型数字 ch-‘0’
                // 补充知识：将字符'0'-'9'转换为数字，只需将字符变量减去'0'就行
                // 因为字符和数字在内存里都是以 ASCII 码形式存储的
                // 减去 '0' ，其实就是减去字符 '0' 的 ASCII 码，而字符 '0' 的 ASCII 码是 30
                // 所以减去'0'也就是减去30，然后就可以得到字符对应的数字了。
                int num = ch - '0';

                // 再将这个数字和前面存储的数字进行组合
                // 1 和 2 组成数字 12，也就是 1 * 10 + 2 = 12
                // 12 和 3 组成数字 123，也就是 12 * 10 + 3 = 123
                digit = digit * 10 + num;

                // 2、如果是字符
            } else if ((ch >= 'a' && ch <= 'z')) {
                // 说明它就出现一次，可以直接存放到 res 中
                res.append(ch);

                // 3、如果是"["
                // 出现了嵌套的内层编码字符串，而外层的解码需要等待内层解码的结果
                // 那么之前已经扫描的字符需要存放起来，等到内层解码之后再重新使用
            } else if (ch == '[') {
                // 把数字存放到数字栈
                numStack.push(digit);
                // 把外层的解码字符串存放到字符串栈
                strStack.push(res);
                // 开始新的一轮解码
                // 于是，digit 归零
                digit = 0;
                // res 重新初始化
                res = new StringBuilder();
                // 4、如果是"]"
            } else if (ch == ']') {
                // 此时，内层解码已经有结果，需要把它和前面的字符串进行拼接
                // 第一步，先去查看内层解码的字符串需要被重复输出几次
                // 比如 e3[abc]，比如内层解码结果 abc 需要输出 3 次
                // 通过数字栈提取出次数
                int count = numStack.poll();

                // 第二步，通过字符串栈提取出之前的解码字符串
                StringBuilder outString = strStack.poll();

                // 第三步，不断的把内层解码的字符串拼接起来
                for (int j = 0; j < count; j++) {

                    // 拼接到 outString 后面，拼接 count 次
                    outString.append(res.toString());
                }

                // 再把此时得到的结果赋值给 res
                res = outString;
            }
        }

        // 返回解码成功的字符串
        return res.toString();
    }
}
