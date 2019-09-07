package com.some.app.placeholder;

import static com.some.app.placeholder.util.JsonPlaceholderUtil.readAllPostsAndSaveToFiles;
import static com.some.app.placeholder.util.JsonPlaceholderUtil.readAllPostsWithCommentsAndSaveToFiles;

public class JsonPlaceholderApp {
    public static void main(String[] args) {
        readAllPostsAndSaveToFiles();
        readAllPostsWithCommentsAndSaveToFiles();
    }
}
