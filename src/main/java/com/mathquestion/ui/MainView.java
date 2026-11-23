package com.mathquestion.ui;

import com.mathquestion.generator.Exporter;
import com.mathquestion.generator.QuestionGenerator;
import com.mathquestion.model.Grade;
import com.mathquestion.model.Question;
import com.mathquestion.model.QuestionType;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.awt.Desktop;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 主界面控制与视图流转
 */
public class MainView extends BorderPane {

    private final Stage primaryStage;
    private final QuestionGenerator generator;

    public static final String APP_TITLE = "中小学数学口算题出题系统 (苏教版 1-9年级)";

    // 当前选中的状态
    private Grade selectedGrade;
    private QuestionType selectedType;
    private int questionsPerPage = 100; // 默认 100 题
    private int pageCount = 1;          // 默认 1 页
    private File outputDirectory = new File(Exporter.DEFAULT_EXPORT_DIR);
    private List<List<Question>> lastGeneratedPages;
    private Exporter.ExportResult lastExportResult;

    // 界面容器
    private final StackPane contentStack;
    private final Text titleText;

    public MainView(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.generator = new QuestionGenerator();

        // 顶栏标题
        HBox header = new HBox();
        header.getStyleClass().add("header-box");
        titleText = new Text(APP_TITLE);
        titleText.getStyleClass().add("main-title");
        header.getChildren().add(titleText);
        setTop(header);

        // 中心内容区域
        contentStack = new StackPane();
        contentStack.setPadding(new Insets(10, 15, 15, 15));
        setCenter(contentStack);

        // 默认显示首页
        showHomePage();
    }

