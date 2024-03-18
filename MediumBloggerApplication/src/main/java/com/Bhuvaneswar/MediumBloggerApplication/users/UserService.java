package com.Bhuvaneswar.MediumBloggerApplication.users;

import com.Bhuvaneswar.MediumBloggerApplication.users.dtos.CreateUserRequest;
import org.apache.catalina.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class UserService
{
    private final UsersRepository usersRepository;
    private final ModelMapper modelMapper;

    public UserService(UsersRepository usersRepository, ModelMapper modelMapper) {
        this.usersRepository = usersRepository;
        this.modelMapper = modelMapper;
    }

    public UserEntity createUser(CreateUserRequest c)
    {
        UserEntity newUser=modelMapper.map(c, UserEntity.class);
        //TODO: password encrypted
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
