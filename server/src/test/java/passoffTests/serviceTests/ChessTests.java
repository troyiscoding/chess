package passoffTests.serviceTests;

import model.UserData;
import org.junit.jupiter.api.Assertions;

public class ChessTests {
    // test register
    public void registerTest() {
        // test register
        if (true) {
            var username = "test";
            var password = "test";
            var email = "test@gmail.com";
            var user = new UserData(username, password, email);
            Assertions.assertEquals("test", user.username());
        }
    }


    // Negative test register with null username
    // test register with null password
    // test register with null email
    // test register with existing username


    // test login

    // Negative options test login with null username,test login with null password
    // test login with incorrect username, test login with incorrect password, test login with data access error


    // test logout

    // Negative options test logout with null username
    // test logout with null password, test logout with incorrect username
    // test logout with incorrect password, test logout with data access error

    //

}
