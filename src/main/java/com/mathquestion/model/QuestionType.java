package com.mathquestion.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 苏教版 1-9 年级口算与数学计算题型枚举
 */
public enum QuestionType {

    // ==========================================
    // 一年级第一学期 (原版 17 种题型)
    // ==========================================
    G1T1_1(Grade.GRADE1_TERM1, "10以内加法"),
    G1T1_2(Grade.GRADE1_TERM1, "10以内减法"),
    G1T1_3(Grade.GRADE1_TERM1, "10以内加减综合"),
    G1T1_4(Grade.GRADE1_TERM1, "10以内连加"),
    G1T1_5(Grade.GRADE1_TERM1, "10以内连减"),
    G1T1_6(Grade.GRADE1_TERM1, "10以内连续加减综合"),
    G1T1_7(Grade.GRADE1_TERM1, "20以内不进位加法"),
    G1T1_8(Grade.GRADE1_TERM1, "20以内不退位减法"),
    G1T1_9(Grade.GRADE1_TERM1, "20以内不进位不退位综合"),
    G1T1_10(Grade.GRADE1_TERM1, "20以内进位加法"),
    G1T1_11(Grade.GRADE1_TERM1, "20以内退位减法"),
    G1T1_12(Grade.GRADE1_TERM1, "20以内进位退位加减综合"),
    G1T1_13(Grade.GRADE1_TERM1, "20以内加减综合"),
    G1T1_14(Grade.GRADE1_TERM1, "20以内连加"),
    G1T1_15(Grade.GRADE1_TERM1, "20以内连减"),
    G1T1_16(Grade.GRADE1_TERM1, "20以内连续加减综合"),
    G1T1_17(Grade.GRADE1_TERM1, "一年级第一学期综合练习"),

    // ==========================================
    // 一年级第二学期 (原版 15 种题型)
    // ==========================================
    G1T2_1(Grade.GRADE1_TERM2, "100以内不进位加整十数"),
    G1T2_2(Grade.GRADE1_TERM2, "100以内不进位加一位数"),
    G1T2_3(Grade.GRADE1_TERM2, "100以内不进位加综合"),
    G1T2_4(Grade.GRADE1_TERM2, "100以内不退位减整十数"),
    G1T2_5(Grade.GRADE1_TERM2, "100以内不退位减一位数"),
    G1T2_6(Grade.GRADE1_TERM2, "100以内不退位减综合"),
    G1T2_7(Grade.GRADE1_TERM2, "100以内不进位不退位加减综合"),
    G1T2_8(Grade.GRADE1_TERM2, "100以内进位加（一位数）"),
    G1T2_9(Grade.GRADE1_TERM2, "100以内退位减（一位数）"),
    G1T2_10(Grade.GRADE1_TERM2, "100以内进位退位加减综合"),
    G1T2_11(Grade.GRADE1_TERM2, "100以内两位数不进不退加减"),
    G1T2_12(Grade.GRADE1_TERM2, "100以内连加"),
    G1T2_13(Grade.GRADE1_TERM2, "100以内连减"),
    G1T2_14(Grade.GRADE1_TERM2, "100以内连续加减综合"),
    G1T2_15(Grade.GRADE1_TERM2, "一年级第二学期综合练习"),

    // ==========================================
    // 二年级第一学期 (原版 12 种题型)
    // ==========================================
    G2T1_1(Grade.GRADE2_TERM1, "表内乘法"),
    G2T1_2(Grade.GRADE2_TERM1, "表内除法"),
    G2T1_3(Grade.GRADE2_TERM1, "表内乘除综合"),
    G2T1_4(Grade.GRADE2_TERM1, "表内连乘"),
    G2T1_5(Grade.GRADE2_TERM1, "表内连除"),
    G2T1_6(Grade.GRADE2_TERM1, "连乘连除综合"),
    G2T1_7(Grade.GRADE2_TERM1, "连续乘除综合2"),
    G2T1_8(Grade.GRADE2_TERM1, "乘加减两步（不进不退）"),
    G2T1_9(Grade.GRADE2_TERM1, "乘加减两步（进位退位）"),
    G2T1_10(Grade.GRADE2_TERM1, "除加减两步（不进不退）"),
    G2T1_11(Grade.GRADE2_TERM1, "除加减两步（进位退位）"),
    G2T1_12(Grade.GRADE2_TERM1, "二年级第一学期综合练习"),

    // ==========================================
    // 二年级第二学期 (原版 7 种题型)
    // ==========================================
    G2T2_1(Grade.GRADE2_TERM2, "整百数加减法"),
    G2T2_2(Grade.GRADE2_TERM2, "整百整十数加法（不进位）"),
    G2T2_3(Grade.GRADE2_TERM2, "整十数加法（进位）"),
    G2T2_4(Grade.GRADE2_TERM2, "整百整十数加法（进位）"),
    G2T2_5(Grade.GRADE2_TERM2, "整百整十数减法（不退位）"),
    G2T2_6(Grade.GRADE2_TERM2, "整百整十数减法（退位）"),
    G2T2_7(Grade.GRADE2_TERM2, "二年级第二学期综合练习"),

