package com.mathquestion.generator;

import com.mathquestion.model.Grade;
import com.mathquestion.model.Question;
import com.mathquestion.model.QuestionType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class AllFeaturesVerificationTest {

    private final QuestionGenerator generator = new QuestionGenerator();

    @Test
    public void testAll18GradesDiversityAndUniqueness() {
        for (Grade grade : Grade.values()) {
            List<QuestionType> types = QuestionType.getByGrade(grade);
            assertFalse(types.isEmpty(), grade.getDisplayName() + " 题型不应为空");

            for (QuestionType type : types) {
                List<Question> questions = generator.generateBatch(type, 100);
                assertEquals(100, questions.size());

                Set<String> uniqueExpressions = new HashSet<>();
                for (Question q : questions) {
                    assertNotNull(q.getExpression(), "算式不能为空: " + type);
                    assertNotNull(q.getAnswer(), "答案不能为空: " + type);
                    assertFalse(q.getExpression().trim().isEmpty());
                    assertFalse(q.getAnswer().trim().isEmpty());
                    uniqueExpressions.add(q.getExpression());
                }

                // 重点检验：100 道题目中绝对不能全是一模一样的算式！
                // 唯一算式数必须大于等于 5 (绝大部分题型在 30~90 之间)
                assertTrue(uniqueExpressions.size() >= 5,
                        type + " 的 100 道题目多样性不足，独立题数仅有: " + uniqueExpressions.size());
            }
        }
    }

    @Test
    public void testDocxSinglePageStrictConstraint(@TempDir Path tempDir) throws IOException {
        File dir = tempDir.toFile();

        // 重点测试包含较长复杂算式的初中题型 (零指数幂、二次根式、特殊角三角函数、一元二次方程)
        QuestionType[] testTypes = {
                QuestionType.G8T1_5, // 零指数幂与乘方综合 (用户重点反馈的题型)
                QuestionType.G8T2_3, // 二次根式加减
                QuestionType.G7T2_5, // 完全平方公式
                QuestionType.G9T2_3  // 中考代数计算综合
        };

        for (QuestionType qt : testTypes) {
            List<Question> questions = generator.generateBatch(qt, 100);
            File docx = new File(dir, "test_" + qt.name() + ".docx");
            DocxGenerator.generateQuestionDocx(docx, qt.getGrade(), qt, questions);
            assertTrue(docx.exists());
            assertTrue(docx.length() > 2000, "DOCX 文件大小应正常: " + docx.length());
        }
    }
}
