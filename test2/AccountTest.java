package test2;

import org.junit.Test;
import static org.junit.Assert.*;

public class AccountTest {
    @Test
    public void アカウント初期化テスト() {
        Account a = new Account("ミナト", 30000);
        assertEquals("ミナト", a.owner);
        assertEquals(30000, a.balance);
        assertNotNull(a);
    }

    @Test
    public void 送金テスト() {
        Account a1 = new Account("ミナト", 30000);
        Account a2 = new Account("ハマジマ", 20000);
        a1.transfer(a2, 5000);
        assertEquals(25000, a1.balance);
        assertEquals(25000, a2.balance);
    }

    @Test
    public void アカウント比較テスト() {
        Account a1 = new Account("ミナト", 30000);
        Account a2 = new Account("ミナト", 30000);
        Account a3 = a1;

        assertNotSame(a1, a2);
        assertSame(a1, a3);
    }

    @Test
    public void 残高ゼロテスト() {
        Account a = new Account("タカハシ", 0);
        assertEquals(0, a.balance);
        assertTrue(a.balance == 0);
    }

    @Test
    public void 全額送金テスト() {
        Account a1 = new Account("サトウ", 10000);
        Account a2 = new Account("スズキ", 5000);
        a1.transfer(a2, 10000);
        assertEquals(0, a1.balance);
        assertEquals(15000, a2.balance);
        assertFalse(a1.balance > 0);
    }
}