import com.timgarrick.account.Account;
import com.timgarrick.account.AccountService;
import com.timgarrick.account.AccountType;
import com.timgarrick.application.ApplicationService;
import com.timgarrick.user.User;
import com.timgarrick.user.UserService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;


public class AccountServiceTests {

    private final UserService userService = new UserService();
    private final AccountService accountService = new AccountService();
    private final ApplicationService applicationService = new ApplicationService("Test bank");


    @Test
    public void createNewClientBankAccountForClientTestAccount() {
        User clientTestUser = new User("Client test account", "password", "email");
        Account clientTestAccount = AccountService.createAccount(new Account("test", AccountType.CLIENT,clientTestUser));

        assertNotNull(clientTestAccount);
    }


}
