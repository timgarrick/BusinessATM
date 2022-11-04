import com.timgarrick.account.Account;
import com.timgarrick.account.AccountType;
import com.timgarrick.user.User;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertNotNull;

public class ModelTests {

    @Test
    public void createNewUser() {
        User user = new User("test", "test", "test@test.com");
        assertNotNull(user);
    }



    @Test
    public void createNewAccount() {
        Account account = new Account("Test", AccountType.BUSINESS,new User("test", "test", "test"));
        assertNotNull(account);
    }

}
