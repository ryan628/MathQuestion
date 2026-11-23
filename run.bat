@echo off
chcp 65001 >nul
cd /d "%~dp0"
if not exist "target\math-question-1.0.0.jar" (
    echo [错误] 未找到 target\math-question-1.0.0.jar，请先运行 mvn clean package 打包。
    pause
    exit /b 1
)
start "" javaw -jar "target\math-question-1.0.0.jar" %*
