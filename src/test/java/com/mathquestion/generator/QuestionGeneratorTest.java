package com.mathquestion.generator;

import com.mathquestion.model.Grade;
import com.mathquestion.model.Question;
import com.mathquestion.model.QuestionType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class QuestionGeneratorTest {

    private final QuestionGenerator generator = new QuestionGenerator(42L);

    @Test
    public void testAllQuestionTypesGenerateValidQuestions() {
        QuestionType[] allTypes = QuestionType.values();
        assertTrue(allTypes.length >= 100, "题型总数应涵盖苏教版1-9年级，数量大于100种，实际: " + allTypes.length);

        for (QuestionType type : allTypes) {
            List<Question> questions = generator.generateBatch(type, 20);
            assertEquals(20, questions.size(), "题型生成数量应为20: " + type);

            for (Question q : questions) {
                assertNotNull(q.getExpression(), "算式不能为空: " + type);
                assertNotNull(q.getAnswer(), "答案不能为空: " + type);
                assertFalse(q.getExpression().trim().isEmpty(), "算式不能为空白: " + type);
                assertFalse(q.getAnswer().trim().isEmpty(), "答案不能为空白: " + type);

                // 完整算式应包含表达式和答案
                assertEquals(q.getExpression() + q.getAnswer(), q.getFullEquation());
            }
        }
    }

    @Test
    public void testOriginalGrade1And2TypesAnswerNonNegative() {
        // 验证 1-2 年级原版 51 种题型的答案均为非负整数
        for (QuestionType type : QuestionType.values()) {
            Grade g = type.getGrade();
            if (g.getYear() <= 2) {
                List<Question> questions = generator.generateBatch(type, 10);
                for (Question q : questions) {
                    int ans = Integer.parseInt(q.getAnswer().trim());
                    assertTrue(ans >= 0, "一二年级口算题答案不应为负数: " + q.getFullEquation());
                }
            }
        }
    }

    @Test
    public void testEveryGradeHasQuestionTypes() {
        assertEquals(18, Grade.values().length, "苏教版 1-9 年级共有 18 个学期");

        for (Grade g : Grade.values()) {
            List<QuestionType> types = QuestionType.getByGrade(g);
            assertNotNull(types);
            assertTrue(types.size() >= 3, g.getDisplayName() + " 至少应配置 3 种以上题型，当前为: " + types.size());
        }

        // 验证原版 1-2 年级题型数量精确一致
        assertEquals(17, QuestionType.getByGrade(Grade.GRADE1_TERM1).size(), "一年级第一学期17种题型");
        assertEquals(15, QuestionType.getByGrade(Grade.GRADE1_TERM2).size(), "一年级第二学期15种题型");
        assertEquals(12, QuestionType.getByGrade(Grade.GRADE2_TERM1).size(), "二年级第一学期12种题型");
        assertEquals(7, QuestionType.getByGrade(Grade.GRADE2_TERM2).size(), "二年级第二学期7种题型");
    }

    @Test
    public void testExporter(@TempDir Path tempDir) throws IOException {
        File dir = tempDir.toFile();
        List<Question> questions = generator.generateBatch(QuestionType.G1T1_1, 20);

        Exporter.ExportResult result = Exporter.export(dir, Grade.GRADE1_TERM1, QuestionType.G1T1_1, questions);

        // 验证只保留 口算题.docx
        assertTrue(result.questionDocx().exists(), "应生成 口算题.docx");
        assertEquals(1, result.totalPages());
        assertEquals(20, result.totalQuestions());

        // 验证其他废弃文件均不生成
        assertFalse(new File(dir, "口算题.doc").exists(), "不应生成 口算题.doc");
        assertFalse(new File(dir, "答案.doc").exists(), "不应生成 答案.doc");
        assertFalse(new File(dir, "口算题.htm").exists(), "不应生成 口算题.htm");
        assertFalse(new File(dir, "答案.htm").exists(), "不应生成 答案.htm");

        // 验证 DOCX 文件大小大于0且为合法文件
        assertTrue(result.questionDocx().length() > 1000, "DOCX 文件大小应正常");
    }

    @Test
    public void testNoCaretInExponentsAndSuperscriptFormat() {
        // 重点测试七、八、九年级包含幂运算的题型，绝对不应出现程序员计算机符号 ^
        QuestionType[] powerTypes = {
                QuestionType.G7T1_4, // 有理数乘方
                QuestionType.G7T2_1, // 同底数幂乘除
                QuestionType.G7T2_2, // 幂的乘方
                QuestionType.G8T1_4, // 负整数指数幂
                QuestionType.G8T1_5  // 零指数幂综合
        };

        for (QuestionType qt : powerTypes) {
            List<Question> questions = generator.generateBatch(qt, 50);
            for (Question q : questions) {
                assertFalse(q.getExpression().contains("^"), "算式中严禁包含程序员代码字符 ^，必须使用标准上标: " + q.getExpression());
                assertFalse(q.getAnswer().contains("^"), "答案中严禁包含程序员代码字符 ^，必须使用标准上标: " + q.getAnswer());
            }
        }
    }

    @Test
    public void testMultiPageExporterMiddleSchool(@TempDir Path tempDir) throws IOException {
        File dir = tempDir.toFile();
        List<List<Question>> pages = new java.util.ArrayList<>();
        // 测试初中七年级有理数出题与多页导出
        pages.add(generator.generateBatch(QuestionType.G7T1_1, 100)); // 100 题 第 1 页
        pages.add(generator.generateBatch(QuestionType.G7T1_1, 100)); // 100 题 第 2 页

        Exporter.ExportResult result = Exporter.exportPages(dir, Grade.GRADE7_TERM1, QuestionType.G7T1_1, pages);

        assertEquals(2, result.totalPages(), "应为 2 页");
        assertEquals(200, result.totalQuestions(), "总题数应为 200");
        assertTrue(result.questionDocx().exists(), "应生成 口算题.docx");

        // 验证其他文件均不生成
        assertFalse(new File(dir, "口算题.htm").exists());
        assertFalse(new File(dir, "口算题.doc").exists());
    }
}
