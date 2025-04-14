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

## test4
coberutaを使って、テストカバレッジを計測してみる