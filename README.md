# 第一回

## test1
書き換えただけ、簡単なもの
```
javac test1/*.java
java test1.AccountTest
```

## test2
junitを使ってみる
Assertを使ってみる(assertSame, assertNotSame, assertEquals, assertTrue, assertFalse)
```
javac -cp .:junit-4.13.2.jar:hamcrest-core-1.3.jar test2/Account.java test2/AccountTest.java  
java -cp .:junit-4.13.2.jar:hamcrest-core-1.3.jar org.junit.runner.JUnitCore test2.AccountTest
```

実行結果
```
pro_ouyou $ java -cp .:junit-4.13.2.jar:hamcrest-core-1.3.jar org.junit.runner.JUnitCore test2.AccountTest
JUnit version 4.13.2
.....
Time: 0.004

OK (5 tests)
```


## test3
assertを使ってみるが、assertは-eaオプションをつけないと行われない
```
javac test3/*.java
java test3.AccountTest
```
これで行った場合、assertのテストが行われない
```
pro_ouyou $ java test3.AccountTest
newできるかテスト
振込処理のテスト
正常に送金完了。
失敗! 振込処理に問題があります。
```
assertを使ってみる
```
java -ea test3.AccountTest
```
これで行った場合、assertのテストが行われる
```
pro_ouyou $ java -ea test3.AccountTest

newできるかテスト
振込処理のテスト
Exception in thread "main" java.lang.AssertionError: 残高不足です
        at test3.Account.transfer(Account.java:20)
        at test3.AccountTest.testTransfer(AccountTest.java:24)
        at test3.AccountTest.main(AccountTest.java:6)
```
しっかりと確認できている。

## test20
coberutaを使って、テストカバレッジを計測してみる
なかなか、カバレッジがうまくいかない
→バージョンを落としても、7にはできない
そこで、jacocoを使ってみる
```
$ chmod +x run_jacoco_clean.sh
./run_jacoco_clean.sh
Javaファイルを確認中...
-rw-r--r--  1   staff   365  4 15 03:19 src/Account.java
-rw-r--r--  1   staff  1373  4 15 11:18 src/AccountTest.java
Java 8でコンパイル中...
警告: [options] ブートストラップ・クラス・パスが-source 8と一緒に設定されていません
  ブートストラップ・クラス・パスを設定しないと、クラス・ファイルがJDK 8で実行できない場合があります
    ブートストラップ・クラス・パスを自動的に設定するため、-source 8 -target 1.8のかわりに--release 8をお薦めします
警告: [options] ソース値8は廃止されていて、今後のリリースで削除される予定です
警告: [options] ターゲット値8は廃止されていて、今後のリリースで削除される予定です
警告: [options] 廃止されたオプションについての警告を表示しないようにするには、-Xlint:オプションを使用します。
警告4個
コンパイルされたクラスファイル:
total 16
drwxr-xr-x   4   staff   128  4 15 11:28 .
drwxr-xr-x  11   staff   352  4 15 11:28 ..
-rw-r--r--   1   staff   408  4 15 11:28 Account.class
-rw-r--r--   1   staff  1527  4 15 11:28 AccountTest.class
テスト実行中...
JUnit version 4.13.2
.....
Time: 0.013

OK (5 tests)

レポート生成中...
[INFO] Loading execution data file pro_ouyou/test20/jacoco.exec.
[INFO] Analyzing 1 classes.
Coverage report generated in report directory
```