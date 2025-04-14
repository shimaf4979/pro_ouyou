package test1;

public class Account {
    String owner;
    int balance;

    public Account(String owner, int balance) {
        this.owner = owner; // 修正: this.owner を使う
        this.balance = balance; // 修正: this.balance を使う
    }

    public void transfer(Account dest, int amount) {
        dest.balance += amount;
        this.balance -= amount;
    }
}
