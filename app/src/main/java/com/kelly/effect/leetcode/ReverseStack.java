package com.kelly.effect.leetcode;

import java.util.Stack;

/**
 * 反转栈
 */
class ReverseStack {

    /**
     * 第一次递归将 栈顶 元素弹出
     *
     * @param stack
     */
    void reverseStack(Stack<Integer> stack) {
        if (stack.isEmpty()){ return;}

        // 弹出栈顶元素，暂存在栈中
        int top = stack.pop();
        // 调用自身，直到所有元素弹出
        reverseStack(stack);
        // 将栈顶元素 放入栈底
        addStackBottom(stack, top);
    }


    /**
     * 第二次递归将 弹出的栈顶元素 放入栈底
     *
     * @param stack
     * @param top
     */
    void addStackBottom(Stack<Integer> stack, int top) {
        if (stack.isEmpty()) {
            // 栈顶元素入栈
            stack.push(top);
            return;
        }
        // 暂存栈中的元素
        int item = stack.pop();
        addStackBottom(stack, top);
        // 将暂存的元素入栈
        stack.push(item);
    }

    public static void main(String... args) {
        Stack<Integer> stack = new Stack<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);
        stack.push(5);

        int index = stack.size() - 1;
        while (index >= 0) {
            System.out.println("原栈：" + stack.elementAt(index--));
        }


        ReverseStack reverse = new ReverseStack();
        reverse.reverseStack(stack);

        while (!stack.isEmpty()) {
            System.out.println("反转之后：" + stack.pop());
        }
    }
}

