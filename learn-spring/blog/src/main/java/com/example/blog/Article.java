package com.example.blog;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Article {
    private String title;
    private String headline;
    private String content;
    private User author;
    private String slug;
    private LocalDateTime addedAt;
    @Id @GeneratedValue
    private Long id;

    public Article(String title, String headline, String content, User author, String slug, LocalDateTime addedAt, Long id) {
        this.title = title;
        this.headline = headline;
        this.content = content;
        this.author = author;
        this.slug = slug;
        this.addedAt = addedAt;
        this.id = id;
    }

    public Article(String title, String headline, String content, User author, String slug, LocalDateTime addedAt) {
        this(title, headline, content, author, slug, addedAt, null);
    }

    public Article(String title, String headline, String content, User author, String slug) {
        this(title, headline, content, author, slug, LocalDateTime.now());
    }

    public Article(String title, String headline, String content, User author) {
        this(title, headline, content, author, StringUtil.toSlug(title));
    }
}
