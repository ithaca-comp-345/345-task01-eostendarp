package edu.ithaca.dragon.bank;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BankAccountTest {

    @Test
    void getBalanceTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals(200, bankAccount.getBalance());
    }

    @Test
    void withdrawTest() throws InsufficientFundsException{
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        bankAccount.withdraw(100);

        assertEquals(100, bankAccount.getBalance());
        assertThrows(InsufficientFundsException.class, () -> bankAccount.withdraw(300));
    }

    @Test
    void isEmailValidTest(){
        // VALID
        assertTrue(BankAccount.isEmailValid("a@b.com"));
        assertTrue(BankAccount.isEmailValid("abc-d@mail.com"));
        assertTrue(BankAccount.isEmailValid("abc.def@mail.com"));
        assertTrue(BankAccount.isEmailValid("abc_def@mail.com"));
        assertTrue(BankAccount.isEmailValid("abc.def@mail-archive.com"));

        
        // EMPTY
        // empty prefix
        assertFalse(BankAccount.isEmailValid("@domain.com"));
        assertFalse(BankAccount.isEmailValid("domain.com"));

        // first part of domain empty
        assertFalse(BankAccount.isEmailValid("foo@.bar"));
        assertFalse(BankAccount.isEmailValid("prefix.do"));

        // last part of domain empty
        assertFalse(BankAccount.isEmailValid("abc@def."));
        assertFalse(BankAccount.isEmailValid("aaa@xyz"));

        // entire domain empty
        assertFalse(BankAccount.isEmailValid("zoz@."));
        assertFalse(BankAccount.isEmailValid("qwerty@"));

        // missing sections
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