    // ==========================================
    // 三年级第一学期 (苏教版 7 种)
    // ==========================================
    G3T1_1(Grade.GRADE3_TERM1, "整十整百数乘一位数"),
    G3T1_2(Grade.GRADE3_TERM1, "两位数乘一位数口算"),
    G3T1_3(Grade.GRADE3_TERM1, "整十整百数除以一位数"),
    G3T1_4(Grade.GRADE3_TERM1, "两位数除以一位数口算"),
    G3T1_5(Grade.GRADE3_TERM1, "同分母分数加法口算"),
    G3T1_6(Grade.GRADE3_TERM1, "同分母分数减法口算"),
    G3T1_7(Grade.GRADE3_TERM1, "三年级上学期综合练习"),

    // ==========================================
    // 三年级第二学期 (苏教版 6 种)
    // ==========================================
    G3T2_1(Grade.GRADE3_TERM2, "两位数乘整十数口算"),
    G3T2_2(Grade.GRADE3_TERM2, "一位小数加法口算"),
    G3T2_3(Grade.GRADE3_TERM2, "一位小数减法口算"),
    G3T2_4(Grade.GRADE3_TERM2, "乘加乘减两步口算"),
    G3T2_5(Grade.GRADE3_TERM2, "除加除减两步口算"),
    G3T2_6(Grade.GRADE3_TERM2, "三年级下学期综合练习"),

    // ==========================================
    // 四年级第一学期 (苏教版 6 种)
    // ==========================================
    G4T1_1(Grade.GRADE4_TERM1, "整百整十数除以整十数"),
    G4T1_2(Grade.GRADE4_TERM1, "三位数除以两位数试商口算"),
    G4T1_3(Grade.GRADE4_TERM1, "加法交换律与结合律凑整"),
    G4T1_4(Grade.GRADE4_TERM1, "带括号四则混合两步口算"),
    G4T1_5(Grade.GRADE4_TERM1, "除法简便速算口算"),
    G4T1_6(Grade.GRADE4_TERM1, "四年级上学期综合练习"),

    // ==========================================
    // 四年级第二学期 (苏教版 6 种)
    // ==========================================
    G4T2_1(Grade.GRADE4_TERM2, "整百数乘整十数口算"),
    G4T2_2(Grade.GRADE4_TERM2, "乘法交换律与结合律简算"),
    G4T2_3(Grade.GRADE4_TERM2, "乘法分配律基础口算"),
    G4T2_4(Grade.GRADE4_TERM2, "乘法分配律逆运算凑整"),
    G4T2_5(Grade.GRADE4_TERM2, "运算律两步综合简算"),
    G4T2_6(Grade.GRADE4_TERM2, "四年级下学期综合练习"),

    // ==========================================
    // 五年级第一学期 (苏教版 7 种)
    // ==========================================
    G5T1_1(Grade.GRADE5_TERM1, "正负数与相反数口算"),
    G5T1_2(Grade.GRADE5_TERM1, "两位小数加法口算"),
    G5T1_3(Grade.GRADE5_TERM1, "两位小数减法口算"),
    G5T1_4(Grade.GRADE5_TERM1, "小数乘整数与小数口算"),
    G5T1_5(Grade.GRADE5_TERM1, "小数除以整数与小数口算"),
    G5T1_6(Grade.GRADE5_TERM1, "小数移动小数点乘除法"),
    G5T1_7(Grade.GRADE5_TERM1, "五年级上学期综合练习"),

    // ==========================================
    // 五年级第二学期 (苏教版 6 种)
    // ==========================================
    G5T2_1(Grade.GRADE5_TERM2, "最大公因数与最小公倍数"),
    G5T2_2(Grade.GRADE5_TERM2, "异分母分数加法口算"),
    G5T2_3(Grade.GRADE5_TERM2, "异分母分数减法口算"),
    G5T2_4(Grade.GRADE5_TERM2, "分数加减混合运算口算"),
    G5T2_5(Grade.GRADE5_TERM2, "一步简易方程求解口算"),
    G5T2_6(Grade.GRADE5_TERM2, "五年级下学期综合练习"),

    // ==========================================
    // 六年级第一学期 (苏教版 6 种)
    // ==========================================
    G6T1_1(Grade.GRADE6_TERM1, "分数乘整数口算"),
    G6T1_2(Grade.GRADE6_TERM1, "分数乘分数口算"),
    G6T1_3(Grade.GRADE6_TERM1, "求一个数的倒数口算"),
    G6T1_4(Grade.GRADE6_TERM1, "分数除法口算"),
    G6T1_5(Grade.GRADE6_TERM1, "化简比与求比值口算"),
    G6T1_6(Grade.GRADE6_TERM1, "六年级上学期综合练习"),

