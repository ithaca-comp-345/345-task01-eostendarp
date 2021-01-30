package edu.ithaca.dragon.bank;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BankAccountTest {

    @Test
    void getBalanceTest() {
        // integer balance
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        assertEquals(200, bankAccount.getBalance());

        // float balance
        bankAccount = new BankAccount("a@b.com", 45.13);
        assertEquals(45.13, bankAccount.getBalance());

        // 0 balance -boundary case
        bankAccount = new BankAccount("a@b.com", 0);
        assertEquals(0, bankAccount.getBalance());

        // .01 balance -boundary case
        bankAccount = new BankAccount("a@b.com", .01);
        assertEquals(.01, bankAccount.getBalance());
    }

    @Test
    void withdrawTest() throws InsufficientFundsException{
        final double delta = .001;  // allowable difference between actual and expected values

        // VALID
        // withdraw integer
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        bankAccount.withdraw(100);
        assertEquals(100, bankAccount.getBalance(), delta);

        // withdraw integer leaving 0 -boundary case
        bankAccount = new BankAccount("a@b.com", 200);
        bankAccount.withdraw(200);
        assertEquals(0, bankAccount.getBalance(), delta);

        // withdraw float
        bankAccount = new BankAccount("a@b.com", 15.75);
        bankAccount.withdraw(13);
        assertEquals(2.75, bankAccount.getBalance(), delta);

        // withdraw float leaving 0 -boundary case
        bankAccount = new BankAccount("a@b.com", 115.42);
        bankAccount.withdraw(115.42);
        assertEquals(0, bankAccount.getBalance(), delta);

        // withdraw 0 -boundary case
        bankAccount = new BankAccount("a@b.com", 200);
        bankAccount.withdraw(0);
        assertEquals(200, bankAccount.getBalance(), delta);

        // withdraw 0 leaving 0 -boundary case
        bankAccount = new BankAccount("a@b.com", 0);
        bankAccount.withdraw(0);
        assertEquals(0, bankAccount.getBalance(), delta);

        // withdraw .01 -boundary case
        bankAccount = new BankAccount("a@b.com", 200);
        bankAccount.withdraw(.01);
        assertEquals(199.99, bankAccount.getBalance(), delta);

        // withdraw .01 leaving 0 -boundary case
        bankAccount = new BankAccount("a@b.com", .01);
        bankAccount.withdraw(.01);
        assertEquals(0, bankAccount.getBalance(), delta);

        // INVALID
        // withdraw negative integer amount
        final BankAccount a = new BankAccount("a@b.com", 200);
        assertThrows(IllegalArgumentException.class, () -> a.withdraw(-100));

        // withdraw negative float amount
        final BankAccount b = new BankAccount("a@b.com", 200);
        assertThrows(IllegalArgumentException.class, () -> b.withdraw(-10.20));

        // withdraw -.01 -boundary case
        final BankAccount c = new BankAccount("a@b.com", 200);
        assertThrows(IllegalArgumentException.class, () -> c.withdraw(-.01));

        // withdraw amount greater than balance
        final BankAccount d = new BankAccount("a@b.com", 200);
        assertThrows(InsufficientFundsException.class, () -> d.withdraw(300));
    }

    @Test
    void isEmailValidTest(){
        // VALID
        // normal cases
        assertTrue(BankAccount.isEmailValid("abc-d@mail.com"));
        assertTrue(BankAccount.isEmailValid("abc.def@mail.com"));
        assertTrue(BankAccount.isEmailValid("abc_def@mail.com"));
        assertTrue(BankAccount.isEmailValid("abc.def@mail-archive.com"));

        // single char prefix -boundary case
        assertTrue(BankAccount.isEmailValid("a@domain.com"));
        // single char low level domain -boundary case
        assertTrue(BankAccount.isEmailValid("prefix@b.com"));
        // single char prefix and low level domain -boundary case
        assertTrue(BankAccount.isEmailValid("a@b.com"));

        // special char after leading char in prefix -boundary case
        assertTrue(BankAccount.isEmailValid("p_refix@domain.com"));
        // special char before tailing char in prefix -boundary case
        assertTrue(BankAccount.isEmailValid("prefi.x@domain.com"));

        // special char after leading char in prefix -boundary case
        assertTrue(BankAccount.isEmailValid("prefix@d-omain.com"));
        // special char before tailing char in prefix -boundary case
        assertTrue(BankAccount.isEmailValid("prefix@domai-n.com"));
        
        // 'almost' consecutive special characters in prefix -boundary case
        assertTrue(BankAccount.isEmailValid("p_r.efix@domain.com"));
        // 'almost' consecutive special characters in domain -boundary case
        assertTrue(BankAccount.isEmailValid("prefix@d-o-main.com"));


        // EMPTY
        // empty prefix
        assertFalse(BankAccount.isEmailValid("@domain.com"));
        assertFalse(BankAccount.isEmailValid("domain.com"));

        // first part of domain empty
        assertFalse(BankAccount.isEmailValid("foo@.bar"));
        assertFalse(BankAccount.isEmailValid("prefix.do"));

        // last part of domain empty -boundary cases
        assertFalse(BankAccount.isEmailValid("abc@def."));
        assertFalse(BankAccount.isEmailValid("aaa@xyz"));

        // entire domain empty -boundary cases
        assertFalse(BankAccount.isEmailValid("zoz@."));
        assertFalse(BankAccount.isEmailValid("qwerty@"));

        // missing sections -boundary cases
        assertFalse( BankAccount.isEmailValid(""));
        assertFalse( BankAccount.isEmailValid("@"));
        assertFalse( BankAccount.isEmailValid("."));
        assertFalse( BankAccount.isEmailValid("@."));


        // INVALID PREFIX
        // invalid characters
        assertFalse(BankAccount.isEmailValid("inv@lid123@foo.cc"));
        assertFalse(BankAccount.isEmailValid("is.wr*ong42@mail-archive.com"));
        assertFalse(BankAccount.isEmailValid("!nc0rre(t.domain@abc.gov"));
        assertFalse(BankAccount.isEmailValid("n*t_g**d@foo-bar.zzzz"));

        // leading '_', '.', '-'
        assertFalse(BankAccount.isEmailValid("_leadingunderscore@test.go"));
        assertFalse(BankAccount.isEmailValid("-this.not@good-email.com"));
        assertFalse(BankAccount.isEmailValid(".pre.fix@doma.in"));
        assertFalse(BankAccount.isEmailValid("_@check.it"));

        // trailing '_', '.', '-'
        assertFalse(BankAccount.isEmailValid("leadingunderscore_@test.go"));
        assertFalse(BankAccount.isEmailValid("this.not-@good-email.com"));
        assertFalse(BankAccount.isEmailValid("pre.fix.@doma.in"));
        assertFalse(BankAccount.isEmailValid(".@check.it"));


        // INVALID DOMAIN
        // invalid characters
        assertFalse(BankAccount.isEmailValid("domain@uh#oh.com"));
        assertFalse(BankAccount.isEmailValid("foo-bar.baz@jun%.be"));
        assertFalse(BankAccount.isEmailValid("abc@def^ghi.jklm"));

        // leading '-'
        assertFalse(BankAccount.isEmailValid("prefix@-abc.com"));
        assertFalse(BankAccount.isEmailValid("prefix@-abc-def.uk"));
        assertFalse(BankAccount.isEmailValid("prefix@-.com"));

        // trailing '-'
        assertFalse(BankAccount.isEmailValid("prefix@abc-.com"));
        assertFalse(BankAccount.isEmailValid("prefix@abc.com-"));
        assertFalse(BankAccount.isEmailValid("prefix@abc-def-.com"));
        assertFalse(BankAccount.isEmailValid("prefix@abc-def.com-"));

        // last part of domain length < two characters
        assertFalse(BankAccount.isEmailValid("abc@def.a"));
        assertFalse(BankAccount.isEmailValid("abc@def-a.b"));
    }

    @Test
    void constructorTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals("a@b.com", bankAccount.getEmail());
        assertEquals(200, bankAccount.getBalance());
        //check for exception thrown correctly
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("", 100));
    }

}