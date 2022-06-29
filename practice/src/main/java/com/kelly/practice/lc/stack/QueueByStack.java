package com.kelly.practice.lc.stack;

import java.util.Stack;

/**
 * author: zongkaili
 * data: 2022/3/25
 * desc: 232. 用栈实现队列
 * 请你仅使用两个栈实现先入先出队列。队列应当支持一般队列支持的所有操作（push、pop、peek、empty）：
 * 实现 MyQueue 类：
 * void push(int x) 将元素 x 推到队列的末尾
 * int pop()        从队列的开头移除并返回元素
 * int peek()       返回队列开头的元素
 * boolean empty()  如果队列为空，返回 true ；否则，返回 false
 * 说明：
 * 你 只能 使用标准的栈操作 —— 也就是只有push to top, peek/pop from top, size, 和 is empty操作是合法的。
 * 你所使用的语言也许不支持栈。你可以使用 list 或者 deque（双端队列）来模拟一个栈，只要是标准的栈操作即可。
 */
class QueueByStack {

    /**
     * 方法1: 使用两个栈 入队 - O(n)， 出队 - O(1)
     */
    private static class MyQueue {
        private Stack<Integer> s1 = new Stack<>();
        private Stack<Integer> s2 = new Stack<>();
        private int front;

        public MyQueue() {
        }

        /**
         * 同时只有一个栈有值，另一个栈为空
         */
        public void push(int x) {
            if (s1.empty())
                front = x;
            while (!s1.isEmpty()) {
                s2.push(s1.pop());
            }
            s2.push(x);
            while (!s2.isEmpty()) {
                s1.push(s2.pop());
            }
        }

        // Removes the element from the front of queue.
        public void pop() {
            s1.pop();
            if (!s1.empty())
                front = s1.peek();
        }

        public int peek() {
            return front;
        }

        // Return whether the queue is empty.
        public boolean empty() {
            return s1.isEmpty();
        }
    }

    /**
     * 方法2: 使用两个栈 入队 - O(1)，出队 - 摊还复杂度 O(1)
     */
    private static class MyQueue2 {
        private Stack<Integer> s1 = new Stack<>();
        private Stack<Integer> s2 = new Stack<>();
        private int front;

        public MyQueue2() {
        }

        // Push element x to the back of queue.
        public void push(int x) {
            if (s1.empty())
                front = x;
            s1.push(x);
        }

        // Removes the element from in front of queue.
        public int pop() {
            if (s2.isEmpty()) {
                while (!s1.isEmpty())
                    s2.push(s1.pop());
            }
            return s2.pop();
        }

        // Get the front element.
        public int peek() {
            if (!s2.isEmpty()) {
                return s2.peek();
            }
            return front;
        }

        // Return whether the queue is empty.
        public boolean empty() {
            return s1.isEmpty() && s2.isEmpty();
        }

    }
}
