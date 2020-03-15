package com.kelly.effect.leetcode;

import java.util.HashMap;
import java.util.Stack;

/**
 * author: zongkaili
 * data: 2020/3/15
 * desc: 20.有效的括号
 * 给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串，判断字符串是否有效。
 *
 * 有效字符串需满足：
 *
 *     左括号必须用相同类型的右括号闭合。
 *     左括号必须以正确的顺序闭合。
 *
 * 注意空字符串可被认为是有效字符串。
 *
 * 示例 1:
 *
 * 输入: "()"
 * 输出: true
 *
 * 示例 2:
 *
 * 输入: "()[]{}"
 * 输出: true
 *
 * 示例 3:
 *
 * 输入: "(]"
 * 输出: false
 *
 * 示例 4:
 *
 * 输入: "([)]"
 * 输出: false
 *
 * 示例 5:
 *
 * 输入: "{[]}"
 * 输出: true
 */
public class ValidParentheses {
    public static void main(String[] args) {
        String intput = "{[{}[]]";
        System.out.println("intput = " + intput + ", isValid = " + isValid(intput));
    }
    private static boolean isValid(String s) {
        //solution1 : 数组
//        char[] stack = new char[s.length() + 1];
//        int top = 1;
//        for (char c : s.toCharArray()) {
//            if (c == '(' || c == '[' || c == '{') {
//                stack[top++] = c;
//            } else if (c == ')' && stack[--top] != '(') {
//                return false;
//            } else if (c == ']' && stack[--top] != '[') {
//                return false;
//            } else if (c == '}' && stack[--top] != '{') {
//                return false;
//            }
//        }
//        return top == 1;

        //solution2 : 栈Stack
        HashMap<Character, Character> mappings = new HashMap<>();
        mappings.put(')', '(');
        mappings.put(']', '[');
        mappings.put('}', '{');
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (mappings.containsKey(c)) {
                char topElement = stack.empty() ? '#' : stack.pop();
                if (topElement != mappings.get(c)) {
                    return false;
                }
            } else {
                stack.push(c);
            }
        }
        return stack.isEmpty();
    }
}
