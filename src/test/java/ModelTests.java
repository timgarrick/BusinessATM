import model.Account;
import model.User;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;

public class ModelTests {

    @Test
    public void createNewUser() {
        User user = new User();
        assertNotNull(user);
    }

    @Test
    public void createNewAccount() {
        Account account = new Account();
        assertNotNull(account);
    }

}