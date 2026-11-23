package com.mathquestion.generator;

import com.mathquestion.model.Grade;
import com.mathquestion.model.Question;
import com.mathquestion.model.QuestionType;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 原生 DOCX (OpenXML) 试卷与答案生成引擎 (支持单页与多页批量出题)
 * <p>
 * 严格遵循国内中小学试卷印刷排版权威规范：
 * - 标题：黑体 (SimHei)
 * - 副标题与考生信息：宋体 (SimSun)
 * - 算式数字、西文字母与符号：Times New Roman，正文字符：宋体 (SimSun)
 * - 分数运算：采用 Office OpenXML 标准 OMML (m:oMath / m:f) 规范上下真分式排版
 * - 乘方运算：右上角标准数学上标 (如 (-1)³、x²、2⁻¹)
 * 完美支持 Microsoft Word、WPS Office 等办公软件高质量显示与打印。
 */
public class DocxGenerator {

    private static final Pattern FRACTION_PATTERN = Pattern.compile("([\\d\\w√±²³⁴⁵⁶⁷⁸⁹⁺⁻]+)/([\\d\\w²³⁴⁵⁶⁷⁸⁹⁺⁻]+)");

    /**
     * 生成口算题目 DOCX 文档 (单页便利重载)
     */
    public static void generateQuestionDocx(File outputFile, Grade grade, QuestionType type, List<Question> questions) throws IOException {
        List<List<Question>> pages = new ArrayList<>();
        pages.add(questions);
        generateQuestionDocxPages(outputFile, grade, type, pages);
    }

    /**
     * 生成口算题答案 DOCX 文档 (单页便利重载)
     */
    public static void generateAnswerDocx(File outputFile, Grade grade, QuestionType type, List<Question> questions) throws IOException {
        List<List<Question>> pages = new ArrayList<>();
        pages.add(questions);
        generateAnswerDocxPages(outputFile, grade, type, pages);
    }

    /**
     * 生成口算题目 DOCX 文档 (支持多页，每页独立标题与分页符)
     *
     * @param outputFile 目标文件
     * @param grade      学期
     * @param type       题型
     * @param pages      按页划分的题目列表集合
     * @throws IOException IO 异常
     */
    public static void generateQuestionDocxPages(File outputFile, Grade grade, QuestionType type, List<List<Question>> pages) throws IOException {
        String mainTitle = grade.getTitleName() + "口算题";
        String baseSubTitle = type.getName();
        String studentInfo = "班级：__________    学号：________    姓名：__________    成绩：__________";

        String documentXml = buildMultiPageDocumentXml(mainTitle, baseSubTitle, studentInfo, pages, false);
        writeDocxZip(outputFile, documentXml);
    }

    /**
     * 生成口算题答案 DOCX 文档 (支持多页)
     *
     * @param outputFile 目标文件
     * @param grade      学期
     * @param type       题型
     * @param pages      按页划分的题目列表集合
     * @throws IOException IO 异常
     */
    public static void generateAnswerDocxPages(File outputFile, Grade grade, QuestionType type, List<List<Question>> pages) throws IOException {
        String mainTitle = grade.getTitleName() + "口算题答案";
        String baseSubTitle = type.getName();

        String documentXml = buildMultiPageDocumentXml(mainTitle, baseSubTitle, null, pages, true);
        writeDocxZip(outputFile, documentXml);
    }

