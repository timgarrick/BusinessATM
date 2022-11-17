import com.timgarrick.application.ApplicationService;
import com.timgarrick.user.User;
import com.timgarrick.user.UserService;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;


public class UserServiceTests {

    private UserService userService = new UserService();
    private ApplicationService applicationService = new ApplicationService("Test bank");


    @Test
    @Order(1)
    public void createElevenUsers() {
        for (int i = 0; i < 11; i++) {
            userService.createUser(new User("Test", "Test", "Test"));
        }

        assertEquals(11, userService.getUserList().size());
    }

    @Test
    @Order(2)
    public void checkToSeeIfUserIDisBeingIncremented() {

        int totalUsersCreated = userService.getUserList().size();
        User lastUser = userService.getUserList().get(totalUsersCreated-1);
        int lastUserUserID = lastUser.getUserID();

        assertEquals(totalUsersCreated, lastUserUserID);
    }

    @Test
    void getUserList() {
    }

    @Test
    void findUser() {


    }

    @Test
    void refreshUserAccountList() {
    }

    @Test
    void getFlaggedMessages() {
    }

    @Test
    void welcomeUser() {
    }

    @Test
    void manageUserAccount() {
    }

    @Test
    void loggedInUserSelection() {
    }

    @Test
    void initialUserSelection() {
    }

    @Test
    void loginUser() {
    }

    @Test
    void validateUserAgainstUserList() {
    }

    @Test
    void createNewUserAccount() {
    }
}
