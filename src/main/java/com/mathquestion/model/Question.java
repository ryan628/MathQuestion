package com.mathquestion.model;

/**
 * 口算题目对象
 */
public class Question {
    private final String expression; // 如 "3 ＋ 5 ＝"
    private final String answer;     // 如 "8"

    public Question(String expression, String answer) {
        this.expression = expression;
        this.answer = answer;
    }

    public String getExpression() {
        return expression;
    }

    public String getAnswer() {
        return answer;
    }

    /**
     * 完整等式，如 "3 ＋ 5 ＝ 8"
     */
    public String getFullEquation() {
        return expression + answer;
    }

    @Override
    public String toString() {
        return getFullEquation();
    }
}