    /**
     * 写入标准 DOCX ZIP 包
     */
    private static void writeDocxZip(File outputFile, String documentXml) throws IOException {
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(outputFile))) {
            // 1. [Content_Types].xml
            addZipEntry(zos, "[Content_Types].xml", buildContentTypesXml());

            // 2. _rels/.rels
            addZipEntry(zos, "_rels/.rels", buildPackageRelsXml());

            // 3. word/_rels/document.xml.rels
            addZipEntry(zos, "word/_rels/document.xml.rels", buildDocumentRelsXml());

            // 4. word/styles.xml
            addZipEntry(zos, "word/styles.xml", buildStylesXml());

            // 5. word/settings.xml
            addZipEntry(zos, "word/settings.xml", buildSettingsXml());

            // 6. word/document.xml
            addZipEntry(zos, "word/document.xml", documentXml);
        }
    }

    private static void addZipEntry(ZipOutputStream zos, String name, String content) throws IOException {
        ZipEntry entry = new ZipEntry(name);
        zos.putNextEntry(entry);
        zos.write(content.getBytes(StandardCharsets.UTF_8));
        zos.closeEntry();
    }

    /**
     * 构建多页 word/document.xml
     */
    public static String buildMultiPageDocumentXml(String mainTitle, String baseSubTitle, String studentInfo, List<List<Question>> pages, boolean isAnswer) {
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n");
        sb.append("<w:document xmlns:w=\"http://schemas.openxmlformats.org/wordprocessingml/2006/main\"\n");
        sb.append("            xmlns:m=\"http://schemas.openxmlformats.org/officeDocument/2006/math\"\n");
        sb.append("            xmlns:r=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships\">\n");
        sb.append("  <w:body>\n");

        int totalPages = pages.size();

        for (int pIdx = 0; pIdx < totalPages; pIdx++) {
            List<Question> pageQuestions = pages.get(pIdx);
            int qCount = pageQuestions.size();

            // 如果不是第一页，插入标准分页符
            if (pIdx > 0) {
                sb.append("    <w:p>\n");
                sb.append("      <w:r><w:br w:type=\"page\"/></w:r>\n");
                sb.append("    </w:p>\n");
            }

            // 副标题文字 (多页时显示 "第 X 页")
            String subTitle = baseSubTitle;
            if (totalPages > 1) {
                subTitle = baseSubTitle + "（第 " + (pIdx + 1) + " 页 / 共 " + totalPages + " 页）";
            }

            // 根据每页题目数自适应排版高度与字号
            boolean isDense = qCount >= 80; // 80~100 题启用紧凑精美排版，确保 25 行优雅容纳在整整 1 页 A4 内
            int titleBefore = isDense ? 40 : 100;
            int titleAfter = isDense ? 30 : 60;
            int subBefore = isDense ? 20 : 40;
            int subAfter = isDense ? 30 : 80;
            int infoAfter = isDense ? 50 : 120;
            int trHeight = isDense ? 500 : 540;
            // 题目标题字号与算式字号适配 (19 half-points = 9.5pt 五号字，确保初中与小学较长算式 4 列绝不折行)
            int fontSize = isDense ? 19 : 22;

            // 1. 主标题 (SimHei)
            sb.append("    <w:p>\n");
            sb.append("      <w:pPr>\n");
            sb.append("        <w:jc w:val=\"center\"/>\n");
            sb.append("        <w:spacing w:before=\"").append(titleBefore).append("\" w:after=\"").append(titleAfter).append("\"/>\n");
            sb.append("      </w:pPr>\n");
            sb.append("      <w:r>\n");
            sb.append("        <w:rPr>\n");
            sb.append("          <w:rFonts w:ascii=\"SimHei\" w:eastAsia=\"SimHei\" w:hAnsi=\"SimHei\"/>\n");
            sb.append("          <w:b/>\n");
            sb.append("          <w:sz w:val=\"32\"/>\n");
            sb.append("          <w:szCs w:val=\"32\"/>\n");
            sb.append("        </w:rPr>\n");
            sb.append("        <w:t>").append(escapeXml(mainTitle)).append("</w:t>\n");
            sb.append("      </w:r>\n");
            sb.append("    </w:p>\n");

            // 2. 副标题 (SimSun)
            if (subTitle != null && !subTitle.isEmpty()) {
                sb.append("    <w:p>\n");
                sb.append("      <w:pPr>\n");
                sb.append("        <w:jc w:val=\"center\"/>\n");
                sb.append("        <w:spacing w:before=\"").append(subBefore).append("\" w:after=\"").append(subAfter).append("\"/>\n");
                sb.append("      </w:pPr>\n");
                sb.append("      <w:r>\n");
                sb.append("        <w:rPr>\n");
                sb.append("          <w:rFonts w:ascii=\"SimSun\" w:eastAsia=\"SimSun\" w:hAnsi=\"SimSun\"/>\n");
                sb.append("          <w:b/>\n");
                sb.append("          <w:sz w:val=\"22\"/>\n");
                sb.append("          <w:szCs w:val=\"22\"/>\n");
                sb.append("        </w:rPr>\n");
                sb.append("        <w:t>").append(escapeXml(subTitle)).append("</w:t>\n");
                sb.append("      </w:r>\n");
                sb.append("    </w:p>\n");
            }

            // 3. 学生信息栏 (SimSun)
            if (studentInfo != null && !studentInfo.isEmpty()) {
                sb.append("    <w:p>\n");
                sb.append("      <w:pPr>\n");
                sb.append("        <w:jc w:val=\"center\"/>\n");
                sb.append("        <w:spacing w:before=\"20\" w:after=\"").append(infoAfter).append("\"/>\n");
                sb.append("      </w:pPr>\n");
                sb.append("      <w:r>\n");
                sb.append("        <w:rPr>\n");
                sb.append("          <w:rFonts w:ascii=\"SimSun\" w:eastAsia=\"SimSun\" w:hAnsi=\"SimSun\"/>\n");
                sb.append("          <w:sz w:val=\"18\"/>\n");
                sb.append("          <w:szCs w:val=\"18\"/>\n");
                sb.append("        </w:rPr>\n");
                sb.append("        <w:t xml:space=\"preserve\">").append(escapeXml(studentInfo)).append("</w:t>\n");
                sb.append("      </w:r>\n");
                sb.append("    </w:p>\n");
            } else {
                sb.append("    <w:p>\n");
                sb.append("      <w:pPr><w:spacing w:before=\"20\" w:after=\"50\"/></w:pPr>\n");
                sb.append("    </w:p>\n");
            }

            // 4. 4 列表格主体
            sb.append("    <w:tbl>\n");
            sb.append("      <w:tblPr>\n");
            sb.append("        <w:tblW w:w=\"9026\" w:type=\"dxa\"/>\n");
            sb.append("        <w:jc w:val=\"center\"/>\n");
            sb.append("        <w:tblBorders>\n");
            sb.append("          <w:top w:val=\"none\"/>\n");
            sb.append("          <w:left w:val=\"none\"/>\n");
            sb.append("          <w:bottom w:val=\"none\"/>\n");
            sb.append("          <w:right w:val=\"none\"/>\n");
            sb.append("          <w:insideH w:val=\"none\"/>\n");
            sb.append("          <w:insideV w:val=\"none\"/>\n");
            sb.append("        </w:tblBorders>\n");
            sb.append("        <w:tblCellMar>\n");
            sb.append("          <w:top w:w=\"10\" w:type=\"dxa\"/>\n");
            sb.append("          <w:bottom w:w=\"10\" w:type=\"dxa\"/>\n");
            sb.append("          <w:left w:w=\"30\" w:type=\"dxa\"/>\n");
            sb.append("          <w:right w:w=\"30\" w:type=\"dxa\"/>\n");
            sb.append("        </w:tblCellMar>\n");
            sb.append("      </w:tblPr>\n");
            sb.append("      <w:tblGrid>\n");
            sb.append("        <w:gridCol w:w=\"2256\"/>\n");
            sb.append("        <w:gridCol w:w=\"2256\"/>\n");
            sb.append("        <w:gridCol w:w=\"2256\"/>\n");
            sb.append("        <w:gridCol w:w=\"2256\"/>\n");
            sb.append("      </w:tblGrid>\n");

            int colCount = 4;
            for (int i = 0; i < qCount; i++) {
                if (i % colCount == 0) {
                    sb.append("      <w:tr>\n");
                    sb.append("        <w:trPr>\n");
                    sb.append("          <w:trHeight w:val=\"").append(trHeight).append("\" w:hRule=\"exact\"/>\n");
                    sb.append("        </w:trPr>\n");
                }

                Question q = pageQuestions.get(i);
                String text = isAnswer ? q.getFullEquation() : q.getExpression();

                sb.append("        <w:tc>\n");
                sb.append("          <w:tcPr>\n");
                sb.append("            <w:tcW w:w=\"2256\" w:type=\"dxa\"/>\n");
                sb.append("            <w:vAlign w:val=\"center\"/>\n");
                sb.append("            <w:noWrap/>\n");
                sb.append("          </w:tcPr>\n");
                sb.append("          <w:p>\n");
                sb.append("            <w:pPr>\n");
                sb.append("              <w:spacing w:before=\"0\" w:after=\"0\" w:line=\"240\" w:lineRule=\"auto\"/>\n");
                sb.append("            </w:pPr>\n");

                // 规范排版：分数渲染为上下真分式 (OMML)，其余算式采用 Times New Roman + 宋体
                buildCellMathXml(sb, text, fontSize, isAnswer);

                sb.append("          </w:p>\n");
                sb.append("        </w:tc>\n");

                if (i % colCount == colCount - 1 || i == qCount - 1) {
                    if (i == qCount - 1 && i % colCount != colCount - 1) {
                        for (int fill = 0; fill < (colCount - 1 - (i % colCount)); fill++) {
                            sb.append("        <w:tc>\n");
                            sb.append("          <w:tcPr><w:tcW w:w=\"2256\" w:type=\"dxa\"/><w:noWrap/></w:tcPr>\n");
                            sb.append("          <w:p/>\n");
                            sb.append("        </w:tc>\n");
                        }
                    }
                    sb.append("      </w:tr>\n");
                }
            }

            sb.append("    </w:tbl>\n");
        }

        // 5. 页面设置：标准 A4 (11906 x 16838 twip)，上下 800 twip (约 14mm)，左右 1134 twip (约 20mm)
        sb.append("    <w:sectPr>\n");
        sb.append("      <w:pgSz w:w=\"11906\" w:h=\"16838\"/>\n");
        sb.append("      <w:pgMar w:top=\"800\" w:right=\"1134\" w:bottom=\"800\" w:left=\"1134\" w:header=\"720\" w:footer=\"720\" w:gutter=\"0\"/>\n");
        sb.append("    </w:sectPr>\n");

        sb.append("  </w:body>\n");
        sb.append("</w:document>\n");
        return sb.toString();
    }

    /**
     * 构建表格单元格数学内容：支持分数上下真分式 (OMML) 与标准中考规范字体
     */
    public static void buildCellMathXml(StringBuilder sb, String text, int fontSize, boolean isAnswer) {
        if (!text.contains("/")) {
            // 普通算式，使用标准规范宋体 + Times New Roman (非加粗，工整清爽)
            sb.append("            <w:r>\n");
            sb.append("              <w:rPr>\n");
            sb.append("                <w:rFonts w:ascii=\"Times New Roman\" w:eastAsia=\"SimSun\" w:hAnsi=\"Times New Roman\" w:cs=\"Times New Roman\"/>\n");
            sb.append("                <w:sz w:val=\"").append(fontSize).append("\"/>\n");
            sb.append("                <w:szCs w:val=\"").append(fontSize).append("\"/>\n");
            if (isAnswer) {
                sb.append("                <w:color w:val=\"B22222\"/>\n");
            }
            sb.append("              </w:rPr>\n");
            sb.append("              <w:t xml:space=\"preserve\">").append(escapeXml(text)).append("</w:t>\n");
            sb.append("            </w:r>\n");
        } else {
            // 包含分数的算式，构建 OMML 数学对象，真上下分式排版
            sb.append("            <m:oMath>\n");
            Matcher m = FRACTION_PATTERN.matcher(text);
            int lastIdx = 0;
            while (m.find()) {
                if (m.start() > lastIdx) {
                    String part = text.substring(lastIdx, m.start());
                    appendOmmlRun(sb, part, fontSize, isAnswer);
                }
                String num = m.group(1);
                String den = m.group(2);
                sb.append("              <m:f>\n");
                sb.append("                <m:fPr><m:type m:val=\"bar\"/></m:fPr>\n");
                sb.append("                <m:num>\n");
                appendOmmlRun(sb, num, fontSize - 1, isAnswer);
                sb.append("                </m:num>\n");
                sb.append("                <m:den>\n");
                appendOmmlRun(sb, den, fontSize - 1, isAnswer);
                sb.append("                </m:den>\n");
                sb.append("              </m:f>\n");
                lastIdx = m.end();
            }
            if (lastIdx < text.length()) {
                String part = text.substring(lastIdx);
                appendOmmlRun(sb, part, fontSize, isAnswer);
            }
            sb.append("            </m:oMath>\n");
        }
    }

    private static void appendOmmlRun(StringBuilder sb, String text, int fontSize, boolean isAnswer) {
        sb.append("              <m:r>\n");
        sb.append("                <w:rPr>\n");
        sb.append("                  <w:rFonts w:ascii=\"Times New Roman\" w:eastAsia=\"SimSun\" w:hAnsi=\"Times New Roman\" w:cs=\"Times New Roman\"/>\n");
        sb.append("                  <w:sz w:val=\"").append(fontSize).append("\"/>\n");
        sb.append("                  <w:szCs w:val=\"").append(fontSize).append("\"/>\n");
        if (isAnswer) {
            sb.append("                  <w:color w:val=\"B22222\"/>\n");
        }
        sb.append("                </w:rPr>\n");
        sb.append("                <m:t xml:space=\"preserve\">").append(escapeXml(text)).append("</m:t>\n");
        sb.append("              </m:r>\n");
    }

    private static String buildContentTypesXml() {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                "<Types xmlns=\"http://schemas.openxmlformats.org/package/2006/content-types\">\n" +
                "  <Default Extension=\"rels\" ContentType=\"application/vnd.openxmlformats-package.relationships+xml\"/>\n" +
                "  <Default Extension=\"xml\" ContentType=\"application/xml\"/>\n" +
                "  <Override PartName=\"/word/document.xml\" ContentType=\"application/vnd.openxmlformats-officedocument.wordprocessingml.document.main+xml\"/>\n" +
                "  <Override PartName=\"/word/styles.xml\" ContentType=\"application/vnd.openxmlformats-officedocument.wordprocessingml.styles+xml\"/>\n" +
                "  <Override PartName=\"/word/settings.xml\" ContentType=\"application/vnd.openxmlformats-officedocument.wordprocessingml.settings+xml\"/>\n" +
                "</Types>";
    }

    private static String buildPackageRelsXml() {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                "<Relationships xmlns=\"http://schemas.openxmlformats.org/package/2006/relationships\">\n" +
                "  <Relationship Id=\"rId1\" Type=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships/officeDocument\" Target=\"word/document.xml\"/>\n" +
                "</Relationships>";
    }

    private static String buildDocumentRelsXml() {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                "<Relationships xmlns=\"http://schemas.openxmlformats.org/package/2006/relationships\">\n" +
                "  <Relationship Id=\"rId1\" Type=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships/styles\" Target=\"styles.xml\"/>\n" +
                "  <Relationship Id=\"rId2\" Type=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships/settings\" Target=\"settings.xml\"/>\n" +
                "</Relationships>";
    }

    private static String buildSettingsXml() {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                "<w:settings xmlns:w=\"http://schemas.openxmlformats.org/wordprocessingml/2006/main\">\n" +
                "  <w:defaultTabStop w:val=\"720\"/>\n" +
                "  <w:characterSpacingControl w:val=\"doNotCompress\"/>\n" +
                "</w:settings>";
    }

    private static String buildStylesXml() {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                "<w:styles xmlns:w=\"http://schemas.openxmlformats.org/wordprocessingml/2006/main\">\n" +
                "  <w:docDefaults>\n" +
                "    <w:rPrDefault>\n" +
                "      <w:rPr>\n" +
                "        <w:rFonts w:ascii=\"Times New Roman\" w:eastAsia=\"SimSun\" w:hAnsi=\"Times New Roman\" w:cs=\"Times New Roman\"/>\n" +
                "        <w:sz w:val=\"20\"/>\n" +
                "        <w:szCs w:val=\"20\"/>\n" +
                "        <w:lang w:val=\"en-US\" w:eastAsia=\"zh-CN\" w:bidi=\"ar-SA\"/>\n" +
                "      </w:rPr>\n" +
                "    </w:rPrDefault>\n" +
                "    <w:pPrDefault>\n" +
                "      <w:pPr>\n" +
                "        <w:spacing w:line=\"240\" w:lineRule=\"auto\"/>\n" +
                "      </w:pPr>\n" +
                "    </w:pPrDefault>\n" +
                "  </w:docDefaults>\n" +
                "</w:styles>";
    }

    private static String escapeXml(String text) {
        if (text == null) return "";
        return text.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&apos;");
    }
}
