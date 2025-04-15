#!/bin/bash

# ディレクトリ構造を定義
SRC_DIR="src"
BIN_DIR="bin"
LIB_DIR="lib"
REPORT_DIR="report"

# binディレクトリを完全にクリーンアップしてから再作成
rm -rf $BIN_DIR
mkdir -p $BIN_DIR

# レポートディレクトリを作成
mkdir -p $REPORT_DIR

# 全てのJavaファイルが正しく配置されているか確認
echo "Javaファイルを確認中..."
ls -la $SRC_DIR/*.java

# 明示的にJava 8でコンパイル
echo "Java 8でコンパイル中..."
javac -source 1.8 -target 1.8 -cp $LIB_DIR/junit-4.13.2.jar:$LIB_DIR/hamcrest-core-1.3.jar -d $BIN_DIR $SRC_DIR/*.java

# コンパイル結果を確認
echo "コンパイルされたクラスファイル:"
ls -la $BIN_DIR

# JaCoCoエージェントを使用してテストを実行
echo "テスト実行中..."
java -javaagent:$LIB_DIR/jacocoagent.jar=destfile=jacoco.exec,includes=Account \
     -cp $BIN_DIR:$LIB_DIR/junit-4.13.2.jar:$LIB_DIR/hamcrest-core-1.3.jar \
     org.junit.runner.JUnitCore AccountTest

# JaCoCoレポートを生成
echo "レポート生成中..."
java -jar $LIB_DIR/jacococli.jar report jacoco.exec \
     --classfiles $BIN_DIR/Account.class \
     --sourcefiles $SRC_DIR/Account.java \
     --html $REPORT_DIR

echo "Coverage report generated in $REPORT_DIR directory"