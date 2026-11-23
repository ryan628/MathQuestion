package com.mathquestion.generator;

import com.mathquestion.model.Grade;
import com.mathquestion.model.Question;
import com.mathquestion.model.QuestionType;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 试卷导出引擎 (仅保留并生成标准 Word 排版 口算题.docx)
 */
public class Exporter {

    public static final String DEFAULT_EXPORT_DIR = "C:\\口算题";

    /**
     * 导出结果集封装 (仅保留口算题.docx)
     */
    public record ExportResult(
            File questionDocx,
            int totalPages,
            int totalQuestions
    ) {}

    /**
     * 单页题目导出便捷重载
     */
    public static ExportResult export(File targetDir, Grade grade, QuestionType type, List<Question> questions) throws IOException {
        List<List<Question>> pages = new ArrayList<>();
        pages.add(questions);
        return exportPages(targetDir, grade, type, pages);
    }

    /**
     * 多页题目导出核心方法 (仅生成 口算题.docx)
     *
     * @param targetDir 目标目录
     * @param grade     学期
     * @param type      题型
     * @param pages     按页组织的题目列表集合
     * @return 导出的文件路径与统计数据
     * @throws IOException 文件写入异常
     */
    public static ExportResult exportPages(File targetDir, Grade grade, QuestionType type, List<List<Question>> pages) throws IOException {
        if (!targetDir.exists()) {
            boolean created = targetDir.mkdirs();
            if (!created && !targetDir.exists()) {
                throw new IOException("无法创建输出目录: " + targetDir.getAbsolutePath());
            }
        }

        File questionDocx = new File(targetDir, "口算题.docx");

        // 唯一生成文件：原生 OpenXML 口算题.docx (标准 Word 试卷排版，支持多页分页)
        DocxGenerator.generateQuestionDocxPages(questionDocx, grade, type, pages);

        int totalCount = 0;
        for (List<Question> page : pages) {
            totalCount += page.size();
        }

        return new ExportResult(questionDocx, pages.size(), totalCount);
    }

    public static void main(String[] args) {
        try {
            File dir = new File(DEFAULT_EXPORT_DIR);
            QuestionGenerator gen = new QuestionGenerator();
            List<Question> qs = gen.generateBatch(QuestionType.G1T1_1, 100);
            ExportResult res = export(dir, Grade.GRADE1_TERM1, QuestionType.G1T1_1, qs);
            System.out.println("成功导出到: " + res.questionDocx().getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
