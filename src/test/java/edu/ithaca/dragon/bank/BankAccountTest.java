package edu.ithaca.dragon.bank;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BankAccountTest {
    @Test
    void isAmountValidTest() {
        // VALID (non-negative and <= 2 decimal places)
        // integer component amount
        assertTrue(BankAccount.isAmountValid(200));

        // 1 decimal place float component amount
        assertTrue(BankAccount.isAmountValid(.4));

        // 2 decimal place float component amount
        assertTrue(BankAccount.isAmountValid(.45));

        // integer and float components amount
        assertTrue(BankAccount.isAmountValid(10.15));

        // .01 amount -boundary case
        assertTrue(BankAccount.isAmountValid(.01));

        // 0 amount -boundary case
        assertTrue(BankAccount.isAmountValid(0));


        // INVALID

        // NEGATIVE
        // integer component amount
        assertFalse(BankAccount.isAmountValid(-16));

        // 1 decimal place float component amount
        assertFalse(BankAccount.isAmountValid(-.5));

        // 2 deciamal place float component amount
        assertFalse(BankAccount.isAmountValid(-.42));

        // integer and float components amount
        assertFalse(BankAccount.isAmountValid(-19.50));

        // -.01 amount -boundary case
        assertFalse(BankAccount.isAmountValid(-.01));


        // DECIMAL PLACES > 2
        // decimal place > 2 float component amount
        assertFalse(BankAccount.isAmountValid(.36787944117));

        // 3 decimal place float component amount -boundary case 
        assertFalse(BankAccount.isAmountValid(.256));

        // .009 amount -boundary case
        assertFalse(BankAccount.isAmountValid(.009));

        // integer and 3 decimal place float component amount
        assertFalse(BankAccount.isAmountValid(32.125));


        // NEGATIVE + DECIMAL PLACES > 2
        // float component amount
        assertFalse(BankAccount.isAmountValid(-.14159265359));

        // integer and float component amount
        assertFalse(BankAccount.isAmountValid(-602.1023));

        // -.001 amount -boundary case
        assertFalse(BankAccount.isAmountValid(-.001));

        // -.009 amount -boundary case
        assertFalse(BankAccount.isAmountValid(-.009));
    }

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

        // withdraw amount .01 greater than balance -boundary case
        final BankAccount e = new BankAccount("a@b.com", 200);
        assertThrows(InsufficientFundsException.class, () -> e.withdraw(200.01));

        // withdraw float component decimal place > 2
        final BankAccount f = new BankAccount("a@b.com", 200);
        assertThrows(IllegalArgumentException.class, () -> f.withdraw(.248));

        // withdraw integer and float component decimal place > 2
        final BankAccount g = new BankAccount("a@b.com", 200);
        assertThrows(IllegalArgumentException.class, () -> g.withdraw(20.091));

        // withdraw negative float component decimal place > 2
        final BankAccount h = new BankAccount("a@b.com", 200);
        assertThrows(IllegalArgumentException.class, () -> h.withdraw(-.009));

        // withdraw negative integer and float component decimal place > 2
        final BankAccount i = new BankAccount("a@b.com", 200);
        assertThrows(IllegalArgumentException.class, () -> i.withdraw(-20.1234));        
    }

    @Test
    void depositTest() {
        final double delta = .001;

        // VALID
        // positive integer component
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        bankAccount.deposit(20);
        assertEquals(220, bankAccount.getBalance(), delta);

        // positive 1 decimal place float component
        bankAccount = new BankAccount("a@b.com", 200);
        bankAccount.deposit(.5);
        assertEquals(200.5, bankAccount.getBalance(), delta);

        // positive 1 decimal place integer and float component
        bankAccount = new BankAccount("a@b.com", 200);
        bankAccount.deposit(125.2);
        assertEquals(325.2, bankAccount.getBalance(), delta);

        // positive 2 decimal place float component
        bankAccount = new BankAccount("a@b.com", 200);
        bankAccount.deposit(.25);
        assertEquals(200.25, bankAccount.getBalance(), delta);

        // positive 2 decimal place integer and float component
        bankAccount = new BankAccount("a@b.com", 200);
        bankAccount.deposit(88.32);
        assertEquals(288.32, bankAccount.getBalance(), delta);

        // 0 amount -boundary case
        bankAccount = new BankAccount("a@b.com", 200);
        bankAccount.deposit(0);
        assertEquals(200, bankAccount.getBalance(), delta);

        // .01 amount -boundary case
        bankAccount = new BankAccount("a@b.com", 200);
        bankAccount.deposit(.01);
        assertEquals(200.01, bankAccount.getBalance(), delta);


        // INVALID

        // NEGATIVE
        // negative integer component
        final BankAccount a = new BankAccount("a@b.com", 200);
        assertThrows(IllegalArgumentException.class, () -> a.deposit(-10));

        // negative 1 place float component
        final BankAccount b = new BankAccount("a@b.com", 200);
        assertThrows(IllegalArgumentException.class, () -> b.deposit(-.5));

        // negative 1 place integer and float component
        final BankAccount c = new BankAccount("a@b.com", 200);
        assertThrows(IllegalArgumentException.class, () -> c.deposit(-1.1));

        // negative 2 place float component
        final BankAccount d = new BankAccount("a@b.com", 200);
        assertThrows(IllegalArgumentException.class, () -> d.deposit(-.75));

        // negative 2 place integer and float component
        final BankAccount e = new BankAccount("a@b.com", 200);
        assertThrows(IllegalArgumentException.class, () -> e.deposit(-6.23));

        // -.01 amount -boundary case
        final BankAccount f = new BankAccount("a@b.com", 200);
        assertThrows(IllegalArgumentException.class, () -> f.deposit(-.01));


        // DECIMAL PLACES > 2
        // decimal place > 2 float component amount
        final BankAccount g = new BankAccount("a@b.com", 200);
        assertThrows(IllegalArgumentException.class, () -> g.deposit(.121212121212));

        // 3 decimal place float component amount -boundary case
        final BankAccount h = new BankAccount("a@b.com", 200);
        assertThrows(IllegalArgumentException.class, () -> h.deposit(.789));

        // .009 amount -boundary case
        final BankAccount i = new BankAccount("a@b.com", 200);
        assertThrows(IllegalArgumentException.class, () -> i.deposit(.009));

        // integer and 3 decimal place float component amount
        final BankAccount j = new BankAccount("a@b.com", 200);
        assertThrows(IllegalArgumentException.class, () -> j.deposit(793.539));


        // NEGATIVE + DECIMAL PLACES > 2
        // float component amount
        final BankAccount k = new BankAccount("a@b.com", 200);
        assertThrows(IllegalArgumentException.class, () -> k.deposit(-.71828));

        // integer and float component amount
        final BankAccount l = new BankAccount("a@b.com", 200);
        assertThrows(IllegalArgumentException.class, () -> l.deposit(-123.4556));

        // -.001 amount -boundary case
        final BankAccount m = new BankAccount("a@b.com", 200);
        assertThrows(IllegalArgumentException.class, () -> m.deposit(-.001));

        // -.009 amount -boundary case
        final BankAccount n = new BankAccount("a@b.com", 200);
        assertThrows(IllegalArgumentException.class, () -> n.deposit(-.009));
    }

    @Test
    void transferTest() throws InsufficientFundsException {
        final double delta = .001;

        // VALID
        // normal case
        BankAccount from = new BankAccount("from@doma.in", 200);
        BankAccount to = new BankAccount("to@doma.in", 200);
        from.transfer(to, 100);
        assertEquals(100, from.getBalance(), delta);
        assertEquals(300, to.getBalance(), delta);

        // trasfer entire balance -boundary case
        from = new BankAccount("from@doma.in", 200);
        to = new BankAccount("to@doma.in", 200);
        from.transfer(to, 200);
        assertEquals(0, from.getBalance(), delta);
        assertEquals(400, to.getBalance(), delta);

        // transfer 0 -boundary case
        from = new BankAccount("from@doma.in", 200);
        to = new BankAccount("to@doma.in", 200);
        from.transfer(to, 0);
        assertEquals(200, from.getBalance(), delta);
        assertEquals(200, to.getBalance(), delta);

        // transfer .01 -boundary case
        from = new BankAccount("from@doma.in", 200);
        to = new BankAccount("to@doma.in", 200);
        from.transfer(to, .01);
        assertEquals(199.99, from.getBalance(), delta);
        assertEquals(200.01, to.getBalance(), delta);

        // transfer leaving .01 -boundary case
        from = new BankAccount("from@doma.in", 200);
        to = new BankAccount("to@doma.in", 200);
        from.transfer(to, 199.99);
        assertEquals(.01, from.getBalance(), delta);
        assertEquals(399.99, to.getBalance(), delta);


        // INVALID
        // transfer amount larger than balance
        BankAccount f1 = new BankAccount("from@doma.in", 200);
        BankAccount t1 = new BankAccount("to@doma.in", 200);
        assertThrows(InsufficientFundsException.class, () -> f1.transfer(t1, 300));

        // transfer .01 more than balance -boundary case
        BankAccount f2 = new BankAccount("from@doma.in", 200);
        BankAccount t2 = new BankAccount("to@doma.in", 200);
        assertThrows(InsufficientFundsException.class, () -> f2.transfer(t2, 200.01));

        // transfer negative amount
        BankAccount f3 = new BankAccount("from@doma.in", 200);
        BankAccount t3 = new BankAccount("to@doma.in", 200);
        assertThrows(IllegalArgumentException.class, () -> f3.transfer(t3, -20));

        // transfer -.01 -boundary case
        BankAccount f4 = new BankAccount("from@doma.in", 200);
        BankAccount t4 = new BankAccount("to@doma.in", 200);
        assertThrows(IllegalArgumentException.class, () -> f4.transfer(t4, -.01));

        // transfer to null BankAccount
        BankAccount f5 = new BankAccount("from@doma.in", 200);
        BankAccount t5 = null;
        assertThrows(IllegalArgumentException.class, () -> f5.transfer(t5, -.01));
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

        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("a@b.com", -53));
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("a@b.com", .125));
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("a@b.com", 120.369));
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("a@b.com", -3.14));
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("a@b.com", -52.999));

    }

}