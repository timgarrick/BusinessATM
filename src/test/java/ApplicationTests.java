import com.timgarrick.application.ApplicationService;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

public class ApplicationTests {
    @Test
    public void createATMApplicationWithNameTest() {
        ApplicationService test = new ApplicationService("test");
        assertEquals("test", ApplicationService.bankName);
    }

    @Test
    public void runATMApplication() {
        ApplicationService test = new ApplicationService("test");
        test.run();

    }

    /*@Test
    public void createUserOptionListWith5OptionsAndReturn5WhenOption5IsChosen() {
        assertEquals(5, UserInterface.userOptionSelection("1#2#3#4#5#"));
    }*/

}
