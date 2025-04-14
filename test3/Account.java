package test3;

public class Account {
    String owner;
    int balance;

    public Account(String owner, int balance) {
        // 入力値の検証を追加
        assert owner != null : "オーナー名はnullであってはいけません";
        assert balance >= 0 : "初期残高は0以上でなければなりません";
        
        this.owner = owner;
        this.balance = balance;
    }

    public void transfer(Account dest, int amount) {
        // 送金前の検証を追加
        assert dest != null : "送金先アカウントはnullであってはいけません";
        assert amount > 0 : "送金額は正の値でなければなりません";
        assert this.balance >= amount : "残高不足です";
        
        dest.balance += amount;
        this.balance -= amount;
        
        System.out.println("正常に送金完了。");
        // 送金後の検証を追加
        assert this.balance >= 0 : "送金後の残高が0未満になっています";
        assert dest.balance >= amount : "送金先の残高が正しく更新されていません";
    }
}