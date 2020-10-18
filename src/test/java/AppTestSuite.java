import com.company.ATM;
import org.junit.After;
import org.junit.Test;

import static org.junit.Assert.*;

public class AppTestSuite {

    @After
    public void resetATM() {
      ATM.reset();
    }

    @Test
    public void initATM(){
        ATM atm = ATM.getInstance();
        atm.process("9000");
        assertEquals(9000.0, atm.getTotalCash(),0.0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void initATMWithInvalidData(){
        ATM atm = ATM.getInstance();
        atm.process("this is NOT a number");
    }

    @Test
    public void loginWithCorrectPin(){
        ATM atm = ATM.getInstance();
        atm.process("9000");
        atm.process("11144555 0909 0909 500 100");
        assertTrue(atm.isInUse());
    }

    @Test
    public void loginWithIncorrectPin(){
        ATM atm = ATM.getInstance();
        atm.process("9000");
        atm.process("11144555 0909 1111");
        assertFalse(atm.isInUse());
    }

    @Test
    public void operateWithoutLogin(){
        ATM atm = ATM.getInstance();
        atm.process("9000");
        atm.process("B");
        atm.process("W 200");
        assertFalse(atm.isInUse());
    }

    @Test
    public void showBalance(){
        ATM atm = ATM.getInstance();
        atm.process("9000");
        atm.process("11144555 0909 0909 500 50");
        atm.process("B");
        assertTrue(atm.isInUse());
        assertEquals(500.0, atm.getOperatingUserBalance(), 0.0);
    }


    @Test
    public void withdraw(){
        ATM atm = ATM.getInstance();
        atm.process("9000");
        atm.process("11144555 0909 0909 500 50");
        atm.process("B");
        atm.process("W 44");
        assertTrue(atm.isInUse());
        assertEquals(456.0, atm.getOperatingUserBalance(), 0.0);
    }

    @Test
    public void withdrawOverdraft(){
        ATM atm = ATM.getInstance();
        atm.process("9000");
        atm.process("11144555 0909 0909 500 50");
        atm.process("B");
        atm.process("W 520");
        assertTrue(atm.isInUse());
        assertEquals(8480.0, atm.getTotalCash(), 0.0);
        assertEquals(-20.0, atm.getOperatingUserBalance(), 0.0);
    }

    @Test
    public void withdrawExceedingly(){
        ATM atm = ATM.getInstance();
        atm.process("9000");
        atm.process("11144555 0909 0909 500 50");
        atm.process("B");
        atm.process("W 600");
        assertTrue(atm.isInUse());
        assertEquals(9000.0, atm.getTotalCash(), 0.0);
        assertEquals(500.0, atm.getOperatingUserBalance(), 0.0);
    }

    @Test
    public void notEnoughCashToWithdraw(){
        ATM atm = ATM.getInstance();
        atm.process("3");
        atm.process("11144555 0909 0909 500 50");
        atm.process("B");
        atm.process("W 20");
        assertTrue(atm.isInUse());
        assertEquals(500.0, atm.getOperatingUserBalance(), 0.0);
    }
}
