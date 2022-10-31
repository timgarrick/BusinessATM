import com.timgarrick.account.AccountService;
import com.timgarrick.user.User;
import com.timgarrick.user.UserService;
import org.junit.jupiter.api.Test;
import com.timgarrick.application.ApplicationService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class ServiceTests {

    private UserService userService = new UserService();
    private AccountService accountService = new AccountService();
    private ApplicationService applicationService = new ApplicationService("Test bank");


    @Test
    public void createElevenUsers() {
        for (int i = 0; i < 11; i++) {
            userService.createUser(new User("Test", "Test", "Test"));
        }

        assertEquals(11, userService.getUserList().size());
    }

    @Test
    public void checkFourthElementOfUserListToSeeIfUserIDIsBeingCreated() {
        createElevenUsers();

        assertNotNull(userService.getUserList().get(4));
    }

    @Test
    public void checkToSeeIfUserIDisBeingIncremented() {
        createElevenUsers();

        int totalUsersCreated = userService.getUserList().size();
        User lastUser = userService.getUserList().get(totalUsersCreated-1);
        int lastUserUserID = lastUser.getUserID();

        assertEquals(totalUsersCreated, lastUserUserID);
    }
}
