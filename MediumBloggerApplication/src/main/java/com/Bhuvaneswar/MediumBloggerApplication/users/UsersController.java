package com.Bhuvaneswar.MediumBloggerApplication.users;

import com.Bhuvaneswar.MediumBloggerApplication.users.dtos.CreateUserRequest;
import com.Bhuvaneswar.MediumBloggerApplication.users.dtos.ErrorResponse;
import com.Bhuvaneswar.MediumBloggerApplication.users.dtos.LoginUserRequest;
import com.Bhuvaneswar.MediumBloggerApplication.users.dtos.UserResponse;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/users")
public class UsersController
{
    private final UserService userService;
    private final ModelMapper modelMapper;

    public UsersController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("")
    ResponseEntity<UserResponse> userSignup(@RequestBody CreateUserRequest request)
    {
        UserEntity savedUser=userService.createUser(request);
        URI savedUserURI= URI.create("/users/" + savedUser.getId());
        var userResponse=modelMapper.map(savedUser, UserResponse.class);
        return ResponseEntity.created(savedUserURI)
                .body(userResponse);// request, response
    }

    @PostMapping("/login")
    ResponseEntity<UserResponse> userLogin(@RequestBody LoginUserRequest request)
    {
        UserEntity savedUser=userService.loginUser(request.getUsername(), request.getPassword());

        return ResponseEntity.ok(modelMapper.map(savedUser, UserResponse.class));
    }

    @ExceptionHandler({
            UserService.UserNotFoundException.class
    })
    ResponseEntity<ErrorResponse> handleUserNotFoundException(Exception e)
    {
        String message;
        HttpStatus status;

        if(e instanceof UserService.UserNotFoundException)
        {
            message=e.getMessage();
            status=HttpStatus.NOT_FOUND;
        }
        else {
            message="Something went wrong";
            status=HttpStatus.INTERNAL_SERVER_ERROR;
        }

        ErrorResponse response=ErrorResponse.builder()
                .message(message)
                .build();
        return ResponseEntity.status(status).body(response);
    }


}