    // ==========================================
    // 六年级第二学期 (苏教版 5 种)
    // ==========================================
    G6T2_1(Grade.GRADE6_TERM2, "百分数与小数分数互化"),
    G6T2_2(Grade.GRADE6_TERM2, "求一个数的百分之几口算"),
    G6T2_3(Grade.GRADE6_TERM2, "解比例口算"),
    G6T2_4(Grade.GRADE6_TERM2, "小学总复习数与代数计算"),
    G6T2_5(Grade.GRADE6_TERM2, "六年级下学期综合练习"),

    // ==========================================
    // 七年级第一学期 (初中 / 苏科版 6 种)
    // ==========================================
    G7T1_1(Grade.GRADE7_TERM1, "有理数加法（含负数）"),
    G7T1_2(Grade.GRADE7_TERM1, "有理数减法（含负数）"),
    G7T1_3(Grade.GRADE7_TERM1, "有理数乘法与除法口算"),
    G7T1_4(Grade.GRADE7_TERM1, "有理数乘方口算"),
    G7T1_5(Grade.GRADE7_TERM1, "合并同类项口算"),
    G7T1_6(Grade.GRADE7_TERM1, "七年级上学期综合练习"),

    // ==========================================
    // 七年级第二学期 (初中 / 苏科版 6 种)
    // ==========================================
    G7T2_1(Grade.GRADE7_TERM2, "同底数幂乘除口算"),
    G7T2_2(Grade.GRADE7_TERM2, "幂的乘方与积的乘方"),
    G7T2_3(Grade.GRADE7_TERM2, "单项式乘除整式口算"),
    G7T2_4(Grade.GRADE7_TERM2, "平方差公式口算展开"),
    G7T2_5(Grade.GRADE7_TERM2, "完全平方公式口算展开"),
    G7T2_6(Grade.GRADE7_TERM2, "一元一次方程口算求解"),

    // ==========================================
    // 八年级第一学期 (初中 / 苏科版 5 种)
    // ==========================================
    G8T1_1(Grade.GRADE8_TERM1, "算术平方根口算"),
    G8T1_2(Grade.GRADE8_TERM1, "平方根与立方根口算"),
    G8T1_3(Grade.GRADE8_TERM1, "实数简单加减口算"),
    G8T1_4(Grade.GRADE8_TERM1, "负整数指数幂口算"),
    G8T1_5(Grade.GRADE8_TERM1, "零指数幂与乘方综合"),

    // ==========================================
    // 八年级第二学期 (初中 / 苏科版 5 种)
    // ==========================================
    G8T2_1(Grade.GRADE8_TERM2, "二次根式化简口算"),
    G8T2_2(Grade.GRADE8_TERM2, "二次根式乘除法口算"),
    G8T2_3(Grade.GRADE8_TERM2, "二次根式加减混合口算"),
    G8T2_4(Grade.GRADE8_TERM2, "分式约分口算"),
    G8T2_5(Grade.GRADE8_TERM2, "分式乘除法口算"),

    // ==========================================
    // 九年级第一学期 (初中 / 苏科版 4 种)
    // ==========================================
    G9T1_1(Grade.GRADE9_TERM1, "一元二次方程直接开平方"),
    G9T1_2(Grade.GRADE9_TERM1, "一元二次方程因式分解求根"),
    G9T1_3(Grade.GRADE9_TERM1, "特殊角三角函数口算"),
    G9T1_4(Grade.GRADE9_TERM1, "九年级上学期综合练习"),

    // ==========================================
    // 九年级第二学期 (初中 / 苏科版 4 种)
    // ==========================================
    G9T2_1(Grade.GRADE9_TERM2, "特殊角三角函数混合运算"),
    G9T2_2(Grade.GRADE9_TERM2, "二次函数顶点坐标口算"),
    G9T2_3(Grade.GRADE9_TERM2, "中考代数式计算综合冲刺"),
    G9T2_4(Grade.GRADE9_TERM2, "九年级下学期综合练习");

    private final Grade grade;
    private final String name;

    QuestionType(Grade grade, String name) {
        this.grade = grade;
        this.name = name;
    }

    public Grade getGrade() {
        return grade;
    }

    public String getName() {
        return name;
    }

    public static List<QuestionType> getByGrade(Grade grade) {
        List<QuestionType> list = new ArrayList<>();
        for (QuestionType type : values()) {
            if (type.grade == grade) {
                list.add(type);
            }
        }
        return Collections.unmodifiableList(list);
    }

    @Override
    public String toString() {
        return name;
    }
}
