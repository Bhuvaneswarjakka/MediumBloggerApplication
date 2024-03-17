package com.Bhuvaneswar.MediumBloggerApplication.users.dtos;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

@Data
@Setter(AccessLevel.NONE)
public class CreateUserRequest
{
    @NonNull
    private String username;
    @Nullable
    private String password;
    @NonNull
    private String email;
}
