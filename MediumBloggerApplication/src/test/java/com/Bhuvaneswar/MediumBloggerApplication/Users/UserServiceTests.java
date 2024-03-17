package com.Bhuvaneswar.MediumBloggerApplication.Users;

import com.Bhuvaneswar.MediumBloggerApplication.users.UserService;
import com.Bhuvaneswar.MediumBloggerApplication.users.dtos.CreateUserRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@AutoConfigureTestDatabase()
public class UserServiceTests {

    @Autowired
    UserService usersService;

    @Test
    void can_create_users() {

        var user = usersService.createUser(new CreateUserRequest(
                "john",
                "Bhuvan123"

        ));

        Assertions.assertNotNull(user);
        Assertions.assertEquals("john", user.getUsername());

    }
}
