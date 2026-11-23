package com.mathquestion.generator;

import com.mathquestion.model.Question;
import com.mathquestion.model.QuestionType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 口算题目生成引擎
 */
public class QuestionGenerator {

    private final Random random;

    public QuestionGenerator() {
        this.random = new Random();
    }

    public QuestionGenerator(long seed) {
        this.random = new Random(seed);
    }

    /**
     * 批量生成指定题型的口算题
     *
     * @param type  题型
     * @param count 题目数量
     * @return 题目列表
     */
    public List<Question> generateBatch(QuestionType type, int count) {
        List<Question> list = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            list.add(generateSingle(type));
        }
        return list;
    }

    /**
     * 生成单道题目
     */
    public Question generateSingle(QuestionType type) {
        return switch (type) {
            // 一年级第1学期
            case G1T1_1 -> gen10Add();
            case G1T1_2 -> gen10Sub();
            case G1T1_3 -> random.nextBoolean() ? gen10Add() : gen10Sub();
            case G1T1_4 -> gen10ContinuousAdd();
            case G1T1_5 -> gen10ContinuousSub();
            case G1T1_6 -> gen10ContinuousMixed();
            case G1T1_7 -> gen20NoCarryAdd();
            case G1T1_8 -> gen20NoBorrowSub();
            case G1T1_9 -> random.nextBoolean() ? gen20NoCarryAdd() : gen20NoBorrowSub();
            case G1T1_10 -> gen20CarryAdd();
            case G1T1_11 -> gen20BorrowSub();
            case G1T1_12 -> random.nextBoolean() ? gen20CarryAdd() : gen20BorrowSub();
            case G1T1_13 -> gen20AddSubComprehensive();
            case G1T1_14 -> gen20ContinuousAdd();
            case G1T1_15 -> gen20ContinuousSub();
            case G1T1_16 -> gen20ContinuousMixed();
            case G1T1_17 -> genG1T1Comprehensive();

            // 一年级第2学期
            case G1T2_1 -> gen100AddTens();
            case G1T2_2 -> gen100AddDigitNoCarry();
            case G1T2_3 -> random.nextBoolean() ? gen100AddTens() : gen100AddDigitNoCarry();
            case G1T2_4 -> gen100SubTens();
            case G1T2_5 -> gen100SubDigitNoBorrow();
            case G1T2_6 -> random.nextBoolean() ? gen100SubTens() : gen100SubDigitNoBorrow();
            case G1T2_7 -> random.nextBoolean() ? 
                    (random.nextBoolean() ? gen100AddTens() : gen100AddDigitNoCarry()) :
                    (random.nextBoolean() ? gen100SubTens() : gen100SubDigitNoBorrow());
            case G1T2_8 -> gen100AddDigitCarry();
            case G1T2_9 -> gen100SubDigitBorrow();
            case G1T2_10 -> random.nextBoolean() ? gen100AddDigitCarry() : gen100SubDigitBorrow();
            case G1T2_11 -> gen100TwoDigitsNoCarryBorrow();
            case G1T2_12 -> gen100ContinuousAdd();
            case G1T2_13 -> gen100ContinuousSub();
            case G1T2_14 -> gen100ContinuousMixed();
            case G1T2_15 -> genG1T2Comprehensive();

            // 二年级第1学期
            case G2T1_1 -> genTableMul();
            case G2T1_2 -> genTableDiv();
            case G2T1_3 -> random.nextBoolean() ? genTableMul() : genTableDiv();
            case G2T1_4 -> genTableContinuousMul();
            case G2T1_5 -> genTableContinuousDiv();
            case G2T1_6 -> random.nextBoolean() ? genTableContinuousMul() : genTableContinuousDiv();
            case G2T1_7 -> genContinuousMulDivMixed();
            case G2T1_8 -> genMulAddSubNoCarryBorrow();
            case G2T1_9 -> genMulAddSubCarryBorrow();
            case G2T1_10 -> genDivAddSubNoCarryBorrow();
            case G2T1_11 -> genDivAddSubCarryBorrow();
            case G2T1_12 -> genMulDivAddSubComprehensive();

            // 二年级第2学期
            case G2T2_1 -> genHundredsAddSub();
            case G2T2_2 -> genHundredsTensAddNoCarry();
            case G2T2_3 -> genTensAddCarry();
            case G2T2_4 -> genHundredsTensAddCarry();
            case G2T2_5 -> genHundredsTensSubNoBorrow();
            case G2T2_6 -> genHundredsTensSubBorrow();
            case G2T2_7 -> genHundredsTensComprehensive();

            // 三年级第一学期
            case G3T1_1 -> genG3T1_1();
            case G3T1_2 -> genG3T1_2();
            case G3T1_3 -> genG3T1_3();
            case G3T1_4 -> genG3T1_4();
            case G3T1_5 -> genG3T1_5();
            case G3T1_6 -> genG3T1_6();
            case G3T1_7 -> genG3T1_7();

            // 三年级第二学期
            case G3T2_1 -> genG3T2_1();
            case G3T2_2 -> genG3T2_2();
            case G3T2_3 -> genG3T2_3();
            case G3T2_4 -> genG3T2_4();
            case G3T2_5 -> genG3T2_5();
            case G3T2_6 -> genG3T2_6();

            // 四年级第一学期
            case G4T1_1 -> genG4T1_1();
            case G4T1_2 -> genG4T1_2();
            case G4T1_3 -> genG4T1_3();
            case G4T1_4 -> genG4T1_4();
            case G4T1_5 -> genG4T1_5();
            case G4T1_6 -> genG4T1_6();

            // 四年级第二学期
            case G4T2_1 -> genG4T2_1();
            case G4T2_2 -> genG4T2_2();
            case G4T2_3 -> genG4T2_3();
            case G4T2_4 -> genG4T2_4();
            case G4T2_5 -> genG4T2_5();
            case G4T2_6 -> genG4T2_6();

            // 五年级第一学期
            case G5T1_1 -> genG5T1_1();
            case G5T1_2 -> genG5T1_2();
            case G5T1_3 -> genG5T1_3();
            case G5T1_4 -> genG5T1_4();
            case G5T1_5 -> genG5T1_5();
            case G5T1_6 -> genG5T1_6();
            case G5T1_7 -> genG5T1_7();

            // 五年级第二学期
            case G5T2_1 -> genG5T2_1();
            case G5T2_2 -> genG5T2_2();
            case G5T2_3 -> genG5T2_3();
            case G5T2_4 -> genG5T2_4();
            case G5T2_5 -> genG5T2_5();
            case G5T2_6 -> genG5T2_6();

            // 六年级第一学期
            case G6T1_1 -> genG6T1_1();
            case G6T1_2 -> genG6T1_2();
            case G6T1_3 -> genG6T1_3();
            case G6T1_4 -> genG6T1_4();
            case G6T1_5 -> genG6T1_5();
            case G6T1_6 -> genG6T1_6();

            // 六年级第二学期
            case G6T2_1 -> genG6T2_1();
            case G6T2_2 -> genG6T2_2();
            case G6T2_3 -> genG6T2_3();
            case G6T2_4 -> genG6T2_4();
            case G6T2_5 -> genG6T2_5();

            // 七年级第一学期
            case G7T1_1 -> genG7T1_1();
            case G7T1_2 -> genG7T1_2();
            case G7T1_3 -> genG7T1_3();
            case G7T1_4 -> genG7T1_4();
            case G7T1_5 -> genG7T1_5();
            case G7T1_6 -> genG7T1_6();

            // 七年级第二学期
            case G7T2_1 -> genG7T2_1();
            case G7T2_2 -> genG7T2_2();
            case G7T2_3 -> genG7T2_3();
            case G7T2_4 -> genG7T2_4();
            case G7T2_5 -> genG7T2_5();
            case G7T2_6 -> genG7T2_6();

            // 八年级第一学期
            case G8T1_1 -> genG8T1_1();
            case G8T1_2 -> genG8T1_2();
            case G8T1_3 -> genG8T1_3();
            case G8T1_4 -> genG8T1_4();
            case G8T1_5 -> genG8T1_5();

            // 八年级第二学期
            case G8T2_1 -> genG8T2_1();
            case G8T2_2 -> genG8T2_2();
            case G8T2_3 -> genG8T2_3();
            case G8T2_4 -> genG8T2_4();
            case G8T2_5 -> genG8T2_5();

            // 九年级第一学期
            case G9T1_1 -> genG9T1_1();
            case G9T1_2 -> genG9T1_2();
            case G9T1_3 -> genG9T1_3();
            case G9T1_4 -> genG9T1_4();

            // 九年级第二学期
            case G9T2_1 -> genG9T2_1();
            case G9T2_2 -> genG9T2_2();
            case G9T2_3 -> genG9T2_3();
            case G9T2_4 -> genG9T2_4();
        };
    }

    // ==========================================
    // 一年级第一学期具体实现
    // ==========================================

    // 10以内加法: a + b <= 10
    private Question gen10Add() {
        int a = random.nextInt(11); // 0..10
        int b = random.nextInt(11 - a);
        return new Question(a + "＋" + b + "＝", String.valueOf(a + b));
    }

    // 10以内减法: a - b >= 0, a <= 10
    private Question gen10Sub() {
        int a = random.nextInt(11); // 0..10
        int b = a == 0 ? 0 : random.nextInt(a + 1);
        return new Question(a + "－" + b + "＝", String.valueOf(a - b));
    }

    // 10以内连加: a + b + c <= 10
    private Question gen10ContinuousAdd() {
        int a = random.nextInt(9) + 1; // 1..9
        int remain = 10 - a;
        int b = random.nextInt(remain) + 1;
        remain -= b;
        int c = remain == 0 ? 0 : random.nextInt(remain + 1);
        return new Question(a + "＋" + b + "＋" + c + "＝", String.valueOf(a + b + c));
    }

    // 10以内连减: a - b - c >= 0
    private Question gen10ContinuousSub() {
        int a = random.nextInt(9) + 2; // 2..10
        int b = random.nextInt(a - 1) + 1;
        int c = random.nextInt(a - b + 1);
        return new Question(a + "－" + b + "－" + c + "＝", String.valueOf(a - b - c));
    }

    // 10以内连续加减综合: a + b - c 或 a - b + c
    private Question gen10ContinuousMixed() {
        if (random.nextBoolean()) {
            // a + b - c
            int a = random.nextInt(9) + 1;
            int b = random.nextInt(10 - a) + 1;
            int sum = a + b;
            int c = random.nextInt(sum + 1);
            return new Question(a + "＋" + b + "－" + c + "＝", String.valueOf(sum - c));
        } else {
            // a - b + c
            int a = random.nextInt(9) + 2;
            int b = random.nextInt(a) + 1;
            int diff = a - b;
            int c = random.nextInt(11 - diff);
            return new Question(a + "－" + b + "＋" + c + "＝", String.valueOf(diff + c));
        }
    }

    // 20以内不进位加法: 如 12 + 3 = 15, 4 + 13 = 17
    private Question gen20NoCarryAdd() {
        boolean leftTwo = random.nextBoolean();
        int twoDigit = 10 + random.nextInt(9); // 10..18
        int maxUnit = 19 - twoDigit;
        int oneDigit = random.nextInt(maxUnit + 1); // 0..maxUnit
        int a = leftTwo ? twoDigit : oneDigit;
        int b = leftTwo ? oneDigit : twoDigit;
        return new Question(a + "＋" + b + "＝", String.valueOf(a + b));
    }

    // 20以内不退位减法: 如 17 - 4 = 13, 18 - 12 = 6, 15 - 5 = 10
    private Question gen20NoBorrowSub() {
        int a = 11 + random.nextInt(9); // 11..19
        int unitA = a % 10;
        int b;
        if (random.nextBoolean()) {
            // 减一位数: b <= unitA
            b = random.nextInt(unitA + 1);
        } else {
            // 减十几: b = 10..a
            b = 10 + random.nextInt(unitA + 1);
        }
        return new Question(a + "－" + b + "＝", String.valueOf(a - b));
    }

    // 20以内进位加法: 一位数 + 一位数，个位相加满10 (如 8 + 5 = 13)
    private Question gen20CarryAdd() {
        int a = 2 + random.nextInt(8); // 2..9
        int minB = 10 - a + 1; // 使得 a + b >= 11
        if (minB > 9) minB = 9;
        int b = minB + random.nextInt(10 - minB); // minB..9
        return new Question(a + "＋" + b + "＝", String.valueOf(a + b));
    }

    // 20以内退位减法: 十几减一位数，个位不够减 (如 14 - 8 = 6)
    private Question gen20BorrowSub() {
        int b = 2 + random.nextInt(8); // 减数 2..9
        int c = 2 + random.nextInt(8); // 差 2..9
        while (b + c <= 10 || b + c >= 20) {
            b = 2 + random.nextInt(8);
            c = 2 + random.nextInt(8);
        }
        int a = b + c; // 11..18
        return new Question(a + "－" + b + "＝", String.valueOf(c));
    }

    // 20以内加减综合
    private Question gen20AddSubComprehensive() {
        int choice = random.nextInt(4);
        return switch (choice) {
            case 0 -> gen20NoCarryAdd();
            case 1 -> gen20NoBorrowSub();
            case 2 -> gen20CarryAdd();
            default -> gen20BorrowSub();
        };
    }

    // 20以内连加: a + b + c <= 20
    private Question gen20ContinuousAdd() {
        int a = random.nextInt(10) + 1;
        int b = random.nextInt(20 - a) + 1;
        int c = random.nextInt(21 - a - b);
        return new Question(a + "＋" + b + "＋" + c + "＝", String.valueOf(a + b + c));
    }

    // 20以内连减: a - b - c >= 0, a <= 20
    private Question gen20ContinuousSub() {
        int a = 11 + random.nextInt(10); // 11..20
        int b = random.nextInt(a - 2) + 1;
        int c = random.nextInt(a - b + 1);
        return new Question(a + "－" + b + "－" + c + "＝", String.valueOf(a - b - c));
    }

    // 20以内连续加减综合
    private Question gen20ContinuousMixed() {
        if (random.nextBoolean()) {
            // a + b - c
            int a = random.nextInt(12) + 1;
            int b = random.nextInt(20 - a) + 1;
            int sum = a + b;
            int c = random.nextInt(sum + 1);
            return new Question(a + "＋" + b + "－" + c + "＝", String.valueOf(sum - c));
        } else {
            // a - b + c
            int a = random.nextInt(15) + 5;
            int b = random.nextInt(a) + 1;
            int diff = a - b;
            int c = random.nextInt(21 - diff);
            return new Question(a + "－" + b + "＋" + c + "＝", String.valueOf(diff + c));
        }
    }

    // 一年级第1学期综合练习
    private Question genG1T1Comprehensive() {
        int r = random.nextInt(6);
        return switch (r) {
            case 0 -> gen10Add();
            case 1 -> gen10Sub();
            case 2 -> gen20CarryAdd();
            case 3 -> gen20BorrowSub();
            case 4 -> gen20ContinuousMixed();
            default -> gen20NoCarryAdd();
        };
    }

    // ==========================================
    // 一年级第二学期具体实现
    // ==========================================

    // 100以内不进位加整十数: 如 34 + 20 = 54
    private Question gen100AddTens() {
        int a = 11 + random.nextInt(80); // 两位数
        int maxTens = (99 - a) / 10;
        if (maxTens < 1) maxTens = 1;
        int tens = (random.nextInt(maxTens) + 1) * 10;
        if (random.nextBoolean()) {
            return new Question(a + "＋" + tens + "＝", String.valueOf(a + tens));
        } else {
            return new Question(tens + "＋" + a + "＝", String.valueOf(tens + a));
        }
    }

    // 100以内不进位加一位数: 如 42 + 5 = 47
    private Question gen100AddDigitNoCarry() {
        int a = 10 + random.nextInt(85);
        int unit = a % 10;
        int b = random.nextInt(10 - unit);
        if (b == 0) b = 1;
        if (random.nextBoolean()) {
            return new Question(a + "＋" + b + "＝", String.valueOf(a + b));
        } else {
            return new Question(b + "＋" + a + "＝", String.valueOf(b + a));
        }
    }

    // 100以内不退位减整十数: 如 56 - 20 = 36
    private Question gen100SubTens() {
        int a = 20 + random.nextInt(79); // 20..98
        int maxTens = a / 10;
        int b = (random.nextInt(maxTens) + 1) * 10;
        return new Question(a + "－" + b + "＝", String.valueOf(a - b));
    }

    // 100以内不退位减一位数: 如 58 - 3 = 55
    private Question gen100SubDigitNoBorrow() {
        int a = 11 + random.nextInt(88); // 11..98
        int unit = a % 10;
        int b = unit == 0 ? 0 : random.nextInt(unit + 1);
        return new Question(a + "－" + b + "＝", String.valueOf(a - b));
    }

    // 100以内进位加（一位数）: 如 38 + 7 = 45
    private Question gen100AddDigitCarry() {
        int tens = 1 + random.nextInt(8); // 10..80
        int unit = 1 + random.nextInt(8); // 1..8
        int a = tens * 10 + unit;
        int minB = 10 - unit; // 使得满10进位
        int b = minB + random.nextInt(10 - minB);
        if (b > 9) b = 9;
        return new Question(a + "＋" + b + "＝", String.valueOf(a + b));
    }

    // 100以内退位减（一位数）: 如 43 - 8 = 35
    private Question gen100SubDigitBorrow() {
        int a = 20 + random.nextInt(79); // 20..98
        int unit = a % 10;
        int minB = unit + 1;
        if (minB > 9) minB = 9;
        int b = minB + random.nextInt(10 - minB);
        return new Question(a + "－" + b + "＝", String.valueOf(a - b));
    }

    // 100以内（两位数不进位不退位）加减: 如 35 + 24 = 59, 68 - 25 = 43
    private Question gen100TwoDigitsNoCarryBorrow() {
        if (random.nextBoolean()) {
            // 加法
            int aTen = 1 + random.nextInt(7);
            int aUnit = random.nextInt(9);
            int bTen = 1 + random.nextInt(9 - aTen);
            int bUnit = random.nextInt(10 - aUnit);
            int a = aTen * 10 + aUnit;
            int b = bTen * 10 + bUnit;
            return new Question(a + "＋" + b + "＝", String.valueOf(a + b));
        } else {
            // 减法
            int aTen = 2 + random.nextInt(8);
            int aUnit = random.nextInt(10);
            int bTen = 1 + random.nextInt(aTen);
            int bUnit = random.nextInt(aUnit + 1);
            int a = aTen * 10 + aUnit;
            int b = bTen * 10 + bUnit;
            return new Question(a + "－" + b + "＝", String.valueOf(a - b));
        }
    }

    // 100以内连加
    private Question gen100ContinuousAdd() {
        int a = 10 + random.nextInt(40);
        int b = 10 + random.nextInt(40);
        int maxC = 100 - a - b;
        if (maxC < 1) maxC = 1;
        int c = 1 + random.nextInt(maxC);
        return new Question(a + "＋" + b + "＋" + c + "＝", String.valueOf(a + b + c));
    }

    // 100以内连减
    private Question gen100ContinuousSub() {
        int a = 50 + random.nextInt(49); // 50..98
        int b = 10 + random.nextInt(a - 20);
        int c = 1 + random.nextInt(a - b);
        return new Question(a + "－" + b + "－" + c + "＝", String.valueOf(a - b - c));
    }

    // 100以内连续加减综合
    private Question gen100ContinuousMixed() {
        if (random.nextBoolean()) {
            int a = 20 + random.nextInt(50);
            int b = 10 + random.nextInt(100 - a);
            int sum = a + b;
            int c = 5 + random.nextInt(sum - 5);
            return new Question(a + "＋" + b + "－" + c + "＝", String.valueOf(sum - c));
        } else {
            int a = 40 + random.nextInt(55);
            int b = 10 + random.nextInt(a - 10);
            int diff = a - b;
            int c = 5 + random.nextInt(100 - diff);
            return new Question(a + "－" + b + "＋" + c + "＝", String.valueOf(diff + c));
        }
    }

    // 一年级第2学期综合练习
    private Question genG1T2Comprehensive() {
        int r = random.nextInt(5);
        return switch (r) {
            case 0 -> gen100AddTens();
            case 1 -> gen100AddDigitCarry();
            case 2 -> gen100SubDigitBorrow();
            case 3 -> gen100TwoDigitsNoCarryBorrow();
            default -> gen100ContinuousMixed();
        };
    }

    // ==========================================
    // 二年级第一学期具体实现
    // ==========================================

    // 表内乘法: 九九表
    private Question genTableMul() {
        int a = 1 + random.nextInt(9);
        int b = 1 + random.nextInt(9);
        return new Question(a + "×" + b + "＝", String.valueOf(a * b));
    }

    // 表内除法: 表内乘法的逆运算
    private Question genTableDiv() {
        int b = 1 + random.nextInt(9);
        int quotient = 1 + random.nextInt(9);
        int a = b * quotient;
        return new Question(a + "÷" + b + "＝", String.valueOf(quotient));
    }

    // 表内连乘: 如 2 × 3 × 4 = 24
    private Question genTableContinuousMul() {
        int a = 2 + random.nextInt(4); // 2..5
        int b = 2 + random.nextInt(4); // 2..5
        int maxC = 100 / (a * b);
        if (maxC > 9) maxC = 9;
        if (maxC < 2) maxC = 2;
        int c = 2 + random.nextInt(maxC - 1);
        return new Question(a + "×" + b + "×" + c + "＝", String.valueOf(a * b * c));
    }

    // 表内连除: 如 36 ÷ 2 ÷ 3 = 6
    private Question genTableContinuousDiv() {
        int q = 1 + random.nextInt(9); // 最终商 1..9
        int d2 = 2 + random.nextInt(4);
        int d1 = 2 + random.nextInt(5);
        int a = q * d2 * d1;
        while (a > 81) {
            d2 = 2;
            d1 = 2 + random.nextInt(3);
            q = 1 + random.nextInt(9);
            a = q * d2 * d1;
        }
        return new Question(a + "÷" + d1 + "÷" + d2 + "＝", String.valueOf(q));
    }

    // 连续乘除综合2: 如 a × b ÷ c 或 a ÷ b × c
    private Question genContinuousMulDivMixed() {
        if (random.nextBoolean()) {
            // a × b ÷ c
            int c = 2 + random.nextInt(8); // 2..9
            int q = 1 + random.nextInt(9);
            int prod = c * q;
            // 找到两个一位数 a, b 使得 a * b = prod
            List<int[]> factors = new ArrayList<>();
            for (int x = 1; x <= 9; x++) {
                if (prod % x == 0 && prod / x <= 9) {
                    factors.add(new int[]{x, prod / x});
                }
            }
            if (factors.isEmpty()) {
                return new Question("4×6÷3＝", "8");
            }
            int[] pair = factors.get(random.nextInt(factors.size()));
            return new Question(pair[0] + "×" + pair[1] + "÷" + c + "＝", String.valueOf(q));
        } else {
            // a ÷ b × c
            int b = 2 + random.nextInt(8);
            int q = 1 + random.nextInt(9);
            int a = b * q;
            int c = 1 + random.nextInt(9);
            return new Question(a + "÷" + b + "×" + c + "＝", String.valueOf(q * c));
        }
    }

    // 乘加减两步（不进、不退）: 如 3 × 4 + 5 = 17, 5 × 6 - 8 = 22
    private Question genMulAddSubNoCarryBorrow() {
        int a = 2 + random.nextInt(8);
        int b = 2 + random.nextInt(8);
        int prod = a * b;
        if (random.nextBoolean()) {
            // 乘加不进位: prod 个位 + c < 10
            int unit = prod % 10;
            int maxC = 9 - unit;
            if (maxC < 1) maxC = 1;
            int c = 1 + random.nextInt(maxC);
            return new Question(a + "×" + b + "＋" + c + "＝", String.valueOf(prod + c));
        } else {
            // 乘减不退位: prod 个位 - c >= 0
            int unit = prod % 10;
            if (unit == 0) unit = 1;
            int c = 1 + random.nextInt(unit);
            return new Question(a + "×" + b + "－" + c + "＝", String.valueOf(prod - c));
        }
    }

    // 乘加减两步（进位、退位）: 如 6 × 7 + 9 = 51, 7 × 8 - 9 = 47
    private Question genMulAddSubCarryBorrow() {
        int a = 3 + random.nextInt(7);
        int b = 3 + random.nextInt(7);
        int prod = a * b;
        if (random.nextBoolean()) {
            // 乘加进位: prod 个位 + c >= 10
            int unit = prod % 10;
            int minC = 10 - unit;
            if (minC > 9) minC = 9;
            int c = minC + random.nextInt(10 - minC);
            return new Question(a + "×" + b + "＋" + c + "＝", String.valueOf(prod + c));
        } else {
            // 乘减退位: prod 个位 < c
            int unit = prod % 10;
            int minC = unit + 1;
            if (minC > 9) minC = 9;
            int c = minC + random.nextInt(10 - minC);
            return new Question(a + "×" + b + "－" + c + "＝", String.valueOf(prod - c));
        }
    }

    // 除加减两步（不进、不退）: 如 24 ÷ 6 + 12 = 16
    private Question genDivAddSubNoCarryBorrow() {
        int d = 2 + random.nextInt(8);
        int q = 1 + random.nextInt(9);
        int a = d * q;
        if (random.nextBoolean()) {
            // 除加不进位
            int maxC = 9 - (q % 10);
            if (maxC < 1) maxC = 1;
            int c = 1 + random.nextInt(maxC);
            return new Question(a + "÷" + d + "＋" + c + "＝", String.valueOf(q + c));
        } else {
            // 除减不退位
            int c = q == 1 ? 1 : 1 + random.nextInt(q);
            return new Question(a + "÷" + d + "－" + c + "＝", String.valueOf(q - c));
        }
    }

    // 除加减两步（进位、退位）: 如 35 ÷ 7 + 18 = 23
    private Question genDivAddSubCarryBorrow() {
        int d = 2 + random.nextInt(8);
        int q = 2 + random.nextInt(8);
        int a = d * q;
        if (random.nextBoolean()) {
            // 进位加
            int minC = 10 - q;
            if (minC > 9) minC = 9;
            int c = minC + random.nextInt(10 - minC);
            return new Question(a + "÷" + d + "＋" + c + "＝", String.valueOf(q + c));
        } else {
            // 退位减 (两步运算减法)
            int c = 1 + random.nextInt(q);
            return new Question(a + "÷" + d + "－" + c + "＝", String.valueOf(q - c));
        }
    }

    // 二年级第1学期乘除加减两步综合
    private Question genMulDivAddSubComprehensive() {
        int r = random.nextInt(4);
        return switch (r) {
            case 0 -> genMulAddSubNoCarryBorrow();
            case 1 -> genMulAddSubCarryBorrow();
            case 2 -> genDivAddSubNoCarryBorrow();
            default -> genDivAddSubCarryBorrow();
        };
    }

    // ==========================================
    // 二年级第二学期具体实现 (万以内整百整十加减)
    // ==========================================

    // 整百数加减法: 如 300 + 400 = 700, 800 - 500 = 300
    private Question genHundredsAddSub() {
        if (random.nextBoolean()) {
            int a = (1 + random.nextInt(8)) * 100;
            int maxB = (900 - a) / 100;
            if (maxB < 1) maxB = 1;
            int b = (1 + random.nextInt(maxB)) * 100;
            return new Question(a + "＋" + b + "＝", String.valueOf(a + b));
        } else {
            int a = (2 + random.nextInt(8)) * 100;
            int b = (1 + random.nextInt(a / 100)) * 100;
            return new Question(a + "－" + b + "＝", String.valueOf(a - b));
        }
    }

    // 整百整十数加法（不进位）: 如 230 + 140 = 370
    private Question genHundredsTensAddNoCarry() {
        int aH = 1 + random.nextInt(7);
        int aT = random.nextInt(9);
        int bH = 1 + random.nextInt(8 - aH);
        int bT = random.nextInt(10 - aT);
        int a = aH * 100 + aT * 10;
        int b = bH * 100 + bT * 10;
        return new Question(a + "＋" + b + "＝", String.valueOf(a + b));
    }

    // 整十数加法（进位）: 如 70 + 80 = 150
    private Question genTensAddCarry() {
        int a = (2 + random.nextInt(8)) * 10; // 20..90
        int minB = 10 - (a / 10);
        int b = (minB + random.nextInt(10 - minB)) * 10;
        return new Question(a + "＋" + b + "＝", String.valueOf(a + b));
    }

    // 整百整十数加法（进位）: 如 380 + 250 = 630
    private Question genHundredsTensAddCarry() {
        int aH = 1 + random.nextInt(6);
        int aT = 2 + random.nextInt(8); // 十位满10
        int bH = 1 + random.nextInt(7 - aH);
        int minBT = 10 - aT;
        int bT = minBT + random.nextInt(10 - minBT);
        int a = aH * 100 + aT * 10;
        int b = bH * 100 + bT * 10;
        return new Question(a + "＋" + b + "＝", String.valueOf(a + b));
    }

    // 整百整十数减法（不退位）: 如 570 - 240 = 330
    private Question genHundredsTensSubNoBorrow() {
        int aH = 2 + random.nextInt(8);
        int aT = random.nextInt(10);
        int bH = 1 + random.nextInt(aH);
        int bT = random.nextInt(aT + 1);
        int a = aH * 100 + aT * 10;
        int b = bH * 100 + bT * 10;
        return new Question(a + "－" + b + "＝", String.valueOf(a - b));
    }

    // 整百整十数减法（退位）: 如 520 - 280 = 240
    private Question genHundredsTensSubBorrow() {
        int aH = 2 + random.nextInt(8);
        int aT = random.nextInt(9);
        int bH = 1 + random.nextInt(aH - 1);
        int minBT = aT + 1;
        int bT = minBT + random.nextInt(10 - minBT);
        int a = aH * 100 + aT * 10;
        int b = bH * 100 + bT * 10;
        return new Question(a + "－" + b + "＝", String.valueOf(a - b));
    }

    // 二年级第2学期整百整十加减综合练习
    private Question genHundredsTensComprehensive() {
        int r = random.nextInt(6);
        return switch (r) {
            case 0 -> genHundredsAddSub();
            case 1 -> genHundredsTensAddNoCarry();
            case 2 -> genTensAddCarry();
            case 3 -> genHundredsTensAddCarry();
            case 4 -> genHundredsTensSubNoBorrow();
            default -> genHundredsTensSubBorrow();
        };
    }

    // ==========================================
    // 三年级第一学期 (苏教版)
    // ==========================================
    private Question genG3T1_1() { // 整十整百数乘一位数
        int a = random.nextBoolean() ? (2 + random.nextInt(8)) * 10 : (1 + random.nextInt(9)) * 100;
        int b = 2 + random.nextInt(8);
        return new Question(a + "×" + b + "＝", String.valueOf(a * b));
    }

    private Question genG3T1_2() { // 两位数乘一位数口算
        int a = 12 + random.nextInt(25); // 12..36
        int b = 2 + random.nextInt(5);  // 2..6
        return new Question(a + "×" + b + "＝", String.valueOf(a * b));
    }

    private Question genG3T1_3() { // 整十整百数除以一位数
        int b = 2 + random.nextInt(8);
        int quotient = random.nextBoolean() ? (1 + random.nextInt(9)) * 10 : (1 + random.nextInt(9)) * 100;
        int a = b * quotient;
        return new Question(a + "÷" + b + "＝", String.valueOf(quotient));
    }

    private Question genG3T1_4() { // 两位数除以一位数口算
        int b = 2 + random.nextInt(5); // 2..6
        int quotient = 11 + random.nextInt(20); // 11..30
        int a = b * quotient;
        return new Question(a + "÷" + b + "＝", String.valueOf(quotient));
    }

    private Question genG3T1_5() { // 同分母分数加法口算
        int d = 3 + random.nextInt(8); // 3..10
        int m1 = 1 + random.nextInt(d - 1);
        int m2 = 1 + random.nextInt(d - m1);
        int sum = m1 + m2;
        String ans = (sum == d) ? "1" : sum + "/" + d;
        return new Question(m1 + "/" + d + "＋" + m2 + "/" + d + "＝", ans);
    }

    private Question genG3T1_6() { // 同分母分数减法口算
        int d = 3 + random.nextInt(8);
        if (random.nextBoolean()) {
            int m = 1 + random.nextInt(d - 1);
            int diff = d - m;
            return new Question("1－" + m + "/" + d + "＝", diff + "/" + d);
        } else {
            int m1 = 2 + random.nextInt(d - 1);
            int m2 = 1 + random.nextInt(m1);
            int diff = m1 - m2;
            return new Question(m1 + "/" + d + "－" + m2 + "/" + d + "＝", diff + "/" + d);
        }
    }

    private Question genG3T1_7() { // 三年级上综合
        int r = random.nextInt(6);
        return switch (r) {
            case 0 -> genG3T1_1();
            case 1 -> genG3T1_2();
            case 2 -> genG3T1_3();
            case 3 -> genG3T1_4();
            case 4 -> genG3T1_5();
            default -> genG3T1_6();
        };
    }

    // ==========================================
    // 三年级第二学期 (苏教版)
    // ==========================================
    private Question genG3T2_1() { // 两位数乘整十数口算
        int a = 12 + random.nextInt(35);
        int b = (1 + random.nextInt(5)) * 10;
        return new Question(a + "×" + b + "＝", String.valueOf(a * b));
    }

    private Question genG3T2_2() { // 一位小数加法口算
        int a = 1 + random.nextInt(80); // 0.1..8.0
        int b = 1 + random.nextInt(80);
        double d1 = a / 10.0;
        double d2 = b / 10.0;
        double sum = Math.round((d1 + d2) * 10.0) / 10.0;
        return new Question(String.format("%.1f＋%.1f＝", d1, d2), String.valueOf(sum));
    }

    private Question genG3T2_3() { // 一位小数减法口算
        int b = 1 + random.nextInt(50);
        int a = b + 1 + random.nextInt(50);
        double d1 = a / 10.0;
        double d2 = b / 10.0;
        double diff = Math.round((d1 - d2) * 10.0) / 10.0;
        return new Question(String.format("%.1f－%.1f＝", d1, d2), String.valueOf(diff));
    }

    private Question genG3T2_4() { // 乘加乘减两步口算
        int a = 10 + random.nextInt(20);
        int b = 2 + random.nextInt(4);
        int c = 10 + random.nextInt(50);
        if (random.nextBoolean()) {
            return new Question(a + "×" + b + "＋" + c + "＝", String.valueOf(a * b + c));
        } else {
            int prod = a * b;
            c = 1 + random.nextInt(prod);
            return new Question(a + "×" + b + "－" + c + "＝", String.valueOf(prod - c));
        }
    }

    private Question genG3T2_5() { // 除加除减两步口算
        int b = 2 + random.nextInt(4);
        int q = 10 + random.nextInt(20);
        int a = b * q;
        int c = 5 + random.nextInt(30);
        if (random.nextBoolean()) {
            return new Question(a + "÷" + b + "＋" + c + "＝", String.valueOf(q + c));
        } else {
            c = 1 + random.nextInt(q);
            return new Question(a + "÷" + b + "－" + c + "＝", String.valueOf(q - c));
        }
    }

    private Question genG3T2_6() { // 三年级下综合
        int r = random.nextInt(5);
        return switch (r) {
            case 0 -> genG3T2_1();
            case 1 -> genG3T2_2();
            case 2 -> genG3T2_3();
            case 3 -> genG3T2_4();
            default -> genG3T2_5();
        };
    }

    // ==========================================
    // 四年级第一学期 (苏教版)
    // ==========================================
    private Question genG4T1_1() { // 整百整十数除以整十数
        int b = (2 + random.nextInt(8)) * 10;
        int q = 2 + random.nextInt(8);
        int a = b * q;
        return new Question(a + "÷" + b + "＝", String.valueOf(q));
    }

    private Question genG4T1_2() { // 三位数除以两位数试商口算
        int b = 12 + random.nextInt(30);
        int q = 3 + random.nextInt(7);
        int a = b * q;
        return new Question(a + "÷" + b + "＝", String.valueOf(q));
    }

    private Question genG4T1_3() { // 加法交换律与结合律凑整
        int a = 15 + random.nextInt(70);
        int complement = 100 - a;
        int c = 10 + random.nextInt(80);
        return new Question(a + "＋" + c + "＋" + complement + "＝", String.valueOf(100 + c));
    }

    private Question genG4T1_4() { // 带括号四则混合两步口算
        int a = 10 + random.nextInt(40);
        int b = 10 + random.nextInt(40);
        int c = 2 + random.nextInt(4);
        return new Question("(" + a + "＋" + b + ")×" + c + "＝", String.valueOf((a + b) * c));
    }

    private Question genG4T1_5() { // 除法简便速算口算
        int b = (1 + random.nextInt(4)) * 5; // 5, 10, 15, 20
        int q = 4 + random.nextInt(15);
        int a = b * q;
        return new Question(a + "÷" + b + "＝", String.valueOf(q));
    }

    private Question genG4T1_6() { // 四年级上综合
        int r = random.nextInt(5);
        return switch (r) {
            case 0 -> genG4T1_1();
            case 1 -> genG4T1_2();
            case 2 -> genG4T1_3();
            case 3 -> genG4T1_4();
            default -> genG4T1_5();
        };
    }

    // ==========================================
    // 四年级第二学期 (苏教版)
    // ==========================================
    private Question genG4T2_1() { // 整百数乘整十数口算
        int a = (1 + random.nextInt(9)) * 100;
        int b = (1 + random.nextInt(5)) * 10;
        return new Question(a + "×" + b + "＝", String.valueOf(a * b));
    }

    private Question genG4T2_2() { // 乘法交换律与结合律简算 (含25*4或125*8)
        if (random.nextBoolean()) {
            int mid = 3 + random.nextInt(15);
            return new Question("25×" + mid + "×4＝", String.valueOf(mid * 100));
        } else {
            int mid = 2 + random.nextInt(9);
            return new Question("125×" + mid + "×8＝", String.valueOf(mid * 1000));
        }
    }

    private Question genG4T2_3() { // 乘法分配律基础口算
        int a = 12 + random.nextInt(35);
        return new Question(a + "×101＝", String.valueOf(a * 101));
    }

    private Question genG4T2_4() { // 乘法分配律逆运算凑整
        int a = 12 + random.nextInt(30);
        int b1 = 20 + random.nextInt(60);
        int b2 = 100 - b1;
        return new Question(a + "×" + b1 + "＋" + a + "×" + b2 + "＝", String.valueOf(a * 100));
    }

    private Question genG4T2_5() { // 运算律两步综合简算
        int a = 10 + random.nextInt(40);
        return new Question(a + "×99＋" + a + "＝", String.valueOf(a * 100));
    }

    private Question genG4T2_6() { // 四年级下综合
        int r = random.nextInt(5);
        return switch (r) {
            case 0 -> genG4T2_1();
            case 1 -> genG4T2_2();
            case 2 -> genG4T2_3();
            case 3 -> genG4T2_4();
            default -> genG4T2_5();
        };
    }

    // ==========================================
    // 五年级第一学期 (苏教版)
    // ==========================================
    private Question genG5T1_1() { // 正负数与相反数口算
        int a = random.nextInt(15) - 7; // -7..7
        int b = random.nextInt(15) - 7;
        int sum = a + b;
        String aStr = a < 0 ? "(" + a + ")" : String.valueOf(a);
        String bStr = b < 0 ? "(" + b + ")" : String.valueOf(b);
        return new Question(aStr + "＋" + bStr + "＝", String.valueOf(sum));
    }

    private Question genG5T1_2() { // 两位小数加法口算
        int a = 10 + random.nextInt(400); // 0.10..4.00
        int b = 10 + random.nextInt(400);
        double d1 = a / 100.0;
        double d2 = b / 100.0;
        double sum = Math.round((d1 + d2) * 100.0) / 100.0;
        return new Question(String.format("%.2f＋%.2f＝", d1, d2), String.valueOf(sum));
    }

    private Question genG5T1_3() { // 两位小数减法口算
        int b = 10 + random.nextInt(300);
        int a = b + 10 + random.nextInt(300);
        double d1 = a / 100.0;
        double d2 = b / 100.0;
        double diff = Math.round((d1 - d2) * 100.0) / 100.0;
        return new Question(String.format("%.2f－%.2f＝", d1, d2), String.valueOf(diff));
    }

    private Question genG5T1_4() { // 小数乘法口算
        int a = 1 + random.nextInt(9);
        int b = 1 + random.nextInt(9);
        double d1 = a / 10.0;
        double d2 = b / 10.0;
        double p = Math.round((d1 * d2) * 100.0) / 100.0;
        return new Question(String.format("%.1f×%.1f＝", d1, d2), String.valueOf(p));
    }

    private Question genG5T1_5() { // 小数除法口算
        int q = 1 + random.nextInt(9);
        int b = 1 + random.nextInt(9);
        double d2 = b / 10.0;
        double d1 = Math.round((d2 * q) * 100.0) / 100.0;
        return new Question(String.format("%.2f÷%.1f＝", d1, d2), String.valueOf(q));
    }

    private Question genG5T1_6() { // 小数移动小数点
        double val = (10 + random.nextInt(890)) / 100.0;
        int factor = random.nextBoolean() ? 10 : 100;
        if (random.nextBoolean()) {
            double res = Math.round(val * factor * 100.0) / 100.0;
            return new Question(val + "×" + factor + "＝", String.valueOf(res));
        } else {
            double res = Math.round((val / factor) * 10000.0) / 10000.0;
            return new Question(val + "÷" + factor + "＝", String.valueOf(res));
        }
    }

    private Question genG5T1_7() { // 五年级上综合
        int r = random.nextInt(6);
        return switch (r) {
            case 0 -> genG5T1_1();
            case 1 -> genG5T1_2();
            case 2 -> genG5T1_3();
            case 3 -> genG5T1_4();
            case 4 -> genG5T1_5();
            default -> genG5T1_6();
        };
    }

    // ==========================================
    // 五年级第二学期 (苏教版)
    // ==========================================
    private Question genG5T2_1() { // 最大公因数与最小公倍数
        int a = 2 + random.nextInt(8);
        int b = 2 + random.nextInt(8);
        int gcd = gcd(a, b);
        int lcm = (a * b) / gcd;
        if (random.nextBoolean()) {
            return new Question("(" + a + ", " + b + ")最大公因数＝", String.valueOf(gcd));
        } else {
            return new Question("[" + a + ", " + b + "]最小公倍数＝", String.valueOf(lcm));
        }
    }

    private Question genG5T2_2() { // 简单的异分母分数加法
        int[] dPool = {2, 3, 4, 5, 6, 8, 10, 12};
        int d1 = dPool[random.nextInt(dPool.length)];
        int d2 = dPool[random.nextInt(dPool.length)];
        while (d1 == d2) d2 = dPool[random.nextInt(dPool.length)];
        int m1 = 1 + random.nextInt(Math.min(2, d1));
        int m2 = 1 + random.nextInt(Math.min(2, d2));
        int num = m1 * d2 + m2 * d1;
        int den = d1 * d2;
        int g = gcd(num, den);
        num /= g;
        den /= g;
        String ans = (num % den == 0) ? String.valueOf(num / den) : (num + "/" + den);
        return new Question(m1 + "/" + d1 + "＋" + m2 + "/" + d2 + "＝", ans);
    }

    private Question genG5T2_3() { // 简单的异分母分数减法
        int[] dPool = {2, 3, 4, 5, 6, 8, 10, 12};
        int d1 = dPool[random.nextInt(dPool.length)];
        int d2 = dPool[random.nextInt(dPool.length)];
        while (d1 == d2) d2 = dPool[random.nextInt(dPool.length)];
        int m1 = 1 + random.nextInt(Math.min(2, d1));
        int m2 = 1 + random.nextInt(Math.min(2, d2));
        if (m1 * d2 < m2 * d1) {
            int td = d1; d1 = d2; d2 = td;
            int tm = m1; m1 = m2; m2 = tm;
        }
        int num = m1 * d2 - m2 * d1;
        int den = d1 * d2;
        int g = gcd(Math.abs(num), den);
        if (g == 0) g = 1;
        num /= g;
        den /= g;
        String ans = (num == 0) ? "0" : (num % den == 0 ? String.valueOf(num / den) : (num + "/" + den));
        return new Question(m1 + "/" + d1 + "－" + m2 + "/" + d2 + "＝", ans);
    }

    private Question genG5T2_4() { // 分数加减混合运算 (真随机 3 项)
        int mode = random.nextInt(5);
        if (mode == 0) {
            int d = 4 + random.nextInt(6);
            int m1 = 1 + random.nextInt(d - 2);
            int m2 = 1 + random.nextInt(d - 1 - m1);
            int rem = d - m1 - m2;
            return new Question("1－" + m1 + "/" + d + "－" + m2 + "/" + d + "＝", rem + "/" + d);
        } else if (mode == 1) {
            int a = random.nextBoolean() ? 1 : 3;
            return new Question(a + "/4＋1/2－1/4＝", (a == 1 ? "1/2" : "1"));
        } else if (mode == 2) {
            int a = 1 + random.nextInt(3);
            int sumNum = 3 + a;
            int g = gcd(sumNum, 6);
            String ans = (sumNum == 6) ? "1" : ((sumNum / g) + "/" + (6 / g));
            return new Question("1/3＋1/6＋" + a + "/6＝", ans);
        } else if (mode == 3) {
            int d = 5 + random.nextInt(5);
            int m1 = 1 + random.nextInt(3);
            int m2 = 1 + random.nextInt(3);
            int m3 = 1;
            int num = m1 + m2 - m3;
            return new Question(m1 + "/" + d + "＋" + m2 + "/" + d + "－" + m3 + "/" + d + "＝", num + "/" + d);
        } else {
            return new Question("3/4－1/2＋1/4＝", "1/2");
        }
    }

    private Question genG5T2_5() { // 一步简易方程求解口算
        int ans = 2 + random.nextInt(15);
        if (random.nextBoolean()) {
            int b = 5 + random.nextInt(30);
            return new Question("x＋" + b + "＝" + (ans + b) + ", x＝", String.valueOf(ans));
        } else {
            int k = 2 + random.nextInt(8);
            return new Question(k + "x＝" + (k * ans) + ", x＝", String.valueOf(ans));
        }
    }

    private Question genG5T2_6() { // 五年级下综合
        int r = random.nextInt(5);
        return switch (r) {
            case 0 -> genG5T2_1();
            case 1 -> genG5T2_2();
            case 2 -> genG5T2_3();
            case 3 -> genG5T2_4();
            default -> genG5T2_5();
        };
    }

    // ==========================================
    // 六年级第一学期 (苏教版)
    // ==========================================
    private Question genG6T1_1() { // 分数乘整数
        int den = 2 + random.nextInt(6);
        int k = 2 + random.nextInt(5);
        int mul = den * k;
        int num = 1 + random.nextInt(den - 1);
        int ans = num * k;
        return new Question(num + "/" + den + "×" + mul + "＝", String.valueOf(ans));
    }

    private Question genG6T1_2() { // 分数乘分数
        int num1 = 1;
        int den1 = 2 + random.nextInt(4);
        int num2 = 1;
        int den2 = 2 + random.nextInt(4);
        return new Question(num1 + "/" + den1 + "×" + num2 + "/" + den2 + "＝", "1/" + (den1 * den2));
    }

    private Question genG6T1_3() { // 求倒数
        int a = 2 + random.nextInt(8);
        int b = 2 + random.nextInt(8);
        while (a == b) b = 2 + random.nextInt(8);
        return new Question(a + "/" + b + "的倒数＝", b + "/" + a);
    }

    private Question genG6T1_4() { // 分数除法口算
        int type = random.nextInt(3);
        if (type == 0) {
            // 分数除以整数
            int b = 2 + random.nextInt(5);
            int c = 2 + random.nextInt(4);
            int a = c * (1 + random.nextInt(3));
            int num = a / c;
            int den = b;
            int g = gcd(num, den);
            return new Question(a + "/" + b + "÷" + c + "＝", (num / g) + "/" + (den / g));
        } else if (type == 1) {
            // 整数除以分数
            int b = 1 + random.nextInt(3);
            int c = 2 + random.nextInt(5);
            int k = 1 + random.nextInt(4);
            int a = b * k;
            return new Question(a + "÷" + b + "/" + c + "＝", String.valueOf(k * c));
        } else {
            // 分数除以分数
            int[] dPool = {2, 3, 4, 5, 6};
            int d1 = dPool[random.nextInt(dPool.length)];
            int d2 = dPool[random.nextInt(dPool.length)];
            int n1 = 1 + random.nextInt(d1 - 1);
            int n2 = 1 + random.nextInt(d2 - 1);
            int num = n1 * d2;
            int den = d1 * n2;
            int g = gcd(num, den);
            num /= g;
            den /= g;
            String ans = (num % den == 0) ? String.valueOf(num / den) : (num + "/" + den);
            return new Question(n1 + "/" + d1 + "÷" + n2 + "/" + d2 + "＝", ans);
        }
    }

    private Question genG6T1_5() { // 化简比与求比值
        int g = 2 + random.nextInt(6);
        int a = 1 + random.nextInt(4);
        int b = 1 + random.nextInt(4);
        while (a == b) b = 1 + random.nextInt(4);
        return new Question((a * g) + " : " + (b * g) + " 化简＝", a + ":" + b);
    }

    private Question genG6T1_6() { // 六年级上综合
        int r = random.nextInt(5);
        return switch (r) {
            case 0 -> genG6T1_1();
            case 1 -> genG6T1_2();
            case 2 -> genG6T1_3();
            case 3 -> genG6T1_4();
            default -> genG6T1_5();
        };
    }

    // ==========================================
    // 六年级第二学期 (苏教版)
    // ==========================================
    private Question genG6T2_1() { // 百分数与小数分数互化
        int pct = (1 + random.nextInt(19)) * 5; // 5%..95%
        return new Question(pct + "%化为小数＝", String.format("%.2f", pct / 100.0));
    }

    private Question genG6T2_2() { // 求一个数的百分之几
        int base = (1 + random.nextInt(10)) * 20; // 20, 40..200
        int pct = (1 + random.nextInt(5)) * 10;   // 10%..50%
        int ans = base * pct / 100;
        return new Question(base + "的" + pct + "%＝", String.valueOf(ans));
    }

    private Question genG6T2_3() { // 解比例
        int a = 1 + random.nextInt(6);
        int b = 2 + random.nextInt(6);
        int k = 2 + random.nextInt(4);
        return new Question("x : " + b + " ＝ " + (a * k) + " : " + (b * k) + ", x＝", String.valueOf(a));
    }

    private Question genG6T2_4() { // 小学总复习计算综合
        int type = random.nextInt(4);
        return switch (type) {
            case 0 -> {
                int a = 12 + random.nextInt(30);
                int b = 2 + random.nextInt(4);
                yield new Question(a + "×" + (b * 2) + "÷" + b + "＝", String.valueOf(a * 2));
            }
            case 1 -> {
                int a = 10 + random.nextInt(40);
                yield new Question(a + "×3/4＋" + a + "×1/4＝", String.valueOf(a));
            }
            case 2 -> {
                int m = 1 + random.nextInt(5);
                yield new Question("1/2＋" + m + "×1/2＝", (m + 1) % 2 == 0 ? String.valueOf((m + 1) / 2) : (m + 1) + "/2");
            }
            default -> {
                int k = 2 + random.nextInt(6);
                yield new Question("(1/2＋1/4)×" + (k * 4) + "＝", String.valueOf(3 * k));
            }
        };
    }

    private Question genG6T2_5() { // 六年级下综合
        int r = random.nextInt(4);
        return switch (r) {
            case 0 -> genG6T2_1();
            case 1 -> genG6T2_2();
            case 2 -> genG6T2_3();
            default -> genG6T2_4();
        };
    }

    // ==========================================
    // 七年级第一学期 (初中 / 苏科版)
    // ==========================================
    private Question genG7T1_1() { // 有理数加法（含负数）
        int a = -(2 + random.nextInt(15));
        int b = 1 + random.nextInt(15);
        return new Question("(" + a + ")＋" + b + "＝", String.valueOf(a + b));
    }

    private Question genG7T1_2() { // 有理数减法（含负数）
        int a = random.nextInt(20) - 10;
        int b = -(1 + random.nextInt(15));
        return new Question(a + "－(" + b + ")＝", String.valueOf(a - b));
    }

    private Question genG7T1_3() { // 有理数乘除法
        int a = -(2 + random.nextInt(9));
        int b = 2 + random.nextInt(9);
        if (random.nextBoolean()) {
            return new Question("(" + a + ")×" + b + "＝", String.valueOf(a * b));
        } else {
            int prod = a * b;
            return new Question("(" + prod + ")÷(" + a + ")＝", String.valueOf(b));
        }
    }

    private Question genG7T1_4() { // 有理数乘方
        int base = -(2 + random.nextInt(3)); // -2, -3, -4
        int exp = 2 + random.nextInt(3);     // 2, 3, 4
        int res = (int) Math.pow(base, exp);
        return new Question("(" + base + ")^" + exp + "＝", String.valueOf(res));
    }

    private Question genG7T1_5() { // 合并同类项
        int a = random.nextInt(15) - 7;
        int b = random.nextInt(15) - 7;
        while (a + b == 0) b = 2 + random.nextInt(5);
        return new Question(a + "a＋" + b + "a＝", (a + b) + "a");
    }

    private Question genG7T1_6() { // 七年级上综合
        int r = random.nextInt(5);
        return switch (r) {
            case 0 -> genG7T1_1();
            case 1 -> genG7T1_2();
            case 2 -> genG7T1_3();
            case 3 -> genG7T1_4();
            default -> genG7T1_5();
        };
    }

    // ==========================================
    // 七年级第二学期 (初中 / 苏科版)
    // ==========================================
    private Question genG7T2_1() { // 同底数幂乘除口算
        int m = 2 + random.nextInt(5);
        int n = 2 + random.nextInt(5);
        if (random.nextBoolean()) {
            return new Question("a^" + m + "·a^" + n + "＝", "a^" + (m + n));
        } else {
            int total = m + n;
            return new Question("x^" + total + "÷x^" + m + "＝", "x^" + n);
        }
    }

    private Question genG7T2_2() { // 幂的乘方与积的乘方
        int m = 2 + random.nextInt(4);
        int n = 2 + random.nextInt(4);
        return new Question("(a^" + m + ")^" + n + "＝", "a^" + (m * n));
    }

    private Question genG7T2_3() { // 单项式乘除整式
        int c1 = 2 + random.nextInt(5);
        int c2 = 2 + random.nextInt(5);
        return new Question(c1 + "x·" + c2 + "x²＝", (c1 * c2) + "x³");
    }

    private Question genG7T2_4() { // 平方差公式口算
        int a = 1 + random.nextInt(6);
        return new Question("(x＋" + a + ")(x－" + a + ")＝", "x²－" + (a * a));
    }

    private Question genG7T2_5() { // 完全平方公式口算
        int a = 1 + random.nextInt(5);
        return new Question("(x＋" + a + ")²＝", "x²＋" + (2 * a) + "x＋" + (a * a));
    }

    private Question genG7T2_6() { // 一元一次方程口算
        int ans = random.nextInt(15) - 5;
        int k = 2 + random.nextInt(4);
        int b = 1 + random.nextInt(10);
        return new Question(k + "x－" + b + "＝" + (k * ans - b) + ", x＝", String.valueOf(ans));
    }

    // ==========================================
    // 八年级第一学期 (初中 / 苏科版)
    // ==========================================
    private Question genG8T1_1() { // 算术平方根口算
        int base = 2 + random.nextInt(15); // 2..16
        return new Question("√" + (base * base) + "＝", String.valueOf(base));
    }

    private Question genG8T1_2() { // 平方根与立方根口算
        if (random.nextBoolean()) {
            int b = 2 + random.nextInt(10);
            return new Question("±√" + (b * b) + "＝", "±" + b);
        } else {
            int b = 2 + random.nextInt(5);
            return new Question("∛" + (b * b * b) + "＝", String.valueOf(b));
        }
    }

    private Question genG8T1_3() { // 实数简单加减口算
        int a = 1 + random.nextInt(6);
        int b = 1 + random.nextInt(6);
        return new Question(a + "√3＋" + b + "√3＝", (a + b) + "√3");
    }

    private Question genG8T1_4() { // 负整数指数幂口算
        int base = 2 + random.nextInt(3);
        int exp = 1 + random.nextInt(3);
        int val = (int) Math.pow(base, exp);
        return new Question(base + "^(-" + exp + ")＝", "1/" + val);
    }

    private Question genG8T1_5() { // 零指数幂与乘方综合口算
        int type = random.nextInt(6);
        return switch (type) {
            case 0 -> {
                int val = random.nextBoolean() ? (2 + random.nextInt(98)) : -(2 + random.nextInt(98));
                yield new Question("(" + val + ")⁰＝", "1");
            }
            case 1 -> {
                String[] bases = {"π－3.14", "π－3", "√2－1", "√3－2", "2024", "1/3"};
                String b = bases[random.nextInt(bases.length)];
                yield new Question("(" + b + ")⁰＝", "1");
            }
            case 2 -> {
                int p = 1 + random.nextInt(4);
                yield new Question("2⁰＋" + p + "²＝", String.valueOf(1 + p * p));
            }
            case 3 -> {
                int b = 1 + random.nextInt(6);
                yield new Question("(-5)⁰－(" + (-b) + ")＝", String.valueOf(1 + b));
            }
            case 4 -> {
                int exp = random.nextBoolean() ? 3 : 4;
                int ans = (exp == 3) ? 0 : 2;
                yield new Question("3⁰＋(-1)^" + exp + "＝", String.valueOf(ans));
            }
            default -> {
                int b = 2 + random.nextInt(5);
                yield new Question("(-" + b + ")⁰＋√4＝", "3");
            }
        };
    }

    // ==========================================
    // 八年级第二学期 (初中 / 苏科版)
    // ==========================================
    private Question genG8T2_1() { // 二次根式化简口算
        int[] out = {2, 3, 4, 5};
        int[] in = {2, 3, 5};
        int o = out[random.nextInt(out.length)];
        int i = in[random.nextInt(in.length)];
        int total = o * o * i;
        return new Question("√" + total + " 化简＝", o + "√" + i);
    }

    private Question genG8T2_2() { // 二次根式乘除法口算
        int a = 2 + random.nextInt(4);
        int b = 2 + random.nextInt(4);
        int prod = a * b;
        return new Question("√" + a + "×√" + b + "＝", "√" + prod);
    }

    private Question genG8T2_3() { // 二次根式加减混合口算
        int c1 = 1 + random.nextInt(4);
        int c2 = 1 + random.nextInt(4);
        int radical = random.nextBoolean() ? 2 : (random.nextBoolean() ? 3 : 5);
        if (random.nextBoolean()) {
            String s1 = (c1 == 1) ? "√" + radical : c1 + "√" + radical;
            String s2 = (c2 == 1) ? "√" + radical : c2 + "√" + radical;
            return new Question(s1 + "＋" + s2 + "＝", (c1 + c2) + "√" + radical);
        } else {
            int maxC = Math.max(c1, c2) + 1;
            int minC = Math.min(c1, c2);
            int diff = maxC - minC;
            String s1 = maxC + "√" + radical;
            String s2 = (minC == 1) ? "√" + radical : minC + "√" + radical;
            String ans = (diff == 1) ? "√" + radical : diff + "√" + radical;
            return new Question(s1 + "－" + s2 + "＝", ans);
        }
    }

    private Question genG8T2_4() { // 分式约分口算
        int k = 2 + random.nextInt(4);
        int varChoice = random.nextInt(3);
        if (varChoice == 0) {
            return new Question((k * 2) + "a²b÷(" + k + "ab)＝", "2a");
        } else if (varChoice == 1) {
            return new Question((k * 3) + "xy²÷(" + k + "xy)＝", "3y");
        } else {
            return new Question("(a²－ab)÷a＝", "a－b");
        }
    }

    private Question genG8T2_5() { // 分式乘除法口算
        int type = random.nextInt(5);
        return switch (type) {
            case 0 -> {
                int k = 2 + random.nextInt(4);
                yield new Question("(a/b)·(" + k + "b/a)＝", String.valueOf(k));
            }
            case 1 -> new Question("(x/y)·(y²/x)＝", "y");
            case 2 -> new Question("(a²/b)÷(a/b)＝", "a");
            case 3 -> {
                int k = 2 + random.nextInt(3);
                yield new Question("(" + k + "x/y)÷(x/y)＝", String.valueOf(k));
            }
            default -> new Question("(m/n)·(n/p)＝", "m/p");
        };
    }

    // ==========================================
    // 九年级第一学期 (初中 / 苏科版)
    // ==========================================
    private Question genG9T1_1() { // 一元二次方程直接开平方法
        int a = 2 + random.nextInt(9);
        return new Question("x²＝" + (a * a) + ", x＝", "±" + a);
    }

    private Question genG9T1_2() { // 一元二次方程因式分解求根
        int a = 1 + random.nextInt(6);
        return new Question("x(x－" + a + ")＝0, x＝", "0或" + a);
    }

    private Question genG9T1_3() { // 特殊角三角函数口算
        String[] qArr = {"sin30°＝", "cos60°＝", "tan45°＝", "sin45°＝", "cos30°＝", "tan60°＝", "cos45°＝", "tan30°＝"};
        String[] aArr = {"1/2", "1/2", "1", "√2/2", "√3/2", "√3", "√2/2", "√3/3"};
        int idx = random.nextInt(qArr.length);
        return new Question(qArr[idx], aArr[idx]);
    }

    private Question genG9T1_4() { // 九年级上综合
        int r = random.nextInt(3);
        return switch (r) {
            case 0 -> genG9T1_1();
            case 1 -> genG9T1_2();
            default -> genG9T1_3();
        };
    }

    // ==========================================
    // 九年级第二学期 (初中 / 苏科版)
    // ==========================================
    private Question genG9T2_1() { // 特殊角三角函数混合运算
        int type = random.nextInt(6);
        return switch (type) {
            case 0 -> new Question("2sin30°＋tan45°＝", "2");
            case 1 -> new Question("2cos60°－1＝", "0");
            case 2 -> new Question("tan45°＋cos60°＝", "3/2");
            case 3 -> new Question("4sin30°－tan45°＝", "1");
            case 4 -> new Question("√3tan30°＋1＝", "2");
            default -> new Question("2sin30°×tan45°＝", "1");
        };
    }

    private Question genG9T2_2() { // 二次函数顶点坐标口算
        int h = 1 + random.nextInt(5);
        int k = 1 + random.nextInt(5);
        return new Question("y＝(x－" + h + ")²＋" + k + " 顶点＝", "(" + h + "," + k + ")");
    }

    private Question genG9T2_3() { // 中考代数式计算综合
        int type = random.nextInt(6);
        return switch (type) {
            case 0 -> {
                int a = 1 + random.nextInt(5);
                yield new Question("|-" + a + "|＋(π-1)⁰＝", String.valueOf(a + 1));
            }
            case 1 -> {
                int a = 2 + random.nextInt(3);
                yield new Question("√" + (a * a) + "＋(-1)⁰＝", String.valueOf(a + 1));
            }
            case 2 -> new Question("2⁻¹＋1/2＝", "1");
            case 3 -> {
                int a = 2 + random.nextInt(3);
                yield new Question("(-" + a + ")²－√" + (a * a) + "＝", String.valueOf(a * a - a));
            }
            case 4 -> {
                int a = 2 + random.nextInt(4);
                yield new Question("(√" + a + ")²－2⁰＝", String.valueOf(a - 1));
            }
            default -> new Question("|-3|－(-2)＝", "5");
        };
    }

    private Question genG9T2_4() { // 九年级下综合
        int r = random.nextInt(3);
        return switch (r) {
            case 0 -> genG9T2_1();
            case 1 -> genG9T2_2();
            default -> genG9T2_3();
        };
    }

    private int gcd(int a, int b) {
        while (b != 0) {
            int t = b;
            b = a % b;
            a = t;
        }
        return a;
    }
}
