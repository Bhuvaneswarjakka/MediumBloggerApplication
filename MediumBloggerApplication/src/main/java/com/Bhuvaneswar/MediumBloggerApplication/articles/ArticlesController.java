package com.Bhuvaneswar.MediumBloggerApplication.articles;

import com.Bhuvaneswar.MediumBloggerApplication.users.UserEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/articles")
public class ArticlesController
{
    @GetMapping("")
    String getArticle()
    {
        return "Get All Articles";
    }

    @GetMapping("{id}")
    String getArticleById(@PathVariable String id)
    {
        return "get article with id: "+id;
    }

    @PostMapping("")
    String createArticle(@AuthenticationPrincipal UserEntity user)
    {
        return "create article called by " + user.getUsername();
    }
}
