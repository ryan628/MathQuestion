package com.mathquestion.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 苏教版 1-9 年级学期枚举 (共 18 个学期)
 */
public enum Grade {
    // 小学低段 (1-2年级)
    GRADE1_TERM1(1, 1, "一年级上册", "一年级第一学期", "小学低段"),
    GRADE1_TERM2(1, 2, "一年级下册", "一年级第二学期", "小学低段"),
    GRADE2_TERM1(2, 1, "二年级上册", "二年级第一学期", "小学低段"),
    GRADE2_TERM2(2, 2, "二年级下册", "二年级第二学期", "小学低段"),

    // 小学中段 (3-4年级)
    GRADE3_TERM1(3, 1, "三年级上册", "三年级第一学期", "小学中段"),
    GRADE3_TERM2(3, 2, "三年级下册", "三年级第二学期", "小学中段"),
    GRADE4_TERM1(4, 1, "四年级上册", "四年级第一学期", "小学中段"),
    GRADE4_TERM2(4, 2, "四年级下册", "四年级第二学期", "小学中段"),

    // 小学高段 (5-6年级)
    GRADE5_TERM1(5, 1, "五年级上册", "五年级第一学期", "小学高段"),
    GRADE5_TERM2(5, 2, "五年级下册", "五年级第二学期", "小学高段"),
    GRADE6_TERM1(6, 1, "六年级上册", "六年级第一学期", "小学高段"),
    GRADE6_TERM2(6, 2, "六年级下册", "六年级第二学期", "小学高段"),

    // 初中阶段 (7-9年级 / 苏科版初中数学)
    GRADE7_TERM1(7, 1, "七年级上册", "七年级第一学期", "初中阶段"),
    GRADE7_TERM2(7, 2, "七年级下册", "七年级第二学期", "初中阶段"),
    GRADE8_TERM1(8, 1, "八年级上册", "八年级第一学期", "初中阶段"),
    GRADE8_TERM2(8, 2, "八年级下册", "八年级第二学期", "初中阶段"),
    GRADE9_TERM1(9, 1, "九年级上册", "九年级第一学期", "初中阶段"),
    GRADE9_TERM2(9, 2, "九年级下册", "九年级第二学期", "初中阶段");

    private final int year;
    private final int term;
    private final String displayName;
    private final String titleName;
    private final String stageName;

    Grade(int year, int term, String displayName, String titleName, String stageName) {
        this.year = year;
        this.term = term;
        this.displayName = displayName;
        this.titleName = titleName;
        this.stageName = stageName;
    }

    public int getYear() {
        return year;
    }

    public int getTerm() {
        return term;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getTitleName() {
        return titleName;
    }

    public String getStageName() {
        return stageName;
    }

    public static List<Grade> getByStage(String stageName) {
        List<Grade> list = new ArrayList<>();
        for (Grade g : values()) {
            if (g.stageName.equals(stageName)) {
                list.add(g);
            }
        }
        return list;
    }

    public static List<Grade> getByYear(int year) {
        List<Grade> list = new ArrayList<>();
        for (Grade g : values()) {
            if (g.year == year) {
                list.add(g);
            }
        }
        return list;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