    // ==========================================
    // 页面 1: 首页（苏教版 1-9 年级学期选择）
    // ==========================================
    private void showHomePage() {
        titleText.setText(APP_TITLE);

        VBox page = new VBox(10);
        page.getStyleClass().add("page-container");
        page.setAlignment(Pos.TOP_CENTER);
        page.setPadding(new Insets(12, 20, 15, 20));

        // 顶部导言小提示
        HBox tipBox = new HBox(10);
        tipBox.setAlignment(Pos.CENTER);
        Label introText = new Label("💡 覆盖苏教版小学 1-6 年级及初中 7-9 年级全 18 个学期 · 点击学期直达对应口算速算题型");
        introText.setStyle("-fx-font-size: 13px; -fx-text-fill: #2B6CB0; -fx-font-weight: bold;");
        tipBox.getChildren().add(introText);

        // 1. 小学阶段 (1-6年级)
        Label primaryLabel = new Label("📘 小学阶段 (苏教版 1-6年级)");
        primaryLabel.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: #1A365D; -fx-padding: 4 0 2 0;");

        GridPane primaryGrid = new GridPane();
        primaryGrid.setHgap(14);
        primaryGrid.setVgap(10);
        primaryGrid.setAlignment(Pos.CENTER);

        primaryGrid.add(createGradeCard("一年级", Grade.GRADE1_TERM1, Grade.GRADE1_TERM2), 0, 0);
        primaryGrid.add(createGradeCard("二年级", Grade.GRADE2_TERM1, Grade.GRADE2_TERM2), 1, 0);
        primaryGrid.add(createGradeCard("三年级", Grade.GRADE3_TERM1, Grade.GRADE3_TERM2), 2, 0);
        primaryGrid.add(createGradeCard("四年级", Grade.GRADE4_TERM1, Grade.GRADE4_TERM2), 0, 1);
        primaryGrid.add(createGradeCard("五年级", Grade.GRADE5_TERM1, Grade.GRADE5_TERM2), 1, 1);
        primaryGrid.add(createGradeCard("六年级", Grade.GRADE6_TERM1, Grade.GRADE6_TERM2), 2, 1);

        // 2. 初中阶段 (7-9年级)
        Label juniorLabel = new Label("📐 初中阶段 (苏科版 / 苏教版 7-9年级)");
        juniorLabel.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: #2C5282; -fx-padding: 8 0 2 0;");

        GridPane juniorGrid = new GridPane();
        juniorGrid.setHgap(14);
        juniorGrid.setVgap(10);
        juniorGrid.setAlignment(Pos.CENTER);

        juniorGrid.add(createGradeCard("七年级", Grade.GRADE7_TERM1, Grade.GRADE7_TERM2), 0, 0);
        juniorGrid.add(createGradeCard("八年级", Grade.GRADE8_TERM1, Grade.GRADE8_TERM2), 1, 0);
        juniorGrid.add(createGradeCard("九年级", Grade.GRADE9_TERM1, Grade.GRADE9_TERM2), 2, 0);

        VBox contentBox = new VBox(6, tipBox, primaryLabel, primaryGrid, juniorLabel, juniorGrid);
        contentBox.setAlignment(Pos.TOP_CENTER);

        ScrollPane scrollPane = new ScrollPane(contentBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background: transparent;");

        page.getChildren().add(scrollPane);
        switchView(page);
    }

    private VBox createGradeCard(String gradeName, Grade term1, Grade term2) {
        VBox card = new VBox(6);
        card.setStyle("-fx-background-color: #F8FAFC; -fx-border-color: #CBD5E0; -fx-border-radius: 6; -fx-background-radius: 6; -fx-padding: 8 10;");
        card.setPrefWidth(250);
        card.setMinWidth(240);

        HBox topBox = new HBox(8);
        topBox.setAlignment(Pos.CENTER_LEFT);

        Label nameLabel = new Label(gradeName);
        nameLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #1A365D;");

        int t1Count = QuestionType.getByGrade(term1).size();
        int t2Count = QuestionType.getByGrade(term2).size();
        Label countLabel = new Label("(" + (t1Count + t2Count) + " 种题型)");
        countLabel.setStyle("-fx-font-size: 11px; -fx-text-fill: #718096;");

        topBox.getChildren().addAll(nameLabel, countLabel);

        HBox btnBox = new HBox(8);
        btnBox.setAlignment(Pos.CENTER);

        Button btn1 = new Button("上 册");
        btn1.getStyleClass().addAll("button-classic", "button-primary");
        btn1.setPrefWidth(112);
        btn1.setPrefHeight(32);
        btn1.setOnAction(e -> {
            this.selectedGrade = term1;
            showTypeSelectionPage();
        });

        Button btn2 = new Button("下 册");
        btn2.getStyleClass().addAll("button-classic", "button-primary");
        btn2.setPrefWidth(112);
        btn2.setPrefHeight(32);
        btn2.setOnAction(e -> {
            this.selectedGrade = term2;
            showTypeSelectionPage();
        });

        btnBox.getChildren().addAll(btn1, btn2);
        card.getChildren().addAll(topBox, btnBox);
        return card;
    }

    // ==========================================
    // 页面 2: 题型选择页
    // ==========================================
    private void showTypeSelectionPage() {
        titleText.setText(APP_TITLE);

        VBox page = new VBox(15);
        page.getStyleClass().add("page-container");
        page.setAlignment(Pos.TOP_CENTER);

        Label subHeader = new Label("请选择题型 — " + selectedGrade.getDisplayName());
        subHeader.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #1A365D; -fx-padding: 0 0 5 0;");

        List<QuestionType> types = QuestionType.getByGrade(selectedGrade);

        GridPane grid = new GridPane();
        grid.setHgap(16);
        grid.setVgap(12);
        grid.setAlignment(Pos.CENTER);

        int cols = 3;
        for (int i = 0; i < types.size(); i++) {
            QuestionType qt = types.get(i);
            Button btn = new Button(qt.getName());
            btn.getStyleClass().addAll("button-classic", "type-button");
            btn.setPrefWidth(240);
            btn.setMinWidth(230);
            btn.setPrefHeight(40);
            btn.setWrapText(false);
            btn.setOnAction(e -> {
                this.selectedType = qt;
                showCountInputPage();
            });
            grid.add(btn, i % cols, i / cols);
        }

        ScrollPane scrollPane = new ScrollPane(grid);
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background: transparent;");
        scrollPane.setPrefHeight(400);

        // 底部返回按钮
        Button backBtn = new Button("返  回");
        backBtn.getStyleClass().add("button-classic");
        backBtn.setPrefWidth(120);
        backBtn.setPrefHeight(36);
        backBtn.setOnAction(e -> showHomePage());

        page.getChildren().addAll(subHeader, scrollPane, backBtn);
        switchView(page);

        page.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ESCAPE) {
                backBtn.fire();
            }
        });
    }

    // ==========================================
    // 页面 3: 题目数与出多少页输入页
    // ==========================================
    private void showCountInputPage() {
        titleText.setText(APP_TITLE);

        VBox page = new VBox(16);
        page.getStyleClass().add("page-container");
        page.setAlignment(Pos.CENTER);

        HBox mainContent = new HBox(45);
        mainContent.setAlignment(Pos.CENTER);

        // 左侧卡片：展示当前选择
        VBox leftInfo = new VBox(15);
        leftInfo.setAlignment(Pos.CENTER_LEFT);
        leftInfo.setPrefWidth(250);

        Label chooseLabel = new Label("您选择的是:");
        chooseLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #333333;");

        Label gradeLabel = new Label(selectedGrade.getTitleName());
        gradeLabel.getStyleClass().add("label-green-value");
        gradeLabel.setStyle("-fx-font-size: 18px;");

        Label typeLabel = new Label(selectedType.getName());
        typeLabel.getStyleClass().add("label-green-value");
        typeLabel.setStyle("-fx-font-size: 18px;");

        leftInfo.getChildren().addAll(chooseLabel, gradeLabel, typeLabel);

        // 右侧卡片：输入题目数、页数与目录设置
        VBox rightInput = new VBox(12);
        rightInput.setAlignment(Pos.CENTER_LEFT);
        rightInput.setPrefWidth(330);

        // 1. 输入题目数
        Label inputPrompt = new Label("输入题目数 (每页):");
        inputPrompt.getStyleClass().add("label-green-title");

        TextField countField = new TextField(String.valueOf(questionsPerPage));
        countField.getStyleClass().add("text-field-classic");
        countField.setPrefWidth(140);
        countField.setPrefHeight(34);

        HBox chipsBox = new HBox(8);
        int[] presetCounts = {20, 50, 80, 100};
        for (int c : presetCounts) {
            Button chip = new Button(c + " 题");
            chip.getStyleClass().add("chip-button");
            chip.setOnAction(e -> countField.setText(String.valueOf(c)));
            chipsBox.getChildren().add(chip);
        }

        // 2. 出多少页 (用户明确要求的新增功能)
        Label pagePrompt = new Label("出多少页:");
        pagePrompt.getStyleClass().add("label-green-title");

        TextField pageField = new TextField(String.valueOf(pageCount));
        pageField.getStyleClass().add("text-field-classic");
        pageField.setPrefWidth(140);
        pageField.setPrefHeight(34);

        HBox pageChipsBox = new HBox(8);
        int[] presetPages = {1, 2, 3, 5, 10};
        for (int p : presetPages) {
            Button chip = new Button(p + " 页");
            chip.getStyleClass().add("chip-button");
            chip.setOnAction(e -> pageField.setText(String.valueOf(p)));
            pageChipsBox.getChildren().add(chip);
        }

        // 3. 统计汇总动态提示
        Label summaryLabel = new Label();
        summaryLabel.setStyle("-fx-font-size: 13px; -fx-text-fill: #2B6CB0; -fx-font-weight: bold; -fx-background-color: #EBF8FF; -fx-padding: 4 8; -fx-border-color: #BEE3F8; -fx-border-radius: 4; -fx-background-radius: 4;");

        Runnable updateSummary = () -> {
            try {
                int c = Integer.parseInt(countField.getText().trim());
                int p = Integer.parseInt(pageField.getText().trim());
                if (c > 0 && p > 0) {
                    summaryLabel.setText("📊 共计: " + (c * p) + " 道题（共 " + p + " 页，每页 " + c + " 题）");
                } else {
                    summaryLabel.setText("请输入大于 0 的有效数字");
                }
            } catch (Exception ignored) {
                summaryLabel.setText("请输入有效的题目数与页数");
            }
        };

        countField.textProperty().addListener((obs, oldV, newV) -> updateSummary.run());
        pageField.textProperty().addListener((obs, oldV, newV) -> updateSummary.run());
        updateSummary.run();

        // 4. 保存目录设置
        Label dirLabel = new Label("保存路径: " + outputDirectory.getAbsolutePath());
        dirLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #666666;");
        dirLabel.setWrapText(true);

        Button changeDirBtn = new Button("更改目录...");
        changeDirBtn.setStyle("-fx-font-size: 12px; -fx-padding: 3 8 3 8;");
        changeDirBtn.setOnAction(e -> {
            DirectoryChooser chooser = new DirectoryChooser();
            chooser.setTitle("选择口算题保存目录");
            if (outputDirectory.exists()) {
                chooser.setInitialDirectory(outputDirectory);
            }
            File chosen = chooser.showDialog(primaryStage);
            if (chosen != null) {
                outputDirectory = chosen;
                dirLabel.setText("保存路径: " + outputDirectory.getAbsolutePath());
            }
        });

        HBox dirBox = new HBox(10, dirLabel, changeDirBtn);
        dirBox.setAlignment(Pos.CENTER_LEFT);

        rightInput.getChildren().addAll(
                inputPrompt, countField, chipsBox,
                pagePrompt, pageField, pageChipsBox,
                summaryLabel, dirBox
        );

        mainContent.getChildren().addAll(leftInfo, rightInput);

        // 底部按钮
        HBox buttonBox = new HBox(30);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(15, 0, 5, 0));

        Button confirmBtn = new Button("确  定");
        confirmBtn.getStyleClass().addAll("button-classic", "button-primary");
        confirmBtn.setPrefWidth(120);
        confirmBtn.setPrefHeight(36);
        confirmBtn.setOnAction(e -> {
            try {
                int count = Integer.parseInt(countField.getText().trim());
                int pages = Integer.parseInt(pageField.getText().trim());

                if (count <= 0 || count > 200) {
                    showAlert("每页题目数请输入 1 到 200 之间的正整数！");
                    return;
                }
                if (pages <= 0 || pages > 50) {
                    showAlert("出题页数请输入 1 到 50 之间的正整数！");
                    return;
                }

                this.questionsPerPage = count;
                this.pageCount = pages;
                executeGenerate();
            } catch (NumberFormatException ex) {
                showAlert("请输入合法的数字！");
            }
        });

        Button backBtn = new Button("返  回");
        backBtn.getStyleClass().add("button-classic");
        backBtn.setPrefWidth(120);
        backBtn.setPrefHeight(36);
        backBtn.setOnAction(e -> showTypeSelectionPage());

        buttonBox.getChildren().addAll(confirmBtn, backBtn);

        page.getChildren().addAll(mainContent, buttonBox);
        switchView(page);

        // 快捷键支持
        countField.setOnAction(e -> pageField.requestFocus());
        pageField.setOnAction(e -> confirmBtn.fire());
        page.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                confirmBtn.fire();
            } else if (e.getCode() == KeyCode.ESCAPE) {
                backBtn.fire();
            }
        });
    }

    // ==========================================
    // 执行生成逻辑 (按页批量生成)
    // ==========================================
    private void executeGenerate() {
        try {
            this.lastGeneratedPages = new ArrayList<>();
            for (int p = 0; p < pageCount; p++) {
                List<Question> pageQs = generator.generateBatch(selectedType, questionsPerPage);
                this.lastGeneratedPages.add(pageQs);
            }

            this.lastExportResult = Exporter.exportPages(outputDirectory, selectedGrade, selectedType, lastGeneratedPages);
            showResultPage();
        } catch (Exception ex) {
            showAlert("导出文件失败: " + ex.getMessage());
        }
    }

    // ==========================================
    // 页面 4: 生成结果与打开操作页 (仅保留 口算题.docx)
    // ==========================================
    private void showResultPage() {
        titleText.setText(APP_TITLE);

        VBox page = new VBox(18);
        page.getStyleClass().add("page-container");
        page.setAlignment(Pos.CENTER);

        HBox mainContent = new HBox(45);
        mainContent.setAlignment(Pos.CENTER);

        // 左侧摘要
        VBox leftInfo = new VBox(12);
        leftInfo.setAlignment(Pos.CENTER_LEFT);
        leftInfo.setPrefWidth(260);

        Label chooseLabel = new Label("您选择的是:");
        chooseLabel.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: #333333;");

        Label gradeLabel = new Label(selectedGrade.getTitleName());
        gradeLabel.getStyleClass().add("label-green-value");

        Label typeLabel = new Label(selectedType.getName());
        typeLabel.getStyleClass().add("label-green-value");

        Label countInfo = new Label("出题规格:");
        countInfo.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #333333; -fx-padding: 8 0 0 0;");

        Label specLabel = new Label("每页 " + questionsPerPage + " 题 × 共 " + pageCount + " 页");
        specLabel.setStyle("-fx-font-size: 15px; -fx-text-fill: #2B6CB0; -fx-font-weight: bold;");

        Label totalLabel = new Label("共计: " + lastExportResult.totalQuestions() + " 道题目");
        totalLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #22543D; -fx-font-weight: bold;");

        leftInfo.getChildren().addAll(chooseLabel, gradeLabel, typeLabel, countInfo, specLabel, totalLabel);

        // 右侧文件状态与核心操作按钮 (仅保留 口算题.docx)
        VBox rightBox = new VBox(14);
        rightBox.setAlignment(Pos.CENTER_LEFT);
        rightBox.setPrefWidth(350);

        Label fileHeader = new Label("已生成 Word 试卷文档:");
        fileHeader.getStyleClass().add("label-green-title");

        VBox fileCard = new VBox(8);
        fileCard.setStyle("-fx-background-color: #F8F9FA; -fx-padding: 12 16; -fx-border-color: #CBD5E0; -fx-border-radius: 6;");

        Label fDocx = new Label("📘 口算题.docx (标准 Word 试卷排版)");
        fDocx.setStyle("-fx-font-size: 14px; -fx-text-fill: #1A365D; -fx-font-weight: bold;");

        Label fPath = new Label("保存路径: " + lastExportResult.questionDocx().getAbsolutePath());
        fPath.setStyle("-fx-font-size: 11px; -fx-text-fill: #718096;");
        fPath.setWrapText(true);

        fileCard.getChildren().addAll(fDocx, fPath);

        // Word 核心打开按钮 (大号醒目，文字完整)
        Button openDocxBtn = new Button("📄 在 Word 中打开试卷 (DOCX)");
        openDocxBtn.getStyleClass().addAll("button-classic", "button-primary");
        openDocxBtn.setPrefWidth(320);
        openDocxBtn.setMinWidth(300);
        openDocxBtn.setPrefHeight(44);
        openDocxBtn.setStyle("-fx-font-size: 15px;");
        openDocxBtn.setOnAction(e -> openFileInDesktop(lastExportResult.questionDocx()));

        // 打开所在文件夹按钮
        Button openFolderBtn = new Button("📂 打开文件所在文件夹");
        openFolderBtn.getStyleClass().add("button-classic");
        openFolderBtn.setPrefWidth(320);
        openFolderBtn.setMinWidth(300);
        openFolderBtn.setPrefHeight(38);
        openFolderBtn.setOnAction(e -> openFileInDesktop(outputDirectory));

        rightBox.getChildren().addAll(fileHeader, fileCard, openDocxBtn, openFolderBtn);

        mainContent.getChildren().addAll(leftInfo, rightBox);

        // 底部辅助操作按钮 (文字绝不截断，按钮自适应宽度)
        HBox bottomActions = new HBox(22);
        bottomActions.setAlignment(Pos.CENTER);
        bottomActions.setPadding(new Insets(16, 0, 5, 0));

        Button previewBtn = new Button("👁 实时预览题目");
        previewBtn.getStyleClass().addAll("button-classic", "action-button");
        previewBtn.setPrefWidth(150);
        previewBtn.setMinWidth(140);
        previewBtn.setPrefHeight(38);
        previewBtn.setOnAction(e -> showPreviewDialog());

        Button reGenerateBtn = new Button("🔄 再出一组题");
        reGenerateBtn.getStyleClass().addAll("button-classic", "action-button");
        reGenerateBtn.setPrefWidth(140);
        reGenerateBtn.setMinWidth(130);
        reGenerateBtn.setPrefHeight(38);
        reGenerateBtn.setOnAction(e -> executeGenerate());

        Button backHomeBtn = new Button("🏠 返回主菜单");
        backHomeBtn.getStyleClass().addAll("button-classic", "action-button");
        backHomeBtn.setPrefWidth(140);
        backHomeBtn.setMinWidth(130);
        backHomeBtn.setPrefHeight(38);
        backHomeBtn.setOnAction(e -> showHomePage());

        bottomActions.getChildren().addAll(previewBtn, reGenerateBtn, backHomeBtn);

        page.getChildren().addAll(mainContent, bottomActions);
        switchView(page);
    }

    // ==========================================
    // 弹窗预览题目与答案 (支持多页切换查看)
    // ==========================================
    private void showPreviewDialog() {
        if (lastGeneratedPages == null || lastGeneratedPages.isEmpty()) return;

        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("题目与答案实时预览 - " + selectedType.getName());
        dialog.initOwner(primaryStage);

        VBox dialogBox = new VBox(10);
        dialogBox.setPrefSize(680, 520);
        dialogBox.setPadding(new Insets(10));

        // 顶部分页指示器 (多页时展示下拉切页)
        HBox pageNavBox = new HBox(12);
        pageNavBox.setAlignment(Pos.CENTER_LEFT);
        Label pageLabel = new Label("当前预览页码:");
        pageLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 13px;");

        ComboBox<String> pageSelector = new ComboBox<>();
        for (int p = 0; p < lastGeneratedPages.size(); p++) {
            pageSelector.getItems().add("第 " + (p + 1) + " 页 (共 " + lastGeneratedPages.get(p).size() + " 题)");
        }
        pageSelector.getSelectionModel().select(0);

        Label totalSummary = new Label("共 " + lastGeneratedPages.size() + " 页 / 总计 " + lastExportResult.totalQuestions() + " 题");
        totalSummary.setStyle("-fx-text-fill: #666; -fx-font-size: 12px;");

        pageNavBox.getChildren().addAll(pageLabel, pageSelector, totalSummary);

        TabPane tabPane = new TabPane();
        tabPane.setPrefSize(660, 440);

        // 题目 Tab
        Tab questionTab = new Tab("试卷题目");
        questionTab.setClosable(false);
        ScrollPane qScroll = new ScrollPane();
        qScroll.setFitToWidth(true);
        questionTab.setContent(qScroll);

        // 答案 Tab
        Tab answerTab = new Tab("参考答案");
        answerTab.setClosable(false);
        ScrollPane aScroll = new ScrollPane();
        aScroll.setFitToWidth(true);
        answerTab.setContent(aScroll);

        // 切换页面时更新内容
        Runnable refreshPageContent = () -> {
            int selectedIdx = pageSelector.getSelectionModel().getSelectedIndex();
            if (selectedIdx < 0 || selectedIdx >= lastGeneratedPages.size()) return;
            List<Question> currentQs = lastGeneratedPages.get(selectedIdx);
            questionTab.setText("试卷题目 (第 " + (selectedIdx + 1) + " 页 · " + currentQs.size() + " 题)");
            qScroll.setContent(buildPreviewGrid(currentQs, false));
            aScroll.setContent(buildPreviewGrid(currentQs, true));
        };

        pageSelector.setOnAction(e -> refreshPageContent.run());
        refreshPageContent.run();

        tabPane.getTabs().addAll(questionTab, answerTab);

        if (lastGeneratedPages.size() > 1) {
            dialogBox.getChildren().addAll(pageNavBox, tabPane);
        } else {
            dialogBox.getChildren().add(tabPane);
        }

        dialog.getDialogPane().setContent(dialogBox);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        dialog.showAndWait();
    }

    private GridPane buildPreviewGrid(List<Question> questions, boolean showAnswer) {
        GridPane grid = new GridPane();
        grid.setHgap(16);
        grid.setVgap(10);
        grid.setPadding(new Insets(15));
        grid.setStyle("-fx-background-color: #FFFFFF;");

        int cols = 4;
        for (int i = 0; i < questions.size(); i++) {
            Question q = questions.get(i);
            String text = showAnswer ? q.getFullEquation() : q.getExpression();
            Label lbl = new Label(text);
            lbl.getStyleClass().add("math-expression");
            lbl.setStyle("-fx-font-family: 'STIX Two Math', 'Cambria Math', 'Microsoft YaHei', sans-serif; -fx-font-size: 14.5px; -fx-font-weight: bold; " + (showAnswer ? "-fx-text-fill: #C00000;" : "-fx-text-fill: #111111;"));
            grid.add(lbl, i % cols, i / cols);
        }
        return grid;
    }

    private void switchView(Region newView) {
        contentStack.getChildren().clear();
        contentStack.getChildren().add(newView);
    }

    private void openFileInDesktop(File file) {
        try {
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(file);
            } else {
                showAlert("系统不支持自动打开，请手动访问: " + file.getAbsolutePath());
            }
        } catch (Exception ex) {
            showAlert("打开失败: " + ex.getMessage() + "\n请手动访问: " + file.getAbsolutePath());
        }
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, msg, ButtonType.OK);
        alert.initOwner(primaryStage);
        alert.setHeaderText(null);
        alert.showAndWait();
    }
}
