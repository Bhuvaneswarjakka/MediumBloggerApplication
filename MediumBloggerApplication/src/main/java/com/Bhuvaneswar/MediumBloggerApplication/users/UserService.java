package com.Bhuvaneswar.MediumBloggerApplication.users;

import com.Bhuvaneswar.MediumBloggerApplication.users.dtos.CreateUserRequest;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

@Service
public class UserService
{
    private final UsersRepository usersRepository;

    public UserService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public UserEntity createUser(CreateUserRequest c)
    {
        UserEntity newUser = UserEntity.builder()
                .username(c.getUsername())
//                .password(c.getPassword()) //TODO: Encrypt password
                .email(c.getEmail())
                .build();
        return usersRepository.save(newUser);
    }

    public UserEntity getUser(String username)
    {
        return usersRepository.findByUsername(username).orElseThrow(()-> new UserNotFoundException(username));
    }

    public UserEntity getUser(Long userId)
    {
        return usersRepository.findById(userId).orElseThrow(()-> new UserNotFoundException(userId));
    }

    public UserEntity loginUser(String username, String password)
    {
        var user=usersRepository.findByUsername(username).orElseThrow(()-> new UserNotFoundException(username));
        //TODO: match password
        return user;
    }

    public static class UserNotFoundException extends IllegalArgumentException {
        public UserNotFoundException(String username) {
            super("User with username: " + username + " not found");
        }

        public UserNotFoundException(Long userId) {
            super("User with id: " + userId + " not found");
        }
    }
}
