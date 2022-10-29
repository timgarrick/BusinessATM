import com.timgarrick.account.AccountService;
import com.timgarrick.user.UserService;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertNotNull;


public class ServiceTests {
    @Test
    public void createNewUserServiceClass() {
        UserService userService = new UserService();
        assertNotNull(userService);
    }

    @Test
    public void createNewAccountServiceClass() {
        AccountService accountService = new AccountService();
        assertNotNull(accountService);
    }
}
