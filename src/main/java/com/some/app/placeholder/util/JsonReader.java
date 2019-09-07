package com.some.app.placeholder.util;

import com.google.common.collect.ImmutableList;
import com.some.app.placeholder.model.Comment;
import com.some.app.placeholder.model.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RequiredArgsConstructor
class JsonReader {
    private static final String POSTS_URL = "https://jsonplaceholder.typicode.com/posts";
    private static final String POST_COMMENTS_URL_TEMPLATE = "https://jsonplaceholder.typicode.com/comments?postId=%s";
    private final RestTemplate restTemplate;

    List<Post> getAllPostsWithComments() {
        List<Post> posts = getAllPosts();

        posts.parallelStream()
                .forEach(post -> post.setComments(getAllCommentsForPost(post.getId())));

        return posts;
    }

    List<Post> getAllPosts() {
        return ImmutableList.copyOf(getFromUrl(POSTS_URL, Post[].class).getBody());

    }

    private List<Comment> getAllCommentsForPost(int postId) {
        return ImmutableList.copyOf(getFromUrl(String.format(POST_COMMENTS_URL_TEMPLATE, postId), Comment[].class).getBody());
    }

    private <T> ResponseEntity<T> getFromUrl(String url, Class<T> type) {
        return restTemplate.getForEntity(url, type);
    }
}