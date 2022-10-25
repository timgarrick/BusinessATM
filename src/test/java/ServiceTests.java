import org.junit.jupiter.api.Test;
import service.AccountService;
import service.ApplicationService;
import service.UserService;

import static org.junit.Assert.*;


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

    @Test
    public void createNewApplicationServiceClass() {
        ApplicationService applicationService = new ApplicationService();
        assertNotNull(applicationService);
    }
}
