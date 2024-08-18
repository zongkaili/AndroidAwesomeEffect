package com.kelly.practice.lc;

/**
 * 551. 学生出勤记录 I
 * <p>
 * 给你一个字符串 s 表示一个学生的出勤记录，其中的每个字符用来标记当天的出勤情况（缺勤、迟到、到场）。记录中只含下面三种字符：
 * </p>
 * <li>'A'：Absent，缺勤</li>
 * <li>'L'：Late，迟到</li>
 * <li>'P'：Present，到场</li>
 *
 * <p>如果学生能够 同时 满足下面两个条件，则可以获得出勤奖励：</p>
 * <li>按 总出勤 计，学生缺勤（'A'）严格 少于两天。</li>
 * <li>学生 不会 存在 连续 3 天或 连续 3 天以上的迟到（'L'）记录。</li>
 * 如果学生可以获得出勤奖励，返回 true ；否则，返回 false 。
 *
 * <p>
 * 示例 1：
 * 输入：s = "PPALLP"
 * 输出：true
 * 解释：学生缺勤次数少于 2 次，且不存在 3 天或以上的连续迟到记录。
 * </p>
 * <p>
 * 示例 2：
 * 输入：s = "PPALLL"
 * 输出：false
 * 解释：学生最后三天连续迟到，所以不满足出勤奖励的条件
 * </p>
 */
class StudentAttendanceRecord {
    /**
     * 一次遍历（自研）
     * 时间复杂度：O(n)，其中 n 是字符串 s 的长度。需要遍历字符串 s 一次。
     * 空间复杂度：O(1)。
     */
    public boolean checkRecord(String s) {
        char[] chars = s.toCharArray();
        int absentCount = 0, serialLateCount = 0, maxSerialLateCount = 0;
        for (char aChar : chars) {
            if (absentCount >= 2 || serialLateCount >= 3) break;
            if (aChar == 'A') {
                absentCount++;
            }
            if (aChar == 'L') {
                serialLateCount++;
            } else {
                if (serialLateCount > 0) {
                    maxSerialLateCount = Math.max(serialLateCount, maxSerialLateCount);
                    serialLateCount = 0;
                }
            }
        }
        return absentCount < 2 && serialLateCount < 3;
    }
}
