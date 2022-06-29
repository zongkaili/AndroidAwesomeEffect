package com.kelly.practice.lc.stack;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 两个队列实现一个栈
 *
 * 一个队列加入元素，弹出元素时，需要把队列中的 元素放到另外一个队列中，删除最后一个元素
 * 两个队列始终保持只有一个队列是有数据的
 */
public class StackByQueue<T> {

    private Queue<T> queue1 = new LinkedList<>();

    private Queue<T> queue2 = new LinkedList<>();

    /**
     * 压栈
     *
     * 入栈非空的队列
     */
    public boolean push(T t) {
        if (!queue1.isEmpty()) {
            return queue1.offer(t);
        } else {
            return queue2.offer(t);
        }
    }

    /**
     * 弹出并删除元素
     */
    public T pop() {
        if (queue1.isEmpty() && queue2.isEmpty()) {
            throw new RuntimeException("queue is empty");
        }
        if (!queue1.isEmpty() && queue2.isEmpty()) {
            while (queue1.size() > 1) {
                queue2.offer(queue1.poll());
            }
            return queue1.poll();
        }
        if (queue1.isEmpty() && !queue2.isEmpty()) {
            while (queue2.size() > 1) {
                queue1.offer(queue2.poll());
            }
            return queue2.poll();
        }

        return null;
    }

    @Override
    public String toString() {
        return this.queue1.toString() + ", " +this.queue2.toString();
    }

    public static void main(String[] args) {
        StackByQueue<Integer> s = new StackByQueue<>();
        s.push(1);
        s.push(2);
        s.push(3);
        s.pop();
        System.out.println(s);
        s.push(4);
        s.push(5);
        s.pop();
        System.out.println(s);
    }

}
