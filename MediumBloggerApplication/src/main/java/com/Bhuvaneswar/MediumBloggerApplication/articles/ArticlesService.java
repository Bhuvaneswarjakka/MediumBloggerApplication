package com.Bhuvaneswar.MediumBloggerApplication.articles;

import com.Bhuvaneswar.MediumBloggerApplication.articles.dtos.CreateArticleRequest;
import com.Bhuvaneswar.MediumBloggerApplication.articles.dtos.UpdateArticleRequest;
import com.Bhuvaneswar.MediumBloggerApplication.users.UserService;
import com.Bhuvaneswar.MediumBloggerApplication.users.UsersRepository;
import org.springframework.stereotype.Service;

@Service
public class ArticlesService
{
    private ArticleRepository articleRepository;
    private UsersRepository usersRepository;

    public ArticlesService(ArticleRepository articleRepository, UsersRepository usersRepository) {
        this.articleRepository = articleRepository;
        this.usersRepository = usersRepository;
    }

    public Iterable<ArticleEntity> getAllArticles() {
        return articleRepository.findAll();
    }

    public ArticleEntity getArticleBySlug(String slug) {
        var article = articleRepository.findBySlug(slug);
        if (article == null) {
            throw new ArticleNotFoundException(slug);
        }
        return article;
    }

    public ArticleEntity createArticle(CreateArticleRequest a, Long authorId) {
        var author = usersRepository.findById(authorId).orElseThrow(() -> new UserService.UserNotFoundException(authorId));

        return articleRepository.save(ArticleEntity.builder()
                .title(a.getTitle())
                // TODO: create a proper slugification function
                .slug(a.getTitle().toLowerCase().replaceAll("\\s+", "-"))
                .body(a.getBody())
                .subtitle(a.getSubtitle())
                .author(author)
                .build()
        );
    }

    public ArticleEntity updateArticle(Long articleId, UpdateArticleRequest a) {
        var article = articleRepository.findById(articleId).orElseThrow(() -> new ArticleNotFoundException(articleId));

        if (a.getTitle() != null) {
            article.setTitle(a.getTitle());
            article.setSlug(a.getTitle().toLowerCase().replaceAll("\\s+", "-"));
        }

        if (a.getBody() != null) {
            article.setBody(a.getBody());
        }

        if (a.getSubtitle() != null) {
            article.setSubtitle(a.getSubtitle());
        }

        return articleRepository.save(article);
    }

    static class ArticleNotFoundException extends IllegalArgumentException
    {
        public ArticleNotFoundException(String slug) {
            super("Article " + slug + " not found");
        }

        public ArticleNotFoundException(Long id) {
            super("Article with id: " + id + " not found");
        }
    }


}
