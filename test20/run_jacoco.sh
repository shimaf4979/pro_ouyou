#!/bin/bash

# ディレクトリ構造を定義
SRC_DIR="src"
BIN_DIR="bin"
LIB_DIR="lib"
REPORT_DIR="report"

# レポートディレクトリを作成
mkdir -p $REPORT_DIR
mkdir -p $BIN_DIR

# ソースファイルをコンパイル (明示的なオプションなし - システムデフォルトJava 8を使用)
javac -cp $LIB_DIR/junit-4.13.2.jar:$LIB_DIR/hamcrest-core-1.3.jar -d $BIN_DIR $SRC_DIR/*.java

# JaCoCoエージェントを使用してテストを実行
java -javaagent:$LIB_DIR/jacocoagent.jar=destfile=jacoco.exec \
     -cp $BIN_DIR:$LIB_DIR/junit-4.13.2.jar:$LIB_DIR/hamcrest-core-1.3.jar \
     org.junit.runner.JUnitCore AccountTest

# JaCoCoレポートを生成
java -jar $LIB_DIR/jacococli.jar report jacoco.exec \
     --classfiles $BIN_DIR \
     --sourcefiles $SRC_DIR \
     --html $REPORT_DIR

echo "Coverage report generated in $REPORT_DIR directory"