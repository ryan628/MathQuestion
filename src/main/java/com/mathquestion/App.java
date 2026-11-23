package com.mathquestion;

import com.mathquestion.ui.MainView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

/**
 * JavaFX 主程序入口
 */
public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            System.out.println("App.start called, initializing MainView...");

            // 设置程序窗口与任务栏图标
            try {
                var iconStream = getClass().getResourceAsStream("/icons/app.png");
                if (iconStream != null) {
                    primaryStage.getIcons().add(new javafx.scene.image.Image(iconStream));
                    System.out.println("Loaded application icon successfully.");
                } else {
                    System.err.println("App icon not found: /icons/app.png");
                }
            } catch (Exception e) {
                System.err.println("Failed to load icon: " + e.getMessage());
            }

            MainView root = new MainView(primaryStage);
            Scene scene = new Scene(root, 880, 620);

            // 加载样式表
            try {
                var cssUrl = getClass().getResource("/css/style.css");
                if (cssUrl != null) {
                    scene.getStylesheets().add(cssUrl.toExternalForm());
                    System.out.println("Loaded CSS successfully.");
                } else {
                    System.err.println("CSS resource not found.");
                }
            } catch (Exception e) {
                System.err.println("Failed to load css: " + e.getMessage());
            }

            primaryStage.setTitle("中小学数学口算题出题系统 (苏教版 1-9年级)");
            primaryStage.setScene(scene);
            primaryStage.setMinWidth(840);
            primaryStage.setMinHeight(580);
            primaryStage.centerOnScreen();
            primaryStage.show();
            System.out.println("Primary stage shown successfully.");

            // 检查是否有 --snapshot 参数
            Parameters params = getParameters();
            for (String arg : params.getRaw()) {
                if (arg.startsWith("--snapshot=")) {
                    String snapPath = arg.substring("--snapshot=".length());
                    javafx.application.Platform.runLater(() -> {
                        try {
                            javafx.scene.image.WritableImage img = scene.snapshot(null);
                            java.awt.image.BufferedImage bImg = javafx.embed.swing.SwingFXUtils.fromFXImage(img, null);
                            javax.imageio.ImageIO.write(bImg, "png", new java.io.File(snapPath));
                            System.out.println("Snapshot successfully saved to: " + snapPath);
                            if (params.getRaw().contains("--exit-after-snapshot")) {
                                javafx.application.Platform.exit();
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    });
                }
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
