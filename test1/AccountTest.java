package test1;

public class AccountTest {
    public static void main(String[] args) {
        testInstantiate();
        testTransfer();
    }

    private static void testInstantiate() {
        System.out.println("newできるかテスト");
        Account a = new Account("ミナト", 30000);
        if (!"ミナト".equals(a.owner)) {
            System.out.println("失敗! オーナー名が正しくありません。");
        }
        if (30000 != a.balance) {
            System.out.println("失敗! 残高が正しくありません。");
        }
    }

    private static void testTransfer() {
        System.out.println("振込処理のテスト");
        Account a1 = new Account("ミナト", 30000);
        Account a2 = new Account("ハマジマ", 20000);
        a1.transfer(a2, 5000);
        if (a1.balance != 25000 || a2.balance != 25000) {
            System.out.println("失敗! 振込処理に問題があります。");
        }
    }
}
