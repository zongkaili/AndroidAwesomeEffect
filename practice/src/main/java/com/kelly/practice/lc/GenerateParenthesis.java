package com.kelly.practice.lc;

import java.util.ArrayList;
import java.util.List;

/**
 * author: zongkaili
 * data: 2020/3/24
 * desc: 22. 括号生成
 * 给出 n 代表生成括号的对数，请你写出一个函数，使其能够生成所有可能的并且有效的括号组合。
 *
 * 例如，给出 n = 3，生成结果为：
 * [
 *   "((()))",
 *   "(()())",
 *   "(())()",
 *   "()(())",
 *   "()()()"
 * ]
 */
public class GenerateParenthesis {

    private static List<String> generateParenthesis(int n) {
        //solution1: 暴力法  todo?
//        List<String> combinations = new ArrayList();
//        generateAll(new char[2 * n], 0, combinations);
//        return combinations;

        //solution2: 回溯法
        List<String> list = new ArrayList<>();
        backtrack(list, "", 0, 0, 3);
        return list;

        //solution3: 闭合数 todo?
//        List<String> ans = new ArrayList();
//        if (n == 0) {
//            ans.add("");
//        } else {
//            for (int c = 0; c < n; ++c)
//                for (String left: generateParenthesis(c))
//                    for (String right: generateParenthesis(n-1-c))
//                        ans.add("(" + left + ")" + right);
//        }
//        return ans;
    }

    private static void generateAll(char[] current, int pos, List<String> result) {
        if (pos == current.length) {
            if (valid(current))
                result.add(new String(current));
        } else {
            current[pos] = '(';
            generateAll(current, pos+1, result);
            current[pos] = ')';
            generateAll(current, pos+1, result);
        }
    }

    private static boolean valid(char[] current) {
        int balance = 0;
        for (char c: current) {
            if (c == '(') balance++;
            else balance--;
            if (balance < 0) return false;
        }
        return (balance == 0);
    }

    private static void backtrack(List<String> list, String s, int open, int close, int max) {
        if (s.length() == max * 2) {
            list.add(s);
            return;
        }
        if (open < max) {
            backtrack(list, s + "(", open + 1, close, max);
        }
        if (close < open) {
            backtrack(list, s + ")", open, close + 1, max);
        }
    }

    public static void main(String[] args) {
        List<String> list = generateParenthesis(1);
        for (String s : list) {
            System.out.println(s);
        }
    }

}
