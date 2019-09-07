package com.some.app.placeholder.util;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.web.client.RestTemplate;

import static java.lang.String.format;

@RequiredArgsConstructor
public class JsonPlaceholderUtil {
    private static final String POST_FILENAME_TEMPLATE = "post_%s.json";
    private static final String POST_WITH_COMMENTS_FILENAME_TEMPLATE = "post_with_comments_%s.json";
    private static final JsonFileWriter JSON_FILE_WRITER = new JsonFileWriter(new Gson());
    private static final JsonReader JSON_READER = new JsonReader(new RestTemplate());

    public static void readAllPostsAndSaveToFiles() {
        JSON_READER.getAllPosts()
                .forEach(post -> JSON_FILE_WRITER.saveToFile(post, format(POST_FILENAME_TEMPLATE, post.getId())));
    }

    public static void readAllPostsWithCommentsAndSaveToFiles() {
        JSON_READER.getAllPostsWithComments()
                .forEach(post -> JSON_FILE_WRITER.saveToFile(post,
                        format(POST_WITH_COMMENTS_FILENAME_TEMPLATE, post.getId())));
    }
}
